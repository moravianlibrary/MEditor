package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

@GenDispatch(isSecure = false)
public class PutRecentlyModified extends UnsecuredActionImpl<ScanInputQueueResult> {

	@In(1)
	private RecentlyModifiedItem item;

	@Out(1)
	private boolean found;

}
