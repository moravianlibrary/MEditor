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

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.HistoryPresenter;
import cz.mzk.editor.client.uihandlers.HistoryUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.HistoryGrid;

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
    private final HistoryGrid historyGrid;
    private final DispatchAsync dispatcher;

    @Inject
    public HistoryView(EventBus eventBus, LangConstants lang, DispatchAsync dispatcher) {
        this.eventBus = eventBus;
        this.lang = lang;
        this.dispatcher = dispatcher;

        this.mainLayout = new VStack();
        //        mainLayout.setPadding(10);

        HTMLFlow title = new HTMLFlow(HtmlCode.title(lang.historyMenu(), 2));
        Layout titleLayout = new Layout();
        titleLayout.addMember(title);
        titleLayout.setAlign(VerticalAlignment.TOP);
        titleLayout.setHeight(30);
        titleLayout.setMargin(10);

        mainLayout.addMember(titleLayout);

        historyGrid = new HistoryGrid(lang, dispatcher);
        mainLayout.addMember(historyGrid);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
