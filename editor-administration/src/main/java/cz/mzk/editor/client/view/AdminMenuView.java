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
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.NameTokens;
import cz.mzk.editor.client.NameTokens.ADMIN_MENU_BUTTONS;
import cz.mzk.editor.client.presenter.AdminMenuPresenter.MyView;
import cz.mzk.editor.client.uihandlers.AdminMenuUiHandlers;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.window.ModalWindow;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent;
import cz.mzk.editor.shared.event.MenuButtonClickedEvent.MenuButtonClickedHandler;

/**
 * @author Matous Jobanek
 * @version Oct 8, 2012
 */
public class AdminMenuView
        extends ViewWithUiHandlers<AdminMenuUiHandlers>
        implements MyView {

    private final LangConstants lang;
    private static EventBus eventBus;
    final ModalWindow mw;

    /** The layout. */
    private final VStack mainLayout;

    private static final class MenuButtonFlow
            extends HTMLFlow {

        /**
         * 
         */
        public MenuButtonFlow(final String buttonType, final String title) {
            super();
            setContents("<p class=\"a-btn\">" + "<span class=\"a-btn-text\">" + title + "</span>"
                    + "<span class=\"a-btn-icon-right\"><span></span></span>" + "</p>");

            setTitle(title);
            setEdgeOffset(5);
            setExtraSpace(10);

            addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    eventBus.fireEvent(new MenuButtonClickedEvent(buttonType, true));
                }
            });

            eventBus.addHandler(MenuButtonClickedEvent.getType(), new MenuButtonClickedHandler() {

                @Override
                public void onMenuButtonClicked(MenuButtonClickedEvent event) {
                    if (event.getMenuButtonType() != buttonType) {
                        setContents("<p class=\"a-btn\">" + "<span class=\"a-btn-text\">" + title + "</span>"
                                + "<span class=\"a-btn-icon-right\"><span></span></span>" + "</p>");
                    } else {
                        setContents("<p class=\"a-btn-selected\">" + "<span class=\"a-btn-text-selected\">"
                                + title + "</span>"
                                + "<span class=\"a-btn-icon-right-selected\"><span></span></span>" + "</p>");
                    }
                    MenuButtonFlow.this.redraw();
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

        mw = new ModalWindow(mainLayout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

    }

    @Override
    public void setButtons(boolean showStat, boolean showUsers) {

        mainLayout.addMember(new MenuButtonFlow(NameTokens.ADMIN_HOME, lang.home()));
        mainLayout.addMember(new MenuButtonFlow(ADMIN_MENU_BUTTONS.MY_ACOUNT, lang.myAcountMenu()));
        mainLayout.addMember(new MenuButtonFlow(ADMIN_MENU_BUTTONS.HISTORY, lang.historyMenu()));
        mainLayout.addMember(new MenuButtonFlow(ADMIN_MENU_BUTTONS.STORED_AND_LOCKS, lang.storedMenu() + "<br>"
                + (lang.and() + " " + lang.locks()).toLowerCase()));
        if (showStat)
            mainLayout.addMember(new MenuButtonFlow(ADMIN_MENU_BUTTONS.STATISTICS, lang.statisticsMenu()));
        if (showUsers) mainLayout.addMember(new MenuButtonFlow(ADMIN_MENU_BUTTONS.USERS, lang.users()));

        mw.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
