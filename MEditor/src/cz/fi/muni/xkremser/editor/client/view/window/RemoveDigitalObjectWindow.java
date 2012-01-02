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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants.CONFLICT;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

import cz.fi.muni.xkremser.editor.shared.event.OpenDigitalObjectEvent;
import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectRelationships;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetRelationshipsResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveDigitalObjectResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class RemoveDigitalObjectWindow
        extends UniversalWindow {

    /** Images constants */
    private static final String GREEN_CIRCLE = "other/greenCircle.png";
    private static final String RED_CIRCLE = "other/redCircle.png";
    private static final String GREEN_FOCUSED_CIRCLE = "other/greenCircle_Over.png";
    private static final String ARROW_LEFT_CONFLICT = "other/arrowLeftConflict.png";
    private static final String RED_FOCUSED_CIRCLE = "other/redCircle_Over.png";
    private static final String GREEN_SQUARE = "other/greenSquare.png";
    private static final String RED_SQUARE = "other/redSquare.png";
    private static final String ARROW_DOWN = "other/arrowDown.png";
    private static final String THREE_DOTS = "other/threeDots.png";
    private static final String ARROW_ASKEW_RIGHT_CONFLICT = "other/arrowAskewRightConflict.png";
    private static final String ARROW_ASKEW_LEFT_CONFLICT = "other/arrowAskewLeftConflict.png";

    private static LangConstants lang;

    /** The uuid of the object is going to be removed */
    private final String uuid;

    /** The dispatcher */
    private final DispatchAsync dispatcher;

    private static RemoveDigitalObjectWindow removeWindow = null;

    /** The main layout */
    private final Layout mainLayout = new VLayout();

    /** The layout depicted on the beginning */
    Layout warningLayout = new VLayout();

    /** The loading progress bar */
    private final ModalWindow mw = new ModalWindow(mainLayout);

    /** Pages which is some internal part or article on */
    private static List<String> sharedPages;

    /** The list of uuid of digital object which has the first conflict */
    private List<String> uuidNotToRemove = new ArrayList<String>();

    /** List of all depicted ItemImgButtons */
    private static List<ItemImgButton> itemList;

    private boolean isConflict = false;

    private int lowestLevel;

    private static EventBus eventBus;

    private static final class ItemImgButton
            extends ImgButton {

        private final String uuid;
        private final String originalSrc;

        public ItemImgButton(final String uuid, final String src, final boolean isRoot) {
            super();
            this.uuid = uuid;
            this.originalSrc = src;
            itemList.add(this);
            if (isRoot) {
                setWidth(15);
                setHeight(15);
            } else {
                setWidth(10);
                setHeight(10);
            }
            setSrc(src);
            setShowFocused(false);
            setShowDown(false);
            setHoverStyle("interactImageHover");
            setHoverWidth(310);
            setExtraSpace(5);

            addRightMouseDownHandler(new RightMouseDownHandler() {

                @Override
                public void onRightMouseDown(RightMouseDownEvent event) {
                    Menu menu = new Menu();
                    menu.setShowShadow(true);
                    menu.setShadowDepth(10);
                    MenuItem newItem = new MenuItem(lang.menuEdit(), "icons/16/document_plain_new.png");
                    newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

                        @Override
                        public void onClick(MenuItemClickEvent event) {
                            eventBus.fireEvent(new OpenDigitalObjectEvent(uuid));
                        }
                    });
                    menu.addItem(newItem);
                    setContextMenu(menu);
                }
            });

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

        public ArrowImg() {
            super(ARROW_DOWN);
            setWidth(9);
            setHeight(50);
            setExtraSpace(5);
        }
    }

    private final class ThreeDots
            extends Layout {

        public ThreeDots(final List<DigitalObjectRelationships> digObjRelList,
                         final boolean children,
                         final boolean isParentConflict) {
            super();
            final ImgButton img = new ImgButton();
            img.setSrc(THREE_DOTS);
            img.setShowFocused(false);
            img.setShowDown(false);
            setVertical(true);
            img.setWidth(24);
            img.setHeight(6);
            addMember(img);
            setHeight(isParentConflict ? 10 : 60);
            setAutoWidth();
            setAlign(isParentConflict ? VerticalAlignment.BOTTOM : VerticalAlignment.CENTER);
            setDefaultLayoutAlign(isParentConflict ? VerticalAlignment.BOTTOM : VerticalAlignment.CENTER);
            img.setCanHover(true);
            img.setHoverOpacity(75);
            img.setHoverStyle("interactImageHover");
            img.setHoverOpacity(75);
            img.setShowHover(true);
            img.addHoverHandler(new HoverHandler() {

                @Override
                public void onHover(HoverEvent event) {
                    img.setPrompt(lang.unroll());
                }
            });
            img.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);
                    Timer timer = new Timer() {

                        @Override
                        public void run() {
                            removeMember(img);
                            setVertical(false);
                            for (DigitalObjectRelationships digObj : digObjRelList) {
                                if (!isParentConflict) {
                                    Layout createdRelLayout =
                                            createRelLayout(digObj,
                                                            2 + digObjRelList.indexOf(digObj),
                                                            children);
                                    createdRelLayout.setExtraSpace(5);
                                    addMember(createdRelLayout);
                                } else {

                                    ItemImgButton itemImgButton =
                                            new ItemImgButton(digObj.getUuid(), RED_CIRCLE, false);
                                    itemImgButton.setValign(VerticalAlignment.BOTTOM);
                                    addMember(itemImgButton);
                                }
                            }
                            customizeWindow();
                            mw.hide();
                        }
                    };
                    timer.schedule(25);
                }
            });
        }
    }

    private static final class ArrowLeftConflict
            extends ImgButton {

        public ArrowLeftConflict(final Map<String, List<DigitalObjectRelationships>> map) {
            super();
            setSrc(ARROW_LEFT_CONFLICT);
            setWidth(37);
            setHeight(9);
            setExtraSpace(5);
            setShowRollOver(false);
            setShowDown(false);
            setCanHover(true);
            setHoverOpacity(95);
            setHoverWidth(300);
            setHoverStyle("interactImageHover");
            setShowHover(true);
            addHoverHandler(new HoverHandler() {

                @Override
                public void onHover(HoverEvent event) {
                    setPrompt(lang.conflictChildExtRef() + "<br><br>"
                            + "<img src=\"images/other/conflictChildExtRef.png\">");
                }
            });
            addRightMouseDownHandler(new RightMouseDownHandler() {

                @Override
                public void onRightMouseDown(RightMouseDownEvent event) {
                    Menu menu = new Menu();
                    menu.setShowShadow(true);
                    menu.setShadowDepth(10);
                    for (final String uuid : map.keySet()) {
                        MenuItem newItem =
                                new MenuItem(lang.menuEdit() + (map.size() == 1 ? "" : " " + uuid),
                                             "icons/16/document_plain_new.png");

                        newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

                            @Override
                            public void onClick(MenuItemClickEvent event) {
                                eventBus.fireEvent(new OpenDigitalObjectEvent(uuid));
                            }
                        });
                        menu.addItem(newItem);
                    }
                    setContextMenu(menu);
                }
            });
        }
    }

    private static final class ArrowAskewConflict
            extends Img {

        public ArrowAskewConflict(String src, final CONFLICT conflict) {
            super(src);
            setWidth(10);
            setHeight(18);
            setExtraSpace(5);
            setCanHover(true);
            setHoverOpacity(95);
            setHoverWidth(250);
            setHoverStyle("interactImageHover");
            setShowHover(true);
            addHoverHandler(new HoverHandler() {

                @Override
                public void onHover(HoverEvent event) {
                    if (conflict == CONFLICT.SAME_PARENT_GRANDPARENT) {
                        setPrompt(lang.conflictPaGrandpa() + "<br><br>"
                                + "<img src=\"images/other/conflictPaGrandpa.png\">");
                    } else if (conflict == CONFLICT.COUSIN) {
                        setHoverWidth(450);
                        setPrompt(lang.conflictCousin() + "<br><br><center>" + lang.or() + "</center>"
                                + "<img src=\"images/other/conflictCousin.png\">");
                    } else if (conflict == CONFLICT.UNCLE_COUSINE) {
                        setPrompt(lang.conflictUncleCousin() + "<br><br>"
                                + "<img src=\"images/other/conflictUncleCousin.png\">");
                    }
                }
            });
        }
    }

    private final class ItemLayout
            extends VLayout {

        public ItemLayout(DigitalObjectRelationships digObjRel, boolean isRoot, boolean isFirst) {
            super();
            ItemImgButton itemImgButton;
            Map<String, List<DigitalObjectRelationships>> parentsMap = digObjRel.getParents();

            if (isRoot) {
                Layout parentsRelLayout = new HLayout(parentsMap.size());
                parentsRelLayout.setAutoWidth();
                parentsRelLayout.setAutoHeight();

                for (String relName : parentsMap.keySet()) {
                    if (parentsMap.get(relName).size() > 0) {
                        Collections.sort(parentsMap.get(relName));
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
                if (digObjRel.getConflict() != CONFLICT.NO_CONFLICT) {
                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), RED_SQUARE, true);
                    if (!isConflict) isConflict = true;
                } else {
                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), GREEN_SQUARE, true);
                }
            } else {
                if (digObjRel.getConflict() != CONFLICT.NO_CONFLICT) {
                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), RED_CIRCLE, false);
                } else {
                    itemImgButton = new ItemImgButton(digObjRel.getUuid(), GREEN_CIRCLE, false);
                }
            }
            if (digObjRel.getConflict() == CONFLICT.CHILD_EXTERNAL_REFERENCE) {
                Layout conflictLayout = new HLayout(2);
                itemImgButton.setExtraSpace(0);
                conflictLayout.addMember(itemImgButton);
                conflictLayout.addMember(new ArrowLeftConflict(digObjRel.getParents()));
                conflictLayout.setAutoHeight();
                conflictLayout.setAutoWidth();
                addMember(conflictLayout);
            } else {
                addMember(itemImgButton);
            }

            Map<String, List<DigitalObjectRelationships>> childrenMap = digObjRel.getChildren();
            Layout childrenRelLayout = new HLayout(childrenMap.size());

            if (isFirst && childrenMap.size() > 0) lowestLevel++;
            for (String relName : childrenMap.keySet()) {
                Collections.sort(childrenMap.get(relName));
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

    private final class RelationshipsLayout
            extends VLayout {

        public RelationshipsLayout(String relationshipName,
                                   List<DigitalObjectRelationships> digObjRelList,
                                   boolean children) {
            super();
            boolean toMany = false;
            int count = ((toMany = (digObjRelList.size() > 5)) ? 2 : digObjRelList.size());
            Layout arrowsLayout = new HLayout(count);

            for (int i = 0; i < count; i++) {
                arrowsLayout.addMember(createRelLayout(digObjRelList.get(i), i, children));
            }

            if (toMany) {
                arrowsLayout.addMember(new ThreeDots(digObjRelList.subList(count, digObjRelList.size() - 1),
                                                     children,
                                                     false));
                arrowsLayout.addMember(createRelLayout(digObjRelList.get(digObjRelList.size() - 1),
                                                       digObjRelList.size() - 1,
                                                       children));
            }

            arrowsLayout.setAutoWidth();
            arrowsLayout.setAutoHeight();
            setAutoWidth();
            setAutoHeight();
            if (count > 3) {
                setDefaultLayoutAlign(Alignment.CENTER);
            }
            if (children) {
                addMember(new HTMLFlow(HtmlCode.underline(relationshipName)));
                addMember(arrowsLayout);
            } else {
                addMember(arrowsLayout);
                addMember(new HTMLFlow(HtmlCode.underline(relationshipName)));
            }

            setEdgeSize(1);
            setShowEdges(true);

        }
    }

    private final class ParentConflictItemLayout
            extends VLayout {

        public ParentConflictItemLayout(String relName, List<DigitalObjectRelationships> relParentsList) {
            super();
            setAutoHeight();
            setAutoWidth();
            addMember(new ItemImgButton(relName, RED_CIRCLE, false));
            Layout relConflits = new HLayout(relParentsList.size() + 1);
            relConflits.addMember(new ArrowImg());
            for (DigitalObjectRelationships digObjRel : relParentsList) {
                relConflits.addMember(new ParentConflictRelLayout(digObjRel));
            }
            addMember(relConflits);
            addMember(new HTMLFlow(HtmlCode.underline("Conflict")));
            setEdgeSize(1);
            setShowEdges(true);
        }
    }

    private final class ParentConflictRelLayout
            extends VLayout {

        public ParentConflictRelLayout(DigitalObjectRelationships digObjRel) {
            super();
            setAutoHeight();
            setAutoWidth();

            addMember(new ArrowAskewConflict(ARROW_ASKEW_RIGHT_CONFLICT, digObjRel.getConflict()));

            Layout circleLayout = new HLayout();
            circleLayout.addMember(new DynamicForm() {

                {
                    setWidth(8);
                }
            });
            circleLayout.setHeight(10);
            ItemImgButton conflictCircle = new ItemImgButton(digObjRel.getUuid(), RED_CIRCLE, false);
            new ItemImgButton(digObjRel.getUuid(), RED_CIRCLE, false).setExtraSpace(0);
            circleLayout.addMember(conflictCircle);
            addMember(circleLayout);

            Layout conArrowLayout = new HLayout(2);
            conArrowLayout.setDefaultLayoutAlign(VerticalAlignment.BOTTOM);
            conArrowLayout.addMember(new ArrowAskewConflict(ARROW_ASKEW_LEFT_CONFLICT, digObjRel
                    .getConflict()));

            if (digObjRel.getConflict() == CONFLICT.COUSIN
                    || digObjRel.getConflict() == CONFLICT.UNCLE_COUSINE) {
                conArrowLayout.addMember(new ArrowAskewConflict(ARROW_ASKEW_RIGHT_CONFLICT, digObjRel
                        .getConflict()));

                for (String childRel : digObjRel.getChildren().keySet()) {

                    List<DigitalObjectRelationships> digObjRelList = digObjRel.getChildren().get(childRel);
                    boolean toMany = false;
                    int count = ((toMany = (digObjRelList.size() > 5)) ? 2 : digObjRelList.size());

                    for (int i = 0; i < count; i++) {
                        conArrowLayout.addMember(new ItemImgButton(digObjRelList.get(i).getUuid(),
                                                                   RED_CIRCLE,
                                                                   false));
                    }

                    if (toMany) {
                        conArrowLayout
                                .addMember(new ThreeDots(digObjRelList.subList(count,
                                                                               digObjRelList.size() - 1),
                                                         false,
                                                         true));
                        conArrowLayout.addMember(new ItemImgButton(digObjRelList
                                .get(digObjRelList.size() - 1).getUuid(), RED_CIRCLE, false));
                    }
                }
            }
            addMember(conArrowLayout);
            setEdgeSize(1);
            setShowEdges(true);
        }
    }

    private final class ButtonsLayout
            extends HLayout {

        private final Button remove = new Button();

        private final Button close = new Button();

        public ButtonsLayout(String removeTitle) {
            super(2);
            remove.setTitle(removeTitle);
            remove.setExtraSpace(5);
            close.setTitle(lang.close());
            setLayoutAlign(Alignment.RIGHT);
            setWidth(remove.getWidth() + close.getWidth() + 20);
            setHeight(15);
            setExtraSpace(10);
            addMember(remove);
            addMember(close);

            close.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    RemoveDigitalObjectWindow.this.hide();
                }
            });

            remove.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    mw.setLoadingIcon("loadingAnimation.gif");
                    mw.show(true);

                    StringBuffer sb = new StringBuffer("");
                    if (isConflict) {
                        sb.append(lang.isConflictsWarning());
                        if (lowestLevel > 2) sb.append("<br>");
                    }
                    if (lowestLevel > 2) sb.append(lang.abundantTree());
                    if (isConflict || lowestLevel > 2) sb.append("<br><br>").append(lang.wishContinue());

                    if (!"".equals(sb.toString())) {
                        SC.ask(sb.toString(), new BooleanCallback() {

                            @Override
                            public void execute(Boolean value) {
                                if (value) {
                                    RemoveDigitalObjectWindow.this.remove();
                                } else {
                                    mw.hide();
                                }
                            }
                        });
                    } else {
                        RemoveDigitalObjectWindow.this.remove();
                    }
                }
            });
        }
    }

    public static void setInstanceOf(final String uuid,
                                     final LangConstants lang,
                                     final DispatchAsync dispatcher,
                                     final EventBus eventBus) {

        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        removeWindow = new RemoveDigitalObjectWindow(uuid, lang, dispatcher, eventBus);
    }

    public static boolean isInstanceVisible() {
        return (removeWindow != null && removeWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        removeWindow.hide();
        removeWindow = null;
    }

    @SuppressWarnings("static-access")
    private RemoveDigitalObjectWindow(final String uuid,
                                      final LangConstants lang,
                                      final DispatchAsync dispatcher,
                                      EventBus eventBus) {
        super(180, 570, lang.removeItem() + ": " + uuid);
        this.lang = lang;
        this.uuid = uuid;
        this.dispatcher = dispatcher;
        this.eventBus = eventBus;
        setEdgeOffset(15);
        itemList = new ArrayList<RemoveDigitalObjectWindow.ItemImgButton>();
        lowestLevel = 1;
        setShowMaximizeButton(true);

        HTMLFlow removeWarning =
                new HTMLFlow(HtmlCode.bold(lang.deleteWarningWant() + ": " + uuid + " "
                        + lang.deleteWarningExplore()));
        removeWarning.setExtraSpace(5);

        Button explore = new Button();
        explore.setTitle(lang.explore());
        explore.setLayoutAlign(Alignment.CENTER);

        warningLayout.addMember(removeWarning);

        Layout rootItemLayout = new HLayout();
        rootItemLayout.addMember(explore);
        warningLayout.addMember(rootItemLayout);

        explore.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                mw.setLoadingIcon("loadingAnimation.gif");
                mw.show(true);
                Timer timer = new Timer() {

                    @Override
                    public void run() {
                        callServer();
                    }
                };
                timer.schedule(25);
            }
        });

        warningLayout.addMember(new ButtonsLayout(lang.deleteAnyway()));

        mainLayout.addMember(warningLayout);
        mainLayout.setAutoHeight();
        mainLayout.setAutoWidth();

        addItem(mainLayout);
        centerInPage();
        show();
        explore.focus();
    }

    private void createRelationshipsTree(DigitalObjectRelationships digitalObjectRelationships) {
        ItemLayout newLayout = new ItemLayout(digitalObjectRelationships, true, true);
        mainLayout.removeMember(warningLayout);
        mainLayout.addMember(newLayout);
        addMember(new ButtonsLayout(lang.removeItem()));
        customizeWindow();
        mw.hide();
    }

    private void customizeWindow() {
        if ((itemList.size()) < 9) {
            setWidth(300);
        } else {
            setWidth100();
            int newWidth = itemList.size() * 35;
            if (newWidth < getWidth()) setWidth(newWidth);
        }
        setHeight100();
        int newHeight = (lowestLevel * 85) + 130;
        if (newHeight < getHeight()) setHeight(newHeight);
        centerInPage();
    }

    private Layout createRelLayout(DigitalObjectRelationships child, int index, boolean children) {
        Layout itemLayout = new VLayout(2);

        if (children) {
            itemLayout.addMember(new ArrowImg());
            itemLayout.addMember(new ItemLayout(child, false, index == 0));

        } else {
            itemLayout.addMember(new ItemImgButton(child.getUuid(), GREEN_CIRCLE, false));
            itemLayout.addMember(new ArrowImg());
        }

        itemLayout.setAutoWidth();
        itemLayout.setAutoHeight();
        return itemLayout;
    }

    /**
     * @param uuid
     * @param dispatcher
     */

    private void callServer() {
        GetRelationshipsAction getDigObjRelAction = new GetRelationshipsAction(uuid);

        DispatchCallback<GetRelationshipsResult> getDigObjRelCallback =
                new DispatchCallback<GetRelationshipsResult>() {

                    @Override
                    public void callback(GetRelationshipsResult result) {
                        sharedPages = result.getSharedPages();
                        uuidNotToRemove = result.getUuidNotToRemove();

                        //                        System.err.println("count of uuid not to remove: " + uuidNotToRemove.size());
                        //                        for (String uuid : uuidNotToRemove) {
                        //                            System.err.println("shared: " + uuid);
                        //                        }

                        createRelationshipsTree(result.getDigObjRel());
                    }

                    @Override
                    public void callbackError(Throwable t) {
                        super.callbackError(t);
                    }
                };
        dispatcher.execute(getDigObjRelAction, getDigObjRelCallback);
    }

    /**
     * 
     */

    protected void remove() {
        RemoveDigitalObjectAction removeAction = new RemoveDigitalObjectAction(uuid, uuidNotToRemove);
        DispatchCallback<RemoveDigitalObjectResult> removeCallback =
                new DispatchCallback<RemoveDigitalObjectResult>() {

                    @Override
                    public void callback(RemoveDigitalObjectResult result) {
                        mw.hide();
                        RemoveDigitalObjectWindow.this.hide();
                        if (result.getErrorMessage() == null) {
                            StringBuffer sb = new StringBuffer(lang.removedUuids());
                            sb.append("<br>");
                            for (String removedUuid : result.getRemoved()) {
                                sb.append("<br>").append(removedUuid);
                            }
                            EditorSC.operationSuccessful(lang, sb.toString());
                        } else {
                            EditorSC.operationFailed(lang, result.getErrorMessage());
                        }
                    }

                    @Override
                    public void callbackError(Throwable t) {
                        mw.hide();
                        super.callbackError(t);
                    }
                };

        dispatcher.execute(removeAction, removeCallback);
    }
}
