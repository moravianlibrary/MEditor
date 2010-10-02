package cz.fi.muni.xkremser.editor.shared.rpc;

import net.customware.gwt.dispatch.shared.Action;

public class ScanInputQueue implements Action<ScanInputQueueResult> {

	private String id;

	@SuppressWarnings("unused")
	private ScanInputQueue() {

	}

	public ScanInputQueue(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
