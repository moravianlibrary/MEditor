/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.presenter.AppPresenter;
import cz.mzk.editor.client.presenter.AppPresenter.MyView;
import cz.mzk.editor.client.uihandlers.MyUiHandlers;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.LangSelectionHTMLFlow;

// TODO: Auto-generated Javadoc
/**
 * The Class AppView.
 */
public class AppView
        extends ViewWithUiHandlers<MyUiHandlers>
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

    /** The edit users. */
    private final HTMLFlow editUsers;

    @SuppressWarnings("unused")
    private final LangConstants lang;

    private Window winModal;

    private IsWidget leftWidget;

    // private HasWidgets mainContainer;

    /**
     * Instantiates a new app view.
     */
    @Inject
    public AppView(final LangConstants lang) {
        this.lang = lang;
        widget = new VLayout();
        leftContainer = new VLayout();
        leftContainer.setWidth(275);
        leftContainer.setShowResizeBar(true);
        mainContainer = new VLayout();
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
                AppView.isEn = isEn;
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
                winModal = new Window();
                winModal.setWidth(600);
                winModal.setHeight(800);
                winModal.setTitle(lang.help());
                winModal.setShowMinimizeButton(false);
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.centerInPage();
                winModal.addCloseClickHandler(new CloseClickHandler() {

                    @Override
                    public void onCloseClick(CloseClickEvent event) {
                        escShortCut();
                    }
                });
                HTMLPane helpPane = new HTMLPane();
                helpPane.setPadding(15);
                helpPane.setContentsURL("./html/help_" + (isEn ? "en.html" : "cs.html"));
                helpPane.setContentsType(ContentsType.FRAGMENT);
                winModal.addItem(helpPane);
                winModal.show();
            }
        });
        logged.addMember(hotKeysFlow);

        editUsers = new HTMLFlow();
        logged.addMember(editUsers);
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
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == AppPresenter.TYPE_MEDIT_MAIN_CONTENT) {
            setMainContent(content);
            // setTopContent(content);
        } else if (slot == AppPresenter.TYPE_MEDIT_LEFT_CONTENT) {
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
    private void setMainContent(IsWidget content) {
        mainContainer.removeMembers(mainContainer.getMembers());
        if (content != null) {
            mainContainer.addMember(content.asWidget());
        }
    }

    /**
     * Sets the left content.
     * 
     * @param content
     *        the new left content
     * @param slot
     */
    private void setLeftContent(IsWidget content) {
        if (content != null) {
            if (leftWidget != null) {
                if (leftWidget != content) {
                    if (getUiHandlers().getDoPresenter().getView().asWidget() == content) {
                        getUiHandlers().getDoPresenter().onShowInputQueue();
                    }
                    leftContainer.removeMember(leftContainer.getMember(0));

                }
            }
            leftContainer.addMember(content.asWidget());
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

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.presenter.AppPresenter.MyView#getEditUsers ()
     */
    @Override
    public HTMLFlow getEditUsers() {
        return editUsers;
    }

    /**
     * Method for close currently displayed window
     */
    @Override
    public void escShortCut() {
        if (winModal != null) {
            winModal.hide();
            winModal = null;
        }
    }

    @Override
    public void changeMenuWidth(String width) {
        leftContainer.setWidth(width);
    }

}