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

package cz.fi.muni.xkremser.editor.server.modelHandler;

import org.w3c.dom.Node;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;

import cz.fi.muni.xkremser.editor.server.fedora.FedoraAccess;
import cz.fi.muni.xkremser.editor.server.fedora.utils.BiblioModsUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.DCUtils;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;

import cz.fi.muni.xkremser.editor.shared.rpc.DublinCore;
import cz.fi.muni.xkremser.editor.shared.rpc.Foxml;

/**
 * @author Matous Jobanek
 * @version $Id$
 */

public class DigitalObjectHandler {

    /**
     * Handle dc
     * 
     * @param uuid
     * @param dcDocument
     * @param onlyTitleAndUuid
     * @return
     */
    protected DublinCore handleDc(String uuid, org.w3c.dom.Element dcElement, boolean onlyTitleAndUuid) {
        DublinCore dc = null;
        if (onlyTitleAndUuid) {
            dc = new DublinCore();
            dc.addTitle(DCUtils.titleFromDC(dcElement));
            dc.addIdentifier(uuid);
        } else {
            dc = DCUtils.getDC(dcElement);
        }
        return dc;
    }

    /**
     * Handle mods.
     * 
     * @param modsNode
     *        the mods document
     * @return the mods collection client
     */
    protected ModsCollectionClient handleMods(Node modsNode) {
        ModsCollection mods = BiblioModsUtils.getModsCollection(modsNode);
        ModsCollectionClient modsClient = BiblioModsUtils.toModsClient(mods);
        return modsClient;
    }

    /**
     * @param uuid
     *        the uuid
     * @param fedoraAccess
     *        the fedora Access
     * @return The Foxml
     */

    protected Foxml handleFoxml(String uuid, FedoraAccess fedoraAccess) {
        return FoxmlUtils.handleFoxml(uuid, fedoraAccess);
    }

    /**
     * Handle ocr.
     * 
     * @param uuid
     *        the uuid
     * @param fedoraAccess
     *        the fedora Access
     * @return the string
     */
    protected String handleOCR(String uuid, FedoraAccess fedoraAccess) {
        return fedoraAccess.getOcr(uuid);
    }

}
