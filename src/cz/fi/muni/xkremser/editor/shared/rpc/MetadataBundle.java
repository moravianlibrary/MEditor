/*
 * Metadata Editor
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

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.io.Serializable;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

/**
 * @author Jiri Kremser
 * @version 16.11.2011
 */
public class MetadataBundle
        implements Serializable {

    private static final long serialVersionUID = -679971373016540771L;
    private DublinCore dc;
    private ModsCollectionClient mods;
    private MarcSpecificMetadata marc;

    @SuppressWarnings("unused")
    private MetadataBundle() {
        // because of serialization
    }

    public MetadataBundle(DublinCore dc, ModsCollectionClient mods, MarcSpecificMetadata marc) {
        this.dc = dc;
        this.mods = mods;
        this.marc = marc;
    }

    public DublinCore getDc() {
        return dc;
    }

    public void setDc(DublinCore dc) {
        this.dc = dc;
    }

    public ModsCollectionClient getMods() {
        return mods;
    }

    public void setMods(ModsCollectionClient mods) {
        this.mods = mods;
    }

    public MarcSpecificMetadata getMarc() {
        return marc;
    }

    public void setMarc(MarcSpecificMetadata marc) {
        this.marc = marc;
    }

}
