/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;

// TODO: Auto-generated Javadoc
/**
 * The Class PageDetail.
 */
public class PageDetail extends AbstractDigitalObjectDetail {

	public PageDetail(ArrayList<ArrayList<String>> related) {
		super(related);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#
	 * getModel()
	 */
	@Override
	public KrameriusModel getModel() {
		return KrameriusModel.PAGE;
	}

	@SuppressWarnings("unused")
	private PageDetail() {
		super();
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

	@Override
	public boolean hasPages() {
		return false;
	}

	@Override
	public int hasContainers() {
		return 0;
	}

	@Override
	public List<PageDetail> getPages() {
		return null;
	}

	@Override
	public List<? extends List<? extends AbstractDigitalObjectDetail>> getContainers() {
		return null;
	}

	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return null;
	}

}