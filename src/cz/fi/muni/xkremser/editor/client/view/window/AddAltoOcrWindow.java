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

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.CheckBox;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

import cz.fi.muni.xkremser.editor.shared.rpc.action.FindAltoOcrFilesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindAltoOcrFilesResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class AddAltoOcrWindow
        extends UniversalWindow {

    private final LangConstants lang;
    private final ListGrid foundAltoFilesGrid;
    private final ListGrid foundOcrFilesGrid;

    public AddAltoOcrWindow(final ListGridRecord listGridRecord,
                            final LangConstants lang,
                            final DispatchAsync dispatcher,
                            EventBus eventBus) {
        super(630, 600, "ALTO", eventBus, 50);
        this.lang = lang;

        HLayout mainLayout = new HLayout(2);
        final VLayout altoLayout = new VLayout(3);
        VLayout ocrLayout = new VLayout(3);

        final Label altoLabel = new Label();
        altoLabel.setContents(HtmlCode.title(lang.choose() + " ALTO", 3));
        altoLabel.setAutoHeight();
        altoLabel.setExtraSpace(8);

        final Label ocrLabel = new Label();
        ocrLabel.setContents(HtmlCode.title(lang.choose() + " OCR", 3));
        ocrLabel.setAutoHeight();
        ocrLabel.setExtraSpace(8);

        foundAltoFilesGrid = new ListGrid();
        foundAltoFilesGrid.setWidth(270);
        foundAltoFilesGrid.setHeight(400);
        foundAltoFilesGrid.setShowSortArrow(SortArrow.CORNER);
        foundAltoFilesGrid.setShowAllRecords(true);
        foundAltoFilesGrid.setExtraSpace(10);
        foundAltoFilesGrid.setSelectionType(SelectionStyle.SINGLE);

        foundOcrFilesGrid = new ListGrid();
        foundOcrFilesGrid.setWidth(270);
        foundOcrFilesGrid.setHeight(400);
        foundOcrFilesGrid.setShowSortArrow(SortArrow.CORNER);
        foundOcrFilesGrid.setShowAllRecords(true);
        foundOcrFilesGrid.setExtraSpace(10);
        foundOcrFilesGrid.setSelectionType(SelectionStyle.SINGLE);

        ModalWindow mw = new ModalWindow(mainLayout);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        final CheckBox noAnyALTO = new CheckBox(lang.noAny() + " ALTO");
        noAnyALTO.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {

            @Override
            public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
                if (noAnyALTO.getValue()) {
                    foundAltoFilesGrid.disable();
                    foundAltoFilesGrid.setOpacity(60);
                    altoLabel.setOpacity(60);
                } else {
                    foundAltoFilesGrid.enable();
                    foundAltoFilesGrid.setOpacity(100);
                    altoLabel.setOpacity(100);
                }
            }
        });

        final CheckBox noAnyOCR = new CheckBox(lang.noAny() + " OCR");
        noAnyOCR.addClickHandler(new com.google.gwt.event.dom.client.ClickHandler() {

            @Override
            public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
                if (noAnyOCR.getValue()) {
                    foundAltoFilesGrid.disable();
                    foundOcrFilesGrid.disable();
                    foundOcrFilesGrid.setOpacity(60);
                    noAnyALTO.setEnabled(false);
                    altoLayout.setOpacity(60);
                    ocrLabel.setOpacity(60);
                } else {
                    foundAltoFilesGrid.enable();
                    foundOcrFilesGrid.enable();
                    foundOcrFilesGrid.setOpacity(100);
                    noAnyALTO.setEnabled(true);
                    altoLayout.setOpacity(100);
                    ocrLabel.setOpacity(100);
                }
            }
        });

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setAlign(Alignment.RIGHT);

        buttonsLayout.setWidth100();
        Button okButton = new Button("Ok");

        okButton.setExtraSpace(8);
        buttonsLayout.addMember(okButton);
        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!noAnyOCR.getValue()) {
                    if (!noAnyALTO.getValue()) {
                        doSaveAction(listGridRecord,
                                     foundAltoFilesGrid.getSelectedRecord(),
                                     foundOcrFilesGrid.getSelectedRecord());
                    } else {
                        doSaveAction(listGridRecord, null, foundOcrFilesGrid.getSelectedRecord());
                    }
                } else {
                    doSaveAction(listGridRecord, null, null);
                }
            }
        });

        Button closeButton = new Button(lang.close());
        closeButton.setExtraSpace(10);
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        buttonsLayout.addMember(closeButton);

        altoLayout.addMember(altoLabel);
        altoLayout.addMember(foundAltoFilesGrid);
        altoLayout.addMember(noAnyALTO);

        ocrLayout.addMember(ocrLabel);
        ocrLayout.addMember(foundOcrFilesGrid);
        ocrLayout.addMember(noAnyOCR);

        mainLayout.addMember(altoLayout);
        mainLayout.addMember(ocrLayout);
        addItem(mainLayout);

        addItem(buttonsLayout);

        setEdgeOffset(20);
        findFiles(listGridRecord, dispatcher);
        centerInPage();
        show();
        focus();
        mw.destroy();
    }

    private void findFiles(final ListGridRecord listGridRecord, final DispatchAsync dispatcher) {

        ListGridField altoFileField = new ListGridField(Constants.ATTR_NAME, lang.name());
        foundAltoFilesGrid.setFields(altoFileField);

        ListGridField txtFileField = new ListGridField(Constants.ATTR_NAME, lang.name());
        foundOcrFilesGrid.setFields(txtFileField);

        FindAltoOcrFilesAction altoFilesAction =
                new FindAltoOcrFilesAction(listGridRecord.getAttributeAsString(Constants.ATTR_PICTURE));
        DispatchCallback<FindAltoOcrFilesResult> altoFilesCallback =
                new DispatchCallback<FindAltoOcrFilesResult>() {

                    @Override
                    public void callback(FindAltoOcrFilesResult result) {
                        if (result.getAltoFileNames() != null) {
                            setAltoData(listGridRecord, result.getAltoFileNames());
                            setOcrData(listGridRecord, result.getOcrFileNames());
                        } else {
                            EditorSC.operationFailed(lang, "");
                        }
                    }

                    @Override
                    public void callbackError(final Throwable cause) {
                        super.callbackError(cause);
                    }
                };
        dispatcher.execute(altoFilesAction, altoFilesCallback);
    }

    private void setAltoData(ListGridRecord listGridRecord, List<String> altoList) {
        String oldAltoPath = listGridRecord.getAttributeAsString(Constants.ATTR_ALTO_PATH);
        Record toSelectRecord = null;

        Record[] altoFiles = new Record[altoList.size()];
        int i = 0;
        for (String path : altoList) {
            Record altoFile = new Record();
            altoFile.setAttribute(Constants.ATTR_NAME, path.substring(path.lastIndexOf("/") + 1));
            altoFile.setAttribute(Constants.ATTR_ALTO_PATH, path);
            altoFiles[i++] = altoFile;

            if (oldAltoPath != null && !"".equals(oldAltoPath) && oldAltoPath.equals(path)) {
                toSelectRecord = altoFile;
            }

        }
        foundAltoFilesGrid.setData(altoFiles);
        foundAltoFilesGrid.sort(Constants.ATTR_NAME, SortDirection.ASCENDING);
        foundAltoFilesGrid.selectRecord(0);
        foundAltoFilesGrid.scrollToRow(0);
        foundAltoFilesGrid.deselectAllRecords();

        if (toSelectRecord != null) {
            foundAltoFilesGrid.selectRecord(toSelectRecord);
        }
    }

    private void setOcrData(ListGridRecord listGridRecord, List<String> ocrList) {
        String oldOcrPath = listGridRecord.getAttributeAsString(Constants.ATTR_OCR_PATH);
        Record toSelectRecord = null;

        Record[] ocrFiles = new Record[ocrList.size()];
        int i = 0;
        for (String path : ocrList) {
            Record ocrFile = new Record();
            ocrFile.setAttribute(Constants.ATTR_NAME, path.substring(path.lastIndexOf("/") + 1));
            ocrFile.setAttribute(Constants.ATTR_OCR_PATH, path);
            ocrFiles[i++] = ocrFile;

            if (oldOcrPath != null && !"".equals(oldOcrPath) && oldOcrPath.equals(path)) {
                toSelectRecord = ocrFile;
            }
        }
        foundOcrFilesGrid.setData(ocrFiles);
        foundOcrFilesGrid.sort(Constants.ATTR_NAME, SortDirection.ASCENDING);
        foundOcrFilesGrid.selectRecord(0);
        foundOcrFilesGrid.scrollToRow(0);
        foundOcrFilesGrid.deselectAllRecords();

        if (toSelectRecord != null) {
            foundOcrFilesGrid.selectRecord(toSelectRecord);
        }
    }

    protected abstract void doSaveAction(ListGridRecord listGridRecord,
                                         ListGridRecord altoRecord,
                                         ListGridRecord ocrRecord);
}
