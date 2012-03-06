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

import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractHolder.
 */
public class AbstractHolder
        extends ListOfSimpleValuesHolder {

    /**
     * Gets the abstract.
     * 
     * @return the abstract
     */
    public AbstractTypeClient getAbstract() {
        AbstractTypeClient abstractTypeClient = null;
        if (getAttributeForm() != null) {
            abstractTypeClient = new AbstractTypeClient();
            abstractTypeClient.setAtType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
            abstractTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            abstractTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
            abstractTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
            abstractTypeClient.setTransliteration(getAttributeForm()
                    .getValueAsString(ModsConstants.TRANSLITERATION));
            abstractTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
            abstractTypeClient.setDisplayLabel(getAttributeForm()
                    .getValueAsString(ModsConstants.DISPLAY_LABEL));
        }
        if (getAttributeForm2() != null) {
            if (abstractTypeClient == null) {
                abstractTypeClient = new AbstractTypeClient();
            }
            abstractTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.ABSTRACT));
        }
        return abstractTypeClient;
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
