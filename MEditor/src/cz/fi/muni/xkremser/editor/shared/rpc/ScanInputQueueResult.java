package cz.fi.muni.xkremser.editor.shared.rpc;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;

public class ScanInputQueueResult implements Result {
	// input
	private String pathPrefix;

	// output
	private ArrayList<InputQueueItemDetail> items;

	@SuppressWarnings("unused")
	private ScanInputQueueResult() {
	}

	public ScanInputQueueResult(String pathPrefix, ArrayList<InputQueueItemDetail> items) {
		this.pathPrefix = pathPrefix;
		this.items = items;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public ArrayList<InputQueueItemDetail> getItems() {
		return items;
	}

}
