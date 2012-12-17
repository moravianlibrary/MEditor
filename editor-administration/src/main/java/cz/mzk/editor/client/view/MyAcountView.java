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

import javax.inject.Inject;

import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.UserDetailLayout;
import cz.mzk.editor.client.presenter.MyAcountPresenter;
import cz.mzk.editor.client.uihandlers.MyAcountUiHandlers;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserResult;

/**
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class MyAcountView
        extends ViewWithUiHandlers<MyAcountUiHandlers>
        implements MyAcountPresenter.MyView {

    private final VStack mainLayout;

    @Inject
    public MyAcountView(final EventBus eventBus, final LangConstants lang, final DispatchAsync dispatcher) {
        this.mainLayout = new VStack();

        final ModalWindow mw = new ModalWindow(mainLayout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        dispatcher.execute(new GetLoggedUserAction(), new DispatchCallback<GetLoggedUserResult>() {

            @Override
            public void callback(GetLoggedUserResult result) {
                ListGridRecord userRec = new ListGridRecord();
                userRec.setAttribute(Constants.ATTR_NAME, result.getName());
                userRec.setAttribute(Constants.ATTR_USER_ID, result.getUserId());
                UserDetailLayout userDetailLayout =
                        new UserDetailLayout(null, userRec, null, lang, dispatcher, eventBus);
                //                userDetailLayout.setHeight("0%");
                userDetailLayout.setAlign(Alignment.CENTER);
                userDetailLayout.setAlign(VerticalAlignment.CENTER);
                userDetailLayout.setLayoutAlign(Alignment.CENTER);
                userDetailLayout.setLayoutAlign(VerticalAlignment.CENTER);

                mainLayout.addMember(userDetailLayout);
                mw.hide();
            }

            @Override
            public void callbackError(final Throwable cause) {
                super.callbackError(cause);
                mw.hide();
            }

        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
