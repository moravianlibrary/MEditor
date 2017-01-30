package cz.mzk.editor.server.DAO;

/**
 * Created by rumanekm on 1/23/17.
 */
public interface UserDaoNew {
    Long getUserIdFromPrincipal(String principal);

    Long insertNewUser(String principal, String username);
}