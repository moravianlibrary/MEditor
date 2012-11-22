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

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;
import com.smartgwt.client.widgets.viewer.DetailViewerRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.CRUD_ACTION_TYPES;
import cz.mzk.editor.client.view.window.SubobjectsWindow;
import cz.mzk.editor.shared.event.GetHistoryItemInfoEvent;
import cz.mzk.editor.shared.rpc.EditorDate;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;

/**
 * @author Matous Jobanek
 * @version Nov 7, 2012
 */
public class HistoryItems
        extends VLayout {

    private ListGrid historyItemsGrid;
    private final LangConstants lang;
    private HLayout bottomLayout;
    private HLayout detailLayout;
    private final EventBus eventBus;
    private DetailViewer detailViewer;
    private IButton showChildren;
    private final boolean isUserH;

    /**
     * 
     */
    public HistoryItems(LangConstants lang, EventBus eventBus, boolean isUserH) {
        super();
        this.eventBus = eventBus;
        this.lang = lang;
        this.isUserH = isUserH;

        setHistoryItemsGrid();
        setAnimateMembers(true);
        setAnimateResizeTime(200);
        addMember(historyItemsGrid);
        addMember(bottomLayout);
    }

    private void setHistoryItemsGrid() {
        historyItemsGrid = new ListGrid();
        historyItemsGrid.setWidth100();
        historyItemsGrid.setShowEdges(true);
        historyItemsGrid.setEdgeSize(4);
        historyItemsGrid.setEdgeOpacity(60);
        historyItemsGrid.setTop(20);
        historyItemsGrid.setBottom(20);
        historyItemsGrid.setMargin(5);
        historyItemsGrid.setGroupStartOpen(GroupStartOpen.ALL);
        historyItemsGrid.setCanHover(true);
        historyItemsGrid.setHoverWidth(200);

        setHistoryGridFields();
        setBottomLayout();
    }

    private void setBottomLayout() {
        final IButton groupButton = new IButton(lang.group());
        groupButton.setExtraSpace(10);
        groupButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (groupButton.getTitle().equals(lang.group())) {
                    historyItemsGrid.groupBy(Constants.ATTR_ACTION);
                    groupButton.setTitle(lang.ungroup());
                } else {
                    historyItemsGrid.ungroup();
                    groupButton.setTitle(lang.group());
                }
            }
        });

        historyItemsGrid.addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                ListGridRecord record = event.getRecord();

                if (!"".equals(record.getAttributeAsString(Constants.ATTR_IS_MORE_INFO))) {
                    HistoryItem eventHistoryItem =
                            new HistoryItem(record.getAttributeAsLong(Constants.ATTR_ID),
                                            new EditorDate(record
                                                    .getAttributeAsString(Constants.ATTR_TIMESTAMP)),
                                            null,
                                            record.getAttributeAsString(Constants.ATTR_TABLE_NAME),
                                            record.getAttributeAsString(Constants.ATTR_ACTION),
                                            true);
                    eventBus.fireEvent(new GetHistoryItemInfoEvent(eventHistoryItem, isUserH));
                } else {
                    hideDetailViewer();
                }
            }
        });
        bottomLayout = new HLayout();
        bottomLayout.setAlign(VerticalAlignment.TOP);
        bottomLayout.setAlign(Alignment.RIGHT);
        bottomLayout.addMember(groupButton);
        bottomLayout.setHeight(10);
        bottomLayout.setMargin(5);
    }

    private void setHistoryGridFields() {

        ListGridField timestampField = new ListGridField(Constants.ATTR_TIMESTAMP);
        timestampField.setTitle(lang.when());
        timestampField.setWidth("15%");

        ListGridField nameField = new ListGridField(Constants.ATTR_ACTION);
        nameField.setTitle(lang.action());
        nameField.setWidth("20%");

        ListGridField objectField = new ListGridField(Constants.ATTR_OBJECT);
        objectField.setTitle(isUserH ? lang.object() : lang.user());
        objectField.setWidth("65%");

        ListGridField isMoreInfoField = new ListGridField(Constants.ATTR_IS_MORE_INFO);
        isMoreInfoField.setTitle(" ");
        isMoreInfoField.setAlign(Alignment.CENTER);
        isMoreInfoField.setWidth(16);
        isMoreInfoField.setType(ListGridFieldType.IMAGE);
        isMoreInfoField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                if (record.getAttribute(Constants.ATTR_IS_MORE_INFO) == null
                        || record.getAttributeAsString(Constants.ATTR_IS_MORE_INFO).equals("")) {
                    return "";
                } else {
                    return lang.infoClick();
                }
            }
        });
        historyItemsGrid.setFields(isMoreInfoField, timestampField, nameField, objectField);

    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        ListGridRecord[] historyItemRecords = new ListGridRecord[historyItems.size()];

        int index = 0;
        for (HistoryItem item : historyItems) {
            historyItemRecords[index++] = getHistoryItemRecord(item);
        }

        historyItemsGrid.setData(historyItemRecords);
        hideDetailViewer();

    }

    private ListGridRecord getHistoryItemRecord(HistoryItem historyItem) {
        ListGridRecord historyItemRecord = new ListGridRecord();
        historyItemRecord.setAttribute(Constants.ATTR_TIMESTAMP, historyItem.getTimestamp().toString());
        historyItemRecord.setAttribute(Constants.ATTR_ACTION, formatRecordOutput(historyItem));
        historyItemRecord.setAttribute(Constants.ATTR_TABLE_NAME, historyItem.getTableName());
        historyItemRecord.setAttribute(Constants.ATTR_OBJECT, historyItem.getObject());
        historyItemRecord.setAttribute(Constants.ATTR_IS_MORE_INFO,
                                       historyItem.isMoreInformation() ? "icons/16/info.png" : "");
        historyItemRecord.setAttribute(Constants.ATTR_ID, historyItem.getId());

        return historyItemRecord;
    }

    private String formatRecordOutput(HistoryItem item) {
        String tableName = item.getTableName();
        CRUD_ACTION_TYPES action = item.getAction();

        if (tableName.equals(Constants.TABLE_LOG_IN_OUT)) {
            return CRUD_ACTION_TYPES.CREATE == action ? lang.logIn() : lang.logOut();

        } else if (tableName.equals(Constants.TABLE_CONVERSION)) {
            return lang.startConversion();

        } else if (tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION)) {
            return getVerb(action, false) + lang.ofLock();

        } else if (tableName.equals(Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION)) {
            return getVerb(action, true)
                    + myStringFormat(lang.ofObjCreated(),
                                     (action == CRUD_ACTION_TYPES.CREATE) ? new String("") : lang.ofSaved());

        } else if (tableName.equals(Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION)
                || tableName.equals(Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT)) {
            return getVerb(action, false) + lang.ofDigObj();

        } else if (tableName.equals(Constants.TABLE_CRUD_REQUEST_TO_ADMIN_ACTION)) {
            return getVerb(action, false) + lang.ofRequest();

        } else if (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION)) {
            return getVerb(action, true)
                    + myStringFormat(lang.ofObjEdited(),
                                     (action == CRUD_ACTION_TYPES.CREATE) ? new String("") : lang.ofSaved());

        } else if (tableName.equals(Constants.TABLE_LONG_RUNNING_PROCESS)) {
            return getVerb(action, false) + lang.ofLongProc();

        } else {
            return lang.unspecAction();
        }

    }

    private String myStringFormat(String origString, String replacement) {
        return origString.replace("%s", replacement);
    }

    private String getVerb(CRUD_ACTION_TYPES action, boolean isSaving) {
        String toReturn = "";
        switch (action) {
            case CREATE:
                toReturn = isSaving ? lang.saving() : lang.creation();
                break;

            case DELETE:
                toReturn = lang.deletion();
                break;

            case UPDATE:
                toReturn = lang.modification();
                break;

            case READ:
                toReturn = lang.opening();
                break;

            default:
                SC.warn("Unsupposted verb - please contact the administrator!!!");
                break;
        }
        return toReturn + " ";
    }

    public void showHistoryItemInfo(final HistoryItemInfo historyItemInfo, HistoryItem eventHistoryItem) {

        if (detailViewer == null) {
            detailViewer = new DetailViewer();
            detailViewer.setWidth(500);
            detailViewer.setCanSelectText(true);
        }
        if (detailLayout == null) {
            detailLayout = new HLayout(2);
            detailLayout.setLayoutAlign(Alignment.LEFT);
            detailLayout.addMember(detailViewer);
        }

        if (showChildren != null && detailLayout.contains(showChildren)) {
            detailLayout.removeMember(showChildren);
        }

        if (!bottomLayout.contains(detailLayout)) {
            bottomLayout.addMember(detailLayout, 0);
        }

        detailViewer.setData(setFieldsGetRecord(historyItemInfo, eventHistoryItem));

        if (eventHistoryItem.getTableName().equals(Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT)
                && historyItemInfo.getChildren() != null) {

            if (showChildren == null) {
                showChildren = new IButton(lang.subobjects());
                showChildren.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        new SubobjectsWindow(lang, eventBus, historyItemInfo.getChildren());
                    }
                });
            }
            detailLayout.addMember(showChildren);
        }
        bottomLayout.redraw();
    }

    private void hideDetailViewer() {
        if (detailLayout != null && bottomLayout.contains(detailLayout)) {
            bottomLayout.removeMember(detailLayout);
        }
    }

    private DetailViewerRecord[] setFieldsGetRecord(HistoryItemInfo historyItemInfo,
                                                    HistoryItem eventHistoryItem) {

        DetailViewerField[] fields = new DetailViewerField[10];
        DetailViewerRecord infoRecord = new DetailViewerRecord();
        String tableName = eventHistoryItem.getTableName();
        int index = 0;

        fields[index++] = new DetailViewerField(Constants.ATTR_TIMESTAMP, lang.when());
        infoRecord.setAttribute(Constants.ATTR_TIMESTAMP, eventHistoryItem.getTimestamp());

        fields[index++] = new DetailViewerField(Constants.ATTR_ACTION, lang.action());
        infoRecord.setAttribute(Constants.ATTR_ACTION, eventHistoryItem.getObject());

        if (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION)) {
            fields[index++] = new DetailViewerField(Constants.ATTR_FILE_NAME, lang.fileName());
            infoRecord.setAttribute(Constants.ATTR_FILE_NAME, historyItemInfo.getPath());
        }

        if ((tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION))
                || (tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION))) {

            fields[index++] = new DetailViewerField(Constants.ATTR_DESC, lang.description());
            infoRecord.setAttribute(Constants.ATTR_DESC, historyItemInfo.getDescription());

            fields[index++] = new DetailViewerField(Constants.ATTR_DESC, lang.description());
            infoRecord.setAttribute(Constants.ATTR_DESC, historyItemInfo.getDescription());

            fields[index++] = new DetailViewerField(Constants.ATTR_STATE, lang.stateItem());
            if (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION)) {
                infoRecord.setAttribute(Constants.ATTR_STATE,
                                        getState(lang.theFile(), historyItemInfo.isState()));
            } else {
                infoRecord.setAttribute(Constants.ATTR_STATE,
                                        getState(lang.theLock(), historyItemInfo.isState()));
            }
        }

        if ((tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION))
                || (tableName.equals(Constants.TABLE_CRUD_SAVED_EDITED_OBJECT_ACTION))
                || (tableName.equals(Constants.TABLE_CRUD_DIGITAL_OBJECT_ACTION))
                || (tableName.equals(Constants.TABLE_CRUD_DO_ACTION_WITH_TOP_OBJECT))) {

            fields[index++] = new DetailViewerField(Constants.ATTR_UUID, "PID");
            infoRecord.setAttribute(Constants.ATTR_UUID, historyItemInfo.getUuid());

            fields[index++] = new DetailViewerField(Constants.ATTR_NAME, lang.title());
            infoRecord.setAttribute(Constants.ATTR_NAME, historyItemInfo.getName());

            fields[index++] = new DetailViewerField(Constants.ATTR_MODEL, "Model");
            infoRecord.setAttribute(Constants.ATTR_MODEL, historyItemInfo.getModel());

            if (!tableName.equals(Constants.TABLE_CRUD_LOCK_ACTION)) {
                fields[index++] = new DetailViewerField(Constants.ATTR_DESC, lang.publicDesc());
                infoRecord.setAttribute(Constants.ATTR_DESC, historyItemInfo.getDescription());

                fields[index++] = new DetailViewerField(Constants.ATTR_STATE, lang.stateItem());
                infoRecord.setAttribute(Constants.ATTR_STATE,
                                        getState(lang.theDigObj(), historyItemInfo.isState()));
            }

        } else if (tableName.equals(Constants.TABLE_CRUD_TREE_STRUCTURE_ACTION)) {
            fields[index++] = new DetailViewerField(Constants.ATTR_BARCODE, lang.fbarcode());
            infoRecord.setAttribute(Constants.ATTR_BARCODE, historyItemInfo.getBarcode());

            fields[index++] = new DetailViewerField(Constants.ATTR_INPUT_PATH, lang.inputQueue());
            infoRecord.setAttribute(Constants.ATTR_INPUT_PATH, historyItemInfo.getPath());

            fields[index++] = new DetailViewerField(Constants.ATTR_NAME, lang.name());
            infoRecord.setAttribute(Constants.ATTR_NAME, historyItemInfo.getName());

            fields[index++] = new DetailViewerField(Constants.ATTR_MODEL, "Model");
            infoRecord.setAttribute(Constants.ATTR_MODEL, historyItemInfo.getModel());

            fields[index++] = new DetailViewerField(Constants.ATTR_DESC, lang.description());
            infoRecord.setAttribute(Constants.ATTR_DESC, historyItemInfo.getDescription());

            fields[index++] = new DetailViewerField(Constants.ATTR_STATE, lang.stateItem());
            infoRecord.setAttribute(Constants.ATTR_STATE,
                                    getState(lang.theSavedStruc(), historyItemInfo.isState()));

        }

        detailViewer.setFields(fields);
        return new DetailViewerRecord[] {infoRecord};
    }

    private String getState(String prefix, boolean state) {
        return prefix + " " + (state ? lang.stilExist() : lang.noExist());
    }

    public void removeAllData() {
        hideDetailViewer();
        historyItemsGrid.setData(new ListGridRecord[] {});
    }
}
