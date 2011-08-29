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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
import cz.fi.muni.xkremser.editor.server.fedora.ImageMimeType;
import cz.fi.muni.xkremser.editor.server.fedora.utils.IOUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanImgServiceImpl.
 */
public class ScanImgServiceImpl
        extends HttpServlet {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ScanImgServiceImpl.class);

    private static final Class<ScanImgServiceImpl> LOCK = ScanImgServiceImpl.class;

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

        String filename =
                req.getRequestURI().substring(req.getRequestURI().indexOf(Constants.SERVLET_SCANS_PREFIX)
                        + Constants.SERVLET_SCANS_PREFIX.length() + 1);
        ServletOutputStream os = resp.getOutputStream();
        if (filename != null && !"".equals(filename) && baseOk) {

            try {
                synchronized (LOCK) {
                    FileInputStream is = new FileInputStream(base + File.separator + filename);
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
                }
                resp.setContentType(ImageMimeType.JPEG.getValue());
                resp.setStatus(HttpURLConnection.HTTP_OK);

            } catch (FileNotFoundException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Unable to open image of a scan. File (" + base + File.separator + filename
                        + ") not found.", e);
            } catch (SecurityException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Unable to open image of a scan because of the access rights on the file system.",
                             e);
            } catch (IOException e) {
                resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                LOGGER.error("Unable to open image of a scan.", e);
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
