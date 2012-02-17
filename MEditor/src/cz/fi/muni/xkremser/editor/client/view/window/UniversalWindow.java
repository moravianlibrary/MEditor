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
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.event.EscKeyPressedEvent;
import cz.fi.muni.xkremser.editor.shared.event.EscKeyPressedEvent.EscKeyPressedHandler;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class UniversalWindow
        extends Window {

    private final HasHandlers eventBus;

    public UniversalWindow(int height, int width, String title, EventBus eventBus, final int milisToWait) {
        this.eventBus = eventBus;
        setMembersMargin(10);
        setHeight(height);
        setWidth(width);
        setCanDragResize(true);
        setShowEdges(true);
        setTitle(title);
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClientEvent event) {
                hide();
            }
        });
        eventBus.addHandler(EscKeyPressedEvent.getType(), new EscKeyPressedHandler() {

            @Override
            public void onEscKeyPressed(final EscKeyPressedEvent event) {
                Timer t = new Timer() {

                    @Override
                    public void run() {
                        if (UniversalWindow.this != null && UniversalWindow.this.isCreated()
                                && !event.isCancelled()) {
                            UniversalWindow.this.hide();
                            event.cancel();
                        }
                    }
                };
                t.schedule(milisToWait);
            }
        });
    }

    @Override
    public void hide() {
        animateHide(AnimationEffect.FLY, new AnimationCallback() {

            @Override
            public void execute(boolean earlyFinish) {
                destroy();
            }
        }, Constants.WINDOW_ANIMATION_DELAY_IN_MILLIS);
    }

    @Override
    public void show() {
        show(null);
    }

    public void show(AnimationCallback callback) {
        animateShow(AnimationEffect.FLY, callback, Constants.WINDOW_ANIMATION_DELAY_IN_MILLIS);
    }

    public HasHandlers getEventBus() {
        return eventBus;
    }

}
