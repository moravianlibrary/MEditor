
package cz.fi.muni.xkremser.editor.client.view.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeAttributeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalLocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public abstract class ModsWindow
        extends UniversalWindow {

    private final LangConstants lang;

    /** The TextItem with title value */
    private final TextItem titleItem = new MyTextItem();

    /** The TextItem with subtitle value */
    private final TextItem subtitleItem = new MyTextItem();

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

    /** The DateTimeItem with day value set to EUROPEANSHORTDATE-format */
    private final DateTimeItem dateItem = new DateTimeItem() {

        {
            setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
            setInputFormat(DateDisplayFormat.TOEUROPEANSHORTDATE.toString());
            setHoverOpacity(75);
            setHoverWidth(330);
            setHoverStyle("interactImageHover");
        }
    };

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
            this.setItems(items);
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
    public ModsWindow(ModsCollectionClient modsCollection, String uuid, LangConstants lang, EventBus eventBus) {
        super(450, 900, lang.quickEdit() + ": " + uuid, eventBus, 100);
        this.lang = lang;
        this.modsCollection = modsCollection;
        if (modsCollection != null) {
            modsTypeClient = modsCollection.getMods().get(0);
        } else {
            modsTypeClient = new ModsTypeClient();
        }
        setVariables();

        VStack mainLayout = new VStack();
        HStack itemsLayout = new HStack();

        itemsLayout.setMargin(20);
        itemsLayout.setMembersMargin(20);
        itemsLayout.addMember(createTitleSubtitle());
        itemsLayout.addMember(createNames());
        itemsLayout.addMember(create3Column());

        mainLayout.addMember(itemsLayout);
        mainLayout.addMember(createButtonsLayout());

        centerInPage();
        addItem(mainLayout);
        init();
    }

    private void setVariables() {

        titleItem.setTitle(lang.title());

        titleItem.setTooltip(lang.titleMARC());

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

        dateItem.setTitle(lang.issuedDateItem());
        dateItem.setTooltip(lang.issuedDate());

    }

    private DynamicForm createTitleSubtitle() {

        DynamicForm modsForm = new DynamicForm();

        if (isNotNullEmpty(modsTypeClient.getTitleInfo())) {

            List<String> titleList = modsTypeClient.getTitleInfo().get(0).getTitle();
            titleItem.setDefaultValue(isNotNullEmpty(titleList) ? titleList.get(0) : "");

            List<String> subtitleList = modsTypeClient.getTitleInfo().get(0).getSubTitle();
            subtitleItem.setDefaultValue(isNotNullEmpty(subtitleList) ? subtitleList.get(0) : "");
        }

        modsForm.setItems(titleItem, subtitleItem);
        return modsForm;
    }

    private VStack createNames() {

        VStack nameLayout = new VStack();
        nameLayout.setMembersMargin(6);

        for (int i = 0; i < 2; i++) {

            boolean isFamily = false;
            boolean isGiven = false;

            if (isNotNullEmpty(modsTypeClient.getName()) && modsTypeClient.getName().size() > i) {

                List<NamePartTypeClient> namePart = modsTypeClient.getName().get(i).getNamePart();
                if (isNotNullEmpty(namePart)) {
                    for (NamePartTypeClient part : namePart) {
                        if (part.getValue() != null && !part.getValue().trim().equals("")) {
                            if (part.getType() == null) {
                                part.setType("");
                            }

                            TextItem newItem = new MyTextItem();
                            newItem.setTitle(part.getType());
                            if (part.getType().equals("date")) newItem.setTooltip(lang.dateParse());
                            if (part.getType().equals("family")) newItem.setTooltip(lang.surname());
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
                newItem.setTooltip(lang.surname());
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
        if (isNotNullEmpty(roleList)) {
            boolean stop = false;
            for (RoleTypeClient roleType : roleList) {
                if (isNotNullEmpty(roleType.getRoleTerm()) && !stop) {
                    for (RoleTermClient roleTerm : roleType.getRoleTerm()) {

                        if (roleTerm.getValue().trim().equals("Author")
                                || roleTerm.getValue().trim().equals("author")
                                || roleTerm.getValue().trim().equals("Autor")
                                || roleTerm.getValue().trim().equals("autor")
                                || roleTerm.getValue().trim().equals("cre")) {

                            System.err.println("role value: " + roleTerm.getValue());
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

    private DynamicForm create3Column() {

        DynamicForm modsForm = new DynamicForm();

        if (isNotNullEmpty(modsTypeClient.getOriginInfo())) {

            List<String> publisherList = modsTypeClient.getOriginInfo().get(0).getPublisher();
            publisherItem.setDefaultValue(isNotNullEmpty(publisherList) ? publisherList.get(0) : "");

            List<DateTypeClient> dateIssueList = modsTypeClient.getOriginInfo().get(0).getDateIssued();

            if (!isNotNullEmpty(dateIssueList)) {

            }
            dateItem.setDefaultValue(isNotNullEmpty(dateIssueList) ? dateIssueList.get(0).getValue() : "");
        }

        if (isNotNullEmpty(modsTypeClient.getLocation())) {
            List<String> shelfLocatorList = modsTypeClient.getLocation().get(0).getShelfLocator();
            shelfLocatorItem.setDefaultValue(isNotNullEmpty(shelfLocatorList) ? shelfLocatorList.get(0) : "");

            List<PhysicalLocationTypeClient> placeList =
                    modsTypeClient.getLocation().get(0).getPhysicalLocation();
            placeItem.setDefaultValue(isNotNullEmpty(placeList) ? placeList.get(0).getValue() : "");
        }

        if (isNotNullEmpty(modsTypeClient.getPhysicalDescription())) {

            List<String> extentList = modsTypeClient.getPhysicalDescription().get(0).getExtent();
            extentItem.setDefaultValue(isNotNullEmpty(extentList) ? extentList.get(0) : "");
        }

        reflectInDC.setTitle(lang.changesInDC());
        reflectInDC.setDefaultValue(true);
        modsForm.setItems(dateItem, publisherItem, placeItem, shelfLocatorItem, extentItem, reflectInDC);
        return modsForm;
    }

    private com.smartgwt.client.widgets.layout.Layout createButtonsLayout() {
        com.smartgwt.client.widgets.layout.Layout buttonsLayout =
                new com.smartgwt.client.widgets.layout.Layout();
        buttonsLayout.setMargin(30);
        buttonsLayout.setMembersMargin(20);
        buttonsLayout.setAlign(Alignment.RIGHT);

        publish.setTitle(lang.publishItem());
        close.setTitle(lang.close());

        buttonsLayout.addMember(publish);
        buttonsLayout.addMember(close);

        return buttonsLayout;
    }

    /**
     * Creates the newmodsCollectionClient
     * 
     * @return ModsCollectionClient the newmodsCollectionClient
     */
    public ModsCollectionClient publishWindow() {
        ModsCollectionClient newModsCollClient = modsCollection;
        ModsTypeClient newModsTypeClient = modsTypeClient;

        newModsTypeClient.setOriginInfo(createNewOriginInfoList(newModsTypeClient));

        newModsTypeClient.setTitleInfo(createNewTitleInfoList(newModsTypeClient));

        newModsTypeClient.setName(createNewNameList(newModsTypeClient));

        newModsTypeClient.setLocation(createNewLocationList(newModsTypeClient));

        newModsTypeClient.setPhysicalDescription(createNewPhysDescrList(newModsTypeClient));

        newModsCollClient.getMods().remove(0);
        newModsCollClient.getMods().add(0, newModsTypeClient);

        return newModsCollClient;
    }

    private List<OriginInfoTypeClient> createNewOriginInfoList(ModsTypeClient newModsTypeClient) {
        /** Original type client part */
        List<OriginInfoTypeClient> newOriginalTypeClient;
        if (!isNotNullEmpty(newModsTypeClient.getOriginInfo())) {
            newOriginalTypeClient = new ArrayList<OriginInfoTypeClient>();
            newOriginalTypeClient.add(new OriginInfoTypeClient());

        } else {
            newOriginalTypeClient = newModsTypeClient.getOriginInfo();
        }

        /** Publisher list part */
        List<String> newPublisherList;
        if (!isNotNullEmpty(newOriginalTypeClient.get(0).getPublisher())) {
            newPublisherList = new ArrayList<String>();
        } else {
            newPublisherList = newOriginalTypeClient.get(0).getPublisher();
        }

        if (publisherItem.getEnteredValue() == null || publisherItem.getEnteredValue().trim().equals("")) {
            if (isNotNullEmpty(newPublisherList)) newPublisherList.remove(0);
        } else {
            if (isNotNullEmpty(newPublisherList)) {
                newPublisherList.remove(0);
            }
            newPublisherList.add(0, publisherItem.getEnteredValue().trim());
        }
        newOriginalTypeClient.get(0).setPublisher(newPublisherList);

        /** Date issue list part */
        List<DateTypeClient> dateIssueList;
        if (!isNotNullEmpty(newOriginalTypeClient.get(0).getDateIssued())) {
            dateIssueList = new ArrayList<DateTypeClient>();
        } else {
            dateIssueList = newModsTypeClient.getOriginInfo().get(0).getDateIssued();
        }

        if (dateItem.getValue() == null || dateItem.getValue().toString().trim().equals("")) {
            if (isNotNullEmpty(dateIssueList)) dateIssueList.remove(0);
        } else {
            if (isNotNullEmpty(dateIssueList)) {
                dateIssueList.get(0).setValue(dateItem.getValue().toString().trim());
            } else {

                DateTypeClient newDateType = new DateTypeClient();
                newDateType.setValue(dateItem.getValue().toString().trim());
                dateIssueList.add(0, newDateType);
            }
        }
        newOriginalTypeClient.get(0).setDateIssued(dateIssueList);

        return newOriginalTypeClient;
    }

    private List<TitleInfoTypeClient> createNewTitleInfoList(ModsTypeClient newModsTypeClient) {
        /** Title info part */
        List<TitleInfoTypeClient> newTitleInfo;
        if (!isNotNullEmpty(newModsTypeClient.getTitleInfo())) {
            newTitleInfo = new ArrayList<TitleInfoTypeClient>();
            newTitleInfo.add(new TitleInfoTypeClient());
        } else {
            newTitleInfo = newModsTypeClient.getTitleInfo();
        }

        /** Title part */
        if (titleItem.getEnteredValue() == null || titleItem.getEnteredValue().trim().equals("")) {

            if (isNotNullEmpty(newTitleInfo.get(0).getTitle())) {
                newTitleInfo.get(0).getTitle().remove(0);
            }
        } else {
            if (isNotNullEmpty(newTitleInfo.get(0).getTitle())) {
                newTitleInfo.get(0).getTitle().remove(0);
            } else {
                newTitleInfo.get(0).setTitle(new ArrayList<String>());
            }
            newTitleInfo.get(0).getTitle().add(0, titleItem.getEnteredValue().trim());
        }

        /** Subtitle part */
        if (subtitleItem.getEnteredValue() == null || subtitleItem.getEnteredValue().trim().equals("")) {

            if (isNotNullEmpty(newTitleInfo.get(0).getSubTitle())) {
                newTitleInfo.get(0).getSubTitle().remove(0);
            }
        } else {
            if (isNotNullEmpty(newTitleInfo.get(0).getSubTitle())) {
                newTitleInfo.get(0).getSubTitle().remove(0);
            } else {
                newTitleInfo.get(0).setSubTitle(new ArrayList<String>());
            }
            newTitleInfo.get(0).getSubTitle().add(0, subtitleItem.getEnteredValue().trim());

        }
        return newTitleInfo;
    }

    private List<NameTypeClient> createNewNameList(ModsTypeClient newModsTypeClient) {
        /** Name part */

        List<NameTypeClient> newNameList;
        if (!isNotNullEmpty(newModsTypeClient.getName())) {
            newNameList = new ArrayList<NameTypeClient>();
        } else {
            newNameList = newModsTypeClient.getName();
        }

        int index = 0;
        for (int i = 0; i < 2; i++) {

            List<NamePartTypeClient> nameParts = new ArrayList<NamePartTypeClient>();
            boolean allIsEmpty = true;
            for (FormItem item : (i == 0 ? authorPartsOfName1 : authorPartsOfName2)) {

                if (item.getValue() != null && !item.getValue().toString().trim().equals("")) {
                    NamePartTypeClient part = new NamePartTypeClient();
                    part.setValue(item.getValue().toString());
                    part.setType(item.getTitle().toString());
                    nameParts.add(part);
                    allIsEmpty = false;
                }
            }

            if (newNameList.size() <= index && (!allIsEmpty)) {
                newNameList.add(index, new NameTypeClient());
                newNameList.get(index).setRole(new ArrayList<RoleTypeClient>());
                newNameList.get(index).getRole().add(new RoleTypeClient());
                newNameList.get(index).getRole().get(0)
                        .setRoleTerm(new ArrayList<RoleTypeClient.RoleTermClient>());

                RoleTermClient newRoleTermCode = new RoleTermClient();
                RoleTermClient newRoleTermText = new RoleTermClient();

                if ((i == 0 ? author1Item : author2Item).getTitle().equals((i == 0 ? AUTHOR1 : AUTHOR2))) {
                    newRoleTermCode.setValue("cre");
                    newRoleTermText.setValue("Author");

                } else {
                    newRoleTermCode.setValue("");
                    newRoleTermText.setValue((i == 0 ? author1Item : author2Item).getTitle());
                }

                newRoleTermCode.setType(CodeOrTextClient.CODE);
                newRoleTermText.setType(CodeOrTextClient.TEXT);
                newNameList.get(index).getRole().get(0).getRoleTerm().add(newRoleTermCode);
                newNameList.get(index).getRole().get(0).getRoleTerm().add(newRoleTermText);
            }

            if (allIsEmpty) {
                if (newNameList.size() > index) newNameList.remove(index);

            } else {

                newNameList.get(index).setNamePart(nameParts);
                if (newNameList.get(index).getType() == null) {
                    newNameList.get(index).setType(NameTypeAttributeClient.PERSONAL);
                }
                index++;
            }
        }
        return newNameList;
    }

    private List<LocationTypeClient> createNewLocationList(ModsTypeClient newModsTypeClient) {
        /** Location list part */
        List<LocationTypeClient> newLocationList;
        if (!isNotNullEmpty(newModsTypeClient.getLocation())) {
            newLocationList = new ArrayList<LocationTypeClient>();
            newLocationList.add(new LocationTypeClient());
        } else {
            newLocationList = newModsTypeClient.getLocation();
        }

        /** Shelf locator part */
        if (shelfLocatorItem.getEnteredValue() == null
                || shelfLocatorItem.getEnteredValue().trim().equals("")) {
            if (isNotNullEmpty(newLocationList.get(0).getShelfLocator())) {
                newLocationList.get(0).getShelfLocator().remove(0);
            }
        } else {
            if (isNotNullEmpty(newLocationList.get(0).getShelfLocator())) {
                newLocationList.get(0).getShelfLocator().remove(0);
            } else {
                newLocationList.get(0).setShelfLocator(new ArrayList<String>());
            }
            newLocationList.get(0).getShelfLocator().add(0, shelfLocatorItem.getEnteredValue().trim());
        }

        /** Physical location part */
        if (placeItem.getEnteredValue() == null || placeItem.getEnteredValue().trim().equals("")) {
            if (isNotNullEmpty(newLocationList.get(0).getPhysicalLocation())) {
                newLocationList.get(0).getPhysicalLocation().remove(0);
            }
        } else {
            if (isNotNullEmpty(newLocationList.get(0).getPhysicalLocation())) {
                newLocationList.get(0).getPhysicalLocation().get(0)
                        .setValue(placeItem.getEnteredValue().trim());
            } else {
                newLocationList.get(0).setPhysicalLocation(new ArrayList<PhysicalLocationTypeClient>());
                newLocationList.get(0).getPhysicalLocation().add(new PhysicalLocationTypeClient());
                newLocationList.get(0).getPhysicalLocation().get(0)
                        .setValue(placeItem.getEnteredValue().trim());
            }
        }
        return newLocationList;
    }

    private List<PhysicalDescriptionTypeClient> createNewPhysDescrList(ModsTypeClient newModsTypeClient) {
        /** Extent part */
        List<PhysicalDescriptionTypeClient> newPhysDescrList;
        if (!isNotNullEmpty(modsTypeClient.getPhysicalDescription())) {
            newPhysDescrList = new ArrayList<PhysicalDescriptionTypeClient>();
            newPhysDescrList.add(new PhysicalDescriptionTypeClient());
        } else {
            newPhysDescrList = modsTypeClient.getPhysicalDescription();
        }

        if (extentItem.getEnteredValue() == null || extentItem.getEnteredValue().trim().equals("")) {
            if (isNotNullEmpty(newPhysDescrList.get(0).getExtent())) {
                newPhysDescrList.get(0).getExtent().remove(0);
            }
        } else {
            if (isNotNullEmpty(newPhysDescrList.get(0).getExtent())) {
                newPhysDescrList.get(0).getExtent().remove(0);
            } else {
                newPhysDescrList.get(0).setExtent(new ArrayList<String>());
            }
            newPhysDescrList.get(0).getExtent().add(0, extentItem.getEnteredValue().trim());
        }
        return newPhysDescrList;
    }

    /**
     * Creates the newDublinCore
     * 
     * @param originalDC
     *        the original DublinCore
     * @return DublinCore the new DublinCore
     */
    public DublinCore reflectInDC(DublinCore originalDC) {
        DublinCore DC = originalDC;

        if (isNotNullEmpty(DC.getTitle())) {
            DC.getTitle().remove(0);
        } else {
            DC.setTitle(new ArrayList<String>());
        }
        if (titleItem.getEnteredValue() != null & !titleItem.getEnteredValue().trim().equals("")) {
            String title = titleItem.getEnteredValue().trim();
            DC.getTitle().add(0, title);
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

            if (isNotNullEmpty(DC.getCreator())) {
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

        if (isNotNullEmpty(DC.getDate())) {
            DC.getDate().remove(0);
        } else {
            DC.setDate(new ArrayList<String>());
        }
        if (dateItem.getValue() != null && !dateItem.getValue().toString().trim().equals("")) {
            DC.getDate().add(0, dateItem.getValue().toString().trim());
        }

        if (isNotNullEmpty(DC.getPublisher())) {
            DC.getPublisher().remove(0);
        } else {
            DC.setPublisher(new ArrayList<String>());
        }
        if (publisherItem.getEnteredValue() != null && !publisherItem.getEnteredValue().trim().equals("")) {
            DC.getPublisher().add(0, publisherItem.getEnteredValue().trim());
        }

        return DC;
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

    private boolean isNotNullEmpty(Object o) {
        List<?> objectList = (List<?>) o;
        return objectList != null && !objectList.isEmpty();
    }

    protected abstract void init();
}
