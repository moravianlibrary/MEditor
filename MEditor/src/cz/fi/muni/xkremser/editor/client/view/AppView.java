package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.presenter.AppPresenter;

public class AppView extends ViewWithUiHandlers<AppView.MyUiHandlers> implements AppPresenter.MyView {

	public interface MyUiHandlers extends UiHandlers {
	}

	private final Layout leftContainer;
	private final Layout topContainer;
	private final Layout mainContainer;
	public VLayout widget;

	// private HasWidgets mainContainer;

	public AppView() {
		widget = new VLayout();
		leftContainer = new VLayout();
		leftContainer.setWidth(275);
		leftContainer.setShowResizeBar(true);
		mainContainer = new VLayout(); // TODO: consider some panel
		mainContainer.setOverflow(Overflow.AUTO);
		widget.setWidth("99%");
		// widget.setWidth("auto");
		widget.setHeight("98%");
		// widget.setAutoHeight();
		widget.setOverflow(Overflow.AUTO);
		topContainer = new HLayout();
		topContainer.setWidth100();
		topContainer.setHeight(90);
		// topContainer.setBorder("1px solid");
		Img logo = new Img("mzk_logo.gif", 283, 87);
		topContainer.addMember(logo);
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

	@Override
	public Widget asWidget() {
		return widget;
	}

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

	private void setMainContent(Widget content) {
		mainContainer.clear();
		if (content != null) {
			mainContainer.addMember(content);
		}
	}

	private void setLeftContent(Widget content) {
		// leftContainer.clear();
		if (content != null) {
			leftContainer.addMember(content);
		}
	}

	// private void setTopContent(Widget content) {
	// topContainer.clear();
	// if (content != null) {
	// topContainer.addMember(content);
	// }
	// }

}