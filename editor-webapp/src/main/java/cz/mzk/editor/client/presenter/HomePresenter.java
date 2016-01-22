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

import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.config.EditorClientConfiguration;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.client.view.window.SchedulerWindow;
import cz.mzk.editor.shared.rpc.action.CheckAvailability;
import cz.mzk.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.mzk.editor.shared.rpc.action.CheckAvailabilityResult;
import cz.mzk.editor.shared.rpc.action.HasUserRightsAction;
import cz.mzk.editor.shared.rpc.action.HasUserRightsResult;
import org.jboss.errai.bus.client.api.ClientMessageBus;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class HomePresenter
        extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View {

        /**
         * Gets the name.
         * 
         * @param fedoraRunning
         *        the fedora running
         * @param url
         *        the url
         * @return the name
         */
        public void refreshFedora(boolean fedoraRunning, String url);

        /**
         * Refresh kramerius.
         * 
         * @param krameriusRunning
         *        the kramerius running
         * @param url
         *        the url
         */
        public void refreshKramerius(boolean krameriusRunning, String url);

        /**
         * Sets the ur ls.
         * 
         * @param fedoraUrl
         *        the fedora url
         * @param krameriusUrl
         *        the kramerius url
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
        public TextItem getUuid();

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

        public IButton getScheduler();

        void showSheduler();
    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.MEDIT_HOME)
    public interface MyProxy
            extends ProxyPlace<HomePresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private final DigitalObjectMenuPresenter leftPresenter;

    /** The place manager. */
    private final PlaceManager placeManager;

    private final LangConstants lang;

    private final ClientMessageBus messageBus;

    /**
     * Instantiates a new home presenter.
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
     * @param messageBus
     */
    @Inject
    public HomePresenter(final EventBus eventBus,
                         final MyView view,
                         final MyProxy proxy,
                         final DigitalObjectMenuPresenter leftPresenter,
                         final DispatchAsync dispatcher,
                         final PlaceManager placeManager,
                         final LangConstants lang, ClientMessageBus messageBus) {
        super(eventBus, view, proxy);
        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.lang = lang;
        this.messageBus = messageBus;

        bind();
    }

    /*
     * (non-Javadoc)
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
                evaluateUuid();
            }
        });
        getView().getUuid().addKeyPressHandler(new KeyPressHandler() {

            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getKeyName().equals("Enter") && !getView().getOpen().getDisabled()) {
                    evaluateUuid();
                }
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

        dispatcher.execute(new HasUserRightsAction(new EDITOR_RIGHTS[] {EDITOR_RIGHTS.LONG_RUNNING_PROCESS}),
                           new DispatchCallback<HasUserRightsResult>() {

                               @Override
                               public void callback(HasUserRightsResult result) {
                                   if (result.getOk()[0]) {
                                       getView().showSheduler();
                                       getView().getScheduler().addClickHandler(new ClickHandler() {

                                           @Override
                                           public void onClick(ClickEvent event) {
                                               new SchedulerWindow(getEventBus(), lang, dispatcher, messageBus);

                                           }
                                       });
                                   }
                               }
                           });
    }

    private void evaluateUuid() {
        if (getView().getForm().validate())
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.MODIFY)
                    .with(Constants.URL_PARAM_UUID, (String) getView().getUuid().getValue()));
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_MEDIT_LEFT_CONTENT, leftPresenter);
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, AppPresenter.TYPE_MEDIT_MAIN_CONTENT, this);
    }

    /**
     * Check availability.
     */
    private void checkAvailability() {
        final DispatchRequest krameriusRequest =
                dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.KRAMERIUS_ID),
                                   new DispatchCallback<CheckAvailabilityResult>() {

                                       @Override
                                       public void callback(CheckAvailabilityResult result) {
                                           String krameriusURL = result.getUrl();
                                           getView().refreshKramerius(result.isAvailability(), krameriusURL);
                                           if (krameriusURL == null || "".equals(krameriusURL)) {
                                               SC.warn("Please set "
                                                       + EditorClientConfiguration.Constants.KRAMERIUS_HOST
                                                       + " in system configuration.");
                                           }
                                       }

                                       @Override
                                       public void callbackError(Throwable t) {
                                           super.callbackError(t, "Kramerius " + lang.notRunning());
                                           getView().refreshKramerius(false, null);
                                       }
                                   });
        final DispatchRequest fedoraRequest =
                dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.FEDORA_ID),
                                   new DispatchCallback<CheckAvailabilityResult>() {

                                       @Override
                                       public void callback(CheckAvailabilityResult result) {
                                           String fedoraURL = result.getUrl();
                                           getView().refreshFedora(result.isAvailability(), fedoraURL);
                                           if (fedoraURL == null || "".equals(fedoraURL)) {
                                               SC.warn("Please set "
                                                       + EditorClientConfiguration.Constants.FEDORA_HOST
                                                       + " in system configuration.");
                                           }
                                       }

                                       @Override
                                       public void callbackError(Throwable t) {
                                           super.callbackError(t, "Fedora " + lang.notRunning());
                                           getView().refreshFedora(false, null);
                                       }
                                   });
        Timer timer = new Timer() {

            @Override
            public void run() {
                if (krameriusRequest.isPending()) {
                    krameriusRequest.cancel();
                    getView().refreshKramerius(false, null);
                }
                if (fedoraRequest.isPending()) {
                    fedoraRequest.cancel();
                    getView().refreshFedora(false, null);
                }
            }
        };
        timer.schedule(7000);
    }
}