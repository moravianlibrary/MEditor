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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.mods.ModsTypeClient;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.client.view.other.EditorTabSet;
import cz.mzk.editor.client.view.other.ModsTab;
import cz.mzk.editor.shared.rpc.DublinCore;

/**
 * @author Matous Jobanek
 * @version Apr 26, 2012
 */
public abstract class CreateWindow
        extends UniversalWindow {

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param isPdf
     * @param milisToWait
     */
    public CreateWindow(final LangConstants lang,
                        final EditorTabSet topTabSet,
                        EventBus eventBus,
                        final boolean isPdf) {
        super(200, 350, lang.publishName(), eventBus, 20);

        HTMLFlow label = new HTMLFlow(HtmlCode.title(lang.areYouSure(), 3));
        label.setMargin(5);
        label.setExtraSpace(10);
        final DynamicForm form = new DynamicForm();
        form.setMargin(0);
        form.setWidth(100);
        form.setHeight(30);

        HTMLFlow setRightsFlow = new HTMLFlow(lang.setRights());

        final CheckboxItem makePublic = new CheckboxItem("makePublic", "public");
        final CheckboxItem makePrivate = new CheckboxItem("makePrivate", "private");
        makePrivate.setValue(true);
        makePublic.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                makePrivate.setValue(!makePublic.getValueAsBoolean());
            }
        });
        makePrivate.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                makePublic.setValue(!makePrivate.getValueAsBoolean());
            }
        });

        final TextItem thumbPageNumItem =
                new TextItem("thumbPageNum", "Insert a page number to be used for a thumbnail");
        if (isPdf) {
            thumbPageNumItem.setWidth(50);
            thumbPageNumItem.setDefaultValue("1");
            thumbPageNumItem.setWrapTitle(false);
        }

        Button publish = new Button();
        publish.setTitle(lang.ok());
        publish.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {

                DublinCore dc = null;
                ModsTypeClient mods = null;
                if (topTabSet != null) {
                    dc = topTabSet.getDcTab().getDc();
                    if (topTabSet.getModsTab() != null) {
                        mods = ((ModsTab) topTabSet.getModsTab()).getMods();
                    }
                }

                int thumbPageNum = Integer.MIN_VALUE;
                if (isPdf) {
                    try {
                        thumbPageNum = Integer.parseInt(thumbPageNumItem.getValueAsString());
                    } catch (NumberFormatException nfe) {
                        SC.say(lang.notANumber());
                        return;
                    }
                    if (thumbPageNum <= 0) SC.say(lang.notANumber());
                }

                createAction(dc, mods, makePublic.getValueAsBoolean(), thumbPageNum);
                hide();
            }
        });
        Button cancel = new Button();
        cancel.setTitle(lang.cancel());
        cancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event2) {
                hide();
            }
        });
        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(10);
        hLayout.addMember(publish);
        hLayout.addMember(cancel);
        hLayout.setMargin(5);

        form.setFields(makePublic, makePrivate);

        setEdgeOffset(20);
        addItem(label);
        addItem(setRightsFlow);

        if (!isPdf) {
            form.setExtraSpace(7);
            addItem(form);
        } else {
            addItem(form);
            final DynamicForm thumbForm = new DynamicForm();
            thumbForm.setMargin(0);
            thumbForm.setWidth(100);
            thumbForm.setFields(thumbPageNumItem);
            thumbForm.setExtraSpace(7);
            setHeight(250);
            addItem(thumbForm);
        }

        addItem(hLayout);

        centerInPage();
        show();
        publish.focus();
    }

    protected abstract void createAction(DublinCore dc,
                                         ModsTypeClient mods,
                                         Boolean makePublic,
                                         int thumbPageNum);
}
