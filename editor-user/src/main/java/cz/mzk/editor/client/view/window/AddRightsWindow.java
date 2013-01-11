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
import java.util.Map;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.UniversalListGrid;
import cz.mzk.editor.client.other.UserClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.shared.rpc.action.CheckRightsAction;
import cz.mzk.editor.shared.rpc.action.CheckRightsResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsResult;

// TODO: Auto-generated Javadoc
/**
 * The Class AddRightsWindow.
 * 
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
public abstract class AddRightsWindow
        extends UniversalWindow {

    private final UniversalListGrid grid;
    private final LangConstants lang;

    /**
     * Instantiates a new adds the rights window.
     * 
     * @param userId
     *        the user id
     * @param eventBus
     *        the event bus
     * @param lang
     *        the lang
     * @param dispatcher
     *        the dispatcher
     * @param currentRights
     *        the current rights
     */
    public AddRightsWindow(final String userId,
                           EventBus eventBus,
                           final LangConstants lang,
                           final DispatchAsync dispatcher,
                           List<EDITOR_RIGHTS> currentRights) {
        super(500, 600, lang.roles(), eventBus, 20);
        this.lang = lang;

        grid = new UniversalListGrid();
        grid.setMargin(10);
        grid.setHeight(400);
        grid.setWidth100();

        ListGridField nameField =
                new ListGridField(Constants.ATTR_NAME, lang.name() + " " + lang.role().toLowerCase());
        ListGridField descField =
                new ListGridField(Constants.ATTR_DESC, lang.description() + " " + lang.role().toLowerCase());
        grid.setFields(nameField, descField);
        List<EDITOR_RIGHTS> sysRights =
                new ArrayList<Constants.EDITOR_RIGHTS>(Arrays.asList(EDITOR_RIGHTS.values()));
        sysRights.removeAll(currentRights);
        grid.setData(UserClientUtils.copyRights(sysRights));

        final ModalWindow mw = new ModalWindow(grid);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(new CheckRightsAction(), new DispatchCallback<CheckRightsResult>() {

            @Override
            public void callback(CheckRightsResult result) {
                StringBuffer sb = new StringBuffer("");
                appendNotRemRights(sb, result.getRightsRefByRole(), true);
                appendNotRemRights(sb, result.getRightsRefByUser(), false);

                if (!"".equals(sb.toString())) {
                    sb.append("<br><br>").append(lang.resolveConflicts());

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

        IButton add = new IButton(lang.addRight());
        add.setExtraSpace(5);
        add.addClickHandler(new ClickHandler() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onClick(ClickEvent event) {

                if (userId != null) {

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
                } else {
                    afterAddAction();
                    hide();
                }
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

    private void appendNotRemRights(StringBuffer sb, Map<String, List<String>> ref, boolean isRole) {
        if (ref != null) {
            for (String right : ref.keySet()) {
                if ("".equals(sb.toString())) sb.append(lang.notRemRights()).append(":<br>");
                sb.append("<br>").append(right).append(" ").append(lang.referencedFrom()).append(" ")
                        .append(isRole ? lang.role() : lang.ofUser()).append(":");

                for (String role : ref.get(right)) {
                    sb.append("<br>").append(role);
                }
            }
        }
    }

    /**
     * @return the grid
     */
    protected UniversalListGrid getGrid() {
        return grid;
    }

    /**
     * After add action.
     */
    protected abstract void afterAddAction();
}
