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

package jobs;

import java.util.List;

import com.google.inject.Injector;

import org.apache.log4j.Logger;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import cz.mzk.editor.server.handler.ConvertToJPEG2000Handler;
import cz.mzk.editor.server.handler.ScanFolderHandler;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderResult;

/**
 * @author Martin Rumanek
 * @version Aug 27, 2012
 */
public class ConvertImages
        implements InterruptableJob {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ConvertImages.class.getPackage().toString());

    private Injector guice = null;

    private boolean continueWithNext = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        guice = (Injector) dataMap.get("Injector");

        String model = dataMap.getString("model");
        String code = dataMap.getString("code");
        ScanFolderAction action = new ScanFolderAction(model, code, null);
        ScanFolderHandler scanFolderHandler = guice.getInstance(ScanFolderHandler.class);
        try {
            ScanFolderResult result = scanFolderHandler.execute(action, null);
            convert(result);
        } catch (Exception e) {

            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private void convert(ScanFolderResult result) {
        LOGGER.debug("convert start");
        final List<ImageItem> itemList = result == null ? null : result.getItems();
        final List<ImageItem> toAdd = result == null ? null : result.getToAdd();
        if (toAdd != null && !toAdd.isEmpty()) {
            for (ImageItem item : toAdd) {
                if (!continueWithNext) break;
                convertItem(item, itemList);
            }
        } else {
            //doTheRest(itemList); TODO?
        }
    }

    private void convertItem(ImageItem item, final List<ImageItem> itemList) {
        ConvertToJPEG2000Action action = new ConvertToJPEG2000Action(item);
        ConvertToJPEG2000Handler handler = guice.getInstance(ConvertToJPEG2000Handler.class);
        try {
            handler.execute(action, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
            e.printStackTrace();
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
