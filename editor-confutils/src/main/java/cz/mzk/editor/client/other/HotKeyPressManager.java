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

package cz.mzk.editor.client.other;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.web.bindery.event.shared.EventBus;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.shared.event.EscKeyPressedEvent;
import cz.mzk.editor.shared.event.KeyPressedEvent;
import cz.mzk.editor.shared.event.SetEnabledHotKeysEvent;

/**
 * @author Matous Jobanek
 * @version Jan 8, 2013
 */
public class HotKeyPressManager {

    private static HotKeyPressManager hotKeyPressEventHandler = null;

    private boolean isHotKeysEnabled = true;

    public static void setInstanceOf(EventBus eventBus) {
        if (hotKeyPressEventHandler == null) hotKeyPressEventHandler = new HotKeyPressManager(eventBus);
    }

    /**
     * 
     */
    private HotKeyPressManager(final EventBus eventBus) {
        /** Hot-keys operations **/
        Event.addNativePreviewHandler(new NativePreviewHandler() {

            private boolean isKnownCtrlAltHotkey(NativePreviewEvent event) {
                if (event.getNativeEvent().getCtrlKey() && event.getNativeEvent().getAltKey()) {
                    int code = event.getNativeEvent().getKeyCode();
                    for (Constants.HOT_KEYS_WITH_CTRL_ALT key : Constants.HOT_KEYS_WITH_CTRL_ALT.values()) {
                        if (code == key.getCode()) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onPreviewNativeEvent(NativePreviewEvent event) {

                if (event.getTypeInt() != Event.ONKEYDOWN) {
                    return;
                }
                int keyCode = event.getNativeEvent().getKeyCode();

                //                System.err.println("pressed key code: " + event.getNativeEvent().getKeyCode());

                if (keyCode != Constants.CODE_KEY_ESC && keyCode != Constants.CODE_KEY_ENTER
                        && keyCode != Constants.CODE_KEY_DELETE && !isKnownCtrlAltHotkey(event)) {
                    return;
                }
                if (isHotKeysEnabled) {
                    if (keyCode == Constants.CODE_KEY_ESC) {
                        eventBus.fireEvent(new EscKeyPressedEvent());
                        return;
                    }
                    eventBus.fireEvent(new KeyPressedEvent(keyCode));
                } else {
                    event.cancel();
                }
            }

        });

        eventBus.addHandler(SetEnabledHotKeysEvent.getType(),
                            new SetEnabledHotKeysEvent.SetEnabledHotKeysHandler() {

                                @Override
                                public void onSetEnabledHotKeys(SetEnabledHotKeysEvent event) {
                                    isHotKeysEnabled = event.isEnable();
                                }
                            });

    }
}
