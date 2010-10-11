package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

import cz.fi.muni.xkremser.editor.client.presenter.ModifyPresenter;

public class ModifyView extends ViewImpl implements ModifyPresenter.MyView {
	private final TileGrid tileGrid;

	public ModifyView() {
		tileGrid = new TileGrid();
		tileGrid.setTileWidth(194);
		tileGrid.setTileHeight(165);
		tileGrid.setHeight100();
		tileGrid.setWidth100();
		tileGrid.setCanDrag(true);
		tileGrid.setCanAcceptDrop(true);
		tileGrid.setShowAllRecords(true);
		tileGrid.setData(CarData.getRecords());

		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		pictureField.setImageURLPrefix("cars/");
		pictureField.setImageWidth(186);
		pictureField.setImageHeight(120);

		DetailViewerField nameField = new DetailViewerField("name");
		DetailViewerField priceField = new DetailViewerField("price");

		tileGrid.setFields(pictureField, nameField, priceField);

	}

	@Override
	public HasValue<String> getName() {
		return null;
	}

	@Override
	public HasClickHandlers getSend() {
		return null;
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	@Override
	public Widget asWidget() {
		return tileGrid;
	}
}