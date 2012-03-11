/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
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

package cz.mzk.editor.client.uihandlers;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

import cz.mzk.editor.client.view.other.InputQueueTree;
import cz.mzk.editor.shared.rpc.RecentlyModifiedItem;

/**
 * @author Jiri Kremser
 * @version 11.3.2012
 */
public interface DigitalObjectMenuUiHandlers
        extends UiHandlers {

    /**
     * On refresh.
     */
    void onRefresh();

    void refreshRecentlyModified();

    /**
     * On show input queue.
     */
    void onShowInputQueue(InputQueueTree tree);

    /**
     * On add digital object.
     * 
     * @param item
     *        the item
     * @param related
     *        the related
     */
    void onAddDigitalObject(final RecentlyModifiedItem item, final List<? extends List<String>> related);

    /**
     * Reveal modified item.
     * 
     * @param uuid
     *        the uuid
     */
    void revealItem(String uuid);

}
