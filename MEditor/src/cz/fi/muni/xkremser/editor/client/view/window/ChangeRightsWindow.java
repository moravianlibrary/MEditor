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
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.dispatcher.TryAgainCallbackError;

import cz.fi.muni.xkremser.editor.shared.rpc.action.ChangeRightsAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.ChangeRightsResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class ChangeRightsWindow
        extends UniversalWindow {

    private static final String POLICY = "policy:";

    private static final String PUBLIC = "public";

    private static final String PRIVATE = "private";

    private final LangConstants lang;

    private final DispatchAsync dispatcher;

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */

    public ChangeRightsWindow(final String parentUuid,
                              final EventBus eventBus,
                              String parentRight,
                              LangConstants lang,
                              DispatchAsync dispatcher) {
        super(200, 350, lang.changeRight(), eventBus, 20);
        this.lang = lang;
        this.dispatcher = dispatcher;

        final RadioGroupItem rightsChooser = new RadioGroupItem("rights", lang.chooseRight());
        rightsChooser.setValueMap(PUBLIC, PRIVATE);
        rightsChooser.setDefaultValue(parentRight.equals(POLICY + PUBLIC) ? PUBLIC : PRIVATE);

        final CheckboxItem forChildren = new CheckboxItem("forChildren", lang.changeRightsOffsprings());
        forChildren.setDefaultValue(true);

        Button closeButton = new Button(lang.close());
        Button executeButton = new Button(lang.execute());
        executeButton.setExtraSpace(5);

        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        executeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                execute(parentUuid, rightsChooser.getValueAsString(), forChildren.getValueAsBoolean());
            }

        });

        DynamicForm df = new DynamicForm();
        df.setWidth100();
        df.setExtraSpace(30);
        df.setItems(rightsChooser, forChildren);
        addItem(df);

        Layout buttonLayout = new HLayout();
        buttonLayout.setWidth("65%");
        buttonLayout.setLayoutAlign(Alignment.RIGHT);
        buttonLayout.addMember(executeButton);
        buttonLayout.addMember(closeButton);
        addItem(buttonLayout);

        centerInPage();
        show();
        focus();
        df.setTop(5);
    }

    private void execute(final String parentUuid, final String right, final Boolean forChildren) {
        final ModalWindow mw = new ModalWindow(ChangeRightsWindow.this);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);
        ChangeRightsAction rightsAction = new ChangeRightsAction(parentUuid, POLICY + right, forChildren);
        DispatchCallback<ChangeRightsResult> rightsCallback = new DispatchCallback<ChangeRightsResult>() {

            @Override
            public void callback(ChangeRightsResult result) {
                EditorSC.operationSuccessful(lang, "");
                mw.hide();
                hide();
            }

            @Override
            public void callbackError(Throwable t) {
                mw.hide();
                super.callbackError(t, new TryAgainCallbackError() {

                    @Override
                    public void theMethodForCalling() {
                        execute(parentUuid, right, forChildren);
                    }
                });
            }
        };
        dispatcher.execute(rightsAction, rightsCallback);
    }
}
