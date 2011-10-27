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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class FindMetadataPresenter
        extends Presenter<FindMetadataPresenter.MyView, FindMetadataPresenter.MyProxy> {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View {

        TextItem getCode();

        SelectItem getFindBy();

        ButtonItem getFind();

        ListGrid getResults();

        void refreshData(ListGridRecord[] data);

        void showProgress(boolean show, boolean msg);

        IButton getNext();

        IButton getWithoutMetadata();
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.FIND_METADATA)
    public interface MyProxy
            extends ProxyPlace<FindMetadataPresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final CreateObjectMenuPresenter leftPresenter;

    /** The doMenuPresenter presenter. */
    private final DigitalObjectMenuPresenter doMenuPresenter;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final LangConstants lang;

    private String code = null;

    private String model = null;

    private final Map<Integer, DublinCore> results = new HashMap<Integer, DublinCore>();

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
    public FindMetadataPresenter(final EventBus eventBus,
                                 final MyView view,
                                 final MyProxy proxy,
                                 final CreateObjectMenuPresenter leftPresenter,
                                 final DigitalObjectMenuPresenter doMenuPresenter,
                                 final DispatchAsync dispatcher,
                                 final PlaceManager placeManager,
                                 final LangConstants lang) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.doMenuPresenter = doMenuPresenter;
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
        getView().getFind().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String finder = (String) getView().getFindBy().getValue();
                Constants.SEARCH_FIELD findBy = null;
                if (finder == null || finder.isEmpty()) {
                    return;
                } else if (finder.equals(Constants.SYSNO)) {
                    findBy = Constants.SEARCH_FIELD.SYSNO;
                } else if (finder.equals(lang.fbarcode())) {
                    findBy = Constants.SEARCH_FIELD.BAR;
                } else if (finder.equals(lang.ftitle())) {
                    findBy = Constants.SEARCH_FIELD.TITLE;
                }
                if (findBy != null) {
                    findMetadata(findBy, (String) getView().getCode().getValue());
                }
            }
        });
        getView().getNext().setDisabled(true);
        getView().getNext().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                int id =
                        getView().getResults().getSelectedRecord()
                                .getAttributeAsInt(Constants.ATTR_GENERIC_ID);
                CreateStructureEvent.fire(getEventBus(),
                                          model,
                                          code,
                                          leftPresenter.getView().getInputTree(),
                                          results.get(id));
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_CODE, code));
            }
        });
        getView().getWithoutMetadata().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                CreateStructureEvent.fire(getEventBus(),
                                          model,
                                          code,
                                          leftPresenter.getView().getInputTree(),
                                          null);
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_CODE, code));
            }
        });
        getView().getResults().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                getView().getNext().setDisabled(false);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
        leftPresenter.getView().setInputTree(doMenuPresenter.getView().getInputTree());
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
        results.clear();
        code = request.getParameter(Constants.URL_PARAM_CODE, null);
        model = request.getParameter(Constants.ATTR_MODEL, null);
        getView().getCode().setValue(code);
        findMetadata(Constants.SEARCH_FIELD.SYSNO, code);
    }

    private void findMetadata(Constants.SEARCH_FIELD field, String code) {
        results.clear();
        dispatcher.execute(new FindMetadataAction(field, code), new DispatchCallback<FindMetadataResult>() {

            @Override
            public void callback(FindMetadataResult result) {
                getView().showProgress(true, true);
                List<DublinCore> list = result.getOutput();
                if (list != null && list.size() != 0) {
                    ListGridRecord[] data = new ListGridRecord[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setId(i);
                        data[i] = list.get(i).toRecord();
                        results.put(i, list.get(i));
                    }
                    getView().refreshData(data);
                } else {
                    getView().refreshData(null);
                }
                getView().showProgress(false, false);
            }

            @Override
            public void callbackError(Throwable t) {
                getView().showProgress(false, false);
                SC.warn(t.getMessage());
            }
        });
        getView().showProgress(true, false);
    }
}