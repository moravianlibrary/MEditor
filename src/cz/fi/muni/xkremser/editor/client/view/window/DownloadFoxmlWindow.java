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
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.BIBLIO_MODS;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.DC;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.RELS_EXT;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DownloadFoxmlWindow
        extends UniversalWindow {

    private final Layout mainLayout;
    private VLayout buttonsLayout = new VLayout();

    private final LangConstants lang;

    private ModalWindow modalWindow;

    private HTMLFlow foxmlLocalFlow;

    private String[] stringsWithXml;

    public DownloadFoxmlWindow(final LangConstants lang, final EditorTabSet ts, EventBus eventBus) {
        super(360, 350, lang.downloadItem(), eventBus, 5);
        this.lang = lang;

        mainLayout = new VLayout(5);
        mainLayout.setHeight100();
        addItem(mainLayout);

        modalWindow = new ModalWindow(mainLayout);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        final RadioGroupItem selectVer = new RadioGroupItem();
        selectVer.setTitle(lang.chooseVersion());
        selectVer.setValueMap(lang.masterCopy(), lang.workingCopy());
        selectVer.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                addButtons(stringsWithXml,
                           ts.getUuid(),
                           selectVer.getValueAsString().equals(lang.masterCopy()));
            }
        });
        DynamicForm comboForm = new DynamicForm();
        comboForm.setItems(selectVer);
        comboForm.setExtraSpace(10);
        mainLayout.addMember(comboForm);
        centerInPage();
        show();
        focus();
    }

    public void setStringsWithXml(String[] stringsWithXml) {
        this.stringsWithXml = stringsWithXml;
        modalWindow.hide();
    }

    public void addButtons(final String[] stringsWithXml, final String uuid, boolean originalVersion) {

        if (mainLayout.hasMember(buttonsLayout)) {
            mainLayout.removeMember(buttonsLayout);
        }
        buttonsLayout = new VLayout();
        buttonsLayout.setMembersMargin(3);
        buttonsLayout.setPadding(10);

        HTMLFlow foxmlLabel = new HTMLFlow(HtmlCode.bold(lang.downloadFoxml() + ":"));
        HTMLFlow streamsLabel = new HTMLFlow(HtmlCode.bold(lang.downloadStream() + ":"));
        modalWindow = new ModalWindow(buttonsLayout);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);

        buttonsLayout.addMember(foxmlLabel);

        final Layout foxmlLayout;
        if (originalVersion) {
            foxmlLayout = new VLayout(1);
            foxmlLayout.addMember(htmlFlowFormLinkFactory(null, uuid, null));
        } else {
            foxmlLayout = new VLayout(2);
            final Layout foxmlLocalFlowLayout = new VLayout(1);
            foxmlLocalFlow = htmlFlowFormLinkFactory(null, uuid, stringsWithXml[1]);
            DynamicForm form = new DynamicForm();
            final CheckboxItem versionable = new CheckboxItem("versionable", lang.versionable());
            versionable.setDefaultValue(false);
            versionable.addChangedHandler(new ChangedHandler() {

                @Override
                public void onChanged(ChangedEvent event) {
                    foxmlLocalFlowLayout.removeMember(foxmlLocalFlow);
                    foxmlLocalFlow =
                            htmlFlowFormLinkFactory(null,
                                                    uuid,
                                                    stringsWithXml[versionable.getValueAsBoolean() ? 0 : 1]);
                    foxmlLocalFlowLayout.addMember(foxmlLocalFlow);
                }
            });
            form.setFields(versionable);
            form.setWidth(200);
            foxmlLocalFlowLayout.addMember(foxmlLocalFlow);
            foxmlLayout.addMember(foxmlLocalFlowLayout);
            foxmlLayout.addMember(form);
        }
        foxmlLayout.setAutoHeight();
        foxmlLayout.setExtraSpace(10);
        foxmlLayout.setEdgeOffset(5);
        foxmlLayout.setPadding(5);
        buttonsLayout.addMember(foxmlLayout);

        buttonsLayout.addMember(streamsLabel);
        final Layout streamsLayout = new VLayout(3);
        if (originalVersion) {
            streamsLayout.addMember(htmlFlowFormLinkFactory(DC.getValue(), uuid, null));
            streamsLayout.addMember(htmlFlowFormLinkFactory(BIBLIO_MODS.getValue(), uuid, null));
            streamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT.getValue(), uuid, null));
        } else {
            streamsLayout.addMember(htmlFlowFormLinkFactory(DC.getValue(), uuid, stringsWithXml[2]));
            streamsLayout.addMember(htmlFlowFormLinkFactory(BIBLIO_MODS.getValue(), uuid, stringsWithXml[3]));
            streamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT.getValue(), uuid, stringsWithXml[4]));
        }
        streamsLayout.setPadding(5);
        buttonsLayout.addMember(streamsLayout);

        mainLayout.addMember(buttonsLayout);
        modalWindow.hide();
    }

    private HTMLFlow htmlFlowFormLinkFactory(String stream, String uuid, String content) {

        StringBuffer sb = new StringBuffer();
        //        sb.append("<center>");
        sb.append("<form method=\"");
        sb.append(content == null ? "get" : "post");
        sb.append("\" action=\"");
        sb.append(Location.getPath());
        sb.append(stream == null ? Constants.SERVLET_DOWNLOAD_FOXML_PREFIX
                : Constants.SERVLET_DOWNLOAD_DATASTREAMS_PREFIX);
        sb.append("\">");

        if (content != null) {
            sb.append("<input type=\"hidden\" name=\"");
            sb.append(Constants.PARAM_CONTENT);
            sb.append("\" value=\"");
            sb.append(URL.encode(content));
            sb.append("\"/>");
        }

        if (stream != null) {
            sb.append("<input type=\"hidden\" name=\"");
            sb.append(Constants.PARAM_DATASTREAM);
            sb.append("\" value=\"");
            sb.append(stream);
            sb.append("\"/>");
        }

        sb.append("<input type=\"hidden\" name=\"");
        sb.append(Constants.PARAM_UUID);
        sb.append("\" value=\"");
        sb.append(uuid);
        sb.append("\"/>");

        sb.append("<input type=\"submit\" value=\"");
        if (stream == null) {
            sb.append(lang.fullFoxml());
        } else if (DC.getValue().equals(stream)) {
            sb.append("DC datastream");
        } else if (BIBLIO_MODS.getValue().equals(stream)) {
            sb.append("MODS datastream");
        } else if (RELS_EXT.getValue().equals(stream)) {
            sb.append("RELS-EXT datastream");
        }
        sb.append("\" />");
        sb.append("</form>");
        //        sb.append("</center>");
        HTMLFlow newFormFlow = new HTMLFlow(sb.toString());
        newFormFlow.setExtraSpace(8);

        return newFormFlow;
    }
}
