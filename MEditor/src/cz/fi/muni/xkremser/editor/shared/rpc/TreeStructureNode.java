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

/**
 * @author Jiri Kremser
 * @version 25.1.2012
 */
public class TreeStructureNode
        implements Serializable {

    private static final long serialVersionUID = 3329911055024751310L;
    private String propId;
    private String propParent;
    private String propName;
    private String propPicture;
    private String propType;
    private String propTypeId;
    private String propPageType;
    private String propDateIssued;
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

    public boolean getPropExist() {
        return propExist;
    }

    public void setPropExist(boolean propExist) {
        this.propExist = propExist;
    }

}