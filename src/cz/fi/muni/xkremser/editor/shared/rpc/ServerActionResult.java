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

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.util.Constants.SERVER_ACTION_RESULT;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

@SuppressWarnings("serial")
public class ServerActionResult
        implements Serializable {

    private Constants.SERVER_ACTION_RESULT serverActionResult;

    private String message;

    /**
      * 
      */

    public ServerActionResult() {
        super();
    }

    /**
     * @param serverActionResult
     * @param message
     */

    public ServerActionResult(SERVER_ACTION_RESULT serverActionResult, String message) {
        super();
        this.serverActionResult = serverActionResult;
        this.message = message;
    }

    /**
     * @param serverActionResult
     */

    public ServerActionResult(SERVER_ACTION_RESULT serverActionResult) {
        super();
        this.serverActionResult = serverActionResult;
    }

    /**
     * @return the serverActionResult
     */

    public Constants.SERVER_ACTION_RESULT getServerActionResult() {
        return serverActionResult;
    }

    /**
     * @param serverActionResult
     *        the serverActionResult to set
     */

    public void setServerActionResult(Constants.SERVER_ACTION_RESULT serverActionResult) {
        this.serverActionResult = serverActionResult;
    }

    /**
     * @return the message
     */

    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *        the message to set
     */

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "ServerActionResult [serverActionResult=" + serverActionResult + ", message=" + message + "]";
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((serverActionResult == null) ? 0 : serverActionResult.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ServerActionResult other = (ServerActionResult) obj;
        if (message == null) {
            if (other.message != null) return false;
        } else if (!message.equals(other.message)) return false;
        if (serverActionResult != other.serverActionResult) return false;
        return true;
    }

}
