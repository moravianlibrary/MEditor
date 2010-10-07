package cz.fi.muni.xkremser.editor.shared.rpc.action;

import cz.fi.muni.xkremser.editor.shared.rpc.result.ScanInputQueueResult;
import net.customware.gwt.dispatch.shared.Action;

public class ScanInputQueue implements Action<ScanInputQueueResult> {

	private String id;

	public enum TYPE {
		DB_UPDATE, DB_GET
	};

	private TYPE type;

	@SuppressWarnings("unused")
	private ScanInputQueue() {

	}

	public ScanInputQueue(final String id, TYPE type) {
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public TYPE getType() {
		return type;
	}

}
