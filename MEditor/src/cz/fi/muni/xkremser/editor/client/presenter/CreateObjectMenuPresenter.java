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
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateObjectMenuView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.window.UuidWindow;

import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectClosedEvent;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectClosedEvent.DigitalObjectClosedHandler;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent;
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent.DigitalObjectOpenedHandler;
import cz.fi.muni.xkremser.editor.shared.event.KeyPressedEvent;
import cz.fi.muni.xkremser.editor.shared.event.RefreshRecentlyTreeEvent;
import cz.fi.muni.xkremser.editor.shared.event.RefreshRecentlyTreeEvent.RefreshRecentlyTreeHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateObjectMenuPresenter.
 */
public class CreateObjectMenuPresenter
        extends Presenter<CreateObjectMenuPresenter.MyView, CreateObjectMenuPresenter.MyProxy>
        implements MyUiHandlers {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<MyUiHandlers> {

        void expandNode(String id);

        HasClickHandlers getRefreshWidget();

        SideNavInputTree getInputTree();

        void setInputTree(SideNavInputTree tree);

        ListGrid getSubelementsGrid();

        void setDS(DispatchAsync dispatcher, EventBus bus);

        SectionStack getSectionStack();

        SelectItem getSelectItem();

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyStandard
    public interface MyProxy
            extends Proxy<CreateObjectMenuPresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The input queue shown. */
    private boolean inputQueueShown = false;

    /** The place manager. */
    private final PlaceManager placeManager;

    /** The config. */
    private final EditorClientConfiguration config;

    private boolean isRefByFocused = false;

    /** The uuid-window **/
    private UuidWindow uuidWindow = null;

    private final LangConstants lang;

    private final Map<String, List<? extends List<String>>> openedObjectsUuidAndRelated =
            new HashMap<String, List<? extends List<String>>>();

    /**
     * Instantiates a new digital object menu presenter.
     * 
     * @param view
     *        the view
     * @param eventBus
     *        the event bus
     * @param proxy
     *        the proxy
     * @param dispatcher
     *        the dispatcher
     * @param config
     *        the config
     * @param placeManager
     *        the place manager
     * @param lang
     *        the lang constants
     */
    @Inject
    public CreateObjectMenuPresenter(final MyView view,
                                     final EventBus eventBus,
                                     final MyProxy proxy,
                                     final DispatchAsync dispatcher,
                                     final EditorClientConfiguration config,
                                     PlaceManager placeManager,
                                     LangConstants lang) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.config = config;
        this.placeManager = placeManager;
        this.lang = lang;
        bind();
        getView().setUiHandlers(this);
    }

    /**
     * Method for handle enter-new-object's-PID short-cut
     */
    private void displayEnterPIDWindow() {
        uuidWindow = new UuidWindow(lang) {

            @Override
            protected void doActiton(TextItem uuidField) {
                evaluateUuid(uuidWindow.getUuidField());
            }

        };
    }

    /**
     * Method for close currently displayed window
     */
    private void escShortCut() {
        if (uuidWindow != null) {
            uuidWindow.destroy();
            //            uuidWindow = null;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();

        getView().setDS(dispatcher, getEventBus());
        getView().getSubelementsGrid().setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return record.getAttribute(Constants.ATTR_DESC);
            }
        });
        getView().getSubelementsGrid().addCellClickHandler(new CellClickHandler() {

            @Override
            public void onCellClick(CellClickEvent event) {
                revealItem(event.getRecord().getAttribute(Constants.ATTR_UUID));
            }
        });

        addRegisteredHandler(DigitalObjectOpenedEvent.getType(), new DigitalObjectOpenedHandler() {

            @Override
            public void onDigitalObjectOpened(DigitalObjectOpenedEvent event) {
                if (event.isStatusOK()) {
                    onAddSubelement(event.getItem(), event.getRelated());
                }
            }
        });

        addRegisteredHandler(DigitalObjectClosedEvent.getType(), new DigitalObjectClosedHandler() {

            @Override
            public void onDigitalObjectClosed(DigitalObjectClosedEvent event) {
                openedObjectsUuidAndRelated.remove(event.getUuid());
            }
        });

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                shortcutPressed(event.getCode());
            }
        });

        addRegisteredHandler(RefreshRecentlyTreeEvent.getType(), new RefreshRecentlyTreeHandler() {

            @Override
            public void onRefreshRecentlyTree(RefreshRecentlyTreeEvent event) {
                refreshRecentlyModified();
            }
        });

    }

    @Override
    public void refreshRecentlyModified() {

        Criteria criteria = new Criteria();
        boolean all = lang.all().equals(getView().getSelectItem().getValue());
        criteria.addCriteria(Constants.ATTR_ALL, all);
        //        getView().getSubelementGrid().getDataSource().fetchData(criteria, new DSCallback() {
        //
        //            @Override
        //            public void execute(DSResponse response, Object rawData, DSRequest request) {
        //                getView().getSubelementGrid().setData(response.getData());
        //                getView().getSubelementGrid().sort(Constants.ATTR_MODIFIED, SortDirection.ASCENDING);
        //                getView().getSubelementGrid().selectRecord(0);
        //                getView().getSubelementGrid().scrollToRow(0);
        //            }
        //        });
    }

    private void evaluateUuid(TextItem uuidField) {
        if (uuidField.validate()) {
            uuidWindow.destroy();
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                    .with(Constants.URL_PARAM_UUID, (String) uuidField.getValue()));
        }

    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
     */
    @Override
    protected void onUnbind() {
        super.onUnbind();
        //        getView().getSubelementGrid().setHoverCustomizer(null);
    }

    /**
     * Checks if is input queue shown.
     * 
     * @return true, if is input queue shown
     */
    public boolean isInputQueueShown() {
        return inputQueueShown;
    }

    /**
     * Sets the input queue shown.
     * 
     * @param inputQueueShown
     *        the new input queue shown
     */
    public void setInputQueueShown(boolean inputQueueShown) {
        this.inputQueueShown = inputQueueShown;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onRefresh()
     */
    @Override
    public void onRefresh() {
        dispatcher.execute(new ScanInputQueueAction(null, true),
                           new DispatchCallback<ScanInputQueueResult>() {

                               @Override
                               public void callback(ScanInputQueueResult result) {
                                   getView().getInputTree().refreshTree();
                               }
                           });
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, this);
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onAddDigitalObject
     * (cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem)
     */
    @Override
    public void onAddSubelement(final RecentlyModifiedItem item, final List<? extends List<String>> related) {
        //        openedObjectsUuidAndRelated.put(item.getUuid(), related);
        //        getView().setRelatedDocuments(related);
        //        RecentlyModifiedRecord record = ClientUtils.toRecord(item);
        //        if (getView().getSubelementGrid().getDataAsRecordList().contains(record)) {
        //            getView().getSubelementGrid().updateData(record);
        //        } else {
        //            getView().getSubelementGrid().addData(record);
        //        }
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #revealModifiedItem(java.lang.String)
     */
    @Override
    public void revealItem(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid));
    }

    private void shortcutPressed(final int code) {
        if (code == Constants.CODE_KEY_ESC) {
            escShortCut();

        } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_M.getCode()) {
            Canvas[] items2 = getView().getSectionStack().getSection(2).getItems();
            if (items2.length > 0) {
                items2[0].focus();
                isRefByFocused = false;
            }
        } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_D.getCode()) {
            Canvas[] items1 = getView().getSectionStack().getSection(1).getItems();
            if (items1.length > 0) {
                items1[0].focus();
                isRefByFocused = true;
            }
        } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_U.getCode()) {
            displayEnterPIDWindow();

        } else if (code == Constants.CODE_KEY_ENTER) {

            if (getView().getSubelementsGrid().getSelection().length > 0 && !isRefByFocused) {

                ListGridRecord[] listGridRecords = getView().getSubelementsGrid().getSelection();
                revealItem(listGridRecords[0].getAttribute(Constants.ATTR_UUID));

            }
        }
    }

}