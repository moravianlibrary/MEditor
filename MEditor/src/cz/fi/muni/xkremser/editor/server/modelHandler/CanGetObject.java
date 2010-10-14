package cz.fi.muni.xkremser.editor.server.modelHandler;

import cz.fi.muni.xkremser.editor.shared.rpc.action.AbstractDigitalObjectDetail;

public interface CanGetObject {
	AbstractDigitalObjectDetail getDigitalObject(String uuid);
}
