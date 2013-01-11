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

package cz.mzk.editor.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.mzk.editor.client.presenter.EditorRootPresenter;

/**
 * @author Matous Jobanek
 * @version Jan 8, 2013
 */
public class EditorRootView
        extends ViewImpl
        implements EditorRootPresenter.MyView {

    private final VLayout mainLayout;

    /**
     * 
     */
    public EditorRootView() {
        this.mainLayout = new VLayout();
        mainLayout.setWidth100();
        mainLayout.setHeight100();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInSlot(Object slot, Widget content) {
        super.setInSlot(slot, content);
        mainLayout.removeMembers(mainLayout.getMembers());
        if (content != null) {
            mainLayout.addMember(content);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Widget asWidget() {
        return mainLayout;
    }

}
