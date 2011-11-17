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
 * @version 16.11.2011
 */
public class MarcSpecificMetadata
        implements Serializable {

    private static final long serialVersionUID = 3831089519749784686L;
    private String sysno;
    private String base;
    private String location;
    private List<String> udcs;
    private String topic;
    private String place;
    private String publisher;
    private String dateIssued;

    @SuppressWarnings("unused")
    private MarcSpecificMetadata() {
        // because of serialization
    }

    public MarcSpecificMetadata(String sysno,
                                String base,
                                String location,
                                List<String> udcs,
                                String topic,
                                String place,
                                String publisher,
                                String dateIssued) {
        this.sysno = sysno;
        this.base = base;
        this.location = location;
        this.udcs = udcs;
        this.topic = topic;
        this.place = place;
        this.publisher = publisher;
        this.dateIssued = dateIssued;
    }

    public String getSysno() {
        return sysno;
    }

    public void setSysno(String sysno) {
        this.sysno = sysno;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getUdcs() {
        return udcs;
    }

    public void setUdcs(List<String> udcs) {
        this.udcs = udcs;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

}
