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
import com.google.gwt.user.client.ui.Image;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DragRepositionMoveEvent;
import com.smartgwt.client.widgets.events.DragRepositionMoveHandler;
import com.smartgwt.client.widgets.events.DragResizeMoveEvent;
import com.smartgwt.client.widgets.events.DragResizeMoveHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.action.GetFullImgHeightAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetFullImgHeightResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class ChooseDetailWindow
        extends UniversalWindow {

    private int imgHeight = Constants.IMAGE_FULL_HEIGHT;
    private double ratio;


    /**
     * @param imgHeight
     * @param width
     * @param title
     * @param eventBus
     * @param dispatcher
     * @param milisToWait
     */

    public ChooseDetailWindow(final int detailHeight,
                              EventBus eventBus,
                              DispatchAsync dispatcher,
                              String pathToImg,
                              String uuid,
                              LangConstants lang,
                              final boolean isTop,
                              final int topSpace) {
        super(Constants.IMAGE_FULL_HEIGHT + 100, 550, isTop ? lang.chooseDetailTop() : lang
                .chooseDetailBottom(), eventBus, 20);
        setMargin(1);

        final ModalWindow mw = new ModalWindow(ChooseDetailWindow.this);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);
        Image testImg = new Image(pathToImg);
        Layout imgLayout = new Layout();
        imgLayout.setLayoutAlign(Alignment.CENTER);

        imgLayout.setAutoWidth();
        imgLayout.setCanDragResize(false);
        imgLayout.setMargin(10);
        setAlign(Alignment.CENTER);

        final Canvas extract = new Img("other/extraction.png");
        extract.setOverflow(Overflow.HIDDEN);
        extract.setDragRepositionCursor(Cursor.MOVE);
        extract.setCanDragReposition(true);
        extract.setDragAppearance(DragAppearance.TARGET);
        extract.setSmoothFade(true);
        extract.setWidth("450px");
        extract.setHeight(detailHeight);
        extract.setOpacity(70);
        extract.setCanDragResize(true);
        extract.addDragRepositionMoveHandler(new DragRepositionMoveHandler() {

            @Override
            public void onDragRepositionMove(DragRepositionMoveEvent event) {
                if (extract.getLeft() != 0) {
                    event.cancel();
                    extract.setLeft(0);
                }
                if (extract.getTop() < 0) {
                    event.cancel();
                    extract.setTop(0);
                }
                if (extract.getTop() > imgHeight - extract.getHeight()) {
                    event.cancel();
                    extract.setTop(imgHeight - extract.getHeight());
                }
            }
        });

        extract.addDragResizeMoveHandler(new DragResizeMoveHandler() {

            @Override
            public void onDragResizeMove(DragResizeMoveEvent event) {
                int recentHeight = (int) (new Integer(extract.getHeight()).doubleValue() / ratio);
                if (recentHeight > Constants.PAGE_PREVIEW_HEIGHT_MAX) {
                    event.cancel();
                    extract.setHeight((int) (Constants.PAGE_PREVIEW_HEIGHT_MAX * ratio));
                }
            }
        });

        Button okButton = new Button("OK");
        okButton.setExtraSpace(5);
        Button closeButton = new Button(lang.close());

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                int recentHeight = (int) (new Integer(extract.getHeight()).doubleValue() / ratio);
                int topSpace = (int) (new Integer(extract.getTop()).doubleValue() / ratio);
                setDetail(recentHeight, isTop, topSpace);
                hide();
            }
        });

        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        imgLayout.setExtraSpace(8);
        addItem(imgLayout);
        imgLayout.addChild(extract);
        imgLayout.addMember(testImg);

        Layout buttonsLayout = new HLayout(2);
        buttonsLayout.setWidth(220);
        buttonsLayout.setLayoutAlign(Alignment.RIGHT);
        buttonsLayout.addMember(okButton);
        buttonsLayout.addMember(closeButton);
        addItem(buttonsLayout);

        centerInPage();
        show();
        focus();

        Integer width = testImg.getWidth();
        imgHeight = testImg.getHeight();
        setWidth(width + 70);
        setHeight(imgHeight + 100);
        imgLayout.setWidth(width);
        imgLayout.setHeight(imgHeight);
        extract.setTop(isTop ? 0 : imgHeight - extract.getHeight());

        extract.setWidth(width + "px");

        GetFullImgHeightAction heightAction = new GetFullImgHeightAction(uuid);
        DispatchCallback<GetFullImgHeightResult> dispatchCallback =
                new DispatchCallback<GetFullImgHeightResult>() {

                    @Override
                    public void callback(GetFullImgHeightResult result) {
                        int fullHeight = result.getHeight();
                        ratio = new Integer(imgHeight).doubleValue() / new Integer(fullHeight).doubleValue();
                        int newHeight = (int) (detailHeight * ratio);
                        extract.setHeight(newHeight);
                        extract.setTop((int) ((topSpace < 0) ? (0)
                                : (((fullHeight - newHeight) < topSpace) ? imgHeight - extract.getHeight()
                                        : new Integer(topSpace).doubleValue() * ratio)));
                        mw.hide();
                    }

                    @Override
                    public void callbackError(Throwable t) {
                        super.callbackError(t);
                    }
                };
        dispatcher.execute(heightAction, dispatchCallback);

        imgLayout.redraw();
    }

    protected abstract void setDetail(int recentHeight, boolean isTop, int topSpace);
}
