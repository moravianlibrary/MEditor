package cz.fi.muni.xkremser.editor.server.DAO;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.UserInfoItem;

public interface UserDAO {

	public static final int NOT_PRESENT = 0;
	public static final int USER = 1;
	public static final int ADMIN = 2;

	int isSupported(String identifier);

	void addUserIdentity(String identifier, String alternativeIdentifier);

	ArrayList<RoleItem> getRoles(String id);

	ArrayList<OpenIDItem> getIdentities(String id);

	void removeUser(long id);

	boolean addUser(UserInfoItem user);

	ArrayList<UserInfoItem> getUsers();

}
