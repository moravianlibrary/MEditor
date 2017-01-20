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

package cz.mzk.editor.client.view.window;

import com.smartgwt.client.widgets.form.fields.TextItem;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version May 7, 2012
 */
//TODO-MR hotfix #107 🐽 (turn off checking validation)
public class EditorDateItem extends TextItem {
    public EditorDateItem() {
        super();
    }

    public EditorDateItem(String name, Object ... drop) {
        super(name);
    }

    public String verify(Object ... drop) {
        return null;
    }

    public String getEditorDate() {
        return (getDisplayValue() != null) ? getDisplayValue() : "";
    }

    public String getDateFormatHint(LangConstants lang, DigitalObjectModel model) {
        return "";
    }
}