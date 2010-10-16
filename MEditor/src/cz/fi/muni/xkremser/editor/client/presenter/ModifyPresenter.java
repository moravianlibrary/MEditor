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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;

import cz.fi.muni.xkremser.editor.client.Constants;
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

	private final DispatchAsync dispatcher;
	private final DigitalObjectMenuPresenter leftPresenter;
	private String uuid;

	@Inject
	public ModifyPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;

	}

	@Override
	protected void onBind() {
		super.onBind();

		if (uuid != null) {
			// test
			// page cc25c992-c94c-11df-84b1-001b63bd97ba
			// monograph 0eaa6730-9068-11dd-97de-000d606f5dc6
			// internal part 1118b1bf-c94d-11df-84b1-001b63bd97ba

			// dispatcher.execute(new
			// GetDigitalObjectDetailAction("cc25c992-c94c-11df-84b1-001b63bd97ba"),
			// new
			// DispatchCallback<GetDigitalObjectDetailResult>() {
			// dispatcher.execute(new
			// GetDigitalObjectDetailAction("0eaa6730-9068-11dd-97de-000d606f5dc6"),
			// new
			// DispatchCallback<GetDigitalObjectDetailResult>() {
			// dispatcher.execute(new
			// GetDigitalObjectDetailAction("1118b1bf-c94d-11df-84b1-001b63bd97ba"),
			// new
			// DispatchCallback<GetDigitalObjectDetailResult>() {

			// test
		}

	};

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		uuid = request.getParameter(Constants.URL_PARAM_UUID, null);

		// SC.say(uuid);
		// if(anything wrong)
		// > {
		// > getProxy().manualRevealFailed();
		// > placeManager.revealErrorPlace( placeRequest.getNameToken() );
		// > return;
		// > }
		// > getProxy().manualReveal(this);
	}

	@Override
	protected void onUnbind() {
		super.onUnbind();
		// Add unbind functionality here for more complex presenters.
	}

	@Override
	protected void onReset() {
		super.onReset();

		SC.say(uuid);
		// if (uuid != null) {
		// dispatcher.execute(new GetDigitalObjectDetailAction(uuid), new
		// DispatchCallback<GetDigitalObjectDetailResult>() {
		//
		// @Override
		// public void callback(GetDigitalObjectDetailResult result) {
		// System.out.println(result.getDetail());
		// // System.out.println(result.getDetail().getClass());
		// // System.out.println(result.getClass());
		// InternalPartDetail inter = (InternalPartDetail) result.getDetail();
		// System.out.println(inter.getPages());
		//
		// }
		// });
		// }
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);

	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

}