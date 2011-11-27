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

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserIdentityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class RemoveUserIdentityHandler
        implements ActionHandler<RemoveUserIdentityAction, RemoveUserIdentityResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveUserIdentityHandler.class.getPackage()
            .toString());

    /** The recently modified dao. */
    @Inject
    private UserDAO userDAO;

    /**
     * Instantiates a new put recently modified handler.
     */
    @Inject
    public RemoveUserIdentityHandler(final EditorConfiguration configuration) {

    }

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public RemoveUserIdentityResult execute(final RemoveUserIdentityAction action,
                                            final ExecutionContext context) throws ActionException {
        if (action.getId() == null) throw new NullPointerException("getId()");
        LOGGER.debug("Processing action: RemoveUserIdentityAction user id:" + action.getId());
        ServerUtils.checkExpiredSession(httpSessionProvider);

        try {
            userDAO.removeUserIdentity(Long.parseLong(action.getId()));
        } catch (NumberFormatException e) {
            throw new ActionException(e);
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
        return new RemoveUserIdentityResult();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<RemoveUserIdentityAction> getActionType() {
        return RemoveUserIdentityAction.class;
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
    public void undo(RemoveUserIdentityAction action,
                     RemoveUserIdentityResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }
}