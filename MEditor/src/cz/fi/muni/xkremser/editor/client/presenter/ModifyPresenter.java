package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import cz.fi.muni.xkremser.editor.client.NameTokens;

public class ModifyPresenter extends Presenter<ModifyPresenter.MyView, ModifyPresenter.MyProxy> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public interface MyView extends View {
		public HasValue<String> getName();

		public HasClickHandlers getSend();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.MODIFY)
	public interface MyProxy extends ProxyPlace<ModifyPresenter> {

	}

	private DispatchAsync dispatcher;
	private final DigitalObjectMenuPresenter leftPresenter;

	@Inject
	public ModifyPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;

	}

	@Override
	protected void onUnbind() {
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	protected void onReset() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

}