package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

public interface UserDAO {

	public static final int NOT_PRESENT = 0;
	public static final int USER = 1;
	public static final int ADMIN = 2;

	public static final String ADMIN_STRING = "admin";

	int isSupported(String identifier);

	boolean hasRole(String role, long userId);

	String getName(String openID);

	String addUserIdentity(OpenIDItem identity, long userId);

	void removeUserIdentity(long id);

	RoleItem addUserRole(RoleItem role, long userId);

	void removeUserRole(long id);

	ArrayList<RoleItem> getRolesOfUser(long id);

	ArrayList<String> getRoles();

	ArrayList<OpenIDItem> getIdentities(String id);

	void removeUser(long id);

	String addUser(UserInfoItem user);

	ArrayList<UserInfoItem> getUsers();

}
