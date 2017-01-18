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

package cz.mzk.editor.client.view.other;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.HTMLFlow;

/**
 * @author Matous Jobanek
 * @version Oct 29, 2012
 */
public abstract class LangSelectionHTMLFlow
        extends HTMLFlow {

    /**
     * 
     */
    public LangSelectionHTMLFlow() {
        setWidth(63);
        setHeight(16);
        final boolean en =
                LocaleInfo.getCurrentLocale().getLocaleName() != null
                        && LocaleInfo.getCurrentLocale().getLocaleName().startsWith("en");
        setStyleName(en ? "langSelectionEN" : "langSelectionCZ");
        setCursor(Cursor.HAND);
        addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
                afterChangeAction(en);
            }

        });
    }

    protected abstract void afterChangeAction(boolean isEn);

}
