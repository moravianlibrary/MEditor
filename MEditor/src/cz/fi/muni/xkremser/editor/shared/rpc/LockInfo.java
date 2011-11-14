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

import java.util.Arrays;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class LockInfo
        implements IsSerializable {

    /**
     * The name of the lock-owner
     * <code>lockOwner<code> is not empty when the digital object has already been locked by somebody else
     * <code>lockOwner == null<code> if the lock has been created
     * <code>"".equals(lockOwner)<code> if the lock has been updated
     */
    private String lockOwner;

    /** The description of the lock */
    private String lockDescription;

    /** The parsed time to expiration of the lock String[days,hours,minutes] */
    private String[] timeToExpiration;

    public LockInfo() {
        super();
    }

    public LockInfo(String lockOwner, String lockDescription, String[] timeToExpiration) {
        super();
        this.lockOwner = lockOwner;
        this.lockDescription = lockDescription;
        this.timeToExpiration = timeToExpiration;
    }

    /**
     * @return the lockOwner, The name of the lock-owner
     *         <code>lockOwner<code> is not empty when the digital object has already been locked by somebody else 
     *         <code>lockOwner == null<code> if the lock has been created right now, or there is now lock
     *         <code>"".equals(lockOwner)<code> if the lock has been updated, or the lock has been created by user
     */

    public String getLockOwner() {
        return lockOwner;
    }

    /**
     * @param lockOwner
     *        The name of the lock-owner to set
     *        <code>lockOwner<code> is not empty when the digital object has already been locked by somebody else
     *        <code>lockOwner == null<code> if the lock has been created right now, or there is now lock
     *        <code>"".equals(lockOwner)<code> if the lock has been updated, or the lock has been created by user
     */

    public void setLockOwner(String lockOwner) {
        this.lockOwner = lockOwner;
    }

    /**
     * @return the lockDescription
     */

    public String getLockDescription() {
        return lockDescription;
    }

    /**
     * @param lockDescription
     *        the lockDescription to set
     */

    public void setLockDescription(String lockDescription) {
        this.lockDescription = lockDescription;
    }

    /**
     * @return the timeToExpiration, The parsed time to expiration of the lock
     *         String[days,hours,minutes]
     */

    public String[] getTimeToExpiration() {
        return timeToExpiration;
    }

    /**
     * @param timeToExpiration
     *        The parsed time to expiration of the lock
     *        String[days,hours,minutes]
     */

    public void setTimeToExpiration(String[] timeToExpiration) {
        this.timeToExpiration = timeToExpiration;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "LockInfo [lockOwner=" + lockOwner + ", lockDescription=" + lockDescription
                + ", timeToExpiration=" + Arrays.toString(timeToExpiration) + "]";
    }

}
