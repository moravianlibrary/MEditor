/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.HtmlCode;

public abstract class UuidWindow
        extends UniversalWindow {

    private final TextItem uuidField;

    /**
     * @param modsCollection
     *        the original modsCollectionClient
     * @param lang
     *        the lang
     */
    public UuidWindow(LangConstants lang, EventBus eventBus) {
        super(155, 440, "PID", eventBus, 10);

        setEdgeOffset(15);

        RegExpValidator regExpValidator = new RegExpValidator();
        regExpValidator
                .setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

        final IButton open = new IButton();
        uuidField = new TextItem();
        uuidField.setTitle("PID");
        uuidField.setHint(HtmlCode.nobr(lang.withoutPrefix()));
        uuidField.setValidators(regExpValidator);
        uuidField.setWidth(255);
        uuidField.addKeyPressHandler(new com.smartgwt.client.widgets.form.fields.events.KeyPressHandler() {

            @Override
            public void onKeyPress(com.smartgwt.client.widgets.form.fields.events.KeyPressEvent event) {
                if (event.getKeyName().equals("Enter") && !open.getDisabled()) {
                    doAction(uuidField);
                }
            }
        });
        uuidField.addChangedHandler(new com.smartgwt.client.widgets.form.fields.events.ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                String text = (String) event.getValue();
                if (text != null && !"".equals(text)) {
                    open.setDisabled(false);
                } else {
                    open.setDisabled(true);
                }
            }
        });
        open.setTitle(lang.open());
        open.setDisabled(true);
        open.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    doAction(uuidField);
                }
            }
        });

        DynamicForm form = new DynamicForm();
        form.setMargin(20);
        form.setWidth(140);
        form.setHeight(15);
        form.setFields(uuidField);
        addItem(new HTMLFlow(lang.enterPID()));
        addItem(form);
        addItem(open);
        centerInPage();
        show();
        uuidField.focusInItem();
    }

    public TextItem getUuidField() {
        return uuidField;
    }

    protected abstract void doAction(TextItem uuidField);
}
