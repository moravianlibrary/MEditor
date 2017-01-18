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

import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class StoredItem
        implements Serializable {

    private static final long serialVersionUID = -8933900314317857197L;

    /** The file_name. */
    private String fileName;

    /** The uuid. */
    private String uuid;

    /** The model. */
    private DigitalObjectModel model;

    /** The description. */
    private String description;

    /** The date of the storing */
    private String storedDate;

    /** The id. */
    private Long id;

    /**
     * Instantiates a digital object basic inform.
     */
    public StoredItem() {

    }

    /**
     * Instantiates a new stored item.
     * 
     * @param id
     *        the id
     * @param fileName
     *        the file name
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param description
     *        the description
     * @param storedDate
     *        the stored date
     */
    public StoredItem(Long id,
                      String fileName,
                      String uuid,
                      DigitalObjectModel model,
                      String description,
                      String storedDate) {
        super();
        this.fileName = fileName;
        this.uuid = uuid;
        this.model = model;
        this.description = description;
        this.storedDate = storedDate;
        this.id = id;
    }

    /**
     * Instantiates a new stored item.
     * 
     * @param id
     *        the id
     * @param fileName
     *        the file name
     */
    public StoredItem(Long id, String fileName) {
        super();
        this.id = id;
        this.fileName = fileName;
    }

    /**
     * Instantiates a new stored item.
     * 
     * @param fileName
     *        the file name
     * @param uuid
     *        the uuid
     * @param model
     *        the model
     * @param description
     *        the description
     * @param storedDate
     *        the stored date
     */
    public StoredItem(String fileName,
                      String uuid,
                      DigitalObjectModel model,
                      String description,
                      String storedDate) {
        super();
        this.fileName = fileName;
        this.uuid = uuid;
        this.model = model;
        this.description = description;
        this.storedDate = storedDate;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @return the model
     */
    public DigitalObjectModel getModel() {
        return model;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the storedDate
     */
    public String getStoredDate() {
        return storedDate;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param fileName
     *        the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param uuid
     *        the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @param model
     *        the model to set
     */
    public void setModel(DigitalObjectModel model) {
        this.model = model;
    }

    /**
     * @param description
     *        the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param storedDate
     *        the storedDate to set
     */
    public void setStoredDate(String storedDate) {
        this.storedDate = storedDate;
    }

    /**
     * @param id
     *        the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((storedDate == null) ? 0 : storedDate.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StoredItem other = (StoredItem) obj;
        if (description == null) {
            if (other.description != null) return false;
        } else if (!description.equals(other.description)) return false;
        if (fileName == null) {
            if (other.fileName != null) return false;
        } else if (!fileName.equals(other.fileName)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (model != other.model) return false;
        if (storedDate == null) {
            if (other.storedDate != null) return false;
        } else if (!storedDate.equals(other.storedDate)) return false;
        if (uuid == null) {
            if (other.uuid != null) return false;
        } else if (!uuid.equals(other.uuid)) return false;
        return true;
    }

}
