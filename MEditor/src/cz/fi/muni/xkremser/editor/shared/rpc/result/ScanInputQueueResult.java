package cz.fi.muni.xkremser.editor.shared.rpc.result;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.shared.rpc.InputQueueItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ScanInputQueue;

import net.customware.gwt.dispatch.shared.Result;

public class ScanInputQueueResult implements Result {
	// input
	private String id;
	private ScanInputQueue.TYPE type;

	// output
	private ArrayList<InputQueueItem> items;

	@SuppressWarnings("unused")
	private ScanInputQueueResult() {
	}

	public ScanInputQueueResult(String id, ScanInputQueue.TYPE type, ArrayList<InputQueueItem> items) {
		this.id = id;
		this.type = type;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public ScanInputQueue.TYPE getType() {
		return type;
	}

	public ArrayList<InputQueueItem> getItems() {
		return items;
	}

}
