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

package cz.mzk.editor.shared.rpc;

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

    public static class TreeStructureNode
            implements Serializable {

        private static final long serialVersionUID = -3963356121173217743L;
        private String propId;
        private String propParent;
        private String propName;
        private String propPictureOrUuid;
        private String propModelId;
        private String propType;
        private String propDateOrIntPartName;
        private String propNoteOrIntSubtitle;
        private String propPartNumberOrAlto;
        private String propAditionalInfoOrOcr;
        private boolean propExist;

        public TreeStructureNode() {

        }

        public TreeStructureNode(String propId,
                                 String propParent,
                                 String propName,
                                 String propPictureOrUuid,
                                 String propModelId,
                                 String propType,
                                 String propDateOrIntPartName,
                                 String propNoteOrIntSubtitle,
                                 String propPartNumberOrAlto,
                                 String propAditionalInfoOrOcr,
                                 boolean propExist) {
            super();
            this.propId = propId;
            this.propParent = propParent;
            this.propName = propName;
            this.propPictureOrUuid = propPictureOrUuid;
            this.propModelId = propModelId;
            this.propType = propType;
            this.propDateOrIntPartName = propDateOrIntPartName;
            this.propNoteOrIntSubtitle = propNoteOrIntSubtitle;
            this.propPartNumberOrAlto = propPartNumberOrAlto;
            this.propAditionalInfoOrOcr = propAditionalInfoOrOcr;
            this.propExist = propExist;
        }

        /**
         * @return the propId
         */
        public String getPropId() {
            return propId;
        }

        /**
         * @param propId
         *        the propId to set
         */
        public void setPropId(String propId) {
            this.propId = propId;
        }

        /**
         * @return the propParent
         */
        public String getPropParent() {
            return propParent;
        }

        /**
         * @param propParent
         *        the propParent to set
         */
        public void setPropParent(String propParent) {
            this.propParent = propParent;
        }

        /**
         * @return the propName
         */
        public String getPropName() {
            return propName;
        }

        /**
         * @param propName
         *        the propName to set
         */
        public void setPropName(String propName) {
            this.propName = propName;
        }

        /**
         * @return the propPictureOrUuid
         */
        public String getPropPictureOrUuid() {
            return propPictureOrUuid;
        }

        /**
         * @param propPictureOrUuid
         *        the propPictureOrUuid to set
         */
        public void setPropPictureOrUuid(String propPictureOrUuid) {
            this.propPictureOrUuid = propPictureOrUuid;
        }

        /**
         * @return the propModelId
         */
        public String getPropModelId() {
            return propModelId;
        }

        /**
         * @param propModelId
         *        the propModelId to set
         */
        public void setPropModelId(String propModelId) {
            this.propModelId = propModelId;
        }

        /**
         * @return the propType
         */
        public String getPropType() {
            return propType;
        }

        /**
         * @param propType
         *        the propType to set
         */
        public void setPropType(String propType) {
            this.propType = propType;
        }

        /**
         * @return the propDateOrIntPartName
         */
        public String getPropDateOrIntPartName() {
            return propDateOrIntPartName;
        }

        /**
         * @param propDateOrIntPartName
         *        the propDateOrIntPartName to set
         */
        public void setPropDateOrIntPartName(String propDateOrIntPartName) {
            this.propDateOrIntPartName = propDateOrIntPartName;
        }

        /**
         * @return the propNoteOrIntSubtitle
         */
        public String getPropNoteOrIntSubtitle() {
            return propNoteOrIntSubtitle;
        }

        /**
         * @param propNoteOrIntSubtitle
         *        the propNoteOrIntSubtitle to set
         */
        public void setPropNoteOrIntSubtitle(String propNoteOrIntSubtitle) {
            this.propNoteOrIntSubtitle = propNoteOrIntSubtitle;
        }

        /**
         * @return the propPartNumberOrAlto
         */
        public String getPropPartNumberOrAlto() {
            return propPartNumberOrAlto;
        }

        /**
         * @param propPartNumberOrAlto
         *        the propPartNumberOrAlto to set
         */
        public void setPropPartNumberOrAlto(String propPartNumberOrAlto) {
            this.propPartNumberOrAlto = propPartNumberOrAlto;
        }

        /**
         * @return the propAditionalInfoOrOcr
         */
        public String getPropAditionalInfoOrOcr() {
            return propAditionalInfoOrOcr;
        }

        /**
         * @param propAditionalInfoOrOcr
         *        the propAditionalInfoOrOcr to set
         */
        public void setPropAditionalInfoOrOcr(String propAditionalInfoOrOcr) {
            this.propAditionalInfoOrOcr = propAditionalInfoOrOcr;
        }

        /**
         * @return the propExist
         */
        public boolean isPropExist() {
            return propExist;
        }

        /**
         * @param propExist
         *        the propExist to set
         */
        public void setPropExist(boolean propExist) {
            this.propExist = propExist;
        }

    }

}