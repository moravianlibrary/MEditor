/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
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

package cz.mzk.editor.shared.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

import static cz.mzk.editor.shared.domain.DigitalObjectModel.ARTICLE;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.INTERNALPART;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.MANUSCRIPT;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.MAP;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.MONOGRAPH;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.MONOGRAPHUNIT;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.PAGE;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.PERIODICAL;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.PERIODICALITEM;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.PERIODICALVOLUME;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.ARCHIVE;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.SOUNDRECORDING;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.TRACK;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.SOUND_UNIT;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.GRAPHIC;
import static cz.mzk.editor.shared.domain.DigitalObjectModel.SHEETMUSIC;
import static cz.mzk.editor.shared.domain.FedoraRelationship.containsTrack;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasIntCompPart;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasItem;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasPage;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasSoundUnit;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasTrack;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasUnit;
import static cz.mzk.editor.shared.domain.FedoraRelationship.hasVolume;
import static cz.mzk.editor.shared.domain.FedoraRelationship.isOnPage;

// TODO: Auto-generated Javadoc
/**
 * The Enum NamedGraphModel.
 */
public class NamedGraphModel
        implements IsSerializable {

    private static final Map<List<DigitalObjectModel>, FedoraRelationship> relations =
            new HashMap<List<DigitalObjectModel>, FedoraRelationship>();
    private static final Map<String, DigitalObjectModel> childrenByRelation =
            new HashMap<String, DigitalObjectModel>();
    private static Map<DigitalObjectModel, List<DigitalObjectModel>> children =
            new HashMap<DigitalObjectModel, List<DigitalObjectModel>>();

    static {

        /** The MONOGRAPH. */
        putRelationship(MONOGRAPH, hasPage, PAGE);
        putRelationship(MONOGRAPH, hasIntCompPart, INTERNALPART);
        putRelationship(MONOGRAPH, hasUnit, MONOGRAPHUNIT);
        //        putRelationship(MONOGRAPH, hasUnit, SUPPLEMENT);

        putRelationship(MAP, hasPage, PAGE);
        putRelationship(MAP, hasIntCompPart, INTERNALPART);
        putRelationship(MAP, hasUnit, MONOGRAPHUNIT);
        //        putRelationship(MAP, hasUnit, SUPPLEMENT);

        putRelationship(MANUSCRIPT, hasPage, PAGE);
        putRelationship(MANUSCRIPT, hasIntCompPart, INTERNALPART);
        putRelationship(MANUSCRIPT, hasUnit, MONOGRAPHUNIT);
        //        putRelationship(MANUSCRIPT, hasUnit, SUPPLEMENT);

        putRelationship(ARCHIVE, hasPage, PAGE);
        putRelationship(ARCHIVE, hasIntCompPart, INTERNALPART);
        putRelationship(ARCHIVE, hasUnit, MONOGRAPHUNIT);
        //        putRelationship(ARCHIVE, hasUnit, SUPPLEMENT);

        putRelationship(GRAPHIC, hasPage, PAGE);
        putRelationship(GRAPHIC, hasIntCompPart, INTERNALPART);
        putRelationship(GRAPHIC, hasUnit, MONOGRAPHUNIT);
        //        putRelationship(ARCHIVE, hasUnit, SUPPLEMENT);

        putRelationship(MONOGRAPHUNIT, hasPage, PAGE);
        putRelationship(MONOGRAPHUNIT, hasIntCompPart, INTERNALPART);
        putRelationship(INTERNALPART, isOnPage, PAGE);
        putRelationship(ARTICLE, isOnPage, PAGE);

        putRelationship(SHEETMUSIC, hasPage, PAGE);
        putRelationship(SHEETMUSIC, hasIntCompPart, INTERNALPART);
        putRelationship(SHEETMUSIC, hasUnit, MONOGRAPHUNIT);

        /** The PERIODICAL. */
        putRelationship(PERIODICAL, hasPage, PAGE);
        putRelationship(PERIODICAL, hasVolume, PERIODICALVOLUME);
        putRelationship(PERIODICALVOLUME, hasPage, PAGE);
        putRelationship(PERIODICALVOLUME, hasIntCompPart, INTERNALPART);
        putRelationship(PERIODICALVOLUME, hasItem, PERIODICALITEM);
        //        putRelationship(PERIODICALVOLUME, hasItem, SUPPLEMENT);
        putRelationship(PERIODICALITEM, hasPage, PAGE);
        putRelationship(PERIODICALITEM, hasIntCompPart, INTERNALPART);

        /** The Sound recording */
        putRelationship(SOUNDRECORDING, hasTrack, TRACK);
        putRelationship(SOUNDRECORDING, hasPage, PAGE);
        putRelationship(SOUNDRECORDING, hasSoundUnit, SOUND_UNIT);
        putRelationship(SOUND_UNIT, containsTrack, TRACK);
    }

    private static void putRelationship(DigitalObjectModel source,
                                        FedoraRelationship relationship,
                                        DigitalObjectModel target) {
        NamedGraphModel.relations.put(Arrays.asList(target, source), relationship);
        NamedGraphModel.childrenByRelation.put(relationship.getStringRepresentation(),
                                               relationship.getTargetModel());
        putChild(source, target);
    }

    public static FedoraRelationship getRelationship(DigitalObjectModel source, DigitalObjectModel target) {
        return relations.get(Arrays.asList(target, source));
    }

    private static void putChild(DigitalObjectModel parent, DigitalObjectModel child) {
        if (children.get(parent) == null) {
            children.put(parent, new ArrayList<DigitalObjectModel>());
        }
        children.get(parent).add(child);
    }

    public static List<DigitalObjectModel> getChildren(DigitalObjectModel parent) {
        return children.get(parent);
    }

    public static DigitalObjectModel getChildByRelation(String relation) {
        return childrenByRelation.get(relation);
    }

    public static boolean isTopLvlModel(DigitalObjectModel model) {
        Collection<List<DigitalObjectModel>> sets = children.values();
        for (List<DigitalObjectModel> list : sets) {
            if (list.contains(model)) {
                return false;
            }
        }
        return true;
    }

}