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

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.CheckBox;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.uihandlers.CreateObjectMenuUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.InputQueueTree;
import cz.mzk.editor.client.view.other.LabelAndModelConverter;
import cz.mzk.editor.client.view.other.SequentialCreateLayout;
import cz.mzk.editor.client.view.other.StructureTreeHoverCreator;
import cz.mzk.editor.client.view.window.AddAltoOcrWindow;
import cz.mzk.editor.client.view.window.ConnectExistingObjectWindow;
import cz.mzk.editor.client.view.window.LoadTreeStructureWindow;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.rpc.action.GetDOModelAction;
import cz.mzk.editor.shared.rpc.action.GetDOModelResult;

/**
 * @author Jiri Kremser
 */
public class CreateObjectMenuPresenter
        extends Presenter<CreateObjectMenuPresenter.MyView, CreateObjectMenuPresenter.MyProxy>
        implements CreateObjectMenuUiHandlers {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<CreateObjectMenuUiHandlers> {

        InputQueueTree getInputTree();

        void setInputTree(DispatchAsync dispatcher, final PlaceManager placeManager);

        TreeGrid getSubelementsGrid();

        void setStructureTree(Tree tree);

        SectionStack getSectionStack();

        //        boolean hasCreateButtonAClickHandler();

        //        void setCreateButtonHasAClickHandler();

        void init();

        void addUndoRedo(boolean useUndoList, boolean isRedoOperation);

        void addSubstructure(String id,
                             String parent,
                             String name,
                             String pictureOrUuid,
                             String modelId,
                             String type,
                             String dateOrIntPartName,
                             String noteOrIntSubtitle,
                             String partNumberOrAlto,
                             String aditionalInfoOrOcr,
                             boolean isOpen,
                             boolean exist);

        List<Record> getMissingPages(TreeNode parentNode, Record[] selection);

        void setSectionCreateLayout(VLayout VLayout);

        SelectItem getCreationModeItem();

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

    private String defaultDateIssued = "";

    private SequentialCreateLayout sequentialCreateLayout = null;

    private VLayout atOnceCreateLayout = null;

    private DigitalObjectModel rootModel;

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
        sequentialCreateLayout = new SequentialCreateLayout(lang, getEventBus());
        setAtOnceCreateLayout();
        getView().setSectionCreateLayout(sequentialCreateLayout);

        getView().getCreationModeItem().addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (lang.atOnce().equals(getView().getCreationModeItem().getSelectedRecord())) {
                    getView().setSectionCreateLayout(atOnceCreateLayout);
                } else {
                    getView().setSectionCreateLayout(sequentialCreateLayout);
                }
            }
        });

        getView().getSubelementsGrid().setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return StructureTreeHoverCreator.getHover(lang, record);
            }
        });

        sequentialCreateLayout.enableCheckbox(false);
        sequentialCreateLayout.getCreateButton().disable();
        registerHandler(getView().getSubelementsGrid()
                .addSelectionChangedHandler(new SelectionChangedHandler() {

                    @Override
                    public void onSelectionChanged(SelectionEvent event) {
                        ListGridRecord rec = event.getSelectedRecord();
                        if (rec != null) {
                            String modelString = rec.getAttribute(Constants.ATTR_MODEL_ID);
                            refreshSelectModel(modelString);
                        }
                    }
                }));

        registerHandler(sequentialCreateLayout.getSelectModel().addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                afterTypeChanged((String) event.getValue());
            }

        }));

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                shortcutPressed(event.getCode());
            }
        });

        // label to model 2 way mapping
        LabelAndModelConverter.setLabelAndModelConverter(lang);

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
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_LEFT_CONTENT, this);
    }

    private void setAtOnceCreateLayout() {
        atOnceCreateLayout = new VLayout();

        Button createAtOnceButton = new Button(lang.create());
        CheckBox markingCheckBox = new CheckBox();
        markingCheckBox.setTitle(lang.mark());

        atOnceCreateLayout.addMember(markingCheckBox);
        atOnceCreateLayout.addMember(createAtOnceButton);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.DigitalObjectMenuView.MyUiHandlers
     * #revealModifiedItem(java.lang.String)
     */
    @Override
    public void revealItem(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid));
    }

    private void afterTypeChanged(String currentModel) {
        if (!"".equals(currentModel.trim())) {
            sequentialCreateLayout.getCreateButton().enable();
            sequentialCreateLayout.setCreate(LabelAndModelConverter.getModelFromLabel().get(currentModel),
                                             defaultDateIssued,
                                             getRootModel() == DigitalObjectModel.PERIODICAL);
        } else {
            sequentialCreateLayout.getCreateButton().disable();
            sequentialCreateLayout.setCreate(null, null, getRootModel() == DigitalObjectModel.PERIODICAL);
        }
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
            String defaultValue = "";
            if (childrenModels != null && childrenModels.size() > 0) {
                for (int i = 0; i < childrenModels.size(); i++) {
                    values[i] =
                            LabelAndModelConverter.getLabelFromModel().get(childrenModels.get(i).getValue());
                    if (defaultValue.equals("")
                            && sequentialCreateLayout.getSelectModel().getValueAsString() != null
                            && sequentialCreateLayout.getSelectModel().getValueAsString().equals(values[i]))
                        defaultValue = values[i];
                }
            }
            sequentialCreateLayout.getSelectModel().setValueMap(values);
            sequentialCreateLayout.getSelectModel().setValue(defaultValue);
            afterTypeChanged(defaultValue);
        }
    }

    @Override
    public int newId() {
        return ++lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
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

    @Override
    public HasHandlers getBus() {
        return getEventBus();
    }

    /**
     * @return the rootModel
     */
    public DigitalObjectModel getRootModel() {
        return rootModel;
    }

    /**
     * @param rootModel
     *        the rootModel to set
     */
    public void setRootModel(DigitalObjectModel rootModel) {
        this.rootModel = rootModel;
    }

    @Override
    public void addPages(List<Record> pages, String parent) {
        for (int i = 0; i < pages.size(); i++) {
            getView().addSubstructure(String.valueOf(newId()),
                                      parent,
                                      pages.get(i).getAttribute(Constants.ATTR_NAME),
                                      pages.get(i).getAttribute(Constants.ATTR_PICTURE_OR_UUID),
                                      DigitalObjectModel.PAGE.getValue(),
                                      pages.get(i).getAttribute(Constants.ATTR_TYPE),
                                      "",
                                      "",
                                      "",
                                      "",
                                      true,
                                      false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SequentialCreateLayout getSequentialCreateLayout() {
        return sequentialCreateLayout;
    }
}