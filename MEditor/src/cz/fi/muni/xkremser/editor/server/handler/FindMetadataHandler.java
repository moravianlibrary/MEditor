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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

import cz.fi.muni.xkremser.editor.server.OAIPMHClient;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.Z3950Client;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataResult;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
public class FindMetadataHandler
        implements ActionHandler<FindMetadataAction, FindMetadataResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(FindMetadataHandler.class.getPackage().toString());

    /** The configuration. */
    private final EditorConfiguration configuration;

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    private final Z3950Client z39Client;

    private final OAIPMHClient oaiClient;

    /**
     * Instantiates a new put recently modified handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public FindMetadataHandler(final EditorConfiguration configuration,
                               Z3950Client z39Client,
                               OAIPMHClient oaiClient) {
        this.configuration = configuration;
        this.z39Client = z39Client;
        this.oaiClient = oaiClient;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public FindMetadataResult execute(final FindMetadataAction action, final ExecutionContext context)
            throws ActionException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing action: FindMetadataAction: for id (" + action.getSearchType() + ") "
                    + action.getId());
        }
        ServerUtils.checkExpiredSession(httpSessionProvider);
        Map<DublinCore, ModsCollectionClient> documents = null;
        if (action.getSearchType() == null) {
            documents = oaiClient.search(action.getId());
        } else {
            documents = z39Client.search(action.getSearchType(), action.getId());
        }
        List<DublinCore> dc = new ArrayList<DublinCore>(documents.size());
        List<ModsCollectionClient> mods = new ArrayList<ModsCollectionClient>(documents.size());
        if (documents != null) {
            for (DublinCore dcFoo : documents.keySet()) {
                dc.add(dcFoo);
                mods.add(documents.get(dcFoo));
            }
        }
        return new FindMetadataResult(dc, mods);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<FindMetadataAction> getActionType() {
        return FindMetadataAction.class;
    }

    @Override
    public void undo(FindMetadataAction action, FindMetadataResult result, ExecutionContext context)
            throws ActionException {
        // TODO Auto-generated method stub

    }
}