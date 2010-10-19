package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
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
	private final TileGrid tileGrid;
	private final VLayout layout;

	// private final GlassPanel glassPanel;

	public ModifyView() {
		layout = new VLayout();
		tileGrid = new TileGrid();

		tileGrid.setTileWidth(194);
		tileGrid.setTileHeight(165);
		tileGrid.setHeight100();
		tileGrid.setWidth100();
		tileGrid.setCanDrag(true);
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setShowAllRecords(true);
		// tileGrid.setData(CarData.getRecords());

		tileGrid.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				RootPanel.get().add(new ImagePopup().onInitialize());
				SC.say(tileGrid.getSelectedRecord().getAttribute("name"));
				// Hyperlink h = new Hyperlink(null, null);
				// h.setHTML("<a href='meditor/single.jpg' title='add a caption to title attribute / or leave blank' class='thickbox'>");
				// h.

			}
		});

		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		pictureField.setImageURLPrefix(Constants.SERVLET_THUMBNAIL_PREFIX + '/');
		pictureField.setImageWidth(100);
		pictureField.setImageHeight(130);

		DetailViewerField nameField = new DetailViewerField("name");
		nameField.setDetailFormatter(new DetailFormatter() {
			@Override
			public String format(Object value, Record record, DetailViewerField field) {
				return "Title: " + value;
			}
		});

		DetailViewerField priceField = new DetailViewerField("price");

		tileGrid.setFields(pictureField, nameField, priceField);
		layout.addMember(tileGrid);

		final DynamicForm filterForm = new DynamicForm();
		filterForm.setIsGroup(true);
		filterForm.setGroupTitle("Search");
		filterForm.setNumCols(1);
		filterForm.setAutoFocus(false);

		TextItem commonNameItem = new TextItem("name");
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
			System.out.println(rec.getAttribute("name"));
		}

	}

	@Override
	public void setData(Record[] data) {
		if (tileGrid != null) {
			tileGrid.setData(data);
		}
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}
}