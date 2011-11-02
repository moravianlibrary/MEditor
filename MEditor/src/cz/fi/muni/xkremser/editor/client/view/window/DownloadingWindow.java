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

import com.google.gwt.http.client.URL;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;

import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.BIBLIO_MODS;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.DC;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID.RELS_EXT;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class DownloadingWindow
        extends Window {

    private final Layout mainLayout;
    private HTMLFlow foxmlLocalFlow;

    private final LangConstants lang;

    private final ModalWindow modalWindow;

    public DownloadingWindow(LangConstants lang, final EditorTabSet ts) {
        this.lang = lang;
        setHeight(350);
        setWidth(450);
        setCanDragResize(true);
        setShowEdges(true);
        setTitle(lang.downloadItem());
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        setEdgeOffset(10);

        mainLayout = new VLayout(5);
        mainLayout.setHeight100();
        addItem(mainLayout);

        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClientEvent event) {
                destroy();
            }
        });

        modalWindow = new ModalWindow(mainLayout);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);
        init();
    }

    protected abstract void init();

    public void addButtons(final String[] stringsWithXml, final String uuid) {

        HTMLFlow foxmlLabel = new HTMLFlow("<big><center>" + lang.downloadFoxml() + ":" + "</center></big>");
        HTMLFlow streamsLabel =
                new HTMLFlow("<big><center>" + lang.downloadStream() + ":" + "</center></big>");

        Layout titleLayout = new HLayout(2);
        titleLayout.addMember(new HTMLFlow("<h2><center>" + lang.masterCopy() + ":" + "</center></h2>"));
        titleLayout.addMember(new HTMLFlow("<h2><center>" + lang.workingCopy() + ":" + "</center></h2>"));
        titleLayout.setAutoHeight();
        mainLayout.addMember(titleLayout);

        mainLayout.addMember(foxmlLabel);

        Layout foxmlLayout = new HLayout(2);
        foxmlLayout.addMember(htmlFlowFormLinkFactory(null, uuid, null));
        
        Layout foxmlLocalLayout = new VLayout(2);        
        foxmlLocalFlow = htmlFlowFormLinkFactory(null, uuid, stringsWithXml[1]);
        final Layout foxmlLocalFlowLayout = new VLayout(1);
        foxmlLocalFlowLayout.addMember(foxmlLocalFlow);
        foxmlLocalLayout.addMember(foxmlLocalFlowLayout);

        DynamicForm form = new DynamicForm();
        final CheckboxItem versionable = new CheckboxItem("versionable", lang.versionable());
        versionable.setDefaultValue(false);
        form.setFields(versionable);
        form.setWidth(200);
        foxmlLocalLayout.addMember(form);
        foxmlLayout.addMember(foxmlLocalLayout);

        foxmlLayout.setMargin(7);
        foxmlLayout.setExtraSpace(10);
        foxmlLayout.setAutoHeight();
        mainLayout.addMember(foxmlLayout);

        mainLayout.addMember(streamsLabel);

        Layout streamLayout = new HLayout(2);
        Layout fedoraStreamsLayout = new VLayout(3);
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(DC.getValue(), uuid, null));
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(BIBLIO_MODS.getValue(), uuid, null));
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT.getValue(), uuid, null));

        Layout localStreamsLayout = new VLayout(3);
        localStreamsLayout.addMember(htmlFlowFormLinkFactory(DC.getValue(), uuid, stringsWithXml[1]));
        localStreamsLayout
                .addMember(htmlFlowFormLinkFactory(BIBLIO_MODS.getValue(), uuid, stringsWithXml[2]));
        localStreamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT.getValue(), uuid, stringsWithXml[3]));

        streamLayout.addMember(fedoraStreamsLayout);
        streamLayout.addMember(localStreamsLayout);
        streamLayout.setMargin(12);
        streamLayout.setAutoHeight();
        mainLayout.addMember(streamLayout);
        modalWindow.hide();

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
    }

    private HTMLFlow htmlFlowFormLinkFactory(String stream, String uuid, String content) {

        StringBuffer sb = new StringBuffer();
        sb.append("<center>");
        sb.append("<form method=\"");
        sb.append(content == null ? "get" : "post");
        sb.append("\" action=\"/");
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
        sb.append("</center>");
        HTMLFlow newFormFlow = new HTMLFlow(sb.toString());
        newFormFlow.setExtraSpace(8);

        return newFormFlow;
    }
}
