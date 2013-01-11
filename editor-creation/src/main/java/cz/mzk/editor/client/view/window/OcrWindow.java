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
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version Aug 29, 2012
 */
public abstract class OcrWindow
        extends UniversalWindow {

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */
    public OcrWindow(EventBus eventBus, String ocr, LangConstants lang) {
        super(600, 500, "OCR", eventBus, 52);

        setEdgeOffset(25);
        setDefaultLayoutAlign(VerticalAlignment.CENTER);

        final TextAreaItem ocrAreaItem = new TextAreaItem("ocrTextBox");

        ocrAreaItem.setWidth(450);
        ocrAreaItem.setHeight(500);
        ocrAreaItem.setShowTitle(false);
        ocrAreaItem.setValue(ocr);

        DynamicForm ocrForm = new DynamicForm();
        ocrForm.setItems(ocrAreaItem);
        ocrForm.setExtraSpace(15);
        ocrForm.setEdgeSize(1);
        ocrForm.setShowEdges(true);
        ocrForm.setWidth(450);
        ocrForm.setHeight(500);
        addItem(ocrForm);

        HLayout buttonsLayout = new HLayout(3);
        buttonsLayout.setWidth(450);
        buttonsLayout.setAlign(Alignment.RIGHT);

        Button stornoButton = new Button(lang.storno());
        stornoButton.setExtraSpace(5);
        stornoButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        Button okButton = new Button("OK");
        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                doSaveAction(ocrAreaItem.getValueAsString());
                hide();
            }
        });

        buttonsLayout.addMember(stornoButton);

        buttonsLayout.addMember(okButton);

        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();
    }

    protected abstract void doSaveAction(String value);

}
