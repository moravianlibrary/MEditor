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

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.server.HttpCookies;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.DAO.RecentlyModifiedItemDAO;
import cz.fi.muni.xkremser.editor.server.DAO.UserDAO;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.LockInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLockInformationAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRecentlyModifiedResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetRecentlyModifiedHandler.
 */
public class GetRecentlyModifiedHandler
        implements ActionHandler<GetRecentlyModifiedAction, GetRecentlyModifiedResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetRecentlyModifiedHandler.class.getPackage()
            .toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The recently modified dao. */
    private final RecentlyModifiedItemDAO recentlyModifiedDAO;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The GetLockInformationHandler handler */
    private final GetLockInformationHandler getLockInformationHandler;

    /** The user DAO **/
    @Inject
    private UserDAO userDAO;

    /**
     * Instantiates a new gets the recently modified handler.
     * 
     * @param configuration
     *        the configuration
     * @param recentlyModifiedDAO
     *        the recently modified dao
     */
    @Inject
    public GetRecentlyModifiedHandler(final EditorConfiguration configuration,
                                      final RecentlyModifiedItemDAO recentlyModifiedDAO) {
        this.configuration = configuration;
        this.recentlyModifiedDAO = recentlyModifiedDAO;
        this.getLockInformationHandler = new GetLockInformationHandler();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public GetRecentlyModifiedResult execute(final GetRecentlyModifiedAction action,
                                             final ExecutionContext context) throws ActionException {
        LOGGER.debug("Processing action: GetRecentlyModified");

        HttpSession session = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(session);
        Injector injector = (Injector) session.getServletContext().getAttribute(Injector.class.getName());
        injector.injectMembers(getLockInformationHandler);

        String openID = (String) session.getAttribute(HttpCookies.SESSION_ID_KEY);

        try {

            ArrayList<RecentlyModifiedItem> recItems = null;
            if (action.isForAllUsers()) {
                recItems = recentlyModifiedDAO.getItems(configuration.getRecentlyModifiedNumber(), null);
            } else {
                recItems = recentlyModifiedDAO.getItems(configuration.getRecentlyModifiedNumber(), openID);
            }

            long userId = userDAO.getUsersId(openID);

            for (RecentlyModifiedItem item : recItems) {
                LockInfo lockInfo =
                        getLockInformationHandler.execute(new GetLockInformationAction(item.getUuid()),
                                                          context).getLockInfo();
                item.setLockInfo(lockInfo);
            }
            return new GetRecentlyModifiedResult(recItems);
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetRecentlyModifiedAction> getActionType() {
        return GetRecentlyModifiedAction.class;
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
    public void undo(GetRecentlyModifiedAction action,
                     GetRecentlyModifiedResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub

    }
}