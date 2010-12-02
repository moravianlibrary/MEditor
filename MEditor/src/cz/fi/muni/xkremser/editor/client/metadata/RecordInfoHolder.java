package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient.RecordIdentifierClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusLanguageClient;

public class RecordInfoHolder extends ListOfSimpleValuesHolder {
	private final ListOfSimpleValuesHolder recordOrigin;
	private final ListOfListOfSimpleValuesHolder recordContentSource;
	private final ListOfListOfSimpleValuesHolder descriptionStandard;
	private final List<DateHolder> creationDate;
	private final List<DateHolder> changeDate;
	private final List<LanguageHolder> language;
	private final ListOfListOfSimpleValuesHolder recordIdentifier;

	public RecordInfoHolder() {
		this.recordOrigin = new ListOfSimpleValuesHolder();
		this.recordContentSource = new ListOfListOfSimpleValuesHolder(ModsConstants.RECORD_CONTENT_SOURCE, ModsConstants.AUTHORITY, ModsConstants.LANG,
				ModsConstants.XML_LANG, ModsConstants.SCRIPT, ModsConstants.TRANSLITERATION);
		this.descriptionStandard = new ListOfListOfSimpleValuesHolder(ModsConstants.DESCRIPTION_STANDARD, ModsConstants.AUTHORITY);
		this.creationDate = new ArrayList<DateHolder>();
		this.changeDate = new ArrayList<DateHolder>();
		this.language = new ArrayList<LanguageHolder>();
		this.recordIdentifier = new ListOfListOfSimpleValuesHolder(ModsConstants.RECORD_IDENTIFIER, ModsConstants.SOURCE);
	}

	public RecordInfoTypeClient getRecordInfo() {
		RecordInfoTypeClient recordInfoTypeClient = new RecordInfoTypeClient();

		if (getAttributeForm() != null) {
			recordInfoTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			recordInfoTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			recordInfoTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			recordInfoTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
		}

		List<RecordIdentifierClient> list = null;
		List<List<String>> listOfValues = recordIdentifier.getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<RecordIdentifierClient>();
			for (List<String> values : listOfValues) {
				RecordIdentifierClient val = new RecordIdentifierClient();
				val.setValue(values.get(0));
				val.setSource(values.get(1));
				list.add(val);
			}
		}
		recordInfoTypeClient.setRecordIdentifier(list);

		recordInfoTypeClient.setRecordChangeDate(getDatesFromHolders(changeDate));
		recordInfoTypeClient.setRecordCreationDate(getDatesFromHolders(creationDate));
		recordInfoTypeClient.setRecordOrigin(recordOrigin.getValues());

		// language
		List<LanguageTypeClient> value2 = new ArrayList<LanguageTypeClient>(language.size());
		for (LanguageHolder holder : language) {
			value2.add(holder.getLanguage());
		}
		recordInfoTypeClient.setLanguageOfCataloging(value2);

		List<StringPlusAuthorityClient> list1 = null;
		List<List<String>> listOfValues1 = descriptionStandard.getListOfList();
		if (listOfValues1 != null && listOfValues1.size() != 0) {
			list1 = new ArrayList<StringPlusAuthorityClient>();
			for (List<String> values : listOfValues1) {
				StringPlusAuthorityClient val = new StringPlusAuthorityClient();
				val.setValue(values.get(0));
				val.setAuthority(values.get(1));
				list1.add(val);
			}
		}
		recordInfoTypeClient.setDescriptionStandard(list1);

		List<StringPlusAuthorityPlusLanguageClient> list2 = null;
		List<List<String>> listOfValues2 = recordContentSource.getListOfList();
		if (listOfValues2 != null && listOfValues2.size() != 0) {
			list2 = new ArrayList<StringPlusAuthorityPlusLanguageClient>();
			for (List<String> values : listOfValues2) {
				StringPlusAuthorityPlusLanguageClient val = new StringPlusAuthorityPlusLanguageClient();
				val.setValue(values.get(0));
				val.setAuthority(values.get(1));
				val.setLang(values.get(2));
				val.setXmlLang(values.get(3));
				val.setScript(values.get(4));
				val.setTransliteration(values.get(5));
				list2.add(val);
			}
		}
		recordInfoTypeClient.setRecordContentSource(list2);
		return recordInfoTypeClient;
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

	public ListOfSimpleValuesHolder getRecordOrigin() {
		return recordOrigin;
	}

	public ListOfListOfSimpleValuesHolder getRecordContentSource() {
		return recordContentSource;
	}

	public ListOfListOfSimpleValuesHolder getDescriptionStandard() {
		return descriptionStandard;
	}

	public List<DateHolder> getCreationDate() {
		return creationDate;
	}

	public List<DateHolder> getChangeDate() {
		return changeDate;
	}

	public List<LanguageHolder> getLanguage() {
		return language;
	}

	public ListOfListOfSimpleValuesHolder getRecordIdentifier() {
		return recordIdentifier;
	}

	private static List<DateTypeClient> getDatesFromHolders(List<DateHolder> holders) {
		List<DateTypeClient> dates = new ArrayList<DateTypeClient>();
		for (DateHolder holder : holders) {
			dates.add(holder.getDate());
		}
		return dates;
	}
}
