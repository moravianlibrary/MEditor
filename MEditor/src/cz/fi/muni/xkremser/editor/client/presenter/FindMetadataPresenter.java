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
package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindMetadataResult;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class FindMetadataPresenter extends Presenter<FindMetadataPresenter.MyView, FindMetadataPresenter.MyProxy> {

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {

		TextItem getCode();

		SelectItem getFindBy();

		ButtonItem getFind();

		void refreshData(ListGridRecord[] data);

		void showProgress(boolean show, boolean msg);
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.FIND_METADATA)
	public interface MyProxy extends ProxyPlace<FindMetadataPresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	/** The place manager. */
	private final PlaceManager placeManager;

	private final LangConstants lang;

	private String code = null;

	/**
	 * Instantiates a new home presenter.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param view
	 *          the view
	 * @param proxy
	 *          the proxy
	 * @param leftPresenter
	 *          the left presenter
	 * @param dispatcher
	 *          the dispatcher
	 * @param placeManager
	 *          the place manager
	 */
	@Inject
	public FindMetadataPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final LangConstants lang) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		this.lang = lang;
		bind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();
		getView().getFind().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String finder = (String) getView().getFindBy().getValue();
				Constants.SEARCH_FIELD findBy = null;
				if (finder == null || finder.isEmpty()) {
					return;
				} else if (finder.equals(Constants.SYSNO)) {
					findBy = Constants.SEARCH_FIELD.SYSNO;
				} else if (finder.equals(lang.fbarcode())) {
					findBy = Constants.SEARCH_FIELD.BAR;
				} else if (finder.equals(lang.ftitle())) {
					findBy = Constants.SEARCH_FIELD.TITLE;
				}
				if (findBy != null) {
					findMetadata(findBy, (String) getView().getCode().getValue());
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
	}

	/*
	 * (non-Javadoc) n
	 * 
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		code = request.getParameter(Constants.URL_PARAM_CODE, null);
		getView().getCode().setValue(code);
		findMetadata(Constants.SEARCH_FIELD.SYSNO, code);
	}

	private void findMetadata(Constants.SEARCH_FIELD field, String code) {
		dispatcher.execute(new FindMetadataAction(field, code), new DispatchCallback<FindMetadataResult>() {
			@Override
			public void callback(FindMetadataResult result) {
				getView().showProgress(true, true);
				List<DublinCore> list = result.getOutput();
				if (list != null && list.size() != 0) {
					ListGridRecord[] data = new ListGridRecord[list.size()];
					for (int i = 0; i < list.size(); i++) {
						data[i] = list.get(i).toRecord();
					}
					getView().refreshData(data);
				} else {
					getView().refreshData(null);
				}
				getView().showProgress(false, false);
			}

			@Override
			public void callbackError(Throwable t) {
				getView().showProgress(false, false);
				SC.warn(t.getMessage());
			}
		});
		getView().showProgress(true, false);
	}
}