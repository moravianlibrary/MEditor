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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.client.presenter.CreateObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.SideNavInputTree;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateObjectMenuView.
 */
public class CreateObjectMenuView
        extends ViewWithUiHandlers<CreateObjectMenuView.MyUiHandlers>
        implements CreateObjectMenuPresenter.MyView {

    /** The Constant SECTION_RELATED_ID. */
    private static final String SECTION_RELATED_ID = "related";

    private static final String SECTION_INPUT_ID = "input";

    private final LangConstants lang;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        void onRefresh();

        void onAddSubelement(final RecentlyModifiedItem item, final List<? extends List<String>> related);

        void revealItem(String uuid);

    }

    /** The input tree. */
    private SideNavInputTree inputTree;

    private final ListGrid structureGrid;

    /** The section stack. */
    private final SectionStack sectionStack;

    private final SectionStackSection createStructure;

    private final SectionStackSection structure;

    /** The refresh button. */
    private ImgButton refreshButton;

    private final ButtonItem createButton;

    private final CheckboxItem keepCheckbox;

    private final SelectItem selectModel;

    /** The layout. */
    private final VLayout layout;

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public CreateObjectMenuView(LangConstants lang) {
        this.lang = lang;
        layout = new VLayout();

        layout.setHeight100();
        layout.setWidth100();
        layout.setOverflow(Overflow.AUTO);

        structureGrid = new ListGrid();
        structureGrid.setWidth100();
        structureGrid.setHeight100();
        structureGrid.setShowSortArrow(SortArrow.CORNER);
        structureGrid.setShowAllRecords(true);
        structureGrid.setCanHover(true);
        structureGrid.setHoverOpacity(75);
        structureGrid.setHoverStyle("interactImageHover");
        ListGridField rangeField = new ListGridField();
        rangeField.setCanFilter(true);
        rangeField.setName(Constants.RANGE);
        rangeField.setTitle(lang.range());
        ListGridField typeField = new ListGridField();
        typeField.setCanFilter(true);
        typeField.setName(Constants.RANGE);
        typeField.setTitle(lang.dcType());
        structureGrid.setFields(rangeField, typeField);

        createStructure = new SectionStackSection();
        createStructure.setTitle(lang.createSubStructure());
        createStructure.setResizeable(true);
        selectModel = new SelectItem();
        selectModel.setTitle(lang.dcType());

        Map<String, String> labelsSingular = new HashMap<String, String>();
        labelsSingular.put(DigitalObjectModel.INTERNALPART.getValue(), lang.internalpart());
        labelsSingular.put(DigitalObjectModel.MONOGRAPH.getValue(), lang.monograph());
        labelsSingular.put(DigitalObjectModel.MONOGRAPHUNIT.getValue(), lang.monographunit());
        labelsSingular.put(DigitalObjectModel.PAGE.getValue(), lang.page());
        labelsSingular.put(DigitalObjectModel.PERIODICAL.getValue(), lang.periodical());
        labelsSingular.put(DigitalObjectModel.PERIODICALITEM.getValue(), lang.periodicalitem());
        labelsSingular.put(DigitalObjectModel.PERIODICALVOLUME.getValue(), lang.periodicalvolume());
        DigitalObjectModel[] models = DigitalObjectModel.values();
        String[] values = new String[models.length];
        for (int i = 0; i < models.length; i++) {
            values[i] = labelsSingular.get(models[i].getValue());
        }
        selectModel.setValueMap(values);
        keepCheckbox = new CheckboxItem();
        keepCheckbox.setTitle(lang.keepOnRight());
        createButton = new ButtonItem();
        createButton.setTitle(lang.create());
        createButton.setAlign(Alignment.CENTER);
        createButton.setColSpan(2);
        final DynamicForm form = new DynamicForm();
        form.setNumCols(2);
        form.setWidth100();

        form.setFields(selectModel, keepCheckbox, createButton);

        createStructure.setItems(form);
        createStructure.setExpanded(true);

        structure = new SectionStackSection();
        structure.setTitle(lang.substructures());
        structure.setResizeable(true);
        structure.setItems(structureGrid);
        structure.setExpanded(true);

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
     * MyView#expandNode(java.lang.String)
     */
    @Override
    public void expandNode(String id) {
        // TODO Auto-generated method stub

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
    public ListGrid getSubelementsGrid() {
        return structureGrid;
    }

    @Override
    public SectionStack getSectionStack() {
        return sectionStack;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInputTree(SideNavInputTree tree) {
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
}