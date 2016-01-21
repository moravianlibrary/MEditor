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

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.metadataDownloader.XServicesClient;
import cz.mzk.editor.server.util.StringUtils;
import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.metadataDownloader.OAIPMHClient;
import cz.mzk.editor.server.metadataDownloader.Z3950Client;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.fedora.utils.Dom4jUtils;
import cz.mzk.editor.server.util.RESTHelper;
import cz.mzk.editor.server.util.ServerUtils;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import cz.mzk.editor.shared.rpc.action.FindMetadataAction;
import cz.mzk.editor.shared.rpc.action.FindMetadataResult;

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

    private final Z3950Client z39Client;

    private final OAIPMHClient oaiClient;

    private final XServicesClient xServicesClient;

    /**
     * Instantiates a new put recently modified handler.
     * 
     * @param configuration
     *        the configuration
     */
    @Inject
    public FindMetadataHandler(final EditorConfiguration configuration,
                               Z3950Client z39Client,
                               OAIPMHClient oaiClient,
                               XServicesClient xServicesClient) {
        this.configuration = configuration;
        this.z39Client = z39Client;
        this.oaiClient = oaiClient;
        this.xServicesClient = xServicesClient;
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
        ServerUtils.checkExpiredSession();

        if (!ServerUtils.checkUserRightOrAll(EDITOR_RIGHTS.FIND_METADATA)) {
            LOGGER.warn("Bad authorization in " + this.getClass().toString());
            throw new ActionException("Bad authorization in " + this.getClass().toString());
        }

        ArrayList<MetadataBundle> bundle = null;
        ArrayList<MetadataBundle> enrichedBundle = null;
        boolean isOai = action.getMethod().equals(Constants.SEARCH_METHOD.OAI);
        boolean isZ39 = action.getMethod().equals(Constants.SEARCH_METHOD.Z39_50);

        String sys = action.getId();
        if (isOai && sys != null && sys.length() == 10) {
            sys = null;
            sys = findSysno(action.getId());
            if (sys == null || "".equals(sys)) isOai = false;
        }

        if (isOai) {
            Map<String, String> properties = new HashMap<String, String>(3);
            properties.put("url", action.getOaiUrl());
            properties.put("oaiprefix", action.getPrefix());
            properties.put("base", action.getBase());
            properties.put("sysno", sys);
            String completeQuery = StringUtils.doTheSubstitution(configuration.getOaiString(), properties);
            bundle = oaiClient.search(completeQuery, action.getBase());
        }
        if (isZ39) {
            bundle = z39Client.search(action.getSearchType(), action.getId());
            if (ClientUtils.toBoolean(configuration.getVsup())) {
                return new FindMetadataResult(bundle);
            }
            // co kdyz to je v Z39.50 a neni to v oai
            enrichedBundle = new ArrayList<MetadataBundle>(bundle.size());
            for (MetadataBundle bun : bundle) {
                Map<String, String> properties = new HashMap<String, String>(3);
                properties.put("url", action.getOaiUrl());
                properties.put("oaiprefix", action.getPrefix());
                properties.put("base", action.getBase());
                properties.put("sysno", bun.getMarc().getSysno());
                String completeQuery = StringUtils.doTheSubstitution(configuration.getOaiString(), properties);
                ArrayList<MetadataBundle> foo = oaiClient.search(completeQuery, action.getBase());
                if (!foo.isEmpty()) {
                    enrichedBundle.add(foo.get(0));
                }
            }
        }

        if (action.getMethod().equals(Constants.SEARCH_METHOD.X_SERVICES)) {
            enrichedBundle = xServicesClient.search(action.getId(), action.getBase());
        }



        return new FindMetadataResult(action.getMethod().equals(Constants.SEARCH_METHOD.OAI) ? bundle : enrichedBundle);
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

    public String findSysno(String barcode) {

        String alephUrl =
                EditorConfiguration.ServerConstants.Z3950_DEFAULT_HOSTS[z39Client.getProfileIndex()];

        if (alephUrl == null || "".equals(alephUrl)) return null;

        if (!alephUrl.startsWith("http")) {
            alephUrl = "http://" + alephUrl;
        }

        String urlToSetNum = alephUrl + "/X?op=find&code=BAR&request=%s&base=";
        String urlToSysno = alephUrl + "/X?op=present&set_entry=1&set_number=";

        String completeUrlToSetNum = String.format(urlToSetNum, barcode);
        String[] oaiBases = configuration.getOaiBases();
        String sysno = null;

        for (String base : oaiBases) {
            try {
                InputStream inputStream = RESTHelper.get(completeUrlToSetNum + base, null, null, false);
                Document records = Dom4jUtils.loadDocument(inputStream, true);

                XPath xpath = DocumentHelper.createXPath("/find/set_number");
                Node resultNode = xpath.selectSingleNode(records);

                if (resultNode != null) {
                    String setNumber = resultNode.getText();
                    InputStream sysnoStream = RESTHelper.get(urlToSysno + setNumber, null, null, false);
                    Document sysnoDoc = Dom4jUtils.loadDocument(sysnoStream, true);
                    xpath = DocumentHelper.createXPath("/present/record/doc_number");
                    Node sysnoNode = xpath.selectSingleNode(sysnoDoc);
                    if (sysnoNode != null) sysno = sysnoNode.getText();
                    break;
                }

            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            } catch (DocumentException e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }

        }

        return sysno;
    }
}