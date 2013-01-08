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

package cz.mzk.editor.client.presenter;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.uihandlers.AdminMenuUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.shared.rpc.action.HasUserRightsAction;
import cz.mzk.editor.shared.rpc.action.HasUserRightsResult;

/**
 * @author Matous Jobanek
 * @version Oct 8, 2012
 */
public class AdminMenuPresenter
        extends Presenter<AdminMenuPresenter.MyView, AdminMenuPresenter.MyProxy> {

    /**
     * @author Matous Jobanek
     * @version Oct 8, 2012 The Interface MyProxy.
     */
    @ProxyStandard
    public interface MyProxy
            extends Proxy<AdminMenuPresenter> {

    }

    /**
     * @author Matous Jobanek
     * @version Oct 8, 2012 The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<AdminMenuUiHandlers> {

        void setButtons(boolean showStat, boolean showUsers);
    }

    private final DispatchAsync dispatcher;

    /**
     * @param eventBus
     * @param view
     * @param proxy
     */
    @Inject
    public AdminMenuPresenter(EventBus eventBus,
                              MyView view,
                              MyProxy proxy,
                              final DispatchAsync dispatcher,
                              final PlaceManager placeManager,
                              final LangConstants lang) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_LEFT_CONTENT, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBind() {
        super.onBind();
        dispatcher.execute(new HasUserRightsAction(new EDITOR_RIGHTS[] {EDITOR_RIGHTS.SHOW_STATISTICS,
                EDITOR_RIGHTS.EDIT_USERS}), new DispatchCallback<HasUserRightsResult>() {

            @Override
            public void callback(HasUserRightsResult result) {
                getView().setButtons(result.getOk()[0], result.getOk()[1]);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void callbackError(Throwable t) {
                getView().setButtons(false, false);
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();
    }

}
