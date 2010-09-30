package cz.fi.muni.xkremser.editor.shared.rpc;

import java.util.ArrayList;

import net.customware.gwt.dispatch.shared.Result;
import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.Constants.DOC_TYPE;

public class ScanInputQueueResult implements Result {
	private Constants.DOC_TYPE documentType;
	private ArrayList<InputQueueItemDetail> items;

	@SuppressWarnings("unused")
	private ScanInputQueueResult() {
	}

	public ScanInputQueueResult(DOC_TYPE documentType,
			ArrayList<InputQueueItemDetail> items) {
		this.documentType = documentType;
		this.items = items;
	}

	public Constants.DOC_TYPE getDocumentType() {
		return documentType;
	}

	public ArrayList<InputQueueItemDetail> getItems() {
		return items;
	}

}
