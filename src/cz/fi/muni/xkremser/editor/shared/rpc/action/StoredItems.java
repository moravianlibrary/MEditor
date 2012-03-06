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

package cz.fi.muni.xkremser.editor.shared.rpc.action;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import com.gwtplatform.dispatch.shared.UnsecuredActionImpl;

import cz.fi.muni.xkremser.editor.client.util.Constants;

import cz.fi.muni.xkremser.editor.shared.rpc.DigitalObjectDetail;
import cz.fi.muni.xkremser.editor.shared.rpc.StoredItem;

/**
 * @author Matous Jobanek
 * @version $Id$
 */
@GenDispatch(isSecure = false)
@SuppressWarnings("unused")
public class StoredItems
        extends UnsecuredActionImpl<StoredItemsResult> {

    /**
     * The Digital Object Detail of the object being stored;
     * <code>detail == null</code> when the user asks for the list of the stored
     * objects
     */
    @In(1)
    private DigitalObjectDetail detail;

    /**
     * The info about the digital object is going to be stored;
     * <code>storedItem == null </code>when the user asks for the list of the
     * stored objects
     */
    @In(2)
    private StoredItem storedItem;

    /**
     * The action to do
     */
    @In(3)
    private Constants.VERB verb;

    /** The List of the items stored by user */
    @Out(1)
    private List<StoredItem> storedItems;
}
