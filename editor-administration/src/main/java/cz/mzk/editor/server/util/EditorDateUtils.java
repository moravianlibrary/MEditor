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

package cz.mzk.editor.server.util;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

import cz.mzk.editor.shared.rpc.EditorDate;

/**
 * @author Matous Jobanek
 * @version Dec 18, 2012
 */
public class EditorDateUtils {

    public static final SimpleDateFormat FORMATTER_TO_DAY = new SimpleDateFormat("dd");
    public static final SimpleDateFormat FORMATTER_TO_MOUNTH = new SimpleDateFormat("MM");
    public static final SimpleDateFormat FORMATTER_TO_YEAR = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat FORMATTER_TO_HOUR = new SimpleDateFormat("HH");
    public static final SimpleDateFormat FORMATTER_TO_MINUTE = new SimpleDateFormat("mm");
    public static final SimpleDateFormat FORMATTER_TO_SECOND = new SimpleDateFormat("ss");

    public static EditorDate getEditorDate(Timestamp timestamp, boolean onlyDay) {
        EditorDate date =
                new EditorDate(Integer.parseInt(FORMATTER_TO_DAY.format(timestamp)),
                               Integer.parseInt(FORMATTER_TO_MOUNTH.format(timestamp)),
                               Integer.parseInt(FORMATTER_TO_YEAR.format(timestamp)));
        if (!onlyDay) {
            date.setHour(Integer.parseInt(FORMATTER_TO_HOUR.format(timestamp)));
            date.setMinute(Integer.parseInt(FORMATTER_TO_MINUTE.format(timestamp)));
            date.setSecond(Integer.parseInt(FORMATTER_TO_SECOND.format(timestamp)));
        }
        return date;
    }

    public static EditorDate getEditorDate(Date date, boolean onlyDay) {
        EditorDate editorDate =
                new EditorDate(Integer.parseInt(FORMATTER_TO_DAY.format(date)),
                               Integer.parseInt(FORMATTER_TO_MOUNTH.format(date)),
                               Integer.parseInt(FORMATTER_TO_YEAR.format(date)));
        if (!onlyDay) {
            editorDate.setHour(Integer.parseInt(FORMATTER_TO_HOUR.format(date)));
            editorDate.setMinute(Integer.parseInt(FORMATTER_TO_MINUTE.format(date)));
            editorDate.setSecond(Integer.parseInt(FORMATTER_TO_SECOND.format(date)));
        }
        return editorDate;
    }

    public static String getStringTimestamp(EditorDate date) {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
    }

}
