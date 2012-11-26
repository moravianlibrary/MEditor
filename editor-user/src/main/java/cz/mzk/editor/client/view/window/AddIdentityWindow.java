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

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityResult;

/**
 * @author Matous Jobanek
 * @version Nov 26, 2012
 */
public abstract class AddIdentityWindow
        extends UniversalWindow {

    private final LangConstants lang;

    /**
     * Instantiates a new adds the identity window.
     * 
     * @param title
     *        the title
     * @param eventBus
     *        the event bus
     * @param lang
     *        the lang
     */
    public AddIdentityWindow(String title,
                             EventBus eventBus,
                             LangConstants lang,
                             final String userId,
                             final DispatchAsync dispatcher,
                             final USER_IDENTITY_TYPES type) {
        super(200, 550, title, eventBus, 50);
        this.lang = lang;

        final DynamicForm form = new DynamicForm();
        form.setMargin(15);
        form.setWidth(500);
        form.setHeight(150);

        final TextItem identity = new TextItem(Constants.ATTR_IDENTITY, lang.openId());
        identity.setWidth(320);
        ButtonItem add = new ButtonItem();
        add.setEndRow(false);
        add.setTitle(lang.addIdentity());
        add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                final String identityValue = (String) identity.getValue();
                if (identityValue != null && !"".equals(identityValue.trim())) {
                    ArrayList<String> ident = new ArrayList<String>();
                    ident.add(identityValue);
                    PutUserIdentityAction putIdentityAction =
                            new PutUserIdentityAction(new UserIdentity(ident, type, Long.parseLong(userId)));
                    dispatcher.execute(putIdentityAction, new DispatchCallback<PutUserIdentityResult>() {

                        @Override
                        public void callback(PutUserIdentityResult result) {
                            if (result.isSuccessful()) {
                                //                                ListGridRecord record = new ListGridRecord();
                                //                                record.setAttribute(Constants.ATTR_IDENTITY, identityValue);
                                //                                ListGridRecord[] previousData = getView().getUserIdentityGrid().getRecords();
                                //                                ListGridRecord[] newData = new ListGridRecord[previousData.length + 1];
                                //                                System.arraycopy(previousData, 0, newData, 0, previousData.length);
                                //                                newData[previousData.length] = record;
                                //                                getView().getUserIdentityGrid().setData(newData);
                                afterSuccessAction();
                                hide();
                            } else {
                                SC.warn("The insertion was not successful. The identity can be already used, please change it a try again.");
                            }
                        }

                    });
                }
            }
        });

        ButtonItem cancel = new ButtonItem();
        cancel.setEndRow(false);
        cancel.setTitle(lang.cancel());
        cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                destroy();
            }
        });

        form.setFields(identity, add, cancel);
        addItem(form);
        centerInPage();
        show();
    }

    protected abstract void afterSuccessAction();
}
