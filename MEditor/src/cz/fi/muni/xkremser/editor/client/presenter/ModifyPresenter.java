/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
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
import cz.fi.muni.xkremser.editor.shared.event.DigitalObjectOpenedEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetDigitalObjectDetailResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;

// TODO: Auto-generated Javadoc
/**
 * The Class ModifyPresenter.
 */
public class ModifyPresenter extends Presenter<ModifyPresenter.MyView, ModifyPresenter.MyProxy> {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while " + "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public HasValue<String> getName();

		/**
		 * Gets the send.
		 *
		 * @return the send
		 */
		public HasClickHandlers getSend();

		/**
		 * Adds the digital object.
		 *
		 * @param tileGridVisible the tile grid visible
		 * @param data the data
		 * @param dispatcher the dispatcher
		 */
		void addDigitalObject(boolean tileGridVisible, Record[] data, DispatchAsync dispatcher);
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.MODIFY)
	public interface MyProxy extends ProxyPlace<ModifyPresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;
	
	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;
	
	/** The uuid. */
	private String uuid;
	
	/** The previous uuid. */
	private String previousUuid;
	
	/** The forced refresh. */
	private boolean forcedRefresh;

	/**
	 * Instantiates a new modify presenter.
	 *
	 * @param eventBus the event bus
	 * @param view the view
	 * @param proxy the proxy
	 * @param leftPresenter the left presenter
	 * @param dispatcher the dispatcher
	 */
	@Inject
	public ModifyPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;

	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();

	};

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform.mvp.client.proxy.PlaceRequest)
	 */
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

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onUnbind()
	 */
	@Override
	protected void onUnbind() {
		super.onUnbind();
		// Add unbind functionality here for more complex presenters.
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		super.onReset();

		// test
		// page cc25c992-c94c-11df-84b1-001b63bd97ba
		// monograph 0eaa6730-9068-11dd-97de-000d606f5dc6
		// internal part 1118b1bf-c94d-11df-84b1-001b63bd97ba
		// uuid = "1118b1bf-c94d-11df-84b1-001b63bd97ba";

		if (uuid != null && (forcedRefresh || (uuid != previousUuid))) {
			dispatcher.execute(new GetDigitalObjectDetailAction(uuid), new DispatchCallback<GetDigitalObjectDetailResult>() {
				@Override
				public void callback(GetDigitalObjectDetailResult result) {
					InternalPartDetail detail = (InternalPartDetail) result.getDetail();
					Record[] data = new Record[detail.getPages().size()];

					List<PageDetail> pages = detail.getPages();
					for (int i = 0, total = pages.size(); i < total; i++) {
						data[i] = new PageRecord(pages.get(i).getDc().getTitle(), pages.get(i).getDc().getIdentifier().get(0), pages.get(i).getDc().getIdentifier().get(0));
					}
					getView().addDigitalObject(true, data, dispatcher);
					DigitalObjectOpenedEvent.fire(ModifyPresenter.this, true, new RecentlyModifiedItem(uuid, detail.getDc().getTitle(), "", detail.getModel()));
				}

				@Override
				public void callbackError(Throwable t) {
					super.callbackError(t);
				}
			});
		}

		// if (uuid != null && (forcedRefresh || (uuid != previousUuid))) {
		// getView().addDigitalObject(false, null, dispatcher);
		// }
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
		previousUuid = uuid;
	}

	/* (non-Javadoc)
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

}