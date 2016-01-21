/*
 * Metadata Editor
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

package cz.mzk.editor.client.other;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemChangedEvent;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.gwtrpcds.UsersGwtRPCDS;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.window.AddIdentityWindow;
import cz.mzk.editor.client.view.window.AddRightsWindow;
import cz.mzk.editor.client.view.window.AddRoleWindow;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesResult;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityResult;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDetailLayout.
 * 
 * @author Matous Jobanek
 * @version Nov 26, 2012
 */
public class UserDetailLayout
        extends VLayout {

    private abstract class GetNewDataHandler {

        public GetNewDataHandler(Canvas toLoading,
                                 String userId,
                                 List<Constants.USER_IDENTITY_TYPES> types,
                                 boolean getRoles) {
            final ModalWindow mw = new ModalWindow(toLoading);
            mw.setLoadingIcon("loadingAnimation.gif");
            mw.show(true);

            dispatcher.execute(new GetUserRolesRightsIdentitiesAction(userId, types, getRoles),
                               new DispatchCallback<GetUserRolesRightsIdentitiesResult>() {

                                   /**
                                    * {@inheritDoc}
                                    */
                                   @Override
                                   public void callback(GetUserRolesRightsIdentitiesResult result) {
                                       if (result != null) {
                                           afterGetAction(result);
                                           mw.hide();
                                       } else {
                                           SC.warn("There is no data!!!");
                                       }
                                   }

                                   /**
                                    * {@inheritDoc}
                                    */
                                   @Override
                                   public void callbackError(Throwable t) {
                                       super.callbackError(t);
                                       mw.hide();
                                   }
                               });
        }

        protected abstract void afterGetAction(GetUserRolesRightsIdentitiesResult result);
    }

    /**
     * The Class UniversalListGrid.
     */
    private final class UniversalSectionGrid
            extends SectionStack {

        /** The grid. */
        private final UniversalListGrid grid;

        /**
         * Instantiates a new universal section grid.
         * 
         * @param title
         *        the title
         * @param identType
         *        the ident type
         * @param isRole
         *        the is role
         */
        public UniversalSectionGrid(String title, Constants.USER_IDENTITY_TYPES identType, boolean isRole) {
            super();

            if (!isMyAcount) {
                setWidth(290);
                setHeight(100);
                setMargin(5);
            } else {
                setWidth(330);
                setHeight(200);
                setMargin(10);
            }

            grid = new UniversalListGrid();
            grid.setCanDragSelectText(true);

            if (isRole) {
                grid.setHoverCustomizer(new HoverCustomizer() {

                    @Override
                    public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                        return lang.dcRights() + ":<br>" + record.getAttribute(Constants.ATTR_RIGHT_IN_ROLE);
                    }
                });
            }

            SectionStackSection sectionStackSection = new SectionStackSection(title);
            sectionStackSection.setItems(grid);
            sectionStackSection.setCanCollapse(false);
            sectionStackSection.setExpanded(true);

            ImgButton add = getAddButton(identType, isRole);
            ImgButton remove = getRemoveButton(identType, isRole);

            if (!isMyAcount) sectionStackSection.setControls(add, remove);
            addSection(sectionStackSection);
            setCanDragResize(true);
        }

        /**
         * Gets the adds the button.
         * 
         * @param identType
         *        the ident type
         * @param isRole
         *        the is role
         * @return the adds the button
         */
        private ImgButton getAddButton(final Constants.USER_IDENTITY_TYPES identType, final boolean isRole) {
            ImgButton add = new ImgButton();
            add.setSrc("icons/16/add.png");
            add.setWidth(16);
            add.setHeight(16);
            add.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (identType != null) {
                        new AddIdentityWindow(UserClientUtils.getIdentityTitle(identType),
                                              eventBus,
                                              lang,
                                              userId,
                                              dispatcher,
                                              identType) {

                            @Override
                            protected void afterSuccessAction() {
                                ArrayList<USER_IDENTITY_TYPES> type =
                                        new ArrayList<Constants.USER_IDENTITY_TYPES>();
                                type.add(identType);
                                new GetNewDataHandler(grid, userId, type, false) {

                                    @Override
                                    protected void afterGetAction(GetUserRolesRightsIdentitiesResult result) {
                                        grid.setData(UserClientUtils.copyIdentities(result.getIdentities()
                                                .get(0)));
                                    }
                                };
                            }
                        };
                    } else if (isRole) {
                        new AddRoleWindow(userId, lang, eventBus, dispatcher, UserClientUtils
                                .copyToRoles(Long.parseLong(userId), grid.getRecords())) {

                            @Override
                            protected void afterAddAction() {

                                new GetNewDataHandler(grid, userId, null, true) {

                                    @Override
                                    protected void afterGetAction(GetUserRolesRightsIdentitiesResult result) {
                                        grid.setData(UserClientUtils.copyRoles(result.getRoles()));
                                    }
                                };
                            }
                        };
                    } else {
                        new AddRightsWindow(userId, eventBus, lang, dispatcher, UserClientUtils
                                .copyToRights(grid.getRecords())) {

                            @Override
                            protected void afterAddAction() {
                                new GetNewDataHandler(grid, userId, null, false) {

                                    @Override
                                    protected void afterGetAction(GetUserRolesRightsIdentitiesResult result) {
                                        grid.setData(UserClientUtils.copyRights(result.getRights()));
                                    }
                                };
                            }
                        };
                    }
                }
            });

            return add;
        }

        /**
         * Gets the removes the button.
         * 
         * @return the removes the button
         */
        private ImgButton getRemoveButton(final Constants.USER_IDENTITY_TYPES identType, final boolean isRole) {
            ImgButton remove = new ImgButton();
            remove.setSrc("icons/16/remove.png");
            remove.setWidth(16);
            remove.setHeight(16);
            remove.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    ListGridRecord[] selectedRecords = grid.getSelectedRecords();
                    if (selectedRecords.length == 0) return;
                    final ModalWindow mw = new ModalWindow(grid);
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);

                    if (identType != null) {
                        RemoveUserIdentityAction remIdentity =
                                new RemoveUserIdentityAction(UserClientUtils
                                        .copyToIdentities(selectedRecords, identType, Long.parseLong(userId)));
                        dispatcher.execute(remIdentity, new DispatchCallback<RemoveUserIdentityResult>() {

                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void callback(RemoveUserIdentityResult result) {
                                if (result.getIdentities() != null) {
                                    grid.setData(UserClientUtils
                                            .copyIdentities(result.getIdentities().get(0)));
                                } else {
                                    SC.warn(lang.operationFailed());
                                }
                                mw.hide();
                            }

                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void callbackError(Throwable t) {
                                super.callbackError(t);
                                mw.hide();
                            }
                        });
                    } else if (isRole) {
                        PutRemoveUserRolesAction remRolesAction =
                                new PutRemoveUserRolesAction(UserClientUtils.copyToRoles(Long
                                        .parseLong(userId), selectedRecords), false);
                        dispatcher.execute(remRolesAction, new DispatchCallback<PutRemoveUserRolesResult>() {

                            @Override
                            public void callback(PutRemoveUserRolesResult result) {
                                if (result.getRoles() != null) {
                                    grid.setData(UserClientUtils.copyRoles(result.getRoles()));
                                } else {
                                    SC.warn(lang.operationFailed());
                                }
                                mw.hide();
                            }

                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void callbackError(Throwable t) {
                                super.callbackError(t);
                                mw.hide();
                            }
                        });
                    } else {
                        PutRemoveUserRightsAction remRolesAction =
                                new PutRemoveUserRightsAction(userId, UserClientUtils
                                        .copyToRightsString(selectedRecords), false);
                        dispatcher.execute(remRolesAction, new DispatchCallback<PutRemoveUserRightsResult>() {

                            @Override
                            public void callback(PutRemoveUserRightsResult result) {
                                if (result.getRights() != null) {
                                    grid.setData(UserClientUtils.copyRights(result.getRights()));
                                } else {
                                    SC.warn(lang.operationFailed());
                                }
                                mw.hide();
                            }

                            /**
                             * {@inheritDoc}
                             */
                            @Override
                            public void callbackError(Throwable t) {
                                super.callbackError(t);
                                mw.hide();
                            }
                        });
                    }
                }
            });

            return remove;
        }

        /**
         * Sets the fields.
         * 
         * @param fields
         *        the new fields
         */
        public void setFields(ListGridField... fields) {
            grid.setFields(fields);
        }

        /**
         * Sets the data.
         * 
         * @param data
         *        the new data
         */
        public void setData(ListGridRecord[] data) {
            grid.setData(data);
        }
    }

    /** The lang. */
    private final LangConstants lang;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The event bus. */
    private final EventBus eventBus;

    /** The user id. */
    private final String userId;

    /** The grids layout. */
    private VLayout gridsLayout;

    private final boolean isMyAcount;

    /**
     * Instantiates a new user detail layout.
     * 
     * @param userDataSource
     *        the user data source
     * @param record
     *        the record
     * @param grid
     *        the grid
     * @param lang
     *        the lang
     * @param dispatcher
     *        the dispatcher
     * @param eventBus
     *        the event bus
     */
    public UserDetailLayout(UsersGwtRPCDS userDataSource,
                            final ListGridRecord record,
                            final ListGrid grid,
                            LangConstants lang,
                            DispatchAsync dispatcher,
                            EventBus eventBus) {
        super();
        this.lang = lang;
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
        this.userId = record.getAttributeAsString(Constants.ATTR_USER_ID);

        isMyAcount = (userDataSource == null);

        setPadding(5);

        if (userDataSource != null) {

            final DynamicForm nameForm = new DynamicForm();
            nameForm.setNumCols(4);
            nameForm.setDataSource(userDataSource);
            nameForm.addDrawHandler(new DrawHandler() {

                @Override
                public void onDraw(DrawEvent event) {
                    nameForm.editRecord(record);
                }
            });

            final IButton saveButton = new IButton(lang.save());
            saveButton.setDisabled(true);
            saveButton.setLayoutAlign(Alignment.RIGHT);
            saveButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    nameForm.saveData();
                }
            });

            nameForm.addItemChangedHandler(new ItemChangedHandler() {

                @Override
                public void onItemChanged(ItemChangedEvent event) {
                    saveButton.setDisabled(false);
                }
            });

            addMember(nameForm);

            setIdentitiesAndRoles(grid, record);

            addMember(saveButton);
        } else {
            HTMLFlow titleFlow =
                    new HTMLFlow(HtmlCode.title(record.getAttributeAsString(Constants.ATTR_NAME), 1));
            titleFlow.setExtraSpace(70);
            titleFlow.setMargin(10);
            addMember(titleFlow);

            setIdentitiesAndRoles(grid, record);
        }

    }

    /**
     * Sets the identities and roles.
     * 
     * @param record
     * @param grid
     */
    private void setIdentitiesAndRoles(final ListGrid grid, final ListGridRecord record) {
        if (gridsLayout == null) gridsLayout = new VLayout();
        if (gridsLayout.getMembers().length > 0) gridsLayout.removeMembers(gridsLayout.getMembers());

        new GetNewDataHandler(gridsLayout, userId, new ArrayList<Constants.USER_IDENTITY_TYPES>(), true) {

            @Override
            protected void afterGetAction(GetUserRolesRightsIdentitiesResult result) {
                HTMLFlow identities = new HTMLFlow(HtmlCode.bold(lang.identities()));
                identities.setHeight(15);
                if (isMyAcount) {
                    identities.setLayoutAlign(Alignment.CENTER);
                    identities.setWidth(1020);
                }

                addMember(identities);
                addMember(getIdentitiesLayout(result.getIdentities()));

                HLayout hLayout = new HLayout(2);
                if (isMyAcount) hLayout.setAlign(Alignment.CENTER);

                hLayout.addMember(getRolesLayout(result.getRoles()));

                hLayout.addMember(getRightsLayout(result.getRights()));

                IButton cancelButton = new IButton("Close");
                cancelButton.setLayoutAlign(VerticalAlignment.BOTTOM);
                cancelButton.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        if (grid != null) grid.collapseRecord(record);
                    }
                });

                if (!isMyAcount) hLayout.addMember(cancelButton);
                addMember(hLayout);
            }
        };
    }

    /**
     * Gets the roles layout.
     * 
     * @param roles
     *        the data
     * @return the roles layout
     */
    private VLayout getRolesLayout(List<RoleItem> roles) {
        VLayout vLayout = new VLayout();
        if (isMyAcount) vLayout.setWidth(450);

        HTMLFlow roleFlow = new HTMLFlow(HtmlCode.bold(lang.roles()));
        roleFlow.setHeight(15);

        UniversalSectionGrid userRoleGrid = new UniversalSectionGrid(lang.role(), null, true);

        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        userRoleGrid.setFields(nameField, descField);

        userRoleGrid.setData(UserClientUtils.copyRoles(roles));

        vLayout.addMember(roleFlow);
        vLayout.addMember(userRoleGrid);

        return vLayout;

    }

    /**
     * Gets the rights layout.
     * 
     * @param rights
     *        the rights
     * @return the rights layout
     */
    private VLayout getRightsLayout(List<Constants.EDITOR_RIGHTS> rights) {
        VLayout vLayout = new VLayout();
        if (isMyAcount) vLayout.setWidth(350);

        HTMLFlow right = new HTMLFlow(HtmlCode.bold(lang.otherRights()));
        right.setHeight(15);

        UniversalSectionGrid userRightsGrid = new UniversalSectionGrid(lang.right(), null, false);

        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        userRightsGrid.setFields(nameField, descField);

        userRightsGrid.setData(UserClientUtils.copyRights(rights));

        vLayout.addMember(right);
        vLayout.addMember(userRightsGrid);

        return vLayout;

    }

    /**
     * Gets the identities layout.
     * 
     * @param identities
     *        the identities
     * @return the identities layout
     */
    private HLayout getIdentitiesLayout(List<UserIdentity> identities) {
        HLayout hLayout = new HLayout();
        if (isMyAcount) {
            hLayout.setAlign(Alignment.CENTER);
            hLayout.setExtraSpace(30);
        }

        if (identities != null && identities.size() > 0) {
            for (UserIdentity userIdentity : identities) {

                UniversalSectionGrid identityGrid =
                        new UniversalSectionGrid(UserClientUtils.getIdentityTitle(userIdentity.getType())
                                + " " + lang.identities(), userIdentity.getType(), false);

                ListGridField identityField = new ListGridField(Constants.ATTR_IDENTITY);
                identityGrid.setFields(identityField);

                identityGrid.setData(UserClientUtils.copyIdentities(userIdentity));
                hLayout.addMember(identityGrid);
            }
        } else {
            hLayout.addMember(new HTMLFlow(HtmlCode.title("WARN: There is no identity", 1)));
        }
        return hLayout;
    }
}
