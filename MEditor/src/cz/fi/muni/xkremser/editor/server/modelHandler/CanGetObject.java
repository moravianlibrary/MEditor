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
package cz.fi.muni.xkremser.editor.server.modelHandler;

import java.io.IOException;

import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;

// TODO: Auto-generated Javadoc
/**
 * The Interface CanGetObject.
 */
public interface CanGetObject {

	/**
	 * Gets the digital object.
	 * 
	 * @param uuid
	 *          the uuid
	 * @param findRelated
	 *          the find related
	 * @return the digital object
	 * @throws IOException
	 *           Signals that an I/O exception has occurred.
	 */
	AbstractDigitalObjectDetail getDigitalObject(String uuid, final boolean findRelated) throws IOException;
}
