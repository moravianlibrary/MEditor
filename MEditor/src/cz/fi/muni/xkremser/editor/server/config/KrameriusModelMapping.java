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

package cz.fi.muni.xkremser.editor.server.config;

import java.util.HashMap;
import java.util.Map;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.server.modelHandler.CanGetObject;
import cz.fi.muni.xkremser.editor.server.modelHandler.InternalPartHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.MonographHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.MonographUnitHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PageHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalItemHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalVolumeHandler;

// TODO: Auto-generated Javadoc
// TODO: to client side
/**
 * The Class KrameriusModelMapping.
 */
public class KrameriusModelMapping {

	/** The Constant TYPES. */
	public final static Map<KrameriusModel, Class<? extends CanGetObject>> TYPES;

	static {
		TYPES = new HashMap<KrameriusModel, Class<? extends CanGetObject>>();
		TYPES.put(KrameriusModel.MONOGRAPH, MonographHandler.class);
		TYPES.put(KrameriusModel.MONOGRAPHUNIT, MonographUnitHandler.class);
		TYPES.put(KrameriusModel.PERIODICAL, PeriodicalHandler.class);
		TYPES.put(KrameriusModel.PERIODICALVOLUME, PeriodicalVolumeHandler.class);
		TYPES.put(KrameriusModel.PERIODICALITEM, PeriodicalItemHandler.class);
		TYPES.put(KrameriusModel.PAGE, PageHandler.class);
		TYPES.put(KrameriusModel.INTERNALPART, InternalPartHandler.class);
	}
}
