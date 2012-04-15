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

package cz.mzk.editor.client.view.window;

import java.util.LinkedHashMap;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_ARTICLE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_CHAPTER_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_PICTURE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.MONOGRAPH_UNIT_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.PAGE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_LEVEL_NAMES;
import cz.mzk.editor.client.view.other.LabelAndModelConverter;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class NewObjectBasicInfoWindow
        extends UniversalWindow {

    /**
     * The DynamicForm which automatically sets items.
     */
    private static final class MyDynamicForm
            extends DynamicForm {

        /**
         * Items are used for set items in this DynamicForm
         * 
         * @param items
         */
        MyDynamicForm(FormItem... items) {
            this.setItems(items);
            setExtraSpace(2);
        }
    }

    //    for (FormItem item : items) {
    //        DynamicForm dynamicForm = new DynamicForm();
    //        dynamicForm.setItems(item);
    //        addMember(dynamicForm);
    //    }
    //    setExtraSpace(15);

    private TextItem nameOrTitle;

    private TextItem subtitle;

    private TextItem partNumber;

    private TextItem partName;

    private TextItem xOfSequence;

    private TextItem xOfLevelNames;

    private TextItem dateIssued;

    private SelectItem type;

    private SelectItem levelNames;

    private HLayout levelNamesLayout;

    private HLayout partNumAndTypeLayout;

    private VLayout otherLayout;

    private VLayout mainLayout;

    private final LangConstants lang;

    private TextAreaItem noteItem = null;

    /**
     * @param structureTreeGrid
     * @param height
     * @param width
     * @param title
     */

    public NewObjectBasicInfoWindow(final ListGridRecord record,
                                    LangConstants lang,
                                    EventBus eventBus,
                                    boolean isPeriodical) {
        super(120, 440, lang.menuEdit()
                + " "
                + LabelAndModelConverter.getLabelFromModel()
                        .get(record.getAttributeAsString(Constants.ATTR_MODEL_ID)) + ": "
                + record.getAttributeAsString(Constants.ATTR_NAME), eventBus, 10);
        this.lang = lang;
        setAlign(Alignment.LEFT);

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setWidth100();
        buttonsLayout.setAlign(Alignment.RIGHT);
        buttonsLayout.setMargin(10);

        Button cancelButton = new Button(lang.cancel());
        Button okButton = new Button("OK");

        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        cancelButton.setExtraSpace(5);

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doSaveAction(record);
            }
        });

        prepareEdit();
        setEditLayout(record, isPeriodical);

        buttonsLayout.addMember(cancelButton);
        buttonsLayout.addMember(okButton);
        mainLayout.addMember(buttonsLayout);
        addItem(mainLayout);

        centerInPage();
        show();
        focus();
    }

    public VLayout prepareEdit() {
        //        ATTR_PICTURE_OR_UUID        uuid      uuid        uuid        picture     uuid   
        //        ATTR_MODEL_ID               modelId   modelid     modelid     modelid     modelid 

        //                                    vol       item        int p       page        int p       mon un

        //        ATTR_NAME                             part name   title       name        title       part name
        //        ATTR_TYPE                             genre       genre       spec type   genre    
        //        ATTR_DATE_OR_INT_PART_NAME  date      date        part name               part name   date
        //        ATTR_NOTE_OR_INT_SUBTITLE   note      note        subtitle                subtitle    note
        //        ATTR_PART_NUMBER_OR_ALTO    part num  part num    part num    alto        part num    part num
        //        ATTR_ADITIONAL_INFO_OR_OCR            item/suppl  art/pic     ocr         pict/chapt  suppl

        nameOrTitle = new TextItem("name", lang.name());
        nameOrTitle.setWidth(300);

        subtitle = new TextItem("subtitle", lang.subtitle());
        subtitle.setWidth(300);

        levelNames = new SelectItem("levelNames", lang.levelName());
        levelNames.setWidth(170);

        xOfLevelNames = new TextItem("xOfLevelNames", "XXXX");
        xOfLevelNames.setWidth(60);
        xOfLevelNames.setPrompt(getOnlyNumbersHint("XXXX"));

        levelNamesLayout = new HLayout(2);
        levelNamesLayout.addMember(new MyDynamicForm(levelNames));
        levelNamesLayout.addMember(new MyDynamicForm(xOfLevelNames));
        levelNamesLayout.setLayoutAlign(Alignment.LEFT);
        levelNamesLayout.setAlign(Alignment.RIGHT);
        levelNamesLayout.setWidth(375);

        dateIssued = new TextItem("dateIssued", lang.dateIssued());
        dateIssued.setWidth(300);

        partNumber = new TextItem("partNumber", lang.partNumber());
        partNumber.setWidth(100);
        partNumber.setPrompt(getOnlyNumbersHint(lang.partNumber()));

        partName = new TextItem("partName", lang.intPartPartName());
        partName.setWidth(300);

        type = new SelectItem("type", lang.dcType());
        type.setWidth(168);

        xOfSequence = new TextItem("xOfSequence", "X");
        xOfSequence.setWidth(80);
        xOfSequence.setPrompt(getOnlyNumbersHint("X"));

        noteItem = new TextAreaItem("note", lang.note());
        noteItem.setWidth(300);
        noteItem.setHeight(60);

        partNumAndTypeLayout = new HLayout(3);
        partNumAndTypeLayout.addMember(new MyDynamicForm(type));
        partNumAndTypeLayout.addMember(new MyDynamicForm(xOfSequence));
        partNumAndTypeLayout.setLayoutAlign(Alignment.LEFT);
        partNumAndTypeLayout.setAlign(Alignment.RIGHT);
        partNumAndTypeLayout.setWidth(375);

        otherLayout = new VLayout();

        mainLayout = new VLayout();
        mainLayout.setPadding(5);
        mainLayout.setWidth100();
        mainLayout.setExtraSpace(10);
        mainLayout.addMember(otherLayout);
        mainLayout.setRight(10);
        return mainLayout;
    }

    public void setEditLayout(Record record, boolean isPeriodical) {
        if (mainLayout.contains(otherLayout)) {
            mainLayout.removeMember(otherLayout);
        }
        otherLayout = new VLayout();
        DigitalObjectModel model =
                DigitalObjectModel.parseString(record.getAttribute(Constants.ATTR_MODEL_ID));
        if (model != null) {

            xOfLevelNames.show();

            switch (model) {
                case PERIODICALVOLUME:
                    setCreatePeriodicalVolume(record);
                    break;
                case PERIODICALITEM:
                    setCreatePeriodicalItem(record);
                    break;

                case INTERNALPART:
                    setCreateInternalPart(record, isPeriodical);
                    break;

                case MONOGRAPHUNIT:
                    setCreateMonographUnit(record);
                    break;

                case PAGE:
                    setCreatePage(record);
                    break;

                default:
                    setCreateDefault();
                    break;
            }
        } else {
            setCreateDefault();
        }
        mainLayout.addMember(otherLayout, 0);
    }

    /**
     * 
     */
    private void setCreatePage(Record record) {
        nameOrTitle.setTitle(lang.dcTitle());
        nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));
        otherLayout.addMember(new MyDynamicForm(nameOrTitle));

        final LinkedHashMap<String, String> valueMapPage = new LinkedHashMap<String, String>(PAGE_TYPES.MAP);
        type.setValueMap(valueMapPage);
        type.setValue(record.getAttribute(Constants.ATTR_TYPE));
        otherLayout.addMember(new MyDynamicForm(type));
        setHeight(150);
    }

    /**
     * 
     */
    private void setCreateDefault() {
    }

    /**
     * 
     */
    private void setCreateMonographUnit(Record record) {
        levelNames.setValueMap(MONOGRAPH_UNIT_LEVEL_NAMES.MODS_SUPPL.getValue());
        levelNames.setDefaultValue(MONOGRAPH_UNIT_LEVEL_NAMES.MODS_SUPPL.getValue());
        levelNames.redraw();
        setDefaultXOfLevelNames(record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR));

        nameOrTitle.setTitle(lang.supplementName());
        nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));

        partNumber.setValue(record.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO));

        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.MONOGRAPHUNIT));
        dateIssued.setValue(record.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME));

        noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE));

        otherLayout.addMember(levelNamesLayout);
        otherLayout.addMember(new MyDynamicForm(nameOrTitle, partNumber, dateIssued, noteItem));
        setHeight(270);
    }

    /**
     * 
     */
    private void setCreateInternalPart(Record record, boolean isPeriodical) {
        String levelName = record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
        String substringLevelName = levelName.substring(0, levelName.indexOf("_", 6));

        if (isPeriodical) {
            levelNames.setValueMap(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue(),
                                   Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICT.getValue());

            if (substringLevelName.equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue());
            } else {
                levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICT.getValue());
            }

        } else {
            levelNames.setValueMap(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue(),
                                   INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.getValue());

            if (substringLevelName.equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue());
            } else {
                levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.getValue());
            }
        }
        levelNames.redraw();
        setDefaultXOfLevelNames(levelName);

        otherLayout.addMember(levelNamesLayout);

        nameOrTitle.setTitle(lang.intPartName());
        nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));

        subtitle.setValue(record.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE));

        partName.setValue(record.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME));

        partNumber.setValue(record.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO));

        otherLayout.addMember(new MyDynamicForm(nameOrTitle, subtitle, partName, partNumber));

        final LinkedHashMap<String, String> valueMapArt =
                new LinkedHashMap<String, String>(INTERNAL_PART_ARTICLE_GENRE_TYPES.MAP);
        final LinkedHashMap<String, String> valueMapPicture =
                new LinkedHashMap<String, String>(INTERNAL_PART_PICTURE_GENRE_TYPES.MAP);
        final LinkedHashMap<String, String> valueMapChapter =
                new LinkedHashMap<String, String>(INTERNAL_PART_CHAPTER_GENRE_TYPES.MAP);

        type.setValueMap(valueMapArt);
        type.setValue(record.getAttribute(Constants.ATTR_TYPE));
        xOfSequence.hide();
        partNumAndTypeLayout.setExtraSpace(10);
        otherLayout.addMember(partNumAndTypeLayout);

        levelNames.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue().equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue())) {
                    type.setValueMap(valueMapArt);
                } else if (event.getValue().equals(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue())) {
                    type.setValueMap(valueMapPicture);
                } else {
                    type.setValueMap(valueMapChapter);
                }
                type.setValue("");
            }
        });
        setHeight(270);
    }

    /**
     * 
     */
    private void setCreatePeriodicalItem(Record record) {
        levelNames.setValueMap(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue(),
                               PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue());
        String levelName = record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
        boolean isIssue =
                levelName.substring(0, levelName.indexOf("_", 6))
                        .equals(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.toString());
        if (isIssue) {
            xOfLevelNames.hide();
            levelNames.setDefaultValue(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue());
        } else {
            levelNames.setDefaultValue(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue());
            setDefaultXOfLevelNames(levelName);
        }

        levelNames.redraw();

        otherLayout.addMember(levelNamesLayout);

        nameOrTitle.setTitle(lang.editionName());
        nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));

        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.PERIODICALITEM));
        dateIssued.setValue(record.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME));

        partNumber.setValue(record.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO));

        otherLayout.addMember(new MyDynamicForm(nameOrTitle, dateIssued, noteItem, partNumber));

        final LinkedHashMap<String, String> valueMap =
                new LinkedHashMap<String, String>(PERIODICAL_ITEM_GENRE_TYPES.MAP);
        type.setValueMap(valueMap);
        if (isIssue) {
            String defaultType = record.getAttribute(Constants.ATTR_TYPE);
            String sequenceX =
                    PERIODICAL_ITEM_GENRE_TYPES.MAP.get(PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString());
            if (defaultType != null
                    && defaultType.contains("_")
                    && defaultType.substring(0, defaultType.indexOf("_"))
                            .equals(sequenceX.substring(0, sequenceX.indexOf("_")))) {
                type.setValue(sequenceX);
                xOfSequence.show();
                xOfSequence.setDefaultValue(defaultType.substring(defaultType.indexOf("_") + 1,
                                                                  defaultType.length()));
            } else {
                xOfSequence.hide();
                type.setValue(defaultType);
            }
        } else {
            type.hide();
            xOfSequence.hide();
            type.setValue("");
        }

        partNumAndTypeLayout.setExtraSpace(10);
        otherLayout.addMember(partNumAndTypeLayout);

        noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE));

        levelNames.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue().equals(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue())) {
                    type.hide();
                    xOfSequence.hide();
                    xOfLevelNames.show();
                } else {
                    type.show();
                    type.setValueMap(valueMap);
                    type.setValue(PERIODICAL_ITEM_GENRE_TYPES.NORMAL.toString());
                    xOfLevelNames.hide();
                }
            }
        });

        type.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (!PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString().equals(event.getValue())) {
                    xOfSequence.hide();
                } else {
                    xOfSequence.show();
                }
            }
        });
        setHeight(310);
    }

    /**
     * 
     */
    private void setCreatePeriodicalVolume(Record record) {
        partNumber.setValue(record.getAttributeAsString(Constants.ATTR_PART_NUMBER_OR_ALTO));

        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.PERIODICALVOLUME));
        dateIssued.setValue(record.getAttributeAsString(Constants.ATTR_DATE_OR_INT_PART_NAME));

        noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE));
        otherLayout.addMember(new MyDynamicForm(partNumber, dateIssued, noteItem));
        setHeight(210);
    }

    private void setDefaultXOfLevelNames(String levelName) {
        xOfLevelNames.show();
        xOfLevelNames.setValue(levelName.substring(levelName.indexOf("_", 6) + 1, levelName.length()));
    }

    /**
     * @return the nameOrTitle
     */
    public String getNameOrTitle() {
        return nameOrTitle.getValueAsString();
    }

    /**
     * @return the subtitle
     */
    public String getSubtitle() {
        return subtitle.getValueAsString();
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber.getValueAsString();
    }

    /**
     * @return the xOfSequence
     */
    public String getxOfSequence() {
        return xOfSequence.getValueAsString();
    }

    /**
     * @return the dateIssued
     */
    public String getDateIssued() {
        return dateIssued.getValueAsString();
    }

    /**
     * @return the partName
     */
    public String getPartName() {
        return partName.getValueAsString();
    }

    /**
     * @param partName
     *        the partName to set
     */
    public void setPartName(TextItem partName) {
        this.partName = partName;
    }

    /**
     * @return the type
     */
    public String getType(DigitalObjectModel model, String levelName) {
        String typeString = "";

        if (model == DigitalObjectModel.PAGE) {
            PAGE_TYPES.MAP.get(type.getValueAsString());

        } else if (model == DigitalObjectModel.PERIODICALITEM) {
            String perItemType = PERIODICAL_ITEM_GENRE_TYPES.MAP.get(type.getValueAsString());

            if (PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString().equals(perItemType)) {
                typeString =
                        perItemType.substring(0, perItemType.length() - 1) + xOfSequence.getValueAsString();
            } else {
                typeString = perItemType;
            }

        } else if (model == DigitalObjectModel.INTERNALPART) {
            if (levelName.substring(0, levelName.indexOf("_", 6))
                    .equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                typeString = INTERNAL_PART_ARTICLE_GENRE_TYPES.MAP.get(type.getValueAsString());

            } else if (levelName.substring(0, levelName.indexOf("_", 6))
                    .equals(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.toString())) {
                typeString = INTERNAL_PART_CHAPTER_GENRE_TYPES.MAP.get(type.getValueAsString());

            } else {
                typeString = INTERNAL_PART_PICTURE_GENRE_TYPES.MAP.get(type.getValueAsString());
            }
        }
        return typeString;
    }

    /**
     * @return the levelName
     */
    public String getLevelName() {
        String levelName = levelNames.getValueAsString();

        if (!PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue().equals(levelName))
            return levelName.substring(0, levelName.length() - 4) + getFormatedXOfLevelNames();

        return levelName;
    }

    /**
     * @return the xOfLevelNames
     */
    public String getxOfLevelNames() {
        return xOfLevelNames.getValueAsString();
    }

    private String getFormatedXOfLevelNames() {
        NumberFormat formatter = NumberFormat.getFormat("0000");
        return formatter.format(Integer.parseInt(getxOfLevelNames()));
    }

    /**
     * @return
     */
    //    public String verify() {
    //        DigitalObjectModel model =
    //                LabelAndModelConverter.getModelFromLabel().get(getSelectModel().getValueAsString());
    //        if (getxOfSequence() != null && !getxOfSequence().matches(Constants.ONLY_NUMBERS))
    //            return getOnlyNumbersHint("X");
    //        if (getxOfLevelNames() != null && !getxOfLevelNames().matches(Constants.ONLY_NUMBERS))
    //            return getOnlyNumbersHint("XXXX");
    //        if (getPartNumber() != null && !getPartNumber().matches(Constants.ONLY_NUMBERS))
    //            return getOnlyNumbersHint(lang.partNumber());
    //
    //        if (model == DigitalObjectModel.PERIODICALVOLUME) {
    //            if (!(getDateIssued().matches(Constants.DATE_RRRR) || getDateIssued()
    //                    .matches(Constants.DATE_RRRR_RRRR))) return getDateFormatHint(model);
    //
    //        } else if (model == DigitalObjectModel.PERIODICALITEM || model == DigitalObjectModel.MONOGRAPHUNIT) {
    //            if (!(getDateIssued().matches(Constants.DATE_DDMMRRRR)
    //                    || getDateIssued().matches(Constants.DATE_MMRRRR)
    //                    || getDateIssued().matches(Constants.DATE_RRRR)
    //                    || getDateIssued().matches(Constants.DATE_DD_DDMMRRRR) || getDateIssued()
    //                    .matches(Constants.DATE_MM_MMRRRR))) return getDateFormatHint(model);
    //        }
    //        return null;
    //    }
    //
    private String getOnlyNumbersHint(String textItemName) {
        return lang.textBox() + " " + textItemName + " " + lang.onlyNum();
    }

    private String getDateFormatHint(DigitalObjectModel model) {
        if (model == DigitalObjectModel.PERIODICALVOLUME) {
            return lang.dcType() + " " + LabelAndModelConverter.getLabelFromModel().get(model.getValue())
                    + " " + lang.dateInFormat() + ": " + "RRRR " + lang.or() + "<br>RRRR-RRRR";

        } else if (model == DigitalObjectModel.PERIODICALITEM || model == DigitalObjectModel.MONOGRAPHUNIT) {
            return lang.dcType() + " " + LabelAndModelConverter.getLabelFromModel().get(model.getValue())
                    + " " + lang.dateInFormat() + ": <br>" + "DDMMRRRR " + lang.or() + "<br>MMRRRR "
                    + lang.or() + "<br>RRRR " + lang.or() + "<br>DD-DDMMRRR " + lang.or() + "<br>MM-MMRRRR";
        }
        return "";
    }

    protected abstract void doSaveAction(ListGridRecord record);

    protected String getNote() {
        if (noteItem != null) return noteItem.getValueAsString();
        return null;
    }
}