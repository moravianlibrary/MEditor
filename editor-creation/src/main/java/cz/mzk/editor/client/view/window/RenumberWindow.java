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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.ClientCreateUtils;
import cz.mzk.editor.client.util.Constants;

/**
 * @author Matous Jobanek
 * @version Jul 13, 2012
 */
public abstract class RenumberWindow
        extends UniversalWindow {

    private CheckboxItem respectPerItems;

    private CheckboxItem useRoman;

    private CheckboxItem incrPref;

    private boolean isRomanRenumber;

    public RenumberWindow(int firstNumber,
                          String title,
                          EventBus eventBus,
                          LangConstants lang,
                          boolean isDivided,
                          boolean isRomanRenumber) {
        super(140, 380, title, eventBus, 100);
        setInstance(firstNumber, eventBus, lang, isDivided, isRomanRenumber, false);
    }

    private RenumberWindow(String title,
                           EventBus eventBus,
                           LangConstants lang,
                           boolean isDivided,
                           boolean isRomanRenumber,
                           boolean toAbc) {
        super(140, 380, title, eventBus, 100);
        setInstance(0, eventBus, lang, isDivided, isRomanRenumber, toAbc);
    }

    private void setInstance(int firstNumber,
                             EventBus eventBus,
                             final LangConstants lang,
                             boolean isDivided,
                             boolean isRomanRenumber,
                             final boolean toAbc) {

        this.isRomanRenumber = isRomanRenumber;

        DynamicForm formFirstNum = new DynamicForm();
        formFirstNum.setLayoutAlign(Alignment.CENTER);
        formFirstNum.setWidth(340);
        formFirstNum.setMargin(15);

        DynamicForm formRes = new DynamicForm();
        formRes.setLayoutAlign(Alignment.CENTER);
        formRes.setWidth(300);

        final TextItem indexOrFirstNumber =
                new TextItem("indexOrFirstNumber",
                             !toAbc ? (isRomanRenumber ? lang.enterLatinNumberForRenumber()
                                     : lang.enterNumberForRenumber()) : "");

        if (!toAbc) indexOrFirstNumber.setValue((firstNumber < 0) ? 1 : firstNumber);
        indexOrFirstNumber.setWidth(60);
        indexOrFirstNumber.setWrapTitle(false);

        formFirstNum.setItems(indexOrFirstNumber);

        respectPerItems = null;
        if (isDivided) {
            respectPerItems = new CheckboxItem("respectPerItems", lang.respectDiv());
            formRes.setItems(respectPerItems);
            respectPerItems.setValue(true);
            setHeight(180);
            formRes.setExtraSpace(20);

            addItem(formFirstNum);
            addItem(formRes);

        } else if (!toAbc) {
            formFirstNum.setExtraSpace(5);
            addItem(formFirstNum);
        }

        incrPref = null;
        useRoman = null;
        if (toAbc) {
            indexOrFirstNumber.setShowTitle(false);
            HTMLFlow label = new HTMLFlow(lang.enterNumberForIndex() + ":");
            label.setHeight(8);
            label.setWidth(250);

            incrPref = new CheckboxItem("incrPref", lang.incrPref());
            useRoman = new CheckboxItem("useRoman", lang.useRomanNum());
            useRoman.setValue(isRomanRenumber);

            formRes.setItems(incrPref, useRoman);
            formRes.setExtraSpace(20);

            formFirstNum.setWidth(50);
            formRes.setWidth(200);

            indexOrFirstNumber.addChangeHandler(new ChangeHandler() {

                @Override
                public void onChange(ChangeEvent event) {
                    if (event.getValue() != null) {
                        try {
                            Integer.parseInt(event.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            SC.warn(lang.onlyNum());
                            event.cancel();
                        }
                    }
                    if (event.getValue() != null) {

                    }
                }
            });

            HLayout vLayout = new HLayout(2);
            vLayout.addMember(label);
            vLayout.addMember(formFirstNum);
            vLayout.setAlign(Alignment.CENTER);
            addItem(vLayout);
            addItem(formRes);
            setHeight(220);
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
                doRenumber((!toAbc ? (respectPerItems == null ? false : respectPerItems.getValueAsBoolean())
                        : incrPref.getValueAsBoolean()), indexOrFirstNumber.getValueAsString());
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

    protected abstract void doRenumber(boolean respectPerItemsOrIncrPref, String firstNumberOrIndex);

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
                                     ClientCreateUtils.decimalToRoman(firstNum + i++, toRomanOld));
                }
            }
        }
    }

    /**
     * @return the useRoman
     */
    protected CheckboxItem getUseRoman() {
        return useRoman;
    }

    /**
     * The Class ToAbcWindow.
     */
    public abstract static class ToAbcWindow {

        /**
         * @param eventBus
         * @param lang
         * @param isRomanRenumber
         */
        public ToAbcWindow(EventBus eventBus, final LangConstants lang, boolean isRomanRenumber) {
            new RenumberWindow(lang.customizeIndex(), eventBus, lang, false, isRomanRenumber, true) {

                @Override
                protected void doRenumber(boolean respectPerItemsOrIncrPref, String firstNumberOrIndex) {

                    try {
                        int index = Integer.parseInt(firstNumberOrIndex);
                        doAbc(respectPerItemsOrIncrPref, index, getUseRoman().getValueAsBoolean());
                    } catch (NumberFormatException nfe) {
                        SC.say(lang.notANumber());
                    }
                }

            };
        }

        protected abstract void doAbc(boolean incrPref, int index, boolean useRomanNumber);
    }
}
