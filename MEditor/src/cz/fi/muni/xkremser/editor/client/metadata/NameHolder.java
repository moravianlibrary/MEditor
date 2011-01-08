/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */
package cz.fi.muni.xkremser.editor.client.metadata;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.CodeOrTextClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeAttributeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;

// TODO: Auto-generated Javadoc
/**
 * The Class NameHolder.
 */
public class NameHolder extends MetadataHolder {
	
	/** The display forms. */
	private final ListOfSimpleValuesHolder displayForms;
	
	/** The affiliations. */
	private final ListOfSimpleValuesHolder affiliations;
	
	/** The descriptions. */
	private final ListOfSimpleValuesHolder descriptions;

	/** The name parts. */
	private final ListOfListOfSimpleValuesHolder nameParts;
	
	/** The roles. */
	private final ListOfListOfSimpleValuesHolder roles;

	/**
	 * Instantiates a new name holder.
	 */
	public NameHolder() {
		this.displayForms = new ListOfSimpleValuesHolder();
		this.affiliations = new ListOfSimpleValuesHolder();
		this.descriptions = new ListOfSimpleValuesHolder();
		this.nameParts = new ListOfListOfSimpleValuesHolder(ModsConstants.NAME_PART, ModsConstants.TYPE);
		this.roles = new ListOfListOfSimpleValuesHolder(ModsConstants.ROLE, ModsConstants.TYPE, ModsConstants.AUTHORITY);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
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
		nameTypeClient.setDescription(affiliations.getValues());

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

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/* (non-Javadoc)
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	/**
	 * Gets the display forms.
	 *
	 * @return the display forms
	 */
	public ListOfSimpleValuesHolder getDisplayForms() {
		return displayForms;
	}

	/**
	 * Gets the affiliations.
	 *
	 * @return the affiliations
	 */
	public ListOfSimpleValuesHolder getAffiliations() {
		return affiliations;
	}

	/**
	 * Gets the name parts.
	 *
	 * @return the name parts
	 */
	public ListOfListOfSimpleValuesHolder getNameParts() {
		return nameParts;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public ListOfListOfSimpleValuesHolder getRoles() {
		return roles;
	}

	/**
	 * Gets the descriptions.
	 *
	 * @return the descriptions
	 */
	public ListOfSimpleValuesHolder getDescriptions() {
		return descriptions;
	}
}
