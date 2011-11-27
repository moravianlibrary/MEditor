/*
 * Metadata Editor
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
import cz.fi.muni.xkremser.editor.server.DAO.LocksDAO;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.shared.rpc.action.UnlockDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.UnlockDigitalObjectResult;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class UnlockDigitalObjectHandler
        implements ActionHandler<UnlockDigitalObjectAction, UnlockDigitalObjectResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(UnlockDigitalObjectHandler.class.getPackage()
            .toString());

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The locks DAO **/
    @Inject
    private LocksDAO locksDAO;

    /** Instantiate a new unlock digital object handler **/
    @Inject
    public UnlockDigitalObjectHandler() {
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public UnlockDigitalObjectResult execute(UnlockDigitalObjectAction action, ExecutionContext context)
            throws ActionException {

        String uuid = action.getUuid();
        LOGGER.debug("Processing action: UnlockDigitalObject: " + action.getUuid());

        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        try {
            return new UnlockDigitalObjectResult(locksDAO.unlockDigitalObject(uuid));
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<UnlockDigitalObjectAction> getActionType() {
        return UnlockDigitalObjectAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(UnlockDigitalObjectAction arg0, UnlockDigitalObjectResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
