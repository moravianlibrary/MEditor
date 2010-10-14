package cz.fi.muni.xkremser.editor.shared.rpc.action;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public class PageDetail extends AbstractDigitalObjectDetail {

	@Override
	public Constants.KrameriusModel getModel() {
		return Constants.KrameriusModel.PAGE;
	}

	// file (literal)
	// handle (literal)
	// policy (literal)
	// text OCR ?
	// MODS ?

	private DublinCore dc;

	public DublinCore getDc() {
		return dc;
	}

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

}