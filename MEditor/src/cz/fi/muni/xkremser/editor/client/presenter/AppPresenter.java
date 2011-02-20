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

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.HTMLFlow;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.view.AppView.MyUiHandlers;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLoggedUserAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetLoggedUserResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AppPresenter.
 */
public class AppPresenter extends Presenter<AppPresenter.MyView, AppPresenter.MyProxy> implements MyUiHandlers {

	private LangConstants lang;

	@Inject
	public void setLang(LangConstants lang) {
		this.lang = lang;
	}

	/**
	 * Use this in leaf presenters, inside their {@link #revealInParent} method.
	 */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

	// @ContentSlot
	// public static final Type<RevealContentHandler<?>> TYPE_SetTopContent = new
	// Type<RevealContentHandler<?>>();

	/** The Constant TYPE_SetLeftContent. */
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetLeftContent = new Type<RevealContentHandler<?>>();

	/**
	 * The Interface MyProxy.
	 */
	@ProxyStandard
	public interface MyProxy extends Proxy<AppPresenter> {

	}

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {

		/**
		 * Gets the username.
		 * 
		 * @return the username
		 */
		HTMLFlow getUsername();

		/**
		 * Gets the edits the users.
		 * 
		 * @return the edits the users
		 */
		HTMLFlow getEditUsers();
	}

	/** The left presenter. */
	DigitalObjectMenuPresenter leftPresenter;

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The place manager. */
	private final PlaceManager placeManager;

	/**
	 * Instantiates a new app presenter.
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
	// public AppPresenter(final DispatchAsync dispatcher, final HomePresenter
	// homePresenter, final DigitalObjectMenuPresenter treePresenter,
	// final DigitalObjectMenuPresenter digitalObjectMenuPresenter, final
	// EditorClientConfiguration config) {
	public AppPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher, final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.leftPresenter = leftPresenter;
		this.placeManager = placeManager;
		getView().setUiHandlers(this);
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		super.onReset();
		dispatcher.execute(new GetLoggedUserAction(), new DispatchCallback<GetLoggedUserResult>() {
			@Override
			public void callback(GetLoggedUserResult result) {
				getView().getUsername().setContents("<b>" + result.getName() + "</b>");
				if (result.isEditUsers()) {
					getView().getEditUsers().setContents(lang.userMgmt());
					getView().getEditUsers().setCursor(Cursor.HAND);
					getView().getEditUsers().setWidth(120);
					getView().getEditUsers().setHeight(15);
					getView().getEditUsers().setStyleName("pseudolink");
					getView().getEditUsers().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
						@Override
						public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
							placeManager.revealRelativePlace(new PlaceRequest(NameTokens.USERS));
						}
					});
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.view.AppView.MyUiHandlers#logout()
	 */
	@Override
	public void logout() {
		dispatcher.execute(new LogoutAction(), new DispatchCallback<LogoutResult>() {
			@Override
			public void callback(LogoutResult result) {
				MEditor.redirect("/login.html");
			}
		});
	}

}