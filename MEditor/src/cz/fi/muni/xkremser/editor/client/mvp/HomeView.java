package cz.fi.muni.xkremser.editor.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GreetingView extends Composite implements GreetingPresenter.Display {
	private final TextBox name;
	private final Button sendButton;

	public GreetingView() {
		final FlowPanel panel = new FlowPanel();

		initWidget(panel);

		name = new TextBox();
		panel.add(name);

		sendButton = new Button("Go");
		panel.add(sendButton);
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(name);
		RootPanel.get("sendButtonContainer").add(sendButton);
		
		reset();
	}

	public HasValue<String> getName() {
		return name;
	}

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
	public Widget asWidget() {
		return this;
	}

	@Override
	public void startProcessing() {
	}

	@Override
	public void stopProcessing() {
	}
}