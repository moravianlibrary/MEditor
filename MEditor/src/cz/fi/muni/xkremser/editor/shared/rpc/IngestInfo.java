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

package cz.fi.muni.xkremser.editor.shared.rpc;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class IngestInfo
        implements Serializable {

    private static final long serialVersionUID = -9182753231634245984L;
    private String directory;
    private List<String> pid = new ArrayList<String>();
    private List<String> username = new ArrayList<String>();
    private List<String> time = new ArrayList<String>();

    /**
     * 
     */

    public IngestInfo() {
    }

    /**
     * @param directory
     * @param pid
     * @param username
     * @param time
     */

    public IngestInfo(String directory, List<String> pid, List<String> username, List<String> time) {
        super();
        this.directory = directory;
        this.pid = pid;
        this.username = username;
        this.time = time;
    }

    /**
     * @return the directory
     */

    public String getDirectory() {
        return directory;
    }

    /**
     * @param directory
     *        the directory to set
     */

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * @return the pid
     */

    public List<String> getPid() {
        return pid;
    }

    /**
     * @param pid
     *        the pid to set
     */

    public void setPid(List<String> pid) {
        this.pid = pid;
    }

    /**
     * @return the username
     */

    public List<String> getUsername() {
        return username;
    }

    /**
     * @param username
     *        the username to set
     */

    public void setUsername(List<String> username) {
        this.username = username;
    }

    /**
     * @return the time
     */

    public List<String> getTime() {
        return time;
    }

    /**
     * @param time
     *        the time to set
     */

    public void setTime(List<String> time) {
        this.time = time;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "IngestInfo [directory=" + directory + ", pid=" + pid + ", username=" + username + ", time="
                + time + "]";
    }

}
