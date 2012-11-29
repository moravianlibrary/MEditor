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
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens.ADMIN_MENU_BUTTONS;
import cz.mzk.editor.client.presenter.AdminMenuPresenter.MyView;
import cz.mzk.editor.client.uihandlers.AdminMenuUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent.MenuButtonClickedHandler;

/**
 * @author Matous Jobanek
 * @version Oct 8, 2012
 */
public class AdminMenuView
        extends ViewWithUiHandlers<AdminMenuUiHandlers>
        implements MyView {

    @SuppressWarnings("unused")
    private final LangConstants lang;
    private static EventBus eventBus;

    /** The layout. */
    private final VStack mainLayout;

    private static final class MenuButtonLayout
            extends VLayout {

        private IButton menuButton = null;

        /**
         * 
         */
        public MenuButtonLayout(final String buttonType, String title) {
            super();
            final VLayout buttonLayout = new VLayout(1);
            buttonLayout.setAlign(VerticalAlignment.CENTER);
            buttonLayout.setLayoutAlign(Alignment.LEFT);
            buttonLayout.setHeight(40);
            buttonLayout.setPadding(5);

            setAlign(VerticalAlignment.CENTER);
            setLayoutAlign(Alignment.LEFT);
            setHeight(50);
            setExtraSpace(20);

            menuButton = new IButton(title);

            buttonLayout.addMember(menuButton);

            HTMLFlow hr = new HTMLFlow();
            int vSize = 1;
            hr.setContents("<HR WIDTH=\"98%\" SIZE=\"" + vSize + "\">");
            hr.setHeight(vSize + "px");

            addMember(buttonLayout);
            addMember(hr);

            menuButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    eventBus.fireEvent(new MenuButtonClickedEvent(buttonType, true));
                }
            });

            eventBus.addHandler(MenuButtonClickedEvent.getType(), new MenuButtonClickedHandler() {

                @Override
                public void onMenuButtonClicked(MenuButtonClickedEvent event) {
                    if (event.getMenuButtonType() != buttonType) {
                        buttonLayout.setBackgroundColor("#ffffff");
                        menuButton.setDisabled(false);
                    } else {
                        buttonLayout.setBackgroundColor("#c7c7c7");
                        menuButton.setDisabled(true);
                    }
                    MenuButtonLayout.this.redraw();
                }
            });
        }
    }

    /**
     * 
     */
    @SuppressWarnings("static-access")
    @Inject
    public AdminMenuView(EventBus eventBus, LangConstants lang) {
        this.lang = lang;
        this.eventBus = eventBus;
        this.mainLayout = new VStack();
        mainLayout.setPadding(3);

        HTMLFlow menu = new HTMLFlow();
        menu.setContents(HtmlCode.title("Menu:", 3));
        menu.setExtraSpace(30);

        mainLayout.addMember(menu);
        mainLayout.addMember(new MenuButtonLayout(ADMIN_MENU_BUTTONS.MY_ACOUNT, lang.myAcountMenu()));
        mainLayout.addMember(new MenuButtonLayout(ADMIN_MENU_BUTTONS.HISTORY, lang.historyMenu()));
        mainLayout.addMember(new MenuButtonLayout(ADMIN_MENU_BUTTONS.STORED, lang.storedMenu()));
        mainLayout.addMember(new MenuButtonLayout(ADMIN_MENU_BUTTONS.STATISTIC, lang.statisticsMenu()));
        mainLayout.addMember(new MenuButtonLayout(ADMIN_MENU_BUTTONS.USERS, lang.users()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        // TODO Auto-generated method stub
        return null;
    }

}
