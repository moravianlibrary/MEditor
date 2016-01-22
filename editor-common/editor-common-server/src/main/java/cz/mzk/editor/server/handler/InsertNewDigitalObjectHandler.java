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

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cz.mzk.editor.server.util.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.errai.bus.client.api.base.MessageBuilder;


import com.google.inject.name.Named;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.mzk.editor.client.CreateObjectException;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.DigitalObjectDAO;
import cz.mzk.editor.server.DAO.ImageResolverDAO;
import cz.mzk.editor.server.DAO.InputQueueItemDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.FedoraAccess;
import cz.mzk.editor.server.newObject.CreateObject;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.NewDigitalObject;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectResult;
import org.jboss.errai.bus.client.api.messaging.RequestDispatcher;


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

    @Inject
    private InputQueueItemDAO inputQueueItemDAO;

    /** The image resolver dao. */
    @Inject
    private ImageResolverDAO imageResolverDAO;

    private static RequestDispatcher erraiDispatcher;

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

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.CREATE_NEW_OBJECTS)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        NewDigitalObject object = action.getObject();
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

            if (!createObject.getTopLevelUuid().equals(object.getUuid())) {
                try {
                    // chro chro
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
                    throw new ActionException(e);
                }
            } else {
        	doAfterIngest(action.getInputPath(), createObject);
            }

            if (object.getUuid() != null && object.getUuid().contains(Constants.FEDORA_UUID_PREFIX)) {
                pid = object.getUuid();
            } else {
                pid = Constants.FEDORA_UUID_PREFIX + object.getUuid();
            }
     
            // urn:nbn resolver
//            notifyResolver(object);
            
            // process post-ingest hooks
            processPostIngestHooks(object);

            if (action.isReindex()) {
                reindexSuccess = ServerUtils.reindex(pid);
            } else {
                reindexSuccess = true;
            }
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
        throw new ActionException("Undo is not supported on " + this.getClass().getSimpleName());
    }

    public static void setErraiDispatcher(RequestDispatcher erraiDispatcher) {
        InsertNewDigitalObjectHandler.erraiDispatcher = erraiDispatcher;
    }
    
    private void doAfterIngest(String inputPath, CreateObject createObject) throws ActionException {
        try {
            digitalObjectDAO.updateState(createObject.getIngestedObjects(), true);
            inputQueueItemDAO.setIngested(inputPath);
            if (erraiDispatcher != null) {
                MessageBuilder.createMessage()
                        .toSubject("InputQueueBroadcastReceiver").signalling()
                        .with("ingested", inputPath)
                        .noErrorHandling().sendNowWith(erraiDispatcher);
            }
        } catch (DatabaseException e) {
            LOGGER.error("DB ERROR!!!: " + e.getMessage() + ": " + e);
            e.printStackTrace();
            throw new ActionException(e);
        }
    }
    
    private void notifyResolver(NewDigitalObject object) {
	// ping urn:nbn resolver
//	Resolver resolver = new Resolver();
//	if (config.getResolverRegistrarCode() != null && !config.getResolverRegistrarCode().isEmpty()
//		&& config.getResolverUrl() != null && !config.getResolverUrl().isEmpty()) {
//	    resolver.resolve(config.getResolverUrl(), config.getResolverRegistrarCode(), object.getModel().getValue());
//	}
    }
    
/**
 * comma separated list of URLs where http get is sent after successful ingest
 * you may want to use following variables:
 *   ${pid} ${sysno} ${name}
 * NOTE: it is also possible to use substring extraction:
 * i.e. ${pid:5} is 'pid' without first 5 characters
 *      ${sysno:-8} is last 8 characters of 'sysno'
 *      ${name:2:4} is a substring of name starting by 2nd char and ending by 4th character
 *      ${pid::5} only first 5 characters
 *      ${pid::-5} pid without last 5 characters
 * postIngestHooks=http://192.168.0.25:8080/katalog/l.dll?bqkram2clav~clid=${sysno::8}&uuid=${pid:5}
 */
    private void processPostIngestHooks(NewDigitalObject object) {
	String[] urls = config.getPostIngestHooks();
	if (urls != null && urls.length > 0) {
	    for (String url : urls) {
		String readyToPingUrl = doTheSubstitution(url, object);
		try {
		    RESTHelper.get(readyToPingUrl, null, null, false);
		} catch (IOException e) {
		    LOGGER.error("Unable to send http get request to " + readyToPingUrl + "  reason: " + e.getMessage()
			    + ": " + e);
		    e.printStackTrace();
		}
	    }
	}
    }
    
    private static String doTheSubstitution(String url, NewDigitalObject object) {
        Map<String, String> properties = new HashMap<String, String>(3);
        properties.put("name", object.getName());
        properties.put("pid", object.getUuid());
        properties.put("sysno", object.getSysno());
        return StringUtils.doTheSubstitution(url, properties);
    }
}
