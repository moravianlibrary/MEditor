/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
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

package cz.mzk.editor.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.inject.Inject;

import com.google.inject.Injector;

import org.apache.log4j.Logger;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.DATASTREAM_ID;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.IOUtils;
import cz.mzk.editor.server.util.RESTHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class ScanImgServiceImpl.
 */
public class PdfServiceImpl
        extends HttpServlet {

    private static final long serialVersionUID = -4036423754419150618L;

    /** The configuration. */
    @Inject
    private EditorConfiguration config;

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(PdfServiceImpl.class.getPackage().toString());

    /*
     * (non-Javadoc)
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        String pdfPath = req.getPathInfo();

        if (pdfPath == null || "".equals(pdfPath)) {
            resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            LOGGER.error("Problem with downloading pdf file - the path is empty.");
        }

        InputStream is;
        if (pdfPath.startsWith("/" + Constants.URL_PDF_FROM_FEDORA_PREFIX)) {

            is =
                    RESTHelper.get(config.getFedoraHost() + "/objects"
                                           + pdfPath.substring(Constants.URL_PDF_FROM_FEDORA_PREFIX.length())
                                           + "/datastreams/" + DATASTREAM_ID.IMG_FULL + "/content",
                                   config.getFedoraLogin(),
                                   config.getFedoraPassword(),
                                   false);
        } else {
            is =
                    new FileInputStream(config.getImagesPath() + File.separator + pdfPath
                            + Constants.PDF_EXTENSION);
        }

        ServletOutputStream os = resp.getOutputStream();
        try {
            IOUtils.copyStreams(is, os);
        } catch (IOException e) {
            resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
            LOGGER.error("Problem with downloading pdf file.", e);
        } finally {
            os.flush();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
                    LOGGER.error("Problem with downloading pdf file.", e);
                } finally {
                    is = null;
                }
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
        //        base = config.getScanInputQueuePath();
        //        baseOk = base != null && !"".equals(base);
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
