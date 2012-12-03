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

import java.util.LinkedHashMap;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.shared.rpc.UserInfoItem;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoAction;
import cz.mzk.editor.shared.rpc.action.GetUsersInfoResult;

/**
 * @author Matous Jobanek
 * @version Dec 3, 2012
 */
public class UserSelect
        extends SelectItem {

    /**
     * 
     */
    public UserSelect(String title, DispatchAsync dispatcher) {
        super("users", title);

        //      TODO
        if (true) {
            GetUsersInfoAction getUsersAction = new GetUsersInfoAction();
            DispatchCallback<GetUsersInfoResult> usersCallback = new DispatchCallback<GetUsersInfoResult>() {

                @Override
                public void callback(GetUsersInfoResult result) {
                    LinkedHashMap<String, String> allUsers = new LinkedHashMap<String, String>();

                    for (UserInfoItem userItem : result.getItems()) {
                        allUsers.put(userItem.getId().toString(),
                                     userItem.getSurname() + " " + userItem.getName());
                    }
                    setValueMap(allUsers);
                }
            };
            dispatcher.execute(getUsersAction, usersCallback);
        }
    }
}
