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

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.CheckAvailability;
import cz.mzk.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.mzk.editor.shared.rpc.action.CheckAvailabilityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class CheckAvailabilityHandler
        implements ActionHandler<CheckAvailabilityAction, CheckAvailabilityResult> {

    private static String SOME_STATIC_KRAMERIUS_PAGE = "/inc/home/info.jsp";

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(CheckAvailabilityHandler.class.getPackage()
            .toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /**
     * Instantiates a new put recently modified handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public CheckAvailabilityHandler(final EditorConfiguration configuration) {
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
    public CheckAvailabilityResult execute(final CheckAvailabilityAction action,
                                           final ExecutionContext context) throws ActionException {
        if (LOGGER.isDebugEnabled()) {
            String serverName = null;
            if (action.getServerId() == CheckAvailability.FEDORA_ID) {
                serverName = "fedora";
            } else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
                serverName = "kramerius";
            }
            LOGGER.debug("Processing action: CheckAvailability: " + serverName);
        }
        ServerUtils.checkExpiredSession();

        String url = null;
        String usr = "";
        String pass = "";
        if (action.getServerId() == CheckAvailability.FEDORA_ID) {
            url = configuration.getFedoraHost();
            usr = configuration.getFedoraLogin();
            pass = configuration.getFedoraPassword();
        } else if (action.getServerId() == CheckAvailability.KRAMERIUS_ID) {
            url = configuration.getKrameriusHost() + SOME_STATIC_KRAMERIUS_PAGE;
        } else {
            throw new ActionException("Unknown server id");
        }

        return new CheckAvailabilityResult(ServerUtils.checkAvailability(url, usr, pass), url);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<CheckAvailabilityAction> getActionType() {
        return CheckAvailabilityAction.class;
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
    public void undo(CheckAvailabilityAction action, CheckAvailabilityResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}