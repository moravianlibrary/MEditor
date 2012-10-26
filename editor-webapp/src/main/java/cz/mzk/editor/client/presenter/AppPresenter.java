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

package cz.mzk.editor.client.presenter;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.gwtplatform.dispatch.shared.DispatchAsync;
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
import com.smartgwt.client.widgets.form.fields.TextItem;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.MEditor;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.uihandlers.MyUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.HtmlCode;
import cz.mzk.editor.client.view.window.IngestInfoWindow;
import cz.mzk.editor.client.view.window.StoreWorkingCopyWindow;
import cz.mzk.editor.client.view.window.UuidWindow;
import cz.mzk.editor.shared.event.ChangeMenuWidthEvent;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.OpenFirstDigitalObjectEvent;
import cz.mzk.editor.shared.event.SetEnabledHotKeysEvent;
import cz.mzk.editor.shared.rpc.StoredItem;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserResult;
import cz.mzk.editor.shared.rpc.action.LogoutAction;
import cz.mzk.editor.shared.rpc.action.LogoutResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AppPresenter.
 */
public class AppPresenter
        extends Presenter<AppPresenter.MyView, AppPresenter.MyProxy>
        implements MyUiHandlers {

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_MAIN_CONTENT = Constants.TYPE_MEDIT_MAIN_CONTENT;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_MEDIT_LEFT_CONTENT = Constants.TYPE_MEDIT_LEFT_CONTENT;

    private LangConstants lang;
    private volatile boolean unknown = true;

    /** The uuid-window **/
    private UuidWindow uuidWindow = null;

    @Inject
    public void setLang(LangConstants lang) {
        this.lang = lang;
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyStandard
    public interface MyProxy
            extends Proxy<AppPresenter> {

    }

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View, HasUiHandlers<MyUiHandlers> {

        HTMLFlow getUsername();

        HTMLFlow getEditUsers();

        void escShortCut();

        void changeMenuWidth(String width);
    }

    /** The left presenter. */
    private final DigitalObjectMenuPresenter doPresenter;

    private final CreateObjectMenuPresenter createPresenter;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The place manager. */
    private final PlaceManager placeManager;

    private boolean isHotKeysEnabled = true;

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
    // public AppPresenter(final DispatchAsync dispatcher, final HomePresenter
    // homePresenter, final DigitalObjectMenuPresenter treePresenter,
    // final DigitalObjectMenuPresenter digitalObjectMenuPresenter, final
    // EditorClientConfiguration config) {
    public AppPresenter(final EventBus eventBus,
                        final MyView view,
                        final MyProxy proxy,
                        final DigitalObjectMenuPresenter doPresenter,
                        final CreateObjectMenuPresenter leftPresenter,
                        final DispatchAsync dispatcher,
                        final PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.doPresenter = doPresenter;
        this.createPresenter = leftPresenter;
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

        addRegisteredHandler(OpenFirstDigitalObjectEvent.getType(),
                             new OpenFirstDigitalObjectEvent.OpenFirstDigitalObjectHandler() {

                                 @Override
                                 public void onOpenFirstDigitalObject(OpenFirstDigitalObjectEvent event) {
                                     StoredItem storedItem = event.getStoredItem();
                                     if (storedItem != null) {
                                         placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                                                 .with(Constants.URL_PARAM_UUID, event.getUuid())
                                                 .with(Constants.ATTR_FILE_NAME, storedItem.getFileName())
                                                 .with(Constants.ATTR_MODEL, storedItem.getModel().getValue()));
                                     } else {
                                         openObject(event.getUuid());
                                     }
                                 }
                             });

        addRegisteredHandler(SetEnabledHotKeysEvent.getType(),
                             new SetEnabledHotKeysEvent.SetEnabledHotKeysHandler() {

                                 @Override
                                 public void onSetEnabledHotKeys(SetEnabledHotKeysEvent event) {
                                     isHotKeysEnabled = event.isEnable();
                                 }
                             });

        addRegisteredHandler(ChangeMenuWidthEvent.getType(),
                             new ChangeMenuWidthEvent.ChangeMenuWidthHandler() {

                                 @Override
                                 public void onChangeMenuWidth(ChangeMenuWidthEvent event) {
                                     getView().changeMenuWidth(event.getWidth());
                                 }
                             });

        addRegisteredHandler(KeyPressedEvent.getType(), new KeyPressedEvent.KeyPressedHandler() {

            @Override
            public void onKeyPressed(KeyPressedEvent event) {
                if (event.getCode() == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_S.getCode()) {
                    StoreWorkingCopyWindow.setInstanceOf(lang, dispatcher, getEventBus());
                    return;
                }
                if (event.getCode() == Constants.HOT_KEYS_WITH_CTRL_ALT.CODE_KEY_U.getCode()) {
                    displayEnterPIDWindow();
                    return;
                }
                if (event.getCode() == Constants.CODE_KEY_ESC) {
                    getView().escShortCut();
                    return;
                }
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
                    if (result.isEditUsers()) {
                        getView().getEditUsers().setContents(lang.userMgmt());
                        getView().getEditUsers().setCursor(Cursor.HAND);
                        getView().getEditUsers().setWidth(120);
                        getView().getEditUsers().setHeight(15);
                        getView().getEditUsers().setStyleName("pseudolink");
                        getView().getEditUsers()
                                .addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                                    @Override
                                    public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                                        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.USERS));
                                    }
                                });
                    }
                }
            });
        }
    }

    /**
     * Method for handle enter-new-object's-PID short-cut
     */
    private void displayEnterPIDWindow() {
        uuidWindow = new UuidWindow(lang, getEventBus()) {

            @Override
            protected void doAction(TextItem uuidField) {
                evaluateUuid(uuidWindow.getUuidField());
            }

        };
    }

    /**
     * Method for close currently displayed window
     */
    private void escShortCut() {
        if (uuidWindow != null) {
            uuidWindow.hide();
            uuidWindow = null;
        } else if (IngestInfoWindow.isInstanceVisible()) {
            IngestInfoWindow.closeInstantiatedWindow();
        }
    }

    private void evaluateUuid(TextItem uuidField) {
        if (uuidField.validate()) {
            uuidWindow.hide();
            openObject(uuidField.getValueAsString());
            uuidWindow = null;
        }
    }

    private void openObject(String uuid) {
        placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
                                                                                  uuid));
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
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
                MEditor.redirect(result.getUrl());
            }
        });
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public DigitalObjectMenuPresenter getDoPresenter() {
        return doPresenter;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public CreateObjectMenuPresenter getCreatePresenter() {
        return createPresenter;
    }

}