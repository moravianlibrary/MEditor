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

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.NameTokens.ADMIN_MENU_BUTTONS;
import cz.mzk.editor.client.gwtrpcds.RequestsGwtRPCDS;
import cz.mzk.editor.client.other.UsersLayout;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;
import cz.mzk.editor.shared.event.OpenUserPresenterEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class UserPresenter
        extends Presenter<UserPresenter.MyView, UserPresenter.MyProxy> {

    private final LangConstants lang;

    /**
     * The Interface MyView.
     */
    public interface MyView
            extends View {

        /**
         * Gets the user grid.
         * 
         * @return the user grid
         */
        public ListGrid getUserGrid();

        /**
         * Gets the users layout.
         * 
         * @return the users layout
         */
        public UsersLayout getUsersLayout();

        /**
         * Gets the requests grid.
         * 
         * @return the requests grid
         */
        public ListGrid getRequestsGrid();

        /**
         * Gets the adds the user.
         * 
         * @return the adds the user
         */
        public IButton getAddUser();

    }

    /**
     * The Interface MyProxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.ADMIN_MENU_BUTTONS.USERS)
    public interface MyProxy
            extends ProxyPlace<UserPresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    @SuppressWarnings("rawtypes")
    private Presenter leftPresenter;

    /** The place manager. */
    @SuppressWarnings("unused")
    private final PlaceManager placeManager;

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
     *        the place manager
     */
    @Inject
    public UserPresenter(final EventBus eventBus,
                         final MyView view,
                         final MyProxy proxy,
                         final DispatchAsync dispatcher,
                         final PlaceManager placeManager,
                         LangConstants lang) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.lang = lang;
        bind();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onBind() {
        super.onBind();

        final RequestsGwtRPCDS reqSource = new RequestsGwtRPCDS(dispatcher, lang);
        getView().getRequestsGrid().setDataSource(reqSource);

        // add user
        getView().getAddUser().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final Window winModal = new Window();
                winModal.setHeight(200);
                winModal.setWidth(550);
                winModal.setCanDragResize(true);
                winModal.setShowEdges(true);
                winModal.setTitle(lang.newUser());
                winModal.setShowMinimizeButton(false);
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.addCloseClickHandler(new CloseClickHandler() {

                    @Override
                    public void onCloseClick(CloseClickEvent event) {
                        winModal.destroy();

                    }
                });
                final DynamicForm form = new DynamicForm();
                form.setMargin(15);
                form.setWidth(500);
                form.setHeight(150);
                form.setDataSource(getView().getUsersLayout().getUserDataSource());
                HiddenItem id = new HiddenItem(Constants.ATTR_USER_ID);
                TextItem name = new TextItem(Constants.ATTR_NAME, lang.firstName());
                name.setWidth(320);
                TextItem surname = new TextItem(Constants.ATTR_SURNAME, lang.lastName());
                surname.setWidth(320);
                ButtonItem add = new ButtonItem();
                add.setTitle(lang.addUser());
                add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        final ModalWindow mw = new ModalWindow(getView().getUserGrid());
                        mw.setLoadingIcon("loadingAnimation.gif");
                        mw.show(true);

                        form.saveData(new DSCallback() {

                            @Override
                            public void execute(DSResponse response, Object rawData, DSRequest request) {
                                winModal.destroy();
                                getView().getUserGrid().selectRecords(response.getData());
                                getView().getUserGrid().expandRecord((ListGridRecord) response.getData()[0]);
                                getView().getUserGrid().scrollToRow(getView().getUserGrid()
                                        .getRecordIndex(response.getData()[0]));
                                mw.hide();
                            }
                        });
                    }
                });
                ButtonItem cancel = new ButtonItem();
                cancel.setColSpan(0);
                cancel.setTitle(lang.cancel());
                cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        winModal.destroy();
                    }
                });
                form.setFields(name, surname, add, cancel, id);
                winModal.addItem(form);

                winModal.centerInPage();
                winModal.show();
            }
        });

        addRegisteredHandler(OpenUserPresenterEvent.getType(),
                             new OpenUserPresenterEvent.OpenUserPresenterHandler() {

                                 @Override
                                 public void onOpenUserPresenter(OpenUserPresenterEvent event) {
                                     leftPresenter = event.getLeftPresenter();
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
        if (leftPresenter != null) {
            RevealContentEvent.fire(this, Constants.TYPE_ADMIN_LEFT_CONTENT, leftPresenter);
        }
        getEventBus().fireEvent(new MenuButtonClickedEvent(ADMIN_MENU_BUTTONS.USERS, false));
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
     */
    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, Constants.TYPE_ADMIN_MAIN_CONTENT, this);
    }

}