/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server;


// TODO: Auto-generated Javadoc
/**
 * The Class ServerUtils.
 */
public class ServerUtils {

	public static boolean isCausedByException(Throwable t, Class<? extends Exception> type) {
		if (t == null)
			return false;
		Throwable aux = t;
		while (aux != null) {
			if (type.isInstance(aux))
				return true;
			aux = aux.getCause();
		}
		return false;
	}

}
