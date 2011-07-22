
package cz.fi.muni.xkremser.editor.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cz.fi.muni.xkremser.editor.server.DAO.RequestDAO;
import cz.fi.muni.xkremser.editor.server.DAO.RequestDAOImpl;
import cz.fi.muni.xkremser.editor.server.exception.DatabaseException;

import cz.fi.muni.xkremser.editor.request4Adding.client.RequestForAddingService;

public class RequestForAddingServiceImpl
        extends RemoteServiceServlet
        implements RequestForAddingService {

    @Override
    public String ask() {
        HttpServletRequest request = this.getThreadLocalRequest();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute(HttpCookies.NAME_KEY);
        String openID = (String) session.getAttribute(HttpCookies.UNKNOWN_ID_KEY);
        RequestDAO reqDAO = new RequestDAOImpl();
        boolean success = false;
        try {
            success = reqDAO.addOpenIDRequest(name, openID);
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
