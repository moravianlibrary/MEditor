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

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
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

    public DownloadingWindow(LangConstants lang, final EditorTabSet ts) {
        setHeight(350);
        setWidth(420);
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

        HTMLFlow foxmlLabel = new HTMLFlow("<big><center>" + lang.downloadFoxml() + ":" + "</center></big>");
        HTMLFlow streamsLabel =
                new HTMLFlow("<big><center>" + lang.downloadStream() + ":" + "</center></big>");
        final HTMLFlow fedoraFoxmlFlow = new HTMLFlow();
        HTMLFlow fedoraDcFlow = new HTMLFlow();
        HTMLFlow fedoraModsFlow = new HTMLFlow();
        HTMLFlow fedoraRelsExtFlow = new HTMLFlow();
        fedoraFoxmlFlow
                .setContents("<form><center><button TYPE=\"button\" onClick=\"window.location.href=\'/"
                        + Constants.SERVLET_DOWNLOAD_FOXML_PREFIX + "/" + ts.getUuid() + "\'\">"
                        + lang.fullFoxml() + "</button></center></form>");

        fedoraDcFlow.setContents("<form><button TYPE=\"button\" onClick=\"window.location.href=\'/"
                + Constants.SERVLET_DOWNLOAD_DATASTREAMS_PREFIX + "/DC/" + ts.getUuid()
                + "\'\">DC datastream</button></form>");
        fedoraDcFlow.setExtraSpace(8);

        fedoraModsFlow.setContents("<form><button TYPE=\"button\" onClick=\"window.location.href=\'/"
                + Constants.SERVLET_DOWNLOAD_DATASTREAMS_PREFIX + "/BIBLIO_MODS/" + ts.getUuid()
                + "\'\">MODS datastream</button></form>");
        fedoraModsFlow.setExtraSpace(8);

        fedoraRelsExtFlow.setContents("<form><button TYPE=\"button\" onClick=\"window.location.href=\'/"
                + Constants.SERVLET_DOWNLOAD_DATASTREAMS_PREFIX + "/RELS-EXT/" + ts.getUuid()
                + "\'\">RELS-EXT datastream</button></form>");
        fedoraRelsExtFlow.setExtraSpace(8);

        final Button localFoxmlButton = new Button();
        localFoxmlButton.setTitle(lang.fullFoxml());
        localFoxmlButton.setHeight(24);
        localFoxmlButton.setWidth(110);
        localFoxmlButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                download(null);
            }
        });
        Button localDcButton = new Button();
        localDcButton.setTitle("DC datastream");
        localDcButton.setHeight(25);
        localDcButton.setWidth(130);
        localDcButton.setExtraSpace(8);
        localDcButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                download(DC);
            }
        });
        Button localModsButton = new Button();
        localModsButton.setTitle("MODS datastream");
        localModsButton.setHeight(25);
        localModsButton.setWidth(150);
        localModsButton.setExtraSpace(8);
        localModsButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                download(BIBLIO_MODS);
            }
        });
        Button localRelsExtButton = new Button();
        localRelsExtButton.setTitle("RELS-EXT datastream");
        localRelsExtButton.setHeight(25);
        localRelsExtButton.setWidth(160);
        localRelsExtButton.setExtraSpace(8);
        localRelsExtButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                download(RELS_EXT);
            }
        });

        Layout mainLayout = new VLayout(5);
        Layout titleLayout = new HLayout(2);
        titleLayout.addMember(new HTMLFlow("<h2><center>" + lang.masterCopy() + ":" + "</center></h2>"));
        titleLayout.addMember(new HTMLFlow("<h2><center>" + lang.workingCopy() + ":" + "</center></h2>"));
        titleLayout.setAutoHeight();
        mainLayout.addMember(titleLayout);

        mainLayout.addMember(foxmlLabel);

        Layout foxmlLayout = new HLayout(2);
        foxmlLayout.addMember(fedoraFoxmlFlow);
        foxmlLayout.addMember(localFoxmlButton);
        foxmlLayout.setMargin(12);
        foxmlLayout.setExtraSpace(10);
        foxmlLayout.setAutoHeight();
        mainLayout.addMember(foxmlLayout);

        mainLayout.addMember(streamsLabel);

        Layout streamLayout = new HLayout(2);
        Layout fedoraStreamsLayout = new VLayout(3);
        fedoraStreamsLayout.addMember(fedoraDcFlow);
        fedoraStreamsLayout.addMember(fedoraModsFlow);
        fedoraStreamsLayout.addMember(fedoraRelsExtFlow);

        Layout localStreamsLayout = new VLayout(3);
        localStreamsLayout.addMember(localDcButton);
        localStreamsLayout.addMember(localModsButton);
        localStreamsLayout.addMember(localRelsExtButton);

        streamLayout.addMember(fedoraStreamsLayout);
        streamLayout.addMember(localStreamsLayout);
        streamLayout.setMargin(12);
        streamLayout.setAutoHeight();
        mainLayout.addMember(streamLayout);
        mainLayout.setAutoHeight();

        setEdgeOffset(10);
        addItem(mainLayout);
        init();
    }

    protected abstract void download(String stream);

    protected abstract void init();

}
