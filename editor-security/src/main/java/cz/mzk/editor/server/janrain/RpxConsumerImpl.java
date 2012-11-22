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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * @author Matous Jobanek
 * @version Nov 22, 2012
 */
public class RpxConsumerImpl
        implements RpxConsumer {

    /**
     * {@inheritDoc}
     */
    public OpenIDAuthenticationToken consume(String rpxBaseUrl, String apiKey, HttpServletRequest request) {
        RpxConnection rpx = new RpxConnection(apiKey, rpxBaseUrl);
        String token = request.getParameter("token");

        if (token == null) {
            throw new IllegalArgumentException("request has no token available.");
        }

        // Make an auth dom element from the xml returned by the provider.
        JSONObject rpxAuth = rpx.authInfo(token);

        // Check if authentication failed.
        String stat = (String) rpxAuth.get("stat");
        if (!"ok".equals(stat)) {
            String error = "User authentication failed";
            //logger.info(error);
            //response.sendError(HttpServletResponse.SC_FORBIDDEN, error);
            return null;
        }

        JSONObject profile = (JSONObject) rpxAuth.get("profile");

        //guaranteed
        String identifier = (String) profile.get("identifier");

        //from most with users consent
        String email = (String) profile.get("email");
        String displayName = (String) profile.get("displayName");
        String username = (String) profile.get("preferredUsername");

        List<OpenIDAttribute> attrs = new ArrayList<OpenIDAttribute>();
        attrs.add(createOpenIDAttribute("email", "http://axschema.org/email", email));
        attrs.add(createOpenIDAttribute("name", "http://axschema.org/namePerson", displayName));
        attrs.add(createOpenIDAttribute("username", "http://axschema.org/namePerson/friendly", username));

        return new OpenIDAuthenticationToken(identifier, new ArrayList<GrantedAuthority>(), identifier, attrs);

    }

    private OpenIDAttribute createOpenIDAttribute(String key, String type, String... values) {
        OpenIDAttribute attribute = new OpenIDAttribute(key, type, Arrays.asList(values));
        return attribute;
    }

}
