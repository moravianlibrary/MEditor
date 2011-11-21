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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.dispatcher.TryAgainCallbackError;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;

import cz.fi.muni.xkremser.editor.shared.rpc.LockInfo;
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
        super(310, 510, lang.lockObjectWindow() + ": " + ts.getUuid());

        final LockInfo lockInfo = ts.getLockInfo();
        this.lang = lang;
        final Label descLabel = new Label("<b>" + lang.lockDescLabel() + "</b>");
        descLabel.setAutoHeight();
        descLabel.setExtraSpace(5);
        final RichTextEditor textEditor = new RichTextEditor();
        textEditor.setHeight(200);
        textEditor.setWidth(472);
        textEditor.setOverflow(Overflow.HIDDEN);
        textEditor.setEdgeSize(3);
        textEditor.setExtraSpace(10);
        textEditor.setShowEdges(true);
        if (lockInfo.getLockDescription() != null) {
            textEditor.setValue(lockInfo.getLockDescription());
        }
        HLayout layout = new HLayout();
        Button lock = new Button();
        lock.setExtraSpace(8);
        lock.setTitle("".equals(ts.getLockInfo().getLockOwner()) ? lang.updateLock() : lang.lockItem());
        Button close = new Button();
        close.setTitle(lang.close());
        layout.addMember(lock);
        layout.addMember(close);

        lock.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                ts.getLockInfo().setLockDescription(textEditor.getValue().equals("<br>") ? null : textEditor
                        .getValue());
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
        addItem(descLabel);
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
                new LockDigitalObjectAction(ts.getUuid(), ts.getLockInfo().getLockDescription());
        final DispatchCallback<LockDigitalObjectResult> lockCallback =
                new DispatchCallback<LockDigitalObjectResult>() {

                    @Override
                    public void callback(LockDigitalObjectResult result) {
                        LockInfo lockInfo = result.getLockInfo();
                        if (lockInfo.getLockOwner() == null) {
                            SC.say(lang.objectLocked(), lang.objectLocked() + "<br>" + lang.lockNote());
                            ts.setLockInfo(new LockInfo("",
                                                        ts.getLockInfo().getLockDescription(),
                                                        new String[] {"0", "0", "0"}));

                        } else if ("".equals(lockInfo.getLockOwner())) {
                            SC.say(lang.lockUpdated(), lang.lockUpdated());
                            ts.setLockInfo(new LockInfo("",
                                                        ts.getLockInfo().getLockDescription(),
                                                        new String[] {"0", "0", "0"}));

                        } else {
                            EditorSC.objectIsLock(lang, lockInfo);
                            ts.setLockInfo(new LockInfo(lockInfo.getLockOwner(), lockInfo
                                    .getLockDescription(), lockInfo.getTimeToExpiration()));
                        }

                        if (ts.getLockInfo().getLockOwner() != null) {
                            ts.setBackgroundColor("".equals(ts.getLockInfo().getLockOwner().trim()) ? Constants.BG_COLOR_FOCUSED_LOCK_BY_USER
                                    : Constants.BG_COLOR_FOCUSED_LOCK);
                        } else {
                            ts.setBackgroundColor(Constants.BG_COLOR_FOCUSED);
                        }
                        mw.hide();
                    }

                    @Override
                    public void callbackError(final Throwable t) {
                        super.callbackError(t, new TryAgainCallbackError() {

                            @Override
                            public void theMethodForCalling() {
                                lockDigitalObject(ts, dispatcher);
                            }
                        });
                        mw.hide();
                    }
                };
        dispatcher.execute(lockAction, lockCallback);
    }
}
