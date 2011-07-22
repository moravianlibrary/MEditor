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

import cz.fi.muni.xkremser.editor.client.mods.DateOtherTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DateHolder.
 */
public class DateHolder
        extends ListOfSimpleValuesHolder {

    /** The date name. */
    private final String dateName;

    /** The other date. */
    private final boolean otherDate;

    /**
     * Instantiates a new date holder.
     * 
     * @param dateName
     *        the date name
     * @param otherDate
     *        the other date
     */
    public DateHolder(String dateName, boolean otherDate) {
        this.dateName = dateName;
        this.otherDate = otherDate;
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public DateTypeClient getDate() {
        DateTypeClient dateTypeClient = otherDate ? new DateOtherTypeClient() : new DateTypeClient();
        if (getAttributeForm() != null) {
            dateTypeClient.setEncoding(getAttributeForm().getValueAsString(ModsConstants.ENCODING));
            String val = getAttributeForm().getValueAsString(ModsConstants.KEY_DATE);
            if (val != null && ClientUtils.toBoolean(val)) {
                dateTypeClient.setKeyDate(YesClient.YES);
            }
            dateTypeClient.setPoint(getAttributeForm().getValueAsString(ModsConstants.POINT));
            dateTypeClient.setQualifier(getAttributeForm().getValueAsString(ModsConstants.QUALIFIER));
            dateTypeClient.setValue(getAttributeForm().getValueAsString(dateName));
            if (otherDate)
                ((DateOtherTypeClient) dateTypeClient).setType(getAttributeForm()
                        .getValueAsString(ModsConstants.TYPE));
        }
        return dateTypeClient;
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
     * getSubelements()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValue
     * ()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValues
     * ()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
     * getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException("Mods");
    }
}
