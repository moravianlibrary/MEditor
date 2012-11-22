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

package cz.mzk.editor.server.janrain;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Matous Jobanek
 * @version Nov 22, 2012
 */
public class RpxConnection {

    private final String apiKey;
    private final String baseUrl;

    public RpxConnection(String apiKey, String baseUrl) {
        if (baseUrl == null || apiKey == null) {
            throw new IllegalArgumentException("apiKey and baseUrl must not be null.");

        }
        while (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public JSONObject authInfo(String token) {
        if (token == null) throw new IllegalArgumentException("token was null.");

        Map query = new HashMap();
        query.put("token", token);
        return apiCall("auth_info", query);
    }

    private JSONObject apiCall(String methodName, Map partialQuery) {
        Map query = null;
        if (partialQuery == null) {
            query = new HashMap();
        } else {
            query = new HashMap(partialQuery);
        }
        query.put("format", "json");
        query.put("apiKey", apiKey);
        StringBuilder sb = new StringBuilder();
        for (Iterator it = query.entrySet().iterator(); it.hasNext();) {
            if (sb.length() > 0) {
                sb.append('&');
            }

            try {
                Map.Entry e = (Map.Entry) it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();

                if (key != null && value != null) {
                    sb.append(URLEncoder.encode(key, "UTF-8"));
                    sb.append('=');
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                }
            } catch (Exception e) {
                throw new RuntimeException("Unexpected encoding error", e);
            }
        }
        String data = sb.toString();
        try {
            URL url = new URL(baseUrl + "/api/v2/" + methodName);
            URLConnection conn = url.openConnection();

            if (conn instanceof HttpURLConnection) {
                HttpURLConnection httpconn = (HttpURLConnection) conn;
                httpconn.setRequestMethod("POST");
                httpconn.setDoOutput(true);
                httpconn.connect();
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                osw.write(data);
                osw.close();
            }
            JSONObject json = (JSONObject) JSONValue.parse(new InputStreamReader(conn.getInputStream()));
            BasicAuthenticationFilter f;
            return json;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unexpected URL error", e);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IO error", e);
        }
    }

}
