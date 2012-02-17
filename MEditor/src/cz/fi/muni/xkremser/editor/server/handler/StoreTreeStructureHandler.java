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

import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.TreeStructureDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class StoreTreeStructureHandler
        implements ActionHandler<StoreTreeStructureAction, StoreTreeStructureResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(StoreTreeStructureHandler.class.getPackage()
            .toString());

    @Inject
    private TreeStructureDAO treeDAO;

    @Inject
    private UserDAO userDAO;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    public StoreTreeStructureHandler() {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public StoreTreeStructureResult execute(final StoreTreeStructureAction action,
                                            final ExecutionContext context) throws ActionException {
        switch (action.getVerb()) {
            case PUT:
                if (action.getBundle() == null || action.getBundle().getInfo() == null
                        || action.getBundle().getNodes() == null) {
                    throw new NullPointerException("bundle");
                }
                break;
            case DELETE:
                if (action.getId() == null) {
                    throw new NullPointerException("id");
                }
                break;
            case GET:
                if (action.getId() == null && action.isAll()) {
                    throw new NullPointerException("id");
                }
                break;
            default:
                throw new IllegalArgumentException("bad verb");

        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing action: StoreTreeStructureResult role:" + action);
        }
        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);
        long userId = 0;
        try {
            userId = userDAO.getUsersId(String.valueOf(session.getAttribute(HttpCookies.SESSION_ID_KEY)));
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
        try {
            switch (action.getVerb()) {
                case PUT:

                    DateFormat dateFormatter =
                            DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("cs", "CZ"));
                    action.getBundle().getInfo().setCreated(dateFormatter.format(new Date()));
                    treeDAO.saveStructure(userId, action.getBundle().getInfo(), action.getBundle().getNodes());
                    return new StoreTreeStructureResult(null, null);
                case GET:
                    if (action.isAll()) {
                        // for all users
                        return new StoreTreeStructureResult(treeDAO.getAllSavedStructures(), null);
                    } else if (action.getId() == null) {
                        // for all objects of particular user
                        return new StoreTreeStructureResult(treeDAO.getAllSavedStructuresOfUser(userId), null);
                    } else if (action.getBundle() == null) {
                        // for user's objects of particular user
                        return new StoreTreeStructureResult(treeDAO.getSavedStructuresOfUser(userId,
                                                                                             action.getId()),
                                                            null);
                    } else {
                        // tree nodes
                        return new StoreTreeStructureResult(null, treeDAO.loadStructure(Long.parseLong(action
                                .getId())));
                    }
                case DELETE:
                    treeDAO.removeSavedStructure(Long.parseLong(action.getId()));
                    break;

            }
        } catch (DatabaseException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new ActionException(e.getMessage(), e);
        }
        return new StoreTreeStructureResult(null, null);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<StoreTreeStructureAction> getActionType() {
        return StoreTreeStructureAction.class;
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
    public void undo(StoreTreeStructureAction action,
                     StoreTreeStructureResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }
}