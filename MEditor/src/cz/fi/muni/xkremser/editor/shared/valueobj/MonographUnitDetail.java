/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class InternalPartDetail.
 */
public class MonographUnitDetail extends AbstractDigitalObjectDetail {

	// DC
	// title
	// creator
	// contributor
	// identifier - uuid
	// identifier - handle
	// type (= model:internalpart)
	// rights (treba policy:private)

	@SuppressWarnings("unused")
	private MonographUnitDetail() {
		super();
	}

	public MonographUnitDetail(ArrayList<ArrayList<String>> related) {
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
		return KrameriusModel.MONOGRAPHUNIT;
	}

	/** The pages. */
	private ArrayList<PageDetail> pages;

	private ArrayList<InternalPartDetail> intParts;

	/** The dc. */
	private DublinCore dc;

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

	public ArrayList<InternalPartDetail> getIntParts() {
		return intParts;
	}

	public void setIntParts(ArrayList<InternalPartDetail> intParts) {
		this.intParts = intParts;
	}

	/**
	 * Sets the dc.
	 * 
	 * @param dc
	 *          the new dc
	 */
	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.fi.muni.xkremser.editor.shared.valueobj.AbstractDigitalObjectDetail#
	 * getDc()
	 */
	@Override
	public DublinCore getDc() {
		return dc;
	}

	@Override
	public boolean hasPages() {
		return true;
	}

	@Override
	public int hasContainers() {
		return 1;
	}

	@Override
	public List<? extends List<? extends AbstractDigitalObjectDetail>> getContainers() {
		return new ArrayList<ArrayList<? extends AbstractDigitalObjectDetail>>() {
			{
				add(intParts);
			}
		};
	}

	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return new ArrayList<KrameriusModel>() {
			{
				add(KrameriusModel.INTERNALPART);
			}
		};
	}

	// handle
	// policy

}
