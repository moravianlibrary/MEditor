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
import com.google.gwt.user.client.Window.Location;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.util.Constants;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class PersistentUrlWindow
        extends UniversalWindow {

    /**
     * @param height
     * @param width
     * @param title
     * @param eventBus
     * @param milisToWait
     */

    public PersistentUrlWindow(String uuid,
                               EditorClientConfiguration config,
                               EventBus eventBus,
                               LangConstants lang) {
        super(110, 800, lang.persistentUrl(), eventBus, 25);

        TextItem editorUrlItem = new TextItem();

        StringBuffer sbEditor = new StringBuffer();
        sbEditor.append(Location.getProtocol()).append("//");
        sbEditor.append(Location.getHost());
        sbEditor.append(Location.getPath());
        sbEditor.append(Location.getQueryString());
        sbEditor.append('?');
        sbEditor.append(NameTokens.MODIFY);
        sbEditor.append('&');
        sbEditor.append(Constants.URL_PARAM_UUID);
        sbEditor.append('=');
        sbEditor.append(uuid);
        editorUrlItem.setDefaultValue(sbEditor.toString());
        editorUrlItem.setWidth(660);
        editorUrlItem.setTitle("<a href=\"" + sbEditor.toString() + "\" target=\"_blank\">Editor URL</a> ");

        TextItem krameriusUrlItem = new TextItem();

        StringBuffer sbKram = new StringBuffer();
        sbKram.append(config.getKrameriusHost());
        sbKram.append("/handle/");
        sbKram.append(uuid);
        krameriusUrlItem.setDefaultValue(sbKram.toString());
        krameriusUrlItem.setWidth(660);
        krameriusUrlItem.setTitle("<a href=\"" + sbKram.toString()
                + "\" target=\"_blank\">Kramerius URL</a> ");

        DynamicForm editorUrlForm = new DynamicForm();
        editorUrlForm.setItems(editorUrlItem);
        editorUrlForm.setExtraSpace(5);
        DynamicForm kramUrlForm = new DynamicForm();
        kramUrlForm.setItems(krameriusUrlItem);

        addItem(editorUrlForm);
        addItem(kramUrlForm);
        centerInPage();
        show();
        focus();
    }

}
