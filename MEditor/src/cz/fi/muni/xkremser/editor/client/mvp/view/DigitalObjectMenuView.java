package cz.fi.muni.xkremser.editor.client.mvp.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.DigitalObjectMenuPresenter;

public class DigitalObjectMenuView extends Composite implements DigitalObjectMenuPresenter.Display {
	private final TextBox name;
	private final Button sendButton;

	// private final TreePanel treePanel;
	// private TreeNode ctxNode;
	// private final TreeEditor treeEditor;
	// private Menu menu;

	public DigitalObjectMenuView() {
		final FlowPanel panel = new FlowPanel();
		Tree tree = new Tree();
		// initWidget(tree);
		TreeItem outerRoot = new TreeItem("Item 1");
		outerRoot.addItem("Item 1-1");
		outerRoot.addItem("Item 1-2");
		outerRoot.addItem("Item 1-3");
		outerRoot.addItem(new CheckBox("Item 1-4"));
		tree.addItem(outerRoot);

		TreeItem innerRoot = new TreeItem("Item 1-5");
		innerRoot.addItem("Item 1-5-1");
		innerRoot.addItem("Item 1-5-2");
		innerRoot.addItem("Item 1-5-3");
		innerRoot.addItem("Item 1-5-4");
		innerRoot.addItem(new CheckBox("Item 1-5-5"));

		outerRoot.addItem(innerRoot);

		initWidget(panel);
		name = new TextBox();
		panel.add(name);

		sendButton = new Button("ahoj");
		panel.add(sendButton);
		panel.add(tree);

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("tree").add(tree);

		reset();
	}

	public void reset() {
		// Focus the cursor on the name field when the app loads
		name.setFocus(true);
		name.selectAll();
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
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