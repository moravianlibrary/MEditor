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

// TODO: Auto-generated Javadoc
/**
 * The Enum ImageMimeType.
 */
public enum ImageMimeType {

	/** The JPEG. */
	JPEG("image/jpeg", "jpg", true, false), /** The PNG. */
	PNG("image/png", "png", true, false), /** The JPE g2000. */
	JPEG2000("image/jp2", "jp2", true, false),

	/** The XDJVU. */
	XDJVU("image/x.djvu", "djvu", false, true), /** The VNDDJVU. */
	VNDDJVU("image/vnd.djvu", "djvu", false, true), /** The DJVU. */
	DJVU("image/djvu", "djvu", false, true), /** The PDF. */
	PDF("application/pdf", "pdf", false, true);

	/** The value. */
	private String value;

	/** The supportedby java. */
	private boolean supportedbyJava;

	/** The multipage format. */
	private boolean multipageFormat;

	/** The default file extension. */
	private String defaultFileExtension;

	/**
	 * Instantiates a new image mime type.
	 * 
	 * @param value
	 *          the value
	 * @param defaultExtension
	 *          the default extension
	 * @param javasupport
	 *          the javasupport
	 * @param multipageformat
	 *          the multipageformat
	 */
	private ImageMimeType(String value, String defaultExtension, boolean javasupport, boolean multipageformat) {
		this.value = value;
		this.supportedbyJava = javasupport;
		this.multipageFormat = multipageformat;
		this.defaultFileExtension = defaultExtension;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Java native support.
	 * 
	 * @return true, if successful
	 */
	public boolean javaNativeSupport() {
		return supportedbyJava;
	}

	/**
	 * Checks if is multipage format.
	 * 
	 * @return true, if is multipage format
	 */
	public boolean isMultipageFormat() {
		return this.multipageFormat;
	}

	/**
	 * Gets the default file extension.
	 * 
	 * @return the default file extension
	 */
	public String getDefaultFileExtension() {
		return defaultFileExtension;
	}

	/**
	 * Load from mime type.
	 * 
	 * @param mime
	 *          the mime
	 * @return the image mime type
	 */
	public static ImageMimeType loadFromMimeType(String mime) {
		ImageMimeType[] values = values();
		for (ImageMimeType iType : values) {
			if (iType.getValue().equals(mime))
				return iType;
		}
		return null;
	}
}
