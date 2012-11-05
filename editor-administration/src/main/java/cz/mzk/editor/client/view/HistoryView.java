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

package cz.mzk.editor.client.view;

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.HistoryPresenter;
import cz.mzk.editor.client.uihandlers.HistoryUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.HistoryDays;
import cz.mzk.editor.shared.rpc.HistoryItem;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class HistoryView
        extends ViewWithUiHandlers<HistoryUiHandlers>
        implements HistoryPresenter.MyView {

    private final EventBus eventBus;
    private final LangConstants lang;
    private final VStack mainLayout;
    private final HistoryDays historyDays;
    private final DispatchAsync dispatcher;
    private final ListGrid historyItemsGrid;
    private static final String ATTR_TIMESTAMP = "timestamp";
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_TABLE_NAME = "tableName";
    private static final String ATTR_OBJECT = "object";

    @Inject
    public HistoryView(EventBus eventBus, LangConstants lang, DispatchAsync dispatcher) {
        this.eventBus = eventBus;
        this.lang = lang;
        this.dispatcher = dispatcher;

        this.mainLayout = new VStack();

        HTMLFlow title = new HTMLFlow(HtmlCode.title(lang.historyMenu(), 2));
        Layout titleLayout = new Layout();
        titleLayout.addMember(title);
        titleLayout.setAlign(VerticalAlignment.TOP);
        titleLayout.setHeight(30);
        titleLayout.setMargin(10);

        mainLayout.addMember(titleLayout);

        historyDays = new HistoryDays(lang, dispatcher, eventBus);
        historyItemsGrid = new ListGrid();
        ListGridField timestampField = new ListGridField(ATTR_TIMESTAMP);
        ListGridField nameField = new ListGridField(ATTR_TABLE_NAME);
        ListGridField actionField = new ListGridField(ATTR_ACTION);
        ListGridField objectField = new ListGridField(ATTR_OBJECT);

        historyItemsGrid.setFields(timestampField, nameField, actionField, objectField);
        historyItemsGrid.setHeight("90%");
        historyItemsGrid.setWidth100();
        historyItemsGrid.setShowEdges(true);
        historyItemsGrid.setEdgeSize(4);
        historyItemsGrid.setEdgeOpacity(60);
        historyItemsGrid.setTop(20);
        historyItemsGrid.setBottom(20);
        historyItemsGrid.setMargin(5);

        HLayout historyLayout = new HLayout(2);
        historyLayout.setAnimateMembers(true);
        historyLayout.setHeight("90%");
        historyLayout.setMargin(10);

        historyLayout.addMember(historyDays);
        historyLayout.addMember(historyItemsGrid);

        mainLayout.addMember(historyLayout);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHistoryItems(List<HistoryItem> historyItems) {
        ListGridRecord[] historyItemRecords = new ListGridRecord[historyItems.size()];

        int index = 0;
        for (HistoryItem item : historyItems) {
            historyItemRecords[index++] = getHistoryItemRecord(item);
        }

        historyItemsGrid.setData(historyItemRecords);
        //        historyItemsGrid.redraw();

    }

    private ListGridRecord getHistoryItemRecord(HistoryItem historyItem) {
        ListGridRecord historyItemRecord = new ListGridRecord();
        historyItemRecord.setAttribute(ATTR_TIMESTAMP, historyItem.getTimestamp());
        historyItemRecord.setAttribute(ATTR_TABLE_NAME, historyItem.getTableName());
        historyItemRecord.setAttribute(ATTR_ACTION, historyItem.getAction());
        historyItemRecord.setAttribute(ATTR_OBJECT, historyItem.getObject());
        return historyItemRecord;
    }

}
