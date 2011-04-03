/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
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

	/** The streams. */
	private Streams streams;

	/** The foxml. */
	private String foxml;

	/** The ocr. */
	private String ocr;

	/** The pages. */
	private List<PageDetail> pages;

	/** The related. */
	private ArrayList<ArrayList<String>> related;

	/** The uuid. */
	private String uuid;

	/** The dc changed. */
	private boolean dcChanged;

	/** The mods changed. */
	private boolean modsChanged;

	/** The ocr changed. */
	private boolean ocrChanged;

	/** The containers. */
	private ArrayList<List<? extends AbstractDigitalObjectDetail>> containers;

	/**
	 * Instantiates a new abstract digital object detail.
	 */
	public AbstractDigitalObjectDetail() {
	}

	/**
	 * Instantiates a new abstract digital object detail.
	 * 
	 * @param related
	 *          the related
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

	/**
	 * Gets the related.
	 * 
	 * @return the related
	 */
	public ArrayList<ArrayList<String>> getRelated() {
		return related;
	}

	/**
	 * Gets the streams.
	 * 
	 * @return the streams
	 */
	public Streams getStreams() {
		return this.streams;
	}

	/**
	 * Sets the streams.
	 * 
	 * @param streams
	 *          the new streams
	 */
	public void setStreams(Streams streams) {
		this.streams = streams;
	}

	/**
	 * Sets the dc.
	 * 
	 * @param dc
	 *          the new dc
	 */
	public void setDc(DublinCore dc) {
		if (getStreams() == null) {
			setStreams(new Streams());
		}
		getStreams().setDc(dc);
	}

	/**
	 * Gets the dc.
	 * 
	 * @return the dc
	 */
	public DublinCore getDc() {
		if (getStreams() != null)
			return getStreams().getDc();
		else
			return null;
	}

	/**
	 * Sets the mods.
	 * 
	 * @param mods
	 *          the new mods
	 */
	public void setMods(ModsCollectionClient mods) {
		if (getStreams() == null) {
			setStreams(new Streams());
		}
		getStreams().setMods(mods);
	}

	/**
	 * Gets the mods.
	 * 
	 * @return the mods
	 */
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

	/**
	 * Checks for pages.
	 * 
	 * @return true, if successful
	 */
	public abstract boolean hasPages();

	/**
	 * Checks if is image.
	 * 
	 * @return true, if is image
	 */
	public boolean isImage() {
		return false;
	}

	/**
	 * Checks for containers.
	 * 
	 * @return the int
	 */
	public abstract int hasContainers();

	/**
	 * Gets the pages.
	 * 
	 * @return the pages
	 */
	public List<PageDetail> getPages() {
		if (!hasPages())
			throw new UnsupportedOperationException();
		return pages;
	}

	/**
	 * Sets the pages.
	 * 
	 * @param pages
	 *          the new pages
	 */
	public void setPages(List<PageDetail> pages) {
		if (!hasPages())
			throw new UnsupportedOperationException();
		this.pages = pages;
	}

	// TODO: consider strategy DP
	/**
	 * Gets the child container models.
	 * 
	 * @return the child container models
	 */
	public abstract List<KrameriusModel> getChildContainerModels();

	/**
	 * Gets the containers.
	 * 
	 * @return the containers
	 */
	public List<List<? extends AbstractDigitalObjectDetail>> getContainers() {
		if (containers == null) {
			containers = new ArrayList<List<? extends AbstractDigitalObjectDetail>>();
		}
		return containers;
	}

	/**
	 * Sets the containers.
	 * 
	 * @param containers
	 *          the new containers
	 */
	public void setContainers(ArrayList<List<? extends AbstractDigitalObjectDetail>> containers) {
		this.containers = containers;
	}

	/**
	 * Gets the foxml.
	 * 
	 * @return the foxml
	 */
	public String getFoxml() {
		return foxml;
	}

	/**
	 * Sets the foxml.
	 * 
	 * @param foxml
	 *          the new foxml
	 */
	public void setFoxml(String foxml) {
		this.foxml = foxml;
	}

	/**
	 * Gets the ocr.
	 * 
	 * @return the ocr
	 */
	public String getOcr() {
		return ocr;
	}

	/**
	 * Sets the ocr.
	 * 
	 * @param ocr
	 *          the new ocr
	 */
	public void setOcr(String ocr) {
		this.ocr = ocr;
	}

	/**
	 * Sets the related.
	 * 
	 * @param related
	 *          the new related
	 */
	public void setRelated(ArrayList<ArrayList<String>> related) {
		this.related = related;
	}

	/**
	 * Gets the uuid.
	 * 
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 * 
	 * @param uuid
	 *          the new uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Checks if is dc changed.
	 * 
	 * @return true, if is dc changed
	 */
	public boolean isDcChanged() {
		return dcChanged;
	}

	/**
	 * Sets the dc changed.
	 * 
	 * @param dcChanged
	 *          the new dc changed
	 */
	public void setDcChanged(boolean dcChanged) {
		this.dcChanged = dcChanged;
	}

	/**
	 * Checks if is mods changed.
	 * 
	 * @return true, if is mods changed
	 */
	public boolean isModsChanged() {
		return modsChanged;
	}

	/**
	 * Sets the mods changed.
	 * 
	 * @param modsChanged
	 *          the new mods changed
	 */
	public void setModsChanged(boolean modsChanged) {
		this.modsChanged = modsChanged;
	}

	/**
	 * Checks if is ocr changed.
	 * 
	 * @return true, if is ocr changed
	 */
	public boolean isOcrChanged() {
		return ocrChanged;
	}

	/**
	 * Sets the ocr changed.
	 * 
	 * @param ocrChanged
	 *          the new ocr changed
	 */
	public void setOcrChanged(boolean ocrChanged) {
		this.ocrChanged = ocrChanged;
	}

}