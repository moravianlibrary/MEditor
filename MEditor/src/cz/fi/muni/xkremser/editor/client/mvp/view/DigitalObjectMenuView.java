package cz.fi.muni.xkremser.editor.client.mvp.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.events.HasFolderOpenedHandlers;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.mvp.view.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.mvp.view.tree.SideNavRecentlyTree;
import cz.fi.muni.xkremser.editor.shared.rpc.ScanInputQueueResult;

public class DigitalObjectMenuView extends VLayout implements DigitalObjectMenuPresenter.Display {

	private SideNavInputTree inputTree;

	// private TreeNode ctxNode;
	// private final TreeEditor treeEditor;
	// private Menu menu;

	public DigitalObjectMenuView() {
		super();
		setHeight100();
		setWidth(285);
		setShowResizeBar(true);

		SideNavRecentlyTree sideNavTree2 = new SideNavRecentlyTree();
		addMember(new Label("Recently modified") {
			{
				setHeight(15);
			}
		});
		addMember(sideNavTree2);
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
	public void expandNode(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showInputQueue() {
		Label l = new Label("Input queue");
		l.setHeight(15);
		addMember(l, 0);
		inputTree = new SideNavInputTree();
		inputTree.setHeight("600");
		addMember(inputTree, 1);

	}

	@Override
	public void refreshInputQueue(ScanInputQueueResult result) {
		inputTree.refresh(result);
	}

	@Override
	public HasFolderOpenedHandlers getInputTree() {
		return inputTree;
	}

	@Override
	public void setDataSourceToInputQueue(DataSource source) {
		inputTree.setDataSource(source);

	}

}