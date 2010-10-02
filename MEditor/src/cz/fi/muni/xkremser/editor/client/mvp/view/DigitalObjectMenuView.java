package cz.fi.muni.xkremser.editor.client.mvp.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.tree.SideNavRecentlyTree;

public class DigitalObjectMenuView extends Composite implements DigitalObjectMenuPresenter.Display {

	// private final TreePanel treePanel;
	// private TreeNode ctxNode;
	// private final TreeEditor treeEditor;
	// private Menu menu;

	public DigitalObjectMenuView() {
		VLayout sideNavLayout = new VLayout();
		sideNavLayout.setHeight100();
		sideNavLayout.setWidth(285);
		sideNavLayout.setShowResizeBar(true);

		SideNavRecentlyTree sideNavTree2 = new SideNavRecentlyTree();
		SideNavInputTree sideNavTree1 = new SideNavInputTree();
		sideNavTree1.setHeight("600");
		sideNavLayout.addMember(sideNavTree1);
		sideNavLayout.addMember(sideNavTree2);

		initWidget(sideNavLayout);
		reset();
	}

	public void reset() {
		// Focus the cursor on the name field when the app loads
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasValue<String> getSelected() {
		return null;
	}

	@Override
	public HasClickHandlers getMenu() {
		return null;
	}
}