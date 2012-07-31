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
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.ClientUtils;
import cz.mzk.editor.client.util.Constants;

/**
 * @author Matous Jobanek
 * @version Jul 13, 2012
 */
public abstract class RenumberWindow
        extends UniversalWindow {

    private CheckboxItem respectPerItems;

    private final boolean isRomanRenumber;

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */
    public RenumberWindow(int firstNumber,
                          String title,
                          EventBus eventBus,
                          LangConstants lang,
                          boolean isDivided,
                          boolean isRomanRenumber) {

        super(140, 380, title, eventBus, 100);

        this.isRomanRenumber = isRomanRenumber;

        DynamicForm formFirstNum = new DynamicForm();
        formFirstNum.setLayoutAlign(Alignment.CENTER);
        formFirstNum.setWidth(340);
        formFirstNum.setHeight(40);

        DynamicForm formRes = new DynamicForm();
        formRes.setLayoutAlign(Alignment.CENTER);
        formRes.setWidth(300);

        final TextItem firstNumberItem =
                new TextItem("firstNumberItem", isRomanRenumber ? lang.enterLatinNumberForRenumber()
                        : lang.enterNumberForRenumber());
        firstNumberItem.setValue((firstNumber < 0) ? 1 : firstNumber);
        firstNumberItem.setWidth(60);
        firstNumberItem.setWrapTitle(false);

        formFirstNum.setItems(firstNumberItem);

        respectPerItems = null;
        if (isDivided) {
            respectPerItems =
                    new CheckboxItem("respectPerItems",
                                     "Would you like to respect the division on the periodical items?");
            formRes.setItems(respectPerItems);
            respectPerItems.setValue(true);
            setHeight(170);
            formRes.setExtraSpace(10);

            addItem(formFirstNum);
            addItem(formRes);

        } else {
            formFirstNum.setExtraSpace(15);
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
                final ModalWindow mw = new ModalWindow(RenumberWindow.this);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
                doRenumber(respectPerItems == null ? false : respectPerItems.getValueAsBoolean(),
                           firstNumberItem.getValueAsString());
                mw.hide();
                hide();
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

    protected void renumber(Record[] data, int firstNum, boolean respectPerItems, boolean toRomanOld) {
        int i = 0;
        if (data != null && data.length > 0) {
            for (Record rec : data) {
                if (respectPerItems) {
                    String isMarked = rec.getAttributeAsString(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
                    if (isMarked != null && !"".equals(isMarked) && Boolean.TRUE.toString().equals(isMarked)) {
                        i = 0;
                        firstNum = 1;
                    }
                }
                if (!isRomanRenumber) {
                    rec.setAttribute(Constants.ATTR_NAME, firstNum + i++);
                } else {
                    rec.setAttribute(Constants.ATTR_NAME,
                                     ClientUtils.decimalToRoman(firstNum + i++, toRomanOld));
                }
            }
        }
    }

}
