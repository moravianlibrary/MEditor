package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.ArrayList;

import com.gwtplatform.annotation.GenDispatch;
import com.gwtplatform.annotation.In;
import com.gwtplatform.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;

@GenDispatch(isSecure = false)
public class ScanInputQueue extends UnsecuredActionImpl<ScanInputQueueResult> {
	@In(1)
	private String id;

	public enum TYPE {
		DB_UPDATE, DB_GET
	};

	@In(2)
	private TYPE type;

	@Out(1)
	private ArrayList<InputQueueItem> items;

	// @Override
	// public String getServiceName() {
	// return super.getServiceName() + "Action";
	// }
}
