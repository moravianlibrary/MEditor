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

package cz.fi.muni.xkremser.editor.shared.rpc.action;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

/**
 * @author Jiri Kremser
 * @version $Id$
 */
@GenDispatch(isSecure = false)
public class LockDigitalObject
        extends UnsecuredActionImpl<LockDigitalObjectResult> {

    /** The uuid of the digital object */
    @In(1)
    private String uuid;

    /** The description */
    @In(2)
    private String description;

    /**
     * Returns the code of the process result
     * <code>processResult &lt 0<code> if the process failed
     * <code>processResult &gt 0<code> if the lock has been created
     * <code>processResult == 0<code> if the lock has been updated
     */
    @Out(1)
    private int processResult;

}
