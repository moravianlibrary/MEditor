package cz.fi.muni.xkremser.editor.shared.valueobj;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.Constants;

public abstract class AbstractDigitalObjectDetail implements IsSerializable {

	public abstract Constants.KrameriusModel getModel();

	public abstract DublinCore getDc();

	// public Class<? extends AbstractDigitalObjectDetail> getClazz() {
	// return getClass();
	// }

	public AbstractDigitalObjectDetail() {
	}

	@Override
	public String toString() {
		return "model: " + getModel() + "\nDC: " + getDc();
	}

}