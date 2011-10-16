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
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateObjectMenuView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.SideNavInputTree;

import cz.fi.muni.xkremser.editor.shared.event.KeyPressedEvent;
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

        SectionStack getSectionStack();

        ButtonItem getCreateButton();

        CheckboxItem getKeepCheckbox();

        SelectItem getSelectModel();

        void enableCheckbox(boolean isEnabled);

        void addSubstructure(String id, String name, String type, String typeId, String parent, boolean isOpen);
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

    private final LangConstants lang;

    private int lastId = 1;

    private final Map<String, DigitalObjectModel> modelsFromLabels =
            new HashMap<String, DigitalObjectModel>();

    private final Map<String, String> labelsFromModels = new HashMap<String, String>();

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

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();

        getView().getSubelementsGrid().setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return record.getAttribute(Constants.ATTR_DESC);
            }
        });
        //        getView().getSubelementsGrid().addCellClickHandler(new CellClickHandler() {
        //
        //            @Override
        //            public void onCellClick(CellClickEvent event) {
        //                revealItem(event.getRecord().getAttribute(Constants.ATTR_UUID));
        //            }
        //        });
        getView().enableCheckbox(false);
        getView().getCreateButton().disable();
        getView().getSubelementsGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord rec = event.getSelectedRecord();
                if (rec != null) {
                    String modelString = rec.getAttribute(Constants.ATTR_TYPE_ID);
                    refreshSelectModel(modelString);
                }
            }
        });

        getView().getSelectModel().addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                String val = (String) event.getValue();
                if (!"".equals(val.trim())) {
                    getView().getCreateButton().enable();
                } else {
                    getView().getCreateButton().disable();
                }
            }
        });

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                shortcutPressed(event.getCode());
            }
        });

        // label to model 2 way mapping
        getLabelFromModel().put(DigitalObjectModel.INTERNALPART.getValue(), lang.internalpart());
        getModelFromLabel().put(lang.internalpart(), DigitalObjectModel.INTERNALPART);
        getLabelFromModel().put(DigitalObjectModel.MONOGRAPH.getValue(), lang.monograph());
        getModelFromLabel().put(lang.monograph(), DigitalObjectModel.MONOGRAPH);
        getLabelFromModel().put(DigitalObjectModel.MONOGRAPHUNIT.getValue(), lang.monographunit());
        getModelFromLabel().put(lang.monographunit(), DigitalObjectModel.MONOGRAPHUNIT);
        getLabelFromModel().put(DigitalObjectModel.PAGE.getValue(), lang.page());
        getModelFromLabel().put(lang.page(), DigitalObjectModel.PAGE);
        getLabelFromModel().put(DigitalObjectModel.PERIODICAL.getValue(), lang.periodical());
        getModelFromLabel().put(lang.periodical(), DigitalObjectModel.PERIODICAL);
        getLabelFromModel().put(DigitalObjectModel.PERIODICALITEM.getValue(), lang.periodicalitem());
        getModelFromLabel().put(lang.periodicalitem(), DigitalObjectModel.PERIODICALITEM);
        getLabelFromModel().put(DigitalObjectModel.PERIODICALVOLUME.getValue(), lang.periodicalvolume());
        getModelFromLabel().put(lang.periodicalvolume(), DigitalObjectModel.PERIODICALVOLUME);
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
     * #revealModifiedItem(java.lang.String)
     */
    @Override
    public void revealItem(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid));
    }

    private void shortcutPressed(final int code) {
        if (isVisible()) {
            if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_M.getCode()) {
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

            } else if (code == Constants.CODE_KEY_ENTER) {

                if (getView().getSubelementsGrid().getSelection().length > 0 && !isRefByFocused) {

                    ListGridRecord[] listGridRecords = getView().getSubelementsGrid().getSelection();
                    revealItem(listGridRecords[0].getAttribute(Constants.ATTR_UUID));

                }
            }
        }
    }

    public void refreshSelectModel(String modelString) {
        if (modelString != null) {
            DigitalObjectModel model = DigitalObjectModel.parseString(modelString);
            List<DigitalObjectModel> childrenModels = NamedGraphModel.getChildren(model);
            String[] values = new String[childrenModels == null ? 0 : childrenModels.size()];
            if (childrenModels != null && childrenModels.size() > 0) {
                for (int i = 0; i < childrenModels.size(); i++) {
                    values[i] = getLabelFromModel().get(childrenModels.get(i).getValue());
                }
            }
            getView().getSelectModel().setValueMap(values);
            if (childrenModels == null) {
                getView().getSelectModel().setValue("");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, DigitalObjectModel> getModelFromLabel() {
        return modelsFromLabels;
    }

    @Override
    public Map<String, String> getLabelFromModel() {
        return labelsFromModels;
    }

    public int newId() {
        return ++lastId;
    }

}