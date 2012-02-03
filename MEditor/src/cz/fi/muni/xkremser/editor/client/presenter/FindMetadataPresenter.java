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

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
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
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.fi.muni.xkremser.editor.shared.event.CreateStructureEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.MetadataBundle;
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

        TextItem getZ39Id();

        TextItem getOaiId();

        SelectItem getOaiPrefix();

        SelectItem getOaiBase();

        SelectItem getOaiUrl();

        SelectItem getFindBy();

        ButtonItem getFindZ39();

        ButtonItem getFindOai();

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

    private final EditorClientConfiguration config;

    private final LangConstants lang;

    private String sysno = null;

    private String inputPath = null;

    private String model = null;

    private boolean findLater = false;

    private final Map<Integer, MetadataBundle> results = new HashMap<Integer, MetadataBundle>();

    public static final String OAI_STRING = "%p/?verb=GetRecord&identifier=%p%p-%s&metadataPrefix=";

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
                                 final DigitalObjectMenuPresenter doMenuPresenter,
                                 final DispatchAsync dispatcher,
                                 final PlaceManager placeManager,
                                 final EditorClientConfiguration config,
                                 final LangConstants lang) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.doMenuPresenter = doMenuPresenter;
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
        getView().getNext().setDisabled(true);
        getView().getNext().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                int id =
                        getView().getResults().getSelectedRecord()
                                .getAttributeAsInt(Constants.ATTR_GENERIC_ID);
                MetadataBundle bundle = results.get(id);
                CreateStructureEvent.fire(getEventBus(),
                                          model,
                                          bundle.getMarc().getSysno(),
                                          inputPath,
                                          leftPresenter.getView().getInputTree(),
                                          bundle);
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model)
                        .with(Constants.URL_PARAM_SYSNO, bundle.getMarc().getSysno())
                        .with(Constants.URL_PARAM_PATH, inputPath));
            }
        });
        getView().getWithoutMetadata().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                CreateStructureEvent.fire(getEventBus(), model, sysno, inputPath, leftPresenter.getView()
                        .getInputTree());
                placeManager.revealRelativePlace(new PlaceRequest(NameTokens.CREATE)
                        .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_SYSNO, sysno)
                        .with(Constants.URL_PARAM_PATH, inputPath));
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
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
        leftPresenter.getView().setInputTree(doMenuPresenter.getView().getInputTree());
        getView().getNext().setDisabled(true);
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

    private void findMetadata(Constants.SEARCH_FIELD field, String id, boolean oai, String query) {
        results.clear();
        if (query == null || id == null) {
            return;
        }
        dispatcher.execute(new FindMetadataAction(field, id, oai, query),
                           new DispatchCallback<FindMetadataResult>() {

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
            findMetadata(findBy, (String) getView().getZ39Id().getValue(), false, getQuery());
        }
    }

    private void findPropriateMetadata() {
        String id = getView().getOaiId().getValueAsString();
        if (id != null && !"".equals(id)) {
            int oaiIdLength = -1;
            try {
                oaiIdLength = Integer.parseInt(config.getOaiRecordIdentifierLength());
            } catch (NumberFormatException nfe) {
                SC.warn("V konfiguraci je spatne zadana hodnota pro "
                        + EditorClientConfiguration.Constants.OAI_RECORD_IDENTIFIER_LENGTH
                        + " (musi byt cislo)");
                return;
            }
            if (id.length() == oaiIdLength) {
                getView().getFindBy().setValue(Constants.SYSNO);
                getView().getZ39Id().setTitle(Constants.SYSNO);
                getView().getZ39Id().redraw();
                findMetadata(null, id, true, getQuery());
            } else if (id.length() == 10) {
                getView().getFindBy().setValue(lang.fbarcode());
                getView().getZ39Id().setTitle(lang.fbarcode());
                getView().getZ39Id().redraw();
                findMetadata(Constants.SEARCH_FIELD.BAR, id, false, getQuery());
            } else {
                getView().getFindBy().setValue(lang.ftitle());
                getView().getZ39Id().setTitle(lang.ftitle());
                getView().getZ39Id().redraw();
                findMetadata(Constants.SEARCH_FIELD.TITLE, id, false, getQuery());
            }
        }
    }

    private String getQuery() {
        String url = getView().getOaiUrl().getValueAsString();
        String prefix = getView().getOaiPrefix().getValueAsString();
        String base = getView().getOaiBase().getValueAsString();
        if (url == null || prefix == null || base == null) {
            return null;
        }
        String query =
                config.getVsup() ? ClientUtils.format(OAI_STRING_VSUP, 'p', url, base) : ClientUtils
                        .format(OAI_STRING, 'p', url, prefix, base);
        return query;
    }

}