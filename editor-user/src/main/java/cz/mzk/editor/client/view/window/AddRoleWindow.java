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

package cz.mzk.editor.client.view.window;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.UniversalListGrid;
import cz.mzk.editor.client.other.UserClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.GetAllRolesAction;
import cz.mzk.editor.shared.rpc.action.GetAllRolesResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AddRoleWindow.
 * 
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public abstract class AddRoleWindow
        extends UniversalWindow {

    /** The lang. */
    private final LangConstants lang;

    /** The event bus. */
    private final EventBus eventBus;

    /** The dispatcher. */
    private final DispatchAsync dispatcher;

    /** The grid. */
    private final UniversalListGrid grid;

    /**
     * Instantiates a new adds the role window.
     * 
     * @param userId
     *        the user id
     * @param lang
     *        the lang
     * @param eventBus
     *        the event bus
     * @param dispatcher
     *        the dispatcher
     * @param currentRoles
     *        the current roles
     */
    public AddRoleWindow(final String userId,
                         final LangConstants lang,
                         EventBus eventBus,
                         final DispatchAsync dispatcher,
                         final List<RoleItem> currentRoles) {
        super(450, 600, lang.addRole(), eventBus, 100);
        this.lang = lang;
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;

        grid = new UniversalListGrid();
        grid.setMargin(10);
        grid.setHeight(350);
        grid.setWidth100();

        ListGridField nameField =
                new ListGridField(Constants.ATTR_NAME, lang.name() + " " + lang.role().toLowerCase());
        ListGridField descField =
                new ListGridField(Constants.ATTR_DESC, lang.description() + " " + lang.role().toLowerCase());
        grid.setFields(nameField, descField);

        grid.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                return lang.dcRights() + ":<br>" + record.getAttribute(Constants.ATTR_RIGHT_IN_ROLE);
            }
        });

        final ModalWindow mw = new ModalWindow(grid);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(new GetAllRolesAction(), new DispatchCallback<GetAllRolesResult>() {

            @Override
            public void callback(GetAllRolesResult result) {
                ArrayList<RoleItem> roles = result.getRoles();
                if (roles != null) {
                    roles.removeAll(currentRoles);
                    grid.setData(UserClientUtils.copyRoles(roles));
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

        IButton add = new IButton(lang.addRole());
        add.setExtraSpace(5);
        add.addClickHandler(new ClickHandler() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onClick(ClickEvent event) {

                final ModalWindow mw = new ModalWindow(grid);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);

                ArrayList<RoleItem> toAdd = new ArrayList<RoleItem>();
                for (ListGridRecord rec : grid.getSelectedRecords()) {
                    toAdd.add(new RoleItem(Long.parseLong(userId), rec
                            .getAttributeAsString(Constants.ATTR_NAME), null));
                }

                PutRemoveUserRolesAction putRolesAction = new PutRemoveUserRolesAction(toAdd, true);
                dispatcher.execute(putRolesAction, new DispatchCallback<PutRemoveUserRolesResult>() {

                    @Override
                    public void callback(PutRemoveUserRolesResult result) {
                        if (result.isSuccessful()) {
                            afterAddAction();
                        } else {
                            SC.warn(lang.operationFailed());
                        }
                        mw.hide();
                        hide();
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
        });

        IButton cancel = new IButton(lang.cancel());
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        HLayout buttonsLayout = new HLayout(10);
        buttonsLayout.setAlign(Alignment.RIGHT);
        buttonsLayout.addMember(add);
        buttonsLayout.addMember(cancel);
        buttonsLayout.setMargin(10);

        addItem(getImgLayout());
        addItem(grid);
        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();

    }

    private HLayout getImgLayout() {

        HLayout imgLayout = new HLayout(2);
        imgLayout.setAlign(Alignment.CENTER);
        imgLayout.addMember(getCreateRoleButton());
        imgLayout.addMember(getRemoveRoleButton());
        imgLayout.setHeight(10);

        return imgLayout;
    }

    /**
     * Gets the adds the right button.
     * 
     * @return the adds the right button
     */
    private IButton getCreateRoleButton() {
        IButton create = new IButton();
        create.setTitle(lang.createRole());
        create.setShowTitle(true);
        create.setIcon("icons/16/add.png");
        create.setShowRollOver(true);
        create.setShowDisabled(true);
        create.setShowDown(true);
        create.setWidth(180);
        create.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                new CreateNewRoleWindow(eventBus, dispatcher, lang) {

                    @Override
                    protected void afterAddAction(ArrayList<RoleItem> rightList) {
                        RecordList currRoles = grid.getRecordList();
                        currRoles.addList(UserClientUtils.copyRoles(rightList));
                        grid.setData(currRoles);
                    }
                };
            }
        });

        return create;
    }

    /**
     * Gets the removes the right button.
     * 
     * @return the removes the right button
     */
    private IButton getRemoveRoleButton() {
        IButton remove = new IButton();
        remove.setTitle(lang.removeRole());
        remove.setShowTitle(true);
        remove.setIcon("icons/16/remove.png");
        remove.setShowRollOver(true);
        remove.setShowDisabled(true);
        remove.setShowDown(true);
        remove.setWidth(180);
        remove.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ModalWindow mw = new ModalWindow(grid);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);

                PutRemoveRolesAction removeRolesAction =
                        new PutRemoveRolesAction(UserClientUtils.copyToRoles(null, grid.getSelectedRecords()),
                                                 false);
                dispatcher.execute(removeRolesAction, new DispatchCallback<PutRemoveRolesResult>() {

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void callback(PutRemoveRolesResult result) {
                        if (result.isSuccessful()) {
                            grid.removeSelectedData();
                            mw.hide();
                        } else {
                            SC.warn(lang.operationFailed());
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
        });

        return remove;
    }

    /**
     * After add action.
     */
    protected abstract void afterAddAction();

}
