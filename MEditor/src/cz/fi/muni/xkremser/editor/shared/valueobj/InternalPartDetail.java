package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.client.Constants.KrameriusModel;

public class InternalPartDetail extends AbstractDigitalObjectDetail {

	// DC
	// title
	// creator
	// contributor
	// identifier - uuid
	// identifier - handle
	// type (= model:internalpart)
	// rights (treba policy:private)

	@Override
	public KrameriusModel getModel() {
		return KrameriusModel.INTERNALPART;
	}

	private ArrayList<PageDetail> pages;

	private DublinCore dc;

	public ArrayList<PageDetail> getPages() {
		return pages;
	}

	public void setPages(ArrayList<PageDetail> pages) {
		this.pages = pages;
	}

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	@Override
	public DublinCore getDc() {
		return dc;
	}

	@Override
	public boolean hasPages() {
		return true;
	}

	// handle
	// policy

}
