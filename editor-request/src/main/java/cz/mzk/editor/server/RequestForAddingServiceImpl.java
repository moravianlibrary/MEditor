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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.request4Adding.client.RequestForAddingService;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.RequestDAO;
import cz.mzk.editor.server.DAO.RequestDAOImpl;

public class RequestForAddingServiceImpl
        extends RemoteServiceServlet
        implements RequestForAddingService {

    private static final long serialVersionUID = 1384030877613255514L;

    @Override
    public String ask() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute(HttpCookies.NAME_KEY);
        String openID = (String) session.getAttribute(HttpCookies.UNKNOWN_ID_KEY);
        RequestDAO reqDAO = new RequestDAOImpl();
        boolean success = false;
        try {

            success =
                    (reqDAO.addNewIdentifierRequest(name, openID, (USER_IDENTITY_TYPES) session
                            .getAttribute(HttpCookies.IDENTITY_TYPE)) >= 0);
        } catch (DatabaseException e) {
            return "Database problem: " + e.getMessage();
        }
        return success ? name + " (" + openID + ")" : null;
    }

    @Override
    public String getName() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        return (String) session.getAttribute(HttpCookies.NAME_KEY);
    }

}
