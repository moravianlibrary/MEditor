package cz.fi.muni.xkremser.editor.shared.rpc;

import net.customware.gwt.dispatch.shared.Action;
import cz.fi.muni.xkremser.editor.client.Constants;

public class ScanInputQueue implements Action<ScanInputQueueResult> {

	private static final long serialVersionUID = 1L;
	private Constants.DOC_TYPE documentType;

	@SuppressWarnings("unused")
	private ScanInputQueue() {

	}

	public ScanInputQueue(final Constants.DOC_TYPE documentType) {
		this.documentType = documentType;
	}

	public Constants.DOC_TYPE getDocumentType() {
		return documentType;
	}

}
