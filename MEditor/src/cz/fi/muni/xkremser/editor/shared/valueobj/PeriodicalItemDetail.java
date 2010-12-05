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
public class PeriodicalItemDetail extends AbstractDigitalObjectDetail {

	// DC
	// title
	// creator
	// contributor
	// identifier - uuid
	// identifier - handle
	// type (= model:internalpart)
	// rights (treba policy:private)

	@SuppressWarnings("unused")
	private PeriodicalItemDetail() {
		super();
	}

	public PeriodicalItemDetail(ArrayList<ArrayList<String>> related) {
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
		return KrameriusModel.PERIODICALITEM;
	}

	private ArrayList<InternalPartDetail> intParts;

	public ArrayList<InternalPartDetail> getIntParts() {
		return intParts;
	}

	public void setIntParts(ArrayList<InternalPartDetail> intParts) {
		this.intParts = intParts;
		getContainers().add(intParts);
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
