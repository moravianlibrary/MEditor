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

package cz.mzk.editor.client.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
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

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.ClientCreateUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.event.ConfigReceivedEvent;
import cz.mzk.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.mzk.editor.shared.event.CreateStructureEvent;
import cz.mzk.editor.shared.rpc.MetadataBundle;
import cz.mzk.editor.shared.rpc.action.FindMetadataAction;
import cz.mzk.editor.shared.rpc.action.FindMetadataResult;

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

        TextItem getZ39Id();

        TextItem getOaiId();

        SelectItem getOaiPrefix();

        SelectItem getOaiBase();

        SelectItem getXServicesBase();

        SelectItem getOaiUrl();

        SelectItem getFindBy();

        ButtonItem getFindZ39();

        ButtonItem getFindOai();

        ButtonItem getFindButtonXservices();

        TextItem getSearchValueXservices();

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

    //    /** The doMenuPresenter presenter. */
    //    private final DigitalObjectMenuPresenter doMenuPresenter;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final EditorClientConfiguration config;

    private final LangConstants lang;

    private String sysno = null;

    private String inputPath = null;

    private String model = null;

    private boolean findLater = false;

    private final Map<Integer, MetadataBundle> results = new HashMap<Integer, MetadataBundle>();

    public static final String OAI_STRING = "%p?verb=GetRecord&identifier=%p%p-%s&metadataPrefix=";

    public static final String OAI_STRING_VSUP = "%p/?verb=GetRecord&identifier=%p%s&metadataPrefix=";

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
                                 final DispatchAsync dispatcher,
                                 final PlaceManager placeManager,
                                 final EditorClientConfiguration config,
                                 final LangConstants lang) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.lang = lang;
        this.config = config;
        bind();
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {

            @Override
            public void onConfigReceived(ConfigReceivedEvent event) {
                if (event.isStatusOK()) {
                    getView().getOaiUrl().setValueMap(config.getOaiUrls());
                    getView().getOaiUrl().setValue(config.getOaiUrls()[0]);
                    getView().getOaiPrefix().setValueMap(config.getOaiPrefixes());
                    getView().getOaiPrefix().setValue(config.getOaiPrefixes()[0]);
                    getView().getOaiBase().setValueMap(config.getOaiBases());
                    getView().getXServicesBase().setValueMap(config.getXServicesBases());
                    getView().getXServicesBase().setValue(config.getXServicesBases()[0]);
                    getView().getOaiBase().setValue(config.getOaiBases()[0]);
                    if (findLater) {
                        findLater = false;
                        findPropriateMetadata();
                    }
                }
            }
        });
        getView().getFindZ39().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                findZ39Metadata();
            }
        });
        getView().getFindOai().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                findPropriateMetadata();
            }
        });
        getView().getFindButtonXservices().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                findMetadataXservices();
            }
        });
        getView().getNext().setDisabled(true);
        getView().getNext().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                int id =
                        getView().getResults().getSelectedRecord()
                                .getAttributeAsInt(Constants.ATTR_GENERIC_ID);
                MetadataBundle bundle = results.get(id);
                getEventBus().fireEvent(new CreateStructureEvent(model,
                                                                 bundle.getMarc().getSysno(),
                                                                 inputPath,
                                                                 bundle));
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model)
                        .with(Constants.URL_PARAM_SYSNO, bundle.getMarc().getSysno())
                        .with(Constants.URL_PARAM_PATH, inputPath)
                        .with(Constants.URL_PARAM_BASE, bundle.getMarc().getBase()));
            }
        });
        getView().getWithoutMetadata().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                getEventBus().fireEvent(new CreateStructureEvent(model, sysno, inputPath, null));
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_SYSNO, sysno)
                        .with(Constants.URL_PARAM_PATH, inputPath)
                        .with(Constants.URL_PARAM_BASE, getView().getOaiBase().getValueAsString()));
            }
        });
        getView().getResults().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                getView().getNext().setDisabled(false);
            }
        });
        if (config != null && config.getConfiguration() != null) {
            getView().getOaiUrl().setValueMap(config.getOaiUrls());
            getView().getOaiUrl().setValue(config.getOaiUrls()[0]);
            getView().getOaiPrefix().setValueMap(config.getOaiPrefixes());
            getView().getOaiPrefix().setValue(config.getOaiPrefixes()[0]);
            getView().getOaiBase().setValueMap(config.getOaiBases());
            getView().getOaiBase().setValue(config.getOaiBases()[0]);
            getView().getXServicesBase().setValueMap(config.getXServicesBases());
            getView().getXServicesBase().setValue(config.getXServicesBases()[0]);
        } else {
            findLater = true;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_LEFT_CONTENT, leftPresenter);
        leftPresenter.getView().setInputTree(dispatcher, placeManager);
        getView().getNext().setDisabled(true);
    }

    /*
     * (non-Javadoc) n
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_MEDIT_MAIN_CONTENT, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        results.clear();
        sysno = request.getParameter(Constants.URL_PARAM_SYSNO, null);
        inputPath = request.getParameter(Constants.URL_PARAM_PATH, null);
        model = request.getParameter(Constants.ATTR_MODEL, null);
        getView().getZ39Id().setValue(sysno);
        getView().getOaiId().setValue(sysno);
        if (config != null && config.getConfiguration() != null) {
            findPropriateMetadata();
        } else {
            findLater = true;
        }
    }

    private void findMetadata(Constants.SEARCH_FIELD field, String id, Constants.SEARCH_METHOD method) {
        results.clear();
        if (id == null) {
            return;
        }
        String base = null;
        switch (method) {
            case OAI:
                base = getView().getOaiBase().getValueAsString();
                break;
            case X_SERVICES:
                base = getView().getXServicesBase().getValueAsString();
        }
        String prefix = getView().getOaiPrefix().getValueAsString();
        String url = getView().getOaiUrl().getValueAsString();

        dispatcher.execute(new FindMetadataAction(field, id, method, url, base, prefix), new DispatchCallback<FindMetadataResult>() {

            @Override
            public void callback(FindMetadataResult result) {
                getView().showProgress(true, true);
                List<MetadataBundle> list = result.getBundle();
                if (list != null && list.size() != 0) {
                    ListGridRecord[] data = new ListGridRecord[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).getDc().setId(i);
                        data[i] = list.get(i).getDc().toRecord();
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
                super.callbackError(t);
            }
        });
        getView().showProgress(true, false);
    }

    private void findZ39Metadata() {
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
            findMetadata(findBy, (String) getView().getZ39Id().getValue(), Constants.SEARCH_METHOD.Z39_50);
        }
    }

    private void findMetadataXservices() {
        findMetadata(Constants.SEARCH_FIELD.SYSNO, (String) getView().getSearchValueXservices().getValue(), Constants.SEARCH_METHOD.X_SERVICES);
    }

    private void findPropriateMetadata() {
        String id = getView().getOaiId().getValueAsString();
        if (id != null && !"".equals(id)) {
            int oaiIdLength = -1;
            try {
                // oairecord length can be variable (for example MLP's oaid is sequence number)
                if (!"variable".equals(config.getOaiRecordIdentifierLength())) {
                    oaiIdLength = Integer.parseInt(config.getOaiRecordIdentifierLength());
                }
            } catch (NumberFormatException nfe) {
                SC.warn("V konfiguraci je spatne zadana hodnota pro "
                        + EditorClientConfiguration.Constants.OAI_RECORD_IDENTIFIER_LENGTH
                        + " (musi byt cislo nebo 'variable' pro promennou delku)");
                return;
            }
            if (oaiIdLength == -1 || id.length() == oaiIdLength) {
                getView().getFindBy().setValue(Constants.SYSNO);
                getView().getZ39Id().setTitle(Constants.SYSNO);
                getView().getZ39Id().redraw();
                findMetadata(null, id, Constants.SEARCH_METHOD.OAI);
            } else if (id.length() == 10) {
                getView().getFindBy().setValue(lang.fbarcode());
                getView().getZ39Id().setTitle(lang.fbarcode());
                getView().getZ39Id().redraw();
                findMetadata(Constants.SEARCH_FIELD.BAR, id, Constants.SEARCH_METHOD.OAI);
            } else {
                getView().getFindBy().setValue(lang.ftitle());
                getView().getZ39Id().setTitle(lang.ftitle());
                getView().getZ39Id().redraw();
                findMetadata(Constants.SEARCH_FIELD.TITLE, id, Constants.SEARCH_METHOD.Z39_50);
            }
        }
    }

}