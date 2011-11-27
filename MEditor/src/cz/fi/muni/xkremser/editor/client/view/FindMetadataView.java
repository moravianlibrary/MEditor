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

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.viewer.DetailViewer;

import cz.fi.muni.xkremser.editor.client.DublinCoreConstants;
import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.Z3950ResultDS;
import cz.fi.muni.xkremser.editor.client.presenter.FindMetadataPresenter;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class FindMetadataView
        extends ViewImpl
        implements FindMetadataPresenter.MyView {

    /** The layout. */
    private final VStack layout;
    private final ButtonItem findButtonZ39;
    private final ButtonItem findButtonOai;
    private final IButton nextButton;
    private final IButton withoutButton;
    private final DynamicForm form1;
    private final DynamicForm form2;
    private final TextItem searchValueZ39;
    private final TextItem searchValueOai;
    private final SelectItem oaiUrl;
    private final SelectItem oaiIdPrefix;
    private final SelectItem oaiBase;
    private final SelectItem findBy;
    private final LangConstants lang;
    private final ListGrid resultGrid;
    private final DetailViewer printViewer;
    private final SectionStack printStack;
    private ModalWindow mw;

    /**
     * Instantiates a new home view.
     */
    @Inject
    public FindMetadataView(final LangConstants lang) {
        this.lang = lang;
        layout = new VStack();
        layout.setHeight100();
        layout.setPadding(12);

        findButtonZ39 = new ButtonItem(lang.find());
        findButtonZ39.setAutoFit(true);
        findButtonZ39.setStartRow(false);
        findButtonZ39.setWidth(80);
        findButtonZ39.setColSpan(2);

        HTMLFlow html1 = new HTMLFlow();
        html1.setContents("<h1>Metadata</h1>");
        html1.setExtraSpace(15);

        findBy = new SelectItem();
        findBy.setTitle(lang.findBy());
        // findBy.setType("comboBox");
        findBy.setValueMap(Constants.SYSNO, lang.fbarcode(), lang.ftitle());
        findBy.setValue(Constants.SYSNO);
        findBy.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                searchValueZ39.setTitle((String) event.getValue());
                searchValueZ39.redraw();
            }
        });
        searchValueZ39 = new TextItem();
        // searchValue.setShowTitle(false);
        searchValueZ39.setTitle(Constants.SYSNO);
        searchValueZ39.setHoverOpacity(75);
        searchValueZ39.setHoverWidth(330);
        searchValueZ39.setHoverStyle("interactImageHover");
        searchValueZ39.addItemHoverHandler(new ItemHoverHandler() {

            @Override
            public void onItemHover(ItemHoverEvent event) {
                searchValueZ39.setPrompt("<nobr>" + lang.findHint() + "</nobr>");
            }
        });

        form1 = new DynamicForm();
        form1.setGroupTitle(lang.findMetadata() + " Z39.50");
        form1.setIsGroup(true);
        form1.setWidth(220);
        form1.setHeight(90);
        form1.setNumCols(2);
        form1.setColWidths(70, "*");
        form1.setPadding(5);
        form1.setFields(findBy, searchValueZ39, findButtonZ39);
        form1.setExtraSpace(25);

        form2 = new DynamicForm();
        form2.setGroupTitle(lang.findMetadata() + " OAI-PMH");
        form2.setIsGroup(true);
        form2.setWidth(320);
        form2.setHeight(90);
        form2.setNumCols(2);
        form2.setColWidths(70, "*");
        form2.setPadding(5);
        findButtonOai = new ButtonItem(lang.find());
        findButtonOai.setAutoFit(true);
        findButtonOai.setStartRow(false);
        findButtonOai.setWidth(80);
        findButtonOai.setColSpan(2);
        oaiUrl = new SelectItem();
        oaiUrl.setTitle(lang.furl());
        oaiUrl.setWrapTitle(false);
        oaiIdPrefix = new SelectItem();
        oaiIdPrefix.setTitle(lang.fprefix());
        oaiIdPrefix.setWrapTitle(false);
        oaiBase = new SelectItem();
        oaiBase.setTitle(lang.fbase());
        oaiBase.setWrapTitle(false);
        searchValueOai = new TextItem();
        searchValueOai.setTitle(Constants.SYSNO);
        searchValueOai.setWrapTitle(false);
        searchValueOai.setHoverOpacity(75);
        searchValueOai.setHoverWidth(330);
        searchValueOai.setHoverStyle("interactImageHover");
        searchValueOai.addItemHoverHandler(new ItemHoverHandler() {

            @Override
            public void onItemHover(ItemHoverEvent event) {
                searchValueOai.setPrompt("<nobr>" + lang.findHint() + "</nobr>");
            }
        });
        form2.setFields(oaiUrl, oaiIdPrefix, oaiBase, searchValueOai, findButtonOai);

        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(10);
        hLayout.addMember(form2);
        hLayout.addMember(form1);
        hLayout.setExtraSpace(10);

        printStack = new SectionStack();
        printStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        printStack.setWidth(600);
        printStack.setHeight(450);

        printViewer = new DetailViewer();
        printViewer.setWidth100();
        printViewer.setMargin(15);
        printViewer.setEmptyMessage(lang.fnothing());
        printViewer.setDataSource(new Z3950ResultDS(lang));

        resultGrid = new ListGrid();
        resultGrid.setHeight(240);
        resultGrid.setShowAllRecords(true);
        resultGrid.setAutoFetchData(false);
        ListGridField dcTitle = new ListGridField(DublinCoreConstants.DC_TITLE, lang.dcTitle());
        ListGridField dcPublisher = new ListGridField(DublinCoreConstants.DC_PUBLISHER, lang.dcPublisher());
        ListGridField dcDate = new ListGridField(DublinCoreConstants.DC_DATE, lang.dcDate());
        resultGrid.setFields(dcTitle, dcPublisher, dcDate);
        resultGrid.setDataSource(new Z3950ResultDS(lang));

        resultGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(final RecordClickEvent event) {
                printViewer.setData(new Record[] {event.getRecord()});
            }
        });

        SectionStackSection resultsSection = new SectionStackSection(lang.results());
        resultsSection.setExpanded(true);
        resultsSection.addItem(resultGrid);
        printStack.addSection(resultsSection);

        SectionStackSection detailsSection = new SectionStackSection("Detail");
        detailsSection.setExpanded(true);
        detailsSection.addItem(printViewer);
        printStack.addSection(detailsSection);

        final VLayout printContainer = new VLayout(10);
        printContainer.addMember(printStack);
        printContainer.setExtraSpace(15);

        nextButton = new IButton(lang.next());
        nextButton.setWidth(80);
        //        nextButton.setMargin(2);
        nextButton.setExtraSpace(8);
        withoutButton = new IButton(lang.fcontinueWithout());
        withoutButton.setAutoFit(true);

        HLayout buttons = new HLayout();
        buttons.setMembers(nextButton, withoutButton);

        layout.addMember(html1);
        layout.addMember(hLayout);
        layout.addMember(printContainer);
        layout.addMember(buttons);

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
     * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getOpen
     * ()
     */
    @Override
    public ButtonItem getFindZ39() {
        return findButtonZ39;
    }

    @Override
    public ButtonItem getFindOai() {
        return findButtonOai;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getUuid
     * ()
     */
    @Override
    public TextItem getZ39Id() {
        return searchValueZ39;
    }

    @Override
    public SelectItem getFindBy() {
        return findBy;
    }

    @Override
    public void refreshData(ListGridRecord[] data) {
        if (data == null) {
            resultGrid.setData(new ListGridRecord[] {});
            printViewer.setData(new ListGridRecord[] {});
            nextButton.setDisabled(true);
        } else {
            resultGrid.setData(data);
            printViewer.setData(new Record[] {data[0]});
            printStack.adjustForContent(true);
            resultGrid.selectRecord(0);
            nextButton.setDisabled(false);
        }
    }

    @Override
    public void showProgress(boolean show, boolean msg) {
        if (show) {
            if (mw == null) {
                mw = new ModalWindow(printStack);
            }
            mw.setLoadingIcon("loadingAnimation.gif");
            if (msg) {
                mw.show(lang.rendering(), true);
            } else {
                mw.show(true);
            }
        } else {
            mw.hide();
        }
    }

    @Override
    public IButton getNext() {
        return nextButton;
    }

    @Override
    public IButton getWithoutMetadata() {
        return withoutButton;
    }

    @Override
    public ListGrid getResults() {
        return resultGrid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TextItem getOaiId() {
        return searchValueOai;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectItem getOaiPrefix() {
        return oaiIdPrefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectItem getOaiBase() {
        return oaiBase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SelectItem getOaiUrl() {
        return oaiUrl;
    }

}