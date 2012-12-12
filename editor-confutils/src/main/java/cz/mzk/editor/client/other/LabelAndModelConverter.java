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

package cz.mzk.editor.client.other;

import java.util.HashMap;
import java.util.Map;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version Apr 4, 2012
 */
public class LabelAndModelConverter {

    private final static Map<String, DigitalObjectModel> modelsFromLabels =
            new HashMap<String, DigitalObjectModel>();

    private final static Map<String, String> labelsFromModels = new HashMap<String, String>();

    public static void setLabelAndModelConverter(LangConstants lang) {
        getLabelFromModel().put(DigitalObjectModel.INTERNALPART.getValue(), lang.internalpart());
        getModelFromLabel().put(lang.internalpart(), DigitalObjectModel.INTERNALPART);
        getLabelFromModel().put(DigitalObjectModel.MONOGRAPH.getValue(), lang.monograph());
        getModelFromLabel().put(lang.monograph(), DigitalObjectModel.MONOGRAPH);
        getLabelFromModel().put(DigitalObjectModel.MONOGRAPHUNIT.getValue(), lang.monographunit());
        getModelFromLabel().put(lang.monographunit(), DigitalObjectModel.MONOGRAPHUNIT);
        getLabelFromModel().put(DigitalObjectModel.PAGE.getValue(), lang.page());
        getModelFromLabel().put(lang.page(), DigitalObjectModel.PAGE);
        getLabelFromModel().put(DigitalObjectModel.PERIODICAL.getValue(), lang.periodical());
        getModelFromLabel().put(lang.periodical(), DigitalObjectModel.PERIODICAL);
        getLabelFromModel().put(DigitalObjectModel.PERIODICALITEM.getValue(), lang.periodicalitem());
        getModelFromLabel().put(lang.periodicalitem(), DigitalObjectModel.PERIODICALITEM);
        getLabelFromModel().put(DigitalObjectModel.PERIODICALVOLUME.getValue(), lang.periodicalvolume());
        getModelFromLabel().put(lang.periodicalvolume(), DigitalObjectModel.PERIODICALVOLUME);
        getLabelFromModel().put(DigitalObjectModel.MAP.getValue(), lang.map());
        getModelFromLabel().put(lang.map(), DigitalObjectModel.MAP);
        getLabelFromModel().put(DigitalObjectModel.MANUSCRIPT.getValue(), lang.manuscript());
        getModelFromLabel().put(lang.manuscript(), DigitalObjectModel.MANUSCRIPT);
        getLabelFromModel().put(DigitalObjectModel.ARCHIVE.getValue(), lang.archive());
        getModelFromLabel().put(lang.archive(), DigitalObjectModel.ARCHIVE);
        getLabelFromModel().put(DigitalObjectModel.GRAPHIC.getValue(), lang.graphic());
        getModelFromLabel().put(lang.graphic(), DigitalObjectModel.GRAPHIC);
        getLabelFromModel().put(DigitalObjectModel.SUPPLEMENT.getValue(), lang.supplement());
        getModelFromLabel().put(lang.supplement(), DigitalObjectModel.SUPPLEMENT);
    }

    public static Map<String, DigitalObjectModel> getModelFromLabel() {
        return modelsFromLabels;
    }

    public static Map<String, String> getLabelFromModel() {
        return labelsFromModels;
    }
}
