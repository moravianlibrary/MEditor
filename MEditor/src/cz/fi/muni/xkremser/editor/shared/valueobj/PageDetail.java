package cz.fi.muni.xkremser.editor.shared.valueobj;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

public class PageDetail extends AbstractDigitalObjectDetail {

	@Override
	public KrameriusModel getModel() {
		return KrameriusModel.PAGE;
	}

	// file (literal)
	// handle (literal)
	// policy (literal)
	// text OCR ?
	// MODS ?

	// DC
	// title
	// identifier - uuid
	// identifier - handle
	// type (= model:page)
	// rights

	private DublinCore dc;

	@Override
	public DublinCore getDc() {
		return dc;
	}

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	@Override
	public boolean hasPages() {
		return false;
	}

}