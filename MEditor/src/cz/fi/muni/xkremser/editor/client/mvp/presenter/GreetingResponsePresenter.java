package cz.fi.muni.xkremser.editor.client.mvp.presenter;

import java.util.List;

import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;

import cz.fi.muni.xkremser.editor.shared.event.GreetingSentEvent;
import cz.fi.muni.xkremser.editor.shared.event.GreetingSentEventHandler;

public class GreetingResponsePresenter extends WidgetPresenter<GreetingResponsePresenter.Display> {
	public interface Display extends WidgetDisplay {
		HasText getTextToServer();

		HasHTML getServerResponse();

		HasClickHandlers getClose();

		DialogBox getDialogBox();
	}

	@Inject
	public GreetingResponsePresenter(final Display display, final EventBus eventBus) {
		super(display, eventBus);

		bind();
	}

	@Override
	protected void onBind() {
		// Add a handler to close the DialogBox
		display.getClose().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				display.getDialogBox().hide();

				// Not sure of a nice place to put these!
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});

		eventBus.addHandler(GreetingSentEvent.TYPE, new GreetingSentEventHandler() {

			@Override
			public void onGreetingSent(final GreetingSentEvent event) {
				Log.info("Handling GreetingSent event");

				display.getTextToServer().setText(event.getName());
				display.getServerResponse().setHTML(event.getMessage());
				display.getDialogBox().show();
			}
		});
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	public void refreshDisplay() {
		// This is called when the presenter should pull the latest data
		// from the server, etc. In this case, there is nothing to do.
	}

	@Override
	public void revealDisplay() {
		// Nothing to do. This is more useful in UI which may be buried
		// in a tab bar, tree, etc.
	}

	@Override
	protected void onRevealDisplay() {
		// TODO Auto-generated method stub

	}

	private List<String> getTopLevel() {
		Log.info("Calling doSend");
		return null;
	}

	private List<String> getLevel(String pathPrefix) {
		return null;
	}
}