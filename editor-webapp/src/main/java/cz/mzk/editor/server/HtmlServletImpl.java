package cz.mzk.editor.server;

import com.google.inject.Injector;
import cz.mzk.editor.server.config.EditorConfiguration;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rumanekm on 6.3.14.
 */
public class HtmlServletImpl  extends HttpServlet {

    private static final long serialVersionUID = 2531308251787968311L;

    @Inject
    private EditorConfiguration config;


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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("hostname", config.getHostname());
        request.setAttribute("enabledIdentities", config.getIdentityTypes());
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}
