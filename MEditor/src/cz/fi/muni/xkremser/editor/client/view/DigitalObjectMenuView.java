package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.RecentlyTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavRecentlyTree;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

public class DigitalObjectMenuView extends ViewWithUiHandlers<DigitalObjectMenuView.MyUiHandlers> implements DigitalObjectMenuPresenter.MyView {

	public interface MyUiHandlers extends UiHandlers {
		void onRefresh();

		void onShowInputQueue();

		void onAddDigitalObject(RecentlyModifiedItem item);

		void revealModifiedItem(String uuid);

	}

	public interface Refreshable {
		void refreshTree();
	}

	private SideNavInputTree inputTree;
	private final SideNavRecentlyTree sideNavTree;
	private final SectionStack sectionStack;
	private final SectionStackSection sectionRecentlyModified;
	private ImgButton refreshButton;
	private final VLayout layout;

	public DigitalObjectMenuView() {
		layout = new VLayout();

		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);

		sideNavTree = new SideNavRecentlyTree();

		final DynamicForm form = new DynamicForm();
		form.setHeight(1);
		form.setWidth(60);
		form.setNumCols(1);
		form.setCanHover(true);
		form.addHoverHandler(new HoverHandler() {
			@Override
			public void onHover(HoverEvent event) {
				form.setPrompt("Show only objects modified by...");
			}
		});

		final SelectItem selectItem = new SelectItem();
		selectItem.setWidth(60);
		selectItem.setShowTitle(false);
		selectItem.setValueMap("me", "all");
		selectItem.setDefaultValue("me");
		selectItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				Criteria criteria = new Criteria();
				boolean all = "all".equals(event.getValue());
				criteria.addCriteria(Constants.ATTR_ALL, all);
				// sideNavTree.fetchData(criteria);
				sideNavTree.filterData(criteria);
			}
		});

		form.setFields(selectItem);
		form.setTitle("by:");

		sectionRecentlyModified = new SectionStackSection();
		sectionRecentlyModified.setTitle("Recently modified");
		sectionRecentlyModified.setResizeable(true);
		sectionRecentlyModified.setItems(sideNavTree);
		sectionRecentlyModified.setControls(form);
		sectionRecentlyModified.setExpanded(true);

		sectionStack = new SectionStack();
		sectionStack.addSection(sectionRecentlyModified);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setAnimateSections(true);
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		// sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.setOverflow(Overflow.HIDDEN);
		layout.addMember(sectionStack);
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}

	// @UiHandler("saveButton")
	// void onSaveButtonClicked(ClickEvent event) {
	// if (getUiHandlers() != null) {
	// getUiHandlers().onRefresh();
	// }
	// }

	@Override
	public HasValue<String> getSelected() {
		return null;
	}

	@Override
	public void setDS(DispatchAsync dispatcher) {
		this.sideNavTree.setDataSource(new RecentlyTreeGwtRPCDS(dispatcher));
	}

	@Override
	public void showInputQueue(DispatchAsync dispatcher) {
		SectionStackSection section1 = new SectionStackSection();
		section1.setTitle("Input queue");
		inputTree = new SideNavInputTree(dispatcher);
		section1.setItems(inputTree);
		refreshButton = new ImgButton();
		refreshButton.setSrc("[SKIN]headerIcons/refresh.png");
		refreshButton.setSize(16);
		refreshButton.setShowRollOver(true);
		refreshButton.setCanHover(true);
		refreshButton.setShowDownIcon(false);
		refreshButton.setShowDown(false);
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

	@Override
	public void expandNode(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public HasClickHandlers getRefreshWidget() {
		return refreshButton;
	}

	@Override
	public Refreshable getInputTree() {
		return inputTree;
	}

	@Override
	public ListGrid getRecentlyModifiedTree() {
		return sideNavTree;
	}

}