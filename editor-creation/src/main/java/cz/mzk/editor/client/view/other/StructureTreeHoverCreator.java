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

package cz.mzk.editor.client.view.other;

import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.HtmlCode;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Matous Jobanek
 * @version Apr 10, 2012
 */
public class StructureTreeHoverCreator {

    public static String getHover(LangConstants lang, ListGridRecord record) {
        StringBuffer sb = new StringBuffer();
        DigitalObjectModel model =
                DigitalObjectModel.parseString(record.getAttribute(Constants.ATTR_MODEL_ID));

        switch (model) {
            case PERIODICALVOLUME:
                sb.append(getPartNumberOrAltoHover(lang, record, false));
                sb.append(getDateOrIntPartHover(lang, record, false));
                sb.append(getNoteOrSubtitleHover(lang, record, false));

                return sb.toString();

            case PERIODICALITEM:
                sb.append(getLevelNameOrOcrHover(lang, record, false));
                sb.append(getNameOrTitleHover(lang, record, lang.editionName()));
                sb.append(getPartNumberOrAltoHover(lang, record, false));
                sb.append(getDateOrIntPartHover(lang, record, false));
                sb.append(getTypeHover(lang, record, false));
                sb.append(getNoteOrSubtitleHover(lang, record, false));

                return sb.toString();

            case INTERNALPART:
                sb.append(getLevelNameOrOcrHover(lang, record, false));
                sb.append(getNameOrTitleHover(lang, record, lang.intPartName()));
                sb.append(getNoteOrSubtitleHover(lang, record, true));
                sb.append(getDateOrIntPartHover(lang, record, true));
                sb.append(getPartNumberOrAltoHover(lang, record, false));
                sb.append(getTypeHover(lang, record, false));

                return sb.toString();

            case MONOGRAPHUNIT:
                sb.append(getLevelNameOrOcrHover(lang, record, false));
                sb.append(getNameOrTitleHover(lang, record, lang.supplementName()));
                sb.append(getDateOrIntPartHover(lang, record, false));
                sb.append(getNoteOrSubtitleHover(lang, record, false));
                sb.append(getPartNumberOrAltoHover(lang, record, false));

                return sb.toString();

            case PAGE:
                sb.append(getNameOrTitleHover(lang, record, lang.name()));
                sb.append(getTypeHover(lang, record, true));
                sb.append(getLevelNameOrOcrHover(lang, record, true));
                sb.append(getPartNumberOrAltoHover(lang, record, true));

                return sb.toString();

            default:
                return "";
        }
    }

    //        ATTR_PICTURE_OR_UUID        uuid      uuid        uuid        picture     uuid   
    //        ATTR_MODEL_ID               modelId   modelid     modelid     modelid     modelid 

    //                                    vol       item        int p       page        int p       mon un

    //        ATTR_NAME                             part name   title       name        title       part name
    //        ATTR_TYPE                             genre       genre       spec type   genre    
    //        ATTR_DATE_OR_INT_PART_NAME  date      date        part name               part name   date
    //        ATTR_NOTE_OR_INT_SUBTITLE   note      note        subtitle                subtitle    note
    //        ATTR_PART_NUMBER_OR_ALTO    part num  part num    part num    alto        part num    part num
    //        ATTR_ADITIONAL_INFO_OR_OCR            item/suppl  art/pic     ocr         pict/chapt  suppl

    private static String getLevelNameOrOcrHover(LangConstants lang, ListGridRecord record, boolean isPage) {
        String levelNameOrOcr = record.getAttribute(Constants.ATTR_ADITIONAL_INFO_OR_OCR);
        if (levelNameOrOcr != null && !"".equals(levelNameOrOcr)) {
            return hoverFactory(isPage ? "OCR" : lang.levelName(), levelNameOrOcr);
        }
        return "";
    }

    private static String getTypeHover(LangConstants lang, ListGridRecord record, boolean isPage) {
        String type = record.getAttribute(Constants.ATTR_TYPE);
        if (type != null && !"".equals(type)) {
            return hoverFactory(isPage ? lang.specialType() : lang.dcType(), type);
        }
        return "";
    }

    private static String getNameOrTitleHover(LangConstants lang, ListGridRecord record, String label) {
        String nameOrTitle = record.getAttribute(Constants.ATTR_NAME);
        if (nameOrTitle != null && !"".equals(nameOrTitle)) {
            return hoverFactory(label, nameOrTitle);
        }
        return "";
    }

    private static String getDateOrIntPartHover(LangConstants lang, ListGridRecord record, boolean getPartName) {
        String date = record.getAttribute(Constants.ATTR_DATE_OR_INT_PART_NAME);
        if (date != null && !"".equals(date)) {
            return hoverFactory(getPartName ? lang.intPartPartName() : lang.dateIssued(), date);
        }
        return "";
    }

    private static String getNoteOrSubtitleHover(LangConstants lang,
                                                 ListGridRecord record,
                                                 boolean getSubtitle) {
        String noteOrSubtitle = record.getAttribute(Constants.ATTR_NOTE_OR_INT_SUBTITLE);
        if (noteOrSubtitle != null && !"".equals(noteOrSubtitle)) {
            return hoverFactory(getSubtitle ? lang.subtitle() : lang.note(), noteOrSubtitle);

        }
        return "";
    }

    private static String getPartNumberOrAltoHover(LangConstants lang, ListGridRecord record, boolean isPage) {
        String partNumber = record.getAttribute(Constants.ATTR_PART_NUMBER_OR_ALTO);
        if (partNumber != null && !"".equals(partNumber)) {
            return partNumber = hoverFactory(isPage ? "ALTO" : lang.partNumber(), partNumber);
        }
        return "";
    }

    private static String hoverFactory(String attribut, String value) {
        return HtmlCode.bold(attribut) + ": " + value + "<br />";
    }
}