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
 * The Class EditorDate.
 * 
 * @author Matous Jobanek
 * @version Oct 30, 2012
 */
public class EditorDate
        implements Serializable, Comparable<EditorDate> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6681571391228734180L;

    /** The day. */
    private int day;

    /** The month. */
    private int month;

    /** The year. */
    private int year;

    /**
     * Instantiates a new editor date.
     */
    public EditorDate() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new editor date.
     * 
     * @param day
     *        the day
     * @param month
     *        the mounth
     * @param year
     *        the year
     */
    public EditorDate(int day, int month, int year) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Gets the day.
     * 
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the year.
     * 
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the day.
     * 
     * @param day
     *        the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month
     *        the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Sets the year.
     * 
     * @param year
     *        the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + year;
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
        EditorDate other = (EditorDate) obj;
        if (day != other.day) return false;
        if (month != other.month) return false;
        if (year != other.year) return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "EditorDate [day=" + day + ", month=" + month + ", year=" + year + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(EditorDate date) {
        if (this.getYear() < date.getYear()) {
            return 1;
        } else if (this.getYear() > date.getYear()) {
            return -1;
        } else if (this.getMonth() < date.getMonth()) {
            return 1;
        } else if (this.getMonth() > date.getMonth()) {
            return -1;
        } else if (this.getDay() < date.getDay()) {
            return 1;
        } else if (this.getDay() > date.getDay()) {
            return -1;
        } else {
            return 0;
        }
    };

}
