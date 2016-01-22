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

import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DescriptionDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.GetDescriptionAction;
import cz.mzk.editor.shared.rpc.action.GetDescriptionResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class GetDescriptionHandler
        implements ActionHandler<GetDescriptionAction, GetDescriptionResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger
            .getLogger(GetDescriptionHandler.class.getPackage().toString());

    /** The description dao. */
    @Inject
    private DescriptionDAO descriptionDAO;

    /**
     * Instantiates a new put recently modified handler.
     */
    @Inject
    public GetDescriptionHandler() {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetDescriptionResult execute(final GetDescriptionAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: GetDescriptionAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        if (action.getUuid() == null || "".equals(action.getUuid()))
            throw new NullPointerException("getUuid()");

        String commonDescription = null;
        String userDescription = null;
        try {
            userDescription = descriptionDAO.getUserDescription(action.getUuid());
            commonDescription = descriptionDAO.getCommonDescription(action.getUuid());

        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
        return new GetDescriptionResult(commonDescription, userDescription);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetDescriptionAction> getActionType() {
        return GetDescriptionAction.class;
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
    public void undo(GetDescriptionAction action, GetDescriptionResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}