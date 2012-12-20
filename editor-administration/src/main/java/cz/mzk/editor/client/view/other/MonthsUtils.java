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

package cz.mzk.editor.client.view.other;

import java.util.HashMap;
import java.util.Map;

import cz.mzk.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version Dec 20, 2012
 */
public class MonthsUtils {

    private static Map<Integer, String> months = null;

    public static Map<Integer, String> getMonths(LangConstants lang) {
        if (months == null) {
            setMonth(lang);
        }
        return months;
    }

    private static void setMonth(LangConstants lang) {
        months = new HashMap<Integer, String>(12);
        months.put(1, lang.january());
        months.put(2, lang.february());
        months.put(3, lang.march());
        months.put(4, lang.april());
        months.put(5, lang.may());
        months.put(6, lang.june());
        months.put(7, lang.july());
        months.put(8, lang.august());
        months.put(9, lang.september());
        months.put(10, lang.october());
        months.put(11, lang.november());
        months.put(12, lang.december());
    }

}
