/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.inject.Inject;

import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.ScanImgServiceImpl;
import cz.fi.muni.xkremser.editor.server.ServerUtils;
import cz.fi.muni.xkremser.editor.server.URLS;
import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

import cz.fi.muni.xkremser.editor.shared.rpc.action.GetFullImgHeightAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetFullImgHeightResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class GetFullImgHeightHandler
        implements ActionHandler<GetFullImgHeightAction, GetFullImgHeightResult> {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    /** The http session provider. */
    @Inject
    private Provider<HttpServletRequest> requestProvider;

    /** The configuration. */
    @Inject
    private EditorConfiguration config;

    /**
     * {@inheritDoc}
     */

    @Override
    public GetFullImgHeightResult execute(GetFullImgHeightAction action, ExecutionContext context)
            throws ActionException {

        HttpSession ses = httpSessionProvider.get();
        ServerUtils.checkExpiredSession(ses);

        HttpServletRequest httpServletRequest = requestProvider.get();

        StringBuffer baseUrl = new StringBuffer();
        baseUrl.append("http");
        if (httpServletRequest.getProtocol().toLowerCase().contains("https")) {
            baseUrl.append('s');
        }
        baseUrl.append("://");
        if (!URLS.LOCALHOST()) {
            baseUrl.append(httpServletRequest.getServerName());
        } else {
            String hostname = config.getHostname();
            if (hostname.contains("://")) {
                baseUrl.append(hostname.substring(hostname.indexOf("://") + "://".length()));
            } else {
                baseUrl.append(hostname);
            }
        }
        String metadata = null;
        try {
            metadata =
                    RESTHelper
                            .convertStreamToString(RESTHelper.get(baseUrl
                                                                          + ScanImgServiceImpl.DJATOKA_URL_GET_METADATA
                                                                          + action.getUuid(),
                                                                  null,
                                                                  null,
                                                                  true));
        } catch (IOException e) {
            throw new ActionException(e);
        }
        Integer height =
                Integer.valueOf(metadata.substring(metadata.indexOf("ght\": \"") + 7,
                                                   metadata.indexOf("\",\n\"dw")));
        return new GetFullImgHeightResult(height);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Class<GetFullImgHeightAction> getActionType() {
        return GetFullImgHeightAction.class;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void undo(GetFullImgHeightAction arg0, GetFullImgHeightResult arg1, ExecutionContext arg2)
            throws ActionException {
        // TODO Auto-generated method stub
    }

}
