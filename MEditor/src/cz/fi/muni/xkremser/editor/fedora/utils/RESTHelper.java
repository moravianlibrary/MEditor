/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gwt.user.server.Base64Utils;

// TODO: Auto-generated Javadoc
/**
 * Umoznuje se dotazovat na fedoru, ktera potrebuje autentizaci.
 *
 * @author pavels
 */
public class RESTHelper {

	/**
	 * Input stream.
	 *
	 * @param urlString the url string
	 * @param user the user
	 * @param pass the pass
	 * @return the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static InputStream inputStream(String urlString, String user, String pass) throws IOException {
		URLConnection uc = openConnection(urlString, user, pass);
		return uc.getInputStream();
	}

	/**
	 * Open connection.
	 *
	 * @param urlString the url string
	 * @param user the user
	 * @param pass the pass
	 * @return the uRL connection
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static URLConnection openConnection(String urlString, String user, String pass) throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		String userPassword = user + ":" + pass;
		String encoded = Base64Utils.toBase64(userPassword.getBytes());
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("Authorization", "Basic " + encoded);
		return uc;
	}

}
