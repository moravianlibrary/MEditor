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

package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gwt.user.server.Base64Utils;

import org.apache.log4j.Logger;

import cz.fi.muni.xkremser.editor.client.ConnectionException;

// TODO: Auto-generated Javadoc
public class RESTHelper {

    /** The Constant GET. */
    public static final int GET = 0;

    /** The Constant PUT. */
    public static final int PUT = 1;

    /** The Constant POST. */
    public static final int POST = 2;

    /** The Constant DELETE. */
    public static final int DELETE = 3;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(RESTHelper.class);

    /**
     * Input stream.
     * 
     * @param urlString
     *        the url string
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return the input stream
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static InputStream get(String urlString, String user, String pass, boolean robustMode)
            throws IOException {
        URLConnection uc = openConnection(urlString, user, pass, robustMode);
        if (uc == null) return null;
        return uc.getInputStream();
    }

    /**
     * Open connection.
     * 
     * @param urlString
     *        the url string
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return the uRL connection
     * @throws MalformedURLException
     *         the malformed url exception
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static URLConnection openConnection(String urlString, String user, String pass, boolean robustMode)
            throws MalformedURLException, IOException {
        return openConnection(urlString, user, pass, GET, null, robustMode);
    }

    /**
     * Open connection.
     * 
     * @param urlString
     *        the url string
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @param method
     *        the method
     * @param content
     *        the content
     * @return the uRL connection
     * @throws MalformedURLException
     *         the malformed url exception
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static URLConnection openConnection(String urlString,
                                               String user,
                                               String pass,
                                               final int method,
                                               String content,
                                               boolean robustMode) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        boolean notAuth = false;
        String encoded = null;
        if (notAuth = (user == null || pass == null)) {
            String userPassword = user + ":" + pass;
            encoded = Base64Utils.toBase64(userPassword.getBytes());
        }
        URLConnection uc = null;
        OutputStreamWriter out = null;
        try {
            uc = url.openConnection();
            if (!notAuth) {
                uc.setRequestProperty("Authorization", "Basic " + encoded);
            }
            switch (method) {
                case GET:
                    break;
                case PUT:
                    uc.setDoOutput(true);
                    ((HttpURLConnection) uc).setRequestMethod("PUT");
                    try {
                        out = new OutputStreamWriter(uc.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.flush();
                    break;
                case POST:
                    uc.setDoOutput(true);
                    ((HttpURLConnection) uc).setRequestMethod("POST");
                    try {
                        out = new OutputStreamWriter(uc.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.flush();
                    break;
                case DELETE:
                    uc.setDoOutput(true);
                    uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    ((HttpURLConnection) uc).setRequestMethod("DELETE");
                    break;
            }

            int resp = ((HttpURLConnection) uc).getResponseCode();
            if (resp != 200) {
                if (robustMode) {
                    return null;
                } else {
                    LOGGER.error("Unable to open connection on " + urlString + "  response code: " + resp);
                    throw new ConnectionException("connection cannot be established");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectionException("connection cannot be established");
        }
        return uc;
    }

    /**
     * Put or post method.
     * 
     * @param urlString
     *        the url string
     * @param content
     *        the content
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return true, if successful
     */
    private static boolean putOrPost(String urlString,
                                     String content,
                                     String user,
                                     String pass,
                                     boolean robustMode,
                                     boolean isPut) {
        URLConnection conn = null;
        try {
            conn = openConnection(urlString, user, pass, isPut ? PUT : POST, content, robustMode);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            if (conn != null) LOGGER.debug(convertStreamToString(conn.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * Put.
     * 
     * @param urlString
     *        the url string
     * @param content
     *        the content
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return true, if successful
     */
    public static boolean put(String urlString, String content, String user, String pass, boolean robustMode) {
        return putOrPost(urlString, content, user, pass, robustMode, true);
    }

    /**
     * Post.
     * 
     * @param urlString
     *        the url string
     * @param content
     *        the content
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return true, if successful
     */
    public static boolean post(String urlString, String content, String user, String pass, boolean robustMode) {
        return putOrPost(urlString, content, user, pass, robustMode, false);
    }

    /**
     * Delete.
     * 
     * @param urlString
     *        the url string
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @return true, if successful
     */
    public static boolean delete(String urlString, String user, String pass, boolean robustMode) {
        HttpURLConnection uc = null;
        try {
            uc = (HttpURLConnection) openConnection(urlString, user, pass, robustMode);
            if (uc == null) return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try {
            uc.setRequestMethod("DELETE");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Convert stream to string.
     * 
     * @param is
     *        the is
     * @return the string
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

}
