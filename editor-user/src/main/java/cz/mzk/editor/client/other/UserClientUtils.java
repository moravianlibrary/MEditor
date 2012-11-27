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

package cz.mzk.editor.client.other;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.UserIdentity;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public class UserClientUtils {

    /**
     * Gets the identity title.
     * 
     * @param type
     *        the type
     * @return the identity title
     */
    public static String getIdentityTitle(Constants.USER_IDENTITY_TYPES type) {
        switch (type) {
            case OPEN_ID:
                return "OpenID";

            case LDAP:
                return "LDAP";

            case SHIBBOLETH:
                return "Shibboleth";

            default:
                return "Unknown";
        }
    }

    /**
     * Copy identities.
     * 
     * @param userIdentity
     *        the user identity
     * @return the list grid record[]
     */
    public static ListGridRecord[] copyIdentities(UserIdentity userIdentity) {
        ListGridRecord[] identityRecords = new ListGridRecord[userIdentity.getIdentities().size()];
        if (userIdentity.getIdentities() != null) {
            for (int i = 0, lastIndex = userIdentity.getIdentities().size(); i < lastIndex; i++) {
                ListGridRecord rec = new ListGridRecord();
                rec.setAttribute(Constants.ATTR_IDENTITY, userIdentity.getIdentities().get(i));
                identityRecords[i] = rec;
            }
        }
        return identityRecords;
    }

    /**
     * Copy values.
     * 
     * @param roles
     *        the role
     * @return the list grid record[]
     */
    public static ListGridRecord[] copyRoles(List<RoleItem> roles) {

        ListGridRecord[] roleRecords = new ListGridRecord[roles.size()];
        if (roles != null && roles.size() > 0) {
            for (int i = 0, lastIndex = roles.size(); i < lastIndex; i++) {
                ListGridRecord rec = new ListGridRecord();
                rec.setAttribute(Constants.ATTR_NAME, roles.get(i).getName());
                rec.setAttribute(Constants.ATTR_DESC, roles.get(i).getDescription());
                roleRecords[i] = rec;
            }
        }

        return roleRecords;
    }

    /**
     * Copy rights.
     * 
     * @param rights
     *        the rights
     * @return the list grid record[]
     */
    public static ListGridRecord[] copyRights(List<Constants.EDITOR_RIGHTS> rights) {
        ListGridRecord[] rightsRecords = new ListGridRecord[rights.size()];
        if (rights != null && rights.size() > 0) {
            for (int i = 0, lastIndex = rights.size(); i < lastIndex; i++) {
                ListGridRecord rec = new ListGridRecord();
                rec.setAttribute(Constants.ATTR_NAME, rights.get(i).toString());
                rec.setAttribute(Constants.ATTR_DESC, rights.get(i).getDesc());
                rightsRecords[i] = rec;
            }
        }
        return rightsRecords;
    }

}
