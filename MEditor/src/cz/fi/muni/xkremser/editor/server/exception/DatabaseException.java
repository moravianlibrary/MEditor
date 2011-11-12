
package cz.fi.muni.xkremser.editor.server.exception;

public class DatabaseException
        extends Exception {

    private static final long serialVersionUID = 6006650420395897243L;

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
