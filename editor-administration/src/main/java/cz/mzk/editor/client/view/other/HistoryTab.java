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

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.mzk.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version Nov 9, 2012
 */
public abstract class HistoryTab
        extends Tab {

    private final HistoryDays historyDays;
    private final HistoryItems historyItems;

    private final HTMLFlow titleFlow;
    private final LangConstants lang;
    private final VStack mainLayout;
    private final DispatchAsync dispatcher;

    /**
     * 
     */
    public HistoryTab(EventBus eventBus,
                      final LangConstants lang,
                      DispatchAsync dispatcher,
                      String label,
                      Long userId,
                      String uuid,
                      String tabTitle) {
        super();
        this.lang = lang;
        this.mainLayout = new VStack();
        this.dispatcher = dispatcher;

        setTitle(tabTitle);
        setWidth(tabTitle.length() * 6 + 35);

        titleFlow = new HTMLFlow(label);
        Layout titleLayout = new Layout();
        titleLayout.addMember(titleFlow);
        titleFlow.setWidth(300);
        titleLayout.setAlign(VerticalAlignment.TOP);
        titleLayout.setHeight(10);
        titleLayout.setMargin(10);
        titleLayout.setWidth(300);

        mainLayout.setWidth100();
        mainLayout.setHeight100();
        mainLayout.addMember(titleLayout);
        setSelection();

        if (userId != null) {
            historyDays = new UserHistoryDays(lang, dispatcher, eventBus, userId);
        } else {
            historyDays = new DOHistoryDays(lang, dispatcher, eventBus, uuid);
        }
        historyItems = new HistoryItems(lang, eventBus, userId != null);

        HLayout historyLayout = new HLayout(2);
        historyLayout.setAnimateMembers(true);
        historyLayout.setHeight("85%");
        historyLayout.setMargin(10);

        historyLayout.addMember(historyDays);
        historyLayout.addMember(historyItems);

        mainLayout.addMember(historyLayout);
        setPane(mainLayout);
    }

    protected abstract void setSelection();

    /**
     * @return the historyItems
     */
    public HistoryItems getHistoryItems() {
        return historyItems;
    }

    /**
     * @return the historyDays
     */
    protected HistoryDays getHistoryDays() {
        return historyDays;
    }

    /**
     * @return the title
     */
    protected HTMLFlow getTitleFlow() {
        return titleFlow;
    }

    /**
     * @return the dispatcher
     */
    protected DispatchAsync getDispatcher() {
        return dispatcher;
    }

    /**
     * @return the mainLayout
     */
    protected VStack getMainLayout() {
        return mainLayout;
    }

    /**
     * @return the lang
     */
    protected LangConstants getLang() {
        return lang;
    }

}
