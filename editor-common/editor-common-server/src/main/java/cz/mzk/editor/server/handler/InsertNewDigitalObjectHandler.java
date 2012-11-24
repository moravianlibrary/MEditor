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

package cz.mzk.editor.server.handler;

import java.sql.SQLException;

import javax.inject.Inject;

import com.google.inject.name.Named;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.CreateObjectException;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.newObject.CreateObject;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectResult;

/**
 * @author Jiri Kremser
 * @version 14.11.2011
 */
public class InsertNewDigitalObjectHandler
        implements ActionHandler<InsertNewDigitalObjectAction, InsertNewDigitalObjectResult> {

    private static final Logger LOGGER = Logger.getLogger(InsertNewDigitalObjectHandler.class);

    /** The fedora access. */
    @Inject
    @Named("securedFedoraAccess")
    private FedoraAccess fedoraAccess;

    /** The config. */
    @Inject
    private EditorConfiguration config;

    /** The dao utils. */
    @Inject
    private DigitalObjectDAO digitalObjectDAO;

    /** The dao utils. */
    @Inject
    private DAOUtils daoUtils;

    /** The image resolver dao. */
    @Inject
    private ImageResolverDAO imageResolverDAO;

    public InsertNewDigitalObjectHandler() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InsertNewDigitalObjectResult execute(InsertNewDigitalObjectAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: InsertNewDigitalObjectAction " + action.getObject().getUuid());
        ServerUtils.checkExpiredSession();

        NewDigitalObject object = action.getObject();
        if (object == null) throw new NullPointerException("object");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.debug("Inserting digital object: " + object.getName());
        }

        boolean ingestSuccess;
        boolean reindexSuccess;
        boolean deepZoomSuccess = true;
        String pid = null;

        try {
            daoUtils.checkInputQueue(action.getInputPath(), object.getName(), true);
        } catch (DatabaseException e1) {
            LOGGER.error(e1.getMessage());
            e1.printStackTrace();
        } catch (SQLException e1) {
            LOGGER.error(e1.getMessage());
            e1.printStackTrace();
        }

        try {
            CreateObject createObject =
                    new CreateObject(action.getInputPath(),
                                     config,
                                     digitalObjectDAO,
                                     imageResolverDAO,
                                     fedoraAccess);
            ingestSuccess = createObject.insertAllTheStructureToFOXMLs(object);

            if (createObject.getTopLevelUuid() != object.getUuid()) {

                try {
                    if (digitalObjectDAO.updateTopObjectUuid(createObject.getTopLevelUuid(),
                                                             object.getUuid(),
                                                             createObject.getIngestedObjects(),
                                                             object.getModel().getValue(),
                                                             object.getName(),
                                                             object.getPath())) {
                        digitalObjectDAO.deleteDigitalObject(createObject.getTopLevelUuid(),
                                                             null,
                                                             null,
                                                             createObject.getTopLevelUuid());
                    }
                } catch (DatabaseException e) {
                    LOGGER.error("DB ERROR!!!: " + e.getMessage() + ": " + e);
                    e.printStackTrace();
                }

            }

            if (object.getUuid() != null && object.getUuid().contains(Constants.FEDORA_UUID_PREFIX)) {
                pid = object.getUuid();
            } else {
                pid = Constants.FEDORA_UUID_PREFIX + object.getUuid();
            }

            reindexSuccess = ServerUtils.reindex(pid);

            if (config.getImageServerInternal()) {
                deepZoomSuccess = ServerUtils.generateDeepZoom(pid);
            }

        } catch (CreateObjectException e) {
            throw new ActionException(e.getMessage());
        }
        return new InsertNewDigitalObjectResult(ingestSuccess, reindexSuccess, deepZoomSuccess, pid);

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
