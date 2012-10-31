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

package cz.mzk.editor.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.mzk.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Enum KrameriusModel.
 */
public enum DigitalObjectModel
        implements IsSerializable {

    /** The MONOGRAPH. */
    MONOGRAPH("monograph", "", TopLevelObjectModel.MONOGRAPH),

    /**
     * The MONOGRAPHUNIT.
     */
    MONOGRAPHUNIT("monographunit", Constants.MONOGRAPH_UNIT_ICON),

    /**
     * The PERIODICAL.
     */
    PERIODICAL("periodical", "", TopLevelObjectModel.PERIODICAL),

    /**
     * The PERIODICALVOLUME.
     */
    PERIODICALVOLUME("periodicalvolume", Constants.VOLUME_ICON),

    /**
     * The PERIODICALITEM.
     */
    PERIODICALITEM("periodicalitem", Constants.PERIODICAL_ITEM_ICON),

    /**
     * The PAGE.
     */
    PAGE("page", ""),

    /** The INTERNALPART. */
    INTERNALPART("internalpart", Constants.INTERNAL_PART_ICON), /*
                                                                 * ,
                                                                 * THESIS("thesis"
                                                                 * , "")
                                                                 */

    /** The ARTICLE. */
    ARTICLE("article", Constants.INTERNAL_PART_ICON),

    /** The MAP */
    MAP("map", "", TopLevelObjectModel.MONOGRAPH),

    /** The MANUSCRIPT */
    MANUSCRIPT("manuscript", "", TopLevelObjectModel.MONOGRAPH),

    /** The ARCHIVE */
    ARCHIVE("archive", "", TopLevelObjectModel.MONOGRAPH),

    /** The SUPPLEMENT */
    SUPPLEMENT("supplement", Constants.INTERNAL_PART_ICON),

    /***/
    //    SUPPLEMENT("supplement", Constants.MONOGRAPH_UNIT_ICON), 

    /**
     * TODO: add the whole RELS-EXT support
     */
    GRAPHIC("graphic", "", null),

    /**
     * Instantiates a new kramerius model.
     * 
     * @param value
     *        the value
     * @param icon
     *        the icon
     */
    ;

    public static enum TopLevelObjectModel {
        MONOGRAPH, PERIODICAL;
    }

    private DigitalObjectModel(String value, String icon) {
        this.value = value;
        this.icon = icon;
    }

    private DigitalObjectModel(String value, String icon, TopLevelObjectModel topLevelType) {
        this.value = value;
        this.icon = icon;
        this.topLevelType = topLevelType;
    }

    /** The value. */
    private final String value;

    /** The icon. */
    private final String icon;

    private TopLevelObjectModel topLevelType = null;

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
     * @return the topLevelType
     */
    public TopLevelObjectModel getTopLevelType() {
        return topLevelType;
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

    public static DigitalObjectModel getModel(int ordinal) {
        for (DigitalObjectModel model : values()) {
            if (ordinal == model.ordinal()) return model;
        }
        return null;
    }

}