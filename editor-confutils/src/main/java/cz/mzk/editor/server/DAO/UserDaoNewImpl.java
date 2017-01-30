package cz.mzk.editor.server.DAO;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by rumanekm on 1/23/17.
 */
public class UserDaoNewImpl extends AbstractDAO implements UserDaoNew {

    private static final Logger LOGGER = Logger.getLogger(UserDaoNewImpl.class);

    @Override
    public Long getUserIdFromPrincipal(String principal) {
        Long userId = null;

        String sql = "SELECT id FROM editor_user WHERE principal=(?)";
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, principal);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                userId = rs.getLong("id");
            } else {
                return null;
            }
        } catch (DatabaseException e) {
            LOGGER.warn(e.getMessage());
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        } finally {
            closeConnection();
        }

        return userId;
    }

    public Long insertNewUser(String principal, String username) {
        Long userId = null;

        try {
            // ldap migration
            PreparedStatement ldapStatement = getConnection()
                    .prepareStatement("SELECT editor_user_id FROM ldap_identity WHERE identity =(?)");
            ldapStatement.setString(1, username);
            ResultSet ldapPesultSet = ldapStatement.executeQuery();
            if (ldapPesultSet.next()) {
                userId = ldapPesultSet.getLong("editor_user_id");
                PreparedStatement addPrincipalStatement = getConnection()
                        .prepareStatement("UPDATE editor_user SET principal=(?) WHERE id=(?)");
                addPrincipalStatement.setString(1, principal);
                addPrincipalStatement.setLong(2, userId);
                addPrincipalStatement.executeUpdate();
            } else {
                String insertSql = "INSERT INTO editor_user (principal) VALUES (?)";
                PreparedStatement newUserStatement = getConnection().prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                newUserStatement.setString(1, principal);
                newUserStatement.execute();
                ResultSet resKey = newUserStatement.getGeneratedKeys();
                if (resKey != null && resKey.next()) {
                    userId = resKey.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        } catch (DatabaseException e) {
            LOGGER.warn(e.getMessage());
        } finally {
            closeConnection();
        }

        return userId;
    }
}
