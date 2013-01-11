/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.client.view.other;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_ARTICLE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_CHAPTER_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_PICTURE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_LEVEL_NAMES;
import cz.mzk.editor.client.view.window.EditorDateItem;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.DigitalObjectModel.TopLevelObjectModel;

/**
 * @author Matous Jobanek
 * @version Apr 15, 2012
 */
public abstract class NewDigitalObjectItemManager
        extends VLayout {

    private final LangConstants lang;

    /**
     * @param lang
     */
    public NewDigitalObjectItemManager(LangConstants lang) {
        super();
        this.lang = lang;
    }

    protected abstract IButton getAddNoteButton();

    protected abstract TextItem getNameOrTitleItem();

    protected abstract TextItem getSubtitleItem();

    protected abstract TextItem getPartNumberItem();

    protected abstract TextItem getXOfSequenceItem();

    protected abstract EditorDateItem getDateIssuedItem();

    protected abstract TextItem getPartNameItem();

    protected abstract SelectItem getTypeItem();

    protected abstract SelectItem getLevelNamesItem();

    protected abstract TextItem getXOfLevelNamesItem();

    protected abstract TextItem getRelatedItemPartNumber();

    protected abstract TextItem getRelatedItemPartName();

    protected abstract DigitalObjectModel getCurrentModel();

    public String getNote() {
        String note = getAddNoteButton().getPrompt();
        getAddNoteButton().setPrompt("");
        getAddNoteButton().setTitle(lang.addNote());
        return note;
    }

    /**
     * @return the nameOrTitle
     */
    public String getNameOrTitle() {
        return getNameOrTitleItem().getValueAsString();
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return getSubtitleItem().getValueAsString();
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return getPartNumberItem().getValueAsString();
    }

    /**
     * @return the xOfSequence
     */
    public String getxOfSequence() {
        return getXOfSequenceItem().getValueAsString();
    }

    /**
     * @return the dateIssued
     */
    public String getDateIssued() {
        return getDateIssuedItem().getEditorDate();
    }

    /**
     * @return the partName
     */
    public String getPartName() {
        return getPartNameItem().getValueAsString();
    }

    /**
     * @return the partName
     */
    public String getRelatedPartName() {
        return getRelatedItemPartName().getValueAsString();
    }

    /**
     * @return the partNumber
     */
    public String getRelatedPartNumber() {
        return getRelatedItemPartNumber().getValueAsString();
    }


    /**
     * @return the type
     */
    public String getType(DigitalObjectModel model, String levelName) {
        String typeString = "";

        if (model == DigitalObjectModel.PAGE) {
            typeString = getTypeItem().getValueAsString();

        } else if (model == DigitalObjectModel.PERIODICALITEM) {
            if (PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue().equals(getLevelNamesItem()
                    .getValueAsString())) {
                String perItemType = PERIODICAL_ITEM_GENRE_TYPES.MAP.get(getTypeItem().getValueAsString());

                if (PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString()
                        .equals(getTypeItem().getValueAsString())) {
                    typeString =
                            perItemType.substring(0, perItemType.length() - 1)
                                    + getXOfSequenceItem().getValueAsString();
                } else {
                    typeString = perItemType;
                }
            }

        } else if (model == DigitalObjectModel.INTERNALPART) {
            if (levelName.substring(0, levelName.indexOf("_", 6))
                    .equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                typeString = INTERNAL_PART_ARTICLE_GENRE_TYPES.MAP.get(getTypeItem().getValueAsString());

            } else if (levelName.substring(0, levelName.indexOf("_", 6))
                    .equals(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.toString())) {
                typeString = INTERNAL_PART_CHAPTER_GENRE_TYPES.MAP.get(getTypeItem().getValueAsString());

            } else {
                typeString = INTERNAL_PART_PICTURE_GENRE_TYPES.MAP.get(getTypeItem().getValueAsString());
            }
        }
        return typeString;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() {
        String levelName = getLevelNamesItem().getValueAsString();

        if (!PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue().equals(levelName))
            return levelName.substring(0, levelName.length() - 4) + getFormatedXOfLevelNames();

        return levelName;
    }

    /**
     * @return the xOfLevelNames
     */
    public String getxOfLevelNames() {
        return getXOfLevelNamesItem().getValueAsString();
    }

    protected String getFormatedXOfLevelNames() {
        NumberFormat formatter = NumberFormat.getFormat("0000");
        return formatter.format(Integer.parseInt(getxOfLevelNames()));
    }

    /**
     * @return
     */
    public String verify() {
        DigitalObjectModel model = getCurrentModel();

        // TODO-MR: recording, potrebuju nejakou kontrolu?
        if (model == DigitalObjectModel.TRACK || model == DigitalObjectModel.SOUND_UNIT) {
            return null;
        }

        if (model == DigitalObjectModel.INTERNALPART || model == DigitalObjectModel.PERIODICALITEM
                || model == DigitalObjectModel.MONOGRAPHUNIT) {
            if (getLevelNamesItem().getValueAsString() == null
                    || "".equals(getLevelNamesItem().getValueAsString()))
                return lang.textBox() + " " + lang.levelName() + " " + lang.notEmpty();
        }

        if (model.getTopLevelType() != TopLevelObjectModel.PERIODICAL
                && model.getTopLevelType() != TopLevelObjectModel.MONOGRAPH) {
            if (model == DigitalObjectModel.PERIODICALITEM) {
                String perItemType = PERIODICAL_ITEM_GENRE_TYPES.MAP.get(getTypeItem().getValueAsString());
                if (PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString().equals(perItemType)) {
                    if (getxOfSequence() == null || "".equals(getxOfSequence()))
                        return lang.textBox() + " " + "X" + " " + lang.notEmpty();
                    if (!getxOfSequence().matches(Constants.ONLY_NUMBERS)) return getOnlyNumbersHint("X");
                }
            }
            String levelName = getLevelNamesItem().getValueAsString();
            if (levelName != null && !"".equals(levelName)
                    && !PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue().equals(levelName)) {
                if (getxOfLevelNames() == null || "".equals(getxOfLevelNames()))
                    return lang.textBox() + " " + "XXXX" + " " + lang.notEmpty();
                if (getxOfLevelNames() != null && !getxOfLevelNames().matches(Constants.ONLY_NUMBERS))
                    return getOnlyNumbersHint("XXXX");
            }

            if (model != DigitalObjectModel.PAGE && model != DigitalObjectModel.PERIODICALVOLUME) {
                if (getPartNumber() == null || "".equals(getPartNumber()))
                    return lang.textBox() + " " + lang.partNumber() + " " + lang.notEmpty();
                if (getPartNumber() != null && !getPartNumber().matches(Constants.ONLY_NUMBERS)
                        && !getPartNumber().matches(Constants.EXTENT_OF_NUMBERS))
                    return getOnlyNumbersHint(lang.partNumber());

            } else if (model == DigitalObjectModel.PERIODICALVOLUME) {
                if ("".equals(getDateIssued()))
                    return lang.textBox() + " " + lang.dateIssued() + " " + lang.notEmpty();
            }
            String message = getDateIssuedItem().verify(lang, model);
            if (message != null && !"".equals(message)) return message;

        } else {
            if (getNameOrTitle() == null || "".equals(getNameOrTitle()))
                return lang.textBox() + " " + lang.dcTitle() + " " + lang.notEmpty();
        }
        return null;
    }

    protected String getOnlyNumbersHint(String textItemName) {
        return lang.textBox() + " " + textItemName + " " + lang.onlyNum();
    }

    protected String getDateFormatHint(DigitalObjectModel model) {
        return getDateIssuedItem().getDateFormatHint(lang, model);
    }
}
