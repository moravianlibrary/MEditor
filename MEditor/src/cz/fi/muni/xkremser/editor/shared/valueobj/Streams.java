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
package cz.fi.muni.xkremser.editor.shared.valueobj;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class Streams.
 */
public class Streams implements IsSerializable {

	/** The dc. */
	private DublinCore dc;

	/** The mods. */
	private ModsCollectionClient mods;

	/**
	 * Gets the dc.
	 * 
	 * @return the dc
	 */
	public DublinCore getDc() {
		return dc;
	}

	/**
	 * Sets the dc.
	 * 
	 * @param dc
	 *          the new dc
	 */
	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	/**
	 * Gets the mods.
	 * 
	 * @return the mods
	 */
	public ModsCollectionClient getMods() {
		return mods;
	}

	/**
	 * Sets the mods.
	 * 
	 * @param mods
	 *          the new mods
	 */
	public void setMods(ModsCollectionClient mods) {
		this.mods = mods;
	}

}
