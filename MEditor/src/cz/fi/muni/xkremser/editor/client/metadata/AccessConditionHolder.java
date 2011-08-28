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

import cz.fi.muni.xkremser.editor.client.mods.AccessConditionTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class AccessConditionHolder.
 */
public class AccessConditionHolder
        extends ListOfSimpleValuesHolder {

    /**
     * Gets the access condition.
     * 
     * @return the access condition
     */
    public AccessConditionTypeClient getAccessCondition() {
        AccessConditionTypeClient value = new AccessConditionTypeClient();
        if (getAttributeForm() != null) {
            value.setType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
            value.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            value.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
            value.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
            value.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
            value.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
            value.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
        }
        if (getAttributeForm2() != null
                && getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION) != null
                && !"".equals(getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION)))
            value.setContent(getAttributeForm2().getValueAsString(ModsConstants.ACCESS_CONDITION));
        return value;
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
