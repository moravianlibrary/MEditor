/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailability;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public void refreshFedora(boolean fedoraRunning, String url);

		public void refreshKramerius(boolean krameriusRunning, String url);

		public void setURLs(String fedoraUrl, String krameriusUrl);

		public void setLoading();

		public String getUuid();

		public HasClickHandlers getCheckAvailability();

		public HasChangedHandlers getUuidItem();

		public IButton getOpen();

		public DynamicForm getForm();
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.HOME)
	public interface MyProxy extends ProxyPlace<HomePresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	private final PlaceManager placeManager;

	/**
	 * Instantiates a new home presenter.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param view
	 *          the view
	 * @param proxy
	 *          the proxy
	 * @param leftPresenter
	 *          the left presenter
	 */
	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher, final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		bind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();
		checkAvailability();
		getView().getCheckAvailability().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getView().setLoading();
				Timer timer = new Timer() {
					@Override
					public void run() {
						checkAvailability();
					}
				};
				timer.schedule(100);
			}
		});
		getView().getOpen().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getView().getForm().validate())
					placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID, getView().getUuid()));
			}
		});

		getView().getUuidItem().addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String text = (String) event.getValue();
				if (text != null && !"".equals(text)) {
					getView().getOpen().setDisabled(false);
				} else {
					getView().getOpen().setDisabled(true);
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

	private void checkAvailability() {
		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.KRAMERIUS_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				String krameriusURL = result.getUrl();
				getView().refreshKramerius(result.isAvailability(), krameriusURL);
				if (krameriusURL == null || "".equals(krameriusURL)) {
					SC.warn("Please set " + EditorClientConfiguration.Constants.KRAMERIUS_HOST + " in system configuration.");
				}
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshKramerius(false, null);
			}
		});

		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.FEDORA_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				String fedoraURL = result.getUrl();
				getView().refreshFedora(result.isAvailability(), fedoraURL);
				if (fedoraURL == null || "".equals(fedoraURL)) {
					SC.warn("Please set " + EditorClientConfiguration.Constants.FEDORA_HOST + " in system configuration.");
				}
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshFedora(false, null);
			}
		});
	}
}