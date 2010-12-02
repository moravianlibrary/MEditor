package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;

public class ExtentHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder start;
	private final ListOfSimpleValuesHolder end;
	private final ListOfSimpleValuesHolder total;
	private final ListOfSimpleValuesHolder list;

	public ExtentHolder() {
		this.start = new ListOfSimpleValuesHolder();
		this.end = new ListOfSimpleValuesHolder();
		this.total = new ListOfSimpleValuesHolder();
		this.list = new ListOfSimpleValuesHolder();
	}

	public ExtentTypeClient getExtent() {
		ExtentTypeClient extentTypeClient = new ExtentTypeClient();
		if (getAttributeForm() != null) {
			extentTypeClient.setUnit(getAttributeForm().getValueAsString(ModsConstants.UNIT));
		}
		extentTypeClient.setStart(safeGet(start, ModsConstants.START));
		extentTypeClient.setEnd(safeGet(end, ModsConstants.END));
		extentTypeClient.setTotal(safeGet(total, ModsConstants.TOTAL));
		extentTypeClient.setList(safeGet(list, ModsConstants.LIST));

		return extentTypeClient;
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

	private String safeGet(ListOfSimpleValuesHolder holder, String key) {
		if ((holder.getValues() != null && holder.getValues().size() > 0 && holder.getValues().get(0) != null)
				|| holder.getAttributeForm().getValueAsString(key) != null)
			return holder.getAttributeForm().getValueAsString(key);
		else
			return null;
	}

	public ListOfSimpleValuesHolder getStart() {
		return start;
	}

	public ListOfSimpleValuesHolder getEnd() {
		return end;
	}

	public ListOfSimpleValuesHolder getTotal() {
		return total;
	}

	public ListOfSimpleValuesHolder getList() {
		return list;
	}

}
