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
public class PeriodicalDetail extends AbstractDigitalObjectDetail {

	private ArrayList<PeriodicalVolumeDetail> perVolumes;

	@SuppressWarnings("unused")
	private PeriodicalDetail() {
		super();
	}

	public PeriodicalDetail(ArrayList<ArrayList<String>> related) {
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
		return KrameriusModel.PERIODICAL;
	}

	public void setPerVolumes(ArrayList<PeriodicalVolumeDetail> perVolumes) {
		this.perVolumes = perVolumes;
		getContainers().add(perVolumes);
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
		return 1;
	}

	@Override
	public List<KrameriusModel> getChildContainerModels() {
		return new ArrayList<KrameriusModel>() {
			{
				add(KrameriusModel.PERIODICALVOLUME);
			}
		};
	}

	// handle
	// policy

}
