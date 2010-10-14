/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.xkremser.editor.fedora;

import java.util.HashMap;
import java.util.Map;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.server.modelHandler.CanGetObject;
import cz.fi.muni.xkremser.editor.server.modelHandler.InternalPartHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.MonographHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.MonographUnitHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PageHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalItemHandler;
import cz.fi.muni.xkremser.editor.server.modelHandler.PeriodicalVolumeHandler;

public class KrameriusModelHelper {

	public final static Map<Constants.KrameriusModel, Class<? extends CanGetObject>> TYPES;

	static {
		TYPES = new HashMap<Constants.KrameriusModel, Class<? extends CanGetObject>>();
		TYPES.put(Constants.KrameriusModel.MONOGRAPH, MonographHandler.class);
		TYPES.put(Constants.KrameriusModel.MONOGRAPHUNIT, MonographUnitHandler.class);
		TYPES.put(Constants.KrameriusModel.PERIODICAL, PeriodicalHandler.class);
		TYPES.put(Constants.KrameriusModel.PERIODICALVOLUME, PeriodicalVolumeHandler.class);
		TYPES.put(Constants.KrameriusModel.PERIODICALITEM, PeriodicalItemHandler.class);
		TYPES.put(Constants.KrameriusModel.PAGE, PageHandler.class);
		TYPES.put(Constants.KrameriusModel.INTERNALPART, InternalPartHandler.class);
	}

	public static Constants.KrameriusModel parseString(String s) {
		Constants.KrameriusModel[] values = Constants.KrameriusModel.values();
		for (Constants.KrameriusModel model : values) {
			if (model.getValue().equalsIgnoreCase(s))
				return model;
		}
		throw new RuntimeException("Unsupported type");
	}

	public static String toString(Constants.KrameriusModel km) {
		return km.getValue();
	}
}
