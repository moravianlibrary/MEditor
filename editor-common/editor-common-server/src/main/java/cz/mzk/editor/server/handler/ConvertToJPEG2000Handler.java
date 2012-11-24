/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

package cz.mzk.editor.server.handler;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;

import javax.inject.Inject;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.config.EditorConfigurationImpl;
import cz.mzk.editor.server.convert.Converter;
import cz.mzk.editor.server.util.IOUtils;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.ImageItem;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Result;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanFolderHandler.
 */
public class ConvertToJPEG2000Handler
        implements ActionHandler<ConvertToJPEG2000Action, ConvertToJPEG2000Result> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ConvertToJPEG2000Handler.class.getPackage()
            .toString());

    private static final Object LOCK = ConvertToJPEG2000Handler.class;

    /** The configuration. */
    private final EditorConfiguration configuration;

    private String djatokaHome = null;

    /**
     * Instantiates a new scan input queue handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public ConvertToJPEG2000Handler(final EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public ConvertToJPEG2000Result execute(final ConvertToJPEG2000Action action,
                                           final ExecutionContext context) throws ActionException {
        // parse input
        final ImageItem item = action.getItem();
        LOGGER.debug("Processing action: ConvertToJPEG2000Action " + action.getItem());

        if (context != null) {
            ServerUtils.checkExpiredSession();
        }

        if (configuration.getAkkaOn()) {
            Converter converter = Converter.getInstance();
            boolean success = converter.convert(item.getJpgFsPath(), item.getJpeg2000FsPath());
            if (!success) {
                convertToJpeg2000(item);
            }
        } else {
            convertToJpeg2000(item);
        }
        return new ConvertToJPEG2000Result();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<ConvertToJPEG2000Action> getActionType() {
        return ConvertToJPEG2000Action.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(ConvertToJPEG2000Action action, ConvertToJPEG2000Result result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }

    private boolean convertToJpeg2000(ImageItem item) throws ActionException {
        File f = new File(item.getJpeg2000FsPath());
        if (f.exists() && f.length() > 0) {
            return true;
        }
        if (item.getJpgFsPath().toLowerCase().endsWith(Constants.JPEG_2000_EXTENSION)) {
            try {
                IOUtils.copyFile(item.getJpgFsPath(), item.getJpeg2000FsPath());
                LOGGER.info("image " + configuration.getImagesPath() + item.getJpgFsPath()
                        + Constants.JPEG_2000_EXTENSION + "  was copied to  " + item.getJpeg2000FsPath()
                        + Constants.JPEG_2000_EXTENSION);
            } catch (IOException e) {
                LOGGER.error("Unable to copy image " + configuration.getImagesPath() + item.getJpgFsPath()
                        + Constants.JPEG_2000_EXTENSION + " to  " + item.getJpeg2000FsPath()
                        + Constants.JPEG_2000_EXTENSION, e);
                e.printStackTrace();
                return false;
            }
            return true;
        }
        StringBuffer sb;
        if (djatokaHome == null) {
            djatokaHome = configuration.getDjatokaHome();
            if (djatokaHome == null) {
                sb = new StringBuffer();
                sb.append(EditorConfigurationImpl.WORKING_DIR).append(File.separator).append("djatoka");
                djatokaHome = sb.toString();
            }
        }
        sb = new StringBuffer();
        sb.append(djatokaHome).append(File.separator).append("bin").append(File.separator)
                .append("compress.").append(((ServerUtils.isUnix() || ServerUtils.isMac()) ? "sh " : "bat "))
                .append(djatokaHome).append(" ");

        StringBuffer command = new StringBuffer();
        command.append(sb.toString()).append(item.getJpgFsPath()).append(" ")
                .append(item.getJpeg2000FsPath());
        Process p;
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Converting " + item.getJpgFsPath() + " into " + item.getJpeg2000FsPath());
            }
            synchronized (LOCK) {
                p = Runtime.getRuntime().exec(command.toString());
            }

            if (processTimeout(p, 100, 20000)) {
                int exitValue = p.exitValue();
                p.getInputStream().close();
                p.getOutputStream().close();
                if (exitValue != 0) {
                    LOGGER.warn("Converting " + item.getJpgFsPath() + " into " + item.getJpeg2000FsPath()
                            + " returns non-zero exitValue: " + exitValue + " with error output: "
                            + IOUtils.readAsString(p.getErrorStream(), Charset.defaultCharset(), true));
                    p.getErrorStream().close();
                    return false;
                } else {
                    p.getErrorStream().close();
                    return true;
                }
            } else {
                LOGGER.warn("Converting " + item.getJpgFsPath() + " into " + item.getJpeg2000FsPath()
                        + " took very long time.");
                return false;
            }

        } catch (IOException e) {
            throw new ActionException(e);
        }
    }

    private static synchronized boolean processTimeout(Process process, long interval, long timeout)
            throws ActionException {
        long time_waiting = 0;
        boolean process_finished = false;

        while (time_waiting < timeout && !process_finished) {
            process_finished = true;
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                throw new ActionException(e);
            }

            try {
                process.exitValue();
            } catch (IllegalThreadStateException e) {
                // process hasn't finished yet
                process_finished = false;
            }
            time_waiting += interval;
        }

        if (process_finished) {
            return true;
        } else {
            process.destroy();
            return false;
        }
    }
}