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

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.VisibilityMode;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.RecentlyTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavRecentlyGrid;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class DigitalObjectMenuView.
 */
public class DigitalObjectMenuView extends ViewWithUiHandlers<DigitalObjectMenuView.MyUiHandlers> implements DigitalObjectMenuPresenter.MyView {

	/** The Constant SECTION_RELATED_ID. */
	private static final String SECTION_RELATED_ID = "related";

	private final LangConstants lang;

	/**
	 * The Interface MyUiHandlers.
	 */
	public interface MyUiHandlers extends UiHandlers {

		/**
		 * On refresh.
		 */
		void onRefresh();

		/**
		 * On show input queue.
		 */
		void onShowInputQueue();

		/**
		 * On add digital object.
		 * 
		 * @param item
		 *          the item
		 * @param related
		 *          the related
		 */
		void onAddDigitalObject(final RecentlyModifiedItem item, final List<? extends List<String>> related);

		/**
		 * Reveal modified item.
		 * 
		 * @param uuid
		 *          the uuid
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
	private final SideNavRecentlyGrid sideNavGrid;

	/** The section stack. */
	private final SectionStack sectionStack;

	/** The section recently modified. */
	private final SectionStackSection sectionRecentlyModified;

	/** The section related. */
	private final SectionStackSection sectionRelated;
	/** The section related. */
	private final ListGrid relatedGrid;

	/** The refresh button. */
	private ImgButton refreshButton;

	/** The layout. */
	private final VLayout layout;

	/**
	 * Instantiates a new digital object menu view.
	 */
	@Inject
	public DigitalObjectMenuView(LangConstants lang) {
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

		sideNavGrid = new SideNavRecentlyGrid();

		final DynamicForm form = new DynamicForm();
		form.setHeight(1);
		form.setWidth(60);
		form.setNumCols(1);

		final SelectItem selectItem = new SelectItem();
		selectItem.setWidth(60);
		selectItem.setShowTitle(false);
		selectItem.setValueMap(lang.me(), lang.all());
		selectItem.setDefaultValue(lang.me());
		selectItem.setHoverOpacity(75);
		selectItem.setHoverStyle("interactImageHover");
		selectItem.addItemHoverHandler(new ItemHoverHandler() {
			@Override
			public void onItemHover(ItemHoverEvent event) {
				selectItem.setPrompt(DigitalObjectMenuView.this.lang.showModifiedHint() + selectItem.getValue());

			}
		});
		selectItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Criteria criteria = new Criteria();
				boolean all = DigitalObjectMenuView.this.lang.all().equals(event.getValue());
				criteria.addCriteria(Constants.ATTR_ALL, all);
				sideNavGrid.getDataSource().fetchData(criteria, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						sideNavGrid.setData(response.getData());
					}
				});
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
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
	 * MyView#getSelected()
	 */
	@Override
	public HasValue<String> getSelected() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
	 * MyView#setDS(com.gwtplatform.dispatch.client.DispatchAsync)
	 */
	@Override
	public void setDS(DispatchAsync dispatcher) {
		this.sideNavGrid.setDataSource(new RecentlyTreeGwtRPCDS(dispatcher, lang));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
	 * MyView#showInputQueue(com.gwtplatform.dispatch.client.DispatchAsync)
	 */
	@Override
	public void showInputQueue(DispatchAsync dispatcher, PlaceManager placeManager) {
		SectionStackSection section1 = new SectionStackSection();
		section1.setTitle("Input queue");
		inputTree = new SideNavInputTree(dispatcher, placeManager, lang);
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
		// inputTree.setHeight("600");
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
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
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
	 * MyView#getInputTree()
	 */
	@Override
	public Refreshable getInputTree() {
		return inputTree;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
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
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
	 * MyView#getRelatedGrid()
	 */
	@Override
	public ListGrid getRelatedGrid() {
		return relatedGrid;
	}
}