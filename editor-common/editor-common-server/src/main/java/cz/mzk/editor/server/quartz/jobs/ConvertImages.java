/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2012  Martin Rumanek (martin.rumanek@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.editor.server.quartz.jobs;

import com.google.inject.Injector;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.utils.ScanFolder;
import cz.mzk.editor.server.utils.ScanFolderImpl;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.handler.ConvertToJPEG2000Handler;
import cz.mzk.editor.shared.erraiPortable.QuartzJobAction;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.framework.RequestDispatcher;
import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Martin Rumanek
 * @version Aug 27, 2012
 */
public class ConvertImages extends ProgressJob
        implements InterruptableJob {

    /**
     * The logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ConvertImages.class.getPackage().toString());
    private Injector guice = null;
    private boolean continueWithNext = true;
    private int percentDone = 0;
    private static RequestDispatcher erraiDispatcher;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {;
        this.setJobKey(context.getJobDetail().getKey());
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        guice = (Injector) dataMap.get("Injector");
        EditorConfiguration configuration = guice.getInstance(EditorConfiguration.class);
        ImageResolverDAO imageResolverDAO = guice.getInstance(ImageResolverDAO.class);

        String model = dataMap.getString("model");
        String code = dataMap.getString("code");

        ScanFolderImpl.ScanFolderFactory scanFolderFactory = guice.getInstance(ScanFolderImpl.ScanFolderFactory.class);
        ScanFolder scanFolder = scanFolderFactory.create(model, code);
        final List<String> fileNames = scanFolder.getFileNames();

        Collections.sort(fileNames);
        ArrayList<String> resolvedIdentifiers = null;
        try {
            resolvedIdentifiers = imageResolverDAO.resolveItems(fileNames);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }


        //TODO-MR need refactoring! (http://jdem.cz/yeqp2)
        ArrayList<ImageItem> toAdd = new ArrayList<ImageItem>();
        ArrayList<ImageItem> result = new ArrayList<ImageItem>(fileNames.size());
        for (int i = 0; i < resolvedIdentifiers.size(); i++) {
            //get mimetype from extension (for audio)
            int position = fileNames.get(i).lastIndexOf('.');
            String extension = null;
            if (position > 0) {
                extension = fileNames.get(i).substring(position);
            }
            Constants.AUDIO_MIMETYPES audioMimeType = Constants.AUDIO_MIMETYPES.findByExtension(extension);

            String newIdentifier = null;
            String resolvedIdentifier = resolvedIdentifiers.get(i);
            if (resolvedIdentifier == null) {
                StringBuffer sb = new StringBuffer();
                sb.append(model).append('#').append(code).append('#').append(i);
                newIdentifier = UUID.nameUUIDFromBytes(sb.toString().getBytes()).toString();
                sb = new StringBuffer();
                sb.append(configuration.getImagesPath()).append(newIdentifier)
                        .append(Constants.JPEG_2000_EXTENSION);
                resolvedIdentifier = sb.toString();

                ImageItem item = new ImageItem(newIdentifier, resolvedIdentifier, fileNames.get(i));
                if (!audioMimeType.equals(Constants.AUDIO_MIMETYPES.UNKOWN_MIMETYPE)) {
                    item.setMimeType(audioMimeType.getMimeType());
                    sb = new StringBuffer();
                    sb.append(configuration.getImagesPath()).append(newIdentifier)
                            .append(Constants.AUDIO_MIMETYPES.WAV_MIMETYPE.getExtension());
                    item.setJpeg2000FsPath(sb.toString());
                }

                toAdd.add(item);
            }
            String uuid =
                    newIdentifier != null ? newIdentifier : resolvedIdentifier
                            .substring(resolvedIdentifier.lastIndexOf('/') + 1,
                                    resolvedIdentifier.lastIndexOf('.'));
            ImageItem item = new ImageItem(uuid, resolvedIdentifier, fileNames.get(i));

            String name = FilenameUtils.getBaseName(fileNames.get(i));
            String[] splits = name.split("-");

            /** audio files - special name convection */
            if (splits.length == 4 && "DS".equals(splits[0])) {
                item.setName(splits[2] + "-" + splits[3]);
            }
            if (splits.length == 5 && "MC".equals(splits[0])) {
                item.setName(splits[2] + "-" + splits[3] + "-" + splits[4]);
            }

            result.add(item);
        }
        if (!toAdd.isEmpty()) {
            try {
                imageResolverDAO.insertItems(toAdd);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            convert(toAdd);
        }
    }

    private void convert(List<ImageItem> toAdd) {
        LOGGER.debug("Converting start (from Quartz)");
        int converted = 0;
        if (toAdd != null && !toAdd.isEmpty()) {
            for (ImageItem item : toAdd) {
                if (!continueWithNext) break;
                convertItem(item);
                converted++;
                this.setPercentDone((int)(((float)converted / toAdd.size()) * 100));
            }

        }

    }

    private void convertItem(ImageItem item) {
        ConvertToJPEG2000Action action = new ConvertToJPEG2000Action(item);
        ConvertToJPEG2000Handler handler = guice.getInstance(ConvertToJPEG2000Handler.class);
        try {
            handler.execute(action, null);
        } catch (ActionException e) {
            LOGGER.error(e.getMessage());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        continueWithNext = false;
    }

}
