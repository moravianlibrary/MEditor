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

package cz.fi.muni.xkremser.editor.client.view.other;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class HtmlCode {

    /**
     * Adds the HTML <code> "b" <code> tags to the string
     * 
     * @param string
     * @return String with the HTML <code> "b" <code> tags
     */
    public static String bold(String string) {
        return "<b>" + string + "</b>";
    }

    /**
     * Adds the HTML <code> "i" <code> tags to the string
     * 
     * @param string
     * @return String with the HTML <code> "i" <code> tags
     */
    public static String italic(String string) {
        return "<i>" + string + "</i>";
    }

    /**
     * Adds the HTML <code> "u" <code> tags to the string
     * 
     * @param string
     * @return String with the HTML <code> "u" <code> tags
     */
    public static String underline(String string) {
        return "<u>" + string + "</u>";
    }

    /**
     * Adds the HTML
     * <code> "h1, h2, h3, ..." <code> tags to the string, where the number is titleLevel
     * 
     * @param string
     * @param titleLevel
     * @return String with the HTML <code> "h1, h2, h3, ..." <code> tags
     */
    public static String title(String string, int titleLevel) {
        return "<h" + titleLevel + ">" + string + "</h" + titleLevel + ">";
    }

    /**
     * Adds the HTML <code> "nobr" <code> tags to the string
     * 
     * @param string
     * @return String with the HTML <code> "nobr" <code> tags
     */
    public static String nobr(String string) {
        return "<nobr>" + string + "</nobr>";
    }

    /**
     * Adds the HTML tags to the string in order to turn it red
     * 
     * @param string
     * @return The red string with the HTML tags
     */
    public static String redFont(String string) {
        return "<span class='redFont'>" + string + "</span>";
    }

    /**
     * Adds the HTML tags to the string in order to turn it green
     * 
     * @param string
     * @return The green string with the HTML tags
     */
    public static String greenFont(String string) {
        return "<span class='greenFont'>" + string + "</span>";
    }
}
