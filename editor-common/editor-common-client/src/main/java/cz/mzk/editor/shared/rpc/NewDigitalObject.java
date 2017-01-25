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

import java.util.ArrayList;

import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */

public class NewDigitalObject
        implements Serializable {

    private static final long serialVersionUID = -8145898237441105426L;
    private int pageIndex;
    private String name;
    private ArrayList<NewDigitalObject> children = new ArrayList<NewDigitalObject>();
    private DigitalObjectModel model;
    private MetadataBundle bundle;
    private String uuid;
    private boolean exist;
    private String sysno;
    private String path;
    private boolean visible;
    private String type;
    private String dateOrIntPartName;
    private String noteOrIntSubtitle;
    private String partNumberOrAlto;
    private String aditionalInfoOrOcr;
    private String base;
    private String signature;
    private String thumbnail;

    @SuppressWarnings("unused")
    private NewDigitalObject() {
        // because of serialization
    }

    public NewDigitalObject(String name) {
        super();
        this.name = name;
    }

    public NewDigitalObject(int pageIndex,
                            String name,
                            DigitalObjectModel model,
                            MetadataBundle bundle,
                            String uuid,
                            boolean exist) {
        super();
        this.pageIndex = pageIndex;
        this.name = name;
        this.model = model;
        this.bundle = bundle;
        this.uuid = uuid;
        this.exist = exist;
    }

    public NewDigitalObject(NewDigitalObject newDigitalObject) {
        super();
        this.pageIndex = newDigitalObject.getPageIndex();
        this.name = newDigitalObject.getName();
        this.children = newDigitalObject.getChildren();
        this.model = newDigitalObject.getModel();
        this.bundle = newDigitalObject.getBundle();
        this.uuid = newDigitalObject.getUuid();
        this.exist = newDigitalObject.getExist();
        this.sysno = newDigitalObject.getSysno();
        this.path = newDigitalObject.getPath();
        this.visible = newDigitalObject.getVisible();
        this.type = newDigitalObject.getType();
        this.dateOrIntPartName = newDigitalObject.getDateOrIntPartName();
        this.noteOrIntSubtitle = newDigitalObject.getNoteOrIntSubtitle();
        this.partNumberOrAlto = newDigitalObject.partNumberOrAlto;
        this.aditionalInfoOrOcr = newDigitalObject.getAditionalInfoOrOcr();
        this.base = newDigitalObject.getBase();
        this.signature = newDigitalObject.getSignature();
    }

    /**
     * @return the sysno
     */

    public String getSysno() {
        // ☢️ mzk03 sysno
        return sysno.replaceFirst("^mzk03", "");
    }

    /**
     * @param sysno
     *        the sysno to set
     */

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }

    /**
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base
     *        the base to set
     */
    public void setBase(String base) {
        this.base = base;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public DigitalObjectModel getModel() {
        return model;
    }

    public void setModel(DigitalObjectModel model) {
        this.model = model;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<NewDigitalObject> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<NewDigitalObject> children) {
        this.children = children;
    }

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MetadataBundle getBundle() {
        return bundle;
    }

    public void setBundle(MetadataBundle bundle) {
        this.bundle = bundle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *        the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return the type
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return the type
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return the dateOrIntPartName
     */
    public String getDateOrIntPartName() {
        return dateOrIntPartName;
    }

    /**
     * @param dateOrIntPartName
     *        the dateOrIntPartName to set
     */
    public void setDateOrIntPartName(String dateOrIntPartName) {
        this.dateOrIntPartName = dateOrIntPartName;
    }

    /**
     * @return the noteOrIntSubtitle
     */
    public String getNoteOrIntSubtitle() {
        return noteOrIntSubtitle;
    }

    /**
     * @param noteOrIntSubtitle
     *        the noteOrIntSubtitle to set
     */
    public void setNoteOrIntSubtitle(String noteOrIntSubtitle) {
        this.noteOrIntSubtitle = noteOrIntSubtitle;
    }

    /**
     * @return the partNumberOrAlto
     */
    public String getPartNumberOrAlto() {
        return partNumberOrAlto;
    }

    /**
     * @param partNumberOrAlto
     *        the partNumberOrAlto to set
     */
    public void setPartNumberOrAlto(String partNumberOrAlto) {
        this.partNumberOrAlto = partNumberOrAlto;
    }

    /**
     * @return the aditionalInfoOrOcr
     */
    public String getAditionalInfoOrOcr() {
        return aditionalInfoOrOcr;
    }

    /**
     * @param aditionalInfoOrOcr
     *        the aditionalInfoOrOcr to set
     */
    public void setAditionalInfoOrOcr(String aditionalInfoOrOcr) {
        this.aditionalInfoOrOcr = aditionalInfoOrOcr;
    }

    @Override
    public String toString() {
        return model + "  --  " + name;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature
     *        the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

}
