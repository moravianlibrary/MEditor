package cz.fi.muni.xkremser.editor.shared.rpc;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class ScanInputQueueResult implements Result {
	// input
	private String id;

	// output
	private ArrayList<InputQueueItem> items;

	@SuppressWarnings("unused")
	private ScanInputQueueResult() {
	}

	public ScanInputQueueResult(String id, ArrayList<InputQueueItem> items) {
		this.id = id;
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public ArrayList<InputQueueItem> getItems() {
		return items;
	}

}
