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

package cz.mzk.editor.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
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

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.presenter.CreateObjectMenuPresenter;
import cz.mzk.editor.client.uihandlers.CreateObjectMenuUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.InputQueueTree;
import cz.mzk.editor.client.view.other.SubstructureTreeNode;
import cz.mzk.editor.client.view.window.ConnectExistingObjectWindow;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.client.view.window.NewObjectBasicInfoWindow;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.event.SaveStructureEvent;

/**
 * @author Jiri Kremser
 * @version 12.11.2011
 */
public class CreateObjectMenuView
        extends ViewWithUiHandlers<CreateObjectMenuUiHandlers>
        implements CreateObjectMenuPresenter.MyView {

    private final LangConstants lang;

    /** The input tree. */
    private InputQueueTree inputTree;

    private TreeGrid structureTreeGrid;

    private Tree structureTree;

    /** The section stack. */
    private SectionStack sectionStack;

    private SectionStackSection createStructure;

    private SectionStackSection structure;

    /** The layout. */
    private VLayout layout;

    private boolean connect2ExEnabled;
    private boolean connectEx2Enabled;
    private boolean removeSelectedEnabled;
    private boolean editSelectedEnabled;
    private boolean scannedTabSelected;
    private boolean addAltoEnabled;

    private List<Tree> undoList;
    private ImgButton undoButton;
    private List<Tree> redoList;
    private ImgButton redoButton;
    private ImgButton addOcrButton;

    private final EventBus eventBus;

    private SelectItem creationModeItem;

    private Layout sectionCreateLayout;

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public CreateObjectMenuView(final LangConstants lang, final EventBus eventBus) {
        this.lang = lang;
        this.eventBus = eventBus;

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
        structureTreeGrid.setShowAllRecords(true);
        structureTreeGrid.setCanHover(true);
        structureTreeGrid.setHoverOpacity(75);
        structureTreeGrid.setHoverWidth(350);
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
        structureTreeGrid.setCanSort(true);

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
                undo();
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

        addOcrButton = new ImgButton();
        addOcrButton.setSrc("icons/16/ocrAlto.png");
        addOcrButton.setSize("16", "16");
        addOcrButton.setShowTitle(false);
        addOcrButton.setHoverOpacity(75);
        addOcrButton.setHoverStyle("interactImageHover");
        addOcrButton.setShowRollOver(false);
        addOcrButton.setShowDown(false);
        addOcrButton.setCanHover(true);
        addOcrButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                addOcrButton.setPrompt(lang.addOcrBatch());
            }
        });

        addOcrButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().addAltoBatch(structureTreeGrid.getSelectedRecords());
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
                    final Record[] selection;
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

                    final DigitalObjectModel targetModel;
                    DigitalObjectModel movedModel;

                    if (dropPlace != null) {
                        targetModel =
                                DigitalObjectModel.parseString(dropPlace
                                        .getAttribute(Constants.ATTR_MODEL_ID));
                        List<DigitalObjectModel> possibleChildModels =
                                NamedGraphModel.getChildren(targetModel);

                        if (tileGrid == null) {
                            movedModel =
                                    DigitalObjectModel.parseString(selection[0]
                                            .getAttribute(Constants.ATTR_MODEL_ID));
                        } else {
                            movedModel = DigitalObjectModel.PAGE;
                        }
                        if ((possibleChildModels == null || !possibleChildModels.contains(movedModel))
                                && (targetModel != DigitalObjectModel.PAGE || targetModel != DigitalObjectModel.PAGE)) {

                            if (targetModel == movedModel) {

                                TreeNode targetNode =
                                        structureTree.findById(dropPlace.getAttribute(Constants.ATTR_ID));
                                TreeNode movedNode =
                                        structureTree.findById(selection[0].getAttribute(Constants.ATTR_ID));
                                structureTree.move(movedNode,
                                                   structureTree.getParent(targetNode),
                                                   structureTreeGrid.getEventRow());
                            } else {
                                SC.say(lang.objNotDropable() + ": <code>" + movedModel.getValue() + "</code>");
                            }
                            event.cancel();
                            return;
                        }
                    } else {
                        SC.say(lang.dropNotHere());
                        event.cancel();
                        return;
                    }

                    final String targetId = dropPlace.getAttribute(Constants.ATTR_ID);
                    if (treeGrid == null) {
                        if (targetModel == DigitalObjectModel.PAGE) {
                            SC.say(lang.pageNotDropable());
                            event.cancel();
                            return;
                        }
                        event.cancel();
                    }
                    addUndoRedo(true, false);
                    TreeNode targetNode = structureTreeGrid.getTree().findById(targetId);
                    final TreeNode parentNode =
                            targetModel == DigitalObjectModel.PAGE ? structureTreeGrid.getTree()
                                    .getParent(targetNode) : targetNode;
                    final DigitalObjectModel parentModel =
                            DigitalObjectModel.parseString(parentNode.getAttribute(Constants.ATTR_MODEL_ID));

                    if ((movedModel == DigitalObjectModel.PAGE
                            && targetModel == DigitalObjectModel.INTERNALPART || parentModel == DigitalObjectModel.INTERNALPART)) {

                        final TreeNode intParNodeId =
                                parentModel == DigitalObjectModel.INTERNALPART ? structureTreeGrid.getTree()
                                        .getParent(parentNode) : parentNode;

                        final List<Record> missingPages = getMissingPages(intParNodeId, selection);
                        if (treeGrid != null) {
                            for (ListGridRecord record : treeGrid.getSelectedRecords()) {
                                TreeNode recordParent =
                                        structureTreeGrid.getTree().getParent(structureTreeGrid.getTree()
                                                .findById(record.getAttributeAsString(Constants.ATTR_ID)));
                                if (DigitalObjectModel.parseString(recordParent
                                        .getAttribute(Constants.ATTR_MODEL_ID)) != DigitalObjectModel.INTERNALPART) {
                                    String uuidRecord =
                                            record.getAttributeAsString(Constants.ATTR_PICTURE_OR_UUID);
                                    boolean found = false;
                                    for (Record misRecord : missingPages) {
                                        if (misRecord.getAttributeAsString(Constants.ATTR_PICTURE_OR_UUID)
                                                .equals(uuidRecord)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) missingPages.add(record);
                                }
                            }
                        }

                        if (missingPages != null && !missingPages.isEmpty()) {
                            final TreeGrid finalTreeGrid = treeGrid;
                            SC.ask(lang.missingPages(), new BooleanCallback() {

                                @Override
                                public void execute(Boolean value) {
                                    if (value != null && value) {
                                        if (finalTreeGrid == null) {
                                            addUndoRedo(true, false);
                                            getUiHandlers().addPages(Arrays.asList(selection),
                                                                     targetId,
                                                                     false);
                                        }
                                        getUiHandlers()
                                                .addPages(missingPages,
                                                          intParNodeId.getAttribute(Constants.ATTR_ID),
                                                          false);
                                    } else {
                                        if (finalTreeGrid != null) undo();
                                    }
                                }

                            });
                        } else {
                            if (treeGrid == null) {
                                addUndoRedo(true, false);
                                getUiHandlers().addPages(Arrays.asList(selection), targetId, false);
                            }
                        }
                    } else {
                        if (treeGrid == null) {
                            addUndoRedo(true, false);
                            getUiHandlers().addPages(Arrays.asList(selection), targetId, false);
                        }
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
                        DigitalObjectModel.parseString(selection[0].getAttribute(Constants.ATTR_MODEL_ID));
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
                                            .getAttribute(Constants.ATTR_MODEL_ID)));
                            String newParentId = String.valueOf(getUiHandlers().newId());

                            // add new parent
                            addSubstructure(newParentId,
                                            parent.getAttribute(parentIsTopLvl ? Constants.ATTR_ID
                                                    : Constants.ATTR_PARENT),
                                            uuidField.getValueAsString(),
                                            uuidField.getValueAsString(),
                                            getModel().getValue(),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            true,
                                            true);
                            for (Record rec : selection) {
                                addSubstructure(String.valueOf(getUiHandlers().newId()),
                                                newParentId,
                                                rec.getAttribute(Constants.ATTR_NAME),
                                                rec.getAttribute(Constants.ATTR_PICTURE_OR_UUID),
                                                rec.getAttribute(Constants.ATTR_MODEL_ID),
                                                rec.getAttribute(Constants.ATTR_TYPE),
                                                rec.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME),
                                                rec.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE),
                                                rec.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO),
                                                rec.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR),
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
                                .getAttribute(Constants.ATTR_MODEL_ID));

                new ConnectExistingObjectWindow(lang, false, model, eventBus) {

                    @Override
                    protected void doAction(TextItem uuidField) {
                        addUndoRedo(true, false);
                        addSubstructure(String.valueOf(getUiHandlers().newId()),
                                        structureTreeGrid.getSelectedRecords()[0]
                                                .getAttribute(Constants.ATTR_ID),
                                        uuidField.getValueAsString(),
                                        uuidField.getValueAsString(),
                                        getModel().getValue(),
                                        getModel() == DigitalObjectModel.PAGE ? Constants.PAGE_TYPES.NP
                                                .toString() : "",
                                        "",
                                        "",
                                        "",
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
                new NewObjectBasicInfoWindow(structureTreeGrid.getSelectedRecords()[0], lang, eventBus, true) {

                    @Override
                    protected void doSaveAction(ListGridRecord record) {
                        setChangedRecord(record);
                        structureTreeGrid.redraw();
                    }
                };
            }
        });
        edit.setEnableIfCondition(new MenuItemIfFunction() {

            @Override
            public boolean execute(Canvas target, Menu menu, MenuItem item) {
                return editSelectedEnabled || !scannedTabSelected;
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
                String modelStr = selection[0].getAttribute(Constants.ATTR_MODEL_ID);
                connect2ExEnabled = true;
                removeSelectedEnabled = true;
                for (int i = 0; i < selection.length; i++) {
                    // root mustn't be selected and all selected items must be of the same type
                    connect2ExEnabled &=
                            (i == 0 || modelStr.equals(selection[i].getAttribute(Constants.ATTR_MODEL_ID)));

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
                                && selection[0].getAttribute(Constants.ATTR_MODEL_ID)
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
        typeField.setName(Constants.ATTR_MODEL_ID);
        typeField.setTitle(lang.dcType());
        typeField.setWidth("40%");
        typeField.setCanSort(false);
        typeField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                return LabelAndModelConverter.getLabelFromModel().get(record
                        .getAttribute(Constants.ATTR_MODEL_ID));
            }
        });

        TreeGridField nameField = new TreeGridField();
        nameField.setCanFilter(true);
        nameField.setCanEdit(true);
        nameField.setName(Constants.ATTR_NAME);
        nameField.setCanSort(false);
        nameField.setTitle(lang.name());
        nameField.setWidth("*");
        nameField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                String altoPath = record.getAttributeAsString(Constants.ATTR_ALTO_PATH);
                String ocrPath = record.getAttributeAsString(Constants.ATTR_OCR_PATH);
                String stringToReturn = record.getAttributeAsString(Constants.ATTR_NAME);
                if (record.getAttributeAsString(Constants.ATTR_NAME) == null
                        || "".equals(record.getAttributeAsString(Constants.ATTR_NAME))) {
                    DigitalObjectModel recModel =
                            DigitalObjectModel.parseString(record.getAttribute(Constants.ATTR_MODEL_ID));
                    if (recModel == DigitalObjectModel.PERIODICALVOLUME
                            || recModel == DigitalObjectModel.PERIODICALITEM)
                        stringToReturn = record.getAttributeAsString(Constants.ATTR_PART_NUMBER_OR_ALTO);
                }
                if (ocrPath != null && !"".equals(ocrPath)) {
                    if (altoPath != null && !"".equals(altoPath)) {
                        stringToReturn =
                                "<img src=\"images/icons/16/ocrAlto.png\">".concat(record
                                        .getAttributeAsString(Constants.ATTR_NAME));
                    } else {
                        stringToReturn =
                                "<img src=\"images/icons/16/ocr.png\">".concat(record
                                        .getAttributeAsString(Constants.ATTR_NAME));
                    }
                }

                return stringToReturn;
            }
        });

        structureTreeGrid.setFields(typeField, nameField);

        createStructure = new SectionStackSection();
        createStructure.setTitle(lang.createSubStructure());
        createStructure.setResizeable(true);
        createStructure.setExpanded(true);

        creationModeItem = new SelectItem("creationMode", lang.creationMode());
        creationModeItem.setValueMap(lang.atOnce(), lang.sequential());
        creationModeItem.setValues(lang.atOnce());
        creationModeItem.setWidth(72);
        creationModeItem.setHeight(18);

        DynamicForm dynForm = new DynamicForm();
        dynForm.setItems(creationModeItem);

        sectionCreateLayout = new Layout();
        createStructure.setControls(dynForm);
        createStructure.setItems(sectionCreateLayout);
        sectionCreateLayout.setAlign(Alignment.CENTER);

        structure = new SectionStackSection();
        structure.setTitle(lang.substructures());
        structure.setResizeable(true);
        structure.setItems(structureTreeGrid);
        structure.setExpanded(true);

        structure.setControls(undoButton, redoButton, addOcrButton, menuButton);

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

    private void undo() {
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

    @Override
    public List<Record> getMissingPages(TreeNode parentNode, Record[] selection) {

        List<Record> missing = new ArrayList<Record>();

        TreeNode[] allNodes = structureTreeGrid.getTree().getChildren(parentNode);

        for (int i = 0; i < selection.length; i++) {
            boolean found = false;
            String selPicture = selection[i].getAttribute(Constants.ATTR_PICTURE_OR_UUID);
            if (selPicture != null && !"".equals(selPicture)) {
                for (int j = 0; j < allNodes.length; j++) {
                    String childPicture = allNodes[j].getAttribute(Constants.ATTR_PICTURE_OR_UUID);
                    if (childPicture != null && childPicture.equals(selPicture)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    missing.add(selection[i]);
                }
            }
        }
        return missing;
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
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getInputTree()
     */
    @Override
    public InputQueueTree getInputTree() {
        return inputTree;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRecentlyModifiedTree()
     */
    @Override
    public TreeGrid getSubelementsGrid() {
        return structureTreeGrid;
    }

    @Override
    public void setStructureTree(Tree tree) {
        structureTree = tree;
    }

    @Override
    public SectionStack getSectionStack() {
        return sectionStack;
    }

    /**
     * @return the creationModeItem
     */
    @Override
    public SelectItem getCreationModeItem() {
        return creationModeItem;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInputTree(DispatchAsync dispatcher, final PlaceManager placeManager) {
        InputQueueTree.setInputTreeToSection(dispatcher, lang, eventBus, sectionStack, placeManager, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSubstructure(String id,
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
                                boolean exist) {
        TreeNode parentNode = structureTree.findById(parent);
        structureTree.add(new SubstructureTreeNode(id,
                                                   parent,
                                                   name,
                                                   pictureOrUuid,
                                                   modelId,
                                                   type,
                                                   dateOrIntPartName,
                                                   noteOrIntSubtitle,
                                                   partNumberOrAlto,
                                                   aditionalInfoOrOcr,
                                                   isOpen,
                                                   exist), parentNode);
        structureTreeGrid.setData(structureTree);
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
                                                 childNode.getAttribute(Constants.ATTR_NAME),
                                                 childNode.getAttribute(Constants.ATTR_PICTURE_OR_UUID),
                                                 childNode.getAttribute(Constants.ATTR_MODEL_ID),
                                                 childNode.getAttribute(Constants.ATTR_TYPE),
                                                 childNode.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME),
                                                 childNode.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE),
                                                 childNode.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO),
                                                 childNode.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR),
                                                 childNode.getAttributeAsBoolean("isOpen"),
                                                 childNode.getAttributeAsBoolean(Constants.ATTR_EXIST));
                String altoPath = childNode.getAttributeAsString(Constants.ATTR_ALTO_PATH);
                if (altoPath != null) {
                    newTreeNode.setAttribute(Constants.ATTR_ALTO_PATH, altoPath);
                }
                String ocrPath = childNode.getAttributeAsString(Constants.ATTR_OCR_PATH);
                if (ocrPath != null) {
                    newTreeNode.setAttribute(Constants.ATTR_OCR_PATH, ocrPath);
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
                getUiHandlers().getBus().fireEvent(new SaveStructureEvent());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSectionCreateLayout(Layout layout) {
        Canvas[] members = sectionCreateLayout.getMembers();
        if (members.length != 0) {
            sectionCreateLayout.removeMember(members[0]);
        }
        sectionCreateLayout.addMember(layout);
    }

    /**
     * @param scannedTabSelected
     *        the scannedTabSelected to set
     */
    @Override
    public void setScannedTabSelected(boolean scannedTabSelected) {
        this.scannedTabSelected = scannedTabSelected;
    }

}
