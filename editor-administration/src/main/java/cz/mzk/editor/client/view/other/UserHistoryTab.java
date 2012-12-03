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

package cz.mzk.editor.client.view.other;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.HtmlCode;

/**
 * @author Matous Jobanek
 * @version Nov 9, 2012
 */
public class UserHistoryTab
        extends HistoryTab {

    private SelectItem users;

    /**
     * @param eventBus
     * @param lang
     * @param dispatcher
     */
    public UserHistoryTab(EventBus eventBus, LangConstants lang, DispatchAsync dispatcher) {
        super(eventBus, lang, dispatcher, HtmlCode.title(lang.my() + " " + lang.historyMenu().toLowerCase(),
                                                         2), new Long(-1), null, lang.historyMenu() + " "
                + lang.ofUser());
        setIcon("pieces/16/pawn_green.png");
    }

    private void setUserSelect() {
        users = new UserSelect(getLang().users(), getDispatcher());
        users.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                getHistoryItems().removeAllData();
                getHistoryDays().getHistory(Long.parseLong(event.getValue().toString()), null);
                getTitleFlow().setContents(HtmlCode.title(getLang().historyMenu() + " " + getLang().ofUser(),
                                                          2));
            }
        });
        DynamicForm usersForm = new DynamicForm();
        usersForm.setItems(users);

        getMainLayout().addMember(usersForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setSelection() {
        setUserSelect();
    }

}
