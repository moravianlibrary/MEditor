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

package cz.fi.muni.xkremser.editor.client.mods;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BiblioModsClient.
 */
public class BiblioModsClient
        implements IsSerializable {

    /** The mods collection. */
    private ModsCollectionClient modsCollection;

    /** The mods. */
    private ModsTypeClient mods;

    /**
     * Gets the mods collection.
     * 
     * @return the mods collection
     */
    public ModsCollectionClient getModsCollection() {
        return modsCollection;
    }

    /**
     * Sets the mods collection.
     * 
     * @param modsCollection
     *        the new mods collection
     */
    public void setModsCollection(ModsCollectionClient modsCollection) {
        this.modsCollection = modsCollection;
    }

    /**
     * Gets the mods.
     * 
     * @return the mods
     */
    public ModsTypeClient getMods() {
        return mods;
    }

    /**
     * Sets the mods.
     * 
     * @param mods
     *        the new mods
     */
    public void setMods(ModsTypeClient mods) {
        this.mods = mods;
    }
}
