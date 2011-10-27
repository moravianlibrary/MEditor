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

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.xpath.XPathExpressionException;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.ImageMimeType;
import cz.fi.muni.xkremser.editor.server.fedora.KrameriusImageSupport;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FedoraUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.RESTHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class FullImgServiceImpl.
 */
public class FullImgServiceImpl
        extends HttpServlet {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(FullImgServiceImpl.class);

    /** The config. */
    @Inject
    private EditorConfiguration config;

    /** The fedora access. */
    @Inject
    @Named("securedFedoraAccess")
    private FedoraAccess fedoraAccess;

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
                req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_FULL_PREFIX)
                        + Constants.SERVLET_FULL_PREFIX.length() + 1);
        boolean notScale = ClientUtils.toBoolean(req.getParameter(Constants.URL_PARAM_NOT_SCALE));
        ServletOutputStream os = resp.getOutputStream();
        if (uuid != null && !"".equals(uuid)) {

            try {
                String mimetype = fedoraAccess.getMimeTypeForStream(uuid, FedoraUtils.IMG_FULL_STREAM);
                ImageMimeType loadFromMimeType = ImageMimeType.loadFromMimeType(mimetype);
                if (loadFromMimeType == ImageMimeType.JPEG || loadFromMimeType == ImageMimeType.PNG) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(config.getFedoraHost()).append("/objects/").append(uuid)
                            .append("/datastreams/IMG_FULL/content");
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
                        LOGGER.error("Unable to open full image.", e);
                    } finally {
                        os.flush();
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                                LOGGER.error("Unable to close stream.", e);
                            } finally {
                                is = null;
                            }
                        }
                    }
                } else {
                    Image rawImg =
                            KrameriusImageSupport.readImage(uuid,
                                                            FedoraUtils.IMG_FULL_STREAM,
                                                            this.fedoraAccess,
                                                            0,
                                                            loadFromMimeType);
                    BufferedImage scaled = null;
                    if (!notScale) {
                        scaled = KrameriusImageSupport.getSmallerImage(rawImg, 1250, 1000);
                    } else {
                        scaled = KrameriusImageSupport.getSmallerImage(rawImg, 2500, 2000);
                    }
                    KrameriusImageSupport.writeImageToStream(scaled, "JPG", os);
                    resp.setContentType(ImageMimeType.JPEG.getValue());
                    resp.setStatus(HttpURLConnection.HTTP_OK);
                }
            } catch (IOException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Unable to open full image.", e);
            } catch (XPathExpressionException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Unable to create XPath expression.", e);
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
