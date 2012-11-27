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

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.UserClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.shared.rpc.action.CheckRightsAction;
import cz.mzk.editor.shared.rpc.action.CheckRightsResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsResult;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public abstract class AddRightsWindow
        extends UniversalWindow {

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */
    public AddRightsWindow(String title,
                           final String userId,
                           EventBus eventBus,
                           LangConstants lang,
                           final DispatchAsync dispatcher) {
        super(300, 600, title, eventBus, 20);

        final ListGrid grid = new ListGrid();
        grid.setShowSortArrow(SortArrow.CORNER);
        grid.setShowAllRecords(true);
        grid.setCanSort(true);
        grid.setCanHover(true);
        grid.setHoverOpacity(75);
        grid.setHoverWidth(400);
        grid.setHoverStyle("interactImageHover");
        grid.setCanSelectText(true);
        grid.setCanDragSelectText(true);
        grid.setMargin(10);
        grid.setHeight(200);
        grid.setWidth100();

        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name() + " " + lang.role());
        ListGridField descField =
                new ListGridField(Constants.ATTR_DESC, lang.description() + " " + lang.role());
        grid.setFields(nameField, descField);
        grid.setData(UserClientUtils.copyRights(Arrays.asList(EDITOR_RIGHTS.values())));

        final ModalWindow mw = new ModalWindow(grid);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(new CheckRightsAction(), new DispatchCallback<CheckRightsResult>() {

            @Override
            public void callback(CheckRightsResult result) {
                if (result.getNotRemovedRights() != null && !result.getNotRemovedRights().isEmpty()) {
                    StringBuffer sb =
                            new StringBuffer("There are some unsupported rights in the database. Please contact the administrator. The problematic rights: ");
                    for (String right : result.getNotRemovedRights()) {
                        sb.append(right).append("<br>");
                    }
                    SC.warn(sb.toString());
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

        IButton add = new IButton(lang.addIdentity());
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

                ArrayList<String> toAdd = new ArrayList<String>();
                for (ListGridRecord rec : grid.getSelectedRecords()) {
                    toAdd.add(rec.getAttributeAsString(Constants.ATTR_NAME));
                }

                PutRemoveUserRightsAction putRightsAction =
                        new PutRemoveUserRightsAction(userId, toAdd, true);

                dispatcher.execute(putRightsAction, new DispatchCallback<PutRemoveUserRightsResult>() {

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void callback(PutRemoveUserRightsResult result) {
                        afterAddAction();
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

        addItem(grid);
        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();
    }

    protected abstract void afterAddAction();
}
