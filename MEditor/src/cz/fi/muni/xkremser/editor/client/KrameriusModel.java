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
package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.InternalPartDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.MonographDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.MonographUnitDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PageDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalItemDetail;
import cz.fi.muni.xkremser.editor.shared.valueobj.PeriodicalVolumeDetail;

// TODO: Auto-generated Javadoc
/**
 * The Enum KrameriusModel.
 */
public enum KrameriusModel implements IsSerializable {

	/** The MONOGRAPH. */
	MONOGRAPH("monograph", ""), /** The MONOGRAPHUNIT. */
	MONOGRAPHUNIT("monographunit", Constants.MONOGRAPH_UNIT_ICON), /**
	 * The
	 * PERIODICAL.
	 */
	PERIODICAL("periodical", ""), /** The PERIODICALVOLUME. */
	PERIODICALVOLUME("periodicalvolume", Constants.VOLUME_ICON), /**
	 * The
	 * PERIODICALITEM.
	 */
	PERIODICALITEM("periodicalitem", Constants.PERIODICAL_ITEM_ICON), /** The PAGE. */
	PAGE("page", ""),
	/** The INTERNALPART. */
	INTERNALPART("internalpart", Constants.INTERNAL_PART_ICON)/*
																														 * ,
																														 * DONATOR("donator"
																														 * )
																														 *//**
	 * Instantiates a
	 * new kramerius model.
	 * 
	 * @param value
	 *          the value
	 * @param icon
	 *          the icon
	 */
	;

	private KrameriusModel(String value, String icon) {
		this.value = value;
		this.icon = icon;
	}

	/** The value. */
	private final String value;

	/** The icon. */
	private final String icon;

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the icon.
	 * 
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * To string.
	 * 
	 * @param km
	 *          the km
	 * @return the string
	 */
	public static String toString(KrameriusModel km) {
		return km.getValue();
	}

	/**
	 * Parses the string.
	 * 
	 * @param s
	 *          the s
	 * @return the kramerius model
	 */
	public static KrameriusModel parseString(String s) {
		KrameriusModel[] values = KrameriusModel.values();
		for (KrameriusModel model : values) {
			if (model.getValue().equalsIgnoreCase(s))
				return model;
		}
		throw new RuntimeException("Unsupported type");
	}

	/**
	 * Gets the detail.
	 * 
	 * @param model
	 *          the model
	 * @return the detail
	 */
	public static AbstractDigitalObjectDetail getDetail(KrameriusModel model) {
		AbstractDigitalObjectDetail object = null;
		switch (model) {
			case INTERNALPART:
				object = new InternalPartDetail(null);
			break;
			case MONOGRAPHUNIT:
				object = new MonographUnitDetail(null);
			break;
			case MONOGRAPH:
				object = new MonographDetail(null);
			break;
			case PAGE:
				object = new PageDetail(null);
			break;
			case PERIODICAL:
				object = new PeriodicalDetail(null);
			break;
			case PERIODICALVOLUME:
				object = new PeriodicalVolumeDetail(null);
			break;
			case PERIODICALITEM:
				object = new PeriodicalItemDetail(null);
			break;
		}
		return object;
	}
}