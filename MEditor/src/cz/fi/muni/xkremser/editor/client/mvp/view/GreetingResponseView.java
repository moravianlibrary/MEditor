package cz.fi.muni.xkremser.editor.client.mvp.view;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import cz.fi.muni.xkremser.editor.client.mvp.presenter.GreetingResponsePresenter;

public class GreetingResponseView extends DialogBox implements GreetingResponsePresenter.Display {
	private final Label textToServerLabel;
	private final HTML serverResponseLabel;
	private final Button closeButton;

	public GreetingResponseView() {
		setText("Remote Procedure Call");
		setAnimationEnabled(true);

		closeButton = new Button("Close");

		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");

		textToServerLabel = new Label();
		serverResponseLabel = new HTML();

		final VerticalPanel dialogVPanel = new VerticalPanel();

		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);

		setWidget(dialogVPanel);
		this.center();
	}

	@Override
	public HasText getTextToServer() {
		return textToServerLabel;
	}

	@Override
	public HasHTML getServerResponse() {
		return serverResponseLabel;
	}

	@Override
	public HasClickHandlers getClose() {
		return closeButton;
	}

	@Override
	public DialogBox getDialogBox() {
		return this;
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

}