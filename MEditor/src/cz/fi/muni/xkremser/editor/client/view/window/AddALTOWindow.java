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

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

import cz.fi.muni.xkremser.editor.shared.rpc.action.FindALTOFilesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.FindALTOFilesResult;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class AddALTOWindow
        extends UniversalWindow {

    private final LangConstants lang;

    //    private static AddALTOWindow addAltoWindow = null;
    //
    //    public static void setInstanceOf(final LangConstants lang, DispatchAsync dispatcher) {
    //        if (isInstanceVisible()) {
    //            closeInstantiatedWindow();
    //        }
    //        addAltoWindow = new AddALTOWindow(lang, dispatcher);
    //    }
    //
    //    public static boolean isInstanceVisible() {
    //        return (addAltoWindow != null && addAltoWindow.isCreated());
    //    }
    //
    //    public static void closeInstantiatedWindow() {
    //        addAltoWindow.hide();
    //        addAltoWindow = null;
    //    }

    public AddALTOWindow(final ListGridRecord listGridRecord,
                         final LangConstants lang,
                         final DispatchAsync dispatcher) {
        super(600, 380, lang.addALTO());
        this.lang = lang;

        final ListGrid foundAltoFiles;
        foundAltoFiles = new ListGrid();
        Label fileNameLabel = new Label();
        fileNameLabel.setContents(HtmlCode.title(lang.chooseALTO(), 3));
        fileNameLabel.setAutoHeight();
        fileNameLabel.setExtraSpace(8);

        foundAltoFiles.setWidth(300);
        foundAltoFiles.setHeight(450);
        foundAltoFiles.setShowSortArrow(SortArrow.CORNER);
        foundAltoFiles.setShowAllRecords(true);
        foundAltoFiles.setExtraSpace(20);
        foundAltoFiles.setSelectionType(SelectionStyle.SINGLE);

        ModalWindow mw = new ModalWindow(foundAltoFiles);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        HLayout buttonsLayout = new HLayout(2);
        buttonsLayout.setAlign(Alignment.RIGHT);

        buttonsLayout.setWidth100();
        Button okButton = new Button("OK");

        okButton.setExtraSpace(8);
        buttonsLayout.addMember(okButton);
        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                listGridRecord.setAttribute(Constants.ATTR_ALTO_PATH, foundAltoFiles.getSelectedRecord()
                        .getAttributeAsString(Constants.ATTR_ALTO_PATH));
                hide();
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

        addItem(fileNameLabel);
        addItem(foundAltoFiles);
        addItem(buttonsLayout);

        setEdgeOffset(20);
        setData(listGridRecord.getAttributeAsString(Constants.ATTR_PICTURE), foundAltoFiles, dispatcher);
        centerInPage();
        show();
        focus();
        buttonsLayout.setLeft(280);
        mw.destroy();
    }

    private void setData(String path, final ListGrid foundAltoFiles, final DispatchAsync dispatcher) {
        ListGridField fileField = new ListGridField(Constants.ATTR_NAME, lang.name());
        //        fileField.setWidth(120);
        foundAltoFiles.setFields(fileField);

        FindALTOFilesAction altoFilesAction = new FindALTOFilesAction(path);
        DispatchCallback<FindALTOFilesResult> altoFilesCallback =
                new DispatchCallback<FindALTOFilesResult>() {

                    @Override
                    public void callback(FindALTOFilesResult result) {
                        if (result.getAltoFileNames() != null) {

                            Record[] altoFiles = new Record[result.getAltoFileNames().size()];
                            int i = 0;
                            for (String path : result.getAltoFileNames()) {
                                Record altoFile = new Record();
                                altoFile.setAttribute(Constants.ATTR_NAME,
                                                      path.substring(path.lastIndexOf("/") + 1));
                                altoFile.setAttribute(Constants.ATTR_ALTO_PATH, path);
                                altoFiles[i++] = altoFile;

                            }
                            foundAltoFiles.setData(altoFiles);
                            foundAltoFiles.sort(Constants.ATTR_NAME, SortDirection.ASCENDING);
                            foundAltoFiles.selectRecord(0);
                            foundAltoFiles.scrollToRow(0);
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
}
