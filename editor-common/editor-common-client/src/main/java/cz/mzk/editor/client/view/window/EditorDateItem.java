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

package cz.mzk.editor.client.view.window;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DateItemSelectorFormat;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.FormItemValueParser;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;

import cz.mzk.editor.client.LangConstants;
import cz.mzk.editor.client.other.LabelAndModelConverter;
import cz.mzk.editor.client.util.Constants.DATE_RIGHT_FORMATS;
import cz.mzk.editor.shared.domain.DigitalObjectModel;
import cz.mzk.editor.shared.domain.DigitalObjectModel.TopLevelObjectModel;

/**
 * @author Matous Jobanek
 * @version May 7, 2012
 */
public class EditorDateItem
        extends DateTimeItem {

    private String value = "";
    private boolean wasParsed = false;
    private LangConstants lang;

    public EditorDateItem() {
        super();
        setFormating();
    }

    public EditorDateItem(String name) {
        super(name);
        setFormating();
    }

    public EditorDateItem(String name, String title) {
        super(name, title);
        setFormating();
    }

    public EditorDateItem(String name, LangConstants lang) {
        super(name);
        this.lang = lang;
        setFormating();
    }

    public EditorDateItem(String name, String title, LangConstants lang) {
        super(name, title);
        this.lang = lang;
        setFormating();
    }

    /**
     * 
     */
    private void setFormating() {
        setUseTextField(true);
        setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
        setInputFormat("dd.MM.yyyy");
        setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);

        setEditorValueFormatter(new FormItemValueFormatter() {

            @Override
            public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
                String toReturn = null;

                if (!wasParsed) {
                    try {
                        toReturn = DateTimeFormat.getFormat("dd.MM.yyyy").format((Date) value);
                    } catch (ClassCastException cce) {
                    }
                    EditorDateItem.this.value = toReturn != null ? toReturn : value.toString();
                } else if (value != null) {
                    toReturn = value.toString();
                } else {
                    return "";
                }

                wasParsed = false;
                return EditorDateItem.this.value;
            }
        });

        setEditorValueParser(new FormItemValueParser() {

            @Override
            public Object parseValue(String value, DynamicForm form, FormItem item) {

                if (isVisible()) {
                    EditorDateItem.this.value = value;

                    if (value != null && !"".equals(value)) {

                        wasParsed = true;

                        if (!verifyAllFormats(value)) {
                            SC.warn((lang != null) ? lang.wrongDate()
                                    : "You have entered a wrong date format");
                            selectValue();
                            return null;
                        }

                        DateTimeFormat format = getDateTimeFormat(value);
                        return (format != null) ? format.parse(value) : value;

                    }
                    EditorDateItem.this.value = "";
                    return "";
                } else {
                    return value;
                }
            }
        });

        setHoverOpacity(75);
        setHoverWidth(330);
        setHoverStyle("interactImageHover");
    }

    private boolean verifyAllFormats(String valueToVerify) {
        for (DATE_RIGHT_FORMATS format : DATE_RIGHT_FORMATS.values()) {
            if (valueToVerify.matches(format.getRegex())) {

                String year = null;

                if (format == DATE_RIGHT_FORMATS.DATE_RRRR) {
                    year = valueToVerify;

                } else if (format == DATE_RIGHT_FORMATS.DATE_RRRR_DASH) {
                    year = valueToVerify.substring(0, valueToVerify.length() - 1);

                } else if (format == DATE_RIGHT_FORMATS.DATE_RRRR_RRRR) {
                    return verifyYearExtent(valueToVerify);

                } else if (format == DATE_RIGHT_FORMATS.DATE_RRRR_RRRR_RRRR_RRRR) {
                    String[] splited = valueToVerify.split(",");
                    return verifyYearExtent(splited[0]) && verifyYearExtent(splited[1]);

                } else {
                    year = valueToVerify.substring(valueToVerify.lastIndexOf(".") + 1);
                }

                if (year != null) {
                    return (new Date().getTime() > DateTimeFormat.getFormat("yyyy").parse(year).getTime());
                }
            }
        }
        return false;
    }

    private boolean verifyYearExtent(String valueToVerify) {
        String[] splitedYears = valueToVerify.split("-");
        if (new Date().getTime() < DateTimeFormat.getFormat("yyyy").parse(splitedYears[1]).getTime()
                || DateTimeFormat.getFormat("yyyy").parse(splitedYears[0]).getTime() > DateTimeFormat
                        .getFormat("yyyy").parse(splitedYears[1]).getTime()) return false;
        return true;
    }

    private DateTimeFormat getDateTimeFormat(String valueToVerify) {
        if (valueToVerify.matches(DATE_RIGHT_FORMATS.DATE_DDMMRRRR.getRegex())) {
            return DateTimeFormat.getFormat("dd.MM.yyyy");

        }
        return null;
    }

    public String getEditorDate() {
        if (getDisplayValue() != null) return getDisplayValue();
        return "";
    }

    public String verify(LangConstants lang, DigitalObjectModel model) {
        if (model == DigitalObjectModel.PERIODICALVOLUME) {
            if (!"".equals(getEditorDate())
                    && !(getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_RRRR.getRegex()) || getEditorDate()
                            .matches(DATE_RIGHT_FORMATS.DATE_RRRR_RRRR.getRegex())))
                return getDateFormatHint(lang, model);

        } else if (model == DigitalObjectModel.PERIODICALITEM || model == DigitalObjectModel.MONOGRAPHUNIT) {
            if (!"".equals(getEditorDate())
                    && !(getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_DDMMRRRR.getRegex())
                            || getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_MMRRRR.getRegex())
                            || getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_RRRR.getRegex())
                            || getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_DD_DDMMRRRR.getRegex()) || getEditorDate()
                            .matches(DATE_RIGHT_FORMATS.DATE_MM_MMRRRR.getRegex())))
                return getDateFormatHint(lang, model);

        } else if (model.getTopLevelType() == TopLevelObjectModel.PERIODICAL) {
            if (!"".equals(getEditorDate())
                    && !(getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_RRRR.getRegex())
                            || getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_RRRR_DASH.getRegex())
                            || getEditorDate().matches(DATE_RIGHT_FORMATS.DATE_RRRR_RRRR.getRegex()) || getEditorDate()
                            .matches(DATE_RIGHT_FORMATS.DATE_RRRR_RRRR_RRRR_RRRR.getRegex())))
                return getDateFormatHint(lang, model);
        }
        return null;
    }

    public String getDateFormatHint(LangConstants lang, DigitalObjectModel model) {
        if (model == DigitalObjectModel.PERIODICALVOLUME) {
            return lang.dcType() + " " + LabelAndModelConverter.getLabelFromModel().get(model.getValue())
                    + " " + lang.dateInFormat() + ": " + "<br>RRRR " + lang.or() + "<br>RRRR-RRRR";

        } else if (model == DigitalObjectModel.PERIODICALITEM || model == DigitalObjectModel.MONOGRAPHUNIT) {
            return lang.dcType() + " " + LabelAndModelConverter.getLabelFromModel().get(model.getValue())
                    + " " + lang.dateInFormat() + ": <br>" + "DD.MM.RRRR " + lang.or() + "<br>MM.RRRR "
                    + lang.or() + "<br>RRRR " + lang.or() + "<br>DD.-DD.MM.RRR " + lang.or()
                    + "<br>MM.-MM.RRRR";

        } else if (model.getTopLevelType() == TopLevelObjectModel.PERIODICAL) {
            return lang.dcType() + " " + LabelAndModelConverter.getLabelFromModel().get(model.getValue())
                    + " " + lang.dateInFormat() + ": " + "<br>RRRR " + lang.or() + "<br>RRRR- " + lang.or()
                    + "<br>RRRR-RRRR " + lang.or() + "<br>RRRR-RRRR,RRRR-RRRR";
        }
        return "";
    }

    /**
     * @param lang
     *        the lang to set
     */
    public void setLang(LangConstants lang) {
        this.lang = lang;
    }

}
