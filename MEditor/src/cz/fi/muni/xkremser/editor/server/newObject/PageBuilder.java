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

package cz.fi.muni.xkremser.editor.server.newObject;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_CONTROLGROUP;
import cz.fi.muni.xkremser.editor.client.util.Constants.DATASTREAM_ID;
import cz.fi.muni.xkremser.editor.server.fedora.utils.FoxmlUtils;
import cz.fi.muni.xkremser.editor.shared.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.rpc.NewDigitalObject;

/**
 * @author Jiri Kremser
 * @version 31.10.2011
 */
public class PageBuilder
        extends FoxmlBuilder {

    /**
     * @param uuid
     * @param label
     */
    public PageBuilder(NewDigitalObject object) {
        super(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateMODSStream() {
        String pageLabel = getLabel();
        Element modsCollection = FoxmlUtils.createModsCollectionEl();
        Namespace modsNs = Namespaces.mods;
        Element mods = modsCollection.addElement(new QName("mods", modsNs));
        mods.addAttribute("version", "3.3");
        Element idUrn = mods.addElement(new QName("identifier", modsNs));
        idUrn.addAttribute("type", "urn");
        idUrn.addText(getUuid());

        Element idSici = mods.addElement(new QName("identifier", modsNs));
        idSici.addAttribute("type", "sici");

        Element typeOfResource = mods.addElement(new QName("typeOfResource", modsNs));
        typeOfResource.addText("text");
        Element part = mods.addElement(new QName("part", modsNs));
        String resolvedPageType = Constants.PAGE_TYPES.MAP.get(getPageType());
        part.addAttribute("type", resolvedPageType == null ? "NormalPage" : resolvedPageType);
        //pageNumber
        Element detail = part.addElement(new QName("detail", modsNs));
        detail.addAttribute("type", "pageNumber");
        Element number = detail.addElement(new QName("number", modsNs));
        number.addText(pageLabel);
        //page index
        detail = part.addElement(new QName("detail", modsNs));
        detail.addAttribute("type", "pageIndex");
        number = detail.addElement(new QName("number", modsNs));
        number.addText(getLabel());
        appendDatastream(DATASTREAM_CONTROLGROUP.X, DATASTREAM_ID.BIBLIO_MODS, modsCollection, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DigitalObjectModel getModel() {
        return DigitalObjectModel.PAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createOtherStreams() {
        appendDatastream(DATASTREAM_CONTROLGROUP.R, DATASTREAM_ID.IMG_FULL, null, "URL", getImageUrl()
                + "/big.jpg");

        appendDatastream(DATASTREAM_CONTROLGROUP.R, DATASTREAM_ID.IMG_THUMB, null, "URL", getImageUrl()
                + "/preview.jpg");

    }
}
