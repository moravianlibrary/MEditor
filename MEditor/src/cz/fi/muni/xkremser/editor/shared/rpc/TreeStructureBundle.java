/*
 * Metadata Editor
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

import java.io.Serializable;

import java.util.List;

/**
 * @author Jiri Kremser
 * @version 1.2.2012
 */
public class TreeStructureBundle
        implements Serializable {

    private static final long serialVersionUID = 2195955464508894443L;

    private TreeStructureInfo info;

    private List<TreeStructureNode> nodes;

    public TreeStructureBundle() {

    }

    public TreeStructureBundle(TreeStructureInfo info, List<TreeStructureNode> nodes) {
        super();
        this.info = info;
        this.nodes = nodes;
    }

    public TreeStructureInfo getInfo() {
        return info;
    }

    public void setInfo(TreeStructureInfo info) {
        this.info = info;
    }

    public List<TreeStructureNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeStructureNode> nodes) {
        this.nodes = nodes;
    }

    public static class TreeStructureInfo
            implements Serializable {

        private static final long serialVersionUID = 5860881968686539047L;

        private long id;

        private String created;

        private String description;

        private String barcode;

        public TreeStructureInfo() {

        }

        public TreeStructureInfo(long id, String created, String description, String barcode) {
            super();
            this.id = id;
            this.created = created;
            this.description = description;
            this.barcode = barcode;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }

    public static class TreeStructureNode
            implements Serializable {

        private static final long serialVersionUID = -3963356121173217743L;
        private String propId;
        private String propParent;
        private String propName;
        private String propPicture;
        private String propType;
        private String propTypeId;
        private String propPageType;
        private String propDateIssued;
        private String propAltoPath;
        private String propOcrPath;
        private boolean propExist;

        public TreeStructureNode() {

        }

        public TreeStructureNode(String propId,
                                 String propParent,
                                 String propName,
                                 String propPicture,
                                 String propType,
                                 String propTypeId,
                                 String propPageType,
                                 String propDateIssued,
                                 String propAltoPath,
                                 String propOcrPath,
                                 boolean propExist) {
            super();
            this.propId = propId;
            this.propParent = propParent;
            this.propName = propName;
            this.propPicture = propPicture;
            this.propType = propType;
            this.propTypeId = propTypeId;
            this.propPageType = propPageType;
            this.propDateIssued = propDateIssued;
            this.propAltoPath = propAltoPath;
            this.propOcrPath = propOcrPath;
            this.propExist = propExist;
        }

        public String getPropId() {
            return propId;
        }

        public void setPropId(String propId) {
            this.propId = propId;
        }

        public String getPropParent() {
            return propParent;
        }

        public void setPropParent(String propParent) {
            this.propParent = propParent;
        }

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public String getPropPicture() {
            return propPicture;
        }

        public void setPropPicture(String propPicture) {
            this.propPicture = propPicture;
        }

        public String getPropType() {
            return propType;
        }

        public void setPropType(String propType) {
            this.propType = propType;
        }

        public String getPropTypeId() {
            return propTypeId;
        }

        public void setPropTypeId(String propTypeId) {
            this.propTypeId = propTypeId;
        }

        public String getPropPageType() {
            return propPageType;
        }

        public void setPropPageType(String propPageType) {
            this.propPageType = propPageType;
        }

        public String getPropDateIssued() {
            return propDateIssued;
        }

        public void setPropDateIssued(String propDateIssued) {
            this.propDateIssued = propDateIssued;
        }

        public String getPropAltoPath() {
            return propAltoPath;
        }

        public void setPropAltoPath(String propAltoPath) {
            this.propAltoPath = propAltoPath;
        }

        public String getPropOcrPath() {
            return propOcrPath;
        }

        public void setPropOcrPath(String propOcrPath) {
            this.propOcrPath = propOcrPath;
        }

        public boolean getPropExist() {
            return propExist;
        }

        public void setPropExist(boolean propExist) {
            this.propExist = propExist;
        }
    }

}