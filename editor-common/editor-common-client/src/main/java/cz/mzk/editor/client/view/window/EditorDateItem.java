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

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DateItemSelectorFormat;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;

/**
 * @author Matous Jobanek
 * @version May 7, 2012
 */
public class EditorDateItem
        extends DateTimeItem {

    /**
     * 
     */
    public EditorDateItem() {
        super();
        setFormating();
    }

    public EditorDateItem(String name) {
        super(name);
        setFormating();
    }

    public EditorDateItem(String name, String title) {
        super(name, title);
        setFormating();
    }

    /**
     * 
     */
    private void setFormating() {
        setUseTextField(true);
        setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
        setInputFormat("dd.MM.yyyy");
        setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
        setEditorValueFormatter(new FormItemValueFormatter() {

            @Override
            public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {

                String date = DateTimeFormat.getFormat("dd/MM/yyyy").format((Date) value);
                return date.replaceAll("/", ".");
            }
        });

        setHoverOpacity(75);
        setHoverWidth(330);
        setHoverStyle("interactImageHover");
    }
}
