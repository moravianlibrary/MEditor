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
	private String ocr;
	private List<PageDetail> pages;
	private ArrayList<ArrayList<String>> related;
	private String uuid;
	private boolean dcChanged;
	private boolean modsChanged;
	private boolean ocrChanged;
	private ArrayList<List<? extends AbstractDigitalObjectDetail>> containers;

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

	public List<PageDetail> getPages() {
		if (!hasPages())
			throw new UnsupportedOperationException();
		return pages;
	}

	public void setPages(List<PageDetail> pages) {
		if (!hasPages())
			throw new UnsupportedOperationException();
		this.pages = pages;
	}

	// TODO: consider strategy DP
	public abstract List<KrameriusModel> getChildContainerModels();

	public List<List<? extends AbstractDigitalObjectDetail>> getContainers() {
		if (containers == null) {
			containers = new ArrayList<List<? extends AbstractDigitalObjectDetail>>();
		}
		return containers;
	}

	public void setContainers(ArrayList<List<? extends AbstractDigitalObjectDetail>> containers) {
		this.containers = containers;
	}

	public String getFoxml() {
		return foxml;
	}

	public void setFoxml(String foxml) {
		this.foxml = foxml;
	}

	public String getOcr() {
		return ocr;
	}

	public void setOcr(String ocr) {
		this.ocr = ocr;
	}

	public void setRelated(ArrayList<ArrayList<String>> related) {
		this.related = related;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isDcChanged() {
		return dcChanged;
	}

	public void setDcChanged(boolean dcChanged) {
		this.dcChanged = dcChanged;
	}

	public boolean isModsChanged() {
		return modsChanged;
	}

	public void setModsChanged(boolean modsChanged) {
		this.modsChanged = modsChanged;
	}

	public boolean isOcrChanged() {
		return ocrChanged;
	}

	public void setOcrChanged(boolean ocrChanged) {
		this.ocrChanged = ocrChanged;
	}

}