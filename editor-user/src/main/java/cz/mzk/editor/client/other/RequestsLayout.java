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

package cz.mzk.editor.client.other;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.window.ModalWindow;

/**
 * @author Matous Jobanek
 * @version Nov 25, 2012
 */
public class RequestsLayout
        extends VLayout {

    /** The requests grid. */
    private final ListGrid requestsGrid;

    /** The remove request. */
    private final IButton removeRequest;

    /**
     * 
     */
    public RequestsLayout(LangConstants lang) {
        super();

        HTMLFlow requests = new HTMLFlow(HtmlCode.bold(lang.requests()));
        requests.setHeight(15);
        addMember(requests);
        setWidth("90%");
        setHeight("*");

        requestsGrid = new ListGrid();
        requestsGrid.setWidth100();
        requestsGrid.setHeight100();
        requestsGrid.setShowSortArrow(SortArrow.CORNER);
        requestsGrid.setShowAllRecords(true);
        requestsGrid.setAutoFetchData(true);
        requestsGrid.setCanHover(true);
        requestsGrid.setCanSort(false);
        requestsGrid.setHoverOpacity(75);
        requestsGrid.setHoverStyle("interactImageHover");
        requestsGrid.setCanEdit(true);
        requestsGrid.setMargin(5);
        addMember(requestsGrid);

        HLayout buttonLayout = new HLayout();
        buttonLayout.setPadding(5);
        removeRequest = new IButton(lang.removeSelected());
        removeRequest.setAutoFit(true);
        removeRequest.setDisabled(true);
        removeRequest.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ModalWindow mw = new ModalWindow(requestsGrid);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
                requestsGrid.removeSelectedData(new DSCallback() {

                    @Override
                    public void execute(DSResponse response, Object rawData, DSRequest request) {
                        mw.hide();
                    }
                }, null);
            }
        });
        requestsGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selection = event.getSelection();
                if (selection != null && selection.length > 0) {
                    removeRequest.setDisabled(false);
                } else {
                    removeRequest.setDisabled(true);
                }
            }
        });

        buttonLayout.addMember(removeRequest);
        addMember(buttonLayout);
    }

    /**
     * @return the requestsGrid
     */
    public ListGrid getRequestsGrid() {
        return requestsGrid;
    }

}
