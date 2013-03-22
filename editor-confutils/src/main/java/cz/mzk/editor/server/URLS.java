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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import javax.inject.Inject;

import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.server.config.EditorConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class URLS.
 */
public class URLS {

    @Inject
    private static EditorConfiguration config;

    /** The Constant LOCALHOST. */
    public static boolean LOCALHOST() {
        if (config != null) {
            return config.isLocalhost();
        } else {
            return false;
        }
    };

    /** The Constant ROOT. */
    public static final String ROOT() {
        return LOCALHOST() ? "" : "/meditor";
    }

    // public static final String ROOT = "/skin1";
    
    public static final String HTML_PREFIX = "/html";
    public static final String MAIN_PAGE = "/MEditor.html";
    public static final String LOGIN_PAGE = HTML_PREFIX + "/login.html";
    public static final String LOGIN_LOCAL_PAGE = HTML_PREFIX + "/loginLocal.html";
    public static final String ERROR_LOGIN_SUFFIX = "login_error=1";
    public static final String ERROR_DB_SUFFIX = "login_error=2";
    public static final String INFO_PAGE = HTML_PREFIX + "/info.html";
    public static final String NOT_FOUND_PAGE = HTML_PREFIX + "/404.html";
    public static final String NOT_FOUND_IMAGE = "/images/404.jpg";
    public static final String ICON = "/favicon.ico";
    public static final String FLAGS = "/images/flags.gif";
    public static final String CSS = "/css/MEditor.css";
    public static final String REQUEST_PREFIX = "/request";
    public static final String AUTH_SERVLET = "/auth";
    public static final String DOWNLOAD_SERVLET = "/download";
    public static final String DJATOKA_METADATA =
            "/djatoka/resolver?url_ver=Z39.88-2004&svc_id=info:lanl-repo/svc/getMetadata&rft_id=";

    @SuppressWarnings("serial")
    public static Set<String> nonRestricted = new HashSet<String>() {

        {
            add(LOGIN_PAGE);
            add(LOGIN_LOCAL_PAGE);
            add(INFO_PAGE);
            add(AUTH_SERVLET);
            add(CSS);
            add(ICON);
            add(FLAGS);
            add(DOWNLOAD_SERVLET);
            add(NOT_FOUND_PAGE);
            add(NOT_FOUND_IMAGE);
        }
    };

    /**
     * Redirect.
     * 
     * @param resp
     *        the resp
     * @param where
     *        the where
     */
    public static void redirect(HttpServletResponse resp, String where) {
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location", where);
    }

    /**
     * Convert to ajaxurl.
     * 
     * @param parameters
     *        the parameters
     * @return the string
     */
    public static String convertToAJAXURL(@SuppressWarnings("rawtypes") Map parameters) {
        StringBuilder sufix = new StringBuilder();
        sufix.append('#');

        String foundAction = null;

        for (Object keyObj : parameters.keySet()) {
            if (NameTokens.getAllNameTokens().contains(keyObj.toString())) {
                foundAction = (String) keyObj;
                sufix.append(foundAction + ";");
            }
        }

        if (foundAction != null) {
            for (Object keyObj : parameters.keySet()) {
                if (!((String) keyObj).equals(foundAction)) {
                    sufix.append(keyObj);
                    String[] values = (String[]) parameters.get(keyObj);
                    if (values.length > 0 && !"".equals(values[0])) {
                        sufix.append('=').append(values[0]);
                    }
                    sufix.append(';');
                }
            }
            return sufix.toString();
        }
        return "";
    }
}
