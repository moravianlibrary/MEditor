package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;

public class HomeView extends ViewImpl implements HomePresenter.MyView {
	private final TextBox name;
	private final Button sendButton;
	private final FlowPanel panel;

	public HomeView() {
		panel = new FlowPanel();
		name = new TextBox();
		panel.add(name);

		sendButton = new Button("Go");
		panel.add(sendButton);

		// // Add the nameField and sendButton to the RootPanel
		// // Use RootPanel.get() to get the entire body element
		// RootPanel.get("nameFieldContainer").add(name);
		// RootPanel.get("sendButtonContainer").add(sendButton);
		reset();
	}

	@Override
	public HasValue<String> getName() {
		return name;
	}

	@Override
	public HasClickHandlers getSend() {
		return sendButton;
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
		return panel;
	}
}