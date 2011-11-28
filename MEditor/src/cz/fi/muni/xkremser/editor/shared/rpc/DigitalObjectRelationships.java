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

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDigitalObjectDetail.
 */
public class DigitalObjectRelationships
        implements IsSerializable {

    private Map<String, List<DigitalObjectRelationships>> parents;
    private Map<String, List<DigitalObjectRelationships>> children;
    private String uuid;
    private int conflictCode = 0;

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
     * @return the conflictCode
     */

    public int getConflictCode() {
        return conflictCode;
    }

    /**
     * @param conflictCode
     *        the conflictCode to set
     */

    public void setConflictCode(int conflictCode) {
        this.conflictCode = conflictCode;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
                + ", conflictCode=" + conflictCode + "]";
    }

}