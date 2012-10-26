/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
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
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class AdminHomePresenter
        extends Presenter<AdminHomePresenter.MyView, AdminHomePresenter.MyProxy> {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View {

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.ADMIN_HOME)
    public interface MyProxy
            extends ProxyPlace<AdminHomePresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final AdminMenuPresenter leftPresenter;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final LangConstants lang;

    /**
     * Instantiates a new home presenter.
     * 
     * @param eventBus
     *        the event bus
     * @param view
     *        the view
     * @param proxy
     *        the proxy
     * @param leftPresenter
     *        the left presenter
     * @param dispatcher
     *        the dispatcher
     * @param placeManager
     *        the place manager
     */
    @Inject
    public AdminHomePresenter(final EventBus eventBus,
                              final MyView view,
                              final MyProxy proxy,
                              final AdminMenuPresenter leftPresenter,
                              final DispatchAsync dispatcher,
                              final PlaceManager placeManager,
                              final LangConstants lang) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.lang = lang;
        bind();
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();

    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        RevealContentEvent.fire(this, AdminPresenter.TYPE_ADMIN_LEFT_CONTENT, leftPresenter);
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AdminPresenter.TYPE_ADMIN_MAIN_CONTENT, this);
    }

}