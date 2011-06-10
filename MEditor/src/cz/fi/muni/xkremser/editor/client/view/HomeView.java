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
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class HomeView extends ViewImpl implements HomePresenter.MyView {

	/** The Constant LOADING. */
	private static final int LOADING = -1;

	/** The Constant NOT_AVAIL. */
	private static final int NOT_AVAIL = 0;

	/** The Constant AVAIL. */
	private static final int AVAIL = 1;

	/** The fedora url. */
	private volatile String fedoraUrl = "#";

	/** The kramerius url. */
	private volatile String krameriusUrl = "#";

	/** The fedora status. */
	private volatile int fedoraStatus = LOADING;

	/** The kramerius status. */
	private volatile int krameriusStatus = LOADING;

	/** The layout. */
	private final VStack layout;

	/** The check button. */
	private final IButton checkButton;

	/** The status. */
	private final HTMLFlow status;

	/** The form. */
	private final DynamicForm form;

	/** The open. */
	private final IButton open;

	/** The uuid field. */
	private final TextItem uuidField;

	private final LangConstants lang;

	// @Inject
	// public void setLang(LangConstants lang) {
	// this.lang = lang;
	// }

	/**
	 * Instantiates a new home view.
	 */
	@Inject
	public HomeView(LangConstants lang) {
		this.lang = lang;
		layout = new VStack();
		layout.setHeight100();
		layout.setPadding(15);
		HTMLFlow html1 = new HTMLFlow();
		html1.setContents(lang.introduction());
		html1.setExtraSpace(15);

		status = new HTMLFlow(getStatusString());

		checkButton = new IButton(lang.checkAvailability());
		checkButton.setAutoFit(true);
		checkButton.setExtraSpace(60);
		checkButton.setStylePrimaryName("checkButton");

		HTMLFlow html2 = new HTMLFlow();
		html2.setContents("<h2>" + lang.openDigitalObject() + "</h2>");
		html2.setExtraSpace(30);

		DataSource dataSource = new DataSource();
		dataSource.setID("regularExpression");

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator.setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

		uuidField = new TextItem();
		uuidField.setTitle("PID");
		uuidField.setHint("<nobr>" + lang.withoutPrefix() + "</nobr>");
		uuidField.setValidators(regExpValidator);

		form = new DynamicForm();
		form.setWidth(300);
		form.setFields(uuidField);

		open = new IButton();
		open.setTitle(lang.open());
		open.setDisabled(true);
		open.setAutoShowParent(false);

		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);
		hLayout.addMember(form);
		hLayout.addMember(open);

		HTMLFlow html3 = new HTMLFlow();
		// html3.setAlign(Alignment.RIGHT);
		html3.setHeight("*");
		html3.setLayoutAlign(VerticalAlignment.BOTTOM);
		html3.setContents(lang.credits());
		html3.setHeight(20);

		layout.addMember(html1);
		layout.addMember(status);
		layout.addMember(checkButton);
		layout.addMember(html2);
		layout.addMember(hLayout);
		layout.addMember(html3);
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
		return checkButton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#refreshFedora
	 * (boolean, java.lang.String)
	 */
	@Override
	public void refreshFedora(boolean fedoraRunning, String url) {
		this.fedoraStatus = fedoraRunning ? AVAIL : NOT_AVAIL;
		if (url != null)
			this.fedoraUrl = url;
		status.setContents(getStatusString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#
	 * refreshKramerius(boolean, java.lang.String)
	 */
	@Override
	public void refreshKramerius(boolean krameriusRunning, String url) {
		this.krameriusStatus = krameriusRunning ? AVAIL : NOT_AVAIL;
		if (url != null)
			this.krameriusUrl = url;
		status.setContents(getStatusString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#setURLs
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void setURLs(String fedoraUrl, String krameriusUrl) {
		if (fedoraUrl != null)
			this.fedoraUrl = fedoraUrl;
		if (krameriusUrl != null)
			this.krameriusUrl = krameriusUrl;
	}

	/**
	 * Gets the status string.
	 * 
	 * @return the status string
	 */
	private String getStatusString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul><li>Fedora (<a href='").append(fedoraUrl == null ? "" : fedoraUrl).append("'>link</a>) <span class='");
		switch (fedoraStatus) {
			case (LOADING):
				sb.append("loading'>" + lang.loading() + "...");
			break;
			case (AVAIL):
				sb.append("greenFont'>" + lang.isRunning());
			break;
			case (NOT_AVAIL):
				sb.append("redFont'>" + lang.isNotRunning());
			break;
		}
		sb.append("</span></li><li>Kramerius 4 (<a href='").append(krameriusUrl == null ? "" : krameriusUrl).append("'>link</a>) <span class='");
		switch (krameriusStatus) {
			case (LOADING):
				sb.append("loading'>" + lang.loading() + "...");
			break;
			case (AVAIL):
				sb.append("greenFont'>" + lang.isRunning());
			break;
			case (NOT_AVAIL):
				sb.append("redFont'>" + lang.isNotRunning());
			break;
		}
		sb.append("</span></li></ul>");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#setLoading
	 * ()
	 */
	@Override
	public void setLoading() {
		this.krameriusStatus = LOADING;
		this.fedoraStatus = LOADING;
		status.setContents(getStatusString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.presenter.HomePresenter.MyView#getOpen()
	 */
	@Override
	public IButton getOpen() {
		return open;
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
	public TextItem getUuid() {
		return uuidField;
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
		return uuidField;
	}

}