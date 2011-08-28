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
import java.util.Arrays;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class PhysicalDescriptionHolder.
 */
public class PhysicalDescriptionHolder
        extends ListOfSimpleValuesHolder {

    /** The internet types. */
    private final ListOfSimpleValuesHolder internetTypes;

    /** The extents. */
    private final ListOfSimpleValuesHolder extents;

    /** The forms. */
    private final ListOfListOfSimpleValuesHolder forms;

    /** The notes. */
    private final ListOfListOfSimpleValuesHolder notes;

    /** The reformatting quality. */
    private final ListOfSimpleValuesHolder reformattingQuality;

    /** The digital origin. */
    private final ListOfSimpleValuesHolder digitalOrigin;

    /**
     * Instantiates a new physical description holder.
     */
    public PhysicalDescriptionHolder() {
        this.internetTypes = new ListOfSimpleValuesHolder();
        this.extents = new ListOfSimpleValuesHolder();
        this.forms = new ListOfListOfSimpleValuesHolder("form", ModsConstants.TYPE, ModsConstants.AUTHORITY);
        this.notes =
                new ListOfListOfSimpleValuesHolder(ModsConstants.NOTE,
                                                   ModsConstants.TYPE,
                                                   ModsConstants.DISPLAY_LABEL,
                                                   ModsConstants.XLINK,
                                                   ModsConstants.LANG,
                                                   ModsConstants.XML_LANG,
                                                   ModsConstants.SCRIPT,
                                                   ModsConstants.TRANSLITERATION);
        this.reformattingQuality = new ListOfSimpleValuesHolder();
        this.digitalOrigin = new ListOfSimpleValuesHolder();
    }

    /**
     * Gets the physical description.
     * 
     * @return the physical description
     */
    public PhysicalDescriptionTypeClient getPhysicalDescription() {
        PhysicalDescriptionTypeClient physicalDescriptionTypeClient = new PhysicalDescriptionTypeClient();

        if (getAttributeForm() != null) {
            physicalDescriptionTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            physicalDescriptionTypeClient.setXmlLang(getAttributeForm()
                    .getValueAsString(ModsConstants.XML_LANG));
            physicalDescriptionTypeClient.setTransliteration(getAttributeForm()
                    .getValueAsString(ModsConstants.TRANSLITERATION));
            physicalDescriptionTypeClient
                    .setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
        }
        physicalDescriptionTypeClient.setInternetMediaType(internetTypes.getValues());
        physicalDescriptionTypeClient.setExtent(extents.getValues());
        String value = null;
        if (reformattingQuality.getAttributeForm() != null) {
            value = reformattingQuality.getAttributeForm().getValueAsString(ModsConstants.REFORMATTING);
            if (value == null || "".equals(value.trim())) {
                value = null;
            }
        }
        physicalDescriptionTypeClient.setReformattingQuality(value == null ? null : Arrays.asList(value));
        if (digitalOrigin.getAttributeForm() != null) {
            value = digitalOrigin.getAttributeForm().getValueAsString(ModsConstants.DIGITAL_ORIGIN);
            if (value == null || "".equals(value.trim())) {
                value = null;
            }
        }

        physicalDescriptionTypeClient.setDigitalOrigin(value == null ? null : Arrays.asList(value));
        List<NoteTypeClient> list = null;
        List<List<String>> listOfValues = notes.getListOfList();
        boolean isNull = true;
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<NoteTypeClient>();
            for (List<String> values : listOfValues) {
                if (values != null) {
                    NoteTypeClient noteTypeClient = new NoteTypeClient();
                    noteTypeClient.setValue(values.get(0));
                    noteTypeClient.setAtType(values.get(1));
                    noteTypeClient.setDisplayLabel(values.get(2));
                    noteTypeClient.setXlink(values.get(3));
                    noteTypeClient.setLang(values.get(4));
                    noteTypeClient.setXmlLang(values.get(5));
                    noteTypeClient.setScript(values.get(6));
                    noteTypeClient.setTransliteration(values.get(7));
                    list.add(noteTypeClient);
                    isNull = false;
                }
            }
        }
        physicalDescriptionTypeClient.setNote(isNull ? null : list);

        List<StringPlusAuthorityPlusTypeClient> list2 = null;
        List<List<String>> listOfValues2 = forms.getListOfList();
        isNull = true;
        if (listOfValues2 != null && listOfValues2.size() != 0) {
            list2 = new ArrayList<StringPlusAuthorityPlusTypeClient>();
            for (List<String> values : listOfValues2) {
                if (values != null) {
                    StringPlusAuthorityPlusTypeClient val = new StringPlusAuthorityPlusTypeClient();
                    val.setValue(values.get(0));
                    val.setType(values.get(1));
                    val.setAuthority(values.get(2));
                    list2.add(val);
                    isNull = false;
                }
            }
        }
        physicalDescriptionTypeClient.setForm(isNull ? null : list2);
        return physicalDescriptionTypeClient;
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
     * Gets the publishers.
     * 
     * @return the publishers
     */
    public ListOfSimpleValuesHolder getPublishers() {
        return internetTypes;
    }

    /**
     * Gets the editions.
     * 
     * @return the editions
     */
    public ListOfSimpleValuesHolder getEditions() {
        return extents;
    }

    /**
     * Gets the frequencies.
     * 
     * @return the frequencies
     */
    public ListOfListOfSimpleValuesHolder getFrequencies() {
        return forms;
    }

    /**
     * Gets the internet types.
     * 
     * @return the internet types
     */
    public ListOfSimpleValuesHolder getInternetTypes() {
        return internetTypes;
    }

    /**
     * Gets the extents.
     * 
     * @return the extents
     */
    public ListOfSimpleValuesHolder getExtents() {
        return extents;
    }

    /**
     * Gets the forms.
     * 
     * @return the forms
     */
    public ListOfListOfSimpleValuesHolder getForms() {
        return forms;
    }

    /**
     * Gets the notes.
     * 
     * @return the notes
     */
    public ListOfListOfSimpleValuesHolder getNotes() {
        return notes;
    }

    /**
     * Gets the reformatting quality.
     * 
     * @return the reformatting quality
     */
    public ListOfSimpleValuesHolder getReformattingQuality() {
        return reformattingQuality;
    }

    /**
     * Gets the digital origin.
     * 
     * @return the digital origin
     */
    public ListOfSimpleValuesHolder getDigitalOrigin() {
        return digitalOrigin;
    }
}
