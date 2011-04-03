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
import java.util.Date;
import java.util.List;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfSimpleValuesHolder.
 */
public class ListOfSimpleValuesHolder extends MetadataHolder {

	/** The attribute form2. */
	private DynamicForm attributeForm2;

	/**
	 * Instantiates a new list of simple values holder.
	 */
	public ListOfSimpleValuesHolder() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getValues()
	 */
	@Override
	public List<String> getValues() {
		if (layout == null)
			return null;
		List<String> values = new ArrayList<String>();
		Canvas[] canvases = this.layout.getMembers();
		for (Canvas canvas : canvases) {
			DynamicForm form = (DynamicForm) canvas;
			Object o = form.getFields()[0].getValue();
			String value = null;
			if (o != null) {
				if (o instanceof String) {
					value = (String) o;
				} else if (o instanceof Date) {
					value = ((Date) o).toGMTString();
				}
				if (!"".equals(value.trim())) {
					values.add(value);
				}
			}
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder#getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the attribute form2.
	 * 
	 * @return the attribute form2
	 */
	public DynamicForm getAttributeForm2() {
		return attributeForm2;
	}

	/**
	 * Sets the attribute form2.
	 * 
	 * @param attributeForm2
	 *          the new attribute form2
	 */
	public void setAttributeForm2(DynamicForm attributeForm2) {
		this.attributeForm2 = attributeForm2;
	}

}
