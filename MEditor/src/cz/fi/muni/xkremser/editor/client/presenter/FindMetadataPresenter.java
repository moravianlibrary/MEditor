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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;

import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class FindMetadataPresenter extends Presenter<FindMetadataPresenter.MyView, FindMetadataPresenter.MyProxy> {

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {

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
		public TextItem getCode();

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
		public IButton getFind();

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
	@NameToken(NameTokens.FIND_METADATA)
	public interface MyProxy extends ProxyPlace<FindMetadataPresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	/** The place manager. */
	private final PlaceManager placeManager;

	private String code = null;

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
	public FindMetadataPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
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
		getView().getFind().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				findMetadata(Constants.SEARCH_FIELD.SYSNO, (String) getView().getCode().getValue());
				// placeManager.revealRelativePlace(new
				// PlaceRequest(NameTokens.CREATE).with(ServerConstants.URL_PARAM_UUID,
				// getView().getCode()));
			}
		});
		getView().getCode().setValue(code);
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

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		code = request.getParameter(Constants.URL_PARAM_CODE, null);
		getView().getCode().setValue(code);
	}

	private void findMetadata(Constants.SEARCH_FIELD field, String code) {
		dispatcher.execute(new FindMetadataAction(field, code), new DispatchCallback<FindMetadataResult>() {
			@Override
			public void callback(FindMetadataResult result) {
				SC.say(result.getOutput().toString());
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				// getView().refreshKramerius(false, null);
			}
		});
	}
}