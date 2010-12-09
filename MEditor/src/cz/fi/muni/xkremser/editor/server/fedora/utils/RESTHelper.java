/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.server.fedora.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import com.google.gwt.user.server.Base64Utils;

import cz.fi.muni.xkremser.editor.client.ConnectionException;

// TODO: Auto-generated Javadoc
/**
 * Umoznuje se dotazovat na fedoru, ktera potrebuje autentizaci.
 * 
 * @author pavels
 */
public class RESTHelper {
	public static final int GET = 0;
	public static final int PUT = 1;
	public static final int POST = 2;
	public static final int DELETE = 3;

	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(RESTHelper.class.getName());

	/**
	 * Input stream.
	 * 
	 * @param urlString
	 *          the url string
	 * @param user
	 *          the user
	 * @param pass
	 *          the pass
	 * @return the input stream
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public static InputStream inputStream(String urlString, String user, String pass) throws IOException {
		URLConnection uc = openConnection(urlString, user, pass);
		if (uc == null)
			return null;
		return uc.getInputStream();
	}

	public static URLConnection openConnection(String urlString, String user, String pass) throws MalformedURLException, IOException {
		return openConnection(urlString, user, pass, GET, null);
	}

	/**
	 * Open connection.
	 * 
	 * @param urlString
	 *          the url string
	 * @param user
	 *          the user
	 * @param pass
	 *          the pass
	 * @return the uRL connection
	 * @throws MalformedURLException
	 *           the malformed url exception
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	public static URLConnection openConnection(String urlString, String user, String pass, final int method, String content) throws MalformedURLException,
			IOException {
		URL url = new URL(urlString);
		String userPassword = user + ":" + pass;
		String encoded = Base64Utils.toBase64(userPassword.getBytes());
		URLConnection uc = null;
		try {
			uc = url.openConnection();
			uc.setRequestProperty("Authorization", "Basic " + encoded);
			switch (method) {
				case GET:
				break;
				case PUT:
					uc.setDoOutput(true);
					((HttpURLConnection) uc).setRequestMethod("PUT");
					OutputStreamWriter out = null;
					try {
						out = new OutputStreamWriter(uc.getOutputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						out.write(content);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// try {
					out.flush();
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
				break;
				case POST:
				break;
				case DELETE:
					uc.setDoOutput(true);
					uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					((HttpURLConnection) uc).setRequestMethod("DELETE");
				break;
			}

			int resp = ((HttpURLConnection) uc).getResponseCode();
			if (resp != 200) {
				LOGGER.log(Level.SEVERE, "Unable to open connection on " + urlString + "  response code: " + resp);
				// return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionException("connection cannot be established");
		}
		return uc;
	}

	public static boolean put(String urlString, String content, String user, String pass) {
		URLConnection conn = null;
		try {
			conn = openConnection(urlString, user, pass, PUT, content);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		try {
			if (conn != null)
				System.out.println(convertStreamToString(conn.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (conn != null)
				System.out.println(conn.getContent().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean delete(String urlString, String user, String pass) {
		HttpURLConnection uc = null;
		try {
			uc = (HttpURLConnection) openConnection(urlString, user, pass);
			if (uc == null)
				return false;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		uc.setDoOutput(true);
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		try {
			uc.setRequestMethod("DELETE");
		} catch (ProtocolException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

}
