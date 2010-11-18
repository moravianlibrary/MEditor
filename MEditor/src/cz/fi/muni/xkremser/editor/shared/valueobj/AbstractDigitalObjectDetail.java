/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.shared.valueobj;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

import cz.fi.muni.xkremser.editor.client.KrameriusModel;
import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public abstract class AbstractDigitalObjectDetail implements IsSerializable {

	private Streams streams;
	private String foxml;
	private ArrayList<ArrayList<String>> related;

	public AbstractDigitalObjectDetail() {
	}

	/**
	 * Instantiates a new abstract digital object detail.
	 */
	public AbstractDigitalObjectDetail(ArrayList<ArrayList<String>> related) {
		this.related = related;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public abstract KrameriusModel getModel();

	public ArrayList<ArrayList<String>> getRelated() {
		return related;
	}

	public Streams getStreams() {
		return this.streams;
	}

	public void setStreams(Streams streams) {
		this.streams = streams;
	}

	public void setDc(DublinCore dc) {
		if (getStreams() == null) {
			setStreams(new Streams());
		}
		getStreams().setDc(dc);
	}

	public DublinCore getDc() {
		if (getStreams() != null)
			return getStreams().getDc();
		else
			return null;
	}

	public void setMods(ModsCollectionClient mods) {
		if (getStreams() == null) {
			setStreams(new Streams());
		}
		getStreams().setMods(mods);
	}

	public ModsCollectionClient getMods() {
		if (getStreams() != null)
			return getStreams().getMods();
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "model: " + getModel() + "\nStreams: " + getStreams();
	}

	public abstract boolean hasPages();

	public boolean isImage() {
		return false;
	}

	public abstract int hasContainers();

	public abstract List<PageDetail> getPages();

	// TODO: consider strategy DP
	public abstract List<KrameriusModel> getChildContainerModels();

	public abstract List<? extends List<? extends AbstractDigitalObjectDetail>> getContainers();

	public String getFoxml() {
		return foxml;
	}

	public void setFoxml(String foxml) {
		this.foxml = foxml;
	}

	public void setRelated(ArrayList<ArrayList<String>> related) {
		this.related = related;
	}
}