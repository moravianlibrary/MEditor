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

import com.gwtplatform.mvp.client.UiHandlers;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tile.TileGrid;

import cz.mzk.editor.client.view.other.EditorTabSet;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.rpc.DigitalObjectDetail;
import cz.mzk.editor.shared.rpc.StoredItem;

/**
 * @author Jiri Kremser
 * @version 11.3.2012
 */
public interface ModifyUiHandlers
        extends UiHandlers {

    void onAddDigitalObject(final TileGrid tileGrid, final Menu menu);

    void onAddDigitalObject(final String uuid, final ImgButton closeButton);

    void onSaveDigitalObject(final DigitalObjectDetail digitalObject, boolean versionable, boolean reindex);

    void onHandleWorkingCopyDigObj(final DigitalObjectDetail digitalObject);

    void getDescription(final String uuid, final TabSet tabSet, final String tabId);

    void putDescription(final String uuid, final String description, boolean common);

    void onRefresh(final String uuid);

    void getStream(final String uuid, final DigitalObjectModel model, TabSet ts);

    void close(final String uuid);

    void onChangeFocusedTabSet(final String focusedUuid);

    void openAnotherObject(final String uuid, StoredItem storedItem);

    void lockDigitalObject(final EditorTabSet ts);

    void unlockDigitalObject(final EditorTabSet ts);

    void storeFoxmlFile(DigitalObjectDetail detail, EditorTabSet ts);

    void getLockDigitalObjectInformation(final EditorTabSet ts, final boolean calledDuringPublishing);

    void removeDigitalObject(String uuid);

    void changeRights(String uuid, String oldRight);

    void sortWithChildren(String uuid);

    void addOcr(String uuid);
}
