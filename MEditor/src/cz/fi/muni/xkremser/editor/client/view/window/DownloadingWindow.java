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
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.other.EditorTabSet;

import static cz.fi.muni.xkremser.editor.client.util.Constants.BIBLIO_MODS;
import static cz.fi.muni.xkremser.editor.client.util.Constants.DC;
import static cz.fi.muni.xkremser.editor.client.util.Constants.RELS_EXT;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public abstract class DownloadingWindow
        extends Window {

    private final Layout mainLayout = new VLayout(5);

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
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClientEvent event) {
                destroy();
            }
        });

        mainLayout.setHeight100();
        setEdgeOffset(10);
        addItem(mainLayout);

        modalWindow = new ModalWindow(mainLayout);
        modalWindow.setLoadingIcon("loadingAnimation.gif");
        modalWindow.show(true);
        init();
    }

    protected abstract void init();

    public void addButtons(String[] stringsWithXml, String uuid) {

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
        foxmlLayout.addMember(htmlFlowFormLinkFactory(null, uuid, stringsWithXml[0]));
        foxmlLayout.setMargin(7);
        foxmlLayout.setExtraSpace(10);
        foxmlLayout.setAutoHeight();
        mainLayout.addMember(foxmlLayout);

        mainLayout.addMember(streamsLabel);

        Layout streamLayout = new HLayout(2);
        Layout fedoraStreamsLayout = new VLayout(3);
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(DC, uuid, null));
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(BIBLIO_MODS, uuid, null));
        fedoraStreamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT, uuid, null));

        Layout localStreamsLayout = new VLayout(3);
        localStreamsLayout.addMember(htmlFlowFormLinkFactory(DC, uuid, stringsWithXml[1]));
        localStreamsLayout.addMember(htmlFlowFormLinkFactory(BIBLIO_MODS, uuid, stringsWithXml[2]));
        localStreamsLayout.addMember(htmlFlowFormLinkFactory(RELS_EXT, uuid, stringsWithXml[3]));

        streamLayout.addMember(fedoraStreamsLayout);
        streamLayout.addMember(localStreamsLayout);
        streamLayout.setMargin(12);
        streamLayout.setAutoHeight();
        mainLayout.addMember(streamLayout);
        modalWindow.hide();
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
        } else if (DC.equals(stream)) {
            sb.append("DC datastream");
        } else if (BIBLIO_MODS.equals(stream)) {
            sb.append("MODS datastream");
        } else if (RELS_EXT.equals(stream)) {
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
