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

import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
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
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.gwtrpcds.RequestsGwtRPCDS;
import cz.mzk.editor.client.other.UsersLayout;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.GetAllRolesAction;
import cz.mzk.editor.shared.rpc.action.GetAllRolesResult;

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
         * Gets the user role grid.
         * 
         * @return the user role grid
         */
        public ListGrid getUserRoleGrid();

        /**
         * Gets the user identity grid.
         * 
         * @return the user identity grid
         */
        public ListGrid getUserIdentityGrid();

        /**
         * Gets the removes the user.
         * 
         * @return the removes the user
         */
        public IButton getRemoveUser();

        /**
         * Gets the adds the user.
         * 
         * @return the adds the user
         */
        public IButton getAddUser();

        /**
         * Gets the removes the role.
         * 
         * @return the removes the role
         */
        public IButton getRemoveRole();

        /**
         * Gets the adds the role.
         * 
         * @return the adds the role
         */
        public IButton getAddRole();

        /**
         * Gets the removes the identity.
         * 
         * @return the removes the identity
         */
        public IButton getRemoveIdentity();

        public IButton getRemoveRequest();

        /**
         * Gets the adds the identity.
         * 
         * @return the adds the identity
         */
        public IButton getAddIdentity();

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
    @NameToken(NameTokens.ADMIN_MENU_BUTTONS.USERS)
    public interface MyProxy
            extends ProxyPlace<UserPresenter> {

    }

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The left presenter. */
    private Presenter leftPresenter;

    /** The place manager. */
    @SuppressWarnings("unused")
    private final PlaceManager placeManager;

    /** The roles. */
    private List<RoleItem> roles;

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
                         //                         AdminMenuPresenter leftPresenter,
                         LangConstants lang) {
        super(eventBus, view, proxy);
        //        this.leftPresenter = leftPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.lang = lang;
        dispatcher.execute(new GetAllRolesAction(), new DispatchCallback<GetAllRolesResult>() {

            @Override
            public void callback(GetAllRolesResult result) {
                UserPresenter.this.roles = result.getRoles();
            }
        });
        bind();
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
     */
    @Override
    protected void onBind() {
        super.onBind();

        final RequestsGwtRPCDS reqSource = new RequestsGwtRPCDS(dispatcher, lang);
        getView().getRequestsGrid().setDataSource(reqSource);

        // fetch roles and identities
        //        getView().getUserGrid().addRecordClickHandler(new RecordClickHandler() {
        //
        //            @Override
        //            public void onRecordClick(RecordClickEvent event) {
        //                
        //            }
        //        });
        //        getView().getUserRoleGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
        //
        //            @Override
        //            public void onSelectionChanged(SelectionEvent event) {
        //                ListGridRecord[] selection = event.getSelection();
        //                if (selection != null && selection.length > 0) {
        //                    getView().getRemoveRole().setDisabled(false);
        //                } else {
        //                    getView().getRemoveRole().setDisabled(true);
        //                }
        //            }
        //        });

        //        getView().getUserIdentityGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
        //
        //            @Override
        //            public void onSelectionChanged(SelectionEvent event) {
        //                ListGridRecord[] selection = event.getSelection();
        //                if (selection != null && selection.length > 0) {
        //                    getView().getRemoveIdentity().setDisabled(false);
        //                } else {
        //                    getView().getRemoveIdentity().setDisabled(true);
        //                }
        //            }
        //        });
        getView().getRequestsGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selection = event.getSelection();
                if (selection != null && selection.length > 0) {
                    getView().getRemoveRequest().setDisabled(false);
                } else {
                    getView().getRemoveRequest().setDisabled(true);
                }
            }
        });

        // remove request
        getView().getRemoveRequest().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getView().getRequestsGrid().removeSelectedData();
            }
        });

        // remove user
        getView().getRemoveUser().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getView().getUserGrid().removeSelectedData();
                getView().getUserRoleGrid().setData(new ListGridRecord[] {});
                getView().getUserIdentityGrid().setData(new ListGridRecord[] {});
            }
        });

        // add user
        getView().getAddUser().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final Window winModal = new Window();
                winModal.setHeight(200);
                winModal.setWidth(550);
                // winModal.setPadding(15);
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
                // CheckboxItem sex = new CheckboxItem(ServerConstants.ATTR_SEX,
                // "Male");
                ButtonItem add = new ButtonItem();
                add.setTitle(lang.addUser());
                add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        form.saveData(new DSCallback() {

                            @Override
                            public void execute(DSResponse response, Object rawData, DSRequest request) {
                                winModal.destroy();
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
                form.setFields(name, surname/* , sex */, add, cancel, id);
                winModal.addItem(form);

                winModal.centerInPage();
                winModal.show();
            }
        });

        // remove identity
        //        getView().getRemoveIdentity().addClickHandler(new ClickHandler() {
        //
        //            private volatile int deletedCounter;
        //
        //            private void deleteFromGUI(int total, ListGridRecord[] selection) {
        //                if (deletedCounter == total) {
        //                    ListGridRecord[] oldData = getView().getUserIdentityGrid().getRecords();
        //                    ListGridRecord[] newData = ClientCreateUtils.subtract(oldData, selection);
        //                    getView().getUserIdentityGrid().setData(newData);
        //                }
        //            }
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                final ListGridRecord[] selection = getView().getUserIdentityGrid().getSelectedRecords();
        //                if (selection != null && selection.length > 0) {
        //                    final int total = selection.length;
        //                    for (final ListGridRecord record : selection) {
        //                        dispatcher.execute(new RemoveUserIdentityAction(record
        //                                                   .getAttribute(Constants.ATTR_GENERIC_ID)),
        //                                           new DispatchCallback<RemoveUserIdentityResult>() {
        //
        //                                               @Override
        //                                               public void callback(RemoveUserIdentityResult result) {
        //                                                   deletedCounter++;
        //                                                   deleteFromGUI(total, selection);
        //                                               }
        //                                           });
        //                    }
        //                }
        //            }
        //        });

        // add identity
        //        getView().getAddIdentity().addClickHandler(new ClickHandler() {
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                
        //            }
        //        });

        // remove role
        //        getView().getRemoveRole().addClickHandler(new ClickHandler() {
        //
        //            private volatile int deletedCounter;
        //
        //            private void deleteFromGUI(int total, ListGridRecord[] selection) {
        //                if (deletedCounter == total) {
        //                    ListGridRecord[] oldData = getView().getUserRoleGrid().getRecords();
        //                    ListGridRecord[] newData = ClientCreateUtils.subtract(oldData, selection);
        //                    getView().getUserRoleGrid().setData(newData);
        //                }
        //            }
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                final ListGridRecord[] selection = getView().getUserRoleGrid().getSelectedRecords();
        //                if (selection != null && selection.length > 0) {
        //                    final int total = selection.length;
        //                    for (final ListGridRecord record : selection) {
        //                        dispatcher.execute(new RemoveUserRoleAction(record
        //                                                   .getAttribute(Constants.ATTR_GENERIC_ID)),
        //                                           new DispatchCallback<RemoveUserRoleResult>() {
        //
        //                                               @Override
        //                                               public void callback(RemoveUserRoleResult result) {
        //                                                   deletedCounter++;
        //                                                   deleteFromGUI(total, selection);
        //                                               }
        //                                           });
        //                    }
        //                }
        //            }
        //        });

        // add role
        //        getView().getAddRole().addClickHandler(new ClickHandler() {
        //
        //            @Override
        //            public void onClick(ClickEvent event) {
        //                final Window winModal = new Window();
        //                winModal.setHeight(200);
        //                winModal.setWidth(550);
        //                winModal.setCanDragResize(true);
        //                winModal.setShowEdges(true);
        //                winModal.setTitle(lang.newRole());
        //                winModal.setShowMinimizeButton(false);
        //                winModal.setIsModal(true);
        //                winModal.setShowModalMask(true);
        //                winModal.addCloseClickHandler(new CloseClickHandler() {
        //
        //                    @Override
        //                    public void onCloseClick(CloseClickEvent event) {
        //                        winModal.destroy();
        //                    }
        //                });
        //                final DynamicForm form = new DynamicForm();
        //                // form.setNumCols(8);
        //                form.setMargin(15);
        //                form.setWidth(500);
        //                form.setHeight(150);
        //                final SelectItem role = new SelectItem(Constants.ATTR_NAME, lang.role());
        //                role.setWidth(320);
        //                role.setValueMap(roles.toArray(new String[] {}));
        //                ButtonItem add = new ButtonItem();
        //                add.setEndRow(false);
        //                add.setTitle(lang.addRole());
        //                add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
        //
        //                    @Override
        //                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
        //                        final String roleValue = (String) role.getValue();
        //                        if (roleValue != null && !"".equals(roleValue.trim())) {
        //                            String userId =
        //                                    getView().getUserGrid().getSelectedRecord()
        //                                            .getAttribute(Constants.ATTR_USER_ID);
        //                            dispatcher.execute(new PutUserRoleAction(new RoleItem(Long.parseLong(userId),
        //                                                                                  roleValue,
        //                                                                                  "")),
        //                                               new DispatchCallback<PutUserRoleResult>() {
        //
        //                                                   @Override
        //                                                   public void callback(PutUserRoleResult result) {
        //                                                       //                                                       if (!result.isFound()
        //                                                       //                                                               && !"error".equals(result.getId())) {
        //                                                       //                                                           ListGridRecord record = new ListGridRecord();
        //                                                       //                                                           record.setAttribute(Constants.ATTR_GENERIC_ID,
        //                                                       //                                                                               result.getId());
        //                                                       //                                                           record.setAttribute(Constants.ATTR_NAME, roleValue);
        //                                                       //                                                           record.setAttribute(Constants.ATTR_DESC,
        //                                                       //                                                                               result.getDescription());
        //                                                       //                                                           ListGridRecord[] previousData =
        //                                                       //                                                                   getView().getUserRoleGrid().getRecords();
        //                                                       //                                                           ListGridRecord[] newData =
        //                                                       //                                                                   new ListGridRecord[previousData.length + 1];
        //                                                       //                                                           System.arraycopy(previousData,
        //                                                       //                                                                            0,
        //                                                       //                                                                            newData,
        //                                                       //                                                                            0,
        //                                                       //                                                                            previousData.length);
        //                                                       //                                                           newData[previousData.length] = record;
        //                                                       //                                                           getView().getUserRoleGrid().setData(newData);
        //                                                       //                                                       }
        //                                                       //                                                       winModal.destroy();
        //                                                   }
        //                                               });
        //                        }
        //                    }
        //                });
        //                ButtonItem cancel = new ButtonItem();
        //                cancel.setEndRow(false);
        //                cancel.setTitle(lang.cancel());
        //                cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
        //
        //                    @Override
        //                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
        //                        winModal.destroy();
        //                    }
        //                });
        //                form.setFields(role, add, cancel);
        //                winModal.addItem(form);
        //                winModal.centerInPage();
        //                winModal.show();
        //            }
        //        });
        //
        //        addRegisteredHandler(OpenUserPresenterEvent.getType(), new OpenUserPresenterHandler() {
        //
        //            @Override
        //            public void onOpenUserPresenter(OpenUserPresenterEvent event) {
        //                leftPresenter = event.getLeftPresenter();
        //            }
        //        });
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