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

package cz.mzk.editor.shared.rpc;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class IntervalStatisticData.
 * 
 * @author Matous Jobanek
 * @version Dec 18, 2012
 */
public class IntervalStatisticData
        implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2337809392314713678L;

    /** The user id. */
    private Long userId;

    /** The value. */
    private int value;

    /** The from date. */
    private EditorDate fromDate;

    /** The to date. */
    private EditorDate toDate;

    /**
     * Instantiates a new interval statistic data.
     */
    public IntervalStatisticData() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new interval statistic data.
     * 
     * @param userId
     *        the user id
     * @param value
     *        the value
     * @param fromDate
     *        the from date
     * @param toDate
     *        the to date
     */
    public IntervalStatisticData(Long userId, int value, EditorDate fromDate, EditorDate toDate) {
        super();
        this.userId = userId;
        this.value = value;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Gets the user id.
     * 
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets the from date.
     * 
     * @return the fromDate
     */
    public EditorDate getFromDate() {
        return fromDate;
    }

    /**
     * Gets the to date.
     * 
     * @return the toDate
     */
    public EditorDate getToDate() {
        return toDate;
    }

    /**
     * Sets the user id.
     * 
     * @param userId
     *        the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Sets the value.
     * 
     * @param value
     *        the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the from date.
     * 
     * @param fromDate
     *        the fromDate to set
     */
    public void setFromDate(EditorDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Sets the to date.
     * 
     * @param toDate
     *        the toDate to set
     */
    public void setToDate(EditorDate toDate) {
        this.toDate = toDate;
    }

    /**
     * To string.
     * 
     * @return the string {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IntervalStatisticData [userId=" + userId + ", value=" + value + ", fromDate=" + fromDate
                + ", toDate=" + toDate + "]";
    }

}
