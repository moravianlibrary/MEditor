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

import java.util.List;

import com.smartgwt.client.widgets.form.DynamicForm;

import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.YesClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TypeOfResourceHolder.
 */
public class TypeOfResourceHolder extends ListOfSimpleValuesHolder {

	/** The attribute form2. */
	private DynamicForm attributeForm2;

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public TypeOfResourceTypeClient getType() {
		TypeOfResourceTypeClient typeOfResourceTypeClient = new TypeOfResourceTypeClient();
		if (getAttributeForm() != null) {
			String val = getAttributeForm().getValueAsString(ModsConstants.COLLECTION);
			if (val != null && ClientUtils.toBoolean(val)) {
				typeOfResourceTypeClient.setCollection(YesClient.YES);
			}
			val = getAttributeForm().getValueAsString(ModsConstants.MANUSCRIPT);
			if (val != null && ClientUtils.toBoolean(val)) {
				typeOfResourceTypeClient.setManuscript(YesClient.YES);
			}
		}
		typeOfResourceTypeClient.setValue(getAttributeForm2().getValueAsString(ModsConstants.TYPE));
		return typeOfResourceTypeClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValue
	 * ()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#getValues
	 * ()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * getAttributeForm2()
	 */
	public DynamicForm getAttributeForm2() {
		return attributeForm2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder#
	 * setAttributeForm2(com.smartgwt.client.widgets.form.DynamicForm)
	 */
	public void setAttributeForm2(DynamicForm attributeForm2) {
		this.attributeForm2 = attributeForm2;
	}

}
