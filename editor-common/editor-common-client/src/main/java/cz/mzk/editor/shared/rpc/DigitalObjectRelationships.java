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

package cz.mzk.editor.shared.rpc;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public class DigitalObjectRelationships
        implements Serializable, Comparable<DigitalObjectRelationships> {

    private static final long serialVersionUID = 2599013877503944957L;
    private Map<String, List<DigitalObjectRelationships>> parents;
    private Map<String, List<DigitalObjectRelationships>> children;
    private String uuid;
    private Constants.CONFLICT conflict = Constants.CONFLICT.NO_CONFLICT;
    private String title;
    private DigitalObjectModel model;

    public DigitalObjectRelationships() {
    }

    public DigitalObjectRelationships(String uuid) {
        parents = new HashMap<String, List<DigitalObjectRelationships>>();
        children = new HashMap<String, List<DigitalObjectRelationships>>();
        this.uuid = uuid;
    }

    /**
     * @return the parents
     */

    public Map<String, List<DigitalObjectRelationships>> getParents() {
        return parents;
    }

    /**
     * @param parents
     *        the parents to set
     */

    public void setParents(Map<String, List<DigitalObjectRelationships>> parents) {
        this.parents = parents;
    }

    /**
     * @return the children
     */

    public Map<String, List<DigitalObjectRelationships>> getChildren() {
        return children;
    }

    /**
     * @param children
     *        the children to set
     */

    public void setChildren(Map<String, List<DigitalObjectRelationships>> children) {
        this.children = children;
    }

    /**
     * @return the uuid
     */

    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *        the uuid to set
     */

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the conflict
     */

    public Constants.CONFLICT getConflict() {
        return conflict;
    }

    /**
     * @param conflict
     *        the conflict to set
     */

    public void setConflict(Constants.CONFLICT conflict) {
        this.conflict = conflict;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *        the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the model
     */
    public DigitalObjectModel getModel() {
        return model;
    }

    /**
     * @param model
     *        the model to set
     */
    public void setModel(DigitalObjectModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((children == null) ? 0 : children.hashCode());
        result = prime * result + ((conflict == null) ? 0 : conflict.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((parents == null) ? 0 : parents.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        DigitalObjectRelationships other = (DigitalObjectRelationships) obj;
        if (children == null) {
            if (other.children != null) return false;
        } else if (!children.equals(other.children)) return false;
        if (conflict != other.conflict) return false;
        if (model != other.model) return false;
        if (parents == null) {
            if (other.parents != null) return false;
        } else if (!parents.equals(other.parents)) return false;
        if (title == null) {
            if (other.title != null) return false;
        } else if (!title.equals(other.title)) return false;
        if (uuid == null) {
            if (other.uuid != null) return false;
        } else if (!uuid.equals(other.uuid)) return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DigitalObjectRelationships [parents=" + parents + ", children=" + children + ", uuid=" + uuid
                + ", conflict=" + conflict + ", title=" + title + ", model=" + model + "]";
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int compareTo(DigitalObjectRelationships toCompare) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (toCompare == null) return AFTER;
        if (this.equals(toCompare)) return EQUAL;

        if (this.getConflict().getConflictCode() > toCompare.getConflict().getConflictCode()) {
            return BEFORE;
        } else if (this.getConflict().getConflictCode() < toCompare.getConflict().getConflictCode()) {
            return AFTER;
        } else {
            return this.getUuid().compareTo(toCompare.getUuid());
        }
    }
}