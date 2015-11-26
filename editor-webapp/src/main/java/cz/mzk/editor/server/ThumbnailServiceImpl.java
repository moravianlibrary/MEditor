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

package cz.mzk.editor.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.inject.Inject;

import com.google.inject.Injector;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.FedoraCredentials;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ThumbnailServiceImpl.
 */
public class ThumbnailServiceImpl
        extends HttpServlet {

    private static final long serialVersionUID = 5031350967017622089L;
    /** The config. */
    @Inject
    private EditorConfiguration config;
    private static final Logger LOGGER = Logger.getLogger(ThumbnailServiceImpl.class);


    /*
     * (non-Javadoc)
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Cache-Control", "max-age=" + Constants.HTTP_CACHE_SECONDS);
        String uuid = req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_THUMBNAIL_PREFIX)
                        + Constants.SERVLET_THUMBNAIL_PREFIX.length() + 1);

        if (uuid != null && !"".equals(uuid)) {
            resp.setContentType("image/jpeg");
            InputStream is = null;
            if (!Constants.MISSING.equals(uuid)) {
                FedoraCredentials credentials = new FedoraCredentials(config.getFedoraHost(), config.getFedoraLogin(), config.getFedoraPassword());
                FedoraClient fc = new FedoraClient(credentials);
                try {
                    is = FedoraClient.getDatastreamDissemination(uuid, "IMG_THUMB").execute(fc).getEntityInputStream();
                } catch (FedoraClientException e) {
                    LOGGER.error(e.getMessage());
                }
            } else {
                is = new FileInputStream(new File("images/other/file_not_found.png"));
            }
            ServletOutputStream os = resp.getOutputStream();

            if (is != null) {
                IOUtils.copy(is, os);
                is.close();
            } else {
                LOGGER.error("Fedora client returned null InputStream");
                resp.setStatus(500);
            }
            resp.setStatus(200);
        } else {
            resp.setStatus(404);
        }
    }

    /**
     * Gets the config.
     * 
     * @return the config
     */
    public EditorConfiguration getConfig() {
        return config;
    }

    /**
     * Sets the config.
     * 
     * @param config
     *        the new config
     */
    public void setConfig(EditorConfiguration config) {
        this.config = config;
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
