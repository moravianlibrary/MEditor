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

import cz.fi.muni.xkremser.editor.client.mods.CopyInformationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.EnumerationAndChronologyTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusDisplayLabelPlusTypeClient;

import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class CopyInformationHolder.
 */
public class CopyInformationHolder
        extends MetadataHolder {

    /** The sub locations. */
    private final ListOfSimpleValuesHolder subLocations;

    /** The shelf locators. */
    private final ListOfSimpleValuesHolder shelfLocators;

    /** The electronic locators. */
    private final ListOfSimpleValuesHolder electronicLocators;

    /** The form. */
    private final ListOfSimpleValuesHolder form;

    /** The enum chrono. */
    private final ListOfListOfSimpleValuesHolder enumChrono;

    /** The notes. */
    private final List<ListOfSimpleValuesHolder> notes;

    /**
     * Instantiates a new copy information holder.
     */
    public CopyInformationHolder() {
        this.shelfLocators = new ListOfSimpleValuesHolder();
        this.subLocations = new ListOfSimpleValuesHolder();
        this.electronicLocators = new ListOfSimpleValuesHolder();
        this.form = new ListOfSimpleValuesHolder();
        this.enumChrono =
                new ListOfListOfSimpleValuesHolder(ModsConstants.ENUM_CHRONO, ModsConstants.UNIT_TYPE);
        this.notes = new ArrayList<ListOfSimpleValuesHolder>();
    }

    /**
     * Gets the copy info.
     * 
     * @return the copy info
     */
    public CopyInformationTypeClient getCopyInfo() {
        CopyInformationTypeClient copyInformationTypeClient = new CopyInformationTypeClient();
        copyInformationTypeClient.setElectronicLocator(electronicLocators.getValues());
        copyInformationTypeClient.setShelfLocator(shelfLocators.getValues());
        copyInformationTypeClient.setSubLocation(subLocations.getValues());
        if ((form.getValues() != null && form.getValues().size() > 0 && form.getValues().get(0) != null)
                || form.getAttributeForm().getValueAsString(ModsConstants.AUTHORITY) != null) {
            StringPlusAuthorityClient val = new StringPlusAuthorityClient();
            val.setValue(form.getValues().get(1));
            val.setAuthority(form.getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
            copyInformationTypeClient.setForm(val);
        }
        List<EnumerationAndChronologyTypeClient> list = null;

        boolean isNull = true;
        List<List<String>> listOfValues = enumChrono.getListOfList();
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<EnumerationAndChronologyTypeClient>();
            for (List<String> values : listOfValues) {
                if (values != null) {
                    EnumerationAndChronologyTypeClient val = new EnumerationAndChronologyTypeClient();
                    val.setValue(values.get(0));
                    val.setUnitType(values.get(1));
                    list.add(val);
                    isNull = false;
                }
            }
        }
        copyInformationTypeClient.setEnumerationAndChronology(isNull ? null : list);

        // notes
        List<StringPlusDisplayLabelPlusTypeClient> noteList =
                new ArrayList<StringPlusDisplayLabelPlusTypeClient>(notes.size());
        for (ListOfSimpleValuesHolder holder : notes) {
            if (holder != null) {
                StringPlusDisplayLabelPlusTypeClient note = new StringPlusDisplayLabelPlusTypeClient();
                if (holder.getAttributeForm() != null) {
                    note.setAtType(holder.getAttributeForm().getValueAsString(ModsConstants.TYPE));
                    note.setDisplayLabel(holder.getAttributeForm()
                            .getValueAsString(ModsConstants.DISPLAY_LABEL));
                }
                if (holder.getAttributeForm2() != null)
                    note.setValue(holder.getAttributeForm2().getValueAsString(ModsConstants.NOTE));
                noteList.add(note);
            }

        }
        copyInformationTypeClient.setNote(noteList);

        if (BiblioModsUtils.hasOnlyNullFields(copyInformationTypeClient)) {
            return null;
        } else
            return copyInformationTypeClient;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements
     * ()
     */
    @Override
    public List<MetadataHolder> getSubelements() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
     */
    @Override
    public List<String> getValues() {
        throw new UnsupportedOperationException("Mods");
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
     */
    @Override
    public List<String> getAttributes() {
        throw new UnsupportedOperationException("Mods");
    }

    /**
     * Gets the sub locations.
     * 
     * @return the sub locations
     */
    public ListOfSimpleValuesHolder getSubLocations() {
        return subLocations;
    }

    /**
     * Gets the shelf locators.
     * 
     * @return the shelf locators
     */
    public ListOfSimpleValuesHolder getShelfLocators() {
        return shelfLocators;
    }

    /**
     * Gets the electronic locators.
     * 
     * @return the electronic locators
     */
    public ListOfSimpleValuesHolder getElectronicLocators() {
        return electronicLocators;
    }

    /**
     * Gets the form.
     * 
     * @return the form
     */
    public ListOfSimpleValuesHolder getForm() {
        return form;
    }

    /**
     * Gets the enum chrono.
     * 
     * @return the enum chrono
     */
    public ListOfListOfSimpleValuesHolder getEnumChrono() {
        return enumChrono;
    }

    /**
     * Gets the notes.
     * 
     * @return the notes
     */
    public List<ListOfSimpleValuesHolder> getNotes() {
        return notes;
    }

}
