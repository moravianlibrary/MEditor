package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeAttributeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;

public class NameHolder extends MetadataHolder {
	private final ListOfSimpleValuesHolder displayForms;
	private final ListOfSimpleValuesHolder affiliations;

	private final ListOfListOfSimpleValuesHolder nameParts;
	private final ListOfListOfSimpleValuesHolder roles;

	public NameHolder() {
		this.displayForms = new ListOfSimpleValuesHolder();
		this.affiliations = new ListOfSimpleValuesHolder();
		this.nameParts = new ListOfListOfSimpleValuesHolder(ModsConstants.NAME_PART, ModsConstants.TYPE);
		this.roles = new ListOfListOfSimpleValuesHolder(ModsConstants.ROLE, ModsConstants.TYPE, ModsConstants.AUTHORITY);
	}

	public NameTypeClient getName() {
		NameTypeClient nameTypeClient = new NameTypeClient();
		if (getAttributeForm() != null) {
			nameTypeClient.setAuthority(getAttributeForm().getValueAsString(ModsConstants.AUTHORITY));
			nameTypeClient.setType(NameTypeAttributeClient.fromValue(getAttributeForm().getValueAsString(ModsConstants.TYPE)));
			nameTypeClient.setLang(getAttributeForm().getValueAsString(ModsConstants.LANG));
			nameTypeClient.setXmlLang(getAttributeForm().getValueAsString(ModsConstants.XML_LANG));
			nameTypeClient.setTransliteration(getAttributeForm().getValueAsString(ModsConstants.TRANSLITERATION));
			nameTypeClient.setScript(getAttributeForm().getValueAsString(ModsConstants.SCRIPT));
			nameTypeClient.setXlink(getAttributeForm().getValueAsString(ModsConstants.XLINK));
			nameTypeClient.setId(getAttributeForm().getValueAsString(ModsConstants.ID));
		}
		nameTypeClient.setDisplayForm(displayForms.getValues());
		nameTypeClient.setAffiliation(affiliations.getValues());

		List<NamePartTypeClient> list1 = new ArrayList<NamePartTypeClient>();
		for (List<String> values : nameParts.getListOfList()) {
			NamePartTypeClient namePartTypeClient = new NamePartTypeClient();
			namePartTypeClient.setValue(values.get(0));
			namePartTypeClient.setType(values.get(1));
			list1.add(namePartTypeClient);
		}
		nameTypeClient.setNamePart(list1);

		List<RoleTypeClient> list2 = new ArrayList<RoleTypeClient>();
		List<RoleTermClient> list3 = new ArrayList<RoleTermClient>();
		for (List<String> values : roles.getListOfList()) {
			RoleTermClient roleTermClient = new RoleTermClient();
			roleTermClient.setValue(values.get(0));
			roleTermClient.setType(CodeOrTextClient.fromValue(values.get(1)));
			roleTermClient.setAuthority(values.get(2));
			list3.add(roleTermClient);
		}
		RoleTypeClient client = new RoleTypeClient();
		client.setRoleTerm(list3);
		list2.add(client);
		nameTypeClient.setRole(list2);
		return nameTypeClient;
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

}
