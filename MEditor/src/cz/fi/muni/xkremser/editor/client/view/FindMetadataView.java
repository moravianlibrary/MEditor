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

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.presenter.FindMetadataPresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class FindMetadataView extends ViewImpl implements FindMetadataPresenter.MyView {

	/** The layout. */
	private final VStack layout;

	/** The check button. */
	private final IButton findButton;

	/** The form. */
	private final DynamicForm form;

	/** The uuid field. */
	private final TextItem searchValue;

	private final LangConstants lang;

	// @Inject
	// public void setLang(LangConstants lang) {
	// this.lang = lang;
	// }

	/**
	 * Instantiates a new home view.
	 */
	@Inject
	public FindMetadataView(LangConstants lang) {
		this.lang = lang;
		layout = new VStack();
		layout.setHeight100();
		layout.setPadding(15);

		findButton = new IButton(lang.find());
		findButton.setAutoFit(true);
		findButton.setExtraSpace(60);
		findButton.setStylePrimaryName("checkButton");

		HTMLFlow html1 = new HTMLFlow();
		html1.setContents("<h1>" + lang.findMetadata() + "</h1>");
		html1.setExtraSpace(20);

		searchValue = new TextItem();
		searchValue.setTitle("PID");
		searchValue.setHint("<nobr>" + lang.findHint() + "</nobr>");

		form = new DynamicForm();
		form.setWidth(300);
		form.setFields(searchValue);

		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);
		hLayout.addMember(form);
		hLayout.addMember(findButton);

		layout.addMember(html1);
		layout.addMember(hLayout);

	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 * 
	 * @return the widget
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#
	 * getCheckAvailability()
	 */
	@Override
	public HasClickHandlers getCheckAvailability() {
		return findButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getOpen()
	 */
	@Override
	public IButton getFind() {
		return findButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getForm()
	 */
	@Override
	public DynamicForm getForm() {
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getUuid()
	 */
	@Override
	public TextItem getCode() {
		return searchValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getUuidItem
	 * ()
	 */
	@Override
	public HasChangedHandlers getUuidItem() {
		return searchValue;
	}

	@Override
	public void setURLs(String fedoraUrl, String krameriusUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoading() {
		// TODO Auto-generated method stub

	}

}