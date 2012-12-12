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

package cz.mzk.editor.client.view.window;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.view.other.NewDigitalObjectItemManager;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version May 3, 2012
 */
public abstract class NewObjectBasicInfoWindow
        extends UniversalWindow {

    private NewDigitalObjectItemManager managerLayout = null;

    public NewObjectBasicInfoWindow(final ListGridRecord record,
                                    LangConstants lang,
                                    EventBus eventBus,
                                    boolean isPeriodical) {
        super(120, 440, lang.menuEdit()
                + " "
                + LabelAndModelConverter.getLabelFromModel()
                        .get(record.getAttributeAsString(Constants.ATTR_MODEL_ID)) + ": "
                + record.getAttributeAsString(Constants.ATTR_NAME), eventBus, 10);

        final HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setWidth100();
        buttonsLayout.setAlign(Alignment.RIGHT);
        buttonsLayout.setMargin(10);

        Button cancelButton = new Button(lang.cancel());
        Button okButton = new Button("OK");

        cancelButton.setExtraSpace(5);
        buttonsLayout.addMember(cancelButton);
        buttonsLayout.addMember(okButton);

        final ObjectBasicInfoLayout objectBasicInfoLayout =
                new ObjectBasicInfoLayout(record, lang, eventBus, isPeriodical, 13) {

                    @Override
                    protected void setWindowHeight(int height) {
                        NewObjectBasicInfoWindow.this.setHeight(height);
                    }

                    @Override
                    protected HLayout getButtonsLayout() {
                        return buttonsLayout;
                    }

                };

        cancelButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String message = objectBasicInfoLayout.verify();
                if (message == null) {
                    NewObjectBasicInfoWindow.this.doSaveAction(record);
                    NewObjectBasicInfoWindow.this.hide();
                } else {
                    SC.warn(message);
                }
            }
        });

        managerLayout = objectBasicInfoLayout.getManagerLayout();
        addItem(objectBasicInfoLayout);

        centerInPage();
        show();
        focus();
    }

    protected void setChangedRecord(ListGridRecord record) {
        String aditionalInfoOrOcr = "";
        DigitalObjectModel model =
                DigitalObjectModel.parseString(record.getAttribute(Constants.ATTR_MODEL_ID));

        switch (model) {
            case PERIODICALVOLUME:
                record.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, managerLayout.getDateIssued());
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getDateIssued());
                record.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, managerLayout.getNote());
                record.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, managerLayout.getPartNumber());
                break;

            case PERIODICALITEM:
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getNameOrTitle());
                record.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, managerLayout.getDateIssued());
                record.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, managerLayout.getNote());
                record.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, managerLayout.getPartNumber());
                aditionalInfoOrOcr = managerLayout.getLevelName();
                record.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, aditionalInfoOrOcr);
                record.setAttribute(Constants.ATTR_TYPE, managerLayout.getType(model, aditionalInfoOrOcr));
                break;

            case INTERNALPART:
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getNameOrTitle());
                record.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, managerLayout.getPartName());
                record.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, managerLayout.getSubtitle());
                record.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, managerLayout.getPartNumber());
                aditionalInfoOrOcr = managerLayout.getLevelName();
                record.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, aditionalInfoOrOcr);
                record.setAttribute(Constants.ATTR_TYPE, managerLayout.getType(model, aditionalInfoOrOcr));
                break;

            case MONOGRAPHUNIT:
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getNameOrTitle());
                record.setAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME, managerLayout.getDateIssued());
                record.setAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE, managerLayout.getNote());
                record.setAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO, managerLayout.getPartNumber());
                record.setAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR, managerLayout.getLevelName());
                break;

            case PAGE:
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getNameOrTitle());
                record.setAttribute(Constants.ATTR_TYPE, managerLayout.getType(model, null));
                break;

            default:
                record.setAttribute(Constants.ATTR_NAME, managerLayout.getNameOrTitle());
                break;
        }
    }

    protected abstract void doSaveAction(ListGridRecord record);
}
