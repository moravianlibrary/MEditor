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

package cz.fi.muni.xkremser.editor.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum KrameriusModel.
 */
public enum DigitalObjectModel
        implements IsSerializable {

    /** The MONOGRAPH. */
    MONOGRAPH("monograph", ""), /** The MONOGRAPHUNIT. */
    MONOGRAPHUNIT("monographunit", Constants.MONOGRAPH_UNIT_ICON), /**
     * The
     * PERIODICAL.
     */
    PERIODICAL("periodical", ""), /** The PERIODICALVOLUME. */
    PERIODICALVOLUME("periodicalvolume", Constants.VOLUME_ICON), /**
     * The
     * PERIODICALITEM.
     */
    PERIODICALITEM("periodicalitem", Constants.PERIODICAL_ITEM_ICON), /**
     * The
     * PAGE.
     */
    PAGE("page", ""),
    /** The INTERNALPART. */
    INTERNALPART("internalpart", Constants.INTERNAL_PART_ICON)/*
                                                               * ,
                                                               * THESIS("thesis"
                                                               * , "")
                                                               */

    /**
     * Instantiates a new kramerius model.
     * 
     * @param value
     *        the value
     * @param icon
     *        the icon
     */
    ;

    private DigitalObjectModel(String value, String icon) {
        this.value = value;
        this.icon = icon;
    }

    /** The value. */
    private final String value;

    /** The icon. */
    private final String icon;

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the icon.
     * 
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * To string.
     * 
     * @param km
     *        the km
     * @return the string
     */
    public static String toString(DigitalObjectModel km) {
        return km.getValue();
    }

    /**
     * Parses the string.
     * 
     * @param s
     *        the s
     * @return the model
     */
    public static DigitalObjectModel parseString(String s) {
        DigitalObjectModel[] values = DigitalObjectModel.values();
        for (DigitalObjectModel model : values) {
            if (model.getValue().equalsIgnoreCase(s)) return model;
        }
        throw new RuntimeException("Unsupported type: " + s);
    }

}