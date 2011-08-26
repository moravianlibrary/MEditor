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

package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.CartographicsClient;

import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class CartographicsHolder.
 */
public class CartographicsHolder
        extends MetadataHolder {

    /** The coordinates. */
    private final ListOfSimpleValuesHolder coordinates;

    /** The scales. */
    private final ListOfSimpleValuesHolder scales;

    /** The projections. */
    private final ListOfSimpleValuesHolder projections;

    /**
     * Instantiates a new cartographics holder.
     */
    public CartographicsHolder() {
        this.coordinates = new ListOfSimpleValuesHolder();
        this.scales = new ListOfSimpleValuesHolder();
        this.projections = new ListOfSimpleValuesHolder();
    }

    /**
     * Gets the cartographics.
     * 
     * @return the cartographics
     */
    public CartographicsClient getCartographics() {
        CartographicsClient cartographicsClient = new CartographicsClient();
        cartographicsClient.setCoordinates(coordinates.getValues());
        cartographicsClient.setScale(scales.getValues().size() > 0 ? scales.getValues().get(0) : null);
        cartographicsClient.setProjection(projections.getValues().size() > 0 ? projections.getValues().get(0)
                : null);
        if (BiblioModsUtils.hasOnlyNullFields(cartographicsClient)) {
            return null;
        } else
            return cartographicsClient;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements
     * ()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException("Mods");
    }

    /**
     * Gets the coordinates.
     * 
     * @return the coordinates
     */
    public ListOfSimpleValuesHolder getCoordinates() {
        return coordinates;
    }

    /**
     * Gets the scales.
     * 
     * @return the scales
     */
    public ListOfSimpleValuesHolder getScales() {
        return scales;
    }

    /**
     * Gets the projections.
     * 
     * @return the projections
     */
    public ListOfSimpleValuesHolder getProjections() {
        return projections;
    }

}
