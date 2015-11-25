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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.google.inject.Injector;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.server.util.IOUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

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
            Response imageServerResponse = null;
            resp.setContentType("image/jpeg");
            StringBuilder sb = new StringBuilder();
            sb.append(config.getFedoraHost()).append("/objects/").append(uuid)
                    .append("/datastreams/IMG_THUMB/content");
            InputStream is = null;
            if (!Constants.MISSING.equals(uuid)) {
                try {
                    // HttpUrlConnection neumi redirect http <-> https proto se musime ptat na dvakrat
                    // http://stackoverflow.com/questions/30196009/jaxrs-2-0-client-follow-redirects-property-doesnt-work
                    // http://stackoverflow.com/questions/1884230/java-doesnt-follow-redirect-in-urlconnection
                    HttpAuthenticationFeature authentication = HttpAuthenticationFeature.basic(config.getFedoraLogin(), config.getFedoraPassword());
                    Client fedoraClient = ClientBuilder.newClient(new ClientConfig().register(authentication));
                    WebTarget fedoraWebTarget = fedoraClient.target(sb.toString());
                    Invocation.Builder invocationBuilder = fedoraWebTarget.request();
                    Response fedoraResponse = invocationBuilder.get();
                    System.out.println("thumbnail response status: " + fedoraResponse.getStatus());
                    String imageLocation = fedoraResponse.getHeaderString("Location");
                    fedoraResponse.close();

                    Client imageServerClient = ClientBuilder.newClient();
                    WebTarget imageServerWebTarget = imageServerClient.target(imageLocation);
                    Invocation.Builder imageServerInvocationBuilder = imageServerWebTarget.request();
                    imageServerResponse = imageServerInvocationBuilder.get();
                    System.out.println("image response status: " + imageServerResponse.getStatus());
                    is = imageServerResponse.readEntity(InputStream.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                is = new FileInputStream(new File("images/other/file_not_found.png"));
            }
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
                        imageServerResponse.close();
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
