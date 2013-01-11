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

import java.util.List;
import java.util.Map;

/**
 * @author Matous Jobanek
 * @version Nov 14, 2012
 */
public interface LDAPSearch {

    /**
     * Auth.
     * 
     * @param pass
     *        the pass
     * @param query
     *        the query
     * @return true, if successful
     */
    boolean auth(String pass, String query);

    /**
     * Search.
     * 
     * @param query
     *        the query
     * @return the list
     */
    List<Map<String, List<Object>>> search(String query);

}
