package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.CartographicsClient;

public class CartographicsHolder extends MetadataHolder {
	private final ListOfSimpleValuesHolder coordinates;
	private final ListOfSimpleValuesHolder scales;
	private final ListOfSimpleValuesHolder projections;

	public CartographicsHolder() {
		this.coordinates = new ListOfSimpleValuesHolder();
		this.scales = new ListOfSimpleValuesHolder();
		this.projections = new ListOfSimpleValuesHolder();
	}

	public CartographicsClient getCartographics() {
		CartographicsClient cartographicsClient = new CartographicsClient();
		cartographicsClient.setCoordinates(coordinates.getValues());
		cartographicsClient.setScale(scales.getValues().size() > 0 ? scales.getValues().get(0) : null);
		cartographicsClient.setProjection(projections.getValues().size() > 0 ? projections.getValues().get(0) : null);
		return cartographicsClient;
	}

	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	public ListOfSimpleValuesHolder getCoordinates() {
		return coordinates;
	}

	public ListOfSimpleValuesHolder getScales() {
		return scales;
	}

	public ListOfSimpleValuesHolder getProjections() {
		return projections;
	}

}
