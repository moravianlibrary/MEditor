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

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureResult;

/**
 * @author Jiri Kremser
 * @version 1.2.2012
 */
public class StoreTreeStructureWindow
        extends UniversalWindow {

    @SuppressWarnings("unused")
    private final LangConstants lang;
    private static StoreTreeStructureWindow storingWindow = null;

    public static void setInstanceOf(TreeStructureBundle structure,
                                     String treeString,
                                     final LangConstants lang,
                                     DispatchAsync dispatcher,
                                     EventBus eventBus) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        storingWindow = new StoreTreeStructureWindow(structure, treeString, lang, dispatcher, eventBus);
    }

    public static boolean isInstanceVisible() {
        return (storingWindow != null && storingWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        storingWindow.hide();
        storingWindow = null;
    }

    private StoreTreeStructureWindow(final TreeStructureBundle structure,
                                     final String treeString,
                                     final LangConstants lang,
                                     final DispatchAsync dispatcher,
                                     final EventBus eventBus) {
        super(550, 620, lang.save() + ": " + structure.getNodes().get(0).getPropType() + " " + lang.name()
                + ": " + structure.getNodes().get(0).getPropName(), eventBus, 40);
        this.lang = lang;

        Layout mainLayout = new VLayout();
        HTMLFlow structureInfo = new HTMLFlow("<pre>" + treeString + "</pre>");
        mainLayout.setHeight100();
        mainLayout.addMember(structureInfo);

        setEdgeOffset(15);
        addItem(mainLayout);
        DynamicForm form = new DynamicForm();
        final TextItem description = new TextItem("desc", lang.description());
        description.setWidth(250);
        form.setWidth(280);
        form.setHeight(15);
        form.setExtraSpace(25);
        form.setFields(description);

        Button saveButton = new Button(lang.save());
        saveButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                structure.getInfo().setDescription(description.getValueAsString());
                dispatcher.execute(new StoreTreeStructureAction(Constants.VERB.PUT, null, false, structure),
                                   new DispatchCallback<StoreTreeStructureResult>() {

                                       @Override
                                       public void callback(StoreTreeStructureResult result) {
                                           hide();
                                       }
                                   });
            }
        });

        Layout bottomLayout = new HLayout();
        bottomLayout.setExtraSpace(10);
        bottomLayout.addMember(form);
        bottomLayout.addMember(saveButton);
        addItem(bottomLayout);
        centerInPage();
        show();
        focus();

        //setWidth100();
        //setHeight100();
    }

}
