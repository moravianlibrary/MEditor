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

import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;

import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TableOfContentsHolder.
 */
public class TableOfContentsHolder
        extends ListOfSimpleValuesHolder {

    /**
     * Gets the table of contents.
     * 
     * @return the table of contents
     */
    public TableOfContentsTypeClient getTableOfContents() {
        TableOfContentsTypeClient tocClient = new TableOfContentsTypeClient();
        if (getAttributeForm() != null) {
            tocClient.setAtType(getAttributeForm().getValueAsString(ModsConstants.TYPE));
            tocClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            tocClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
            tocClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
            tocClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
            tocClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
            tocClient.setDisplayLabel(getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
        }
        tocClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.TOC));

        if (BiblioModsUtils.hasOnlyNullFields(tocClient)) {
            return null;
        } else
            return tocClient;
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
