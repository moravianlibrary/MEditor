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
import com.smartgwt.client.widgets.layout.Layout;

// TODO: Auto-generated Javadoc
/**
 * The Class MetadataHolder.
 */
public abstract class MetadataHolder {

	/** The layout. */
	protected Layout layout;

	/** The attribute form. */
	private DynamicForm attributeForm;

	/**
	 * Instantiates a new metadata holder.
	 */
	public MetadataHolder() {
	}

	/**
	 * Sets the attribute form.
	 * 
	 * @param form
	 *          the new attribute form
	 */
	public void setAttributeForm(DynamicForm form) {
		this.attributeForm = form;
	}

	/**
	 * Gets the attribute form.
	 * 
	 * @return the attribute form
	 */
	public DynamicForm getAttributeForm() {
		return this.attributeForm;
	}

	/**
	 * Gets the subelements.
	 * 
	 * @return the subelements
	 */
	public abstract List<MetadataHolder> getSubelements();

	/**
	 * Gets the values.
	 * 
	 * @return the values
	 */
	public abstract List<String> getValues();

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public abstract String getValue();

	/**
	 * Gets the attributes.
	 * 
	 * @return the attributes
	 */
	public abstract List<String> getAttributes();

	/**
	 * Gets the subelement.
	 * 
	 * @return the subelement
	 */
	public MetadataHolder getSubelement() {
		return getSubelements().get(0);
	}

	/**
	 * Sets the layout.
	 * 
	 * @param layout
	 *          the new layout
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
	}

}
