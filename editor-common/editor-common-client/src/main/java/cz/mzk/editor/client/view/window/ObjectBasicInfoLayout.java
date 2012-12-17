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

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
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
import cz.mzk.editor.client.view.other.NewDigitalObjectItemManager;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class ObjectBasicInfoLayout
        extends VLayout {

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

    private final class MainEditLayoutManager
            extends NewDigitalObjectItemManager {

        private TextItem nameOrTitle;

        private TextItem subtitle;

        private TextItem partNumber;

        private TextItem partName;

        private TextItem xOfSequence;

        private TextItem xOfLevelNames;

        private EditorDateItem dateIssued;

        private SelectItem type;

        private SelectItem levelNames;

        private HLayout levelNamesLayout;

        private HLayout typeLayout;

        private VLayout otherLayout;

        private final LangConstants lang;

        private TextAreaItem noteItem = null;

        private final int coeficientOfLength;

        /**
         * @param lang
         */
        public MainEditLayoutManager(LangConstants lang, int coeficientOfLength) {
            super(lang);
            this.lang = lang;
            this.coeficientOfLength = coeficientOfLength;
            // TODO Auto-generated constructor stub
        }

        public void prepareEdit() {
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
            nameOrTitle.setWidth(coeficientOfLength * 23);

            subtitle = new TextItem("subtitle", lang.subtitle());
            subtitle.setWidth(coeficientOfLength * 23);

            levelNames = new SelectItem("levelNames", lang.levelName());
            levelNames.setWidth(coeficientOfLength * 13);

            xOfLevelNames = new TextItem("xOfLevelNames", "XXXX");
            xOfLevelNames.setWidth(coeficientOfLength * 6);
            xOfLevelNames.setPrompt(getOnlyNumbersHint("XXXX"));

            levelNamesLayout = new HLayout(2);
            levelNamesLayout.addMember(new MyDynamicForm(levelNames));
            levelNamesLayout.addMember(new MyDynamicForm(xOfLevelNames));
            levelNamesLayout.setLayoutAlign(Alignment.LEFT);
            levelNamesLayout.setAlign(Alignment.RIGHT);
            levelNamesLayout.setWidth(coeficientOfLength * 29);

            dateIssued = new EditorDateItem("dateIssued", lang.dateIssued(), lang);
            dateIssued.setWidth(coeficientOfLength * 23);

            partNumber = new TextItem("partNumber", lang.partNumber());
            partNumber.setWidth(coeficientOfLength * 8);
            partNumber.setPrompt(getOnlyNumbersHint(lang.partNumber()));

            partName = new TextItem("partName", lang.intPartPartName());
            partName.setWidth(coeficientOfLength * 23);

            type = new SelectItem("type", lang.dcType());
            type.setWidth(coeficientOfLength * 14);

            xOfSequence = new TextItem("xOfSequence", "X");
            xOfSequence.setWidth(coeficientOfLength * 6);
            xOfSequence.setPrompt(getOnlyNumbersHint("X"));

            noteItem = new TextAreaItem("note", lang.note());
            noteItem.setWidth(coeficientOfLength * 23);
            noteItem.setHeight(coeficientOfLength * 5);

            typeLayout = new HLayout(3);
            typeLayout.addMember(new MyDynamicForm(type));
            typeLayout.addMember(new MyDynamicForm(xOfSequence));
            typeLayout.setLayoutAlign(Alignment.LEFT);
            typeLayout.setAlign(Alignment.RIGHT);
            typeLayout.setWidth(coeficientOfLength * 29);

            otherLayout = new VLayout();

            setPadding(5);
            setWidth100();
            setExtraSpace(10);
            addMember(otherLayout);
            setRight(10);
        }

        public void setEditLayout(boolean isPeriodical) {
            if (contains(otherLayout)) {
                removeMember(otherLayout);
            }
            otherLayout = new VLayout();
            DigitalObjectModel model =
                    DigitalObjectModel.parseString(record.getAttribute(Constants.ATTR_MODEL_ID));
            if (model != null) {

                xOfLevelNames.show();

                switch (model) {
                    case PERIODICALVOLUME:
                        setEditPeriodicalVolume();
                        break;
                    case PERIODICALITEM:
                        setEditPeriodicalItem();
                        break;

                    case INTERNALPART:
                        setEditInternalPart(isPeriodical);
                        break;

                    case MONOGRAPHUNIT:
                        setEditMonographUnit();
                        break;

                    case PAGE:
                        setEditPage();
                        break;

                    default:
                        setEditDefault();
                        break;
                }
            } else {
                setEditDefault();
            }
            addMember(otherLayout, 0);
        }

        /**
         * 
         */
        private void setEditPage() {
            nameOrTitle.setTitle(lang.dcTitle());
            nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));
            otherLayout.addMember(new MyDynamicForm(nameOrTitle));

            final LinkedHashMap<String, String> valueMapPage =
                    new LinkedHashMap<String, String>(PAGE_TYPES.MAP);

            type.setValueMap(valueMapPage);
            type.setValue(record.getAttribute(Constants.ATTR_TYPE));
            type.redraw();
            xOfSequence.hide();
            otherLayout.addMember(typeLayout);
            ObjectBasicInfoLayout.this.setWindowHeight(150);
        }

        /**
         * 
         */
        private void setEditDefault() {
            nameOrTitle.setTitle(lang.dcTitle());
            nameOrTitle.setValue(record.getAttribute(Constants.ATTR_NAME));
            otherLayout.addMember(new MyDynamicForm(nameOrTitle));
        }

        /**
         * 
         */
        private void setEditMonographUnit() {
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
            ObjectBasicInfoLayout.this.setWindowHeight(270);
        }

        /**
         * 
         */
        private void setEditInternalPart(boolean isPeriodical) {
            final LinkedHashMap<String, String> valueMapArt =
                    new LinkedHashMap<String, String>(INTERNAL_PART_ARTICLE_GENRE_TYPES.MAP);
            final LinkedHashMap<String, String> valueMapPicture =
                    new LinkedHashMap<String, String>(INTERNAL_PART_PICTURE_GENRE_TYPES.MAP);
            final LinkedHashMap<String, String> valueMapChapter =
                    new LinkedHashMap<String, String>(INTERNAL_PART_CHAPTER_GENRE_TYPES.MAP);

            String levelName = record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
            String defaultType = record.getAttribute(Constants.ATTR_TYPE);

            String substringLevelName = "";
            if (!"".equals(levelName))
                substringLevelName = levelName.substring(0, levelName.indexOf("_", 6));

            if (isPeriodical) {
                levelNames.setValueMap(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue(),
                                       Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICT.getValue());

                if ("".equals(substringLevelName)) {
                    levelNames.setDefaultValue("");
                    type.setValueMap("");

                } else if (substringLevelName.equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                    levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue());
                    type.setValueMap(valueMapArt);
                    defaultType = INTERNAL_PART_ARTICLE_GENRE_TYPES.getEnumAsStringFromMap(defaultType);

                } else {
                    levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICT.getValue());
                    type.setValueMap(valueMapPicture);
                    defaultType = INTERNAL_PART_PICTURE_GENRE_TYPES.getEnumAsStringFromMap(defaultType);
                }

            } else {
                levelNames.setValueMap(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue(),
                                       INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.getValue());

                if ("".equals(substringLevelName)) {
                    levelNames.setDefaultValue("");
                    type.setValueMap("");

                } else if (substringLevelName.equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.toString())) {
                    levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue());
                    type.setValueMap(valueMapChapter);
                    defaultType = INTERNAL_PART_CHAPTER_GENRE_TYPES.getEnumAsStringFromMap(defaultType);

                } else {
                    levelNames.setDefaultValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.getValue());
                    type.setValueMap(valueMapPicture);
                    defaultType = INTERNAL_PART_PICTURE_GENRE_TYPES.getEnumAsStringFromMap(defaultType);
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

            //            type.setValueMap(valueMapArt);
            type.setValue(defaultType);
            xOfSequence.hide();
            typeLayout.setExtraSpace(10);
            otherLayout.addMember(typeLayout);

            levelNames.addChangedHandler(new ChangedHandler() {

                @Override
                public void onChanged(ChangedEvent event) {
                    if (event.getValue().equals(INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue())) {
                        type.setValueMap(valueMapArt);
                    } else if (event.getValue().equals(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue())) {
                        type.setValueMap(valueMapChapter);
                    } else {
                        type.setValueMap(valueMapPicture);
                    }
                    type.setValue("");
                }
            });
            ObjectBasicInfoLayout.this.setWindowHeight(270);
        }

        /**
         * 
         */
        private void setEditPeriodicalItem() {
            levelNames.setValueMap(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue(),
                                   PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue());
            String levelName = record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
            String substringLevelName = "";
            if (!"".equals(levelName))
                substringLevelName = levelName.substring(0, levelName.indexOf("_", 6));

            boolean isIssue = substringLevelName.equals(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.toString());

            if ("".equals(substringLevelName)) {
                levelNames.setDefaultValue("");
                xOfLevelNames.hide();
                type.setValueMap("");
            } else if (isIssue) {
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
                        PERIODICAL_ITEM_GENRE_TYPES.MAP
                                .get(PERIODICAL_ITEM_GENRE_TYPES.SEQUENCE_X.toString());
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
                    type.setValue(PERIODICAL_ITEM_GENRE_TYPES.getEnumAsStringFromMap(defaultType));
                }
            } else {
                type.hide();
                xOfSequence.hide();
                type.setValue("");
            }

            typeLayout.setExtraSpace(10);
            otherLayout.addMember(typeLayout);

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
            ObjectBasicInfoLayout.this.setWindowHeight(310);
        }

        /**
         * 
         */
        private void setEditPeriodicalVolume() {
            partNumber.setValue(record.getAttributeAsString(Constants.ATTR_PART_NUMBER_OR_ALTO));

            dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.PERIODICALVOLUME));
            dateIssued.setValue(record.getAttributeAsString(Constants.ATTR_DATE_OR_INT_PART_NAME));

            noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE));
            otherLayout.addMember(new MyDynamicForm(partNumber, dateIssued, noteItem));
            ObjectBasicInfoLayout.this.setWindowHeight(210);
        }

        private void setDefaultXOfLevelNames(String levelName) {
            xOfLevelNames.show();
            xOfLevelNames.setValue(levelName.substring(levelName.indexOf("_", 6) + 1, levelName.length()));
        }

        @Override
        protected TextItem getXOfSequenceItem() {
            return xOfSequence;
        }

        @Override
        protected TextItem getXOfLevelNamesItem() {
            return xOfLevelNames;
        }

        @Override
        protected SelectItem getTypeItem() {
            return type;
        }

        @Override
        protected TextItem getSubtitleItem() {
            return subtitle;
        }

        @Override
        protected TextItem getPartNumberItem() {
            return partNumber;
        }

        @Override
        protected TextItem getPartNameItem() {
            return partName;
        }

        @Override
        protected TextItem getNameOrTitleItem() {
            return nameOrTitle;
        }

        @Override
        protected SelectItem getLevelNamesItem() {
            return levelNames;
        }

        @Override
        protected EditorDateItem getDateIssuedItem() {
            return dateIssued;
        }

        @Override
        protected DigitalObjectModel getCurrentModel() {
            return DigitalObjectModel.parseString(ObjectBasicInfoLayout.this.record
                    .getAttribute(Constants.ATTR_MODEL_ID));
        }

        @Override
        protected IButton getAddNoteButton() {
            return null;
        }

        @Override
        public String getNote() {
            if (noteItem != null) return noteItem.getValueAsString();
            return null;
        }
    }

    private final Record record;
    MainEditLayoutManager managerLayout;

    /**
     * @param structureTreeGrid
     * @param height
     * @param width
     * @param title
     */

    public ObjectBasicInfoLayout(final ListGridRecord record,
                                 LangConstants lang,
                                 EventBus eventBus,
                                 boolean isPeriodical,
                                 int coeficientOfLength) {
        super();
        this.record = record;
        managerLayout = new MainEditLayoutManager(lang, coeficientOfLength);
        setAlign(Alignment.LEFT);

        managerLayout.prepareEdit();
        managerLayout.setEditLayout(isPeriodical);

        managerLayout.addMember(getButtonsLayout());

        addMember(managerLayout);
    }

    /**
     * @return the managerLayout
     */
    public NewDigitalObjectItemManager getManagerLayout() {
        return managerLayout;
    }

    public String verify() {
        return managerLayout.verify();
    }

    /**
     * @return
     */
    protected abstract HLayout getButtonsLayout();

    /**
     * @param height
     */
    protected abstract void setWindowHeight(int height);

}