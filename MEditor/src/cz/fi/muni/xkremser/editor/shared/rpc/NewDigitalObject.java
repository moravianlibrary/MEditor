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

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */

public class NewDigitalObject
        implements Serializable {

    private static final long serialVersionUID = -1473902336327167367L;
    private int orderIndex;
    private String name;
    private ArrayList<NewDigitalObject> children = new ArrayList<NewDigitalObject>();
    private DigitalObjectModel model;
    private MetadataBundle bundle;
    private String uuid;
    private boolean exist;

    @SuppressWarnings("unused")
    private NewDigitalObject() {
        // because of serialization
    }

    public NewDigitalObject(int orderIndex,
                            String name,
                            DigitalObjectModel model,
                            MetadataBundle bundle,
                            String uuid,
                            boolean exist) {
        super();
        this.orderIndex = orderIndex;
        this.name = name;
        this.model = model;
        this.bundle = bundle;
        this.uuid = uuid;
        this.exist = exist;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
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

    public List<NewDigitalObject> getChildren() {
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

}
