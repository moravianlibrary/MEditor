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
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
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

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.gwtrpcds.RecentlyTreeGwtRPCDS;
import cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.mzk.editor.client.uihandlers.DigitalObjectMenuUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.InputQueueTree;
import cz.mzk.editor.client.view.window.EditorSC;
import cz.mzk.editor.shared.rpc.LockInfo;

/**
 * @author Jiri Kremser
 */
public class DigitalObjectMenuView
        extends ViewWithUiHandlers<DigitalObjectMenuUiHandlers>
        implements DigitalObjectMenuPresenter.MyView {

    /** The Constant SECTION_RELATED_ID. */
    private static final String SECTION_RELATED_ID = "related";

    private final LangConstants lang;

    /**
     * The Interface Refreshable.
     */
    public interface Refreshable {

        /**
         * Refresh tree.
         */
        void refreshTree();
    }

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

    /** The layout. */
    private final VLayout layout;

    private final SelectItem selectItem = new SelectItem();
    private final EventBus eventBus;

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public DigitalObjectMenuView(final LangConstants lang, final EventBus eventBus) {
        this.lang = lang;
        this.eventBus = eventBus;

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
                            EditorSC.objectIsLock(lang,
                                                  new LockInfo(lockOwner,
                                                               rollOverRecord
                                                                       .getAttributeAsString(Constants.ATTR_LOCK_DESCRIPTION),
                                                               rollOverRecord
                                                                       .getAttributeAsStringArray(Constants.ATTR_TIME_TO_EXP_LOCK)));
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
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
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
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#showInputQueue(com.gwtplatform.dispatch.client.DispatchAsync)
     */
    @Override
    public void showInputQueue(DispatchAsync dispatcher, final PlaceManager placeManager, boolean force) {
        InputQueueTree.setInputTreeToSection(dispatcher, lang, eventBus, sectionStack, placeManager, force);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRecentlyModifiedTree()
     */
    @Override
    public ListGrid getRecentlyModifiedGrid() {
        return sideNavGrid;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
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
     * @see cz.mzk.editor.client.presenter.DigitalObjectMenuPresenter.
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