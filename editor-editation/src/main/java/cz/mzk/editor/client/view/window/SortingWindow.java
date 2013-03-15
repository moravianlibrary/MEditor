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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.other.AlphanumComparator;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.NamedGraphModel;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.DigitalObjectRelationships;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailResult;

/**
 * The Class SortingWindow.
 * 
 * @author Matous Jobanek
 * @version Jan 29, 2013
 */
public abstract class SortingWindow
        extends UniversalWindow {

    private TreeGrid objects;
    private Tree objectsTree;
    private ModalWindow mw;
    private final LangConstants lang;
    private IButton ascSort;
    private IButton descSort;
    private DigitalObjectRelationships digObjRel;
    private DigitalObjectRelationships origDigObjRel;
    private int count = 0;
    private final DispatchAsync dispatcher;

    /**
     * Instantiates a new sorting window.
     * 
     * @param lang
     *        the lang
     * @param eventBus
     *        the event bus
     */
    public SortingWindow(LangConstants lang, DispatchAsync dispatcher, EventBus eventBus) {
        super(600, 400, lang.sort(), eventBus, 60);
        this.lang = lang;
        this.dispatcher = dispatcher;

        mw = new ModalWindow(this);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        centerInPage();
        show();
        focus();
    }

    /**
     *
     * @param digObjRelations
     */
    public void setObjects(DigitalObjectRelationships digObjRelations) {
        objects = new TreeGrid();
        objectsTree = new Tree();

        setAnimateResizeAcceleration(AnimationAcceleration.SMOOTH_END);

        ListGridField titleField = new ListGridField(Constants.ATTR_NAME, lang.title());
        objects.setFields(titleField);

        objects.setWidth100();
        objects.setShowConnectors(true);
        objects.setShowOpenIcons(true);
        objects.setBaseStyle("noBorderCell");
        objects.setNodeIcon("icons/16/shape_3d.png");
        objects.setFolderIcon("icons/16/shape_3d.png");
        objects.setCanSort(false);
        objects.setMargin(10);

        this.digObjRel = digObjRelations;
        origDigObjRel = digObjRelDeepCopy(digObjRelations);

        ascSort = new IButton(lang.ascending());
        ascSort.setExtraSpace(5);
        ascSort.setWidth(160);
        ascSort.setIcon("icons/16/sort_ascending.png");

        descSort = new IButton(lang.descending());
        descSort.setWidth(160);
        descSort.setIcon("icons/16/sort_descending.png");

        setTreeNodes(digObjRelations, null, true);

        objects.getData().openAll();

        HLayout buttonLayout = new HLayout(2);
        buttonLayout.setAlign(Alignment.CENTER);

        buttonLayout.addMember(ascSort);
        buttonLayout.addMember(descSort);

        ascSort.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                mw.show(true);
                sortObject(digObjRel, false);
                setTreeNodes(digObjRel, null, false);
                objects.getData().openAll();
                objects.redraw();
                mw.hide();
            }
        });
        descSort.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                mw.show(true);
                sortObject(digObjRel, true);
                setTreeNodes(digObjRel, null, false);
                objects.getData().openAll();
                objects.redraw();
                mw.hide();
            }
        });

        IButton publish = new IButton(lang.publishItem());
        publish.setExtraSpace(10);
        publish.setLayoutAlign(Alignment.CENTER);
        publish.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                publish();
            }

        });

        int height = count * 30 + 200;
        height = height > Window.getClientHeight() ? Window.getClientHeight() : height;
        setHeight(height);
        objects.setHeight(height - 120);
        addMember(buttonLayout);
        addMember(objects);
        addMember(publish);

        mw.hide();
        centerInPage();
    }

    private DigitalObjectRelationships digObjRelDeepCopy(DigitalObjectRelationships digObjRelFrom) {

        DigitalObjectRelationships digObjRelTo = new DigitalObjectRelationships(digObjRelFrom.getUuid());
        digObjRelTo.setTitle(digObjRelFrom.getTitle());
        digObjRelTo.setModel(digObjRelFrom.getModel());

        Map<String, List<DigitalObjectRelationships>> copyPom =
                new HashMap<String, List<DigitalObjectRelationships>>(digObjRelFrom.getChildren().size());

        for (String rel : digObjRelFrom.getChildren().keySet()) {
            List<DigitalObjectRelationships> relObj =
                    new ArrayList<DigitalObjectRelationships>(digObjRelFrom.getChildren().get(rel).size());

            for (DigitalObjectRelationships digObj : digObjRelFrom.getChildren().get(rel)) {
                relObj.add(digObjRelDeepCopy(digObj));
            }
            copyPom.put(rel, relObj);
        }

        digObjRelTo.setChildren(copyPom);
        return digObjRelTo;
    }

    private void setTreeNodes(DigitalObjectRelationships digObjRelations, TreeNode parent, boolean first) {
        count++;
        if (digObjRelations != null) {
            TreeNode node = new TreeNode();
            node.setAttribute(Constants.ATTR_NAME, digObjRelations.getTitle());

            if (parent != null) {
                objectsTree.add(node, parent);
            } else {
                if (first) {
                    objectsTree.setData(new TreeNode[] {node});
                    count++;
                } else {
                    objectsTree = new Tree();
                    objectsTree.setData(new TreeNode[] {node});
                }
                objects.setData(objectsTree);
            }
            node.setIcon("icons/16/shape_3d.png");

            for (String rel : digObjRelations.getChildren().keySet()) {
                TreeNode relNode = new TreeNode();
                relNode.setAttribute(Constants.ATTR_NAME, rel);
                relNode.setIcon("icons/16/arrow_se.png");
                objectsTree.add(relNode, node);

                for (DigitalObjectRelationships child : digObjRelations.getChildren().get(rel)) {
                    setTreeNodes(child, relNode, false);
                }
            }
        }
    }

    private void sortObject(DigitalObjectRelationships digObjRelations, boolean descending) {
        for (String rel : digObjRelations.getChildren().keySet()) {
            List<DigitalObjectRelationships> children = digObjRelations.getChildren().get(rel);

            if (children != null && children.size() > 1) {
                TreeMap<String, DigitalObjectRelationships> sorted =
                        new TreeMap<String, DigitalObjectRelationships>(new AlphanumComparator());

                for (int i = 0; i < children.size(); i++) {
                    sorted.put(children.get(i).getTitle(), children.get(i));
                    sortObject(children.get(i), descending);
                }

                Object[] keys = sorted.keySet().toArray();
                digObjRelations.getChildren().get(rel).clear();
                for (int i = (descending ? sorted.size() - 1 : 0); descending ? (i >= 0) : i < sorted.size(); i +=
                        (descending ? -1 : 1)) {
                    digObjRelations.getChildren().get(rel).add(sorted.get(keys[i]));
                }
            } else if (children.size() == 1) {
                sortObject(children.get(0), descending);
            }
        }
    }

    private void publish() {
        mw = new ModalWindow(objects);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);
        List<DigitalObjectDetail> digObjects = new ArrayList<DigitalObjectDetail>();
        createDigObjDetails(digObjRel, origDigObjRel, digObjects);

        if (!digObjects.isEmpty()) {

            sent(digObjects);
        } else {
            SC.say(lang.noFoundChanges());
            mw.hide();
            hide();
        }
    }

    private void sent(final List<DigitalObjectDetail> digObjects) {
        PutDigitalObjectDetailAction putObjAction =
                new PutDigitalObjectDetailAction(digObjects.get(0), false, true);
        dispatcher.execute(putObjAction, new DispatchCallback<PutDigitalObjectDetailResult>() {

            @Override
            public void callback(PutDigitalObjectDetailResult result) {
                digObjects.get(0);
                digObjects.remove(0);

                if (!digObjects.isEmpty()) {
                    sent(digObjects);
                } else {
                    SortingWindow.this.hide();
                    mw.hide();
                    afterPublishAction();
                }
            }
        });
    }

    private void createDigObjDetails(DigitalObjectRelationships digObjRelations,
                                     DigitalObjectRelationships origDigObjRelations,
                                     List<DigitalObjectDetail> digObjects) {
        boolean anyChanged = false;
        DigitalObjectModel parentModel = digObjRelations.getModel();
        List<DigitalObjectModel> childrenModels = NamedGraphModel.getChildren(parentModel);

        List<List<DigitalObjectDetail>> relationsObj =
                new ArrayList<List<DigitalObjectDetail>>(childrenModels.size());
        for (int i = 0; i < childrenModels.size(); i++) {
            relationsObj.add(null);
        }

        for (String rel : digObjRelations.getChildren().keySet()) {
            boolean changed = false;
            List<DigitalObjectRelationships> children = digObjRelations.getChildren().get(rel);
            List<DigitalObjectRelationships> origChildren = origDigObjRelations.getChildren().get(rel);

            for (int i = 0; i < children.size(); i++) {
                if (!children.get(i).getTitle().equals(origChildren.get(i).getTitle())) {
                    changed = true;
                    anyChanged = true;
                    break;
                }
            }

            if (changed) {
                int indexOfModel = childrenModels.indexOf(origChildren.get(0).getModel());
                List<DigitalObjectDetail> childrenObj = new ArrayList<DigitalObjectDetail>(children.size());
                for (int i = 0; i < children.size(); i++) {
                    childrenObj.add(new DigitalObjectDetail(children.get(i).getUuid()));
                }
                relationsObj.remove(indexOfModel);
                relationsObj.add(indexOfModel, childrenObj);
            }

            for (int i = 0; i < children.size(); i++) {
                int indexOfOrigObj = -1;
                for (DigitalObjectRelationships origObj : origChildren) {
                    if (origObj.getUuid().equals(children.get(i).getUuid())) {
                        indexOfOrigObj = origChildren.indexOf(origObj);
                    }
                }
                if (indexOfOrigObj >= 0)
                    createDigObjDetails(children.get(i), origChildren.get(indexOfOrigObj), digObjects);
            }
        }

        if (anyChanged) {
            DigitalObjectDetail digObjDetail = new DigitalObjectDetail();
            digObjDetail.setUuid(digObjRelations.getUuid());
            digObjDetail.setModel(parentModel);
            digObjDetail.setAllItems(relationsObj);

            digObjDetail.setDcChanged(false);
            digObjDetail.setModsChanged(false);
            digObjDetail.setOcrChanged(false);
            digObjDetail.setLabelChanged(false);

            digObjects.add(digObjDetail);
        }
    }

    protected abstract void afterPublishAction();
}
