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

import cz.fi.muni.xkremser.editor.client.mods.IdentifierTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class IdentifierHolder.
 */
public class IdentifierHolder
        extends ListOfListOfSimpleValuesHolder {

    /**
     * Instantiates a new identifier holder.
     */
    public IdentifierHolder() {
        this("identifier",
             ModsConstants.TYPE,
             ModsConstants.DISPLAY_LABEL,
             ModsConstants.LANG,
             ModsConstants.XML_LANG,
             ModsConstants.TRANSLITERATION,
             ModsConstants.SCRIPT,
             ModsConstants.INVALID);
    }

    /**
     * Instantiates a new identifier holder.
     * 
     * @param keys
     *        the keys
     */
    public IdentifierHolder(String... keys) {
        super(keys);
    }

    /**
     * Gets the identifier.
     * 
     * @return the identifier
     */
    public List<IdentifierTypeClient> getIdentifier() {
        List<IdentifierTypeClient> list = null;
        List<List<String>> listOfValues = getListOfList();
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<IdentifierTypeClient>();
            for (List<String> values : listOfValues) {
                if (values != null) {
                    IdentifierTypeClient val = new IdentifierTypeClient();
                    val.setValue(values.get(0));
                    val.setType(values.get(1));
                    val.setDisplayLabel(values.get(2));
                    val.setLang(values.get(3));
                    val.setXmlLang(values.get(4));
                    val.setTransliteration(values.get(5));
                    val.setScript(values.get(6));
                    if (ClientUtils.toBoolean(values.get(7))) val.setInvalid(YesClient.YES);
                    list.add(val);
                }
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
     * #getSubelements()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
     * #getValue()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
     * #getValues()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
     * #getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException("Mods");
    }

}
