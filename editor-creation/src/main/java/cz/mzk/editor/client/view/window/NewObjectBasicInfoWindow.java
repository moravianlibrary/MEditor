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

import java.util.LinkedHashMap;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.LabelAndModelConverter;
import cz.mzk.editor.shared.domain.DigitalObjectModel;


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
                item.setWidth(300);
            }
            setExtraSpace(15);
        }
    }

    private final TextItem dateIssuedItem = null;
    private TextAreaItem noteItem = null;
    private SelectItem pageTypeItem = null;
    private final TextItem issueNumberItem = null;
    private final SelectItem genreTypeItem = null;

    /**
     * @param structureTreeGrid
     * @param height
     * @param width
     * @param title
     */

    public NewObjectBasicInfoWindow(final ListGridRecord record, LangConstants lang, EventBus eventBus) {
        super(120, 420, lang.menuEdit()
                + " "
                + LabelAndModelConverter.getLabelFromModel()
                        .get(record.getAttributeAsString(Constants.ATTR_MODEL_ID)) + ": "
                + record.getAttributeAsString(Constants.ATTR_NAME), eventBus, 10);

        setAlign(Alignment.LEFT);

        VLayout mainLayout = new VLayout();
        final TextItem nameItem = new TextItem("name", lang.name());
        nameItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NAME));
        boolean isPeriodicalItem =
                record.getAttributeAsString(Constants.ATTR_MODEL_ID)
                        .equals(DigitalObjectModel.PERIODICALITEM.getValue());
        if (isPeriodicalItem
                || record.getAttributeAsString(Constants.ATTR_MODEL_ID)
                        .equals(DigitalObjectModel.PERIODICALVOLUME.getValue())) {

            //            issueNumberItem = new TextItem("issueNumber", lang.issueNumber());
            //            issueNumberItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_SEQUENCE_NUMBER));
            //
            //            dateIssuedItem = new TextItem("dateIssued", lang.dateIssued());
            //            dateIssuedItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_DATE_ISSUED));
            //
            //            noteItem = new TextAreaItem("note", lang.note());
            //            noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE));
            //            noteItem.setHeight(60);
            //
            //            if (isPeriodicalItem) {
            //                genreTypeItem = new SelectItem();
            //                genreTypeItem.setWidth(100);
            //                genreTypeItem.setTitle(lang.dcType());
            //
            //                LinkedHashMap<String, String> valueMap =
            //                        new LinkedHashMap<String, String>(Constants.GENRE_TYPES.MAP);
            //                genreTypeItem.setValueMap(valueMap);
            //                String genreType = record.getAttribute(Constants.ATTR_GENRE_TYPE);
            //
            //                if (genreType != null && !"".equals(genreType)) {
            //                    genreTypeItem.setDefaultValue(genreType);
            //                }
            //                mainLayout.addMember(new MyDynamicForm(nameItem,
            //                                                       issueNumberItem,
            //                                                       genreTypeItem,
            //                                                       dateIssuedItem,
            //                                                       noteItem));
            //                setHeight(300);
            //            } else {
            //                mainLayout.addMember(new MyDynamicForm(nameItem, issueNumberItem, dateIssuedItem, noteItem));
            //                setHeight(270);
            //            }

        } else if (record.getAttributeAsString(Constants.ATTR_MODEL_ID)
                .equals(DigitalObjectModel.MONOGRAPHUNIT.getValue())) {

            noteItem = new TextAreaItem("note", lang.note());
            //            noteItem.setDefaultValue(record.getAttributeAsString(Constants.ATTR_NOTE));
            noteItem.setHeight(60);
            mainLayout.addMember(new MyDynamicForm(nameItem, noteItem));
            setHeight(200);

        } else if (record.getAttributeAsString(Constants.ATTR_MODEL_ID)
                .equals(DigitalObjectModel.PAGE.getValue())) {
            pageTypeItem = new SelectItem();
            pageTypeItem.setWidth(100);
            pageTypeItem.setTitle(lang.specialType());

            LinkedHashMap<String, String> valueMap =
                    new LinkedHashMap<String, String>(Constants.PAGE_TYPES.MAP);
            pageTypeItem.setValueMap(valueMap);
            //            String pageType = record.getAttribute(Constants.ATTR_PAGE_TYPE);
            //            if (pageType != null && !"".equals(pageType)) {
            //                pageTypeItem.setDefaultValue(pageType);
            //            }
            mainLayout.addMember(new MyDynamicForm(nameItem, pageTypeItem));
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
                doSaveAction(record, name);
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

    protected abstract void doSaveAction(ListGridRecord record, String name);

    protected String getDateIssued() {
        if (dateIssuedItem != null) return dateIssuedItem.getValueAsString();
        return null;
    }

    protected String getNote() {
        if (noteItem != null) return noteItem.getValueAsString();
        return null;
    }

    protected String getGenreType() {
        if (genreTypeItem != null) return genreTypeItem.getValueAsString();
        return null;
    }

    protected String getIssueNumber() {
        if (issueNumberItem != null) return issueNumberItem.getValueAsString();
        return null;
    }

    protected String getPageType() {
        if (pageTypeItem != null) return pageTypeItem.getValueAsString();
        return null;
    }
}
