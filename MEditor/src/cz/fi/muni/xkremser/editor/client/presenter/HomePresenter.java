/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent;
import cz.fi.muni.xkremser.editor.shared.event.ConfigReceivedEvent.ConfigReceivedHandler;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailability;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueueResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

	// @Inject
	/** The config. */
	private final EditorClientConfiguration config;

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

		public HasClickHandlers getCheckAvailability();
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

	private volatile boolean configReceived = false;

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
	// public HomePresenter(final MyView display, final EventBus eventBus, final
	// DispatchAsync dispatcher, final DigitalObjectMenuPresenter treePresenter) {
	public HomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final EditorClientConfiguration config, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.config = config;
		this.dispatcher = dispatcher;
	}

	/**
	 * Try to send the greeting message.
	 */
	private void doSend() {
		Log.info("Calling doSend");

		// delete
		dispatcher.execute(new ScanInputQueueAction("", false), new DispatchCallback<ScanInputQueueResult>() {
			@Override
			public void callbackError(final Throwable cause) {
				Log.error("Handle Failure:", cause);
			}

			@Override
			public void callback(final ScanInputQueueResult result) {
				// take the result from the server and notify client
				// interested

			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(ConfigReceivedEvent.getType(), new ConfigReceivedHandler() {
			@Override
			public void onConfigReceived(ConfigReceivedEvent event) {
				configReceived = true;
				if (config == null) {
					SC.warn("Could not find the system config file.");
				}
				getView().setURLs(config == null || config.getFedoraHost() == null ? "#" : config.getFedoraHost(),
						config == null || config.getKrameriusHost() == null ? "#" : config.getKrameriusHost());

				checkAvailability();
			}
		});
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
				timer.schedule(150);
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
		String fedoraURL = config.getFedoraHost();
		if (fedoraURL == null || "".equals(fedoraURL)) {
			SC.warn("Please set " + EditorClientConfiguration.Constants.FEDORA_HOST + " in system configuration.");
		}
		String krameriusURL = config.getKrameriusHost();
		if (krameriusURL == null || "".equals(krameriusURL)) {
			SC.warn("Please set " + EditorClientConfiguration.Constants.KRAMERIUS_HOST + " in system configuration.");
		}

		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.FEDORA_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				getView().refreshFedora(result.isAvailability(), result.getUrl());
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshFedora(false, null);
			}
		});

		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.KRAMERIUS_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				getView().refreshKramerius(result.isAvailability(), result.getUrl());
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshKramerius(false, null);
			}
		});

	}
}