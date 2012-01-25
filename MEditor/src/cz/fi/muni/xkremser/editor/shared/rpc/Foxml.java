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

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Value object pro Dublin core stream.
 * 
 * @author xkremser
 */
public class Foxml
        implements Serializable {

    private static final long serialVersionUID = 2611047223936243752L;
    private int id;
    private String label;
    private String identifier;
    private String foxml;
    private String noCodedfoxml;

    public String getFoxml() {
        return foxml;
    }

    /**
     * @return the noCodedfoxml
     */

    public String getNoCodedfoxml() {
        return noCodedfoxml;
    }

    /**
     * @param noCodedfoxml
     *        the noCodedfoxml to set
     */

    public void setNoCodedfoxml(String noCodedfoxml) {
        this.noCodedfoxml = noCodedfoxml;
    }

    public void setFoxml(String foxml) {
        this.foxml = foxml;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DublinCore [nameValue=" + label + ", identifier=" + identifier + ", Foxml=" + foxml + "]";
    }

    //    public ListGridRecord toRecord() {
    //        ListGridRecord record = new ListGridRecord();
    //        record.setAttribute(Constants.ATTR_GENERIC_ID, getId());
    //
    //        if (getIdentifier() != null) {
    //            record.setAttribute(DublinCoreConstants.DC_IDENTIFIER, getIdentifier());
    //        }
    //        if (getLabel() != null) {
    //            record.setAttribute(DublinCoreConstants.DC_TITLE, getLabel());
    //        }if (getFoxml() != null) {
    //            record.setAttribute(DublinCoreConstants.DC_TITLE, getFoxml());
    //        }
    //        return record;
    //    }
}
