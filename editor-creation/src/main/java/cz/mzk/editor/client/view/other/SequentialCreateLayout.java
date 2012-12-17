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

import java.util.LinkedHashMap;

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_ARTICLE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_CHAPTER_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_PICTURE_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.MONOGRAPH_UNIT_LEVEL_NAMES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_GENRE_TYPES;
import cz.mzk.editor.client.util.Constants.PERIODICAL_ITEM_LEVEL_NAMES;
import cz.mzk.editor.client.view.window.AddNoteWindow;
import cz.mzk.editor.client.view.window.EditorDateItem;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version Apr 4, 2012
 */
public class SequentialCreateLayout
        extends NewDigitalObjectItemManager {

    private ButtonItem createButton;

    private IButton addNoteButton;

    private Layout addNoteButtonLyout;

    private CheckboxItem keepCheckbox;

    private SelectItem selectModel;

    private TextItem nameOrTitle;

    private CreateDynamicForm nameOrTitleForm;

    private TextItem subtitle;

    private CreateDynamicForm subtitleForm;

    private TextItem partNumber;

    private CreateDynamicForm partNumberForm;

    private TextItem partName;

    private CreateDynamicForm partNameForm;

    private TextItem xOfSequence;

    private CreateDynamicForm xOfSequenceForm;

    private TextItem xOfLevelNames;

    private CreateDynamicForm xOfLevelNamesForm;

    private EditorDateItem dateIssued;

    private CreateDynamicForm dateIssuedForm;

    private SelectItem type;

    private CreateDynamicForm typeForm;

    private SelectItem levelNames;

    private CreateDynamicForm levelNamesForm;

    private HLayout levelNamesLayout;

    private HLayout partNumAndTypeLayout;

    private VLayout otherLayout;

    private VLayout createLayout;

    private final LangConstants lang;

    private final EventBus eventBus;

    public static final String CREATE_BUTTON_HAS_A_HANDLER = "CREATE_BUTTON_HAS_A_HANDLER";

    private static final class CreateDynamicForm
            extends DynamicForm {

        public CreateDynamicForm(FormItem item) {
            super();
            setItems(item);
        }
    }

    public SequentialCreateLayout(LangConstants lang, EventBus eventBus) {
        super(lang);
        this.lang = lang;
        this.eventBus = eventBus;
        addMember(getSequentialCreateLayout());
    }

    public VLayout getSequentialCreateLayout() {
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
        nameOrTitleForm = new CreateDynamicForm(nameOrTitle);

        subtitle = new TextItem("subtitle", lang.subtitle());
        subtitleForm = new CreateDynamicForm(subtitle);

        levelNames = new SelectItem("levelNames", lang.levelName());
        levelNamesForm = new CreateDynamicForm(levelNames);

        xOfLevelNames = new TextItem("xOfLevelNames", "XXXX");
        xOfLevelNames.setWidth(50);
        xOfLevelNames.setPrompt(getOnlyNumbersHint("XXXX"));
        xOfLevelNamesForm = new CreateDynamicForm(xOfLevelNames);

        levelNamesLayout = new HLayout(2);
        levelNamesLayout.addMember(levelNamesForm);
        levelNamesLayout.addMember(xOfLevelNamesForm);
        levelNamesLayout.setLayoutAlign(Alignment.LEFT);
        levelNamesLayout.setAlign(Alignment.RIGHT);

        dateIssued = new EditorDateItem("dateIssued", lang.dateIssued(), lang);
        dateIssuedForm = new CreateDynamicForm(dateIssued);

        partNumber = new TextItem("partNumber", lang.partNumber());
        partNumber.setWidth(40);
        partNumber.setPrompt(getOnlyNumbersHint(lang.partNumber()));
        partNumberForm = new CreateDynamicForm(partNumber);

        partName = new TextItem("partName", lang.intPartPartName());
        partNameForm = new CreateDynamicForm(partName);

        type = new SelectItem("type", lang.dcType());
        type.setWidth(90);
        typeForm = new CreateDynamicForm(type);

        xOfSequence = new TextItem("xOfSequence", "X");
        xOfSequence.setWidth(30);
        xOfSequence.setPrompt(getOnlyNumbersHint("X"));
        xOfSequenceForm = new CreateDynamicForm(xOfSequence);
        xOfSequenceForm.setWidth(60);

        partNumAndTypeLayout = new HLayout(3);
        partNumAndTypeLayout.addMember(partNumberForm);
        partNumAndTypeLayout.addMember(typeForm);
        partNumAndTypeLayout.addMember(xOfSequenceForm);
        partNumAndTypeLayout.setLayoutAlign(Alignment.LEFT);
        partNumAndTypeLayout.setAlign(Alignment.RIGHT);

        addNoteButton = new IButton();
        addNoteButton.setTitle(lang.addNote());
        addNoteButton.setHeight(18);
        addNoteButton.setWidth(140);
        addNoteButton.setExtraSpace(3);

        addNoteButtonLyout = new Layout();
        addNoteButtonLyout.addMember(addNoteButton);
        addNoteButtonLyout.setAlign(Alignment.CENTER);

        addNoteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                new AddNoteWindow(addNoteButton.getTitle(), eventBus, lang, addNoteButton.getPrompt()) {

                    @Override
                    protected void doSave(String note) {
                        if (note != null && !"".equals(note)) {
                            addNoteButton.setTitle(lang.modifyNote());
                        } else {
                            addNoteButton.setTitle(lang.addNote());
                        }
                        addNoteButton.setTooltip(note);
                        hide();
                    }
                };
            }
        });

        otherLayout = new VLayout();

        selectModel = new SelectItem();
        selectModel.setTitle(lang.dcType());

        keepCheckbox = new CheckboxItem();
        keepCheckbox.setTitle(lang.keepOnRight());
        createButton = new ButtonItem();
        createButton.setTitle(lang.create());
        createButton.setAlign(Alignment.CENTER);
        createButton.setColSpan(2);
        createButton.setAttribute(CREATE_BUTTON_HAS_A_HANDLER, false);

        createLayout = new VLayout();
        createLayout.setPadding(5);
        createLayout.setWidth100();
        createLayout.setExtraSpace(10);
        createLayout.addMember(otherLayout);
        createLayout.addMember(new CreateDynamicForm(selectModel));
        createLayout.addMember(new CreateDynamicForm(keepCheckbox));
        createLayout.addMember(new CreateDynamicForm(createButton));
        createLayout.setRight(10);
        return createLayout;
    }

    public void setCreate(DigitalObjectModel model, String defaultDateIssued, boolean isPeriodical) {
        if (createLayout.contains(otherLayout)) {
            createLayout.removeMember(otherLayout);
        }
        otherLayout = new VLayout();
        if (model != null) {

            addNoteButton.setTitle(lang.addNote());
            addNoteButton.setTooltip("");
            xOfLevelNames.show();
            dateIssued.setDefaultValue(defaultDateIssued);
            partNumberForm.setWidth(100);
            levelNames.setValue("");

            switch (model) {
                case PERIODICALVOLUME:
                    setCreatePeriodicalVolume();
                    break;
                case PERIODICALITEM:
                    setCreatePeriodicalItem();
                    break;

                case INTERNALPART:
                    setCreateInternalPart(isPeriodical);
                    break;

                case MONOGRAPHUNIT:
                    setCreateMonographUnit();
                    break;

                case PAGE:
                    setCreatePage();
                    break;

                default:
                    setCreateDefault();
                    break;
            }
        } else {
            setCreateDefault();
        }
        createLayout.addMember(otherLayout, 0);
    }

    /**
     * 
     */
    private void setCreatePage() {
        nameOrTitle.setTitle(lang.dcTitle());
        otherLayout.addMember(nameOrTitleForm);
    }

    /**
     * 
     */
    private void setCreateDefault() {
    }

    /**
     * 
     */
    private void setCreateMonographUnit() {
        levelNames.setValueMap(MONOGRAPH_UNIT_LEVEL_NAMES.MODS_SUPPL.getValue());
        levelNames.setValue(MONOGRAPH_UNIT_LEVEL_NAMES.MODS_SUPPL.getValue());
        levelNames.redraw();
        otherLayout.addMember(levelNamesLayout);

        nameOrTitle.setTitle(lang.supplementName());
        otherLayout.addMember(nameOrTitleForm);

        partNumberForm.setWidth100();
        otherLayout.addMember(partNumberForm);

        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.MONOGRAPHUNIT));
        otherLayout.addMember(dateIssuedForm);
        otherLayout.addMember(addNoteButtonLyout);

    }

    /**
     * 
     */
    private void setCreateInternalPart(boolean isPeriodical) {
        if (isPeriodical) {
            levelNames.setValueMap(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue(),
                                   Constants.INTERNAL_PART_LEVEL_NAMES.MODS_PICT.getValue());
            levelNames.setValue(Constants.INTERNAL_PART_LEVEL_NAMES.MODS_ART.getValue());
        } else {
            levelNames.setValueMap(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue(),
                                   INTERNAL_PART_LEVEL_NAMES.MODS_PICTURE.getValue());
            levelNames.setValue(INTERNAL_PART_LEVEL_NAMES.MODS_CHAPTER.getValue());
        }
        levelNames.redraw();
        type.setValue("");

        otherLayout.addMember(levelNamesLayout);

        nameOrTitle.setTitle(lang.intPartName());
        otherLayout.addMember(nameOrTitleForm);
        otherLayout.addMember(subtitleForm);
        otherLayout.addMember(partNameForm);

        final LinkedHashMap<String, String> valueMapArt =
                new LinkedHashMap<String, String>(INTERNAL_PART_ARTICLE_GENRE_TYPES.MAP);
        final LinkedHashMap<String, String> valueMapPicture =
                new LinkedHashMap<String, String>(INTERNAL_PART_PICTURE_GENRE_TYPES.MAP);
        final LinkedHashMap<String, String> valueMapChapter =
                new LinkedHashMap<String, String>(INTERNAL_PART_CHAPTER_GENRE_TYPES.MAP);

        type.setValueMap(valueMapArt);
        xOfSequence.hide();
        otherLayout.addMember(partNumAndTypeLayout);

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
                xOfLevelNames.show();
            }
        });
    }

    /**
     * 
     */
    private void setCreatePeriodicalItem() {
        levelNames.setValueMap(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue(),
                               PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue());
        levelNames.setValue(PERIODICAL_ITEM_LEVEL_NAMES.MODS_ISSUE.getValue());
        levelNames.redraw();

        xOfLevelNames.hide();
        otherLayout.addMember(levelNamesLayout);

        nameOrTitle.setTitle(lang.editionName());
        otherLayout.addMember(nameOrTitleForm);

        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.PERIODICALITEM));
        otherLayout.addMember(dateIssuedForm);

        final LinkedHashMap<String, String> valueMap =
                new LinkedHashMap<String, String>(PERIODICAL_ITEM_GENRE_TYPES.MAP);
        type.show();
        type.setValueMap(valueMap);
        type.setValue(PERIODICAL_ITEM_GENRE_TYPES.NORMAL.toString());
        xOfSequence.hide();
        otherLayout.addMember(partNumAndTypeLayout);

        otherLayout.addMember(addNoteButtonLyout);

        levelNames.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (event.getValue().equals(PERIODICAL_ITEM_LEVEL_NAMES.MODS_SUPPL.getValue())) {
                    type.hide();
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

    }

    /**
     * 
     */
    private void setCreatePeriodicalVolume() {
        partNumberForm.setWidth100();
        otherLayout.addMember(partNumberForm);
        dateIssued.setPrompt(getDateFormatHint(DigitalObjectModel.PERIODICALVOLUME));
        otherLayout.addMember(dateIssuedForm);
        otherLayout.addMember(addNoteButtonLyout);
    }

    public ButtonItem getCreateButton() {
        return createButton;
    }

    public CheckboxItem getKeepCheckbox() {
        return keepCheckbox;
    }

    public SelectItem getSelectModel() {
        return selectModel;
    }

    /**
     * @param partName
     *        the partName to set
     */
    public void setPartName(TextItem partName) {
        this.partName = partName;
    }

    public void enableCheckbox(boolean isEnabled) {
        if (isEnabled) {
            keepCheckbox.enable();
        } else {
            keepCheckbox.disable();
        }
    }

    public boolean hasCreateButtonAClickHandler() {
        return createButton.getAttributeAsBoolean(CREATE_BUTTON_HAS_A_HANDLER);
    }

    public void setCreateButtonHasAClickHandler() {
        createButton.setAttribute(CREATE_BUTTON_HAS_A_HANDLER, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IButton getAddNoteButton() {
        return addNoteButton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getNameOrTitleItem() {
        return nameOrTitle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getSubtitleItem() {
        return subtitle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getPartNumberItem() {
        return partNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getXOfSequenceItem() {
        return xOfSequence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EditorDateItem getDateIssuedItem() {
        return dateIssued;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getPartNameItem() {
        return partName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SelectItem getTypeItem() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SelectItem getLevelNamesItem() {
        return levelNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TextItem getXOfLevelNamesItem() {
        return xOfLevelNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DigitalObjectModel getCurrentModel() {
        return LabelAndModelConverter.getModelFromLabel().get(getSelectModel().getValueAsString());
    }

}
