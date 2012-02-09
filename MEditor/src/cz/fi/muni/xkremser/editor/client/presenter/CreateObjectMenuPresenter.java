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
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tree.TreeGrid;

import cz.fi.muni.xkremser.editor.client.CreateObjectException;
import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.CreateObjectMenuView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;
import cz.fi.muni.xkremser.editor.client.view.other.InputQueueTree;
import cz.fi.muni.xkremser.editor.client.view.window.AddAltoOcrWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ConnectExistingObjectWindow;
import cz.fi.muni.xkremser.editor.client.view.window.LoadTreeStructureWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.StoreTreeStructureWindow;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.event.KeyPressedEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDOModelAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDOModelResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

/**
 * @author Jiri Kremser
 */
public class CreateObjectMenuPresenter
        extends Presenter<CreateObjectMenuPresenter.MyView, CreateObjectMenuPresenter.MyProxy>
        implements MyUiHandlers {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<MyUiHandlers> {

        HasClickHandlers getRefreshWidget();

        InputQueueTree getInputTree();

        void setInputTree(InputQueueTree tree);

        TreeGrid getSubelementsGrid();

        SectionStack getSectionStack();

        ButtonItem getCreateButton();

        CheckboxItem getKeepCheckbox();

        SelectItem getSelectModel();

        boolean hasCreateButtonAClickHandler();

        void setCreateButtonHasAClickHandler();

        void enableCheckbox(boolean isEnabled);

        TextItem getNewName();

        void init();

        void addUndoRedo(boolean useUndoList, boolean isRedoOperation);

        void addSubstructure(String id,
                             String name,
                             String uuid,
                             String type,
                             String typeId,
                             String parent,
                             String pageType,
                             String dateIssued,
                             boolean isOpen,
                             boolean exist);

        TextItem getDateIssued();

        void setCreateVolume(boolean setCreateVolume, String defaultDateIssued);
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

    /** The place manager. */
    private final PlaceManager placeManager;

    /** The config. */
    @SuppressWarnings("unused")
    private final EditorClientConfiguration config;

    @SuppressWarnings("unused")
    private boolean isRefByFocused = false;

    private final LangConstants lang;

    private int lastId = 1;

    private String barcode = null;

    private final Map<String, DigitalObjectModel> modelsFromLabels =
            new HashMap<String, DigitalObjectModel>();

    private final Map<String, String> labelsFromModels = new HashMap<String, String>();

    // this is not a mistake (the lock should be shared with another presenter)
    private static final Object LOCK = DigitalObjectMenuPresenter.class;

    private static volatile boolean ready = true;

    private String defaultDateIssued = "";

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
        getView().init();
        getView().getSubelementsGrid().setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                StringBuffer sb = new StringBuffer();
                String nameHover = hoverFactory(lang.name(), record.getAttribute(Constants.ATTR_NAME));
                sb.append(nameHover);

                String dIssued = record.getAttribute(Constants.ATTR_DATE_ISSUED);
                if (dIssued != null && !"".equals(dIssued)) {
                    String dateIssuedHover = hoverFactory(lang.dateIssued(), dIssued);
                    sb.append(dateIssuedHover);
                }

                String alto = record.getAttribute(Constants.ATTR_ALTO_PATH);
                if (alto != null && !"".equals(alto)) {
                    String altoHover = hoverFactory("ALTO", alto.substring(alto.lastIndexOf("/") + 1));
                    sb.append(altoHover);
                }

                String ocr = record.getAttribute(Constants.ATTR_OCR_PATH);
                if (ocr != null && !"".equals(ocr)) {
                    String ocrHover = hoverFactory("OCR", ocr.substring(ocr.lastIndexOf("/") + 1));
                    sb.append(ocrHover);
                }

                getView().getSubelementsGrid().setHoverWidth(350);
                return sb.toString();
            }
        });
        getView().enableCheckbox(false);
        getView().getCreateButton().disable();
        registerHandler(getView().getSubelementsGrid()
                .addSelectionChangedHandler(new SelectionChangedHandler() {

                    @Override
                    public void onSelectionChanged(SelectionEvent event) {
                        ListGridRecord rec = event.getSelectedRecord();
                        if (rec != null) {
                            String modelString = rec.getAttribute(Constants.ATTR_TYPE_ID);
                            refreshSelectModel(modelString);
                        }
                    }
                }));

        registerHandler(getView().getSelectModel().addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                String val = (String) event.getValue();
                if (!"".equals(val.trim())) {
                    getView().getCreateButton().enable();
                } else {
                    getView().getCreateButton().disable();
                }
                if (event.getValue().equals(getLabelFromModel()
                        .get(DigitalObjectModel.PERIODICALVOLUME.getValue()))
                        || event.getValue().equals(getLabelFromModel()
                                .get(DigitalObjectModel.PERIODICALITEM.getValue()))) {
                    getView().setCreateVolume(true, defaultDateIssued);
                } else {
                    getView().setCreateVolume(false, null);
                }
            }
        }));

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

    private String hoverFactory(String attribut, String value) {
        return HtmlCode.bold(attribut) + ": " + value + "<br />";
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

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #onRefresh()
     */
    @Override
    public void onRefresh() {
        if (ready) {
            synchronized (LOCK) {
                if (ready) { // double-lock idiom
                    ready = false;
                    final ModalWindow mw = new ModalWindow(getView().getInputTree());
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    dispatcher.execute(new ScanInputQueueAction(null, true),
                                       new DispatchCallback<ScanInputQueueResult>() {

                                           @Override
                                           public void callback(ScanInputQueueResult result) {
                                               mw.hide();
                                               getView().getInputTree().refreshTree();
                                               ready = true;
                                           }

                                           @Override
                                           public void callbackError(final Throwable t) {
                                               mw.hide();
                                               super.callbackError(t);
                                               ready = true;
                                           }
                                       });
                }
            }
        }
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
            } else if (code == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_H.getCode()) {
                Canvas[] items1 = getView().getSectionStack().getSection(1).getItems();
                if (items1.length > 0) {
                    items1[0].focus();
                    isRefByFocused = true;
                }

            }
            //  tadle else vetev nedava smysl, ty subelementy, jeste nejsou vytvorene, takze je nejde editovat
            //            else if (code == Constants.CODE_KEY_ENTER) {
            //                if (getView().getSubelementsGrid().getSelection().length > 0 && !isRefByFocused) {
            //                    ListGridRecord[] listGridRecords = getView().getSubelementsGrid().getSelection();
            //                    revealItem(listGridRecords[0].getAttribute(Constants.ATTR_UUID));
            //                }
            //            }
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

    @Override
    public int newId() {
        return ++lastId;
    }

    /**
     * @return the defaultDateIssued
     */

    public String getDefaultDateIssued() {
        return defaultDateIssued;
    }

    /**
     * @param defaultDateIssued
     *        the defaultDateIssued to set
     */

    public void setDefaultDateIssued(String defaultDateIssued) {
        this.defaultDateIssued = defaultDateIssued;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getModel(String valueAsString, final ConnectExistingObjectWindow window) {
        dispatcher.execute(new GetDOModelAction(valueAsString), new DispatchCallback<GetDOModelResult>() {

            @Override
            public void callback(GetDOModelResult result) {
                window.changeModel(result == null ? null : result.getModel());
            }

            @Override
            public void onFailure(Throwable caught) {
                window.changeModel(null);
            }
        });
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void addAlto(ListGridRecord record) {
        new AddAltoOcrWindow(record, lang, dispatcher, getEventBus()) {

            @Override
            protected void doSaveAction(ListGridRecord listGridRecord,
                                        ListGridRecord altoRecord,
                                        ListGridRecord ocrRecord) {

                getView().addUndoRedo(true, false);
                listGridRecord.setAttribute(Constants.ATTR_ALTO_PATH,
                                            (altoRecord != null ? altoRecord
                                                    .getAttributeAsString(Constants.ATTR_ALTO_PATH) : null));
                listGridRecord.setAttribute(Constants.ATTR_OCR_PATH,
                                            (ocrRecord != null ? ocrRecord
                                                    .getAttributeAsString(Constants.ATTR_OCR_PATH) : null));
                hide();
                getView().getSubelementsGrid().redraw();
            }
        };
    }

    @Override
    public void saveStructure() {
        NewDigitalObject object = null;
        try {
            object = ClientUtils.createTheStructure(null, getView().getSubelementsGrid().getTree());
        } catch (CreateObjectException e) {
            SC.warn(e.getMessage());
            e.printStackTrace();
        }
        if (object == null) {
            // TODO" i18n
            SC.warn("Strom je prázdný.");
        } else {
            TreeStructureBundle bundle = new TreeStructureBundle();
            bundle.setInfo(new TreeStructureInfo(-1, null, null, barcode, object.getName(), null));
            bundle.setNodes(ClientUtils.toNodes(getView().getSubelementsGrid().getTree()));
            StoreTreeStructureWindow.setInstanceOf(bundle,
                                                   ClientUtils.toStringTree(object),
                                                   lang,
                                                   dispatcher,
                                                   getEventBus());
        }
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public void loadStructure() {
        LoadTreeStructureWindow.setInstanceOf(barcode, lang, dispatcher, getEventBus());
    }

}