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
import java.util.List;
import java.util.Map;

import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectRelationships;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class RemoveDigitalObjectWindow
        extends UniversalWindow {

    private static final String BLACK_CIRCLE = "other/blackCircle.png";
    private static final String GREEN_CIRCLE = "other/greenCircle.png";
    private static final String RED_CIRCLE = "other/redCircle.png";
    private static final String GREEN_FOCUSED_CIRCLE = "other/greenCircle_Over.png";
    private static final String ARROW_LEFT_CONFLICT = "other/arrowLeftConflict.png";
    private static final String RED_FOCUSED_CIRCLE = "other/redCircle_Over.png";
    private static final String BLACK_SQUARE = "other/blackSquare.png";
    private static final String GREEN_SQUARE = "other/greenSquare.png";
    private static final String RED_SQUARE = "other/redSquare.png";
    private static final String ARROW_DOWN = "other/arrowDown.png";
    private static final String ARROW_UP = "other/arrowUp.png";
    private static final String ARROW_ASKEW_RIGHT_CONFLICT = "other/arrowAskewRightConflict.png";
    private static final String ARROW_ASKEW_LEFT_CONFLICT = "other/arrowAskewLeftConflict.png";

    private final LangConstants lang;
    private static RemoveDigitalObjectWindow removeWindow = null;
    private final Layout mainLayout = new VLayout();
    Layout rootItemLayout = new HLayout();
    private final ModalWindow mw = new ModalWindow(mainLayout);
    private static List<String> sharedPages;
    private static List<ItemImgButton> itemList;

    private static final class ItemImgButton
            extends ImgButton {

        private final String uuid;
        private final String originalSrc;

        public ItemImgButton(final String uuid, final String src) {
            super();
            this.uuid = uuid;
            this.originalSrc = src;
            itemList.add(this);
            setWidth(10);
            setHeight(10);
            setSrc(src);
            setShowFocused(false);
            setShowDown(false);
            setHoverStyle("interactImageHover");
            setHoverWidth(310);

            addMouseOverHandler(new MouseOverHandler() {

                @Override
                public void onMouseOver(MouseOverEvent event) {
                    setPrompt(uuid);
                    if (!src.contains("square") && sharedPages.contains(uuid)) {
                        for (ItemImgButton img : itemList) {
                            if (img.getUuid().equals(uuid)) {
                                img.setFocusedImg();
                                img.redraw();
                            }
                        }
                    }
                }
            });
            addMouseOutHandler(new MouseOutHandler() {

                @Override
                public void onMouseOut(MouseOutEvent event) {
                    if (!src.contains("square") && sharedPages.contains(uuid)) {
                        for (ItemImgButton img : itemList) {
                            if (img.getUuid().equals(uuid)) {
                                img.setUnfocusedImg();
                                img.redraw();
                            }
                        }
                    }
                }
            });
        }

        public void setFocusedImg() {
            if (originalSrc.equals(RED_CIRCLE)) {
                setSrc(RED_FOCUSED_CIRCLE);
            } else if (originalSrc.equals(GREEN_CIRCLE)) {
                setSrc(GREEN_FOCUSED_CIRCLE);
            }
        }

        public void setUnfocusedImg() {
            setSrc(originalSrc);
        }

        public String getUuid() {
            return uuid;
        }

    }

    private static final class ArrowImg
            extends Img {

        public ArrowImg(String src) {
            super(src);
            setWidth(9);
            setHeight(50);
            setExtraSpace(5);
        }
    }

    private static final class arrowLeftConflict
            extends Img {

        public arrowLeftConflict() {
            super(ARROW_LEFT_CONFLICT);
            setWidth(10);
            setHeight(18);
            setExtraSpace(5);
        }
    }

    private static final class ItemLayout
            extends VLayout {

        public ItemLayout(DigitalObjectRelationships digObjRel, boolean isRoot) {
            super();
            ItemImgButton itemImgButton;
            Map<String, List<DigitalObjectRelationships>> parentsMap = digObjRel.getParents();

            if (isRoot) {
                Layout parentsRelLayout = new HLayout(parentsMap.size());
                parentsRelLayout.setAutoWidth();
                parentsRelLayout.setAutoHeight();

                for (String relName : parentsMap.keySet()) {
                    if (parentsMap.get(relName).size() > 0) {
                        if (relName.contains("uuid:")) {
                            parentsRelLayout.addMember(new ParentConflictItemLayout(relName, parentsMap
                                    .get(relName)));

                        } else {
                            parentsRelLayout.addMember(new RelationshipsLayout(relName, parentsMap
                                    .get(relName), false));
                        }
                    }
                }
                addMember(parentsRelLayout);
                itemImgButton =
                        new ItemImgButton(digObjRel.getUuid(), digObjRel.getConflictCode() > 0 ? RED_SQUARE
                                : GREEN_SQUARE);

            } else {
                if (digObjRel.getConflictCode() > 0) {

                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), RED_CIRCLE);
                } else {
                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), GREEN_CIRCLE);
                }
            }
            itemImgButton.setExtraSpace(5);
            if (digObjRel.getConflictCode() == 1) {
                Layout conflictLayout = new HLayout(2);
                conflictLayout.addMember(itemImgButton);
                conflictLayout.addMember(new arrowLeftConflict());
                conflictLayout.setAutoHeight();
                conflictLayout.setAutoWidth();
                addMember(conflictLayout);
            } else {
                addMember(itemImgButton);
            }

            Map<String, List<DigitalObjectRelationships>> childrenMap = digObjRel.getChildren();
            Layout childrenRelLayout = new HLayout(childrenMap.size());

            for (String relName : childrenMap.keySet()) {
                childrenRelLayout.addMember(new RelationshipsLayout(relName, childrenMap.get(relName), true));
            }
            childrenRelLayout.setAutoWidth();
            childrenRelLayout.setAutoHeight();
            setAutoWidth();
            setAutoHeight();
            setEdgeSize(1);
            if (!childrenMap.isEmpty() || !parentsMap.isEmpty()) {
                setShowEdges(true);
            }
            addMember(childrenRelLayout);
            itemImgButton.setTop(10);
        }
    }

    private static final class RelationshipsLayout
            extends VLayout {

        public RelationshipsLayout(String relationshipName,
                                   List<DigitalObjectRelationships> digObjRelList,
                                   boolean children) {
            super();
            int count = digObjRelList.size() > 5 ? 5 : digObjRelList.size();
            Layout arrowsLayout = new HLayout(count);

            for (int i = 0; i < count; i++) {
                Layout itemLayout = new VLayout(2);
                DigitalObjectRelationships child = digObjRelList.get(i);

                if (children) {
                    itemLayout.addMember(new ArrowImg(ARROW_DOWN));
                    itemLayout.addMember(new ItemLayout(child, false));

                } else {
                    itemLayout.addMember(new ItemImgButton(child.getUuid(), GREEN_CIRCLE));
                    itemLayout.addMember(new ArrowImg(ARROW_UP));
                }

                itemLayout.setAutoWidth();
                itemLayout.setAutoHeight();
                arrowsLayout.addMember(itemLayout);
            }

            arrowsLayout.setAutoWidth();
            arrowsLayout.setAutoHeight();
            setAutoWidth();
            setAutoHeight();
            if (count > 3) {
                setDefaultLayoutAlign(Alignment.CENTER);
            }
            if (children) {
                addMember(new HTMLFlow("<U>" + relationshipName + "</U>"));
                addMember(arrowsLayout);
            } else {
                addMember(arrowsLayout);
                addMember(new HTMLFlow("<U>" + relationshipName + "</U>"));
            }

            setEdgeSize(1);
            setShowEdges(true);

        }
    }

    private static final class ParentConflictItemLayout
            extends VLayout {

        public ParentConflictItemLayout(String relName, List<DigitalObjectRelationships> relParentsList) {
            super();
            setAutoHeight();
            setAutoWidth();
            addMember(new ItemImgButton(relName, RED_CIRCLE));
            Layout relConflits = new HLayout(relParentsList.size() + 1);
            relConflits.addMember(new ArrowImg(ARROW_UP));
            for (DigitalObjectRelationships digObjRel : relParentsList) {
                relConflits.addMember(new ParentConflictRelLayout(digObjRel));
            }
            addMember(relConflits);
            addMember(new HTMLFlow("<U>" + "Conflict" + "</U>"));
            setEdgeSize(1);
            setShowEdges(true);
        }
    }

    private static final class ParentConflictRelLayout
            extends VLayout {

        public ParentConflictRelLayout(DigitalObjectRelationships digObjRel) {
            super();
            setAutoHeight();
            setAutoWidth();

            addMember(new ArrowAskewConflict(ARROW_ASKEW_RIGHT_CONFLICT));

            Layout circleLayout = new HLayout();
            circleLayout.addMember(new DynamicForm() {

                {
                    setWidth(8);
                }
            });
            circleLayout.setHeight(10);
            circleLayout.addMember(new ItemImgButton(digObjRel.getUuid(), RED_CIRCLE));
            addMember(circleLayout);

            Layout conArrowLayout = new HLayout(2);
            conArrowLayout.setDefaultLayoutAlign(VerticalAlignment.BOTTOM);
            conArrowLayout.addMember(new ArrowAskewConflict(ARROW_ASKEW_LEFT_CONFLICT));

            if (digObjRel.getConflictCode() == 3 || digObjRel.getConflictCode() == 2) {
                conArrowLayout.addMember(new ArrowAskewConflict(ARROW_ASKEW_RIGHT_CONFLICT));

                for (String childRel : digObjRel.getChildren().keySet()) {
                    for (DigitalObjectRelationships child : digObjRel.getChildren().get(childRel)) {
                        conArrowLayout.addMember(new ItemImgButton(child.getUuid(), RED_CIRCLE));
                    }
                }
            }
            addMember(conArrowLayout);

        }
    }

    private static final class ArrowAskewConflict
            extends Img {

        public ArrowAskewConflict(String src) {
            super(src);
            setWidth(10);
            setHeight(18);
            setExtraSpace(5);
        }
    }

    public static void setInstanceOf(final String uuid,
                                     final LangConstants lang,
                                     final DispatchAsync dispatcher) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        removeWindow = new RemoveDigitalObjectWindow(uuid, lang, dispatcher);
    }

    public static boolean isInstanceVisible() {
        return (removeWindow != null && removeWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        removeWindow.hide();
        removeWindow = null;
    }

    private RemoveDigitalObjectWindow(final String uuid,
                                      final LangConstants lang,
                                      final DispatchAsync dispatcher) {
        super(450, 550, lang.removeItem() + ": " + uuid);
        this.lang = lang;
        itemList = new ArrayList<RemoveDigitalObjectWindow.ItemImgButton>();
        setShowMaximizeButton(true);

        Button explore = new Button();
        explore.setTitle("Explore");

        rootItemLayout.addMember(new ItemImgButton(uuid, BLACK_SQUARE));
        rootItemLayout.addMember(explore);

        explore.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                callServer(uuid, dispatcher, true);
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
            }
        });

        mainLayout.addMember(rootItemLayout);
        addItem(mainLayout);
        centerInPage();
        show();
        focus();
    }

    private void createRelationshipsTree(DigitalObjectRelationships digitalObjectRelationships) {
        ItemLayout newLayout = new ItemLayout(digitalObjectRelationships, true);
        mainLayout.removeMember(rootItemLayout);
        mainLayout.addMember(newLayout);
        mw.hide();
    }

    /**
     * @param uuid
     * @param dispatcher
     */

    private void callServer(String uuid, DispatchAsync dispatcher, boolean getOnlyChildren) {
        GetRelationshipsAction getDigObjRelAction = new GetRelationshipsAction(uuid, getOnlyChildren);

        DispatchCallback<GetRelationshipsResult> getDigObjRelCallback =
                new DispatchCallback<GetRelationshipsResult>() {

                    @Override
                    public void callback(GetRelationshipsResult result) {
                        sharedPages = result.getSharedPages();
                        createRelationshipsTree(result.getDigObjRel());
                    }

                    @Override
                    public void callbackError(Throwable t) {
                        super.callbackError(t);
                    }
                };
        dispatcher.execute(getDigObjRelAction, getDigObjRelCallback);
    }
}
