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

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfListOfSimpleValuesHolder.
 */
public class ListOfListOfSimpleValuesHolder
        extends MetadataHolder {

    /** The keys. */
    private final String[] keys;

    /**
     * Instantiates a new list of list of simple values holder.
     * 
     * @param keys
     *        the keys
     */
    public ListOfListOfSimpleValuesHolder(String... keys) {
        this.keys = keys;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements
     * ()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the list of list.
     * 
     * @return the list of list
     */
    public List<List<String>> getListOfList() {
        if (this.layout == null) {
            throw new IllegalStateException("bind the holder to some layout with data first");
        }
        Canvas[] canvases = this.layout.getMembers();
        List<List<String>> values = new ArrayList<List<String>>(canvases.length);
        int i = 0;
        boolean isOuterListNull = true;
        for (Canvas canvas : canvases) {
            values.add(new ArrayList<String>());
            DynamicForm form = (DynamicForm) canvas;
            boolean isInnerListNull = true;
            for (String key : keys) {
                String value = form.getValueAsString(key);
                if (value != null) {
                    isInnerListNull = false;
                    values.get(i).add(value.trim());
                } else {
                    values.get(i).add(null);
                }
            }
            if (isInnerListNull) {
                values.set(i, null);
            } else {
                isOuterListNull = false;
            }
            i++;
        }
        return isOuterListNull ? null : values;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException();
    }

}
