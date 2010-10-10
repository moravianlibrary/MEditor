package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
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
		VLayout main = new VLayout();
		leftContainer = new VLayout();
		mainContainer = new VLayout(); // TODO: consider some panel
		// main.setLayoutMargin(5);
		widget.setWidth100();
		widget.setHeight100();
		topContainer = new HLayout();
		topContainer.setWidth100();
		topContainer.setHeight(90);
		topContainer.setBorder("1px solid");
		widget.addMember(topContainer);

		HLayout underTop = new HLayout();
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
		} else if (slot == AppPresenter.TYPE_SetTopContent) {
			setLeftContent(content);
		} else if (slot == AppPresenter.TYPE_SetLeftContent) {
			setTopContent(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	private void setTopContent(Widget content) {
		mainContainer.clear();
		if (content != null) {
			mainContainer.addMember(content);
		}
	}

	private void setLeftContent(Widget content) {
		leftContainer.clear();
		if (content != null) {
			leftContainer.addMember(content);
		}
	}

	private void setMainContent(Widget content) {
		topContainer.clear();
		if (content != null) {
			topContainer.addMember(content);
		}
	}

}