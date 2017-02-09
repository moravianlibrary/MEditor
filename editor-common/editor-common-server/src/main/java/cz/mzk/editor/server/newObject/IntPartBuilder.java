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

package cz.mzk.editor.server.newObject;

import cz.mzk.editor.client.util.Constants.DATASTREAM_CONTROLGROUP;
import cz.mzk.editor.client.util.Constants.DATASTREAM_ID;
import cz.mzk.editor.client.util.Constants.INTERNAL_PART_LEVEL_NAMES;
import cz.mzk.editor.server.fedora.utils.FoxmlUtils;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jiri Kremser
 * @version 31.10.2011
 */
@Component
public class IntPartBuilder
        extends FoxmlBuilder {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateMODSStream() {
        Element modsCollection = FoxmlUtils.createModsCollectionEl();
        Namespace modsNs = Namespaces.mods;
        Element mods = modsCollection.addElement(new QName("mods", modsNs));
        mods.addAttribute("version", "3.3");
        mods.addAttribute("ID", getAditionalInfo());
        Element idUrn = mods.addElement(new QName("identifier", modsNs));
        idUrn.addAttribute("type", "urn");
        idUrn.addText(getUuid());

        Element titleInfo = mods.addElement(new QName("titleInfo", modsNs));
        Element title = titleInfo.addElement(new QName("title", modsNs));
        title.addText(getName());

        if (isNotNullOrEmpty(getNoteOrIntSubtitle())) {
            Element subtitle = titleInfo.addElement(new QName("subtitle", modsNs));
            subtitle.addText(getNoteOrIntSubtitle());
        }
        if (isNotNullOrEmpty(getPartNumber())) {
            Element partNumber = titleInfo.addElement(new QName("partNumber", modsNs));
            partNumber.addText(getPartNumber());
        }
        if (isNotNullOrEmpty(getDateOrIntPartName())) {
            Element partName = titleInfo.addElement(new QName("partName", modsNs));
            partName.addText(getDateOrIntPartName());
        }

        Element genre = mods.addElement(new QName("genre", modsNs));
        genre.addAttribute("type", getType());
        String levelName = getAditionalInfo().substring(0, getAditionalInfo().indexOf("_", 6));
        genre.addText(INTERNAL_PART_LEVEL_NAMES.MAP.get(levelName));

        Element part = mods.addElement(new QName("part", modsNs));
        Element extent = part.addElement(new QName("extent", modsNs));

        List<RelsExtRelation> children = getChildren();
        Element start = extent.addElement(new QName("start", modsNs));
        start.addText(children.get(0).getTargetName());
        Element end = extent.addElement(new QName("end", modsNs));
        end.addText(children.get(children.size() - 1).getTargetName());
        Element total = extent.addElement(new QName("total", modsNs));
        total.addText(String.valueOf(children.size()));

        addRootLanguage(mods);
        addIdentifierUuid(mods, getUuid());
        addRootRecordInfo(mods.addElement(new QName("recordInfo", modsNs)));
        addRootTopic(mods);
        addLocation(mods.addElement(new QName("location", modsNs)));

        appendDatastream(DATASTREAM_CONTROLGROUP.X, DATASTREAM_ID.BIBLIO_MODS, modsCollection, null, null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DigitalObjectModel getModel() {
        return DigitalObjectModel.INTERNALPART;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createOtherStreams() {
        // TODO Auto-generated method stub

    }

}
