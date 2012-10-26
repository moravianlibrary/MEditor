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

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.uihandlers.MenuUiHandlers;

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
    @ProxyCodeSplit
    @NameToken(NameTokens.MENU)
    public interface MyProxy
            extends ProxyPlace<AdminHomePresenter> {

    }

    /**
     * @author Matous Jobanek
     * @version Oct 8, 2012 The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<MenuUiHandlers> {

    }

    private final LangConstants lang;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;

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
        this.placeManager = placeManager;
        this.lang = lang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void revealInParent() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBind() {
        super.onBind();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onReset() {
        super.onReset();
    }
}
