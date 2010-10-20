package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.List;

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
import com.smartgwt.client.data.Record;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.PageRecord;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

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

		void setData(Record[] data);

		void setWidgets(boolean tileGridVisible, DispatchAsync dispatcher);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.MODIFY)
	public interface MyProxy extends ProxyPlace<ModifyPresenter> {

	}

	private final DispatchAsync dispatcher;
	private final DigitalObjectMenuPresenter leftPresenter;
	private String uuid;
	private String previousUuid;
	private boolean forcedRefresh;

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

		// test
		// page cc25c992-c94c-11df-84b1-001b63bd97ba
		// monograph 0eaa6730-9068-11dd-97de-000d606f5dc6
		// internal part 1118b1bf-c94d-11df-84b1-001b63bd97ba
		uuid = "1118b1bf-c94d-11df-84b1-001b63bd97ba";

		if (uuid != null && (forcedRefresh || (uuid != previousUuid))) {
			dispatcher.execute(new GetDigitalObjectDetailAction(uuid), new DispatchCallback<GetDigitalObjectDetailResult>() {

				@Override
				public void callback(GetDigitalObjectDetailResult result) {
					InternalPartDetail pageDetail = (InternalPartDetail) result.getDetail();
					Record[] data = new Record[pageDetail.getPages().size()];

					List<PageDetail> pages = pageDetail.getPages();
					for (int i = 0, total = pages.size(); i < total; i++) {
						data[i] = new PageRecord(pages.get(i).getDc().getTitle(), pages.get(i).getDc().getIdentifier().get(0), pages.get(i).getDc().getIdentifier().get(0));
					}
					getView().setWidgets(true, dispatcher);
					getView().setData(data);
				}
			});
		}
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
		previousUuid = uuid;
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

}