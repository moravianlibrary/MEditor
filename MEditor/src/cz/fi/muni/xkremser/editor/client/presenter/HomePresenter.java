/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
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

import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
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
		 * @param fedoraRunning
		 *          the fedora running
		 * @param url
		 *          the url
		 * @return the name
		 */
		public void refreshFedora(boolean fedoraRunning, String url);

		/**
		 * Refresh kramerius.
		 * 
		 * @param krameriusRunning
		 *          the kramerius running
		 * @param url
		 *          the url
		 */
		public void refreshKramerius(boolean krameriusRunning, String url);

		/**
		 * Sets the ur ls.
		 * 
		 * @param fedoraUrl
		 *          the fedora url
		 * @param krameriusUrl
		 *          the kramerius url
		 */
		public void setURLs(String fedoraUrl, String krameriusUrl);

		/**
		 * Sets the loading.
		 */
		public void setLoading();

		/**
		 * Gets the uuid.
		 * 
		 * @return the uuid
		 */
		public String getUuid();

		/**
		 * Gets the check availability.
		 * 
		 * @return the check availability
		 */
		public HasClickHandlers getCheckAvailability();

		/**
		 * Gets the uuid item.
		 * 
		 * @return the uuid item
		 */
		public HasChangedHandlers getUuidItem();

		/**
		 * Gets the open.
		 * 
		 * @return the open
		 */
		public IButton getOpen();

		/**
		 * Gets the form.
		 * 
		 * @return the form
		 */
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

	/** The place manager. */
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
	 * @param dispatcher
	 *          the dispatcher
	 * @param placeManager
	 *          the place manager
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

	/**
	 * Check availability.
	 */
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