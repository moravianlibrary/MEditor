package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.ArrayList;

import cz.fi.muni.xkremser.editor.client.Constants.KrameriusModel;
import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;

public class IntCompPartDetail extends AbstractDigitalObjectDetail {

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

	public DublinCore getDc() {
		return dc;
	}

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	// handle
	// policy

}
