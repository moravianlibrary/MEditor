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

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.action.PutUserInfoAction;
import cz.mzk.editor.shared.rpc.action.PutUserInfoResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class PutUserInfoHandler
        implements ActionHandler<PutUserInfoAction, PutUserInfoResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(PutUserInfoHandler.class.getPackage().toString());

    @Inject
    private UserDAO userDAO;

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public PutUserInfoResult execute(final PutUserInfoAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutUserInfoAction " + action.getUser().getId());
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.EDIT_USERS)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        if (action.getUser() == null) throw new NullPointerException("getUser()");

        Long id = new Long(-1);
        try {
            id = userDAO.insertUpdatetUser(action.getUser());
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

        return new PutUserInfoResult(id);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<PutUserInfoAction> getActionType() {
        return PutUserInfoAction.class;
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
    public void undo(PutUserInfoAction action, PutUserInfoResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}