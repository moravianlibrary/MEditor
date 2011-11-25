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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;

import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.newObject.CreateObjectUtils;

import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.InsertNewDigitalObjectResult;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
public class InsertNewDigitalObjectHandler
        implements ActionHandler<InsertNewDigitalObjectAction, InsertNewDigitalObjectResult> {

    private static final Logger LOGGER = Logger.getLogger(InsertNewDigitalObjectHandler.class);

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public InsertNewDigitalObjectResult execute(InsertNewDigitalObjectAction action, ExecutionContext context)
            throws ActionException {
        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);
        NewDigitalObject object = action.getObject();
        if (object == null) throw new NullPointerException("object");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.debug("Inserting digital object: " + object.getName());
        }

        boolean success;
        try {
            success = CreateObjectUtils.insertAllTheStructureToFOXMLs(object);
        } catch (CreateObjectException e) {
            throw new ActionException(e.getMessage());
        }
        return new InsertNewDigitalObjectResult(success);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<InsertNewDigitalObjectAction> getActionType() {
        return InsertNewDigitalObjectAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(InsertNewDigitalObjectAction action,
                     InsertNewDigitalObjectResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
    }

}
