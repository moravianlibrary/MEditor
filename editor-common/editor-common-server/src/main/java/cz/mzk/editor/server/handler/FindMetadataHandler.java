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

import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import org.apache.log4j.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.server.OAIPMHClient;
import cz.mzk.editor.server.Z3950Client;
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
        ServerUtils.checkExpiredSession(httpSessionProvider.get());
        ArrayList<MetadataBundle> bundle = null;
        ArrayList<MetadataBundle> enrichedBundle = null;
        boolean isOai = action.isOai();

        String sys = action.getId();
        if (isOai && sys != null && sys.length() == 10) {
            sys = null;
            sys = findSysno(action.getId());
            if (sys == null || "".equals(sys)) isOai = false;
        }

        if (isOai) {
            String completeQuery = String.format(action.getOaiQuery(), sys);
            bundle = oaiClient.search(completeQuery, action.getBase());

        } else {
            bundle = z39Client.search(action.getSearchType(), action.getId());
            if (ClientUtils.toBoolean(configuration.getVsup())) {
                return new FindMetadataResult(bundle);
            }
            // co kdyz to je v Z39.50 a neni to v oai
            enrichedBundle = new ArrayList<MetadataBundle>(bundle.size());
            for (MetadataBundle bun : bundle) {
                String completeQuery = String.format(action.getOaiQuery(), bun.getMarc().getSysno());
                ArrayList<MetadataBundle> foo = oaiClient.search(completeQuery, action.getBase());
                if (!foo.isEmpty()) {
                    enrichedBundle.add(foo.get(0));
                }
            }
        }
        return new FindMetadataResult(action.isOai() ? bundle : enrichedBundle);
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