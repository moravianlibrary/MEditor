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

package cz.fi.muni.xkremser.editor.client.view.window;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class NewObjectBasicInfoWindow
        extends UniversalWindow {

    /**
     * The DynamicForm which automatically sets items.
     */
    private static final class MyDynamicForm
            extends DynamicForm {

        /**
         * Items are used for set items in this DynamicForm
         * 
         * @param items
         */
        MyDynamicForm(FormItem... items) {
            this.setItems(items);
            for (FormItem item : items) {
                item.setWidth(220);
            }
            setExtraSpace(15);
        }
    }

    private TextItem dateIssuedItem = null;

    /**
     * @param structureTreeGrid
     * @param height
     * @param width
     * @param title
     */

    public NewObjectBasicInfoWindow(final ListGridRecord record, LangConstants lang) {
        super(120, 350, lang.menuEdit() + " " + record.getAttributeAsString(Constants.ATTR_TYPE) + ": "
                + record.getAttributeAsString(Constants.ATTR_NAME));

        setAlign(Alignment.LEFT);

        VLayout mainLayout = new VLayout();
        final TextItem nameItem = new TextItem("name", lang.name());
        nameItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NAME));

        if (record.getAttributeAsString(Constants.ATTR_TYPE_ID)
                .equals(DigitalObjectModel.PERIODICALITEM.getValue())
                || record.getAttributeAsString(Constants.ATTR_TYPE_ID)
                        .equals(DigitalObjectModel.PERIODICALVOLUME.getValue())) {

            dateIssuedItem = new TextItem("dateIssued", lang.dateIssued() + " " + lang.formYyyy());
            dateIssuedItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_DATE_ISSUED));
            mainLayout.addMember(new MyDynamicForm(nameItem, dateIssuedItem));
            setHeight(160);
        } else {
            mainLayout.addMember(new MyDynamicForm(nameItem));
        }

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setWidth100();
        buttonsLayout.setAlign(Alignment.RIGHT);

        Button cancelButton = new Button(lang.cancel());
        Button okButton = new Button("OK");

        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        cancelButton.setExtraSpace(5);

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String name = nameItem.getValueAsString();
                String dateIssued = null;
                if (dateIssuedItem != null) dateIssued = dateIssuedItem.getValueAsString();
                doSaveAction(record, name, dateIssued);
            }
        });
        okButton.setExtraSpace(5);

        buttonsLayout.addMember(cancelButton);
        buttonsLayout.addMember(okButton);
        mainLayout.addMember(buttonsLayout);
        addItem(mainLayout);

        centerInPage();
        show();
        focus();
    }

    protected abstract void doSaveAction(ListGridRecord record, String name, String dateIssued);
}
