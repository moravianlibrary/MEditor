/*
 * Metadata Editor
 * @author Matous Jobanek
 * 
 * 
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

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.AdminPresenter;
import cz.mzk.editor.client.presenter.AdminPresenter.MyView;
import cz.mzk.editor.client.uihandlers.AdminUiHandlers;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.LangSelectionHTMLFlow;
import cz.mzk.editor.client.view.window.UniversalWindow;

// TODO: Auto-generated Javadoc
/**
 * The Class AppView.
 */
public class AdminView
        extends ViewWithUiHandlers<AdminUiHandlers>
        implements MyView {

    /** The is en. */
    private static boolean isEn;

    /** The left container. */
    private final Layout leftContainer;

    /** The top container. */
    private final Layout topContainer;

    /** The main container. */
    private final Layout mainContainer;

    /** The widget. */
    private final VLayout widget;

    /** The username. */
    private final HTMLFlow username;

    private final HTMLFlow langSelection;

    @SuppressWarnings("unused")
    private final LangConstants lang;

    private Window winModal;

    private Widget leftWidget;

    @SuppressWarnings("unused")
    private final EventBus eventBus;

    // private HasWidgets mainContainer;

    /**
     * Instantiates a new app view.
     */
    @Inject
    public AdminView(final LangConstants lang, final EventBus eventBus) {
        this.lang = lang;
        this.eventBus = eventBus;
        widget = new VLayout();
        leftContainer = new VLayout();
        leftContainer.setWidth("20%");
        leftContainer.setShowResizeBar(true);
        mainContainer = new VLayout();
        mainContainer.setWidth("*");
        widget.setWidth100();
        widget.setHeight("98%");
        widget.setLeaveScrollbarGap(true);
        widget.setOverflow(Overflow.AUTO);
        topContainer = new HLayout();
        topContainer.setWidth100();
        topContainer.setHeight(45);

        HTMLFlow logo = new HTMLFlow(Constants.LOGO_HTML);

        topContainer.addMember(logo);

        HLayout logged = new HLayout();
        username = new HTMLFlow();
        username.setWidth(150);
        username.setStyleName("username");
        username.setHeight(15);
        isEn =
                LocaleInfo.getCurrentLocale().getLocaleName() != null
                        && LocaleInfo.getCurrentLocale().getLocaleName().startsWith("en");
        langSelection = new LangSelectionHTMLFlow() {

            @Override
            protected void afterChangeAction(boolean isEn) {
                AdminView.isEn = isEn;
                ClientUtils.langRefresh(isEn ? "cs_CZ" : "en_US");
            }
        };
        HTMLFlow anchor = new HTMLFlow(lang.logout());
        anchor.setCursor(Cursor.HAND);
        anchor.setWidth(60);
        anchor.setHeight(15);
        anchor.setStyleName("pseudolink");
        anchor.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                getUiHandlers().logout();
            }
        });

        HTMLFlow hotKeysFlow = new HTMLFlow();
        hotKeysFlow.setContents(lang.help());
        hotKeysFlow.setCursor(Cursor.HAND);
        hotKeysFlow.setWidth(80);
        hotKeysFlow.setHeight(15);
        hotKeysFlow.setStyleName("pseudolink");
        hotKeysFlow.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                winModal = new UniversalWindow(800, 600, lang.help(), eventBus, 50);
                HTMLPane helpPane = new HTMLPane();
                helpPane.setPadding(15);
                helpPane.setContentsURL("./help_" + (isEn ? "en.html" : "cs.html"));
                helpPane.setContentsType(ContentsType.FRAGMENT);
                winModal.addItem(helpPane);
                winModal.centerInPage();
                winModal.focus();
                winModal.show();
            }
        });
        logged.addMember(hotKeysFlow);

        logged.addMember(username);
        logged.addMember(langSelection);
        logged.addMember(anchor);
        logged.setAlign(Alignment.RIGHT);
        topContainer.addMember(logged);

        widget.addMember(topContainer);

        HLayout underTop = new HLayout();
        underTop.setAutoWidth();
        underTop.setAutoHeight();
        underTop.setOverflow(Overflow.AUTO);
        underTop.setWidth100();
        underTop.setHeight100();
        widget.addMember(underTop);

        underTop.addMember(leftContainer);
        underTop.addMember(mainContainer);
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.View#asWidget()
     */
    @Override
    public Widget asWidget() {
        return widget;
    }

    /*
     * (non-Javadoc)
     * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object,
     * com.google.gwt.user.client.ui.Widget)
     */
    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == AdminPresenter.TYPE_ADMIN_MAIN_CONTENT) {
            setMainContent(content);
        } else if (slot == AdminPresenter.TYPE_ADMIN_LEFT_CONTENT) {
            setLeftContent(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    /**
     * Sets the main content.
     * 
     * @param content
     *        the new main content
     */
    private void setMainContent(Widget content) {
        mainContainer.removeMembers(mainContainer.getMembers());
        if (content != null) {
            mainContainer.addMember(content);
        }
    }

    /**
     * Sets the left content.
     * 
     * @param content
     *        the new left content
     * @param slot
     */
    private void setLeftContent(Widget content) {
        if (content != null) {
            if (leftWidget != null) {
                if (leftWidget != content) {
                    if (getUiHandlers().getLeftPresenter().getView().asWidget() == content) {
                        //                        getUiHandlers().getLeftPresenter().onShowInputQueue();
                    }
                    leftContainer.removeMember(leftContainer.getMember(0));

                }
            }
            leftContainer.addMember(content);
            leftWidget = content;
        }
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.AppPresenter.MyView#getUsername ()
     */
    @Override
    public HTMLFlow getUsername() {
        return username;
    }

    @Override
    public void changeMenuWidth(String width) {
        leftContainer.setWidth(width);
    }

}