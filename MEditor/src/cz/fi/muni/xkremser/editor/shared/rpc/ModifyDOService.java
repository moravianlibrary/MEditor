package cz.fi.muni.xkremser.editor.shared.rpc;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ModifyDOService extends RemoteService {
	void modify(String pid);
}
