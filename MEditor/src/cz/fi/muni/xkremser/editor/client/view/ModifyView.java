package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailFormatter;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;

public class ModifyView extends ViewImpl implements ModifyPresenter.MyView {
	private TileGrid tileGrid;
	private final VLayout layout;
	private PopupPanel imagePopup;

	// private final GlassPanel glassPanel;

	public ModifyView() {
		layout = new VLayout();

		final DynamicForm filterForm = new DynamicForm();
		filterForm.setIsGroup(true);
		filterForm.setGroupTitle("Search");
		filterForm.setNumCols(1);
		filterForm.setAutoFocus(false);

		TextItem commonNameItem = new TextItem(Constants.ATTR_NAME);
		filterForm.setFields(commonNameItem);

		layout.addMember(filterForm);

		IButton print = new IButton("print");
		print.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				print();
			}
		});
		layout.addMember(print);

	}

	@Override
	public HasValue<String> getName() {
		return null;
	}

	@Override
	public HasClickHandlers getSend() {
		return null;
	}

	public void print() {
		Record[] data = tileGrid.getData();
		for (Record rec : data) {
			System.out.println(rec.getAttribute(Constants.ATTR_NAME));
		}

	}

	@Override
	public void setData(Record[] data) {
		if (tileGrid != null) {
			tileGrid.setData(data);
		}
	}

	@Override
	public void setWidgets(boolean tileGridVisible, DispatchAsync dispatcher) {

		if (tileGridVisible == true) {
			setTileGrid();
		}
	}

	private void setTileGrid() {
		tileGrid = new TileGrid();

		tileGrid.setTileWidth(194);
		tileGrid.setTileHeight(165);
		tileGrid.setHeight100();
		tileGrid.setWidth100();
		tileGrid.setCanDrag(true);
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setShowAllRecords(true);
		imagePopup = new PopupPanel(true);
		imagePopup.setGlassEnabled(true);
		imagePopup.setAnimationEnabled(true);
		tileGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				final Image full = new Image("images/full/" + tileGrid.getSelectedRecord().getAttribute(Constants.ATTR_UUID));
				full.setHeight("700px");
				Timer timer = new Timer() {
					@Override
					public void run() {
						imagePopup.setWidget(full);
						imagePopup.center();
					}
				};
				timer.schedule(150);
			}
		});
		DetailViewerField pictureField = new DetailViewerField(Constants.ATTR_PICTURE);
		pictureField.setType("image");
		pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
		pictureField.setImageWidth(100);
		pictureField.setImageHeight(130);

		DetailViewerField nameField = new DetailViewerField(Constants.ATTR_NAME);
		nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "Title: " + value;
			}
		});

		DetailViewerField descField = new DetailViewerField(Constants.ATTR_DESC);

		tileGrid.setFields(pictureField, nameField, descField);
		layout.addMember(tileGrid);
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}
}