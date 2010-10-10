package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.Date;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.tree.events.HasFolderOpenedHandlers;

import cz.fi.muni.xkremser.editor.client.view.DigitalObjectMenuViewImpl.MyUiHandlers;

public class DigitalObjectMenuPresenter extends PresenterWidget<DigitalObjectMenuPresenter.MyView> implements MyUiHandlers {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server.";

	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
		HasValue<String> getSelected();

		void expandNode(String id);

		// void showInputQueue(DispatchAsync dispatcher);

		HasFolderOpenedHandlers getInputTree();

		HasClickHandlers getRefreshWidget();
	}

	private final DispatchAsync dispatcher;
	private boolean inputQueueShown = false;

	// private final EditorClientConfiguration config;

	@Inject
	public DigitalObjectMenuPresenter(final MyView view, final EventBus eventBus, final DispatchAsync dispatcher/*
																																																							 * ,
																																																							 * final
																																																							 * EditorClientConfiguration
																																																							 * config
																																																							 */) {
		super(eventBus, view);
		this.dispatcher = dispatcher;
		getView().setUiHandlers(this);
		// this.config = config;
		bind();
	}

	@Override
	protected void onBind() {

		Log.info("tady to chci pouzit" + new Date().toString());
		Log.info(String.valueOf(System.currentTimeMillis()));

		// getDisplay().showInputQueue(dispatcher);
	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	public boolean isInputQueueShown() {
		return inputQueueShown;
	}

	public void setInputQueueShown(boolean inputQueueShown) {
		this.inputQueueShown = inputQueueShown;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub

	}

}