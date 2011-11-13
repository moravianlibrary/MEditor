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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 29.10.2011
 */

public class NewDigitalObject {

    private int orderIndex;
    private String name;
    private final List<NewDigitalObject> children = new ArrayList<NewDigitalObject>();
    private DigitalObjectModel model;
    private DublinCore dc;
    private ModsCollectionClient mods;
    private String uuid;
    private boolean exist;

    public NewDigitalObject(int orderIndex,
                            String name,
                            DigitalObjectModel model,
                            DublinCore dc,
                            ModsCollectionClient mods,
                            String uuid,
                            boolean exist) {
        super();
        this.orderIndex = orderIndex;
        this.name = name;
        this.model = model;
        this.dc = dc;
        this.mods = mods;
        this.uuid = uuid;
        this.exist = exist;
    }

    @Override
    public String toString() {
        return orderIndex + ". " + model + " title: "
                + (dc != null && dc.getTitle() != null ? dc.getTitle().get(0) : "no title");
    }

    public static String toStringTree(NewDigitalObject node) {
        final StringBuilder buffer = new StringBuilder();
        return toStringTreeHelper(node, buffer, new LinkedList<Iterator<NewDigitalObject>>()).toString();
    }

    private static String toStringTreeDrawLines(List<Iterator<NewDigitalObject>> parentIterators,
                                                boolean amLast) {
        StringBuilder result = new StringBuilder();
        Iterator<Iterator<NewDigitalObject>> it = parentIterators.iterator();
        while (it.hasNext()) {
            Iterator<NewDigitalObject> anIt = it.next();
            if (anIt.hasNext() || (!it.hasNext() && amLast)) {
                result.append("   |");
            } else {
                result.append("    ");
            }
        }
        return result.toString();
    }

    private static StringBuilder toStringTreeHelper(NewDigitalObject node,
                                                    StringBuilder buffer,
                                                    List<Iterator<NewDigitalObject>> parentIterators) {
        if (!parentIterators.isEmpty()) {
            boolean amLast = !parentIterators.get(parentIterators.size() - 1).hasNext();
            buffer.append("\n");
            String lines = toStringTreeDrawLines(parentIterators, amLast);
            buffer.append(lines);
            buffer.append("\n");
            buffer.append(lines);
            buffer.append("- ");
        }
        buffer.append(node.toString());
        if (node.children != null && node.children.size() > 0) {
            Iterator<NewDigitalObject> it = node.children.iterator();
            parentIterators.add(it);
            while (it.hasNext()) {
                NewDigitalObject child = it.next();
                toStringTreeHelper(child, buffer, parentIterators);
            }
            parentIterators.remove(it);
        }
        return buffer;
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

    public DublinCore getDc() {
        return dc;
    }

    public void setDc(DublinCore dc) {
        this.dc = dc;
    }

    public ModsCollectionClient getMods() {
        return mods;
    }

    public void setMods(ModsCollectionClient mods) {
        this.mods = mods;
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
}
