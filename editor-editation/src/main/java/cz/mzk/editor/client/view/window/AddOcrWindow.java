package cz.mzk.editor.client.view.window;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.smartgwt.client.types.AnimationAcceleration;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.FedoraRelationship;
import cz.mzk.editor.shared.rpc.DigitalObjectRelationships;
import cz.mzk.editor.shared.rpc.action.QuartzAddOcrAction;
import cz.mzk.editor.shared.rpc.action.QuartzAddOcrResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Martin Rumanek
 * @version: 18.2.13
 */
public class AddOcrWindow extends UniversalWindow {

    public static final String ATTR_PAGE_COUNT = "atPageCount";
    private final LangConstants lang;
    private final DispatchAsync dispatcher;
    private TreeGrid objects;
    private Tree objectsTree;
    private ModalWindow mw;
    private int count = 0;
    private Map<String, Integer> countPages;
    private IButton prepareOcrButton;

    public AddOcrWindow(final LangConstants lang, final DispatchAsync dispatcher, final EventBus eventBus,
                        final String uuid) {

        super(600, 600, lang.addOcr(), eventBus, 60);
        this.lang = lang;
        this.dispatcher = dispatcher;
        prepareOcrButton = new IButton(lang.addOcr());

        mw = new ModalWindow(this);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        centerInPage();
        show();
        focus();
        prepareOcrButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                QuartzAddOcrAction action = new QuartzAddOcrAction(uuid);
                dispatcher.execute(action, new DispatchCallback<QuartzAddOcrResult>() {
                    @Override
                    public void callback(QuartzAddOcrResult result) {
                    }
                });
            }
        });


    }

    public void setObjects(DigitalObjectRelationships digObjRel) {
        objects = new TreeGrid();
        objectsTree = new Tree();

        setAnimateResizeAcceleration(AnimationAcceleration.SMOOTH_END);

        ListGridField titleField = new ListGridField(Constants.ATTR_NAME, lang.title());
        ListGridField ocrCounter = new ListGridField(ATTR_PAGE_COUNT, lang.numberOfPages());
        objects.setFields(titleField, ocrCounter);

        objects.setWidth100();
        objects.setShowConnectors(true);
        objects.setShowOpenIcons(true);
        objects.setBaseStyle("noBorderCell");
        objects.setNodeIcon("icons/16/shape_3d.png");
        objects.setFolderIcon("icons/16/shape_3d.png");
        objects.setCanSort(false);
        objects.setMargin(10);

        Map<String, Integer> countPages = getCountPages(digObjRel);
        setTreeNodes(digObjRel, null, true, countPages);
        mw.hide();
        addMember(prepareOcrButton);
        addMember(objects);

    }

    private void setTreeNodes(DigitalObjectRelationships digObjRelations, TreeNode parent, boolean first, Map<String, Integer> countPages) {
        count++;
        if (digObjRelations != null) {
            TreeNode node = new TreeNode();
            node.setAttribute(Constants.ATTR_NAME, digObjRelations.getTitle());
            node.setAttribute(ATTR_PAGE_COUNT, countPages.get(digObjRelations.getUuid()));

            if (parent != null) {
                objectsTree.add(node, parent);
            } else {
                if (first) {
                    objectsTree.setData(new TreeNode[]{node});
                    count++;
                } else {
                    objectsTree = new Tree();
                    objectsTree.setData(new TreeNode[]{node});
                }
                objects.setData(objectsTree);
            }
            node.setIcon("icons/16/shape_3d.png");

            for (String rel : digObjRelations.getChildren().keySet()) {
                if (!FedoraRelationship.hasPage.getStringRepresentation().equals(rel)
                        && !FedoraRelationship.isOnPage.getStringRepresentation().equals(rel)) {

                    TreeNode relNode = new TreeNode();
                    relNode.setAttribute(Constants.ATTR_NAME, rel);
                    relNode.setIcon("icons/16/arrow_se.png");
                    objectsTree.add(relNode, node);

                    for (DigitalObjectRelationships child : digObjRelations.getChildren().get(rel)) {
                        setTreeNodes(child, relNode, false, countPages);
                    }
                }
            }
        }
    }

    private int getCount(DigitalObjectRelationships relationships, Map<String, Integer> map) {
        count = 0;
        for (String relItem : relationships.getChildren().keySet()) {

            for (DigitalObjectRelationships child : relationships.getChildren().get(relItem)) {

                if (child.getModel().equals(DigitalObjectModel.PAGE)) {
                    count++;
                } else {
                    count += getCount(child, map);
                }
            }

        }
        map.put(relationships.getUuid(), count);

        return count;
    }

    private Map<String, Integer> getCountPages(DigitalObjectRelationships relationships) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        getCount(relationships, map);

        return map;
    }
}
