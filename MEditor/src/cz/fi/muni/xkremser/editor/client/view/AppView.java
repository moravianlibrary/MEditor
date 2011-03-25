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
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter;
import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter.MyView;
import cz.fi.muni.xkremser.editor.client.view.AppView.MyUiHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class AppView.
 */
public class AppView extends ViewWithUiHandlers<MyUiHandlers> implements MyView {

	/**
	 * The Interface MyUiHandlers.
	 */
	public interface MyUiHandlers extends UiHandlers {

		/**
		 * Logout.
		 */
		void logout();
	}

	/** The left container. */
	private final Layout leftContainer;

	/** The top container. */
	private final Layout topContainer;

	/** The main container. */
	private final Layout mainContainer;

	/** The widget. */
	public VLayout widget;

	/** The username. */
	private final HTMLFlow username;

	private final HTMLFlow langSelection;

	/** The edit users. */
	private final HTMLFlow editUsers;

	private final LangConstants lang;

	// private HasWidgets mainContainer;

	/**
	 * Instantiates a new app view.
	 */
	@Inject
	public AppView(LangConstants lang) {
		this.lang = lang;
		widget = new VLayout();
		leftContainer = new VLayout();
		leftContainer.setWidth(275);
		leftContainer.setShowResizeBar(true);
		mainContainer = new VLayout(); // TODO: consider some panel
		widget.setWidth100();
		widget.setHeight100();
		widget.setLeaveScrollbarGap(true);
		widget.setOverflow(Overflow.AUTO);
		topContainer = new HLayout();
		topContainer.setWidth100();
		topContainer.setHeight(45);

		HTMLFlow logo = new HTMLFlow("<a href='/meditor'><img class='noFx' src='images/logo_bw.png' width='162' height='50' alt='logo'></a>");
		// Img logo = new Img("logo_bw.png", 140, 40);
		// Img logo = new Img("mzk_logo.gif", 283, 87);
		topContainer.addMember(logo);

		HLayout logged = new HLayout();
		username = new HTMLFlow();
		username.setWidth(150);
		username.setStyleName("username");
		username.setHeight(15);
		langSelection = new HTMLFlow();
		langSelection.setWidth(63);
		langSelection.setHeight(16);
		final boolean en = LocaleInfo.getCurrentLocale().getLocaleName() != null && LocaleInfo.getCurrentLocale().getLocaleName().startsWith("en");
		langSelection.setStyleName(en ? "langSelectionEN" : "langSelectionCZ");
		langSelection.setCursor(Cursor.HAND);
		langSelection.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				MEditor.langRefresh(en ? "cs_CZ" : "en_US");
			}
		});
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
	 * 
	 * @see com.gwtplatform.mvp.client.View#asWidget()
	 */
	@Override
	public Widget asWidget() {
		return widget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.ViewImpl#setInSlot(java.lang.Object,
	 * com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == AppPresenter.TYPE_SetMainContent) {
			setMainContent(content);
			// } else if (slot == AppPresenter.TYPE_SetTopContent) {
			// setTopContent(content);
		} else if (slot == AppPresenter.TYPE_SetLeftContent) {
			setLeftContent(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	/**
	 * Sets the main content.
	 * 
	 * @param content
	 *          the new main content
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
	 *          the new left content
	 */
	private void setLeftContent(Widget content) {
		// leftContainer.clear();
		if (content != null) {
			leftContainer.addMember(content);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.AppPresenter.MyView#getUsername
	 * ()
	 */
	@Override
	public HTMLFlow getUsername() {
		return username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.AppPresenter.MyView#getEditUsers
	 * ()
	 */
	@Override
	public HTMLFlow getEditUsers() {
		return editUsers;
	}

}