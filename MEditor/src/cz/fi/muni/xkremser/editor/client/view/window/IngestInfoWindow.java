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

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.view.other.HtmlCode;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class IngestInfoWindow
        extends UniversalWindow {

    private static IngestInfoWindow ingestInfoWindow = null;

    public static void setInstanceOf(List<String> pid,
                                     List<String> username,
                                     List<String> time,
                                     LangConstants lang) {
        if (isInstanceVisible()) {
            closeInstantiatedWindow();
        }

        ingestInfoWindow = new IngestInfoWindow(pid, username, time, lang);
    }

    public static boolean isInstanceVisible() {
        return (ingestInfoWindow != null && ingestInfoWindow.isCreated());
    }

    public static void closeInstantiatedWindow() {
        ingestInfoWindow.hide();
        ingestInfoWindow = null;
    }

    /**
     * @param height
     * @param width
     * @param title
     */

    private IngestInfoWindow(final List<String> pid,
                             List<String> username,
                             List<String> time,
                             final LangConstants lang) {

        super(pid.size() * 140, 350, lang.ingestInfo());
        Layout mainLayout = new VLayout();

        for (int i = 0; i < pid.size(); i++) {
            HTMLFlow titleFlow = new HTMLFlow(HtmlCode.title(lang.ingestNumber() + ": " + (i + 1), 4));
            mainLayout.addMember(titleFlow);

            final String pidString = pid.get(i);
            Layout pidLayout = new HLayout(2);
            HTMLFlow pidFlow = new HTMLFlow(HtmlCode.bold("PID: ") + pidString);
            pidFlow.setWidth(280);

            //            ImgButton editButton = new ImgButton();
            //            editButton.setSrc(Constants.PATH_IMG_EDIT);
            //            editButton.setWidth(16);
            //            editButton.setHeight(16);
            //            editButton.setShowRollOver(false);
            //            editButton.setShowDown(false);
            //            editButton.addClickHandler(new ClickHandler() {
            //
            //                @Override
            //                public void onClick(ClickEvent event) {
            //                    Menu menu = new Menu();
            //                    menu.setShowShadow(true);
            //                    menu.setShadowDepth(10);
            //                    MenuItem newItem = new MenuItem(lang.menuEdit());
            //                    newItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
            //
            //                        @Override
            //                        public void onClick(MenuItemClickEvent event) {
            //                            eventBus.fireEvent(new OpenDigitalObjectEvent(pidString));
            //                        }
            //                    });
            //                    menu.addItem(newItem);
            //                    setContextMenu(menu);
            //                }
            //            });

            pidLayout.addMember(pidFlow);
            //            pidLayout.addMember(editButton);
            pidLayout.setExtraSpace(3);
            pidLayout.setAutoHeight();
            mainLayout.addMember(pidLayout);

            HTMLFlow userNameFlow = new HTMLFlow(HtmlCode.bold(lang.username()) + ": " + username.get(i));
            userNameFlow.setExtraSpace(3);
            mainLayout.addMember(userNameFlow);

            HTMLFlow timeFlow = new HTMLFlow(HtmlCode.bold(lang.date()) + ": " + time.get(i));
            timeFlow.setExtraSpace(12);
            mainLayout.addMember(timeFlow);
        }
        setEdgeOffset(15);
        addItem(mainLayout);
        centerInPage();
        show();
        focus();
    }
}
