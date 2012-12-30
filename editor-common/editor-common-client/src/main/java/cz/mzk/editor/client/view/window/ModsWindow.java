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

package cz.mzk.editor.client.view.window;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.metadata.ModsConstants;
import cz.mzk.editor.client.mods.CodeOrTextClient;
import cz.mzk.editor.client.mods.ModsCollectionClient;
import cz.mzk.editor.client.mods.ModsTypeClient;
import cz.mzk.editor.client.mods.ModsTypeClientManager;
import cz.mzk.editor.client.mods.ModsTypeClientManagerImpl;
import cz.mzk.editor.client.mods.NamePartTypeClient;
import cz.mzk.editor.client.mods.RoleTypeClient;
import cz.mzk.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.mzk.editor.client.util.ClientCreateUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.DigitalObjectModel.TopLevelObjectModel;
import cz.mzk.editor.shared.rpc.DublinCore;

public abstract class ModsWindow
        extends UniversalWindow {

    private final LangConstants lang;

    /** The TextItem with title value */
    private TextItem titleItem;

    /** The TextItem with subtitle value */
    private TextItem subtitleItem;

    /** The Constant AUTHOR1 */
    private static String AUTHOR1;

    /** The Constant AUTHOR2 */
    private static String AUTHOR2;

    /** The StaticTextItem with Author1 */
    private final StaticTextItem author1Item = new MyStaticTextItem();

    /** The StaticTextItem with Author2 */
    private final StaticTextItem author2Item = new MyStaticTextItem();

    /** The TextItem with publisher value */
    private final TextItem publisherItem = new MyTextItem();

    /** The TextItem with signature value */
    private final TextItem shelfLocatorItem = new MyTextItem();

    /** The TextItem with place term value */
    private final TextItem placeItem = new MyTextItem();

    /** The TextItem with extent value */
    private final TextItem extentItem = new MyTextItem();

    /** The IButton to publish the content of the window */
    private final IButton publish = new IButton();

    /** The IButton to close the window */
    private final IButton close = new IButton();

    /** The CheckboxItem to enable reflect changes in DC */
    private final CheckboxItem reflectInDC = new CheckboxItem();

    /** The original modsTypeClient */
    private ModsTypeClient modsTypeClient;

    /** The original modsCollectionClient */
    private final ModsCollectionClient modsCollection;

    /** The manager of the original modsTypeClient */
    private final ModsTypeClientManager modsClientManager;

    /** The DateTimeItem with day value set to EUROPEANSHORTDATE-format */
    private EditorDateItem dateItem;

    private ObjectBasicInfoLayout objectBasicInfoLayout;

    private DigitalObjectModel model;

    /** The List of FormItem designated for Author1 */
    @SuppressWarnings("serial")
    private final List<FormItem> authorPartsOfName1 = new ArrayList<FormItem>() {

        {
            add(author1Item);
        }
    };

    /** The List of FormItem designated for Author2 */
    @SuppressWarnings("serial")
    private final List<FormItem> authorPartsOfName2 = new ArrayList<FormItem>() {

        {
            add(author2Item);
        }
    };

    private String label = null;

    /**
     * The TextItem with width=180, wrapTitle=false, selectOnFocus=true,
     * cellHeight=40. Automatically sets the default value of title.
     */
    private static class MyTextItem
            extends TextItem {

        private String myDefaultValue;

        public MyTextItem() {
            setWidth(180);
            setWrapTitle(false);
            setSelectOnFocus(true);
            setCellHeight(40);
            setHoverOpacity(75);
            setHoverWidth(330);
            setHoverStyle("interactImageHover");
        }

        @Override
        public void setDefaultValue(String defaultValue) {
            this.myDefaultValue = defaultValue;
            setAttribute("defaultValue", defaultValue);
        }

        /**
         * @return the default value of this item
         */
        public String getMyDefaultValue() {
            return this.myDefaultValue;
        }
    }

    /**
     * The DynamicForm which sets automatically items.
     */
    private static class MyDynamicForm
            extends DynamicForm {

        /**
         * Items are used for set items in this DynamicForm
         * 
         * @param items
         */
        MyDynamicForm(FormItem... items) {
            List<FormItem> toAddItems = new ArrayList<FormItem>();
            for (FormItem item : items) {
                if (item != null) toAddItems.add(item);
            }
            this.setItems(toAddItems.toArray(new FormItem[] {}));
        }
    }

    /**
     * The StaticTextItem with wrapTitle=false. Automatically sets the value of
     * deafultTitle in method setTitle.
     */
    private static class MyStaticTextItem
            extends StaticTextItem {

        private String defaultTitle;

        public MyStaticTextItem() {
            setWrapTitle(false);
        }

        @Override
        public void setTitle(String title) {
            defaultTitle = title;
            setAttribute("title", title);
        }

        /**
         * Set defaultTitle as the title.
         */
        public void setDefaultTitle() {
            setTitle(defaultTitle);
        }
    }

    /**
     * @param modsCollection
     *        the original modsCollectionClient
     * @param lang
     *        the lang
     */
    public ModsWindow(ModsCollectionClient modsCollection,
                      String uuid,
                      LangConstants lang,
                      EventBus eventBus,
                      DigitalObjectModel model,
                      String label) {
        super(450, 900, lang.quickEdit() + ": " + uuid, eventBus, 100);
        this.lang = lang;
        this.modsCollection = modsCollection;
        this.model = model;
        if (modsCollection != null) {
            modsTypeClient = modsCollection.getMods().get(0);
        } else {
            modsTypeClient = new ModsTypeClient();
        }
        modsClientManager = new ModsTypeClientManagerImpl(modsTypeClient);
        setVariables();

        VStack mainLayout = new VStack();
        HStack itemsLayout = new HStack();

        itemsLayout.setMargin(20);
        itemsLayout.setMembersMargin(20);

        ListGridRecord listGridRecord = getListGridRecord(label);

        objectBasicInfoLayout = new ObjectBasicInfoLayout(listGridRecord, lang, eventBus, true, 14) {

            @Override
            protected void setWindowHeight(int height) {
                ModsWindow.this.setHeight(450 + height);
            }

            @Override
            protected HLayout getButtonsLayout() {
                return new HLayout();
            }
        };
        mainLayout.addMember(objectBasicInfoLayout);
        itemsLayout.addMember(createTitleSubtitle(model));
        itemsLayout.addMember(createNames());
        itemsLayout.addMember(create3Column(model));

        mainLayout.addMember(itemsLayout);
        mainLayout.addMember(createButtonsLayout());

        centerInPage();
        addItem(mainLayout);
        init();
    }

    /**
     * @param label
     * @return
     */
    private ListGridRecord getListGridRecord(String label) {

        ListGridRecord listGridRecord = new ListGridRecord();
        listGridRecord.setAttribute(Constants.ATTR_MODEL_ID, model.toString());

        switch (model) {
            case PERIODICALVOLUME:
                setPeriodicalVolumeRecord(listGridRecord);
                break;
            case PERIODICALITEM:
                setPeriodicalItemRecord(listGridRecord);
                break;

            case INTERNALPART:
                setInternalPartRecord(listGridRecord);
                break;

            case MONOGRAPHUNIT:
                setMonographUnitRecord(listGridRecord);
                break;

            case PAGE:
                setPageRecord(listGridRecord, label);
                break;

            default:
                listGridRecord.setAttribute(Constants.ATTR_NAME, modsClientManager.getTitle());
                break;
        }
        return listGridRecord;
    }

    /**
     * @param listGridRecord
     * @param label
     */
    private void setPageRecord(ListGridRecord listGridRecord, String label) {
        listGridRecord.setAttribute(Constants.ATTR_NAME, label);
        listGridRecord.setAttribute(Constants.ATTR_TYPE, modsClientManager.getType(model));
    }

    /**
     * @param listGridRecord
     */
    private void setMonographUnitRecord(ListGridRecord listGridRecord) {
        listGridRecord.setAttribute(Constants.ATTR_NAME, modsClientManager.getPartName());
        listGridRecord.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, modsClientManager.getDateIssued());
        listGridRecord.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, modsClientManager.getNote());
        listGridRecord.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, modsClientManager.getPartNumber());
        listGridRecord.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, modsClientManager.getLevelName());
    }

    /**
     * @param listGridRecord
     */
    private void setInternalPartRecord(ListGridRecord listGridRecord) {
        listGridRecord.setAttribute(Constants.ATTR_NAME, modsClientManager.getTitle());
        listGridRecord.setAttribute(Constants.ATTR_TYPE, modsClientManager.getType(model));
        listGridRecord.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, modsClientManager.getPartName());
        listGridRecord.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, modsClientManager.getSubtitle());
        listGridRecord.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, modsClientManager.getPartNumber());
        listGridRecord.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, modsClientManager.getLevelName());
    }

    /**
     * @param listGridRecord
     */
    private void setPeriodicalItemRecord(ListGridRecord listGridRecord) {
        listGridRecord.setAttribute(Constants.ATTR_NAME, modsClientManager.getPartName());
        listGridRecord.setAttribute(Constants.ATTR_TYPE, modsClientManager.getType(model));
        listGridRecord.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, modsClientManager.getDateIssued());
        listGridRecord.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, modsClientManager.getNote());
        listGridRecord.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, modsClientManager.getPartNumber());
        listGridRecord.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, modsClientManager.getLevelName());
    }

    /**
     * @param listGridRecord
     */
    private void setPeriodicalVolumeRecord(ListGridRecord listGridRecord) {
        listGridRecord.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, modsClientManager.getDateIssued());
        listGridRecord.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, modsClientManager.getNote());
        listGridRecord.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, modsClientManager.getPartNumber());
    }

    private void setVariables() {

        titleItem = new MyTextItem();
        titleItem.setTitle(lang.title());
        titleItem.setTooltip(lang.titleMARC());

        subtitleItem = new MyTextItem();
        subtitleItem.setTitle(lang.subtitle());
        subtitleItem.setTooltip(lang.subTitleMARC());

        AUTHOR1 = lang.author1();
        author1Item.setTitle(AUTHOR1);

        AUTHOR2 = lang.author2();
        author2Item.setTitle(AUTHOR2);

        publisherItem.setTitle(lang.publisher());
        publisherItem.setTooltip(lang.publisherMARC());

        shelfLocatorItem.setTitle(lang.signature());
        shelfLocatorItem.setTooltip(lang.shelfLocator());

        placeItem.setTitle(lang.place());
        placeItem.setTooltip(lang.placeTerm());

        extentItem.setTitle(lang.extent());
        extentItem.setTooltip(lang.extentMARC());

        dateItem = new EditorDateItem();
        dateItem.setTitle(lang.issuedDateItem());
        dateItem.setTooltip(lang.issuedDate());

    }

    private DynamicForm createTitleSubtitle(DigitalObjectModel model) {

        if (model.getTopLevelType() != TopLevelObjectModel.PERIODICAL
                && model != DigitalObjectModel.INTERNALPART
                && model.getTopLevelType() != TopLevelObjectModel.MONOGRAPH
                && model != DigitalObjectModel.PAGE) {
            titleItem.setDefaultValue(modsClientManager.getTitle());
        } else {
            titleItem = null;
        }
        if (model != DigitalObjectModel.INTERNALPART) {
            subtitleItem.setDefaultValue(modsClientManager.getSubtitle());
        } else {
            subtitleItem = null;
        }
        return new MyDynamicForm(titleItem, subtitleItem);
    }

    private VStack createNames() {

        VStack nameLayout = new VStack();
        nameLayout.setMembersMargin(6);

        for (int i = 0; i < 2; i++) {

            boolean isFamily = false;
            boolean isGiven = false;

            if (isNotNullOrEmpty(modsTypeClient.getName()) && modsTypeClient.getName().size() > i) {

                List<NamePartTypeClient> namePart = modsTypeClient.getName().get(i).getNamePart();
                if (isNotNullOrEmpty(namePart)) {
                    for (NamePartTypeClient part : namePart) {
                        if (part.getValue() != null && !part.getValue().trim().equals("")) {
                            if (part.getType() == null) {
                                part.setType("");
                            }

                            TextItem newItem = new MyTextItem();
                            newItem.setTitle(part.getType());
                            if (part.getType().equals("date")) newItem.setTooltip(lang.dateParse());
                            if (part.getType().equals("family")) newItem.setTooltip(lang.lastName());
                            if (part.getType().equals("termsOfAddress")) {
                                newItem.setTitle("termsOf Address");
                                newItem.setWrapTitle(true);
                                newItem.setTooltip(lang.addressMARC());
                            }
                            if (part.getType().equals("given")) newItem.setTooltip(lang.firstName());
                            if (part.getType().equals("")) newItem.setTooltip(lang.attributeOmitted());

                            setAuthorRole(modsTypeClient.getName().get(i).getRole(), (i == 0 ? author1Item
                                    : author2Item));

                            newItem.setDefaultValue(part.getValue());
                            (i == 0 ? authorPartsOfName1 : authorPartsOfName2).add(newItem);

                            if (part.getType() != null) {
                                if (part.getType().equals(ModsConstants.FAMILY)) isFamily = true;
                                if (part.getType().equals(ModsConstants.GIVEN)) isGiven = true;
                            }
                        }
                    }
                }
            }

            if (!isFamily) {
                final TextItem newItem = new MyTextItem();
                newItem.setTitle(ModsConstants.FAMILY);
                newItem.setTooltip(lang.lastName());
                (i == 0 ? authorPartsOfName1 : authorPartsOfName2).add(newItem);
            }

            if (!isGiven) {
                final TextItem newItem = new MyTextItem();
                newItem.setTitle(ModsConstants.GIVEN);
                newItem.setTooltip(lang.firstName());
                (i == 0 ? authorPartsOfName1 : authorPartsOfName2).add(newItem);
            }

        }

        nameLayout.addMember(new MyDynamicForm(authorPartsOfName1.toArray(new FormItem[] {})));
        nameLayout.addMember(new MyDynamicForm(authorPartsOfName2.toArray(new FormItem[] {})));

        return nameLayout;
    }

    private void setAuthorRole(List<RoleTypeClient> roleList, StaticTextItem roleItem) {
        if (isNotNullOrEmpty(roleList)) {
            boolean stop = false;
            for (RoleTypeClient roleType : roleList) {
                if (isNotNullOrEmpty(roleType.getRoleTerm()) && !stop) {
                    for (RoleTermClient roleTerm : roleType.getRoleTerm()) {

                        if (roleTerm.getValue().trim().equals("Author")
                                || roleTerm.getValue().trim().equals("author")
                                || roleTerm.getValue().trim().equals("Autor")
                                || roleTerm.getValue().trim().equals("autor")
                                || roleTerm.getValue().trim().equals("cre")) {

                            roleItem.setTitle(roleTerm.getValue());
                            ((MyStaticTextItem) roleItem).setDefaultTitle();

                            if (roleTerm.getType().equals(CodeOrTextClient.TEXT)) {
                                stop = true;
                                break;
                            }
                        } else {
                            roleItem.setTitle(roleTerm.getValue());
                        }
                    }
                }
            }
        }
    }

    private DynamicForm create3Column(DigitalObjectModel model) {

        publisherItem.setDefaultValue(modsClientManager.getPublisher());

        if (model != DigitalObjectModel.PERIODICALITEM && model != DigitalObjectModel.PERIODICALVOLUME
                && model != DigitalObjectModel.MONOGRAPHUNIT) {
            dateItem.setDefaultValue(modsClientManager.getDateIssued());
        } else {
            dateItem = null;
        }
        shelfLocatorItem.setDefaultValue(modsClientManager.getShelfLocator());
        placeItem.setDefaultValue(modsClientManager.getPlace());
        extentItem.setDefaultValue(modsClientManager.getExtent());

        reflectInDC.setTitle(lang.changesInDC());
        reflectInDC.setDefaultValue(true);
        return new MyDynamicForm(dateItem,
                                 publisherItem,
                                 placeItem,
                                 shelfLocatorItem,
                                 extentItem,
                                 reflectInDC);
    }

    private com.smartgwt.client.widgets.layout.Layout createButtonsLayout() {
        com.smartgwt.client.widgets.layout.Layout buttonsLayout =
                new com.smartgwt.client.widgets.layout.Layout();
        buttonsLayout.setMargin(30);
        buttonsLayout.setMembersMargin(20);
        buttonsLayout.setAlign(Alignment.RIGHT);

        publish.setTitle(lang.publishItem());
        close.setTitle(lang.storno());

        buttonsLayout.addMember(publish);
        buttonsLayout.addMember(close);

        return buttonsLayout;
    }

    /**
     * Creates the newmodsCollectionClient
     * 
     * @return ModsCollectionClient the newmodsCollectionClient
     */
    public ModsCollectionClient publishWindow(DigitalObjectModel model) {

        String levelName = "";
        String partNumber = "";
        if (model == DigitalObjectModel.MONOGRAPHUNIT || model == DigitalObjectModel.PERIODICALITEM
                || model == DigitalObjectModel.PERIODICALVOLUME || model == DigitalObjectModel.INTERNALPART) {
            partNumber = objectBasicInfoLayout.getManagerLayout().getPartNumber();
            modsClientManager.modifyPartNumber(partNumber);

            if (model != DigitalObjectModel.INTERNALPART)
                modsClientManager.modifyNote(objectBasicInfoLayout.getManagerLayout().getNote());

            if (model != DigitalObjectModel.PERIODICALVOLUME) {
                levelName = objectBasicInfoLayout.getManagerLayout().getLevelName();
                modsClientManager.modifyLevelName(levelName);
                modsClientManager.modifyPartName(objectBasicInfoLayout.getManagerLayout().getNameOrTitle());
            }
        }

        if (model != DigitalObjectModel.MONOGRAPHUNIT && model != DigitalObjectModel.PERIODICALVOLUME)
            modsClientManager.modifyType(model,
                                         objectBasicInfoLayout.getManagerLayout().getType(model, levelName));

        String newDate =
                (dateItem == null) ? objectBasicInfoLayout.getManagerLayout().getDateIssued() : (dateItem
                        .getValue() != null ? dateItem.getValue().toString() : "");
        modsClientManager.modifyOriginInfoList(publisherItem.getEnteredValue(), newDate);

        String title = "";
        title =
                (titleItem == null) ? objectBasicInfoLayout.getManagerLayout().getNameOrTitle() : titleItem
                        .getEnteredValue();
        modsClientManager.modifyTitle(title, model);

        if (model == DigitalObjectModel.PERIODICALITEM) {
            title = partNumber;
        } else if (model == DigitalObjectModel.PERIODICALVOLUME) {
            title = newDate;
        }

        modsClientManager.modifySubtitle((subtitleItem == null) ? objectBasicInfoLayout.getManagerLayout()
                .getSubtitle() : subtitleItem.getEnteredValue());

        modsClientManager.modifyNames(authorPartsOfName1,
                                      authorPartsOfName2,
                                      author1Item.getTitle().equals(AUTHOR1) ? null : author1Item.getTitle(),
                                      author2Item.getTitle().equals(AUTHOR2) ? null : author2Item.getTitle());
        modsClientManager.modifyShelfLocatorAndPlace(shelfLocatorItem.getEnteredValue(),
                                                     placeItem.getEnteredValue());
        modsClientManager.modifyExtent(extentItem.getEnteredValue());

        setLabel(model, title);

        return modsCollection;
    }

    private void setLabel(DigitalObjectModel model, String title) {
        this.label =
                ClientCreateUtils.trimLabel((title != null && !"".equals(title) ? title : "untitled"),
                                            Constants.MAX_LABEL_LENGTH);
    }

    /**
     * Creates the newDublinCore
     * 
     * @param originalDC
     *        the original DublinCore
     * @param model
     * @return DublinCore the new DublinCore
     */
    public DublinCore reflectInDC(DublinCore originalDC, DigitalObjectModel model) {
        DublinCore DC = originalDC;

        if (isNotNullOrEmpty(DC.getTitle())) {
            DC.getTitle().remove(0);
        } else {
            DC.setTitle(new ArrayList<String>());
        }

        String title = getLabel();

        if (title != null && !title.trim().equals("")) {
            DC.getTitle().add(0, title.trim());
        }

        for (int i = 0; i < 2; i++) {
            StringBuffer bufferedAuthor = new StringBuffer("");
            StringBuffer originalAuthor = new StringBuffer("");

            for (FormItem item : (i == 0 ? authorPartsOfName1 : authorPartsOfName2)) {
                if (item instanceof MyTextItem) {
                    if (((MyTextItem) item).getMyDefaultValue() != null && !item.getTitle().trim().equals("")) {
                        originalAuthor.append(((MyTextItem) item).getMyDefaultValue());
                        originalAuthor.append(" ");
                    }
                }
                if (item.getValue() != null && !item.getValue().toString().trim().equals("")
                        && !item.getTitle().trim().equals("")) {
                    bufferedAuthor.append(item.getValue().toString().trim());
                    bufferedAuthor.append(" ");
                }
            }

            if (isNotNullOrEmpty(DC.getCreator())) {
                if (DC.getCreator().contains(originalAuthor.toString().trim())) {
                    DC.getCreator().remove(originalAuthor.toString().trim());
                }

            } else {
                DC.setCreator(new ArrayList<String>());
            }

            if (!bufferedAuthor.toString().trim().equals("")) {
                DC.getCreator().add(i, bufferedAuthor.toString().trim());
            }
        }

        if (isNotNullOrEmpty(DC.getDate())) {
            DC.getDate().remove(0);
        } else {
            DC.setDate(new ArrayList<String>());
        }

        String dateIssued =
                (dateItem == null) ? objectBasicInfoLayout.getManagerLayout().getDateIssued() : (dateItem
                        .getValue() != null ? dateItem.getValue().toString() : "");

        if (dateIssued != null && !dateIssued.toString().trim().equals("")) {
            DC.getDate().add(0, dateIssued.toString().trim());
        }

        if (isNotNullOrEmpty(DC.getPublisher())) {
            DC.getPublisher().remove(0);
        } else {
            DC.setPublisher(new ArrayList<String>());
        }
        if (publisherItem.getEnteredValue() != null && !publisherItem.getEnteredValue().trim().equals("")) {
            DC.getPublisher().add(0, publisherItem.getEnteredValue().trim());
        }

        return DC;
    }

    public String verify() {
        String message = objectBasicInfoLayout.getManagerLayout().verify();
        if (dateItem != null && (message == null || "".equals(message))) return dateItem.verify(lang, model);
        return message;
    }

    public String getLabel() {
        return label;
    }

    /**
     * @return publish the IButton for publishing
     */
    protected IButton getPublish() {
        return publish;
    }

    /**
     * @return close the IButton for closing
     */
    protected IButton getClose() {
        return close;
    }

    /**
     * @return isReflectInDC whether CheckboxItem reflectInDC is checked or not
     */
    public Boolean getReflectInDC() {
        return (Boolean) reflectInDC.getValue();
    }

    private boolean isNotNullOrEmpty(List<?> objectList) {
        return objectList != null && !objectList.isEmpty() && objectList.get(0) != null;
    }

    protected abstract void init();
}
