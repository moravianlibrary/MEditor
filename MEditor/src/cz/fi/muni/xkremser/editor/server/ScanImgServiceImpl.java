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

package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanImgServiceImpl.
 */
public class ScanImgServiceImpl
        extends HttpServlet {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ScanImgServiceImpl.class);

    private static final String DJATOKA_URL =
            "/djatoka/resolver?url_ver=Z39.88-2004&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.level=2&svc.scale="
                    + Constants.IMAGE_THUMBNAIL_HEIGHT + "&rft_id=";

    private static final String DJATOKA_URL_FULL_IMG =
            "/djatoka/resolver?url_ver=Z39.88-2004&svc_id=info:lanl-repo/svc/getRegion&svc_val_fmt=info:ofi/fmt:kev:mtx:jpeg2000&svc.level=5&svc.scale="
                    + Constants.IMAGE_FULL_WIDTH + "&rft_id=";

    /** The config. */
    @Inject
    private EditorConfiguration config;

    private boolean baseOk;

    private String base;

    /*
     * (non-Javadoc)
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        resp.setDateHeader("Last Fetched", System.currentTimeMillis());
        //         resp.setDateHeader("Expires", instance.getTime().getTime());
        boolean full = ClientUtils.toBoolean(req.getParameter("full"));

        String uuid =
                req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_SCANS_PREFIX)
                        + Constants.SERVLET_SCANS_PREFIX.length() + 1);

        StringBuffer sb = new StringBuffer();
        sb.append("http");
        if (req.getProtocol().toLowerCase().contains("https")) {
            sb.append('s');
        }
        sb.append("://").append(URLS.LOCALHOST() ? "editor.mzk.cz" : req.getServerName())
                .append(full ? DJATOKA_URL_FULL_IMG : DJATOKA_URL).append(uuid);
        resp.setContentType("image/jpeg");
        resp.sendRedirect(resp.encodeRedirectURL(sb.toString()));
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        Injector injector = getInjector();
        injector.injectMembers(this);
        base = config.getScanInputQueuePath();
        baseOk = base != null && !"".equals(base);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Injector injector = getInjector();
        injector.injectMembers(this);
    }

    /**
     * Gets the injector.
     * 
     * @return the injector
     */
    protected Injector getInjector() {
        return (Injector) getServletContext().getAttribute(Injector.class.getName());
    }

}
