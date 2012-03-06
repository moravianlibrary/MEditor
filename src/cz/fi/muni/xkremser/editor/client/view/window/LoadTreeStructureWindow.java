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

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.event.LoadStructureEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureInfo;
import cz.fi.muni.xkremser.editor.shared.rpc.TreeStructureBundle.TreeStructureNode;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.StoreTreeStructureResult;

/**
 * @author Jiri Kremser
 * @version 1.2.2012
 */
public class LoadTreeStructureWindow
        extends UniversalWindow {

    @SuppressWarnings("unused")
    private final LangConstants lang;
    private static LoadTreeStructureWindow storingWindow = null;
    private final ListGrid storedStructures;
    private final ListGrid storedStructuresByAll;
    private final DispatchAsync dispatcher;

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
        this.dispatcher = dispatcher;

        Layout userMainLayout = new VLayout();
        Layout commonMainLayout = new VLayout();
        Layout userTopLayout = new VLayout();
        Layout commonTopLayout = new VLayout();
        //        HTMLFlow structureInfo = new HTMLFlow("<pre>" + treeString + "</pre>");
        userTopLayout.setHeight100();
        commonTopLayout.setHeight100();

        setEdgeOffset(15);
        userMainLayout.addMember(userTopLayout);
        commonMainLayout.addMember(commonTopLayout);
        storedStructures = new ListGrid();
        storedStructures.setHeight(240);
        storedStructures.setShowAllRecords(true);
        storedStructures.setAutoFetchData(false);
        ListGridField dateField = new ListGridField(Constants.ATTR_DATE, lang.date());
        ListGridField nameField = new ListGridField(Constants.ATTR_NAME, lang.name());
        ListGridField modelField = new ListGridField(Constants.ATTR_MODEL, lang.dcType());
        ListGridField descField = new ListGridField(Constants.ATTR_DESC, lang.description());
        //        storedStructures.sort(Constants.ATTR_DATE, SortDirection.DESCENDING);
        storedStructures.setSortField(Constants.ATTR_DATE);
        storedStructures.setFields(dateField, modelField, nameField, descField);
        userTopLayout.addMember(storedStructures);

        storedStructuresByAll = new ListGrid();
        storedStructuresByAll.setHeight(240);
        storedStructuresByAll.setShowAllRecords(true);
        storedStructuresByAll.setAutoFetchData(false);
        //        storedStructures.sort(Constants.ATTR_DATE, SortDirection.DESCENDING);
        storedStructuresByAll.setSortField(Constants.ATTR_DATE);
        ListGridField ownerField = new ListGridField(Constants.ATTR_OWNER, lang.owner());
        storedStructuresByAll.setFields(dateField, modelField, nameField, ownerField, descField);
        commonTopLayout.addMember(storedStructuresByAll);

        DynamicForm form = new DynamicForm();
        final RadioGroupItem radioGroupItem = new RadioGroupItem();
        radioGroupItem.setTitle(lang.show());
        final String forObj = lang.forObj() + " (" + code + ")";
        final String forAll = lang.forAll();
        radioGroupItem.setValueMap(forObj, forAll);

        form.setExtraSpace(20);
        form.setFields(radioGroupItem);
        radioGroupItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                if (forObj.equals(event.getValue())) {
                    fetchData(code, false);
                } else {
                    fetchData(null, false);
                }

            }
        });

        final Button userLoadButton = new Button(lang.loadStructure());
        userLoadButton.setExtraSpace(5);
        userLoadButton.setDisabled(true);
        userLoadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                Record rec = storedStructures.getSelectedRecord();
                if (rec != null) {
                    load(rec.getAttribute(Constants.ATTR_ID));
                }
            }
        });

        final Button userDeleteButton = new Button(lang.removeSelected());
        userDeleteButton.setDisabled(true);
        userDeleteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                deleteSelected(storedStructures.getSelectedRecords());
                storedStructures.removeSelectedData();
            }
        });
        storedStructures.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selection = event.getSelection();
                if (selection != null && selection.length > 0) {
                    userDeleteButton.setDisabled(false);
                    if (selection.length == 1 && forObj.equals(radioGroupItem.getValue())) {
                        userLoadButton.setDisabled(false);
                    } else {
                        userLoadButton.setDisabled(true);
                    }
                } else {
                    userDeleteButton.setDisabled(true);
                    userLoadButton.setDisabled(true);
                }
            }
        });

        final Button commonLoadButton = new Button(lang.loadStructure());
        commonLoadButton.setExtraSpace(5);
        commonLoadButton.setDisabled(true);
        commonLoadButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                Record rec = storedStructuresByAll.getSelectedRecord();
                if (rec != null) {
                    load(rec.getAttribute(Constants.ATTR_ID));
                }
            }
        });

        final Button commonDeleteButton = new Button(lang.removeSelected());
        commonDeleteButton.setDisabled(true);
        commonDeleteButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                deleteSelected(storedStructuresByAll.getSelectedRecords());
                storedStructuresByAll.removeSelectedData();
            }
        });

        storedStructuresByAll.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                ListGridRecord[] selection = event.getSelection();
                if (selection != null && selection.length > 0) {
                    commonDeleteButton.setDisabled(false);
                    if (selection.length == 1
                            && selection[0].getAttribute(Constants.ATTR_BARCODE).equals(code)) {
                        commonLoadButton.setDisabled(false);
                    } else {
                        commonLoadButton.setDisabled(true);
                    }
                } else {
                    commonDeleteButton.setDisabled(true);
                    commonLoadButton.setDisabled(true);
                }
            }
        });

        userMainLayout.addMember(form);
        Layout userBottomLayout = new HLayout();
        userBottomLayout.setExtraSpace(10);
        userBottomLayout.addMember(userLoadButton);
        userBottomLayout.addMember(userDeleteButton);
        userMainLayout.addMember(userBottomLayout);

        Layout commonBottomLayout = new HLayout();
        commonBottomLayout.setExtraSpace(10);
        commonBottomLayout.addMember(commonLoadButton);
        commonBottomLayout.addMember(commonDeleteButton);
        commonMainLayout.addMember(commonBottomLayout);

        final TabSet mainTabSet = new TabSet();
        mainTabSet.setTabBarPosition(Side.RIGHT);
        mainTabSet.setWidth100();
        mainTabSet.setHeight100();
        Tab commonStoredStructures = new Tab("", "other/more_people.png");
        commonStoredStructures.setPane(commonMainLayout);
        commonStoredStructures.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                fetchData(code, true);
            }
        });
        Tab userStoredStructures = new Tab("", "other/loner.png");
        userStoredStructures.setPane(userMainLayout);
        mainTabSet.setTabs(userStoredStructures, commonStoredStructures);
        addItem(mainTabSet);
        centerInPage();
        show(new AnimationCallback() {

            @Override
            public void execute(boolean earlyFinish) {
                fetchData(code, false);
            }
        });
        focus();
        radioGroupItem.setValue(forObj);

    }

    private void fetchData(final String code, final boolean ownedByAll) {

        final ListGrid gridToFetch = ownedByAll ? storedStructuresByAll : storedStructures;
        final ModalWindow mw = new ModalWindow(gridToFetch);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);
        dispatcher.execute(new StoreTreeStructureAction(Constants.VERB.GET, code, ownedByAll, null),
                           new DispatchCallback<StoreTreeStructureResult>() {

                               @Override
                               public void callback(StoreTreeStructureResult result) {
                                   ArrayList<TreeStructureInfo> resultList = result.getInfos();
                                   if (resultList != null) {
                                       ListGridRecord[] records = new ListGridRecord[resultList.size()];
                                       int i = 0;
                                       for (TreeStructureInfo info : resultList) {
                                           records[i] = new ListGridRecord();
                                           records[i].setAttribute(Constants.ATTR_ID, info.getId());
                                           records[i].setAttribute(Constants.ATTR_DATE, info.getCreated());
                                           records[i].setAttribute(Constants.ATTR_DESC, info.getDescription());
                                           records[i].setAttribute(Constants.ATTR_BARCODE, info.getBarcode());
                                           records[i].setAttribute(Constants.ATTR_NAME, info.getName());
                                           records[i].setAttribute(Constants.ATTR_OWNER, info.getOwner());
                                           records[i].setAttribute(Constants.ATTR_INPUT_PATH,
                                                                   info.getInputPath());
                                           records[i].setAttribute(Constants.ATTR_MODEL, info.getModel());
                                           i++;
                                       }
                                       gridToFetch.setData(records);
                                   } else {
                                       gridToFetch.setData(new ListGridRecord[] {});
                                   }
                                   mw.hide();
                               }

                               @Override
                               public void callbackError(Throwable t) {
                                   mw.hide();
                                   super.callbackError(t);
                               }
                           });

    }

    private void deleteSelected(ListGridRecord[] selection) {
        for (ListGridRecord rec : selection) {
            delete(rec.getAttribute(Constants.ATTR_ID));
        }
    }

    private void delete(final String id) {
        dispatcher.execute(new StoreTreeStructureAction(Constants.VERB.DELETE, id, false, null),
                           new DispatchCallback<StoreTreeStructureResult>() {

                               @Override
                               public void callback(StoreTreeStructureResult result) {
                               }
                           });
    }

    private void load(final String id) {
        dispatcher.execute(new StoreTreeStructureAction(Constants.VERB.GET,
                                                        id,
                                                        false,
                                                        new TreeStructureBundle()),
                           new DispatchCallback<StoreTreeStructureResult>() {

                               @Override
                               public void callback(StoreTreeStructureResult result) {
                                   ArrayList<TreeStructureNode> nodes = result.getNodes();
                                   if (nodes != null) {
                                       TreeNode[] tree = new TreeNode[nodes.size()];
                                       Record[] pages = new Record[nodes.size()];
                                       int i = 0, j = 0;
                                       for (TreeStructureNode node : nodes) {
                                           if (node.getPropParent() == null) {
                                               pages[j++] = ClientUtils.toRecord(node, true);
                                           } else {
                                               tree[i++] = ClientUtils.toRecord(node, false);
                                           }
                                       }
                                       LoadStructureEvent.fire(getEventBus(), tree, pages);
                                       hide();
                                   }
                               }
                           });
    }
}
