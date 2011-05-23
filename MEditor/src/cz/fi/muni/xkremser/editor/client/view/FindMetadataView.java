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

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
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

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class FindMetadataView extends ViewImpl implements FindMetadataPresenter.MyView {

	/** The layout. */
	private final VStack layout;

	private final ButtonItem findButton;

	private final IButton nextButton;

	/** The form. */
	private final DynamicForm form;

	/** The uuid field. */
	private final TextItem searchValue;

	private final SelectItem findBy;

	private final LangConstants lang;

	private final ListGrid resultGrid;

	private final DetailViewer printViewer;

	SectionStack printStack;

	// @Inject
	// public void setLang(LangConstants lang) {
	// this.lang = lang;
	// }

	/**
	 * Instantiates a new home view.
	 */
	@Inject
	public FindMetadataView(final LangConstants lang) {
		this.lang = lang;
		layout = new VStack();
		layout.setHeight100();
		layout.setPadding(15);

		findButton = new ButtonItem(lang.find());
		findButton.setAutoFit(true);
		findButton.setStartRow(false);
		findButton.setWidth(80);
		findButton.setColSpan(2);

		HTMLFlow html1 = new HTMLFlow();
		html1.setContents("<h1>" + lang.create() + "</h1>");
		html1.setExtraSpace(20);

		findBy = new SelectItem();
		findBy.setTitle(lang.findBy());
		// findBy.setType("comboBox");
		findBy.setValueMap(Constants.SYSNO, lang.fbarcode(), lang.ftitle());
		findBy.setValue(Constants.SYSNO);
		findBy.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				searchValue.setTitle((String) event.getValue());
				searchValue.redraw();
			}
		});
		searchValue = new TextItem();
		// searchValue.setShowTitle(false);
		searchValue.setTitle(Constants.SYSNO);
		searchValue.setHoverOpacity(75);
		searchValue.setHoverWidth(330);
		searchValue.setHoverStyle("interactImageHover");
		searchValue.addItemHoverHandler(new ItemHoverHandler() {
			@Override
			public void onItemHover(ItemHoverEvent event) {
				searchValue.setPrompt("<nobr>" + lang.findHint() + "</nobr>");
			}
		});

		form = new DynamicForm();
		form.setGroupTitle(lang.findMetadata());
		form.setIsGroup(true);
		form.setWidth(220);
		form.setHeight(90);
		form.setNumCols(2);
		form.setColWidths(70, "*");
		form.setPadding(5);
		form.setFields(findBy, searchValue, findButton);

		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);
		hLayout.addMember(form);
		hLayout.setExtraSpace(10);

		printStack = new SectionStack();
		printStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		printStack.setWidth(600);
		printStack.setHeight(560);

		printViewer = new DetailViewer();
		printViewer.setWidth100();
		printViewer.setMargin(15);
		printViewer.setEmptyMessage(lang.fnothing());
		printViewer.setDataSource(new Z3950ResultDS(lang));

		resultGrid = new ListGrid();
		resultGrid.setHeight(290);
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
				printViewer.setData(new Record[] { event.getRecord() });
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
		printContainer.setExtraSpace(5);

		nextButton = new IButton(lang.next());
		nextButton.setWidth(80);

		layout.addMember(html1);
		layout.addMember(hLayout);
		layout.addMember(printContainer);
		layout.addMember(nextButton);

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
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getOpen()
	 */
	@Override
	public ButtonItem getFind() {
		return findButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getUuid()
	 */
	@Override
	public TextItem getCode() {
		return searchValue;
	}

	@Override
	public SelectItem getFindBy() {
		return findBy;
	}

	@Override
	public void refreshData(ListGridRecord[] data) {
		if (data == null) {
			resultGrid.setData(new ListGridRecord[] {});
		} else {
			resultGrid.setData(data);
			printViewer.setData(new Record[] { data[0] });
			printStack.adjustForContent(true);
		}
	}

}