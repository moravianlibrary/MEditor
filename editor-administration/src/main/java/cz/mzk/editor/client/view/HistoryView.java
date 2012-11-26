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
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.TabSet;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.HistoryPresenter;
import cz.mzk.editor.client.uihandlers.HistoryUiHandlers;
import cz.mzk.editor.client.view.other.DOHistoryTab;
import cz.mzk.editor.client.view.other.HistoryTab;
import cz.mzk.editor.client.view.other.UserHistoryTab;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class HistoryView
        extends ViewWithUiHandlers<HistoryUiHandlers>
        implements HistoryPresenter.MyView {

    private final VStack mainLayout;
    private final HistoryTab userHistoryTab;
    private final HistoryTab digObjHistoryTab;

    @Inject
    public HistoryView(EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.mainLayout = new VStack();

        mainLayout.setWidth100();
        mainLayout.setHeight100();

        userHistoryTab = new UserHistoryTab(eventBus, lang, dispatcher);
        digObjHistoryTab = new DOHistoryTab(eventBus, lang, dispatcher);

        TabSet historyTabs = new TabSet();
        historyTabs.setAnimateTabScrolling(true);
        historyTabs.setShowPaneContainerEdges(false);
        historyTabs.setHeight100();
        historyTabs.setTabs(userHistoryTab, digObjHistoryTab);

        mainLayout.addMember(historyTabs);
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
    public void setHistoryItems(List<HistoryItem> hItems, boolean isUserH) {
        if (isUserH) {
            userHistoryTab.getHistoryItems().setHistoryItems(hItems);
        } else {
            digObjHistoryTab.getHistoryItems().setHistoryItems(hItems);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showHistoryItemInfo(HistoryItemInfo historyItemInfo,
                                    HistoryItem eventHistoryItem,
                                    boolean isUserH) {
        if (isUserH) {
            userHistoryTab.getHistoryItems().showHistoryItemInfo(historyItemInfo, eventHistoryItem);
        } else {
            digObjHistoryTab.getHistoryItems().showHistoryItemInfo(historyItemInfo, eventHistoryItem);
        }
    }

}
