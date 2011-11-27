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
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.inject.Inject;

import com.google.inject.Injector;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

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

    /*
     * (non-Javadoc)
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.addHeader("Cache-Control", "max-age=" + Constants.HTTP_CACHE_SECONDS);
        String uuid =
                req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_THUMBNAIL_PREFIX)
                        + Constants.SERVLET_THUMBNAIL_PREFIX.length() + 1);

        if (uuid != null && !"".equals(uuid)) {
            resp.setContentType("image/jpeg");
            StringBuffer sb = new StringBuffer();
            sb.append(config.getFedoraHost()).append("/objects/").append(uuid)
                    .append("/datastreams/IMG_THUMB/content");
            InputStream is =
                    RESTHelper.get(sb.toString(), config.getFedoraLogin(), config.getFedoraPassword(), true);
            if (is == null) {
                return;
            }
            ServletOutputStream os = resp.getOutputStream();

            try {
                IOUtils.copyStreams(is, os);

            } catch (IOException e) {
                // TODO: zalogovat
            } finally {
                os.flush();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        // TODO: zalogovat
                    } finally {
                        is = null;
                    }
                }
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
