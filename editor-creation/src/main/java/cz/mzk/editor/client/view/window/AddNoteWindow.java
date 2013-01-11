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

import com.google.web.bindery.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version Mar 23, 2012
 */
public abstract class AddNoteWindow
        extends UniversalWindow {

    /**
     * @param title
     * @param eventBus
     */
    public AddNoteWindow(String title, EventBus eventBus, LangConstants lang, String defaultNote) {
        super(200, 400, title, eventBus, 50);

        setLayoutAlign(Alignment.CENTER);

        DynamicForm noteForm = new DynamicForm();
        noteForm.setAutoWidth();
        noteForm.setMargin(10);
        noteForm.setLayoutAlign(Alignment.CENTER);
        final TextAreaItem note = new TextAreaItem();
        note.setShowTitle(false);
        note.setWidth(350);
        note.setDefaultValue(defaultNote);

        HLayout buttonsLayout = new HLayout();
        buttonsLayout.setLayoutAlign(Alignment.RIGHT);
        buttonsLayout.setWidth("220");
        Button closeButton = new Button(lang.close());
        closeButton.setExtraSpace(5);
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        Button okButton = new Button("OK");
        okButton.setExtraSpace(15);
        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doSave(note.getValueAsString());
            }
        });

        noteForm.setItems(note);
        addItem(noteForm);
        buttonsLayout.addMember(closeButton);
        buttonsLayout.addMember(okButton);
        addItem(buttonsLayout);
        centerInPage();
        show();
        note.focusInItem();
    }

    protected abstract void doSave(String note);
}
