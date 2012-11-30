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
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.UniversalListGrid;
import cz.mzk.editor.client.other.UserClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.rpc.RoleItem;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesResult;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public abstract class CreateNewRoleWindow
        extends UniversalWindow {

    private final LangConstants lang;
    private final UniversalListGrid rightsGrid;
    private final EventBus eventBus;
    private final DispatchAsync dispatcher;
    private final TextItem name;
    private IButton create;

    public CreateNewRoleWindow(EventBus eventBus, final DispatchAsync dispatcher, final LangConstants lang) {
        super(500, 500, lang.createRole(), eventBus, 50);
        this.lang = lang;
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;

        final DynamicForm form = new DynamicForm();
        form.setMargin(15);
        form.setHeight(150);

        name = new TextItem("name", lang.name());
        final TextItem description = new TextItem("description", lang.description());

        form.setFields(name, description);

        rightsGrid = new UniversalListGrid();
        rightsGrid.setMargin(10);
        //        rightsGrid.setHeight(350);
        //        rightsGrid.setWidth100();

        ListGridField nameField =
                new ListGridField(Constants.ATTR_NAME, lang.name() + " " + lang.role().toLowerCase());
        ListGridField descField =
                new ListGridField(Constants.ATTR_DESC, lang.description() + " " + lang.role().toLowerCase());
        rightsGrid.setFields(nameField, descField);

        HLayout buttonsLayout = new HLayout(10);
        buttonsLayout.setAlign(Alignment.RIGHT);
        buttonsLayout.addMember(getCreateButton(description));
        buttonsLayout.addMember(getCancelButton());
        buttonsLayout.setMargin(10);

        addItem(form);
        addItem(getImgLayout());
        addItem(rightsGrid);
        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();
    }

    private void enableIfCondButton() {
        if (name.getValueAsString() != null && !"".equals(name.getValueAsString().trim())
                && rightsGrid.getRecords().length > 0) {
            create.setDisabled(false);
        } else {
            create.setDisabled(true);
        }
    }

    private IButton getCreateButton(final TextItem description) {
        create = new IButton();
        create.setTitle(lang.create());
        create.setDisabled(true);

        name.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                enableIfCondButton();
            }
        });

        create.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ModalWindow mw = new ModalWindow(CreateNewRoleWindow.this);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);

                final ArrayList<RoleItem> rightList = new ArrayList<RoleItem>(1);
                rightList.add(new RoleItem(name.getValueAsString(),
                                           description.getValueAsString(),
                                           UserClientUtils.copyToRights(rightsGrid.getRecords())));

                PutRemoveRolesAction putRoleAction = new PutRemoveRolesAction(rightList, true);
                dispatcher.execute(putRoleAction, new DispatchCallback<PutRemoveRolesResult>() {

                    @Override
                    public void callback(PutRemoveRolesResult result) {
                        if (result.isSuccessful()) {
                            afterAddAction(rightList);
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

        return create;
    }

    private IButton getCancelButton() {
        IButton cancel = new IButton();
        cancel.setTitle(lang.cancel());
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        return cancel;
    }

    private HLayout getImgLayout() {

        HLayout imgLayout = new HLayout(2);
        imgLayout.setAlign(Alignment.CENTER);
        imgLayout.addMember(getAddRightButton());
        imgLayout.addMember(getRemoveRightButton());
        imgLayout.setHeight(10);

        return imgLayout;
    }

    /**
     * Gets the adds the right button.
     * 
     * @return the adds the right button
     */
    private IButton getAddRightButton() {
        IButton add = new IButton();
        add.setTitle(lang.addRight());
        add.setShowTitle(true);
        add.setIcon("icons/16/add.png");
        add.setShowRollOver(true);
        add.setShowDisabled(true);
        add.setShowDown(true);
        add.setWidth(180);
        add.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final List<ListGridRecord> currentRights =
                        new ArrayList<ListGridRecord>(Arrays.asList(rightsGrid.getRecords()));
                new AddRightsWindow(null, eventBus, lang, dispatcher, UserClientUtils.copyToRights(rightsGrid
                        .getRecords())) {

                    @Override
                    protected void afterAddAction() {
                        currentRights.addAll(Arrays.asList(getGrid().getSelectedRecords()));
                        ListGridRecord[] records = new ListGridRecord[currentRights.size()];
                        currentRights.toArray(records);
                        rightsGrid.setData(records);
                        enableIfCondButton();
                    }
                };
            }
        });

        return add;
    }

    /**
     * Gets the removes the right button.
     * 
     * @return the removes the right button
     */
    private IButton getRemoveRightButton() {
        IButton remove = new IButton();
        remove.setTitle(lang.remove() + " " + lang.right().toLowerCase());
        remove.setShowTitle(true);
        remove.setIcon("icons/16/remove.png");
        remove.setShowRollOver(true);
        remove.setShowDisabled(true);
        remove.setShowDown(true);
        remove.setWidth(180);
        remove.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                rightsGrid.removeSelectedData();
            }
        });

        return remove;
    }

    /**
     * After add action.
     * 
     * @param rightList
     */
    protected abstract void afterAddAction(ArrayList<RoleItem> rightList);
}
