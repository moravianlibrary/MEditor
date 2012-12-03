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

package cz.mzk.editor.server.LDAP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

// TODO: Auto-generated Javadoc
/**
 * The Class LDAPSearchImpl.
 * 
 * @author Matous Jobanek
 * @author xrosecky
 * @version Nov 14, 2012
 */
public class LDAPSearchImpl
        implements LDAPSearch {

    /** The ldap template. */
    private final LdapTemplate ldapTemplate;

    /** The base. */
    private final String base;

    /**
     * Instantiates a new lDAP search impl.
     * 
     * @param properties
     *        the properties
     * @throws Exception
     *         the exception
     */
    public LDAPSearchImpl(Properties properties)
            throws Exception {
        LdapContextSource ldapContext = new LdapContextSource();
        ldapContext.setUrl(properties.getProperty("url"));
        base = properties.getProperty("base");
        ldapContext.setBase(base);
        if (properties.containsKey("userDN") && properties.containsKey("password")) {
            ldapContext.setUserDn(properties.getProperty("userDN"));
            ldapContext.setPassword(properties.getProperty("password"));
            ldapContext.setAnonymousReadOnly(false);
        } else {
            ldapContext.setAnonymousReadOnly(true);
        }
        ldapContext.afterPropertiesSet();
        ldapContext.setPooled(true);
        this.ldapTemplate = new LdapTemplate(ldapContext);
    }

    /**
     * Search.
     * 
     * @param query
     *        the query
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, List<Object>>> search(String query) {
        AttributesMapper attributesMapper = new AttributesMapper() {

            public Object mapFromAttributes(Attributes attrs) throws NamingException {
                Map<String, List<Object>> result = new HashMap<String, List<Object>>();
                NamingEnumeration<String> names = attrs.getIDs();
                while (names.hasMore()) {
                    String key = names.next();
                    Attribute attr = attrs.get(key);
                    List<Object> values = new ArrayList<Object>();
                    for (int i = 0; i != attr.size(); i++) {
                        values.add(attr.get(i));
                    }
                    result.put(key, values);
                }
                return result;
            }
        };
        return ldapTemplate.search("", query, attributesMapper);
    }

    /**
     * Auth.
     * 
     * @param user
     *        the user
     * @param pass
     *        the pass
     * @param query
     *        the query
     * @return true, if successful
     */
    public boolean auth(String pass, String query) {
        return ldapTemplate.authenticate("", query, pass);
    }
}
