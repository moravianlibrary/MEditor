package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusTypeClient;

public class PhysicalDescriptionHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder internetTypes;
	private final ListOfSimpleValuesHolder extents;
	private final ListOfListOfSimpleValuesHolder forms;
	private final ListOfListOfSimpleValuesHolder notes;
	private final ListOfSimpleValuesHolder reformattingQuality;
	private final ListOfSimpleValuesHolder digitalOrigin;

	public PhysicalDescriptionHolder() {
		this.internetTypes = new ListOfSimpleValuesHolder();
		this.extents = new ListOfSimpleValuesHolder();
		this.forms = new ListOfListOfSimpleValuesHolder("form", ModsConstants.TYPE, ModsConstants.AUTHORITY);
		this.notes = new ListOfListOfSimpleValuesHolder(ModsConstants.NOTE, ModsConstants.TYPE, ModsConstants.DISPLAY_LABEL, ModsConstants.XLINK,
				ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.SCRIPT, ModsConstants.TRANSLITERATION);
		this.reformattingQuality = new ListOfSimpleValuesHolder();
		this.digitalOrigin = new ListOfSimpleValuesHolder();
	}

	public PhysicalDescriptionTypeClient getPhysicalDescription() {
		PhysicalDescriptionTypeClient physicalDescriptionTypeClient = new PhysicalDescriptionTypeClient();

		if (getAttributeForm() != null) {
			physicalDescriptionTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			physicalDescriptionTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			physicalDescriptionTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			physicalDescriptionTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
		}
		physicalDescriptionTypeClient.setInternetMediaType(internetTypes.getValues());
		physicalDescriptionTypeClient.setExtent(extents.getValues());
		String value = null;
		if (reformattingQuality.getAttributeForm() != null) {
			value = reformattingQuality.getAttributeForm().getValueAsString(ModsConstants.REFORMATTING);
			if (value == null || "".equals(value.trim())) {
				value = null;
			}
		}
		physicalDescriptionTypeClient.setReformattingQuality(value == null ? null : Arrays.asList(value));
		if (digitalOrigin.getAttributeForm() != null) {
			value = digitalOrigin.getAttributeForm().getValueAsString(ModsConstants.DIGITAL_ORIGIN);
			if (value == null || "".equals(value.trim())) {
				value = null;
			}
		}
		physicalDescriptionTypeClient.setDigitalOrigin(value == null ? null : Arrays.asList(value));
		List<NoteTypeClient> list = null;
		List<List<String>> listOfValues = notes.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<NoteTypeClient>();
			for (List<String> values : listOfValues) {
				NoteTypeClient noteTypeClient = new NoteTypeClient();
				noteTypeClient.setValue(values.get(0));
				noteTypeClient.setAtType(values.get(1));
				noteTypeClient.setDisplayLabel(values.get(2));
				noteTypeClient.setXlink(values.get(3));
				noteTypeClient.setLang(values.get(4));
				noteTypeClient.setXmlLang(values.get(5));
				noteTypeClient.setScript(values.get(6));
				noteTypeClient.setTransliteration(values.get(7));
				list.add(noteTypeClient);
			}
		}
		physicalDescriptionTypeClient.setNote(list);

		List<StringPlusAuthorityPlusTypeClient> list2 = null;
		List<List<String>> listOfValues2 = forms.getListOfList();
		if (listOfValues2 != null && listOfValues2.size() != 0) {
			list2 = new ArrayList<StringPlusAuthorityPlusTypeClient>();
			for (List<String> values : listOfValues2) {
				StringPlusAuthorityPlusTypeClient val = new StringPlusAuthorityPlusTypeClient();
				val.setValue(values.get(0));
				val.setType(values.get(1));
				val.setAuthority(values.get(2));
				list2.add(val);
			}
		}
		physicalDescriptionTypeClient.setForm(list2);
		return physicalDescriptionTypeClient;
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

	public ListOfSimpleValuesHolder getPublishers() {
		return internetTypes;
	}

	public ListOfSimpleValuesHolder getEditions() {
		return extents;
	}

	public ListOfListOfSimpleValuesHolder getFrequencies() {
		return forms;
	}

	public ListOfSimpleValuesHolder getInternetTypes() {
		return internetTypes;
	}

	public ListOfSimpleValuesHolder getExtents() {
		return extents;
	}

	public ListOfListOfSimpleValuesHolder getForms() {
		return forms;
	}

	public ListOfListOfSimpleValuesHolder getNotes() {
		return notes;
	}

	public ListOfSimpleValuesHolder getReformattingQuality() {
		return reformattingQuality;
	}

	public ListOfSimpleValuesHolder getDigitalOrigin() {
		return digitalOrigin;
	}
}
