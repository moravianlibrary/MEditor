/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class HomeView extends ViewImpl implements HomePresenter.MyView {
	
	/** The name. */
	private final TextBox name;
	
	/** The send button. */
	private final Button sendButton;
	
	/** The panel. */
	private final FlowPanel panel;

	/**
	 * Instantiates a new home view.
	 */
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

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getName()
	 */
	@Override
	public HasValue<String> getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getSend()
	 */
	@Override
	public HasClickHandlers getSend() {
		return sendButton;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		// Focus the cursor on the name field when the app loads
		name.setFocus(true);
		name.selectAll();
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 *
	 * @return the widget
	 */
	@Override
	public Widget asWidget() {
		return panel;
	}
}