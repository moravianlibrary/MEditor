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

import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient.RecordIdentifierClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusLanguageClient;

// TODO: Auto-generated Javadoc
/**
 * The Class RecordInfoHolder.
 */
public class RecordInfoHolder
        extends ListOfSimpleValuesHolder {

    /** The record origin. */
    private final ListOfSimpleValuesHolder recordOrigin;

    /** The record content source. */
    private final ListOfListOfSimpleValuesHolder recordContentSource;

    /** The description standard. */
    private final ListOfListOfSimpleValuesHolder descriptionStandard;

    /** The creation date. */
    private final List<DateHolder> creationDate;

    /** The change date. */
    private final List<DateHolder> changeDate;

    /** The language. */
    private final List<LanguageHolder> language;

    /** The record identifier. */
    private final ListOfListOfSimpleValuesHolder recordIdentifier;

    /**
     * Instantiates a new record info holder.
     */
    public RecordInfoHolder() {
        this.recordOrigin = new ListOfSimpleValuesHolder();
        this.recordContentSource =
                new ListOfListOfSimpleValuesHolder(ModsConstants.RECORD_CONTENT_SOURCE,
                                                   ModsConstants.AUTHORITY,
                                                   ModsConstants.LANG,
                                                   ModsConstants.XML_LANG,
                                                   ModsConstants.SCRIPT,
                                                   ModsConstants.TRANSLITERATION);
        this.descriptionStandard =
                new ListOfListOfSimpleValuesHolder(ModsConstants.DESCRIPTION_STANDARD,
                                                   ModsConstants.AUTHORITY);
        this.creationDate = new ArrayList<DateHolder>();
        this.changeDate = new ArrayList<DateHolder>();
        this.language = new ArrayList<LanguageHolder>();
        this.recordIdentifier =
                new ListOfListOfSimpleValuesHolder(ModsConstants.RECORD_IDENTIFIER, ModsConstants.SOURCE);
    }

    /**
     * Gets the record info.
     * 
     * @return the record info
     */
    public RecordInfoTypeClient getRecordInfo() {
        RecordInfoTypeClient recordInfoTypeClient = new RecordInfoTypeClient();

        if (getAttributeForm() != null) {
            recordInfoTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
            recordInfoTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
            recordInfoTypeClient.setTransliteration(getAttributeForm()
                    .getValueAsString(ModsConstants.TRANSLITERATION));
            recordInfoTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
        }

        List<RecordIdentifierClient> list = null;
        List<List<String>> listOfValues = recordIdentifier.getListOfList();
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<RecordIdentifierClient>();
            for (List<String> values : listOfValues) {
                RecordIdentifierClient val = new RecordIdentifierClient();
                val.setValue(values.get(0));
                val.setSource(values.get(1));
                list.add(val);
            }
        }
        recordInfoTypeClient.setRecordIdentifier(list);

        recordInfoTypeClient.setRecordChangeDate(getDatesFromHolders(changeDate));
        recordInfoTypeClient.setRecordCreationDate(getDatesFromHolders(creationDate));
        recordInfoTypeClient.setRecordOrigin(recordOrigin.getValues());

        // language
        List<LanguageTypeClient> value2 = new ArrayList<LanguageTypeClient>(language.size());
        for (LanguageHolder holder : language) {
            value2.add(holder.getLanguage());
        }
        recordInfoTypeClient.setLanguageOfCataloging(value2);

        List<StringPlusAuthorityClient> list1 = null;
        List<List<String>> listOfValues1 = descriptionStandard.getListOfList();
        if (listOfValues1 != null && listOfValues1.size() != 0) {
            list1 = new ArrayList<StringPlusAuthorityClient>();
            for (List<String> values : listOfValues1) {
                StringPlusAuthorityClient val = new StringPlusAuthorityClient();
                val.setValue(values.get(0));
                val.setAuthority(values.get(1));
                list1.add(val);
            }
        }
        recordInfoTypeClient.setDescriptionStandard(list1);

        List<StringPlusAuthorityPlusLanguageClient> list2 = null;
        List<List<String>> listOfValues2 = recordContentSource.getListOfList();
        if (listOfValues2 != null && listOfValues2.size() != 0) {
            list2 = new ArrayList<StringPlusAuthorityPlusLanguageClient>();
            for (List<String> values : listOfValues2) {
                StringPlusAuthorityPlusLanguageClient val = new StringPlusAuthorityPlusLanguageClient();
                val.setValue(values.get(0));
                val.setAuthority(values.get(1));
                val.setLang(values.get(2));
                val.setXmlLang(values.get(3));
                val.setScript(values.get(4));
                val.setTransliteration(values.get(5));
                list2.add(val);
            }
        }
        recordInfoTypeClient.setRecordContentSource(list2);
        return recordInfoTypeClient;
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
     * Gets the record origin.
     * 
     * @return the record origin
     */
    public ListOfSimpleValuesHolder getRecordOrigin() {
        return recordOrigin;
    }

    /**
     * Gets the record content source.
     * 
     * @return the record content source
     */
    public ListOfListOfSimpleValuesHolder getRecordContentSource() {
        return recordContentSource;
    }

    /**
     * Gets the description standard.
     * 
     * @return the description standard
     */
    public ListOfListOfSimpleValuesHolder getDescriptionStandard() {
        return descriptionStandard;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creation date
     */
    public List<DateHolder> getCreationDate() {
        return creationDate;
    }

    /**
     * Gets the change date.
     * 
     * @return the change date
     */
    public List<DateHolder> getChangeDate() {
        return changeDate;
    }

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public List<LanguageHolder> getLanguage() {
        return language;
    }

    /**
     * Gets the record identifier.
     * 
     * @return the record identifier
     */
    public ListOfListOfSimpleValuesHolder getRecordIdentifier() {
        return recordIdentifier;
    }

    /**
     * Gets the dates from holders.
     * 
     * @param holders
     *        the holders
     * @return the dates from holders
     */
    private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
        List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
        for (DateHolder holder : holders) {
            dates.add(holder.getDate());
        }
        return dates;
    }
}
