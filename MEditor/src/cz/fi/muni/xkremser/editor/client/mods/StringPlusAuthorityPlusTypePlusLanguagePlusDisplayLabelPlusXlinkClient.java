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
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.11.13 at 05:02:55 odp. CET 
//

package cz.fi.muni.xkremser.editor.client.mods;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class
 * StringPlusAuthorityPlusTypePlusLanguagePlusDisplayLabelPlusXlinkClient.
 */
public class StringPlusAuthorityPlusTypePlusLanguagePlusDisplayLabelPlusXlinkClient extends StringPlusAuthorityPlusTypePlusLanguageClient implements
		IsSerializable {

	/** The display label. */
	protected String displayLabel;

	/** The xlink. */
	protected String xlink;

	/**
	 * Gets the value of the displayLabel property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * Sets the value of the displayLabel property.
	 * 
	 * @param value
	 *          allowed object is {@link String }
	 * 
	 */
	public void setDisplayLabel(String value) {
		this.displayLabel = value;
	}

	/**
	 * Gets the xlink.
	 * 
	 * @return the xlink
	 */
	public String getXlink() {
		return xlink;
	}

	/**
	 * Sets the xlink.
	 * 
	 * @param xlink
	 *          the new xlink
	 */
	public void setXlink(String xlink) {
		this.xlink = xlink;
	}

}
