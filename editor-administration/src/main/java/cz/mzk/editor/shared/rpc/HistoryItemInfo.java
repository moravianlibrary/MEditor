/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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

package cz.mzk.editor.shared.rpc;

import java.io.Serializable;

import java.util.List;

import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version Oct 31, 2012
 */
public class HistoryItemInfo
        implements Serializable {

    private static final long serialVersionUID = 8547925472051690544L;

    private Long id;
    List<HistoryItemInfo> children;
    private String barcode;
    private String description;
    private String name;
    private DigitalObjectModel model;
    private String uuid;
    private boolean state;
    private String path;

    /**
     * Instantiates a new history item.
     */
    public HistoryItemInfo() {
    }

    /**
     * @param id
     * @param children
     * @param barcode
     * @param description
     * @param name
     * @param model
     * @param uuid
     * @param state
     * @param path
     */
    public HistoryItemInfo(Long id,
                           List<HistoryItemInfo> children,
                           String barcode,
                           String description,
                           String name,
                           DigitalObjectModel model,
                           String uuid,
                           boolean state,
                           String path) {
        super();
        this.id = id;
        this.children = children;
        this.barcode = barcode;
        this.description = description;
        this.name = name;
        this.model = model;
        this.uuid = uuid;
        this.state = state;
        this.path = path;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the children
     */
    public List<HistoryItemInfo> getChildren() {
        return children;
    }

    /**
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the model
     */
    public DigitalObjectModel getModel() {
        return model;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @return the state
     */
    public boolean isState() {
        return state;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param children
     *        the children to set
     */
    public void setChildren(List<HistoryItemInfo> children) {
        this.children = children;
    }

    /**
     * @param barcode
     *        the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * @param description
     *        the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param name
     *        the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param model
     *        the model to set
     */
    public void setModel(DigitalObjectModel model) {
        this.model = model;
    }

    /**
     * @param uuid
     *        the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @param state
     *        the state to set
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * @param path
     *        the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}