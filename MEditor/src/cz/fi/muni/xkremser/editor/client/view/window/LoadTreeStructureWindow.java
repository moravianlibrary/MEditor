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
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;

/**
 * @author Jiri Kremser
 * @version 1.2.2012
 */
public class LoadTreeStructureWindow
        extends UniversalWindow {

    private final LangConstants lang;
    private static LoadTreeStructureWindow storingWindow = null;

    public static void setInstanceOf(String code,
                                     final LangConstants lang,
                                     DispatchAsync dispatcher,
                                     EventBus eventBus) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        storingWindow = new LoadTreeStructureWindow(code, lang, dispatcher, eventBus);
    }

    public static boolean isInstanceVisible() {
        return (storingWindow != null && storingWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        storingWindow.hide();
        storingWindow = null;
    }

    private LoadTreeStructureWindow(final String code,
                                    final LangConstants lang,
                                    final DispatchAsync dispatcher,
                                    final EventBus eventBus) {
        super(550, 620, lang.loadStructure() + ": " + code, eventBus, 40);
        this.lang = lang;

        Layout mainLayout = new VLayout();

        Layout topLayout = new VLayout();
        //        HTMLFlow structureInfo = new HTMLFlow("<pre>" + treeString + "</pre>");
        topLayout.setHeight100();
        //        mainLayout.addMember(structureInfo);

        setEdgeOffset(15);
        mainLayout.addMember(topLayout);
        ListGrid storedStructures = new ListGrid();
        storedStructures.setHeight(240);
        storedStructures.setShowAllRecords(true);
        storedStructures.setAutoFetchData(false);
        ListGridField dateField = new ListGridField(Constants.ATTR_DATE, lang.date());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        storedStructures.setFields(dateField, descField);
        storedStructures.addData(new ListGridRecord() {

            {
                setAttribute(Constants.ATTR_DESC, "popis");
                setAttribute(Constants.ATTR_DATE, "datum");
            }
        });
        topLayout.addMember(storedStructures);

        DynamicForm form = new DynamicForm();
        final RadioGroupItem radioGroupItem = new RadioGroupItem();
        radioGroupItem.setTitle("Zobrazit");
        radioGroupItem.setValueMap("Všechny mé uložené struktury", "Jen pro tento vytvářený objekt (" + code
                + ")");
        //        form.setWidth(280);

        form.setExtraSpace(20);
        form.setFields(radioGroupItem);

        Button loadButton = new Button(lang.save());
        loadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                SC.say("loading..");
                hide();
                //                dispatcher.execute(new StoreTreeStructureAction(Constants.VERB.PUT, null, structure),
                //                                   new DispatchCallback<StoreTreeStructureResult>() {
                //
                //                                       @Override
                //                                       public void callback(StoreTreeStructureResult result) {
                //                                           hide();
                //                                       }
                //                                   });

            }
        });

        Button deleteButton = new Button(lang.removeSelected());
        deleteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                SC.say("deleting..");
                hide();
            }
        });

        mainLayout.addMember(form);
        Layout bottomLayout = new HLayout();
        bottomLayout.setExtraSpace(10);
        bottomLayout.addMember(loadButton);
        bottomLayout.addMember(deleteButton);
        mainLayout.addMember(bottomLayout);

        final TabSet mainTabSet = new TabSet();
        mainTabSet.setTabBarPosition(Side.RIGHT);
        mainTabSet.setWidth100();
        mainTabSet.setHeight100();
        Tab commonStoredStructures = new Tab("", "other/more_people.png");
        commonStoredStructures.setPane(new Label("under construction..."));
        Tab userStoredStructures = new Tab("", "other/loner.png");
        userStoredStructures.setPane(mainLayout);
        mainTabSet.setTabs(userStoredStructures, commonStoredStructures);
        //        mainTabSet.setTabPane(tabId, mainTabSet);
        addItem(mainTabSet);

        centerInPage();
        show();
        focus();

        //setWidth100();
        //setHeight100();
    }
}
