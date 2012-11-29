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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
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

    /** The form. */
    private DynamicForm form;

    /** The open. */
    private IButton open;

    /** The uuid field. */
    private TextItem uuidField;

    /** The remove role. */
    private IButton removeRole;

    /** The remove identity. */
    private IButton removeIdentity;

    /** The add role. */
    private IButton addRole;

    /** The add identity. */
    private IButton addIdentity;

    private final LangConstants lang;

    private final RequestsLayout requestsLayout;

    private final UsersLayout usersLayout;

    //    private final EditorClientConfiguration config;

    /**
     * Instantiates a new home view.
     */
    @Inject
    public UserView(LangConstants lang, final DispatchAsync dispatcher, EventBus eventBus) {
        this.lang = lang;
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

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#getOpen ()
     */
    @Override
    public IButton getOpen() {
        return open;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#getForm ()
     */
    @Override
    public DynamicForm getForm() {
        return form;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#getUuidItem ()
     */
    @Override
    public HasChangedHandlers getUuidItem() {
        return uuidField;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView# refreshFedora
     * (boolean, java.lang.String)
     */
    @Override
    public void refreshFedora(boolean fedoraRunning, String url) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#
     * refreshKramerius(boolean, java.lang.String)
     */
    @Override
    public void refreshKramerius(boolean krameriusRunning, String url) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#setURLs
     * (java.lang.String, java.lang.String)
     */
    @Override
    public void setURLs(String fedoraUrl, String krameriusUrl) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#setLoading ()
     */
    @Override
    public void setLoading() {
        // TODO Auto-generated method stub

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
    public IButton getRemoveUser() {
        return usersLayout.getRemoveUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton getRemoveRequest() {
        return requestsLayout.getRemoveRequest();
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView# getRemoveRole
     * ()
     */
    @Override
    public IButton getRemoveRole() {
        return removeRole;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#
     * getRemoveIdentity()
     */
    @Override
    public IButton getRemoveIdentity() {
        return removeIdentity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IButton getAddUser() {
        return usersLayout.getAddUser();
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView#getAddRole ()
     */
    @Override
    public IButton getAddRole() {
        return addRole;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.UserPresenter.MyView# getAddIdentity
     * ()
     */
    @Override
    public IButton getAddIdentity() {
        return addIdentity;
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