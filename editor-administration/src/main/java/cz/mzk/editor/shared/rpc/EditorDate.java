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

    /** The hour. */
    private int hour = -1;

    /** The minute. */
    private int minute = -1;

    /** The second. */
    private int second = -1;

    /** The second. */
    private String timestamp = null;

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
     * Instantiates a new editor date.
     * 
     * @param day
     *        the day
     * @param month
     *        the month
     * @param year
     *        the year
     * @param hour
     *        the hour
     * @param minute
     *        the minute
     * @param second
     *        the second
     */
    public EditorDate(int day, int month, int year, int hour, int minute, int second) {
        super();
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /**
     * @param timestamp
     */
    public EditorDate(String timestamp) {
        super();
        this.timestamp = timestamp;
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
     * Gets the month.
     * 
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month.
     * 
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
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @return the second
     */
    public int getSecond() {
        return second;
    }

    /**
     * @param hour
     *        the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * @param minute
     *        the minute to set
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @param second
     *        the second to set
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *        the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + hour;
        result = prime * result + minute;
        result = prime * result + month;
        result = prime * result + second;
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
        if (hour != other.hour) return false;
        if (minute != other.minute) return false;
        if (month != other.month) return false;
        if (second != other.second) return false;
        if (timestamp == null) {
            if (other.timestamp != null) return false;
        } else if (!timestamp.equals(other.timestamp)) return false;
        if (year != other.year) return false;
        return true;
    }

    /**
     * To string.
     * 
     * @return the string {@inheritDoc}
     */
    @Override
    public String toString() {
        if (timestamp == null) {
            String day = addZero(getDay()) + ". " + addZero(getMonth()) + ". " + getYear();
            if (getHour() < 0 || getMinute() < 0 || getSecond() < 0) {
                return day;
            } else {
                return day + " " + addZero(getHour()) + ":" + addZero(getMinute()) + ":"
                        + addZero(getSecond());
            }
        } else {
            return timestamp;
        }
    }

    private String addZero(int number) {
        String numberString = String.valueOf(number);
        if (numberString.length() != 1) {
            return numberString;
        }
        return "0" + numberString;
    }

    /**
     * Compare to.
     * 
     * @param date
     *        the date
     * @return the int {@inheritDoc}
     */
    @Override
    public int compareTo(EditorDate date) {
        if (timestamp == null) {
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
            } else if (getHour() < 0 || getMinute() < 0 || getSecond() < 0) {
                return 0;
            } else if (this.getHour() < date.getHour()) {
                return 1;
            } else if (this.getHour() > date.getHour()) {
                return -1;
            } else if (this.getMinute() < date.getMinute()) {
                return 1;
            } else if (this.getMinute() > date.getMinute()) {
                return -1;
            } else if (this.getSecond() < date.getSecond()) {
                return 1;
            } else if (this.getSecond() > date.getSecond()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return this.timestamp.compareTo(date.getTimestamp());
        }
    };

}
