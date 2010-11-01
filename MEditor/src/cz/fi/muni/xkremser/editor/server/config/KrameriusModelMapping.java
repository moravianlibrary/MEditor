/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
