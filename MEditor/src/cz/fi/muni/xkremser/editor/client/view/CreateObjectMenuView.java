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

package cz.fi.muni.xkremser.editor.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.presenter.CreateObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.InputQueueTree;
import cz.fi.muni.xkremser.editor.client.view.window.ConnectExistingObjectWindow;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.window.NewObjectBasicInfoWindow;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.domain.NamedGraphModel;
import cz.fi.muni.xkremser.editor.shared.event.SaveStructureEvent;

/**
 * @author Jiri Kremser
 * @version 12.11.2011
 */
public class CreateObjectMenuView
        extends ViewWithUiHandlers<CreateObjectMenuView.MyUiHandlers>
        implements CreateObjectMenuPresenter.MyView {

    private static final String SECTION_INPUT_ID = "input";

    public static final String CREATE_BUTTON_HAS_A_HANDLER = "CREATE_BUTTON_HAS_A_HANDLER";

    private final LangConstants lang;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        void onRefresh();

        void revealItem(String uuid);

        Map<String, DigitalObjectModel> getModelFromLabel();

        Map<String, String> getLabelFromModel();

        void getModel(String valueAsString, ConnectExistingObjectWindow window);

        //Adds an ALTO file to a page
        void addAlto(ListGridRecord record);

        int newId();

        void loadStructure();

        HasHandlers getBus();

    }

    /** The input tree. */
    private InputQueueTree inputTree;

    private TreeGrid structureTreeGrid;

    private Tree structureTree;

    /** The section stack. */
    private SectionStack sectionStack;

    private SectionStackSection createStructure;

    private SectionStackSection structure;

    /** The refresh button. */
    private ImgButton refreshButton;

    private ButtonItem createButton;

    private CheckboxItem keepCheckbox;

    private SelectItem selectModel;

    private TextItem name;

    private TextItem dateIssued;

    /** The layout. */
    private VLayout layout;

    private boolean connect2ExEnabled;
    private boolean connectEx2Enabled;
    private boolean removeSelectedEnabled;
    private boolean editSelectedEnabled;
    private boolean addAltoEnabled;

    private List<Tree> undoList;
    private ImgButton undoButton;
    private List<Tree> redoList;
    private ImgButton redoButton;

    private final VLayout createLayout;

    private static final class CreateDynamicForm
            extends DynamicForm {

        public CreateDynamicForm(FormItem item) {
            super();
            setItems(item);
            setRight(5);
        }
    }

    private CreateDynamicForm dateIssuedForm;

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public CreateObjectMenuView(final LangConstants lang, final EventBus eventBus) {
        this.lang = lang;

        layout = new VLayout();

        layout.setHeight100();
        layout.setWidth100();
        layout.setOverflow(Overflow.AUTO);

        structureTreeGrid = new TreeGrid() {

            @Override
            protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
                boolean exist = record.getAttributeAsBoolean(Constants.ATTR_EXIST);
                return exist ? "color: grey" : "";
            };

        };
        structureTreeGrid.setWidth100();
        structureTreeGrid.setHeight100();
        //        structureTreeGrid.setShowSortArrow(SortArrow.CORNER);
        structureTreeGrid.setCanSort(false);
        structureTreeGrid.setShowAllRecords(true);
        structureTreeGrid.setCanHover(true);
        structureTreeGrid.setHoverOpacity(75);
        structureTreeGrid.setHoverWidth(300);
        structureTreeGrid.setCanEdit(true);
        structureTreeGrid.setCanReparentNodes(true);
        structureTreeGrid.setHoverStyle("interactImageHover");
        structureTreeGrid.setCanReorderRecords(true);
        structureTreeGrid.setCanAcceptDroppedRecords(true);
        structureTreeGrid.setCanDragRecordsOut(true);
        structureTreeGrid.setDropTypes(DigitalObjectModel.PAGE.getValue());
        structureTreeGrid.setDragType(DigitalObjectModel.PAGE.getValue());
        structureTreeGrid.setShowOpenIcons(false);
        structureTreeGrid.setDropIconSuffix("into");
        structureTreeGrid.setClosedIconSuffix("");
        structureTreeGrid.setOpenIconSuffix("");
        structureTreeGrid.setTreeRootValue(SubstructureTreeNode.ROOT_ID);
        structureTreeGrid.setFolderIcon("icons/16/structure.png");
        structureTreeGrid.setShowConnectors(true);
        structureTreeGrid.setRecordEditProperty(Constants.ATTR_CREATE);

        undoButton = new ImgButton();
        redoButton = new ImgButton();
        undoList = new ArrayList<Tree>();
        redoList = new ArrayList<Tree>();

        ImgButton menuButton = getMenuButton();

        undoButton.setSrc("icons/16/undo.png");
        undoButton.setSize("16", "16");
        undoButton.setShowTitle(false);
        undoButton.setHoverOpacity(75);
        undoButton.setHoverStyle("interactImageHover");
        undoButton.setCanHover(true);
        undoButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                undoButton.setPrompt(lang.undo());
            }
        });
        undoButton.disable();

        redoButton.setSrc("icons/16/redo.png");
        redoButton.setSize("16", "16");
        redoButton.setShowTitle(false);
        redoButton.setHoverOpacity(75);
        redoButton.setHoverStyle("interactImageHover");
        redoButton.setCanHover(true);
        redoButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                redoButton.setPrompt(lang.redo());
            }
        });
        redoButton.disable();

        undoButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (undoList.size() > 0) {
                    ModalWindow mw = new ModalWindow(structureTreeGrid);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    addUndoRedo(false, false);
                    structureTree = undoList.remove(undoList.size() - 1);
                    structureTreeGrid.setData(structureTree);
                    if (undoList.size() == 0) undoButton.disable();
                    structureTreeGrid.redraw();
                    structureTreeGrid.selectRecord(0);
                    mw.hide();
                } else {
                    undoButton.disable();
                }
            }
        });

        redoButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (redoList.size() > 0) {
                    ModalWindow mw = new ModalWindow(structureTreeGrid);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    addUndoRedo(true, true);
                    structureTree = redoList.remove(redoList.size() - 1);
                    structureTreeGrid.setData(structureTree);
                    if (redoList.size() == 0) redoButton.disable();
                    structureTreeGrid.redraw();
                    structureTreeGrid.selectRecord(0);
                    mw.hide();
                } else {
                    redoButton.disable();
                }
            }
        });

        structureTreeGrid.addDropHandler(new DropHandler() {

            @Override
            public void onDrop(DropEvent event) {
                Object draggable = EventHandler.getDragTarget();

                TileGrid tileGrid = null;
                TreeGrid treeGrid = null;
                if (draggable instanceof TileGrid) tileGrid = (TileGrid) draggable;
                if (draggable instanceof TreeGrid) treeGrid = (TreeGrid) draggable;

                if (tileGrid != null || treeGrid != null) {

                    Object source = event.getSource();
                    if (draggable instanceof TreeGrid) treeGrid = (TreeGrid) source;
                    Record[] selection;
                    if (treeGrid == null) {
                        selection = tileGrid.getSelection();
                    } else {
                        selection = treeGrid.getSelectedRecords();
                    }

                    Record dropPlace = structureTreeGrid.getRecord(structureTreeGrid.getEventRow());
                    if (selection == null || selection.length == 0) {
                        event.cancel();
                        return;
                    }

                    DigitalObjectModel parentModel;
                    DigitalObjectModel movedModel;
                    if (dropPlace != null) {
                        parentModel =
                                DigitalObjectModel.parseString(dropPlace.getAttribute(Constants.ATTR_TYPE_ID));
                        List<DigitalObjectModel> possibleChildModels =
                                NamedGraphModel.getChildren(parentModel);

                        if (tileGrid == null) {
                            movedModel =
                                    DigitalObjectModel.parseString(selection[0]
                                            .getAttribute(Constants.ATTR_TYPE_ID));
                        } else {
                            movedModel = DigitalObjectModel.PAGE;
                        }
                        if ((possibleChildModels == null || !possibleChildModels.contains(movedModel))
                                && (parentModel != DigitalObjectModel.PAGE || parentModel != DigitalObjectModel.PAGE)) {
                            SC.say(lang.objNotDropable() + ": <code>" + movedModel.getValue() + "</code>");
                            event.cancel();
                            return;
                        }
                    } else {
                        SC.say(lang.dropNotHere());
                        event.cancel();
                        return;
                    }

                    addUndoRedo(true, false);
                    if (treeGrid == null) {
                        if (parentModel == DigitalObjectModel.PAGE) {
                            SC.say(lang.pageNotDropable());
                            event.cancel();
                            return;
                        }

                        for (Record rec : selection) {
                            addSubstructure(String.valueOf(getUiHandlers().newId()),
                                            rec.getAttributeAsInt(Constants.ATTR_SCAN_INDEX),
                                            rec.getAttribute(Constants.ATTR_NAME),
                                            rec.getAttribute(Constants.ATTR_PICTURE),
                                            getUiHandlers().getLabelFromModel().get(DigitalObjectModel.PAGE
                                                    .getValue()),
                                            DigitalObjectModel.PAGE.getValue(),
                                            dropPlace.getAttribute(Constants.ATTR_ID),
                                            rec.getAttribute(Constants.ATTR_PAGE_TYPE),
                                            "",
                                            true,
                                            false);
                        }
                        event.cancel();
                    }

                    if (tileGrid != null && !event.isCtrlKeyDown()) {
                        tileGrid.removeSelectedData();
                    }
                }
            }
        });

        final MenuItem deleteSelected = new MenuItem(lang.removeSelected(), "icons/16/close.png");
        deleteSelected.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return removeSelectedEnabled;
            }
        });

        final MenuItem connectToExisting = new MenuItem(lang.connectToExisting(), "icons/16/connect2Ex.png");
        final MenuItem connectExistingTo = new MenuItem(lang.connectExistingTo(), "icons/16/ex2Connect.png");
        connectToExisting.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return connect2ExEnabled;
            }
        });
        deleteSelected.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                addUndoRedo(true, false);
                structureTreeGrid.removeSelectedData();
            }
        });
        connectExistingTo.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return connectEx2Enabled;
            }
        });
        connectToExisting.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                final Record[] selection = structureTreeGrid.getSelectedRecords();
                DigitalObjectModel model =
                        DigitalObjectModel.parseString(selection[0].getAttribute(Constants.ATTR_TYPE_ID));
                new ConnectExistingObjectWindow(lang, true, model, eventBus) {

                    @Override
                    protected void doAction(TextItem uuidField) {
                        addUndoRedo(true, false);
                        if (NamedGraphModel.isTopLvlModel(getModel())) {
                            TreeNode root = structureTree.findById(SubstructureTreeNode.ROOT_OBJECT_ID);
                            root.setAttribute(Constants.ATTR_EXIST, true);
                            root.setAttribute(Constants.ATTR_CREATE, false);
                            root.setAttribute(Constants.ATTR_NAME, uuidField.getValueAsString());
                            structureTreeGrid.setData(structureTree);
                        } else {
                            String parentId = selection[0].getAttributeAsString(Constants.ATTR_PARENT);
                            TreeNode parent = structureTree.findById(parentId);
                            boolean parentIsTopLvl =
                                    NamedGraphModel.isTopLvlModel(DigitalObjectModel.parseString(parent
                                            .getAttribute(Constants.ATTR_TYPE_ID)));
                            String newParentId = String.valueOf(getUiHandlers().newId());

                            // add new parent
                            addSubstructure(newParentId,
                                            -1,
                                            uuidField.getValueAsString(),
                                            uuidField.getValueAsString(),
                                            getUiHandlers().getLabelFromModel().get(getModel().getValue()),
                                            getModel().getValue(),
                                            parent.getAttribute(parentIsTopLvl ? Constants.ATTR_ID
                                                    : Constants.ATTR_PARENT),
                                            "",
                                            "",
                                            true,
                                            true);
                            for (Record rec : selection) {
                                addSubstructure(String.valueOf(getUiHandlers().newId()),
                                                rec.getAttributeAsInt(Constants.ATTR_SCAN_INDEX),
                                                rec.getAttribute(Constants.ATTR_NAME),
                                                rec.getAttribute(Constants.ATTR_PICTURE),
                                                rec.getAttribute(Constants.ATTR_TYPE),
                                                rec.getAttribute(Constants.ATTR_TYPE_ID),
                                                newParentId,
                                                rec.getAttribute(Constants.ATTR_PAGE_TYPE),
                                                rec.getAttribute(Constants.ATTR_DATE_ISSUED),
                                                true,
                                                false);
                            }
                            if (structureTree.getChildren(parent).length == structureTreeGrid
                                    .getSelectedRecords().length && !parentIsTopLvl) {
                                //parent has no other children (no siblings) and is not top lvl
                                structureTree.remove(parent);
                                structureTreeGrid.setData(structureTree);
                            }
                        }
                        structureTreeGrid.redraw();
                    }

                    @Override
                    protected void checkAvailability(TextItem uuidField) {
                        getUiHandlers().getModel(uuidField.getValueAsString(), this);
                    }
                };
            }
        });
        connectExistingTo.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                DigitalObjectModel model =
                        DigitalObjectModel.parseString(structureTreeGrid.getSelectedRecords()[0]
                                .getAttribute(Constants.ATTR_TYPE_ID));

                new ConnectExistingObjectWindow(lang, false, model, eventBus) {

                    @Override
                    protected void doAction(TextItem uuidField) {
                        addUndoRedo(true, false);
                        addSubstructure(String.valueOf(getUiHandlers().newId()),
                                        -1,
                                        uuidField.getValueAsString(),
                                        uuidField.getValueAsString(),
                                        getUiHandlers().getLabelFromModel().get(getModel().getValue()),
                                        getModel().getValue(),
                                        structureTreeGrid.getSelectedRecords()[0]
                                                .getAttribute(Constants.ATTR_ID),
                                        getModel() == DigitalObjectModel.PAGE ? Constants.PAGE_TYPES.NP
                                                .toString() : "",
                                        "",
                                        false,
                                        true);
                    }

                    @Override
                    protected void checkAvailability(TextItem uuidField) {
                        getUiHandlers().getModel(uuidField.getValueAsString(), this);
                    }
                };
            }
        });
        final Menu editMenu = new Menu();
        editMenu.setShowShadow(true);
        editMenu.setShadowDepth(10);

        final MenuItem edit = new MenuItem(lang.menuEdit(), "icons/16/edit.png");
        edit.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                new NewObjectBasicInfoWindow(structureTreeGrid.getSelectedRecords()[0], lang, eventBus) {

                    @Override
                    protected void doSaveAction(ListGridRecord record, String name) {
                        addUndoRedo(true, false);
                        record.setAttribute(Constants.ATTR_NAME, name);
                        if (getDateIssued() != null)
                            record.setAttribute(Constants.ATTR_DATE_ISSUED, getDateIssued());
                        if (getPageType() != null)
                            record.setAttribute(Constants.ATTR_PAGE_TYPE, getPageType());
                        structureTreeGrid.redraw();
                        hide();
                    }
                };
            }
        });
        edit.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return editSelectedEnabled;
            }
        });

        final MenuItem addAlto = new MenuItem(lang.add() + " ALTO/OCR", "icons/16/ocrAlto.png");
        addAlto.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getUiHandlers().addAlto(structureTreeGrid.getSelectedRecords()[0]);
            }
        });
        addAlto.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return addAltoEnabled;
            }
        });

        editMenu.setItems(edit,
                          deleteSelected,
                          addAlto,
                          new MenuItemSeparator(),
                          connectToExisting,
                          connectExistingTo);
        structureTreeGrid.setContextMenu(editMenu);
        structureTreeGrid.addCellContextClickHandler(new CellContextClickHandler() {

            @Override
            public void onCellContextClick(CellContextClickEvent event) {
                ListGridRecord[] selection = structureTreeGrid.getSelectedRecords();
                if (selection == null || selection.length == 0) {
                    return;
                }
                String modelStr = selection[0].getAttribute(Constants.ATTR_TYPE_ID);
                connect2ExEnabled = true;
                removeSelectedEnabled = true;
                for (int i = 0; i < selection.length; i++) {
                    // root mustn't be selected and all selected items must be of the same type
                    connect2ExEnabled &=
                            (i == 0 || modelStr.equals(selection[i].getAttribute(Constants.ATTR_TYPE_ID)));

                    removeSelectedEnabled &=
                            !SubstructureTreeNode.ROOT_ID.equals(selection[i].getAttribute(Constants.ATTR_ID));
                    if (!removeSelectedEnabled) {
                        break;
                    }
                }
                connect2ExEnabled &= removeSelectedEnabled;
                // only 1 element must be selected and type of selected element must allow connecting children (is not a leave)
                connectEx2Enabled =
                        selection.length == 1
                                && NamedGraphModel.getChildren(DigitalObjectModel.parseString(modelStr)) != null;

                //only 1 element can be edited
                editSelectedEnabled = selection.length == 1;

                //only one and only pages can be selected
                if (addAltoEnabled =
                        editSelectedEnabled
                                && selection[0].getAttribute(Constants.ATTR_TYPE_ID)
                                        .equals(DigitalObjectModel.PAGE.getValue())) {

                    String altoPath = selection[0].getAttributeAsString(Constants.ATTR_ALTO_PATH);
                    if (altoPath != null && !"".equals(altoPath)) {
                        addAlto.setTitle(lang.change() + " ALTO/OCR");
                    } else {
                        addAlto.setTitle(lang.add() + " ALTO/OCR");
                    }
                    editMenu.redraw();
                }

                editMenu.showContextMenu();
            }
        });
        structureTreeGrid.addShowContextMenuHandler(new ShowContextMenuHandler() {

            @Override
            public void onShowContextMenu(ShowContextMenuEvent event) {
                event.cancel();
            }
        });

        structureTree = new Tree();
        structureTree.setModelType(TreeModelType.PARENT);
        structureTree.setRootValue(SubstructureTreeNode.ROOT_ID);
        //        structureTree.setNameProperty(Constants.ATTR_NAME);
        structureTree.setIdField(Constants.ATTR_ID);
        structureTree.setParentIdField(Constants.ATTR_PARENT);
        structureTree.setOpenProperty("isOpen");

        TreeGridField typeField = new TreeGridField();
        typeField.setCanFilter(true);
        typeField.setCanEdit(false);
        typeField.setName(Constants.ATTR_TYPE);
        typeField.setTitle(lang.dcType());
        typeField.setWidth("40%");

        TreeGridField nameField = new TreeGridField();
        nameField.setCanFilter(true);
        nameField.setCanEdit(true);
        nameField.setName(Constants.ATTR_NAME);
        nameField.setTitle(lang.name());
        nameField.setWidth("*");
        nameField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                String altoPath = record.getAttributeAsString(Constants.ATTR_ALTO_PATH);
                String ocrPath = record.getAttributeAsString(Constants.ATTR_OCR_PATH);
                if (ocrPath != null && !"".equals(ocrPath)) {
                    if (altoPath != null && !"".equals(altoPath)) {
                        return "<img src=\"images/icons/16/ocrAlto.png\">".concat(record
                                .getAttributeAsString(Constants.ATTR_NAME));
                    }
                    return "<img src=\"images/icons/16/ocr.png\">".concat(record
                            .getAttributeAsString(Constants.ATTR_NAME));
                }
                return record.getAttributeAsString(Constants.ATTR_NAME);
            }
        });

        structureTreeGrid.setFields(typeField, nameField);
        structureTreeGrid.setRecordEditProperty(Constants.ATTR_CREATE);

        createStructure = new SectionStackSection();
        createStructure.setTitle(lang.createSubStructure());
        createStructure.setResizeable(true);
        name = new TextItem();
        name.setTitle(lang.name());

        dateIssued = new TextItem();
        dateIssued.setTitle(lang.dateIssued() + " " + lang.formYyyy());
        dateIssuedForm = new CreateDynamicForm(dateIssued);

        selectModel = new SelectItem();
        selectModel.setTitle(lang.dcType());

        keepCheckbox = new CheckboxItem();
        keepCheckbox.setTitle(lang.keepOnRight());
        createButton = new ButtonItem();
        createButton.setTitle(lang.create());
        createButton.setAlign(Alignment.CENTER);
        createButton.setColSpan(2);
        createButton.setAttribute(CREATE_BUTTON_HAS_A_HANDLER, false);

        createLayout = new VLayout();
        createLayout.setPadding(5);
        createLayout.setWidth100();
        createLayout.setExtraSpace(10);
        createLayout.addMember(new CreateDynamicForm(name));
        createLayout.addMember(new CreateDynamicForm(selectModel));
        createLayout.addMember(new CreateDynamicForm(keepCheckbox));
        createLayout.addMember(new CreateDynamicForm(createButton));
        createLayout.setRight(10);

        createStructure.setItems(createLayout);
        createStructure.setExpanded(false);

        structure = new SectionStackSection();
        structure.setTitle(lang.substructures());
        structure.setResizeable(true);
        structure.setItems(structureTreeGrid);
        structure.setExpanded(false);

        structure.setControls(undoButton, redoButton, menuButton);

        sectionStack = new SectionStack();
        sectionStack.addSection(createStructure);
        sectionStack.addSection(structure);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setAnimateSections(true);
        sectionStack.setWidth100();
        sectionStack.setHeight100();
        sectionStack.setOverflow(Overflow.HIDDEN);
        layout.addMember(sectionStack);
    }

    @Override
    public void setCreateVolume(boolean setCreateVolume, String defaultDateIssued) {
        boolean contains = createLayout.contains(dateIssuedForm);

        if (setCreateVolume) {
            dateIssued.setDefaultValue(defaultDateIssued);
            if (!contains) {
                createLayout.addMember(dateIssuedForm, 1);
            }
        } else {
            if (contains) {
                createLayout.removeMember(dateIssuedForm);
            }
        }
    }

    @Override
    public void init() {
        undoButton.disable();
        redoButton.disable();
        undoList = new ArrayList<Tree>();
        redoList = new ArrayList<Tree>();
    }

    /**
     * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
     * 
     * @return the widget
     */
    @Override
    public Widget asWidget() {
        return layout;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRefreshWidget()
     */
    @Override
    public HasClickHandlers getRefreshWidget() {
        return refreshButton;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getInputTree()
     */
    @Override
    public InputQueueTree getInputTree() {
        return inputTree;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRecentlyModifiedTree()
     */
    @Override
    public TreeGrid getSubelementsGrid() {
        return structureTreeGrid;
    }

    @Override
    public SectionStack getSectionStack() {
        return sectionStack;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInputTree(InputQueueTree tree) {
        String isInputSection = sectionStack.getSection(0).getAttribute(SECTION_INPUT_ID);
        if (isInputSection != null && "yes".equals(isInputSection)) {
            return;
        }
        inputTree = tree;
        SectionStackSection section1 = new SectionStackSection();
        section1.setTitle(lang.inputQueue());
        section1.setItems(inputTree);
        refreshButton = new ImgButton();
        refreshButton.setSrc("[SKIN]headerIcons/refresh.png");
        refreshButton.setSize(16);
        refreshButton.setShowRollOver(true);
        refreshButton.setCanHover(true);
        refreshButton.setShowDownIcon(false);
        refreshButton.setShowDown(false);
        refreshButton.setHoverOpacity(75);
        refreshButton.setHoverStyle("interactImageHover");
        refreshButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                refreshButton.setPrompt(lang.inputQueueRescan());
            }
        });
        refreshButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().onRefresh();
            }
        });

        section1.setControls(refreshButton);
        section1.setResizeable(true);
        section1.setExpanded(true);
        sectionStack.addSection(section1, 0);
        section1.setAttribute(SECTION_INPUT_ID, "yes");
    }

    @Override
    public ButtonItem getCreateButton() {
        return createButton;
    }

    @Override
    public CheckboxItem getKeepCheckbox() {
        return keepCheckbox;
    }

    @Override
    public SelectItem getSelectModel() {
        return selectModel;
    }

    @Override
    public TextItem getNewName() {
        return name;
    }

    @Override
    public TextItem getDateIssued() {
        return dateIssued;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void enableCheckbox(boolean isEnabled) {
        if (isEnabled) {
            keepCheckbox.enable();
        } else {
            keepCheckbox.disable();
        }
    }

    public static class SubstructureTreeNode
            extends TreeNode {

        public static final String ROOT_ID = "1";

        public static final String ROOT_OBJECT_ID = "0";

        public SubstructureTreeNode(String id,
                                    String parent,
                                    int scanIndex,
                                    String name,
                                    String uuid,
                                    String type,
                                    String typeId,
                                    String pageType,
                                    String dateIssued,
                                    boolean isOpen,
                                    boolean exist) {
            setAttribute(Constants.ATTR_ID, id);
            setAttribute(Constants.ATTR_PARENT, parent);
            setAttribute(Constants.ATTR_SCAN_INDEX, scanIndex);
            setAttribute(Constants.ATTR_NAME, name);
            setAttribute(Constants.ATTR_PICTURE, uuid);
            setAttribute(Constants.ATTR_TYPE, type);
            setAttribute(Constants.ATTR_TYPE_ID, typeId);
            setAttribute(Constants.ATTR_PAGE_TYPE, pageType);
            setAttribute(Constants.ATTR_DATE_ISSUED, dateIssued);
            setAttribute("isOpen", isOpen);
            setAttribute(Constants.ATTR_EXIST, exist);
            setAttribute(Constants.ATTR_CREATE, !exist);

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSubstructure(String id,
                                int scanIndex,
                                String name,
                                String uuid,
                                String type,
                                String typeId,
                                String parent,
                                String pageType,
                                String dateIssued,
                                boolean isOpen,
                                boolean exist) {
        TreeNode parentNode = structureTree.findById(parent);
        structureTree.add(new SubstructureTreeNode(id,
                                                   parent,
                                                   scanIndex,
                                                   name,
                                                   uuid,
                                                   type,
                                                   typeId,
                                                   pageType,
                                                   dateIssued,
                                                   isOpen,
                                                   exist), parentNode);
        structureTreeGrid.setData(structureTree);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasCreateButtonAClickHandler() {
        return createButton.getAttributeAsBoolean(CREATE_BUTTON_HAS_A_HANDLER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateButtonHasAClickHandler() {
        createButton.setAttribute(CREATE_BUTTON_HAS_A_HANDLER, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addUndoRedo(boolean useUndoList, boolean isRedoOperation) {
        Tree tree = structureTreeGrid.getData();

        if (tree != null && tree.findById(SubstructureTreeNode.ROOT_OBJECT_ID) != null) {

            Tree newTree = new Tree();
            newTree.setModelType(TreeModelType.PARENT);
            newTree.setRootValue(SubstructureTreeNode.ROOT_ID);
            newTree.setIdField(Constants.ATTR_ID);
            newTree.setParentIdField(Constants.ATTR_PARENT);
            newTree.setOpenProperty("isOpen");
            newTree.setData(copyOfTree(tree, tree.getChildren(tree.getRoot())));

            if (useUndoList) {
                undoList.add(newTree);
                if (undoList.size() > 0) undoButton.enable();
                if (!isRedoOperation && redoList.size() > 0) {
                    redoList = new ArrayList<Tree>();
                    redoButton.setDisabled(true);
                }
            } else {
                redoList.add(newTree);
                redoButton.enable();
            }
        }
    }

    private TreeNode[] copyOfTree(Tree tree, TreeNode[] childrenTreeNodes) {

        TreeNode[] newTreeNodes = new TreeNode[childrenTreeNodes.length];

        if (childrenTreeNodes != null && childrenTreeNodes.length > 0) {
            int i = 0;
            for (TreeNode childNode : childrenTreeNodes) {

                TreeNode newTreeNode =
                        new SubstructureTreeNode(childNode.getAttribute(Constants.ATTR_ID),
                                                 childNode.getAttribute(Constants.ATTR_PARENT),
                                                 childNode.getAttributeAsInt(Constants.ATTR_SCAN_INDEX),
                                                 childNode.getAttribute(Constants.ATTR_NAME),
                                                 childNode.getAttribute(Constants.ATTR_PICTURE),
                                                 childNode.getAttribute(Constants.ATTR_TYPE),
                                                 childNode.getAttribute(Constants.ATTR_TYPE_ID),
                                                 childNode.getAttribute(Constants.ATTR_PAGE_TYPE),
                                                 childNode.getAttribute(Constants.ATTR_DATE_ISSUED),
                                                 childNode.getAttributeAsBoolean("isOpen"),
                                                 childNode.getAttributeAsBoolean(Constants.ATTR_EXIST));
                String altoPath = childNode.getAttributeAsString(Constants.ATTR_ALTO_PATH);
                if (altoPath != null) {
                    newTreeNode.setAttribute(Constants.ATTR_ALTO_PATH, altoPath);
                }
                TreeNode[] children = tree.getChildren(childNode);
                if (children.length > 0) {
                    newTreeNode.setChildren(copyOfTree(tree, children));
                }
                newTreeNodes[i++] = newTreeNode;
            }
        }
        return newTreeNodes;
    }

    private ImgButton getMenuButton() {
        final Menu menu = new Menu();
        menu.setShowShadow(true);
        menu.setShadowDepth(3);

        MenuItem saveStructure = new MenuItem(lang.saveStructure() + "...", "icons/16/save_structure.png");
        MenuItem loadStructure = new MenuItem(lang.loadStructure() + "...", "icons/16/load_structure.png");
        saveStructure.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                SaveStructureEvent.fire(getUiHandlers().getBus());
            }
        });
        loadStructure.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                getUiHandlers().loadStructure();
            }
        });
        menu.setItems(saveStructure, loadStructure);
        final ImgButton menuButton = new ImgButton();
        menuButton.setShowTitle(false);
        menuButton.setSrc("[SKIN]headerIcons/save.png");
        menuButton.setSize(16);
        menuButton.setShowRollOver(true);
        menuButton.setCanHover(true);
        menuButton.setShowDownIcon(false);
        menuButton.setShowDown(false);
        menuButton.setHoverOpacity(75);
        menuButton.setHoverStyle("interactImageHover");
        menuButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                menuButton.setPrompt(lang.opensStructureTreeMenu());
            }
        });
        menuButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                menu.showContextMenu();
            }
        });
        return menuButton;
    }
}