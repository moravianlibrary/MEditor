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

package cz.mzk.editor.client.view;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.other.RequestsLayout;
import cz.mzk.editor.client.other.UsersLayout;
import cz.mzk.editor.client.presenter.UserPresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class UserView
        extends ViewImpl
        implements UserPresenter.MyView {

    /** The layout. */
    private final VLayout layout;

    private final RequestsLayout requestsLayout;

    private final UsersLayout usersLayout;

    /**
     * Instantiates a new home view.
     */
    @Inject
    public UserView(LangConstants lang, final DispatchAsync dispatcher, EventBus eventBus) {
        this.layout = new VLayout();
        this.layout.setPadding(10);
        this.layout.setHeight100();

        usersLayout = new UsersLayout(lang, dispatcher, eventBus);

        layout.addMember(usersLayout);

        requestsLayout = new RequestsLayout(lang);
        layout.addMember(requestsLayout);
    }

    /**
     * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
     * 
     * @return the widget
     */
    @Override
    public Widget asWidget() {
        return layout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListGrid getUserGrid() {
        return usersLayout.getUserGrid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton getAddUser() {
        return usersLayout.getAddUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListGrid getRequestsGrid() {
        return requestsLayout.getRequestsGrid();
    }

    /**
     * @return the usersLayout
     */
    @Override
    public UsersLayout getUsersLayout() {
        return usersLayout;
    }

}