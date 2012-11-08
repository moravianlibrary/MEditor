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

package cz.mzk.editor.client.view;

import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.presenter.HistoryPresenter;
import cz.mzk.editor.client.uihandlers.HistoryUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.HistoryDays;
import cz.mzk.editor.client.view.other.HistoryItems;
import cz.mzk.editor.shared.rpc.HistoryItem;
import cz.mzk.editor.shared.rpc.HistoryItemInfo;
import cz.mzk.editor.shared.rpc.UserInfoItem;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class HistoryView
        extends ViewWithUiHandlers<HistoryUiHandlers>
        implements HistoryPresenter.MyView {

    private final EventBus eventBus;
    private final LangConstants lang;
    private final VStack mainLayout;
    private final HistoryDays historyDays;
    private final DispatchAsync dispatcher;
    private final HistoryItems historyItems;
    private SelectItem users;
    private final HTMLFlow title;

    @Inject
    public HistoryView(EventBus eventBus, final LangConstants lang, DispatchAsync dispatcher) {
        this.eventBus = eventBus;
        this.lang = lang;
        this.dispatcher = dispatcher;

        this.mainLayout = new VStack();

        title = new HTMLFlow(HtmlCode.title(lang.my() + " " + lang.historyMenu(), 2));
        Layout titleLayout = new Layout();
        titleLayout.addMember(title);
        titleLayout.setAlign(VerticalAlignment.TOP);
        titleLayout.setHeight(30);
        titleLayout.setMargin(10);

        mainLayout.addMember(titleLayout);
        setUserSelect();

        historyDays = new HistoryDays(lang, dispatcher, eventBus);
        historyItems = new HistoryItems(lang, eventBus);

        HLayout historyLayout = new HLayout(2);
        historyLayout.setAnimateMembers(true);
        historyLayout.setHeight("90%");
        historyLayout.setMargin(10);

        historyLayout.addMember(historyDays);
        historyLayout.addMember(historyItems);

        mainLayout.addMember(historyLayout);

    }

    private void setUserSelect() {
        //        TODO
        if (true) {
            users = new SelectItem("users", lang.users());
            GetUsersInfoAction getUsersAction = new GetUsersInfoAction();
            DispatchCallback<GetUsersInfoResult> usersCallback = new DispatchCallback<GetUsersInfoResult>() {

                @Override
                public void callback(GetUsersInfoResult result) {
                    LinkedHashMap<String, String> allUsers = new LinkedHashMap<String, String>();
                    for (UserInfoItem userItem : result.getItems()) {
                        allUsers.put(userItem.getId().toString(),
                                     userItem.getSurname() + " " + userItem.getName());
                    }
                    users.setValueMap(allUsers);
                }
            };

            users.addChangedHandler(new ChangedHandler() {

                @Override
                public void onChanged(ChangedEvent event) {
                    historyItems.removeAllData();
                    historyDays.getUserHistory(Long.parseLong(event.getValue().toString()));
                    title.setContents(HtmlCode.title(lang.historyMenu() + " " + lang.ofUser(), 2));
                }
            });

            dispatcher.execute(getUsersAction, usersCallback);
            DynamicForm usersForm = new DynamicForm();
            usersForm.setItems(users);

            mainLayout.addMember(usersForm);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHistoryItems(List<HistoryItem> hItems) {
        historyItems.setHistoryItems(hItems);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showHistoryItemInfo(HistoryItemInfo historyItemInfo, HistoryItem eventHistoryItem) {
        historyItems.showHistoryItemInfo(historyItemInfo, eventHistoryItem);
    }

}
