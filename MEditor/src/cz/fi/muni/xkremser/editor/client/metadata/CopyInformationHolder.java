package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.CopyInformationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.EnumerationAndChronologyTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusDisplayLabelPlusTypeClient;

public class CopyInformationHolder extends MetadataHolder {
	private final ListOfSimpleValuesHolder subLocations;
	private final ListOfSimpleValuesHolder shelfLocators;
	private final ListOfSimpleValuesHolder electronicLocators;
	private final ListOfSimpleValuesHolder form;
	private final ListOfListOfSimpleValuesHolder enumChrono;
	private final List<ListOfSimpleValuesHolder> notes;

	public CopyInformationHolder() {
		this.shelfLocators = new ListOfSimpleValuesHolder();
		this.subLocations = new ListOfSimpleValuesHolder();
		this.electronicLocators = new ListOfSimpleValuesHolder();
		this.form = new ListOfSimpleValuesHolder();
		this.enumChrono = new ListOfListOfSimpleValuesHolder(ModsConstants.ENUM_CHRONO, ModsConstants.UNIT_TYPE);
		this.notes = new ArrayList<ListOfSimpleValuesHolder>();
	}

	public CopyInformationTypeClient getCopyInfo() {
		CopyInformationTypeClient copyInformationTypeClient = new CopyInformationTypeClient();
		copyInformationTypeClient.setElectronicLocator(electronicLocators.getValues());
		copyInformationTypeClient.setShelfLocator(shelfLocators.getValues());
		copyInformationTypeClient.setSubLocation(subLocations.getValues());
		if ((form.getValues() != null && form.getValues().size() > 0 && form.getValues().get(0) != null)
				|| form.getAttributeForm().getValueAsString(ModsConstants.AUTHORITY) != null) {
			StringPlusAuthorityClient val = new StringPlusAuthorityClient();
			val.setValue(form.getValues().get(1));
			val.setAuthority(form.getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
			copyInformationTypeClient.setForm(val);
		}
		List<EnumerationAndChronologyTypeClient> list = null;
		List<List<String>> listOfValues = enumChrono.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<EnumerationAndChronologyTypeClient>();
			for (List<String> values : listOfValues) {
				EnumerationAndChronologyTypeClient val = new EnumerationAndChronologyTypeClient();
				val.setValue(values.get(0));
				val.setUnitType(values.get(1));
				list.add(val);
			}
		}
		copyInformationTypeClient.setEnumerationAndChronology(list);

		// notes
		List<StringPlusDisplayLabelPlusTypeClient> noteList = new ArrayList<StringPlusDisplayLabelPlusTypeClient>(notes.size());
		for (ListOfSimpleValuesHolder holder : notes) {
			if (holder != null) {
				StringPlusDisplayLabelPlusTypeClient note = new StringPlusDisplayLabelPlusTypeClient();
				if (holder.getAttributeForm() != null) {
					note.setAtType(holder.getAttributeForm().getValueAsString(ModsConstants.TYPE));
					note.setDisplayLabel(holder.getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
				}
				if (holder.getAttributeForm2() != null)
					note.setValue(holder.getAttributeForm2().getValueAsString(ModsConstants.NOTE));
				noteList.add(note);
			}

		}
		copyInformationTypeClient.setNote(noteList);

		return copyInformationTypeClient;
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

	public ListOfSimpleValuesHolder getSubLocations() {
		return subLocations;
	}

	public ListOfSimpleValuesHolder getShelfLocators() {
		return shelfLocators;
	}

	public ListOfSimpleValuesHolder getElectronicLocators() {
		return electronicLocators;
	}

	public ListOfSimpleValuesHolder getForm() {
		return form;
	}

	public ListOfListOfSimpleValuesHolder getEnumChrono() {
		return enumChrono;
	}

	public List<ListOfSimpleValuesHolder> getNotes() {
		return notes;
	}

}
