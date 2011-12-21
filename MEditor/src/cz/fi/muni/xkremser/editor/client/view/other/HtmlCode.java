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
     * Adds the HTML <code> "h4" <code> tags to the string
     * 
     * @param string
     * @return String with the HTML <code> "h4" <code> tags
     */
    public static String h4(String string) {
        return "<h4>" + string + "</h4>";
    }

}
