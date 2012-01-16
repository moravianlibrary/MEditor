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

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DeleteInfoWindow
        extends UniversalWindow {

    private final LangConstants lang;

    private static DeleteInfoWindow deleteInfoWindow = null;

    public static void setInstanceOf(List<String> removedUuid, LangConstants lang) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }
        deleteInfoWindow = new DeleteInfoWindow(removedUuid, lang);
    }

    public static boolean isInstanceVisible() {
        return (deleteInfoWindow != null && deleteInfoWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        deleteInfoWindow.hide();
        deleteInfoWindow = null;
    }

    /**
     * @param height
     * @param width
     * @param title
     */

    private DeleteInfoWindow(List<String> removedUuid, LangConstants lang) {
        super(170 + (removedUuid.size() * 12), 450, "Delete info");
        this.lang = lang;

        Layout mainLayout = new VLayout();
        mainLayout.setWidth100();
        mainLayout.setHeight100();

        mainLayout.setDefaultLayoutAlign(Alignment.CENTER);

        StringBuffer sb = new StringBuffer("<center>" + HtmlCode.title(lang.removedUuids() + ":", 3));
        for (String uuid : removedUuid) {
            sb.append("<br>").append(uuid);
        }
        sb.append("</center>");
        HTMLFlow htmlFlow = new HTMLFlow(sb.toString());
        htmlFlow.setExtraSpace(25);
        mainLayout.addMember(htmlFlow);

        IButton closeButton = new IButton("close");
        closeButton.setTitle(lang.close());
        mainLayout.addMember(closeButton);

        addItem(mainLayout);

        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                DeleteInfoWindow.this.hide();
            }
        });
        closeButton.setExtraSpace(10);

        int newHeight = 170 + (removedUuid.size() * 12);

        centerInPage();
        show();
        focus();

        setHeight100();
        if (newHeight < getHeight()) setHeight(newHeight);
    }

}
