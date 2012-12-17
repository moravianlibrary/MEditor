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

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.gwtrpcds.UsersGwtRPCDS;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.window.ModalWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersLayout.
 * 
 * @author Matous Jobanek
 * @version Nov 26, 2012
 */
public class UsersLayout
        extends VLayout {

    /** The user grid. */
    private ListGrid userGrid;

    /** The remove user. */
    private IButton removeUser;

    /** The add user. */
    private IButton addUser;

    /** The lang. */
    private final LangConstants lang;

    /** The user data source. */
    final UsersGwtRPCDS userDataSource;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The event bus. */
    private final EventBus eventBus;

    /**
     * Instantiates a new users layout.
     * 
     * @param lang
     *        the lang
     * @param dispatcher
     *        the dispatcher
     * @param eventBus
     *        the event bus
     */
    public UsersLayout(LangConstants lang, final DispatchAsync dispatcher, EventBus eventBus) {
        super();
        this.lang = lang;
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;

        userDataSource = new UsersGwtRPCDS(dispatcher, lang);

        setWidth("90%");
        setHeight("70%");

        HTMLFlow users = new HTMLFlow(HtmlCode.bold(lang.users()));
        users.setHeight(15);

        addMember(users);
        setUserGrid();
        setButtonLayout();

    }

    /**
     * Sets the user grid.
     */
    private void setUserGrid() {
        this.userGrid = new ListGrid() {

            @Override
            protected Canvas getExpansionComponent(final ListGridRecord record) {

                return new UserDetailLayout(userDataSource, record, this, lang, dispatcher, eventBus);
            }
        };

        userGrid.setWidth100();
        userGrid.setHeight100();
        userGrid.setShowSortArrow(SortArrow.CORNER);
        userGrid.setShowAllRecords(true);
        userGrid.setAutoFetchData(true);
        userGrid.setCanSort(false); // TODO: sort by date (define in datasource)
        userGrid.setHoverOpacity(75);
        userGrid.setHoverStyle("interactImageHover");
        userGrid.setCanExpandRecords(true);
        userGrid.setMargin(5);
        userGrid.setDataSource(userDataSource);

        ListGridField nameField = new ListGridField(Constants.ATTR_NAME);
        ListGridField surnameField = new ListGridField(Constants.ATTR_SURNAME);
        userGrid.setFields(nameField, surnameField);

        addMember(userGrid);
    }

    /**
     * Sets the button layout.
     */
    private void setButtonLayout() {
        HLayout buttonLayout = new HLayout();
        buttonLayout.setPadding(5);
        addUser = new IButton(lang.addUser());
        addUser.setExtraSpace(10);
        removeUser = new IButton(lang.removeSelected());
        removeUser.setAutoFit(true);
        removeUser.setDisabled(true);
        removeUser.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ModalWindow mw = new ModalWindow(userGrid);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
                userGrid.removeSelectedData(new DSCallback() {

                    @Override
                    public void execute(DSResponse response, Object rawData, DSRequest request) {
                        removeUser.setDisabled(true);
                        mw.hide();
                    }
                }, null);
            }
        });
        userGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (userGrid.getSelectedRecords().length > 0) {
                    removeUser.setDisabled(false);
                } else {
                    removeUser.setDisabled(true);
                }
            }
        });
        buttonLayout.addMember(addUser);
        buttonLayout.addMember(removeUser);
        addMember(buttonLayout);
    }

    /**
     * Gets the user grid.
     * 
     * @return the userGrid
     */
    public ListGrid getUserGrid() {
        return userGrid;
    }

    /**
     * Gets the removes the user.
     * 
     * @return the removeUser
     */
    public IButton getRemoveUser() {
        return removeUser;
    }

    /**
     * Gets the adds the user.
     * 
     * @return the addUser
     */
    public IButton getAddUser() {
        return addUser;
    }

    /**
     * Gets the user data source.
     * 
     * @return the userDataSource
     */
    public UsersGwtRPCDS getUserDataSource() {
        return userDataSource;
    }

}
