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

import java.io.IOException;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.action.GetDOModelAction;
import cz.mzk.editor.shared.rpc.action.GetDOModelResult;

/**
 * @author Jiri Kremser
 * @version 13.11.2011
 */
public class GetDOModelHandler
        implements ActionHandler<GetDOModelAction, GetDOModelResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetDOModelHandler.class.getPackage().toString());

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetDOModelResult execute(final GetDOModelAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetDOModelAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        // parse input
        String uuid = action.getUuid();
        return new GetDOModelResult(getModel(uuid));
    }

    public DigitalObjectModel getModel(String uuid) {
        try {
            DigitalObjectModel model = FedoraUtils.getModel(uuid);
            return model;
        } catch (IOException e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetDOModelAction> getActionType() {
        return GetDOModelAction.class;
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
    public void undo(GetDOModelAction action, GetDOModelResult result, ExecutionContext context)
            throws ActionException {
        // idempotency -> no need for undo
    }
}