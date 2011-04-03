/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.server.fedora;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class FedoraURLConnection.
 */
public class FedoraURLConnection extends URLConnection {

	/** The Constant IMG_FULL. */
	public static final String IMG_FULL = "IMG_FULL";

	/** The Constant IMG_THUMB. */
	public static final String IMG_THUMB = "IMG_THUMB";

	/** The fedora access. */
	private final FedoraAccess fedoraAccess;

	/**
	 * Instantiates a new fedora url connection.
	 * 
	 * @param url
	 *          the url
	 * @param fedoraAccess
	 *          the fedora access
	 */
	FedoraURLConnection(URL url, FedoraAccess fedoraAccess) {
		super(url);
		this.fedoraAccess = fedoraAccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		String path = getURL().getPath();
		String uuid = null;
		String stream = null;
		StringTokenizer tokenizer = new StringTokenizer(path, "/");
		if (tokenizer.hasMoreTokens()) {
			uuid = tokenizer.nextToken();
		}
		if (tokenizer.hasMoreTokens()) {
			stream = tokenizer.nextToken();
		}
		if (stream.equals(IMG_FULL)) {
			return this.fedoraAccess.getImageFULL(uuid);
		} else if (stream.equals(IMG_THUMB)) {
			return this.fedoraAccess.getThumbnail(uuid);
		} else
			throw new IOException("uknown stream !");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
	}
}
