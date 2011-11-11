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

import com.gwtplatform.dispatch.client.DispatchAsync;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;

import cz.fi.muni.xkremser.editor.shared.rpc.action.LockDigitalObjectAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.LockDigitalObjectResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class LockDigitalObjectWindow
        extends UniversalWindow {

    private final LangConstants lang;
    private static LockDigitalObjectWindow lockWindow = null;

    public static void setInstanceOf(LangConstants lang, EditorTabSet ts, DispatchAsync dispatcher) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        lockWindow = new LockDigitalObjectWindow(lang, ts, dispatcher);
    }

    public static boolean isInstanceVisible() {
        return (lockWindow != null && lockWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        lockWindow.destroy();
        lockWindow = null;
    }

    private LockDigitalObjectWindow(final LangConstants lang,
                                    final EditorTabSet ts,
                                    final DispatchAsync dispatcher) {
        super(300, 510, lang.lockObjectWindow() + ": " + ts.getUuid());

        this.lang = lang;
        final RichTextEditor textEditor = new RichTextEditor();
        textEditor.setHeight(200);
        textEditor.setWidth(473);
        textEditor.setOverflow(Overflow.HIDDEN);
        textEditor.setEdgeSize(3);
        textEditor.setExtraSpace(10);
        textEditor.setShowEdges(true);
        if (ts.getLockDescription() != null) {
            textEditor.setValue(ts.getLockDescription());
        }
        HLayout layout = new HLayout();
        Button lock = new Button();
        lock.setExtraSpace(8);
        lock.setTitle(lang.lockItem());
        Button close = new Button();
        close.setTitle(lang.close());
        layout.addMember(lock);
        layout.addMember(close);

        lock.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ts.setLockDescription(textEditor.getValue().equals("<br>") ? null : textEditor.getValue());
                lockDigitalObject(ts, dispatcher);
                closeInstantiatedWindow();
            }
        });

        close.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                closeInstantiatedWindow();
            }
        });
        layout.setAutoWidth();
        setEdgeOffset(20);
        addItem(textEditor);
        addItem(layout);
        centerInPage();
        show();
        lock.focus();
        layout.setLeft(260);

    }

    private void lockDigitalObject(final EditorTabSet ts, final DispatchAsync dispatcher) {
        final ModalWindow mw = new ModalWindow(ts);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        final LockDigitalObjectAction lockAction =
                new LockDigitalObjectAction(ts.getUuid(), ts.getLockDescription());
        final DispatchCallback<LockDigitalObjectResult> lockCallback =
                new DispatchCallback<LockDigitalObjectResult>() {

                    @Override
                    public void callback(LockDigitalObjectResult result) {
                        if (result.getLockOwner() == null) {
                            SC.say(lang.objectLocked(), lang.objectLocked() + "<br>" + lang.lockNote());

                        } else if ("".equals(result.getLockOwner())) {
                            SC.say(lang.lockUpdated(), lang.lockUpdated());

                        } else {
                            EditorSC.objectIsLock(lang,
                                                  result.getLockOwner(),
                                                  result.getLockDescription(),
                                                  result.getTimeToExpiration());
                        }
                        mw.hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        if (t.getMessage() != null && t.getMessage().length() > 0
                                && t.getMessage().charAt(0) == Constants.SESSION_EXPIRED_FLAG) {
                            SC.confirm("Session has expired. Do you want to be redirected to login page?",
                                       new BooleanCallback() {

                                           @Override
                                           public void execute(Boolean value) {
                                               if (value != null && value) {
                                                   MEditor.redirect(t.getMessage().substring(1));
                                               }
                                           }
                                       });
                        } else {
                            SC.ask(t.getMessage() + "<br>" + lang.mesTryAgain(), new BooleanCallback() {

                                @Override
                                public void execute(Boolean value) {
                                    if (value != null && value) {
                                        lockDigitalObject(ts, dispatcher);
                                    }
                                }
                            });
                        }
                        mw.hide();
                    }
                };
        dispatcher.execute(lockAction, lockCallback);
    }
}
