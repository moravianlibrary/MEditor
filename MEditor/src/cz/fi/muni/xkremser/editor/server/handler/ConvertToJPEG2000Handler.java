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

package cz.fi.muni.xkremser.editor.server.handler;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.config.EditorConfigurationImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.ImageItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ConvertToJPEG2000Result;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanFolderHandler.
 */
public class ConvertToJPEG2000Handler
        implements ActionHandler<ConvertToJPEG2000Action, ConvertToJPEG2000Result> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ConvertToJPEG2000Handler.class.getPackage()
            .toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

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
        ServerUtils.checkExpiredSession(httpSessionProvider);
        convertToJpeg2000(item);
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

    private void convertToJpeg2000(ImageItem item) throws ActionException {
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
            p = Runtime.getRuntime().exec(command.toString());
            p.waitFor();
        } catch (IOException e) {
            throw new ActionException(e);
        } catch (InterruptedException e) {
            throw new ActionException(e);
        }
    }

}