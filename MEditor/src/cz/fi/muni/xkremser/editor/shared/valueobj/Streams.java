package cz.fi.muni.xkremser.editor.shared.valueobj;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

public class Streams implements IsSerializable {
	private DublinCore dc;
	private ModsCollectionClient mods;

	public DublinCore getDc() {
		return dc;
	}

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	public ModsCollectionClient getMods() {
		return mods;
	}

	public void setMods(ModsCollectionClient mods) {
		this.mods = mods;
	}

}
