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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
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

	/** The check button. */
	private final ButtonItem findButton;

	/** The form. */
	private final DynamicForm form;

	/** The uuid field. */
	private final TextItem searchValue;

	private final SelectItem findBy;

	private final LangConstants lang;

	private final ListGrid resultGrid;

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

		SectionStack printStack = new SectionStack();
		printStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		printStack.setWidth(600);
		printStack.setHeight(450);

		final DetailViewer printViewer = new DetailViewer();
		printViewer.setWidth100();
		printViewer.setMargin(15);
		printViewer.setEmptyMessage(lang.fnothing());
		printViewer.setDataSource(new Z3950ResultDS(lang));

		resultGrid = new ListGrid();
		resultGrid.setHeight(300);
		resultGrid.setShowAllRecords(true);
		resultGrid.setAutoFetchData(false);
		ListGridField dcTitle = new ListGridField(DublinCoreConstants.DC_TITLE, lang.dcTitle(), 70);
		ListGridField dcPublisher = new ListGridField(DublinCoreConstants.DC_PUBLISHER, lang.dcPublisher());
		ListGridField dcDate = new ListGridField(DublinCoreConstants.DC_DATE, lang.dcDate());
		ListGridField dcIdentifier = new ListGridField(DublinCoreConstants.DC_IDENTIFIER, lang.dcIdentifier());
		// resultGrid.setFields(dcTitle, dcPublisher, dcDate, dcIdentifier);
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

		HLayout printButtonLayout = new HLayout(5);

		printContainer.addMember(printStack);
		printContainer.addMember(printButtonLayout);

		// // The filter is just to limit the number of records in the ListGrid - we
		// // don't want to print them all
		// printGrid.filterData(new Criteria("CountryName", "land"), new
		// DSCallback() {
		// @Override
		// public void execute(DSResponse response, Object rawData, DSRequest
		// request) {
		// printGrid.selectRecord(0);
		// printViewer.setData(new Record[] { printGrid.getSelectedRecord() });
		// }
		// });
		// printContainer.draw();
		// hLayout.addMember(printContainer);
		layout.addMember(html1);
		layout.addMember(hLayout);
		layout.addMember(printContainer);

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
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getForm()
	 */
	@Override
	public DynamicForm getForm() {
		return form;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getUuidItem
	 * ()
	 */
	@Override
	public HasChangedHandlers getUuidItem() {
		return searchValue;
	}

	@Override
	public void setURLs(String fedoraUrl, String krameriusUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoading() {
		// TODO Auto-generated method stub

	}

	@Override
	public SelectItem getFindBy() {
		return findBy;
	}

	@Override
	public void refreshData(ListGridRecord[] data) {
		System.out.println(data[0].getAttribute(DublinCoreConstants.DC_TITLE));
		System.out.println(data[0].getAttribute(DublinCoreConstants.DC_TYPE));
		ListGridRecord[] data1 = new ListGridRecord[1];
		data1[0] = new ListGridRecord();
		data1[0].setAttribute(DublinCoreConstants.DC_TITLE, new String[] { "jmeno1", "jmeno2" });
		data1[0].setAttribute(DublinCoreConstants.DC_TYPE, "typ");
		data1[0].setAttribute(DublinCoreConstants.DC_PUBLISHER, "pub");
		data1[0].setAttribute(DublinCoreConstants.DC_DATE, "date");
		data1[0].setAttribute(DublinCoreConstants.DC_IDENTIFIER, new String[] { "id1", "id2" });
		// System.out.println(data[0].g);

		// resultGrid.setData(data1);
		resultGrid.setData(data);
	}

}