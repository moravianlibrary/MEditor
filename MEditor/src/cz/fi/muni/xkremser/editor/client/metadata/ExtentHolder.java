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

import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtentHolder.
 */
public class ExtentHolder
        extends ListOfSimpleValuesHolder {

    /** The start. */
    private final ListOfSimpleValuesHolder start;

    /** The end. */
    private final ListOfSimpleValuesHolder end;

    /** The total. */
    private final ListOfSimpleValuesHolder total;

    /** The list. */
    private final ListOfSimpleValuesHolder list;

    /**
     * Instantiates a new extent holder.
     */
    public ExtentHolder() {
        this.start = new ListOfSimpleValuesHolder();
        this.end = new ListOfSimpleValuesHolder();
        this.total = new ListOfSimpleValuesHolder();
        this.list = new ListOfSimpleValuesHolder();
    }

    /**
     * Gets the extent.
     * 
     * @return the extent
     */
    public ExtentTypeClient getExtent() {
        ExtentTypeClient extentTypeClient = new ExtentTypeClient();
        if (getAttributeForm() != null) {
            extentTypeClient.setUnit(getAttributeForm().getValueAsString(ModsConstants.UNIT));
        }
        extentTypeClient.setStart(safeGet(start, ModsConstants.START));
        extentTypeClient.setEnd(safeGet(end, ModsConstants.END));
        extentTypeClient.setTotal(safeGet(total, ModsConstants.TOTAL));
        extentTypeClient.setList(safeGet(list, ModsConstants.LIST));

        return extentTypeClient;
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

    /**
     * Safe get.
     * 
     * @param holder
     *        the holder
     * @param key
     *        the key
     * @return the string
     */
    private String safeGet(ListOfSimpleValuesHolder holder, String key) {
        if ((holder.getValues() != null && holder.getValues().size() > 0 && holder.getValues().get(0) != null)
                || holder.getAttributeForm().getValueAsString(key) != null)
            return holder.getAttributeForm().getValueAsString(key);
        else
            return null;
    }

    /**
     * Gets the start.
     * 
     * @return the start
     */
    public ListOfSimpleValuesHolder getStart() {
        return start;
    }

    /**
     * Gets the end.
     * 
     * @return the end
     */
    public ListOfSimpleValuesHolder getEnd() {
        return end;
    }

    /**
     * Gets the total.
     * 
     * @return the total
     */
    public ListOfSimpleValuesHolder getTotal() {
        return total;
    }

    /**
     * Gets the list.
     * 
     * @return the list
     */
    public ListOfSimpleValuesHolder getList() {
        return list;
    }

}
