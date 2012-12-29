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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Date;

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Injector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.ConnectionException;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DescriptionDAO;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.fedora.utils.FedoraUtils;
import cz.mzk.editor.server.modelHandler.FedoraDigitalObjectHandler;
import cz.mzk.editor.server.modelHandler.StoredDigitalObjectHandler;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.LockInfo;
import cz.mzk.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.mzk.editor.shared.rpc.action.GetLockInformationAction;

// TODO: Auto-generated Javadoc
/**
 * The Class GetDigitalObjectDetailHandler.
 */
public class GetDigitalObjectDetailHandler
        implements ActionHandler<GetDigitalObjectDetailAction, GetDigitalObjectDetailResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(GetDigitalObjectDetailHandler.class.getPackage()
            .toString());

    /** The Fedora digitalObject handler. */
    private final FedoraDigitalObjectHandler fedoraObjectHandler;

    /** The Stored digitalObject handler. */
    private final StoredDigitalObjectHandler storedObjectHandler;

    /** The GetDescriptionHandler handler. */
    private final GetDescriptionHandler descritptionHandler;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The GetLockInformationHandler handler */
    private final GetLockInformationHandler getLockInformationHandler;

    /** The description dao. */
    @Inject
    private DescriptionDAO descriptionDAO;

    @Inject
    private DigitalObjectDAO digObjDAO;

    /**
     * Instantiates a new gets the digital object detail handler.
     * 
     * @param handler
     *        the handler
     */
    @Inject
    public GetDigitalObjectDetailHandler(final FedoraDigitalObjectHandler fedoraObjectHandler,
                                         final StoredDigitalObjectHandler storedObjectHandler) {
        this.fedoraObjectHandler = fedoraObjectHandler;
        this.storedObjectHandler = storedObjectHandler;
        this.descritptionHandler = new GetDescriptionHandler();
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
    public GetDigitalObjectDetailResult execute(final GetDigitalObjectDetailAction action,
                                                final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: GetDigitalObjectDetailAction " + action.getUuid());
        ServerUtils.checkExpiredSession();

        // parse input
        String uuid = action.getUuid();
        String storedFOXMLFilePath = null;
        if (action.getSavedEditedObject() != null && action.getSavedEditedObject().getFileName() != null
                && !action.getSavedEditedObject().getFileName().equals("")) {
            storedFOXMLFilePath = action.getSavedEditedObject().getFileName();
        }

        try {
            if (storedFOXMLFilePath == null) {
                LOGGER.debug("Processing action: GetDigitalObjectDetailAction: " + action.getUuid());
            } else {
                LOGGER.debug("Processing action: GetDigitalObjectDetailAction: " + action.getUuid()
                        + " from the file: " + storedFOXMLFilePath);
                if (!new File(storedFOXMLFilePath).exists() || FedoraUtils.getModel(uuid) == null)
                    return new GetDigitalObjectDetailResult(null, null, null);
            }

            HttpSession ses = httpSessionProvider.get();
            Injector injector = (Injector) ses.getServletContext().getAttribute(Injector.class.getName());
            injector.injectMembers(descritptionHandler);
            injector.injectMembers(getLockInformationHandler);

            DigitalObjectDetail obj = null;
            if (storedFOXMLFilePath == null) {
                if (action.getModel() == null) { // lazy
                    obj = fedoraObjectHandler.getDigitalObject(uuid);
                } else { // fetch uuids
                    obj = fedoraObjectHandler.getDigitalObjectItems(uuid, action.getModel());
                }
            } else {
                obj =
                        storedObjectHandler.getStoredDigitalObject(uuid,
                                                                   storedFOXMLFilePath,
                                                                   action.getModel());
                try {
                    digObjDAO.insertDOCrudAction(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION,
                                                 "saved_edited_object_id",
                                                 action.getSavedEditedObject().getId(),
                                                 CRUD_ACTION_TYPES.READ);
                } catch (DatabaseException e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                    throw new ActionException(e);
                }
            }

            //TODO storedFOXMLFilePath != null------------------------------------------------------
            //            GetDescriptionResult result =
            //                    descritptionHandler.execute(new GetDescriptionAction(uuid), context);
            //
            //            String description = result.getUserDescription();
            //            Date modified = result.getModified();
            //--------------------------------------------------------------------------------------
            String description = null;
            Date modified = null;
            try {
                description = descriptionDAO.getUserDescription(uuid);

                // TODO: is the given user authorized to this operation?
            } catch (DatabaseException e) {
                throw new ActionException(e);
            }
            LockInfo lockInfo =
                    getLockInformationHandler.execute(new GetLockInformationAction(uuid), context)
                            .getLockInfo();
            obj.setLockInfo(lockInfo);

            return new GetDigitalObjectDetailResult(obj,
                                                    description == null ? "" : description,
                                                    modified == null ? new Date() : modified);
        } catch (IOException e) {
            String msg = null;
            if (ServerUtils.isCausedByException(e, FileNotFoundException.class)) {
                msg = "Digital object with uuid " + uuid + " is not present in the repository. ";
            } else if (ServerUtils.isCausedByException(e, ConnectionException.class)) {
                msg = "Connection cannot be established. Please check whether Fedora is running. ";
            } else {
                msg = "Unable to obtain digital object with uuid " + uuid + ". ";
            }
            LOGGER.error(msg, e);
            throw new ActionException(msg, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<GetDigitalObjectDetailAction> getActionType() {
        return GetDigitalObjectDetailAction.class;
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
    public void undo(GetDigitalObjectDetailAction action,
                     GetDigitalObjectDetailResult result,
                     ExecutionContext context) throws ActionException {
        // idempotency -> no need for undo
    }
}