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

package cz.fi.muni.xkremser.editor.client.view.other;

import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.DragMoveEvent;
import com.smartgwt.client.widgets.events.DragMoveHandler;
import com.smartgwt.client.widgets.tile.TileGrid;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public class EditorDragMoveHandler
        implements DragMoveHandler {

    private final TileGrid tileGrid;
    private String moveTracker;
    private final String copySymbol = Canvas.imgHTML("icons/16/copy.png", 16, 16);

    public EditorDragMoveHandler(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    public void setMoveTracker(String moveTracker) {
        this.moveTracker = moveTracker;
    }

    @Override
    public void onDragMove(DragMoveEvent event) {
        if (event.isCtrlKeyDown()) {
            tileGrid.setDragDataAction(DragDataAction.COPY);
            EventHandler.setDragTracker(moveTracker + copySymbol);

        } else {
            tileGrid.setDragDataAction(DragDataAction.MOVE);
            EventHandler.setDragTracker(moveTracker);
        }

    }

}
