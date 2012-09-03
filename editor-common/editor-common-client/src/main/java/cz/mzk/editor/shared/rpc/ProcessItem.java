/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2012  Martin Rumanek (martin.rumanek@mzk.cz)
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

/**
 * For quartz scheduler manager
 * 
 * @author Martin Rumanek
 * @version 30.8.2012
 */
public class ProcessItem
        implements Serializable {

    private static final long serialVersionUID = -2715147888347199197L;

    private String processGroup;

    private String processName;

    @SuppressWarnings("unused")
    private ProcessItem() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ProcessItem [processGroup=" + processGroup + ", processName=" + processName + "]";
    }

    /**
     * @param processGroup
     * @param processName
     */
    public ProcessItem(String processGroup, String processName) {
        super();
        this.processGroup = processGroup;
        this.processName = processName;
    }

    /**
     * @return the processGroup
     */
    public String getProcessGroup() {
        return processGroup;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

}
