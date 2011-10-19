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

package cz.fi.muni.xkremser.editor.server;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DownloadFoxmlServlet
        extends HttpServlet {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(DownloadFoxmlServlet.class.getPackage().toString());

    /** The configuration. */
    @Inject
    private EditorConfiguration config;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        String uuid = null;
        String datastream = null;
        if (req.getRequestURI().contains(Constants.SERVLET_DOWNLOAD_FOXML_PREFIX)) {
            uuid =
                    req.getRequestURI().substring(req.getRequestURI()
                            .indexOf(Constants.SERVLET_DOWNLOAD_FOXML_PREFIX)
                            + Constants.SERVLET_DOWNLOAD_FOXML_PREFIX.length() + 1);
            resp.addHeader("Content-Disposition", "attachment; ContentType = \"text/XML\"; filename=\""
                    + uuid + ".foxml\"");
        } else {

            datastream = req.getRequestURI().split("/")[3];
            uuid =
                    req.getRequestURI().substring(req.getRequestURI().indexOf(datastream)
                            + datastream.length() + 1);
            resp.addHeader("Content-Disposition", "attachment; ContentType = \"text/XML\"; filename=\""
                    + uuid + "_datastream_" + datastream + ".xml\"");
        }

        ServletOutputStream os = resp.getOutputStream();
        if (uuid != null && !"".equals(uuid)) {

            try {
                StringBuffer sb = new StringBuffer();

                if (req.getRequestURI().contains(Constants.SERVLET_DOWNLOAD_FOXML_PREFIX)) {
                    sb.append(config.getFedoraHost()).append("/objects/").append(uuid).append("/objectXML");
                } else {
                    sb.append(config.getFedoraHost()).append("/objects/").append(uuid)
                            .append("/datastreams/").append(datastream).append("/content");
                }
                InputStream is =
                        RESTHelper.get(sb.toString(),
                                       config.getFedoraLogin(),
                                       config.getFedoraPassword(),
                                       false);
                if (is == null) {
                    return;
                }
                try {
                    IOUtils.copyStreams(is, os);
                } catch (IOException e) {
                    resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                    LOGGER.error("Problem with downloading foxml.", e);
                } finally {
                    os.flush();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                            LOGGER.error("Problem with downloading foxml.", e);
                        } finally {
                            is = null;
                        }
                    }
                }
            } catch (IOException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Problem with downloading foxml.", e);
            } finally {
                os.flush();
            }
        }
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
