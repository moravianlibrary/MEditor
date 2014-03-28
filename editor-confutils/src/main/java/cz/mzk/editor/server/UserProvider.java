package cz.mzk.editor.server;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.server.DAO.DAOUtils;
import cz.mzk.editor.server.DAO.DatabaseException;
import org.springframework.security.core.context.SecurityContext;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by rumanekm on 28.3.14.
 */
public class UserProvider {

    /** The http session provider. */
    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    DAOUtils daoUtils;

    public Long getUserId() {
        SecurityContext secContext =
                (SecurityContext) httpSessionProvider.get().getAttribute("SPRING_SECURITY_CONTEXT");
        EditorUserAuthentication authentication = null;
        if (secContext != null) authentication = (EditorUserAuthentication) secContext.getAuthentication();

        try {
        if (authentication != null) {
            return daoUtils.getUsersId((String) authentication.getPrincipal(),
                    authentication.getIdentityType(),
                    true);
        } else {
           //TODO
        }
        } catch (SQLException e) {
           //TODO
        } catch (DatabaseException e) {
          // TODO
        }
        return null;
    }
}
