/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.client.presenter;

import javax.inject.Inject;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.smartgwt.client.widgets.HTMLFlow;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.HotKeyPressManager;
import cz.mzk.editor.client.uihandlers.AdminUiHandlers;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent.MenuButtonClickedHandler;
import cz.mzk.editor.shared.event.OpenUserPresenterEvent;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserResult;
import cz.mzk.editor.shared.rpc.action.LogoutAction;
import cz.mzk.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AppPresenter.
 */
public class AdminPresenter
        extends Presenter<AdminPresenter.MyView, AdminPresenter.MyProxy>
        implements AdminUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_MAIN_CONTENT =
            Constants.TYPE_ADMIN_MAIN_CONTENT;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_ADMIN_LEFT_CONTENT =
            Constants.TYPE_ADMIN_LEFT_CONTENT;

    @SuppressWarnings("unused")
    private LangConstants lang;
    private volatile boolean unknown = true;

    @Inject
    public void setLang(LangConstants lang) {
        this.lang = lang;
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyStandard
    public interface MyProxy
            extends Proxy<AdminPresenter> {

    }

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<AdminUiHandlers> {

        HTMLFlow getUsername();

        void changeMenuWidth(String width);
    }

    /** The left presenter. */
    private final AdminMenuPresenter leftPresenter;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The place manager. */
    private final PlaceManager placeManager;

    /**
     * Instantiates a new app presenter.
     * 
     * @param eventBus
     *        the event bus
     * @param view
     *        the view
     * @param proxy
     *        the proxy
     * @param leftPresenter
     *        the left presenter
     * @param dispatcher
     *        the dispatcher
     * @param placeManager
     *        the place manager
     */
    @Inject
    public AdminPresenter(final EventBus eventBus,
                          final MyView view,
                          final MyProxy proxy,
                          final AdminMenuPresenter leftPresenter,
                          final DispatchAsync dispatcher,
                          final PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.leftPresenter = leftPresenter;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
        bind();
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();
        HotKeyPressManager.setInstanceOf(getEventBus());

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
            }
        });

        addRegisteredHandler(MenuButtonClickedEvent.getType(), new MenuButtonClickedHandler() {

            @Override
            public void onMenuButtonClicked(MenuButtonClickedEvent event) {
                if (event.isToOpenPresenter())
                    placeManager.revealRelativePlace(new PlaceRequest(event.getMenuButtonType()));
            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        super.onReset();
        if (unknown) {
            unknown = false;
            dispatcher.execute(new GetLoggedUserAction(), new DispatchCallback<GetLoggedUserResult>() {

                @Override
                public void callback(GetLoggedUserResult result) {
                    getView().getUsername().setContents(HtmlCode.bold(result.getName()));
                }
            });
        }
        getEventBus().fireEvent(new OpenUserPresenterEvent(leftPresenter));
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_ROOT_CONTENT, this);
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.view.AppView.MyUiHandlers#logout()
     */
    @Override
    public void logout() {
        dispatcher.execute(new LogoutAction(), new DispatchCallback<LogoutResult>() {

            @Override
            public void callback(LogoutResult result) {
                unknown = true;
                ClientUtils.redirect(result.getUrl());
            }
        });
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public AdminMenuPresenter getLeftPresenter() {
        return leftPresenter;
    }

}