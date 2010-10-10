package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.events.HasFolderOpenedHandlers;

import cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavRecentlyTree;

public class DigitalObjectMenuViewImpl extends ViewWithUiHandlers<DigitalObjectMenuViewImpl.MyUiHandlers> implements DigitalObjectMenuPresenter.MyView {

	public interface MyUiHandlers extends UiHandlers {
		void onRefresh();
	}

	private SideNavInputTree inputTree;
	private final SectionStack sectionStack;
	private final SectionStackSection sectionRecentlyModified;
	private ImgButton refreshButton;
	private final VLayout layout;

	// private TreeNode ctxNode;
	// private final TreeEditor treeEditor;
	// private Menu menu;

	public DigitalObjectMenuViewImpl() {
		layout = new VLayout();

		layout.setHeight100();
		layout.setWidth(285);
		layout.setShowResizeBar(true);

		SideNavRecentlyTree sideNavTree = new SideNavRecentlyTree();

		DynamicForm form = new DynamicForm();
		form.setHeight(1);
		form.setWidth(75);
		form.setNumCols(1);

		SelectItem selectItem = new SelectItem();
		selectItem.setWidth(120);
		selectItem.setShowTitle(false);
		selectItem.setValueMap("Development", "Staging", "Production");
		selectItem.setDefaultValue("Development");

		form.setFields(selectItem);

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

	// @Override
	// public void showInputQueue(DispatchAsync dispatcher) {
	// SectionStackSection section1 = new SectionStackSection();
	// section1.setTitle("Input queue");
	// section1.setItems(new SideNavInputTree(dispatcher));
	// refreshButton = new ImgButton();
	// refreshButton.setSrc("[SKIN]actions/refresh.png");
	// refreshButton.setSize(16);
	// refreshButton.setShowFocused(false);
	// refreshButton.setShowRollOver(false);
	// refreshButton.setShowDown(false);
	//
	// section1.setControls(refreshButton);
	// section1.setResizeable(true);
	// section1.setExpanded(true);
	// sectionStack.addSection(section1, 0);
	// // inputTree.setHeight("600");
	// }

	@Override
	public HasFolderOpenedHandlers getInputTree() {
		return inputTree;
	}

	@Override
	public void expandNode(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public HasClickHandlers getRefreshWidget() {
		return refreshButton;
	}

}