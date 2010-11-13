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
 * The Class InternalPartDetail.
 */
public class MonographDetail extends AbstractDigitalObjectDetail {

	private ArrayList<InternalPartDetail> intParts;

	private ArrayList<MonographUnitDetail> monUnits;

	/** The pages. */
	private ArrayList<PageDetail> pages;

	// DC
	// title
	// creator
	// contributor
	// identifier - uuid
	// identifier - handle
	// type (= model:internalpart)
	// rights (treba policy:private)

	@SuppressWarnings("unused")
	private MonographDetail() {
		super();
	}

	public MonographDetail(ArrayList<ArrayList<String>> related) {
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
		return KrameriusModel.MONOGRAPH;
	}

	/**
	 * Gets the pages.
	 * 
	 * @return the pages
	 */
	@Override
	public ArrayList<PageDetail> getPages() {
		return pages;
	}

	/**
	 * Sets the pages.
	 * 
	 * @param pages
	 *          the new pages
	 */
	public void setPages(ArrayList<PageDetail> pages) {
		this.pages = pages;
	}

	public void setIntParts(ArrayList<InternalPartDetail> intParts) {
		this.intParts = intParts;
	}

	public void setMonUnits(ArrayList<MonographUnitDetail> monUnits) {
		this.monUnits = monUnits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#
	 * hasPages()
	 */
	@Override
	public boolean hasPages() {
		return true;
	}

	@Override
	public int hasContainers() {
		return 2;
	}

	@Override
	public List<? extends List<? extends AbstractDigitalObjectDetail>> getContainers() {
		return new ArrayList<ArrayList<? extends AbstractDigitalObjectDetail>>() {
			{
				add(intParts);
				add(monUnits);
			}
		};
	}

	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return new ArrayList<KrameriusModel>() {
			{
				add(KrameriusModel.INTERNALPART);
				add(KrameriusModel.MONOGRAPHUNIT);
			}
		};
	}

	// handle
	// policy

}
