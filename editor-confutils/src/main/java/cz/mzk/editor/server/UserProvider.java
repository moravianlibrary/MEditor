package cz.mzk.editor.server;

import cz.mzk.editor.server.DAO.UserDaoNew;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class UserProvider {

    @Inject
    private Provider<HttpSession> httpSessionProvider;

    @Inject
    private Provider<ServletRequest> servletRequestProvider;

    @Inject
    private UserDaoNew userDaoNew;


    public Long getUserId() {
        SecurityContext secContext =
                (SecurityContext) httpSessionProvider.get().getAttribute("SPRING_SECURITY_CONTEXT");

        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) secContext.getAuthentication();
        Long userId = userDaoNew.getUserIdFromPrincipal(token.getName());
        if (userId == null) {
            userId = userDaoNew.insertNewUser(token.getName(), getName());
        }

        return userId;
    }

    public String getName() {
        SecurityContext secContext =
                (SecurityContext) httpSessionProvider.get().getAttribute("SPRING_SECURITY_CONTEXT");
        return ((KeycloakAuthenticationToken)secContext.getAuthentication())
                .getAccount().getKeycloakSecurityContext().getIdToken().getPreferredUsername();
    }


}
