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

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;

/**
 * @author Matous Jobanek
 * @version Jul 13, 2012
 */
public abstract class RenumberWindow
        extends UniversalWindow {

    //    public static void instanceOfRenumberAll(LangConstants lang, EventBus eventBus, boolean isDivided) {
    //        new RenumberWindow(lang.renumberAll(), eventBus, lang, isDivided);
    //    }

    private CheckboxItem respectPerItems;

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */
    public RenumberWindow(String title, EventBus eventBus, LangConstants lang, boolean isDivided) {
        super(150, 350, title, eventBus, 100);

        setEdgeOffset(15);

        DynamicForm formFirstNum = new DynamicForm();
        DynamicForm formRes = new DynamicForm();
        final TextItem firstNumberItem = new TextItem("firstNumberItem");

        addItem(new HTMLFlow(lang.enterNumberForRenumber() + ":"));

        firstNumberItem.setShowTitle(false);
        formFirstNum.setItems(firstNumberItem);

        respectPerItems = null;
        if (isDivided) {
            respectPerItems =
                    new CheckboxItem("respectPerItems",
                                     "Would you like to respect the division on the periodical items?");
            formRes.setItems(respectPerItems);
            setHeight(200);
            formRes.setExtraSpace(10);

            addItem(formFirstNum);
            addItem(formRes);

        } else {
            formFirstNum.setExtraSpace(10);
            addItem(formFirstNum);
        }

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
                doRenumber(respectPerItems == null ? false : respectPerItems.getValueAsBoolean(),
                           firstNumberItem.getValueAsString());
            }

        });

        buttonsLayout.addMember(closeButton);
        buttonsLayout.addMember(okButton);
        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();
    }

    protected abstract void doRenumber(boolean respectPerItems, String firstNumber);
}
