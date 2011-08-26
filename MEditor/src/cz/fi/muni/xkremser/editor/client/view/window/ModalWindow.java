/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

// TODO: Auto-generated Javadoc
/**
 * Class that supports masking an entire {@link Canvas} and adds the possibility
 * to display a message during masking and a loading image.
 * 
 * @author Mihai Ile (mihai007)
 */
public class ModalWindow {

    /** The canvas to be masked. */
    private final Canvas canvas;

    /** Parent for modal layer. */
    private HLayout parent;

    /** The modal layer. */
    private VLayout modal;

    /** The reference to the transparent layer inside the modal HStack. */
    private Canvas transparent;

    /** The exact time when show was called on this modal. */
    private long showStartedTime;

    /**
     * The loading icon, by default "{@link Page#getSkinImgDir()}/loading.gif"
     */
    private String loadingIcon = null;

    /** The color, by default white. */
    private String messageBoxBgColor = "#fff";

    /** Holds the default opacity for the background masking color. */
    private int opacity = 30;

    /** Holds the default color for the masking. */
    private String maskingColor = "#555";

    /**
     * Creates a new {@link ModalWindow} given the canvas to be masked (an.
     * 
     * @param canvas
     *        the canvas to be masked {@link Canvas#addChild(Canvas)} will be
     *        called to add the masking layer above the given canvas)
     */
    public ModalWindow(Canvas canvas) {
        this.canvas = canvas;
        createModalPanel();
    }

    /**
     * Creates a new {@link ModalWindow} given the canvas to be masked (an.
     * 
     * @param canvas
     *        the canvas to be masked
     * @param opacity
     *        the opacity used for modal
     * @param maskingColor
     *        the color used for modal {@link Canvas#addChild(Canvas)} will be
     *        called to add the masking layer above the given canvas)
     */
    public ModalWindow(Canvas canvas, int opacity, String maskingColor) {
        this.canvas = canvas;
        this.opacity = opacity;
        this.maskingColor = maskingColor;
        createModalPanel();
    }

    /**
     * Mask the {@link Canvas} with a transparent color.
     * 
     * @param showLoading
     *        whether to show a box with a loading indicator above the
     *        background
     */
    public void show(boolean showLoading) {
        showStartedTime = System.currentTimeMillis();
        insertModalIntoCanvas();
        clearLabel();
        if (showLoading) {
            modal.addMember(createLabel("", showLoading));
        }
        parent.show();
    }

    /**
     * Mask the {@link Canvas} with a transparent color and display a message
     * above it.
     * 
     * @param message
     *        the message to display above the background
     * @param showLoading
     *        whether to show a box with a loading indicator above the
     *        background
     */
    public void show(String message, boolean showLoading) {
        showStartedTime = System.currentTimeMillis();
        insertModalIntoCanvas();
        clearLabel();
        if (showLoading || !message.equals("")) {
            modal.addMember(createLabel(message, showLoading));
        }
        parent.show();
    }

    /**
     * Hide the masking layer from the {@link Canvas}.
     */
    public void hide() {
        long showEndTime = System.currentTimeMillis();
        // don't hide if is showed for less than 1s, avoid flickering
        int delay = 1000;
        if (showEndTime - showStartedTime < delay) {
            new Timer() {

                @Override
                public void run() {
                    _hide();
                }
            }.schedule((int) (delay - (showEndTime - showStartedTime)));
        } else {
            _hide();
        }
    }

    /**
     * _hide.
     */
    private void _hide() {
        parent.hide();
    }

    /**
     * Insert modal into canvas.
     */
    private void insertModalIntoCanvas() {
        if (!canvas.contains(parent)) {
            canvas.addChild(parent);
            canvas.addResizedHandler(new ResizedHandler() {

                @Override
                public void onResized(ResizedEvent event) {
                    parent.setWidth(canvas.getWidth());
                    parent.setHeight(canvas.getHeight());
                }
            });
        }
    }

    /**
     * Clear label.
     */
    private void clearLabel() {
        Canvas[] children = modal.getChildren();
        for (Canvas canvas : children) {
            if (canvas instanceof Label) {
                Label label = (Label) canvas;
                modal.removeChild(label);
                label.destroy();
            }
        }
    }

    /**
     * Creates the modal panel.
     */
    private void createModalPanel() {
        parent = new HLayout();
        parent.setDefaultLayoutAlign(VerticalAlignment.CENTER);
        parent.hide();
        // we need a handler to ensure we always cover the entire canvas
        parent.addDrawHandler(new DrawHandler() {

            @Override
            public void onDraw(DrawEvent event) {
                parent.setWidth(canvas.getVisibleWidth());
                parent.setHeight(canvas.getVisibleHeight());
            }
        });

        transparent = new Canvas();
        transparent.setWidth100();
        transparent.setHeight100();
        transparent.setBackgroundColor(maskingColor);
        transparent.setOpacity(opacity);
        parent.addChild(transparent);

        modal = new VLayout();
        modal.setDefaultLayoutAlign(Alignment.CENTER);
        modal.setHeight(25); // this is for label height
        modal.setZIndex(transparent.getZIndex() + 2);
        parent.addMember(modal);

        insertModalIntoCanvas();
    }

    /**
     * Creates the label.
     * 
     * @param message
     *        the message
     * @param showLoading
     *        the show loading
     * @return the label
     */
    private Label createLabel(String message, boolean showLoading) {
        final Label label = new Label();
        label.setWrap(false);
        label.setPadding(5);
        label.setWidth(1);
        label.setHeight(1);
        label.setContents(message);
        label.setBackgroundColor(messageBoxBgColor);
        label.setBorder("1px solid #999");
        label.setShowShadow(true);
        label.setShadowSoftness(0);
        label.setShadowOffset(10);
        label.addDrawHandler(new DrawHandler() {

            @Override
            public void onDraw(DrawEvent event) {
                int visibleWidth = label.getVisibleWidth();
                label.setWidth(visibleWidth);
                label.setMargin(10);
            }
        });
        label.setAlign(Alignment.CENTER);
        if (showLoading) {
            if (loadingIcon != null) { // icon provided by user
                label.setIcon(loadingIcon);
            } else { // show default icon from used skin
                String icon = Page.getSkinImgDir() + "loading.gif";
                label.setIcon(icon);
            }
            if (message.equals("")) { // no spacing, just show the loading icon
                // centered
                label.setIconSpacing(0);
            }
        }
        label.setZIndex(modal.getZIndex() + 2);
        return label;
    }

    /**
     * Destroy the {@link ModalWindow} freeing up resources.
     */
    public void destroy() {
        parent.destroy();
    }

    /**
     * Optional icon to be used instead of the default one.
     * <P>
     * Specify as the partial URL to an image, relative to the imgDir (by
     * default "public/images/") of this component
     * 
     * @param loadingIcon
     *        icon URL of new image icon. Default value is <blockquote> "
     *        {@link Page#getSkinImgDir()}/loading.gif "
     */
    public void setLoadingIcon(String loadingIcon) {
        this.loadingIcon = loadingIcon;
    }

    /**
     * The background color for the message/loading box. You can set this
     * property to an RGB value (e.g. #22AAFF) or a named color (e.g. red) from
     * a list of browser supported color names.
     * 
     * @param backgroundColor
     *        new background color to set to the message/loading box. Default
     *        value is "#fff"
     */
    public void setBackgroundColor(String backgroundColor) {
        this.messageBoxBgColor = backgroundColor;
    }

}