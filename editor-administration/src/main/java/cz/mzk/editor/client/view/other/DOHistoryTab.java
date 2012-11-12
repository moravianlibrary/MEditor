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

package cz.mzk.editor.client.view.other;

import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.HtmlCode;

/**
 * @author Matous Jobanek
 * @version Nov 9, 2012
 */
public class DOHistoryTab
        extends HistoryTab {

    private TextItem uuidField;
    private IButton okButton;

    /**
     * @param eventBus
     * @param lang
     * @param dispatcher
     */
    public DOHistoryTab(EventBus eventBus, LangConstants lang, DispatchAsync dispatcher) {
        super(eventBus,
              lang,
              dispatcher,
              HtmlCode.title(lang.historyMenu() + " " + lang.ofDigObj(), 2),
              null,
              null,
              lang.historyMenu() + " " + lang.ofDigObj());
        setIcon("pieces/16/cube_blue.png");

    }

    private void setUuidSelect() {
        DataSource dataSource = new DataSource();
        dataSource.setID("regularExpression");

        RegExpValidator regExpValidator = new RegExpValidator();
        regExpValidator
                .setExpression("^.*:([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

        uuidField = new TextItem();
        uuidField.setTitle("PID");
        uuidField.setWidth(255);
        uuidField.setHint(HtmlCode.nobr(getLang().withoutPrefix()));
        uuidField.setValidators(regExpValidator);

        uuidField.addChangedHandler(new com.smartgwt.client.widgets.form.fields.events.ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                String text = (String) event.getValue();
                if (text != null && !"".equals(text)) {
                    okButton.setDisabled(false);
                } else {
                    okButton.setDisabled(true);
                }
            }
        });

        DynamicForm form = new DynamicForm();
        form.setWidth(300);
        form.setFields(uuidField);

        okButton = new IButton();
        okButton.setTitle("OK");
        okButton.setDisabled(true);
        okButton.setAutoShowParent(false);

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (uuidField.validate()) {
                    getHistoryItems().removeAllData();
                    getHistoryDays().getHistory(null, uuidField.getValueAsString());
                }
            }
        });

        HLayout hLayout = new HLayout();
        hLayout.setMembersMargin(10);
        hLayout.addMember(form);
        hLayout.addMember(okButton);
        hLayout.setHeight(10);

        getMainLayout().addMember(hLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setSelection() {
        setUuidSelect();
    }

}
