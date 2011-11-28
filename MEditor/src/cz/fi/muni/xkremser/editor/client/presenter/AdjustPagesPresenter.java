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

package cz.fi.muni.xkremser.editor.client.presenter;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.event.StartAdjustingPagesEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class AdjustPagesPresenter
        extends Presenter<AdjustPagesPresenter.MyView, AdjustPagesPresenter.MyProxy>
        implements StartAdjustingPagesEvent.StartAdjustingPagesHandler {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View {

        TextItem getCode();

        SelectItem getFindBy();

        ButtonItem getFind();

        void refreshData(ListGridRecord[] data);

        void showProgress(boolean show, boolean msg);

        IButton getNext();

        IButton getWithoutMetadata();
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    // @NameToken(NameTokens.ADJUST_PAGES)
    public interface MyProxy
            extends Proxy<AdjustPagesPresenter> {

    }

    /** The dispatcher. */
    @SuppressWarnings("unused")
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final DigitalObjectMenuPresenter leftPresenter;

    /** The place manager. */
    @SuppressWarnings("unused")
    private final PlaceManager placeManager;

    @SuppressWarnings("unused")
    private final LangConstants lang;

    private String code = null;

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
    public AdjustPagesPresenter(final EventBus eventBus,
                                final MyView view,
                                final MyProxy proxy,
                                final DigitalObjectMenuPresenter leftPresenter,
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
        System.out.println("on bind");
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
    }

    /*
     * (non-Javadoc) n
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        code = request.getParameter(Constants.URL_PARAM_CODE, null);
        getView().getCode().setValue(code);
    }

    @ProxyEvent
    @Override
    public void onStartAdjustingPages(StartAdjustingPagesEvent event) {
        forceReveal();
        System.out.println("dc   :  " + event.getDc());
        System.out.println("path :  " + event.getPath());
    }

}