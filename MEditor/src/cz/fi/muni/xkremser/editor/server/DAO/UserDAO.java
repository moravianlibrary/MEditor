package cz.fi.muni.xkremser.editor.server.DAO;

public interface UserDAO {

	public static final int NOT_PRESENT = 0;
	public static final int USER = 1;
	public static final int ADMIN = 2;

	int isSupported(String identifier);

	void addUser(String identifier);

	void addUserIdentity(String identifier, String alternativeIdentifier);

}
