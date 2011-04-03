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

import cz.fi.muni.xkremser.editor.client.mods.TargetAudienceTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class AudienceHolder.
 */
public class AudienceHolder extends ListOfListOfSimpleValuesHolder {

	/**
	 * Instantiates a new audience holder.
	 */
	public AudienceHolder() {
		this("target_audience", ModsConstants.AUTHORITY, ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.TRANSLITERATION, ModsConstants.SCRIPT);
	}

	/**
	 * Instantiates a new audience holder.
	 * 
	 * @param keys
	 *          the keys
	 */
	public AudienceHolder(String... keys) {
		super(keys);
	}

	/**
	 * Gets the audience.
	 * 
	 * @return the audience
	 */
	public List<TargetAudienceTypeClient> getAudience() {
		List<TargetAudienceTypeClient> list = null;
		List<List<String>> listOfValues = getListOfList();
		if (listOfValues != null && listOfValues.size() != 0) {
			list = new ArrayList<TargetAudienceTypeClient>();
			for (List<String> values : listOfValues) {
				if (values != null) {
					TargetAudienceTypeClient val = new TargetAudienceTypeClient();
					val.setValue(values.get(0));
					val.setAuthority(values.get(1));
					val.setLang(values.get(2));
					val.setXmlLang(values.get(3));
					val.setTransliteration(values.get(4));
					val.setScript(values.get(5));
					list.add(val);
				}
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getSubelements()
	 */
	@Override
	public List<MetadataHolder> getSubelements() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getValue()
	 */
	@Override
	public String getValue() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getValues()
	 */
	@Override
	public List<String> getValues() {
		throw new UnsupportedOperationException("Mods");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder
	 * #getAttributes()
	 */
	@Override
	public List<String> getAttributes() {
		throw new UnsupportedOperationException("Mods");
	}

}
