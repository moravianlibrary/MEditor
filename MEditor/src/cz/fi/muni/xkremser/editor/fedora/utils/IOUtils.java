/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.logging.Level;

// TODO: Auto-generated Javadoc
/**
 * The Class IOUtils.
 */
public class IOUtils {

	/** The Constant LOGGER. */
	public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(IOUtils.class.getName());

	/**
	 * Instantiates a new iO utils.
	 */
	private IOUtils() {
	}

	/**
	 * Kopirovani ze vstupniho proudo do vystupniho.
	 *
	 * @param is Vstupni proud
	 * @param os Vystupni proud
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyStreams(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[8192];
		int read = -1;
		while ((read = is.read(buffer)) > 0) {
			os.write(buffer, 0, read);
		}
	}

	/**
	 * Kopiruje a pocita digest.
	 *
	 * @param is Vstupni stream
	 * @param os Vystupni stream
	 * @param digest Digest
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyStreams(InputStream is, OutputStream os, MessageDigest digest) throws IOException {
		byte[] buffer = new byte[8192];
		int read = -1;
		while ((read = is.read(buffer)) > 0) {
			os.write(buffer, 0, read);
			digest.update(buffer, 0, read);
		}
	}

	/**
	 * Read as string.
	 *
	 * @param is the is
	 * @param charset the charset
	 * @param closeInput the close input
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readAsString(InputStream is, Charset charset, boolean closeInput) throws IOException {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			copyStreams(is, bos);
			return new String(bos.toByteArray(), charset);
		} finally {
			if ((is != null) && closeInput) {
				is.close();
			}
		}
	}

	/**
	 * Save to file.
	 *
	 * @param data the data
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void saveToFile(String data, File file) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * Bos.
	 *
	 * @param inFile the in file
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] bos(File inFile) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(inFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			copyStreams(is, bos);
			return bos.toByteArray();
		} finally {
			if (is != null)
				is.close();
		}

	}

	/**
	 * Check directory.
	 *
	 * @param name the name
	 * @return the file
	 */
	public static File checkDirectory(String name) { // TODO: zaintegrovatz
		File directory = new File(name);
		if (!directory.exists() || !directory.isDirectory()) {
			if (!directory.mkdir()) {
				LOGGER.severe("Folder doesn't exist and can't be created: " + directory.getAbsolutePath());
				throw new RuntimeException("Folder doesn't exist and can't be created: " + directory.getAbsolutePath());
			}
		}
		return directory;
	}

	/**
	 * Clean directory.
	 *
	 * @param directory the directory
	 */
	public static void cleanDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++)
				files[i].delete();
		}
	}

	/**
	 * Copy bundled resources.
	 *
	 * @param caller the caller
	 * @param texts the texts
	 * @param prefix the prefix
	 * @param folder the folder
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyBundledResources(Class caller, String[] texts, String prefix, File folder) throws FileNotFoundException, IOException {
		for (String def : texts) {
			InputStream is = null;
			FileOutputStream os = null;
			try {
				File file = new File(folder, def);
				if (!file.exists()) {
					String res = prefix + def;
					is = caller.getResourceAsStream(res);
					if (is == null)
						throw new IOException("cannot find resource " + res);
					os = new FileOutputStream(file);
					copyStreams(is, os);
				}
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
					}
				}
			}
		}
	}
}
