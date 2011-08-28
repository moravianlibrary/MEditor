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

import com.smartgwt.client.widgets.form.DynamicForm;

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient.LanguageTermClient;

// TODO: Auto-generated Javadoc
/**
 * The Class LanguageHolder.
 */
public class LanguageHolder
        extends ListOfSimpleValuesHolder {

    /** The attribute form. */
    private DynamicForm attributeForm;

    /** The lang terms. */
    private final ListOfListOfSimpleValuesHolder langTerms;

    /**
     * Instantiates a new language holder.
     */
    public LanguageHolder() {
        this.langTerms =
                new ListOfListOfSimpleValuesHolder("language_term",
                                                   ModsConstants.TYPE,
                                                   ModsConstants.AUTHORITY);
    }

    /**
     * Gets the language.
     * 
     * @return the language
     */
    public LanguageTypeClient getLanguage() {
        LanguageTypeClient languageTypeClient = new LanguageTypeClient();
        if (getAttributeForm() != null) {
            languageTypeClient.setObjectPart(getAttributeForm().getValueAsString(ModsConstants.OBJECT_PART));
        }

        List<LanguageTermClient> list = null;
        List<List<String>> listOfValues = langTerms.getListOfList();
        boolean isNull = true;
        if (listOfValues != null && listOfValues.size() != 0) {
            list = new ArrayList<LanguageTermClient>();
            for (List<String> values : listOfValues) {
                if (values != null) {
                    LanguageTermClient languageTermClient = new LanguageTermClient();
                    languageTermClient.setValue(values.get(0));
                    languageTermClient.setType(CodeOrTextClient.fromValue(values.get(1)));
                    languageTermClient.setAuthority(values.get(2));
                    list.add(languageTermClient);
                    isNull = false;
                }
            }
        }
        languageTypeClient.setLanguageTerm(isNull ? null : list);
        return languageTypeClient;
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

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributeForm
     * ()
     */
    @Override
    public DynamicForm getAttributeForm() {
        return attributeForm;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#setAttributeForm
     * (com.smartgwt.client.widgets.form.DynamicForm)
     */
    @Override
    public void setAttributeForm(DynamicForm attributeForm) {
        this.attributeForm = attributeForm;
    }

    /**
     * Gets the lang terms.
     * 
     * @return the lang terms
     */
    public ListOfListOfSimpleValuesHolder getLangTerms() {
        return langTerms;
    }

}
