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

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.RecentlyTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.window.EditorSC;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectMenuView.
 */
public class DigitalObjectMenuView
        extends ViewWithUiHandlers<DigitalObjectMenuView.MyUiHandlers>
        implements DigitalObjectMenuPresenter.MyView {

    /** The Constant SECTION_RELATED_ID. */
    private static final String SECTION_RELATED_ID = "related";

    private static final String SECTION_INPUT_ID = "input";

    private final LangConstants lang;

    private final boolean inputQueueShown = false;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        /**
         * On refresh.
         */
        void onRefresh();

        void refreshRecentlyModified();

        /**
         * On show input queue.
         */
        void onShowInputQueue(SideNavInputTree tree);

        /**
         * On add digital object.
         * 
         * @param item
         *        the item
         * @param related
         *        the related
         */
        void onAddDigitalObject(final RecentlyModifiedItem item, final List<? extends List<String>> related);

        /**
         * Reveal modified item.
         * 
         * @param uuid
         *        the uuid
         */
        void revealItem(String uuid);

    }

    /**
     * The Interface Refreshable.
     */
    public interface Refreshable {

        /**
         * Refresh tree.
         */
        void refreshTree();
    }

    /** The input tree. */
    private SideNavInputTree inputTree;

    /** The side nav grid. */
    private final ListGrid sideNavGrid;

    /** The section stack. */
    private final SectionStack sectionStack;

    /** The section recently modified. */
    private final SectionStackSection sectionRecentlyModified;

    /** The section related. */
    private final SectionStackSection sectionRelated;

    /** The roll over canvas */
    private HLayout rollOverCanvas;

    /** The section related. */
    private final ListGrid relatedGrid;

    /** The refresh button. */
    private ImgButton refreshButton;

    /** The layout. */
    private final VLayout layout;

    private final SelectItem selectItem = new SelectItem();

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public DigitalObjectMenuView(final LangConstants lang) {
        this.lang = lang;
        layout = new VLayout();

        layout.setHeight100();
        layout.setWidth100();
        layout.setOverflow(Overflow.AUTO);

        relatedGrid = new ListGrid();
        relatedGrid.setWidth100();
        relatedGrid.setHeight100();
        relatedGrid.setShowSortArrow(SortArrow.CORNER);
        relatedGrid.setShowAllRecords(true);
        relatedGrid.setAutoFetchData(false);
        relatedGrid.setCanHover(true);
        relatedGrid.setCanSort(false);
        ListGridField field1 = new ListGridField("relation", lang.relation());
        field1.setWidth("40%");
        ListGridField field2 = new ListGridField("uuid", "PID");
        field2.setWidth("*");
        relatedGrid.setFields(field1, field2);
        sectionRelated = new SectionStackSection();
        sectionRelated.setID(SECTION_RELATED_ID);
        sectionRelated.setTitle(lang.referencedBy());
        sectionRelated.setResizeable(true);
        sectionRelated.setItems(relatedGrid);
        sectionRelated.setExpanded(false);

        sideNavGrid = new ListGrid() {

            @Override
            protected Canvas getRollOverCanvas(Integer rowNum, Integer colNum) {
                final ListGridRecord rollOverRecord = this.getRecord(rowNum);
                if (rollOverCanvas == null) {
                    rollOverCanvas = new HLayout();
                    rollOverCanvas.setSnapTo("TR");
                    rollOverCanvas.setWidth(50);
                    rollOverCanvas.setHeight(22);
                }

                if (rollOverCanvas.getChildren().length > 0) {
                    rollOverCanvas.removeChild(rollOverCanvas.getChildren()[0]);
                }
                final String lockOwner = rollOverRecord.getAttributeAsString(Constants.ATTR_LOCK_OWNER);
                if (lockOwner != null) {
                    ImgButton lockImg = new ImgButton();
                    lockImg.setShowDown(false);
                    lockImg.setShowRollOver(false);
                    lockImg.setLayoutAlign(Alignment.CENTER);

                    if ("".equals(lockOwner)) {
                        lockImg.setSrc("icons/16/lock_lock_all.png");
                    } else if (lockOwner.length() > 0) {
                        lockImg.setSrc("icons/16/lock_lock_all_red.png");
                    }

                    lockImg.setPrompt(lang.lockInfoButton());
                    lockImg.setHeight(16);
                    lockImg.setWidth(16);
                    lockImg.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                        @Override
                        public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                            EditorSC.objectIsLock(lang, lockOwner, rollOverRecord
                                    .getAttributeAsString(Constants.ATTR_LOCK_DESCRIPTION), rollOverRecord
                                    .getAttribute(Constants.ATTR_TIME_TO_EXP_LOCK));
                        }
                    });
                    rollOverCanvas.addChild(lockImg);
                }
                return rollOverCanvas;
            }
        };
        sideNavGrid.setShowSelectionCanvas(false);

        sideNavGrid.setWidth100();
        sideNavGrid.setHeight100();
        sideNavGrid.setShowSortArrow(SortArrow.CORNER);
        sideNavGrid.setShowAllRecords(true);
        sideNavGrid.setAutoFetchData(true);
        sideNavGrid.setCanHover(true);
        sideNavGrid.setHoverOpacity(75);
        sideNavGrid.setHoverStyle("interactImageHover");
        sideNavGrid.setShowRollOverCanvas(true);

        final DynamicForm form = new DynamicForm();
        form.setHeight(1);
        form.setWidth(60);
        form.setNumCols(1);

        selectItem.setWidth(60);
        selectItem.setShowTitle(false);
        selectItem.setValueMap(lang.me(), lang.all());
        selectItem.setDefaultValue(lang.me());
        selectItem.setHoverOpacity(75);
        selectItem.setHoverStyle("interactImageHover");
        selectItem.addItemHoverHandler(new ItemHoverHandler() {

            @Override
            public void onItemHover(ItemHoverEvent event) {
                selectItem.setPrompt(DigitalObjectMenuView.this.lang.showModifiedHint()
                        + selectItem.getValue());

            }
        });
        selectItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                getUiHandlers().refreshRecentlyModified();
            }
        });

        form.setFields(selectItem);
        form.setTitle("by:");

        sectionRecentlyModified = new SectionStackSection();
        sectionRecentlyModified.setTitle(lang.recentlyModified());
        sectionRecentlyModified.setResizeable(true);
        sectionRecentlyModified.setItems(sideNavGrid);
        sectionRecentlyModified.setControls(form);
        sectionRecentlyModified.setExpanded(true);

        sectionStack = new SectionStack();
        sectionStack.addSection(sectionRelated);
        sectionStack.addSection(sectionRecentlyModified);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setAnimateSections(true);
        sectionStack.setWidth100();
        sectionStack.setHeight100();
        sectionStack.setOverflow(Overflow.HIDDEN);
        layout.addMember(sectionStack);
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
     * MyView#setDS(com.gwtplatform.dispatch.client.DispatchAsync)
     */
    @Override
    public void setDS(DispatchAsync dispatcher, EventBus bus) {
        this.sideNavGrid.setDataSource(new RecentlyTreeGwtRPCDS(dispatcher, lang, bus));
        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        nameField.setRequired(true);
        nameField.setWidth("40%");
        nameField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                String owner = record.getAttribute(Constants.ATTR_LOCK_OWNER);
                if (owner != null) {
                    return ("".equals(owner) ? RecentlyTreeGwtRPCDS.FIRST_PART_OF_COLOR_LOCK_BY_USER
                            : RecentlyTreeGwtRPCDS.FIRST_PART_OF_COLOR_LOCK)
                            + record.getAttribute(Constants.ATTR_NAME)
                            + RecentlyTreeGwtRPCDS.SECOND_PART_OF_COLOR_LOCK;
                } else {
                    return record.getAttribute(Constants.ATTR_NAME);
                }
            }
        });
        ListGridField uuidField = new ListGridField(Constants.ATTR_UUID, "PID");
        uuidField.setRequired(true);
        uuidField.setWidth("*");
        uuidField.setCellFormatter(new CellFormatter() {

            @Override
            public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
                String owner = record.getAttribute(Constants.ATTR_LOCK_OWNER);
                if (owner != null) {
                    return ("".equals(owner) ? RecentlyTreeGwtRPCDS.FIRST_PART_OF_COLOR_LOCK_BY_USER
                            : RecentlyTreeGwtRPCDS.FIRST_PART_OF_COLOR_LOCK)
                            + record.getAttribute(Constants.ATTR_UUID)
                            + RecentlyTreeGwtRPCDS.SECOND_PART_OF_COLOR_LOCK;
                } else {
                    return record.getAttribute(Constants.ATTR_UUID);
                }
            }
        });
        sideNavGrid.setFields(nameField, uuidField);
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#showInputQueue(com.gwtplatform.dispatch.client.DispatchAsync)
     */
    @Override
    public void showInputQueue(SideNavInputTree tree,
                               DispatchAsync dispatcher,
                               final PlaceManager placeManager) {
        SectionStackSection section1 = new SectionStackSection();
        section1.setTitle(lang.inputQueue());
        if (tree == null) {
            inputTree = new SideNavInputTree(dispatcher, lang);
            inputTree.getCreateMenuItem()
                    .addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

                        @Override
                        public void onClick(final MenuItemClickEvent event) {
                            String msg = event.getMenu().getEmptyMessage();
                            String model = msg.substring(0, msg.indexOf("/"));
                            String id = msg.substring(msg.indexOf("/") + 1);

                            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.FIND_METADATA)
                                    .with(Constants.ATTR_MODEL, model).with(Constants.URL_PARAM_CODE, id));
                        }
                    });
        } else {
            String isInputSection = sectionStack.getSection(0).getAttribute(SECTION_INPUT_ID);
            if (isInputSection != null && "yes".equals(isInputSection)) {
                sectionStack.removeSection(0);
            }
            inputTree = tree;
        }
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
        refreshButton.setHoverOpacity(75);
        refreshButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                refreshButton.setPrompt("Rescan directory structure.");
            }
        });

        section1.setControls(refreshButton);
        section1.setResizeable(true);
        section1.setExpanded(true);
        sectionStack.addSection(section1, 0);
        section1.setAttribute(SECTION_INPUT_ID, "yes");
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
    public SideNavInputTree getInputTree() {
        return inputTree;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRecentlyModifiedTree()
     */
    @Override
    public ListGrid getRecentlyModifiedGrid() {
        return sideNavGrid;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#setRelatedDocuments(java.util.List)
     */
    @Override
    public void setRelatedDocuments(List<? extends List<String>> data) {
        if (data != null && data.size() != 0) {
            sectionStack.getSection(SECTION_RELATED_ID).setExpanded(true);
            // sectionRelated.setExpanded(true);
            Record[] records = new Record[data.size()];
            for (int i = 0; i < data.size(); i++) {
                records[i] = new ListGridRecord();
                records[i].setAttribute("uuid", data.get(i).get(0));
                records[i].setAttribute("relation", data.get(i).get(1));
            }
            relatedGrid.setData(records);
        } else
            sectionStack.getSection(SECTION_RELATED_ID).setExpanded(false);
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRelatedGrid()
     */
    @Override
    public ListGrid getRelatedGrid() {
        return relatedGrid;
    }

    @Override
    public SectionStack getSectionStack() {
        return sectionStack;
    }

    @Override
    public SelectItem getSelectItem() {
        return selectItem;

    }
}