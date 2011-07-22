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

package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.metadata.AbstractHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AccessConditionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AudienceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ClassificationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.CopyInformationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.DateHolder;
import cz.fi.muni.xkremser.editor.client.metadata.DetailHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ExtensionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ExtentHolder;
import cz.fi.muni.xkremser.editor.client.metadata.GenreHolder;
import cz.fi.muni.xkremser.editor.client.metadata.IdentifierHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LanguageHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LocationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.metadata.NameHolder;
import cz.fi.muni.xkremser.editor.client.metadata.NoteHolder;
import cz.fi.muni.xkremser.editor.client.metadata.OriginInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PartHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PhysicalDescriptionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PlaceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.RecordInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.SubjectHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TableOfContentsHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TitleInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TypeOfResourceHolder;
import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.AccessConditionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.BaseDateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ClassificationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.CopyInformationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.DetailTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.EnumerationAndChronologyTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtentTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.IdentifierTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient.LanguageTermClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalLocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTermTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient.RecordIdentifierClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusAuthorityPlusLanguageClient;
import cz.fi.muni.xkremser.editor.client.mods.StringPlusDisplayLabelPlusTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient.GeographicCodeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TargetAudienceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.UnstructuredTextClient;
import cz.fi.muni.xkremser.editor.client.mods.UrlTypeClient;
import cz.fi.muni.xkremser.editor.client.util.ClientUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TabUtils.
 */
public final class TabUtils {

    private static LangConstants lang;

    static {
        TabUtils.lang = GWT.create(LangConstants.class);
    }

    /** The Constant W3C_ENCODING. */
    private static final String W3C_ENCODING = "w3cdtf";

    /** The Constant LANG_AUTHORITY_TOOLTIPS. */
    private static final Map<String, String> LANG_AUTHORITY_TOOLTIPS = new HashMap<String, String>() {

        {
            put("", lang.nothing());
            put("iso639-2b", lang.iso6392b());
            put("rfc3066", lang.rfc3066());
            put("iso639-3", lang.iso6393());
            put("rfc4646", lang.rfc4646());
        }
    };

    /** The Constant TYPE_TEXT_CODE_TOOLTIPS. */
    private static final Map<String, String> TYPE_TEXT_CODE_TOOLTIPS = new HashMap<String, String>() {

        {
            put("", lang.attributeOmitted());
            put("text", lang.attributeInText());
            put("code", lang.attributeInCode());
        }
    };

    /** The Constant ENCODING_TOOLTIPS. */
    private static final Map<String, String> ENCODING_TOOLTIPS = new HashMap<String, String>() {

        {
            put("", lang.attributeOmitted());
            put(W3C_ENCODING, lang.W3CencodingDatePattern());
            put("iso8601", lang.iso8601DatePatern());
            put("marc", lang.marcDate());
        }
    };

    /** The Constant POINT_TOOLTIPS. */
    private static final Map<String, String> POINT_TOOLTIPS = new HashMap<String, String>() {

        {
            put("", lang.attributeOmitted());
            put("start", lang.startValue());
            put("end", lang.endValue());
        }
    };

    /** The Constant QUALIFIER_TOOLTIPS. */
    private static final Map<String, String> QUALIFIER_TOOLTIPS = new HashMap<String, String>() {

        {
            put("", lang.attributeOmitted());
            put("approximate", lang.approximateValue());
            put("inferred", lang.inferredValue());
            put("questionable", lang.questionableValue());
        }
    };

    /**
     * ATT r_ authority.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    private static final Attribute ATTR_AUTHORITY(String value) {
        return new Attribute(TextItem.class,
                             ModsConstants.AUTHORITY,
                             "Authority",
                             lang.authorityAttr(),
                             value);
    }

    /**
     * The Constant ATTR_LANG.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_LANG(String value) {
        return new Attribute(TextItem.class, ModsConstants.LANG, "Lang", lang.iso6392b());
    }

    /**
     * ATT r_ xm l_ lang.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_XML_LANG(String value) {
        return new Attribute(TextItem.class, ModsConstants.XML_LANG, "xml:lang", lang.xmlLangAttr(), value);
    }

    /**
     * ATT r_ transliteration.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_TRANSLITERATION(String value) {
        return new Attribute(TextItem.class,
                             ModsConstants.TRANSLITERATION,
                             "Transliteration",
                             lang.transliterationAttr(),
                             value);
    }

    /**
     * ATT r_ script.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_SCRIPT(String value) {
        return new Attribute(TextItem.class, ModsConstants.SCRIPT, "Script", lang.scriptAttr(), value);
    }

    /**
     * ATT r_ id.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_ID(String value) {
        return new Attribute(TextItem.class, ModsConstants.ID, "ID", lang.idAttr(), value);
    }

    /**
     * ATT r_ xlink.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_XLINK(String value) {
        return new Attribute(TextItem.class, ModsConstants.XLINK, "xlink", lang.xlinkAttr(), value);
    }

    /**
     * ATT r_ encoding.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_ENCODING(String value) {
        return new Attribute(SelectItem.class, ModsConstants.ENCODING, "Encoding", ENCODING_TOOLTIPS, value);
    }

    /**
     * ATT r_ point.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_POINT(String value) {
        return new Attribute(SelectItem.class, ModsConstants.POINT, "Point", POINT_TOOLTIPS, value);
    }

    /**
     * ATT r_ ke y_ date.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_KEY_DATE(String value) {
        return new Attribute(CheckboxItem.class,
                             ModsConstants.KEY_DATE,
                             "Key Date",
                             lang.keyDateAttr(),
                             value);
    }

    /**
     * ATT r_ qualifier.
     * 
     * @param value
     *        the value
     * @return the attribute
     */
    public static final Attribute ATTR_QUALIFIER(String value) {
        return new Attribute(SelectItem.class,
                             ModsConstants.QUALIFIER,
                             "Qualifier",
                             QUALIFIER_TOOLTIPS,
                             value);
    }

    /** The Constant ATTR_TYPE_TEXT_CODE. */
    public static final Attribute ATTR_TYPE_TEXT_CODE = new Attribute(SelectItem.class,
                                                                      ModsConstants.TYPE,
                                                                      "Type",
                                                                      TYPE_TEXT_CODE_TOOLTIPS);

    /** The Constant ATTR_LANG_AUTHORITY. */
    public static final Attribute ATTR_LANG_AUTHORITY = new Attribute(SelectItem.class,
                                                                      ModsConstants.AUTHORITY,
                                                                      "Authority",
                                                                      LANG_AUTHORITY_TOOLTIPS);

    /**
     * Gets the display label.
     * 
     * @param tooltip
     *        the tooltip
     * @param value
     *        the value
     * @return the display label
     */
    public static Attribute getDisplayLabel(final String tooltip, final String value) {
        return new Attribute(TextItem.class, ModsConstants.DISPLAY_LABEL, "Display Label", tooltip, value);
    }

    /**
     * The Interface GetLayoutOperation.
     */
    public static abstract class GetLayoutOperation {

        /** The holders. */
        private List<? extends Object> holders;

        /** The values. */
        private List<? extends Object> values;

        /** The counter. */
        protected int counter = 0;

        /**
         * Increase counter.
         */
        public void increaseCounter() {
            ++counter;
        }

        /**
         * Decrease counter.
         */
        public void decreaseCounter() {
            --counter;
        }

        /**
         * Gets the counter.
         * 
         * @return the counter
         */
        public int getCounter() {
            return counter;
        }

        /**
         * Gets the element number.
         * 
         * @return the element number
         */
        public int getElementNumber() {
            return values == null ? 1 : values.size();
        }

        /**
         * Gets the holders.
         * 
         * @return the holders
         */
        public List<? extends Object> getHolders() {
            return holders;
        }

        /**
         * Sets the holders.
         * 
         * @param holders
         *        the new holders
         */
        public void setHolders(List<? extends Object> holders) {
            this.holders = holders;
        }

        /**
         * Gets the values.
         * 
         * @return the values
         */
        public List<? extends Object> getValues() {
            return values;
        }

        /**
         * Sets the values.
         * 
         * @param values
         *        the new values
         */
        public void setValues(List<? extends Object> values) {
            this.values = values;
        }

        /**
         * Execute.
         * 
         * @return the canvas
         */
        abstract Canvas execute();
    }

    /**
     * The Class GetTitleInfoLayout.
     */
    private static class GetTitleInfoLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            TitleInfoTypeClient titleInfoTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                titleInfoTypeClient = (TitleInfoTypeClient) getValues().get(counter);
            }
            TitleInfoHolder titleInfoHolder = new TitleInfoHolder();
            ((List<TitleInfoHolder>) getHolders()).add(titleInfoHolder);
            increaseCounter();

            return getTitleInfoLayout(titleInfoTypeClient, titleInfoHolder);
        }
    }

    /**
     * The Class GetNameLayout.
     */
    private static class GetNameLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            NameTypeClient nameTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                nameTypeClient = (NameTypeClient) getValues().get(counter);
            }
            NameHolder nameHolder = new NameHolder();
            ((List<NameHolder>) getHolders()).add(nameHolder);
            increaseCounter();
            return getNameLayout(nameTypeClient, nameHolder);
        }
    }

    /**
     * The Class GetTypeOfResourceLayout.
     */
    private static class GetTypeOfResourceLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            TypeOfResourceTypeClient typeOfResourceTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                typeOfResourceTypeClient = (TypeOfResourceTypeClient) getValues().get(counter);
            }
            TypeOfResourceHolder holder = new TypeOfResourceHolder();
            ((List<TypeOfResourceHolder>) getHolders()).add(holder);
            increaseCounter();
            return getTypeOfResourceLayout(typeOfResourceTypeClient, holder);
        }
    }

    /**
     * The Class GetGenreLayout.
     */
    private static class GetGenreLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            GenreTypeClient genreTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                genreTypeClient = (GenreTypeClient) getValues().get(counter);
            }
            GenreHolder holder = new GenreHolder();
            ((List<GenreHolder>) getHolders()).add(holder);
            increaseCounter();
            return getGenreLayout(genreTypeClient, holder);
        }
    }

    /**
     * The Class GetOriginInfoLayout.
     */
    private static class GetOriginInfoLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            OriginInfoTypeClient originInfoTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                originInfoTypeClient = (OriginInfoTypeClient) getValues().get(counter);
            }
            OriginInfoHolder holder = new OriginInfoHolder();
            ((List<OriginInfoHolder>) getHolders()).add(holder);
            increaseCounter();
            return getOriginInfoLayout(originInfoTypeClient, holder);
        }
    }

    /**
     * The Class GetPhysicialDescriptionLayout.
     */
    private static class GetPhysicalDescriptionLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            PhysicalDescriptionTypeClient values = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                values = (PhysicalDescriptionTypeClient) getValues().get(counter);
            }
            PhysicalDescriptionHolder holder = new PhysicalDescriptionHolder();
            ((List<PhysicalDescriptionHolder>) getHolders()).add(holder);
            increaseCounter();
            return getPhysicialDescriptionLayout(values, holder);
        }
    }

    /**
     * The Class GetGeneralLayout.
     */
    private static class GetGeneralLayout
            extends GetLayoutOperation {

        /** The Constant ABSTRACT. */
        public static final int ABSTRACT = 0;

        /** The Constant TOC. */
        public static final int TOC = 1;

        /** The Constant NOTE. */
        private static final int NOTE = 2;

        /** The Constant SIMPLE_NOTE. */
        private static final int SIMPLE_NOTE = 3;

        /** The Constant ACCESS_CONDITION. */
        private static final int ACCESS_CONDITION = 4;

        /** The Constant EXTENSION. */
        private static final int EXTENSION = 5;

        /** The head. */
        private final Attribute head;

        /** The attributes. */
        private final Attribute[] attributes;

        /** The type. */
        private final int type;

        /**
         * Instantiates a new gets the general layout.
         * 
         * @param head
         *        the head
         * @param attributes
         *        the attributes
         * @param type
         *        the type
         */
        public GetGeneralLayout(Attribute head, Attribute[] attributes, int type) {
            super();
            this.head = head;
            this.attributes = attributes;
            this.type = type;
        }

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            Map<String, String> values = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                values = (HashMap<String, String>) getValues().get(counter);
            }
            ListOfSimpleValuesHolder holder = null;
            switch (type) {
                case ABSTRACT:
                    holder = new AbstractHolder();
                    break;
                case TOC:
                    holder = new TableOfContentsHolder();
                    break;
                case NOTE:
                    holder = new NoteHolder();
                    break;
                case SIMPLE_NOTE:
                    holder = new ListOfSimpleValuesHolder();
                    break;
                case ACCESS_CONDITION:
                    holder = new AccessConditionHolder();
                    break;
                case EXTENSION:
                    holder = new ExtensionHolder();
                    break;
                default:
                    throw new IllegalStateException("unknown holder type");
            }

            if (getHolders() != null) ((List<ListOfSimpleValuesHolder>) getHolders()).add(holder);
            increaseCounter();
            if (values != null) {
                head.setValue(values.get(head.getName()));
                for (Attribute att : attributes) {
                    att.setValue(values.get(att.getName()));
                }
            }
            return getGeneralLayout(head, attributes, holder);

        }
    }

    /**
     * The Class GetGeneralDeepLayout.
     */
    private static class GetGeneralDeepLayout
            extends GetLayoutOperation {

        /** The attributes. */
        private final Attribute[] attributes;

        /**
         * Instantiates a new gets the general deep layout.
         * 
         * @param attributes
         *        the attributes
         */
        public GetGeneralDeepLayout(Attribute[] attributes) {
            super();
            this.attributes = attributes;
        }

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            return getGeneralDeepLayout(attributes);
        }
    }

    /**
     * The Class GetSubjectLayout.
     */
    private static class GetSubjectLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            SubjectTypeClient values = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                values = (SubjectTypeClient) getValues().get(counter);
            }
            SubjectHolder holder = new SubjectHolder();
            ((List<SubjectHolder>) getHolders()).add(holder);
            increaseCounter();
            return getSubjectLayout(values, holder);
        }
    }

    /**
     * The Class GetLocationLayout.
     */
    private static class GetLocationLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            LocationTypeClient values = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                values = (LocationTypeClient) getValues().get(counter);
            }
            LocationHolder holder = new LocationHolder();
            ((List<LocationHolder>) getHolders()).add(holder);
            increaseCounter();
            return getLocationLayout(values, holder);
        }
    }

    /**
     * The Class GetCopyInformationLayout.
     */
    private static class GetCopyInformationLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            CopyInformationTypeClient values = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                values = (CopyInformationTypeClient) getValues().get(counter);
            }
            CopyInformationHolder holder = new CopyInformationHolder();
            ((List<CopyInformationHolder>) getHolders()).add(holder);
            increaseCounter();
            return getCopyInformationLayout(values, holder);
        }
    }

    /**
     * The Class GetPartLayout.
     */
    private static class GetPartLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            PartTypeClient value = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                value = (PartTypeClient) getValues().get(counter);
            }
            PartHolder holder = new PartHolder();
            ((List<PartHolder>) getHolders()).add(holder);
            increaseCounter();
            return getPartLayout(value, holder);
        }
    }

    /**
     * The Class GetRecordInfoLayout.
     */
    private static class GetRecordInfoLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            RecordInfoTypeClient value = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                value = (RecordInfoTypeClient) getValues().get(counter);
            }
            RecordInfoHolder holder = new RecordInfoHolder();
            ((List<RecordInfoHolder>) getHolders()).add(holder);
            increaseCounter();
            return getRecordInfoLayout(value, holder);
        }
    }

    /**
     * The Class GetPlaceLayout.
     */
    private static class GetPlaceLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            PlaceTypeClient placeTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                placeTypeClient = (PlaceTypeClient) getValues().get(counter);
            }
            PlaceHolder holder = new PlaceHolder();
            ((List<PlaceHolder>) getHolders()).add(holder);
            increaseCounter();
            return getPlaceLayout(placeTypeClient, holder);
        }
    }

    /**
     * The Class GetDetailLayout.
     */
    private static class GetDetailLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            DetailTypeClient detailTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                detailTypeClient = (DetailTypeClient) getValues().get(counter);
            }
            DetailHolder holder = new DetailHolder();
            ((List<DetailHolder>) getHolders()).add(holder);
            increaseCounter();
            return getDetailLayout(detailTypeClient, holder);
        }
    }

    /**
     * The Class GetExtentLayout.
     */
    private static class GetExtentLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            ExtentTypeClient extentTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                extentTypeClient = (ExtentTypeClient) getValues().get(counter);
            }
            ExtentHolder holder = new ExtentHolder();
            ((List<ExtentHolder>) getHolders()).add(holder);
            increaseCounter();
            return getExtentLayout(extentTypeClient, holder);
        }
    }

    /**
     * The Class GetLanguageLayout.
     */
    private static class GetLanguageLayout
            extends GetLayoutOperation {

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            LanguageTypeClient languageTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                languageTypeClient = (LanguageTypeClient) getValues().get(counter);
            }
            LanguageHolder holder = new LanguageHolder();
            ((List<LanguageHolder>) getHolders()).add(holder);
            increaseCounter();
            return getLanguageLayout(languageTypeClient, holder);
        }
    }

    /**
     * The Class GetDateLayout.
     */
    private static class GetDateLayout
            extends GetLayoutOperation {

        /** The name. */
        private final String name;

        /** The title. */
        private final String title;

        /** The tooltip. */
        private final String tooltip;

        /** The attribute. */
        private final Attribute attribute;

        /** The key date. */
        private final boolean keyDate;

        /** The is other date. */
        private final boolean isOtherDate;

        /**
         * Instantiates a new gets the date layout.
         * 
         * @param name
         *        the name
         * @param title
         *        the title
         * @param tooltip
         *        the tooltip
         * @param values
         *        the values
         * @param holders
         *        the holders
         */
        public GetDateLayout(String name,
                             String title,
                             String tooltip,
                             List<? extends BaseDateTypeClient> values,
                             List<DateHolder> holders) {
            this(name, title, tooltip, null, true, values, holders);
        }

        /**
         * Instantiates a new gets the date layout.
         * 
         * @param name
         *        the name
         * @param title
         *        the title
         * @param tooltip
         *        the tooltip
         * @param attribute
         *        the attribute
         * @param keyDate
         *        the key date
         * @param values
         *        the values
         * @param holders
         *        the holders
         */
        public GetDateLayout(String name,
                             String title,
                             String tooltip,
                             Attribute attribute,
                             boolean keyDate,
                             List<? extends BaseDateTypeClient> values,
                             List<DateHolder> holders) {
            super();
            this.name = name;
            this.title = title;
            this.tooltip = tooltip;
            this.attribute = attribute;
            this.keyDate = keyDate;
            isOtherDate = false;
            setHolders(holders == null ? new ArrayList<DateHolder>() : holders);
            setValues(values);
        }

        /**
         * Instantiates a new gets the date layout.
         * 
         * @param name
         *        the name
         * @param title
         *        the title
         * @param tooltip
         *        the tooltip
         * @param attribute
         *        the attribute
         * @param keyDate
         *        the key date
         * @param values
         *        the values
         * @param holders
         *        the holders
         * @param isOtherDate
         *        the is other date
         */
        public GetDateLayout(String name,
                             String title,
                             String tooltip,
                             Attribute attribute,
                             boolean keyDate,
                             List<? extends DateTypeClient> values,
                             List<DateHolder> holders,
                             boolean isOtherDate) {
            super();
            this.name = name;
            this.title = title;
            this.tooltip = tooltip;
            this.attribute = attribute;
            this.keyDate = keyDate;
            this.isOtherDate = isOtherDate;
            setHolders(holders == null ? new ArrayList<DateHolder>() : holders);
            setValues(values);
        }

        /*
         * (non-Javadoc)
         * @see
         * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
         * #execute()
         */
        @Override
        public VLayout execute() {
            BaseDateTypeClient dateTypeClient = null;
            if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
                dateTypeClient = (BaseDateTypeClient) getValues().get(counter);
            }
            DateHolder holder = new DateHolder(name, isOtherDate);
            ((List<DateHolder>) getHolders()).add(holder);
            increaseCounter();
            return getDateLayout(name, title, tooltip, attribute, keyDate, dateTypeClient, holder);
        }
    }

    /**
     * Gets the stack section.
     * 
     * @param attribute
     *        the attribute
     * @param expanded
     *        the expanded
     * @param holder
     *        the holder
     * @return the stack section
     */
    public static SectionStackSection getStackSection(Attribute attribute,
                                                      final boolean expanded,
                                                      MetadataHolder holder) {
        return getStackSectionWithAttributes(attribute.getLabel(), attribute, expanded, null, holder);
    }

    /**
     * Gets the stack section.
     * 
     * @param attribute
     *        the attribute
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the stack section
     */
    public static SectionStackSection getStackSection(Attribute attribute,
                                                      final boolean expanded,
                                                      final List<String> values,
                                                      MetadataHolder holder) {
        return getStackSectionWithAttributes(attribute.getName(),
                                             attribute,
                                             expanded,
                                             null,
                                             new ArrayList<List<String>>() {

                                                 {
                                                     add(values);
                                                 }
                                             },
                                             holder,
                                             true);
    }

    /**
     * Gets the stack section.
     * 
     * @param label1
     *        the label1
     * @param label2
     *        the label2
     * @param tooltip
     *        the tooltip
     * @param expanded
     *        the expanded
     * @return the stack section
     */
    public static SectionStackSection getStackSection(final String label1,
                                                      final String label2,
                                                      final String tooltip,
                                                      final boolean expanded) {
        return getStackSectionWithAttributes(label1, label2, tooltip, expanded, null, null);
    }

    /**
     * Gets the stack section.
     * 
     * @param label1
     *        the label1
     * @param label2
     *        the label2
     * @param tooltip
     *        the tooltip
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the stack section
     */
    public static SectionStackSection getStackSection(final String label1,
                                                      final String label2,
                                                      final String tooltip,
                                                      final boolean expanded,
                                                      final List<String> values,
                                                      MetadataHolder holder) {
        return getStackSectionWithAttributes(label1,
                                             label2,
                                             tooltip,
                                             expanded,
                                             null,
                                             new ArrayList<List<String>>() {

                                                 {
                                                     add(values);
                                                 }
                                             },
                                             holder,
                                             true);
    }

    /**
     * Gets the simple section.
     * 
     * @param attribute
     *        the attribute
     * @param expanded
     *        the expanded
     * @param holder
     *        the holder
     * @return the simple section
     */
    public static SectionStackSection getSimpleSection(final Attribute attribute,
                                                       final boolean expanded,
                                                       MetadataHolder holder) {
        return getSimpleSectionWithAttributes(attribute, expanded, null, holder);
    }

    /**
     * Gets the simple section.
     * 
     * @param type
     *        the type
     * @param label
     *        the label
     * @param tooltip
     *        the tooltip
     * @param expanded
     *        the expanded
     * @return the simple section
     */
    public static SectionStackSection getSimpleSection(final Class<? extends FormItem> type,
                                                       final String label,
                                                       final String tooltip,
                                                       final boolean expanded) {
        return getSimpleSectionWithAttributes(new Attribute(type,
                                                            label.toLowerCase().replaceAll(" ", "_"),
                                                            label,
                                                            tooltip), expanded, null, null);
    }

    /**
     * Gets the stack section with attributes.
     * 
     * @param label1
     *        the label1
     * @param label2
     *        the label2
     * @param tooltip
     *        the tooltip
     * @param expanded
     *        the expanded
     * @param attributes
     *        the attributes
     * @param holder
     *        the holder
     * @return the stack section with attributes
     */
    public static SectionStackSection getStackSectionWithAttributes(final String label1,
                                                                    final String label2,
                                                                    final String tooltip,
                                                                    final boolean expanded,
                                                                    final Attribute[] attributes,
                                                                    MetadataHolder holder) {
        return getStackSectionWithAttributes(label1,
                                             label2,
                                             tooltip,
                                             expanded,
                                             attributes,
                                             null,
                                             holder,
                                             true);
    }

    /**
     * Gets the stack section with attributes.
     * 
     * @param label1
     *        the label1
     * @param label2
     *        the label2
     * @param tooltip
     *        the tooltip
     * @param expanded
     *        the expanded
     * @param attributes
     *        the attributes
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @param flat
     *        the flat
     * @return the stack section with attributes
     */
    public static SectionStackSection getStackSectionWithAttributes(final String label1,
                                                                    final String label2,
                                                                    final String tooltip,
                                                                    final boolean expanded,
                                                                    final Attribute[] attributes,
                                                                    final List<List<String>> values,
                                                                    MetadataHolder holder,
                                                                    boolean flat) {
        return getStackSectionWithAttributes(label1, new Attribute(TextItem.class, label2.toLowerCase()
                .replaceAll(" ", "_"), label2, tooltip), expanded, attributes, values, holder, flat);
    }

    /**
     * Gets the simple section with attributes.
     * 
     * @param mainAttr
     *        the main attr
     * @param expanded
     *        the expanded
     * @param attributes
     *        the attributes
     * @param holder
     *        the holder
     * @return the simple section with attributes
     */
    public static SectionStackSection getSimpleSectionWithAttributes(final Attribute mainAttr,
                                                                     final boolean expanded,
                                                                     Attribute[] attributes,
                                                                     MetadataHolder holder) {
        final boolean isAttribPresent = attributes != null && attributes.length != 0;
        final VLayout layout = new VLayout();
        if (isAttribPresent) {
            DynamicForm f = getAttributes(true, attributes);
            if (holder != null) // should not
                holder.setAttributeForm(f);
            layout.addMember(f);
        }

        SectionStackSection section = new SectionStackSection(mainAttr.getName());
        section.setTitle(mainAttr.getLabel());

        final DynamicForm form = new DynamicForm();
        FormItem item = newItem(mainAttr);
        item.setWidth(200);
        form.setFields(item);
        form.setPadding(5);
        if (isAttribPresent) {
            form.setMargin(10);
            form.setGroupTitle("Values");
            layout.addMember(form);
            section.addItem(layout);
            if (holder != null) {
                holder.setLayout(layout);
            }
        } else {
            section.addItem(form);
            if (holder != null) {
                holder.setAttributeForm(form);
            }
        }
        section.setExpanded(expanded);

        return section;
    }

    /**
     * Gets the stack section with attributes.
     * 
     * @param stackLabel
     *        the stack label
     * @param mainAttr
     *        the main attr
     * @param expanded
     *        the expanded
     * @param attributes
     *        the attributes
     * @param holder
     *        the holder
     * @return the stack section with attributes
     */
    public static SectionStackSection getStackSectionWithAttributes(final String stackLabel,
                                                                    final Attribute mainAttr,
                                                                    final boolean expanded,
                                                                    final Attribute[] attributes,
                                                                    MetadataHolder holder) {
        return getStackSectionWithAttributes(stackLabel, mainAttr, expanded, attributes, null, holder, true);
    }

    /**
     * Gets the stack section with attributes.
     * 
     * @param stackLabel
     *        the stack label
     * @param mainAttr
     *        the main attr
     * @param expanded
     *        the expanded
     * @param attributes
     *        the attributes
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @param flat
     *        the flat
     * @return the stack section with attributes
     */
    public static SectionStackSection getStackSectionWithAttributes(final String stackLabel,
                                                                    final Attribute mainAttr,
                                                                    final boolean expanded,
                                                                    final Attribute[] attributes,
                                                                    List<List<String>> values,
                                                                    MetadataHolder holder,
                                                                    boolean flat) {
        final boolean isAttribPresent = attributes != null && attributes.length != 0;
        final VLayout layout = new VLayout();
        layout.setOverflow(Overflow.VISIBLE);
        if (holder != null) {
            holder.setLayout(layout);
        }
        SectionStackSection section = new SectionStackSection(stackLabel.toLowerCase().replaceAll(" ", "_"));
        section.setTitle(stackLabel);
        final ImgButton addButton = new ImgButton();
        addButton.setSrc("[SKIN]headerIcons/plus.png");
        addButton.setShowDownIcon(false);
        addButton.setShowDown(false);
        addButton.setSize(16);
        addButton.setShowRollOver(true);
        addButton.setCanHover(true);
        addButton.setHoverOpacity(75);
        addButton.setHoverStyle("interactImageHover");
        addButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                addButton.setPrompt("Add new " + mainAttr.getLabel() + ".");
            }
        });
        addButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final DynamicForm form = new DynamicForm();
                FormItem item = newItem(mainAttr);
                item.setWidth(200);
                if (isAttribPresent) {
                    FormItem[] items = getAttributes(false, attributes).getFields();
                    FormItem[] itemsToAdd = new FormItem[items.length + 1];
                    itemsToAdd[0] = item;
                    for (int i = 0; i < items.length; i++) {
                        itemsToAdd[i + 1] = items[i];
                    }
                    form.setFields(itemsToAdd);
                    form.setNumCols(4);
                } else {
                    form.setFields(item);
                }
                form.setPadding(5);
                layout.addMember(form);
            }
        });

        final ImgButton removeButton = new ImgButton();
        removeButton.setSrc("[SKIN]headerIcons/minus.png");
        removeButton.setShowDownIcon(false);
        removeButton.setShowDown(false);
        removeButton.setSize(16);
        removeButton.setShowRollOver(true);
        removeButton.setCanHover(true);
        removeButton.setHoverOpacity(75);
        removeButton.setHoverStyle("interactImageHover");
        removeButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                removeButton.setPrompt("Remove last " + mainAttr.getLabel() + ".");
            }
        });
        removeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Canvas[] members = layout.getMembers();
                if (members != null && members.length != 0) {
                    layout.removeMember(members[members.length - 1]);
                }
            }
        });
        section.setControls(addButton, removeButton);

        // TODO: refactoring (code duplication)
        if (!flat) {
            if (values != null && values.size() > 0) {
                for (List<String> valueVector : values) {
                    final DynamicForm form = new DynamicForm();
                    FormItem item = newItem(mainAttr);
                    item.setValue(valueVector == null ? "" : valueVector.get(0));
                    item.setWidth(200);
                    if (isAttribPresent) {
                        FormItem[] items = getAttributes(false, attributes).getFields();
                        FormItem[] itemsToAdd = new FormItem[items.length + 1];
                        itemsToAdd[0] = item;
                        for (int i = 0; i < items.length; i++) {
                            itemsToAdd[i + 1] = items[i];
                            if (itemsToAdd[i + 1] instanceof CheckboxItem) {
                                itemsToAdd[i + 1].setValue(valueVector == null ? false : valueVector
                                        .get(i + 1) == null ? false : ClientUtils.toBoolean(valueVector
                                        .get(i + 1)));
                            } else {
                                // If it throws the IndexOutOfBoundsException, there are less
                                // values than it should be and the mistake is probably where
                                // the values list is created
                                itemsToAdd[i + 1].setValue(valueVector == null ? ""
                                        : valueVector.get(i + 1) == null ? "" : valueVector.get(i + 1));
                            }
                        }
                        form.setFields(itemsToAdd);
                        form.setNumCols(4);
                    } else {
                        form.setFields(item);
                    }
                    form.setPadding(5);
                    layout.addMember(form);
                }
            } else {
                final DynamicForm form = new DynamicForm();
                FormItem item = newItem(mainAttr);
                item.setWidth(200);
                if (isAttribPresent) {
                    FormItem[] items = getAttributes(false, attributes).getFields();
                    FormItem[] itemsToAdd = new FormItem[items.length + 1];
                    itemsToAdd[0] = item;
                    for (int i = 0; i < items.length; i++) {
                        itemsToAdd[i + 1] = items[i];
                    }
                    form.setFields(itemsToAdd);
                    form.setNumCols(4);
                } else {
                    form.setFields(item);
                }
                form.setPadding(5);
                layout.addMember(form);
            }
        } else if (flat) {
            boolean isVals =
                    values != null && values.size() > 0 && values.get(0) != null && values.get(0).size() > 0;
            int number = isVals ? values.get(0).size() : 1;
            for (int j = 0; j < number; j++) {
                final DynamicForm form = new DynamicForm();
                FormItem item = newItem(mainAttr);
                item.setValue(isVals ? values.get(0).get(j) : "");
                item.setWidth(200);
                if (isAttribPresent) {
                    FormItem[] items = getAttributes(false, attributes).getFields();
                    FormItem[] itemsToAdd = new FormItem[items.length + 1];
                    itemsToAdd[0] = item;
                    for (int i = 0; i < items.length; i++) {
                        itemsToAdd[i + 1] = items[i];
                    }
                    form.setFields(itemsToAdd);
                    form.setNumCols(4);
                } else {
                    form.setFields(item);
                }
                form.setPadding(5);
                layout.addMember(form);
            }
        }

        section.addItem(layout);
        section.setExpanded(expanded);

        return section;
    }

    /**
     * Gets the attributes.
     * 
     * @param group
     *        the group
     * @param attributes
     *        the attributes
     * @return the attributes
     */
    public static DynamicForm getAttributes(final boolean group, final Attribute... attributes) {
        final DynamicForm form = new DynamicForm();
        form.setStyleName("metadata-attributes");
        if (group) {
            form.setGroupTitle("Attributes");
            form.setIsGroup(true);
            if (attributes.length > 5) {
                form.setNumCols(4);
            } else {
                form.setWidth(450);
            }
        }

        FormItem[] fields = new FormItem[attributes.length];
        int i = 0;
        for (final Attribute attr : attributes) {
            FormItem item = newItem(attr);
            if (item != null) {
                item.setWidth(200);
                item.setTitle(attr.getLabel());
                fields[i++] = item;
            }
        }
        form.setFields(fields);

        return form;
    }

    /**
     * New item.
     * 
     * @param attr
     *        the attr
     * @return the form item
     */
    private static FormItem newItem(final Attribute attr) {
        FormItem item = null;
        boolean isCheckbox = false;
        Class<? extends FormItem> type = attr.getType();
        if (type.equals(TextAreaItem.class)) {
            item = new TextAreaItem(attr.getName());
        } else if (type.equals(TextItem.class)) {
            item = new TextItem(attr.getName());
        } else if (type.equals(CheckboxItem.class)) {
            isCheckbox = true;
            item = new CheckboxItem(attr.getName());
        } else if (type.equals(DateItem.class)) {
            item = new DateItem(attr.getName());
            ((DateItem) item).setUseTextField(true);
        } else if (type.equals(SelectItem.class)) {
            item = new SelectItem(attr.getName());
            item.setValueMap(attr.getLabels());
            item.setHoverOpacity(75);
            item.setHoverWidth(330);
            item.setHoverStyle("interactImageHover");
            final FormItem finalItem = item;
            item.addItemHoverHandler(new ItemHoverHandler() {

                @Override
                public void onItemHover(ItemHoverEvent event) {
                    if (finalItem.getValue() != null && attr.getTooltips().get(finalItem.getValue()) != null) {
                        finalItem.setPrompt(attr.getTooltips().get(finalItem.getValue()));
                    }
                }
            });
        }
        if (attr.getValue() != null && !"".equals(attr.getValue())) {
            if (isCheckbox) {
                item.setValue(ClientUtils.toBoolean(attr.getValue()));
            } else {
                item.setValue(attr.getValue());
            }
        }
        if (attr.getTooltip() != null && !"".equals(attr.getTooltip())) {
            item.setHoverOpacity(75);
            item.setHoverWidth(330);
            item.setHoverStyle("interactImageHover");
            item.setTooltip(attr.getTooltip());
        }
        item.setTitle(attr.getLabel());
        return item;
    }

    /**
     * Gets the some stack.
     * 
     * @param expanded
     *        the expanded
     * @param label
     *        the label
     * @param operation
     *        the operation
     * @return the some stack
     */
    public static SectionStackSection getSomeStack(boolean expanded,
                                                   final String label,
                                                   final GetLayoutOperation operation) {
        final SectionStackSection section = new SectionStackSection(label);
        final VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        int i = operation.getElementNumber();
        while (i-- > 0)
            layout.addMember(operation.execute());

        final ImgButton addButton = new ImgButton();
        addButton.setSrc("[SKIN]headerIcons/plus.png");
        addButton.setShowDownIcon(false);
        addButton.setShowDown(false);
        addButton.setSize(16);
        addButton.setShowRollOver(true);
        addButton.setCanHover(true);
        addButton.setHoverOpacity(75);
        addButton.setHoverStyle("interactImageHover");
        addButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                addButton.setPrompt("Add new " + label + ".");
            }
        });
        addButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                layout.addMember(operation.execute());
                operation.increaseCounter();
            }
        });

        final ImgButton removeButton = new ImgButton();
        removeButton.setSrc("[SKIN]headerIcons/minus.png");
        removeButton.setShowDownIcon(false);
        removeButton.setShowDown(false);
        removeButton.setSize(16);
        removeButton.setShowRollOver(true);
        removeButton.setCanHover(true);
        removeButton.setHoverOpacity(75);
        removeButton.setHoverStyle("interactImageHover");
        removeButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                removeButton.setPrompt("Remove last " + label + ".");
            }
        });
        removeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                Canvas[] members = layout.getMembers();
                if (members != null && members.length != 0) {
                    layout.removeMember(members[members.length - 1]);
                    operation.decreaseCounter();
                    operation.getHolders().remove(operation.getCounter() - 1);
                }
            }
        });
        section.setControls(addButton, removeButton);
        section.setResizeable(true);
        // layout.setHeight100();
        section.addItem(layout);
        section.setExpanded(expanded);

        return section;
    }

    /**
     * Gets the title info stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the title info stack
     */
    public static SectionStackSection getTitleInfoStack(boolean expanded,
                                                        List<TitleInfoTypeClient> values,
                                                        List<TitleInfoHolder> holders) {
        GetTitleInfoLayout getTitleInfoLayout = new GetTitleInfoLayout();
        getTitleInfoLayout.setHolders(holders);
        getTitleInfoLayout.setValues(values);
        return getSomeStack(expanded, "Title Info", getTitleInfoLayout);
    }

    /**
     * Gets the name stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the name stack
     */
    public static SectionStackSection getNameStack(boolean expanded,
                                                   List<NameTypeClient> values,
                                                   List<NameHolder> holders) {
        GetNameLayout getNameLayout = new GetNameLayout();
        getNameLayout.setHolders(holders);
        getNameLayout.setValues(values);
        return getSomeStack(expanded, "Name", getNameLayout);
    }

    /**
     * Gets the type of resource stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the type of resource stack
     */
    public static SectionStackSection getTypeOfResourceStack(boolean expanded,
                                                             List<TypeOfResourceTypeClient> values,
                                                             List<TypeOfResourceHolder> holders) {
        GetTypeOfResourceLayout getTypeOfResourceLayout = new GetTypeOfResourceLayout();
        getTypeOfResourceLayout.setHolders(holders);
        getTypeOfResourceLayout.setValues(values);
        return getSomeStack(expanded, "Type of Resource", getTypeOfResourceLayout);
    }

    /**
     * Gets the genre stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the genre stack
     */
    public static SectionStackSection getGenreStack(boolean expanded,
                                                    List<GenreTypeClient> values,
                                                    List<GenreHolder> holders) {
        GetGenreLayout getLayout = new GetGenreLayout();
        getLayout.setHolders(holders);
        getLayout.setValues(values);
        return getSomeStack(expanded, "Genre", getLayout);
    }

    /**
     * Gets the origin info stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the origin info stack
     */
    public static SectionStackSection getOriginInfoStack(boolean expanded,
                                                         List<OriginInfoTypeClient> values,
                                                         List<OriginInfoHolder> holders) {
        GetOriginInfoLayout getOriginInfoLayout = new GetOriginInfoLayout();
        getOriginInfoLayout.setValues(values);
        getOriginInfoLayout.setHolders(holders);
        return getSomeStack(expanded, "Origin Info", getOriginInfoLayout);
    }

    /**
     * Gets the language stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the language stack
     */
    public static SectionStackSection getLanguageStack(boolean expanded,
                                                       List<LanguageTypeClient> values,
                                                       List<LanguageHolder> holders) {
        GetLanguageLayout getLanguageLayout = new GetLanguageLayout();
        getLanguageLayout.setValues(values);
        getLanguageLayout.setHolders(holders);
        return getSomeStack(expanded, "Language", getLanguageLayout);
    }

    /**
     * Gets the physical description stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the physical description stack
     */
    public static SectionStackSection getPhysicalDescriptionStack(boolean expanded,
                                                                  List<PhysicalDescriptionTypeClient> values,
                                                                  List<PhysicalDescriptionHolder> holders) {
        GetPhysicalDescriptionLayout getPhysicialDescriptionLayout = new GetPhysicalDescriptionLayout();
        getPhysicialDescriptionLayout.setValues(values);
        getPhysicialDescriptionLayout.setHolders(holders);
        return getSomeStack(expanded, "Physicial Description", getPhysicialDescriptionLayout);
    }

    /**
     * Gets the abstract stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the abstract stack
     */
    public static SectionStackSection getAbstractStack(boolean expanded,
                                                       List<AbstractTypeClient> values,
                                                       List<AbstractHolder> holders) {
        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.ABSTRACT,
                                                   "Abstract",
                                                   lang.abstractAndTableMARC()), new Attribute[] {
                                             new Attribute(TextItem.class,
                                                           ModsConstants.TYPE,
                                                           "Type",
                                                           lang.conTypeList()),
                                             getDisplayLabel(lang.textAsocAbstract(), ""), ATTR_XLINK(""),
                                             ATTR_LANG(""), ATTR_XML_LANG(""), ATTR_SCRIPT(""),
                                             ATTR_TRANSLITERATION("")}, GetGeneralLayout.ABSTRACT);
        getGeneralLayout.setValues(getValues(values, ModsConstants.ABSTRACT));
        getGeneralLayout.setHolders(holders);
        return getSomeStack(expanded, "Abstract", getGeneralLayout);
    }

    /**
     * Gets the table of contents stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the table of contents stack
     */
    public static SectionStackSection getTableOfContentsStack(boolean expanded,
                                                              List<TableOfContentsTypeClient> values,
                                                              List<TableOfContentsHolder> holders) {
        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.TOC,
                                                   "Table of Contents",
                                                   lang.abstractAndTableMARC()), new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.conTypeList()),
                        getDisplayLabel(lang.textAsocTable(), ""), ATTR_XLINK(""), ATTR_LANG(""),
                        ATTR_XML_LANG(""), ATTR_SCRIPT(""), ATTR_TRANSLITERATION("")}, GetGeneralLayout.TOC);
        getGeneralLayout.setValues(getValues(values, ModsConstants.TOC));
        getGeneralLayout.setHolders(holders);
        return getSomeStack(expanded, "Table of Contents", getGeneralLayout);
    }

    /**
     * Gets the target audience stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the target audience stack
     */
    public static SectionStackSection getTargetAudienceStack(boolean expanded,
                                                             List<TargetAudienceTypeClient> values,
                                                             AudienceHolder holder) {
        Attribute[] attributes =
                new Attribute[] {ATTR_AUTHORITY(""), ATTR_LANG(""), ATTR_XML_LANG(""),
                        ATTR_TRANSLITERATION(""), ATTR_SCRIPT("")};
        List<List<String>> vals = null;
        if (values != null && values.size() > 0) {
            vals = new ArrayList<List<String>>();
            for (TargetAudienceTypeClient audience : values) {
                if (audience != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(audience.getValue());
                    list.add(audience.getAuthority());
                    list.add(audience.getLang());
                    list.add(audience.getXmlLang());
                    list.add(audience.getTransliteration());
                    list.add(audience.getScript());
                    vals.add(list);
                }
            }
        }
        return getStackSectionWithAttributes("Target Audience",
                                             "Target Audience",
                                             lang.targetAudience(),
                                             true,
                                             attributes,
                                             vals,
                                             holder,
                                             false);
    }

    /**
     * Gets the note stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the note stack
     */
    public static SectionStackSection getNoteStack(boolean expanded,
                                                   List<NoteTypeClient> values,
                                                   List<NoteHolder> holders) {
        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.NOTE,
                                                   "Note",
                                                   lang.typeMARC()), new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.conTypeList()),
                        getDisplayLabel(lang.textAsocNote(), ""), ATTR_XLINK(""), ATTR_ID(""), ATTR_LANG(""),
                        ATTR_XML_LANG(""), ATTR_SCRIPT(""), ATTR_TRANSLITERATION("")}, GetGeneralLayout.NOTE);

        List<Map<String, String>> valueList = null;
        if (values != null && values.size() > 0) {
            valueList = new ArrayList<Map<String, String>>();
            Map<String, String> valueMap = new HashMap<String, String>();
            for (NoteTypeClient value : values) {
                if (value != null) {
                    if (value.getAtType() != null && !"".equals(value.getAtType()))
                        valueMap.put(ModsConstants.TYPE, value.getAtType());
                    if (value.getDisplayLabel() != null && !"".equals(value.getDisplayLabel()))
                        valueMap.put(ModsConstants.DISPLAY_LABEL, value.getDisplayLabel());
                    if (value.getLang() != null && !"".equals(value.getLang()))
                        valueMap.put(ModsConstants.LANG, value.getLang());
                    if (value.getXmlLang() != null && !"".equals(value.getXmlLang()))
                        valueMap.put(ModsConstants.XML_LANG, value.getXmlLang());
                    if (value.getXlink() != null && !"".equals(value.getXlink()))
                        valueMap.put(ModsConstants.XLINK, value.getXlink());
                    if (value.getScript() != null && !"".equals(value.getScript()))
                        valueMap.put(ModsConstants.SCRIPT, value.getScript());
                    if (value.getTransliteration() != null && !"".equals(value.getTransliteration()))
                        valueMap.put(ModsConstants.TRANSLITERATION, value.getTransliteration());
                    if (value.getID() != null && !"".equals(value.getID()))
                        valueMap.put(ModsConstants.ID, value.getID());
                    if (value.getValue() != null && !"".equals(value.getValue()))
                        valueMap.put(ModsConstants.NOTE, value.getValue());
                }
                valueList.add(valueMap);
            }
        }
        getGeneralLayout.setValues(valueList);
        getGeneralLayout.setHolders(holders);

        return getSomeStack(expanded, "Note", getGeneralLayout);
    }

    /**
     * Gets the subject stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the subject stack
     */
    public static SectionStackSection getSubjectStack(boolean expanded,
                                                      List<SubjectTypeClient> values,
                                                      List<SubjectHolder> holders) {
        GetSubjectLayout getSubjectLayout = new GetSubjectLayout();
        getSubjectLayout.setValues(values);
        getSubjectLayout.setHolders(holders);
        return getSomeStack(expanded, "Subject", getSubjectLayout);
    }

    /**
     * Gets the classification stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the classification stack
     */
    public static SectionStackSection getClassificationStack(boolean expanded,
                                                             List<ClassificationTypeClient> values,
                                                             ClassificationHolder holder) {
        Attribute[] attributes =
                new Attribute[] {ATTR_AUTHORITY(""),
                        new Attribute(TextItem.class, ModsConstants.EDITION, "Edition", lang.editionAttr()),
                        getDisplayLabel(lang.editionMARC(), ""), ATTR_LANG(""), ATTR_XML_LANG(""),
                        ATTR_TRANSLITERATION(""), ATTR_SCRIPT("")};
        List<List<String>> vals = null;
        if (values != null && values.size() > 0) {
            vals = new ArrayList<List<String>>();
            for (ClassificationTypeClient classification : values) {
                if (classification != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(classification.getValue());
                    list.add(classification.getAuthority());
                    list.add(classification.getEdition());
                    list.add(classification.getDisplayLabel());
                    list.add(classification.getLang());
                    list.add(classification.getXmlLang());
                    list.add(classification.getTransliteration());
                    list.add(classification.getScript());
                    vals.add(list);
                }
            }
        }
        return getStackSectionWithAttributes("Classifications",
                                             "Classification",
                                             lang.classAndTypeMARC(),
                                             true,
                                             attributes,
                                             vals,
                                             holder,
                                             false);
    }

    /**
     * Gets the identifier stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the identifier stack
     */
    public static SectionStackSection getIdentifierStack(boolean expanded,
                                                         List<IdentifierTypeClient> values,
                                                         IdentifierHolder holder) {
        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.typeSuggestions()),
                        getDisplayLabel(lang.classAndTypeMARC(), ""), ATTR_LANG(""), ATTR_XML_LANG(""),
                        ATTR_TRANSLITERATION(""), ATTR_SCRIPT(""),
                        new Attribute(CheckboxItem.class, ModsConstants.INVALID, "Invalid", lang.invalid())};

        List<List<String>> vals = null;
        if (values != null && values.size() > 0) {
            vals = new ArrayList<List<String>>();
            for (IdentifierTypeClient value : values) {
                if (value != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(value.getValue());
                    list.add(value.getType());
                    list.add(value.getDisplayLabel());
                    list.add(value.getLang());
                    list.add(value.getXmlLang());
                    list.add(value.getTransliteration());
                    list.add(value.getScript());
                    list.add(value.getInvalid() == null ? null : value.getInvalid().value());
                    vals.add(list);
                }
            }
        }
        return getStackSectionWithAttributes("Identifiers",
                                             "Identifier",
                                             lang.identifierMARC(),
                                             true,
                                             attributes,
                                             vals,
                                             holder,
                                             false);
    }

    /**
     * Gets the location stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the location stack
     */
    public static SectionStackSection getLocationStack(boolean expanded,
                                                       List<LocationTypeClient> values,
                                                       List<LocationHolder> holders) {
        GetLocationLayout getLocationLayout = new GetLocationLayout();
        getLocationLayout.setHolders(holders);
        getLocationLayout.setValues(values);
        return getSomeStack(expanded, "Location", getLocationLayout);
    }

    /**
     * Gets the access condition stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the access condition stack
     */
    public static SectionStackSection getAccessConditionStack(boolean expanded,
                                                              List<AccessConditionTypeClient> values,
                                                              List<AccessConditionHolder> holders) {

        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.ACCESS_CONDITION,
                                                   "Access Condition",
                                                   lang.accessConMARC()), new Attribute[] {
                                             new Attribute(TextItem.class,
                                                           ModsConstants.TYPE,
                                                           "Type",
                                                           lang.typeSuggesRestr()),
                                             getDisplayLabel(lang.textAsocCon(), ""), ATTR_XLINK(""),
                                             ATTR_LANG(""), ATTR_XML_LANG(""), ATTR_SCRIPT(""),
                                             ATTR_TRANSLITERATION("")}, GetGeneralLayout.ACCESS_CONDITION);
        getGeneralLayout.setValues(values);
        getGeneralLayout.setHolders(holders);

        return getSomeStack(expanded, "Access Condition", getGeneralLayout);
    }

    /**
     * Gets the extension stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the extension stack
     */
    public static SectionStackSection getExtensionStack(boolean expanded,
                                                        List<ExtensionTypeClient> values,
                                                        List<ExtensionHolder> holders) {
        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.EXTENSION,
                                                   "Extension",
                                                   lang.extension()),
                                     new Attribute[] {new Attribute(TextItem.class,
                                                                    ModsConstants.NAMESPACE,
                                                                    "Namespace",
                                                                    "Namespace")},
                                     GetGeneralLayout.EXTENSION);
        getGeneralLayout.setValues(values);
        getGeneralLayout.setHolders(holders);
        return getSomeStack(expanded, "Access Condition", getGeneralLayout);
    }

    /**
     * Gets the record info stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the record info stack
     */
    public static SectionStackSection getRecordInfoStack(boolean expanded,
                                                         List<RecordInfoTypeClient> values,
                                                         List<RecordInfoHolder> holders) {
        GetRecordInfoLayout getRecordInfoLayout = new GetRecordInfoLayout();
        getRecordInfoLayout.setValues(values);
        getRecordInfoLayout.setHolders(holders);
        return getSomeStack(expanded, "Record Info", getRecordInfoLayout);
    }

    /**
     * Gets the part stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the part stack
     */
    public static SectionStackSection getPartStack(boolean expanded,
                                                   List<PartTypeClient> values,
                                                   List<PartHolder> holders) {
        GetPartLayout getPartLayout = new GetPartLayout();
        getPartLayout.setValues(values);
        getPartLayout.setHolders(holders);
        return getSomeStack(expanded, "Part", getPartLayout);
    }

    /**
     * Gets the place stack.
     * 
     * @param expanded
     *        the expanded
     * @param values
     *        the values
     * @param holders
     *        the holders
     * @return the place stack
     */
    private static SectionStackSection getPlaceStack(boolean expanded,
                                                     List<PlaceTypeClient> values,
                                                     List<PlaceHolder> holders) {
        GetPlaceLayout getPlaceLayout = new GetPlaceLayout();
        getPlaceLayout.setHolders(holders);
        getPlaceLayout.setValues(values);
        return getSomeStack(expanded, "Place", getPlaceLayout);
    }

    /**
     * Gets the title info layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the title info layout
     */
    public static VLayout getTitleInfoLayout(final TitleInfoTypeClient values, final TitleInfoHolder holder) {
        final VLayout layout = new VLayout();

        final Map<String, String> tooltips = new HashMap<String, String>();
        tooltips.put("", lang.attributeOmitted());
        tooltips.put("abbreviated", lang.abbreviatedMARC());
        tooltips.put("translated", lang.translatedMARC());
        tooltips.put("alternative", lang.alternativeMARC());
        tooltips.put("uniform", lang.uniformMARC());

        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(SelectItem.class,
                                      ModsConstants.TYPE,
                                      "Type",
                                      tooltips,
                                      values == null ? "" : values.getType()),
                        ATTR_LANG(values == null ? "" : values.getLang()),
                        getDisplayLabel(lang.textAsocTitle(), values == null ? "" : values.getDisplayLabel()),
                        ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                        ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
                        ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()),
                        ATTR_SCRIPT(values == null ? "" : values.getScript()),
                        ATTR_ID(values == null ? "" : values.getId()),
                        ATTR_XLINK(values == null ? "" : values.getXlink())};
        DynamicForm form = getAttributes(true, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        layout.setLeaveScrollbarGap(true);
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);

        sectionStack.addSection(getStackSection("Titles",
                                                "Title",
                                                lang.titleMARC(),
                                                true,
                                                values == null ? null : values.getTitle(),
                                                holder.getTitles()));
        sectionStack.addSection(getStackSection("Sub Titles",
                                                "Sub Title",
                                                lang.subTitleMARC(),
                                                false,
                                                values == null ? null : values.getSubTitle(),
                                                holder.getSubTitles()));
        sectionStack.addSection(getStackSection("Part Numbers",
                                                "Part Number",
                                                lang.partNumMARC(),
                                                false,
                                                values == null ? null : values.getPartNumber(),
                                                holder.getPartNumbers()));
        sectionStack.addSection(getStackSection("Part Names",
                                                "Part Name",
                                                lang.partNameMARC(),
                                                false,
                                                values == null ? null : values.getPartName(),
                                                holder.getPartNames()));
        sectionStack.addSection(getStackSection("Non Sort",
                                                "Non Sort",
                                                lang.nonSortMARC(),
                                                false,
                                                values == null ? null : values.getNonSort(),
                                                holder.getNonSorts()));
        layout.addMember(sectionStack);

        return layout;
    }

    /**
     * Gets the name layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the name layout
     */
    public static VLayout getNameLayout(final NameTypeClient values, final NameHolder holder) {
        final VLayout layout = new VLayout();

        Map<String, String> tooltips = new HashMap<String, String>();
        tooltips.put("", lang.attributeOmitted());
        tooltips.put("personal", lang.personalMARC());
        tooltips.put("corporate", lang.corporateMARC());
        tooltips.put("conference", lang.conferenceMARC());
        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", tooltips, values == null
                                || values.getType() == null ? "" : values.getType().value()),
                        ATTR_LANG(values == null ? "" : values.getLang()),
                        ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
                        ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                        ATTR_SCRIPT(values == null ? "" : values.getScript()),
                        ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()),
                        ATTR_XLINK(values == null ? "" : values.getXlink()),
                        ATTR_ID(values == null ? "" : values.getId())};
        DynamicForm form = getAttributes(true, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        sectionStack.setWidth100();

        tooltips = new HashMap<String, String>();
        tooltips.put("", lang.attributeOmitted());
        tooltips.put("date", lang.dateParse());
        tooltips.put("family", lang.surname());
        tooltips.put("given ", lang.firstName());
        tooltips.put("termsOfAddress ", lang.addressMARC());

        List<List<String>> vals = null;
        if (values != null && values.getNamePart() != null && values.getNamePart().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (NamePartTypeClient namePart : values.getNamePart()) {
                if (namePart != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(namePart.getValue());
                    list.add(namePart.getType());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Name Parts",
                                                          "Name Part",
                                                          lang.nameParse(),
                                                          false,
                                                          new Attribute[] {new Attribute(SelectItem.class,
                                                                                         ModsConstants.TYPE,
                                                                                         "Type",
                                                                                         tooltips)},
                                                          vals,
                                                          holder.getNameParts(),
                                                          false));
        sectionStack.addSection(getStackSection("Display Forms",
                                                "Display Form",
                                                lang.displayFormMARC(),
                                                false,
                                                values == null ? null : values.getDisplayForm(),
                                                holder.getDisplayForms()));
        sectionStack.addSection(getStackSection("Affiliations",
                                                "Affiliation",
                                                lang.affiliation(),
                                                false,
                                                values == null ? null : values.getAffiliation(),
                                                holder.getAffiliations()));
        if (values != null && values.getRole() != null && values.getRole().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (RoleTypeClient roleTypeClient : values.getRole()) {
                if (roleTypeClient != null) {

                    for (RoleTermClient term : roleTypeClient.getRoleTerm()) {
                        if (term != null) {
                            List<String> list = new ArrayList<String>();
                            list.add(term.getValue());
                            list.add(term.getType() == null ? null : term.getType().value());
                            list.add(term.getAuthority());
                            vals.add(list);
                        }
                    }
                }
            }
        }
        sectionStack.addSection(getStackSectionWithAttributes("Roles", "Role", "", false, new Attribute[] {
                ATTR_TYPE_TEXT_CODE, ATTR_AUTHORITY("")}, vals, holder.getRoles(), false));
        sectionStack.addSection(getStackSection("Descriptions",
                                                "Description",
                                                lang.textDescription(),
                                                false,
                                                values == null ? null : values.getDescription(),
                                                holder.getDescriptions()));

        layout.addMember(sectionStack);

        return layout;
    }

    /**
     * Gets the type of resource layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the type of resource layout
     */
    public static VLayout getTypeOfResourceLayout(final TypeOfResourceTypeClient values,
                                                  final TypeOfResourceHolder holder) {
        final VLayout layout = new VLayout();

        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(CheckboxItem.class,
                                      ModsConstants.COLLECTION,
                                      "Collection",
                                      lang.collectionAttr(),
                                      values == null ? "" : values.getCollection() == null ? "" : values
                                              .getCollection().value()),
                        new Attribute(CheckboxItem.class,
                                      ModsConstants.MANUSCRIPT,
                                      "Manuscript",
                                      lang.manuscriptAttr(),
                                      values == null ? "" : values.getManuscript() == null ? "" : values
                                              .getManuscript().value())};
        DynamicForm form = getAttributes(true, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        layout.setExtraSpace(40);

        final Map<String, String> tooltips = new HashMap<String, String>();
        tooltips.put("", lang.nothing());
        tooltips.put("cartographic", lang.cartographicMARC());
        tooltips.put("notated music", lang.musicMARC());
        tooltips.put("sound recording", lang.musicMix());
        tooltips.put("sound recording-musical", lang.musicalMARC());
        tooltips.put("sound recording-nonmusical", lang.nonmusicalMARC());
        tooltips.put("still image", lang.sImageMARC());
        tooltips.put("moving image", lang.mImageMARC());
        tooltips.put("three dimensional object", lang.dimObjMARC());
        tooltips.put("software, multimedia", lang.swMultMARC());
        tooltips.put("mixed material", lang.mixMatMARC());
        form = new DynamicForm();
        form.setItems(newItem(new Attribute(SelectItem.class,
                                            ModsConstants.TYPE,
                                            "Type",
                                            tooltips,
                                            values == null ? "" : values.getValue())));
        layout.addMember(form);
        holder.setAttributeForm2(form);

        return layout;
    }

    /**
     * Gets the genre layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the genre layout
     */
    public static VLayout getGenreLayout(final GenreTypeClient values, final GenreHolder holder) {
        final VLayout layout = new VLayout();
        Attribute[] attributes =
                new Attribute[] {new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.typeDist()),
                        ATTR_LANG(values == null ? "" : values.getLang()),
                        ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
                        ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                        ATTR_SCRIPT(values == null ? "" : values.getScript()),
                        ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration())};
        DynamicForm form = getAttributes(true, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        layout.setExtraSpace(40);

        form = new DynamicForm();
        form.setItems(newItem(new Attribute(TextItem.class,
                                            ModsConstants.GENRE,
                                            "Genre",
                                            lang.genreAttr(),
                                            values == null ? "" : values.getValue())));
        layout.addMember(form);
        holder.setAttributeForm2(form);

        return layout;
    }

    /**
     * Gets the date layout.
     * 
     * @param name
     *        the name
     * @param title
     *        the title
     * @param tooltip
     *        the tooltip
     * @param attribute
     *        the attribute
     * @param keyDate
     *        the key date
     * @param value
     *        the value
     * @param holder
     *        the holder
     * @return the date layout
     */
    public static VLayout getDateLayout(String name,
                                        String title,
                                        String tooltip,
                                        Attribute attribute,
                                        boolean keyDate,
                                        BaseDateTypeClient value,
                                        DateHolder holder) {
        final VLayout layout = new VLayout();
        Attribute[] attributes = null;
        final Attribute encoding = ATTR_ENCODING(value == null ? "" : value.getEncoding());
        if (attribute != null) {
            attribute.setValue(value == null ? "" : value.getValue());
            if (keyDate) {
                String key =
                        value == null ? "" : ((DateTypeClient) value).getKeyDate() == null ? ""
                                : ((DateTypeClient) value).getKeyDate().value();
                attributes =
                        new Attribute[] {attribute, encoding,
                                ATTR_POINT(value == null ? "" : value.getPoint()), ATTR_KEY_DATE(key),
                                ATTR_QUALIFIER(value == null ? "" : value.getQualifier())};
            } else {
                attributes =
                        new Attribute[] {attribute, encoding,
                                ATTR_POINT(value == null ? "" : value.getPoint()),
                                ATTR_QUALIFIER(value == null ? "" : value.getQualifier())};
            }
        } else if (keyDate) {
            String key =
                    value == null ? "" : ((DateTypeClient) value).getKeyDate() == null ? ""
                            : ((DateTypeClient) value).getKeyDate().value();
            attributes =
                    new Attribute[] {encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
                            ATTR_KEY_DATE(key), ATTR_QUALIFIER(value == null ? "" : value.getQualifier())};
        } else {
            attributes =
                    new Attribute[] {encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
                            ATTR_QUALIFIER(value == null ? "" : value.getQualifier())};
        }

        final DynamicForm formAttr = getAttributes(true, attributes);

        FormItem item1 =
                newItem(new Attribute(TextItem.class, name, title, tooltip, value == null ? ""
                        : value.getValue()));
        FormItem item2 =
                newItem(new Attribute(DateItem.class, name, title, tooltip, value == null ? ""
                        : value.getValue()));

        FormItem[] items = formAttr.getFields();
        FormItem[] itemsToAdd = new FormItem[items.length + 2];
        for (int i = 0; i < items.length; i++) {
            itemsToAdd[i] = items[i];
            itemsToAdd[i].setRedrawOnChange(true);
        }
        itemsToAdd[items.length] = item1;
        itemsToAdd[items.length + 1] = item2;
        item1.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                if (encoding.getName() == null || "".equals(encoding.getName())) {
                    return false;
                }
                String encodingString = (String) form.getValue(encoding.getName());
                boolean returnVal = false;
                if (encodingString != null && !"".equals(encodingString)) {
                    returnVal = !W3C_ENCODING.equals(encodingString);
                }
                return returnVal;
            }
        });
        item2.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                if (encoding.getName() == null || "".equals(encoding.getName())) {
                    return true;
                }
                String encodingString = (String) form.getValue(encoding.getName());
                boolean returnVal = true;
                if (encodingString != null && !"".equals(encodingString)) {
                    returnVal = W3C_ENCODING.equals(encodingString);
                }
                return returnVal;
            }
        });
        holder.setAttributeForm(formAttr);
        formAttr.setItems(itemsToAdd);
        layout.addMember(formAttr);

        return layout;
    }

    /**
     * Gets the place layout.
     * 
     * @param value
     *        the value
     * @param holder
     *        the holder
     * @return the place layout
     */
    private static VLayout getPlaceLayout(PlaceTypeClient value, PlaceHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        // sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setStyleName("metadata-sectionstack-topmargin");
        List<List<String>> vals = null;
        if (value != null && value.getPlaceTerm() != null && value.getPlaceTerm().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (PlaceTermTypeClient placeTermClient : value.getPlaceTerm()) {
                if (placeTermClient != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(placeTermClient.getValue());
                    list.add(placeTermClient.getType() == null ? null : placeTermClient.getType().value());
                    list.add(placeTermClient.getAuthority() == null ? null : placeTermClient.getAuthority()
                            .value());
                    vals.add(list);
                }
            }
        }
        sectionStack.addSection(getStackSectionWithAttributes("Place Terms",
                                                              "Place Term",
                                                              lang.placeTerm(),
                                                              true,
                                                              new Attribute[] {ATTR_TYPE_TEXT_CODE,
                                                                      ATTR_AUTHORITY("")},
                                                              vals,
                                                              holder,
                                                              false));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the origin info layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the origin info layout
     */
    public static VLayout getOriginInfoLayout(OriginInfoTypeClient values, OriginInfoHolder holder) {
        final VLayout layout = new VLayout();

        Attribute[] attributes =
                new Attribute[] {ATTR_LANG(values == null ? "" : values.getLang()),
                        ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                        ATTR_SCRIPT(values == null ? "" : values.getScript()),
                        ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration())};
        DynamicForm formAttr = getAttributes(true, attributes);
        holder.setAttributeForm(formAttr);
        layout.addMember(formAttr);

        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        sectionStack.addSection(getPlaceStack(false,
                                              values == null ? null : values.getPlace(),
                                              holder.getPlaces()));
        sectionStack.addSection(getStackSection("Publishers",
                                                "Publisher",
                                                lang.publisherMARC(),
                                                false,
                                                values == null ? null : values.getPublisher(),
                                                holder.getPublishers()));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Issued",
                                             new GetDateLayout(ModsConstants.DATE_ISSUED, "Issued Date", lang
                                                     .issuedDate(), values == null ? null : values
                                                     .getDateIssued(), holder.getDatesIssued())));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Created",
                                             new GetDateLayout(ModsConstants.DATE_CREATED,
                                                               "Created Date",
                                                               lang.createdDate(),
                                                               values == null ? null : values
                                                                       .getDateCreated(), holder
                                                                       .getDatesCreated())));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Captured",
                                             new GetDateLayout(ModsConstants.DATE_CAPTURED,
                                                               "Captured Date",
                                                               lang.capturedDate(),
                                                               values == null ? null : values
                                                                       .getDateCaptured(), holder
                                                                       .getDatesCaptured())));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Valid",
                                             new GetDateLayout(ModsConstants.DATE_VALID, " Valid Date", lang
                                                     .dateValidMARC(), values == null ? null : values
                                                     .getDateValid(), holder.getDatesValid())));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Modified",
                                             new GetDateLayout(ModsConstants.DATE_MODIFIED,
                                                               "Modified Date",
                                                               lang.datesModifiedMARC(),
                                                               values == null ? null : values
                                                                       .getDateModified(), holder
                                                                       .getDatesModified())));
        sectionStack.addSection(getSomeStack(false,
                                             "Dates Copyright",
                                             new GetDateLayout(ModsConstants.DATE_COPYRIGHT,
                                                               "Copyright Date",
                                                               lang.copyrightDate(),
                                                               values == null ? null : values
                                                                       .getCopyrightDate(), holder
                                                                       .getDatesCopyright())));
        sectionStack.addSection(getSomeStack(false,
                                             "Other Dates",
                                             new GetDateLayout(ModsConstants.DATE_OTHER,
                                                               "Other Date",
                                                               lang.otherDateMARC(),
                                                               new Attribute(TextItem.class,
                                                                             ModsConstants.TYPE,
                                                                             "Type",
                                                                             lang.otherDateType()),
                                                               true,
                                                               values == null ? null : values.getDateOther(),
                                                               holder.getDatesOther(),
                                                               true)));
        sectionStack.addSection(getStackSection("Editions", "Edition", lang.editionMARC(), false));

        // getStackSectionWithAttributes(final String label1, final String label2,
        // final String tooltip, final boolean expanded,
        // final Attribute[] attributes, final List<List<String>> values,
        // MetadataHolder holder, boolean flat)

        sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class,
                                                                             ModsConstants.ISSUANCE,
                                                                             "Issuance",
                                                                             new HashMap<String, String>() {

                                                                                 {
                                                                                     put("",
                                                                                         lang.elementOmitted());
                                                                                     put("continuing",
                                                                                         lang.continuingMARC());
                                                                                     put("monographic",
                                                                                         lang.monographicMARC());
                                                                                 }
                                                                             },
                                                                             values == null ? null
                                                                                     : values.getIssuance() == null ? null
                                                                                             : values.getIssuance()
                                                                                                     .get(0)),
                                                               false,
                                                               null,
                                                               holder.getIssuances()));
        sectionStack.addSection(getStackSectionWithAttributes("Frequencies",
                                                              "Frequency",
                                                              lang.frequency(),
                                                              false,
                                                              new Attribute[] {ATTR_AUTHORITY("")},
                                                              values == null ? null : ClientUtils
                                                                      .toListOfListOfStrings(values
                                                                              .getFrequency()),
                                                              holder.getFrequencies(),
                                                              false));

        layout.addMember(sectionStack);

        return layout;
    }

    /**
     * Gets the physicial description layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the physicial description layout
     */
    private static VLayout getPhysicialDescriptionLayout(PhysicalDescriptionTypeClient values,
                                                         PhysicalDescriptionHolder holder) {

        final VLayout layout = new VLayout();

        Attribute[] attributes =
                new Attribute[] {ATTR_LANG(values == null ? null : values.getLang()),
                        ATTR_XML_LANG(values == null ? null : values.getXmlLang()),
                        ATTR_TRANSLITERATION(values == null ? null : values.getTransliteration()),
                        ATTR_SCRIPT(values == null ? null : values.getScript())};
        DynamicForm formAttr = getAttributes(true, attributes);
        holder.setAttributeForm(formAttr);
        layout.addMember(formAttr);
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);

        attributes =
                new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.physicalType()),
                        ATTR_AUTHORITY("")};
        sectionStack.addSection(getStackSectionWithAttributes("Forms",
                                                              "Form",
                                                              lang.physicalForm(),
                                                              false,
                                                              attributes,
                                                              values == null ? null
                                                                      : ClientUtils
                                                                              .toListOfListOfStrings(values
                                                                                      .getForm(), false),
                                                              holder.getForms(),
                                                              false));
        sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class,
                                                                             ModsConstants.REFORMATTING,
                                                                             "Reformatting Quality",
                                                                             new HashMap<String, String>() {

                                                                                 {
                                                                                     put("",
                                                                                         lang.elementOmitted());
                                                                                     put("access",
                                                                                         lang.access());
                                                                                     put("preservation",
                                                                                         lang.preservation());
                                                                                     put("replacement",
                                                                                         lang.replacement());
                                                                                 }
                                                                             },
                                                                             values == null
                                                                                     || values
                                                                                             .getReformattingQuality() == null
                                                                                     || values
                                                                                             .getReformattingQuality()
                                                                                             .size() == 0 ? ""
                                                                                     : values.getReformattingQuality()
                                                                                             .get(0)),
                                                               false,
                                                               null,
                                                               holder.getReformattingQuality()));
        sectionStack.addSection(getStackSection("Internet Media Types",
                                                "Internet Media Type",
                                                lang.intMedTypeMARC(),
                                                false,
                                                values == null ? null : values.getInternetMediaType(),
                                                holder.getInternetTypes()));
        sectionStack.addSection(getStackSection("Extents",
                                                "Extent",
                                                lang.extentMARC(),
                                                false,
                                                values == null ? null : values.getExtent(),
                                                holder.getExtents()));
        sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class,
                                                                             ModsConstants.DIGITAL_ORIGIN,
                                                                             "Digital Origin",
                                                                             new HashMap<String, String>() {

                                                                                 {
                                                                                     put("",
                                                                                         lang.elementOmitted());
                                                                                     put("born digital",
                                                                                         lang.bornDigital());
                                                                                     put("reformatted digital",
                                                                                         lang.rDigital());
                                                                                     put("digitized microfilm",
                                                                                         lang.microfilm());
                                                                                     put("digitized other analog",
                                                                                         lang.analog());
                                                                                 }
                                                                             },
                                                                             values == null
                                                                                     || values
                                                                                             .getDigitalOrigin() == null
                                                                                     || values
                                                                                             .getDigitalOrigin()
                                                                                             .size() == 0 ? ""
                                                                                     : values.getDigitalOrigin()
                                                                                             .get(0)),
                                                               false,
                                                               null,
                                                               holder.getDigitalOrigin()));
        attributes =
                new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.typeMARC340()),
                        getDisplayLabel(lang.textAsocNote(), ""), ATTR_XLINK(""), ATTR_LANG(""),
                        ATTR_XML_LANG(""), ATTR_SCRIPT(""), ATTR_TRANSLITERATION("")};

        List<List<String>> vals = null;
        if (values != null && values.getNote() != null && values.getNote().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (NoteTypeClient noteClient : values.getNote()) {
                if (noteClient != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(noteClient.getValue());
                    list.add(noteClient.getAtType());
                    list.add(noteClient.getDisplayLabel());
                    list.add(noteClient.getXlink());
                    list.add(noteClient.getLang());
                    list.add(noteClient.getXmlLang());
                    list.add(noteClient.getScript());
                    list.add(noteClient.getTransliteration());
                    vals.add(list);
                }
            }
        }
        sectionStack.addSection(getStackSectionWithAttributes("Notes",
                                                              new Attribute(TextAreaItem.class,
                                                                            ModsConstants.NOTE,
                                                                            "Note",
                                                                            lang.physicalForm()),
                                                              false,
                                                              attributes,
                                                              vals,
                                                              holder.getNotes(),
                                                              false));

        layout.addMember(sectionStack);

        return layout;
    }

    /**
     * Gets the general layout.
     * 
     * @param head
     *        the head
     * @param attribs
     *        the attribs
     * @param holder
     *        the holder
     * @return the general layout
     */
    private static VLayout getGeneralLayout(final Attribute head,
                                            final Attribute[] attribs,
                                            final ListOfSimpleValuesHolder holder) {
        final VLayout layout = new VLayout();
        DynamicForm form1 = getAttributes(true, attribs);

        holder.setAttributeForm(form1);
        DynamicForm form = new DynamicForm();
        holder.setAttributeForm2(form);

        FormItem item = newItem(head);
        if (head.getType() == TextAreaItem.class)
            item.setWidth(600);
        else {
            item.setWidth(200);
        }
        form.setItems(item);
        layout.addMember(form);
        layout.addMember(form1);
        layout.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        return layout;
    }

    /**
     * Gets the general deep layout.
     * 
     * @param attribs
     *        the attribs
     * @return the general deep layout
     */
    private static VLayout getGeneralDeepLayout(final Attribute[] attribs) {
        final VLayout layout = new VLayout();
        layout.setExtraSpace(40);
        if (attribs != null || attribs.length > 0) {
            boolean first = true;
            for (Attribute attr : attribs) {
                final SectionStack sectionStack = new SectionStack();
                sectionStack.setOverflow(Overflow.VISIBLE);
                sectionStack.setLeaveScrollbarGap(true);
                sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
                sectionStack.setStyleName("metadata-elements");
                sectionStack.setWidth100();
                sectionStack.addSection(getStackSection(attr, first, null));
                layout.addMember(sectionStack);
                if (first) first = false;
            }
        }
        layout.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        return layout;
    }

    /**
     * Gets the language layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the language layout
     */
    private static VLayout getLanguageLayout(LanguageTypeClient values, LanguageHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        DynamicForm formAttr =
                getAttributes(true,
                              new Attribute[] {new Attribute(TextItem.class,
                                                             ModsConstants.OBJECT_PART,
                                                             "Object Part",
                                                             lang.objectPart(),
                                                             values == null ? "" : values.getObjectPart())});
        holder.setAttributeForm(formAttr);
        layout.addMember(formAttr);

        layout.addMember(formAttr);
        List<List<String>> vals = null;
        if (values != null && values.getLanguageTerm() != null && values.getLanguageTerm().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (LanguageTermClient languageTermClient : values.getLanguageTerm()) {
                if (languageTermClient != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(languageTermClient.getValue());
                    list.add(languageTermClient.getType() == null ? null : languageTermClient.getType()
                            .value());
                    list.add(languageTermClient.getAuthority());
                    vals.add(list);
                }
            }
        }
        sectionStack.addSection(getStackSectionWithAttributes("Language Terms",
                                                              "Language Term",
                                                              lang.langTerm(),
                                                              true,
                                                              new Attribute[] {ATTR_TYPE_TEXT_CODE,
                                                                      ATTR_LANG_AUTHORITY},
                                                              vals,
                                                              holder.getLangTerms(),
                                                              false));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the copy information layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the copy information layout
     */
    private static VLayout getCopyInformationLayout(CopyInformationTypeClient values,
                                                    CopyInformationHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack
                .addSection(getSimpleSectionWithAttributes(new Attribute(TextItem.class, "form", "Form", lang
                                                                   .copyInfForm(), values == null
                                                                   || values.getForm() == null ? "" : values
                                                                   .getForm().getValue()),
                                                           false,
                                                           new Attribute[] {ATTR_AUTHORITY(values == null
                                                                   || values.getForm() == null ? "" : values
                                                                   .getForm().getAuthority())},
                                                           holder.getForm()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Sublocations",
                                                              "Sublocation",
                                                              lang.sublocation()),
                                                false,
                                                values == null ? null : values.getSubLocation(),
                                                holder.getSubLocations()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Shelf Locators",
                                                              "Shelf Locator",
                                                              lang.shelfLocator()),
                                                false,
                                                values == null ? null : values.getShelfLocator(),
                                                holder.getShelfLocators()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Electronic Locators",
                                                              "Electronic Locator",
                                                              lang.elecLocator()),
                                                false,
                                                values == null ? null : values.getElectronicLocator(),
                                                holder.getElectronicLocators()));
        GetGeneralLayout getGeneralLayout =
                new GetGeneralLayout(new Attribute(TextAreaItem.class,
                                                   ModsConstants.NOTE,
                                                   "Note",
                                                   lang.copyInfNote()), new Attribute[] {
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.copyInfType()),
                        getDisplayLabel(lang.textAsocNote(), "")}, GetGeneralLayout.NOTE);
        List<Map<String, String>> valueList = null;
        if (values != null && values.getNote() != null && values.getNote().size() > 0) {
            valueList = new ArrayList<Map<String, String>>();
            Map<String, String> valueMap = new HashMap<String, String>();
            for (StringPlusDisplayLabelPlusTypeClient value : values.getNote()) {
                if (value != null) {
                    if (value.getAtType() != null && !"".equals(value.getAtType()))
                        valueMap.put(ModsConstants.TYPE, value.getAtType());
                    if (value.getDisplayLabel() != null && !"".equals(value.getDisplayLabel()))
                        valueMap.put(ModsConstants.DISPLAY_LABEL, value.getDisplayLabel());
                    if (value.getValue() != null && !"".equals(value.getValue()))
                        valueMap.put(ModsConstants.NOTE, value.getValue());
                }
                valueList.add(valueMap);
            }
        }

        getGeneralLayout.setValues(valueList);
        getGeneralLayout.setHolders(holder.getNotes());
        sectionStack.addSection(getSomeStack(false, "Note", getGeneralLayout));
        List<List<String>> vals = null;
        if (values != null && values.getEnumerationAndChronology() != null
                && values.getEnumerationAndChronology().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (EnumerationAndChronologyTypeClient val : values.getEnumerationAndChronology()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getUnitType());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Enumeration and Chronology",
                                                          new Attribute(TextItem.class,
                                                                        ModsConstants.ENUM_CHRONO,
                                                                        "Enumeration and Chronology",
                                                                        lang.enumAndChron()),
                                                          false,
                                                          new Attribute[] {new Attribute(SelectItem.class,
                                                                                         ModsConstants.UNIT_TYPE,
                                                                                         "Unit Type",
                                                                                         new HashMap<String, String>() {

                                                                                             {
                                                                                                 put("",
                                                                                                     lang.attributeOmitted());
                                                                                                 put("1",
                                                                                                     lang.inf1());
                                                                                                 put("2",
                                                                                                     lang.inf2());
                                                                                                 put("3",
                                                                                                     lang.inf3());
                                                                                             }
                                                                                         })},
                                                          vals,
                                                          holder.getEnumChrono(),
                                                          false));
        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the subject layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the subject layout
     */
    private static VLayout getSubjectLayout(SubjectTypeClient values, SubjectHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        layout.setExtraSpace(40);
        DynamicForm form =
                getAttributes(true,
                              new Attribute[] {ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
                                      ATTR_XLINK(values == null ? "" : values.getXlink()),
                                      ATTR_ID(values == null ? "" : values.getId()),
                                      ATTR_LANG(values == null ? "" : values.getLang()),
                                      ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                                      ATTR_SCRIPT(values == null ? "" : values.getScript()),
                                      ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration())});
        layout.addMember(form);
        holder.setAttributeForm(form);

        sectionStack
                .addSection(getStackSection(new Attribute(TextItem.class, "Topics", "Topic", lang.topic()),
                                            false,
                                            values == null ? null : values.getTopic(),
                                            holder.getTopics()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Geographics",
                                                              "Geographic",
                                                              lang.geographicMARC()),
                                                false,
                                                values == null ? null : values.getGeographic(),
                                                holder.getGeographics()));
        sectionStack.addSection(getSomeStack(false,
                                             "Temporal",
                                             new GetDateLayout("temporal",
                                                               "Temporal",
                                                               lang.temporalMARC(),
                                                               values == null ? null : values.getTemporal(),
                                                               holder.getTemporals())));
        GetTitleInfoLayout getTitleInfoLayout = new GetTitleInfoLayout();
        getTitleInfoLayout.setHolders(holder.getTitleInfo());
        getTitleInfoLayout.setValues(values == null ? null : values.getTitleInfo());
        sectionStack.addSection(getSomeStack(false, "Title Info", getTitleInfoLayout));
        GetNameLayout getNameLayout = new GetNameLayout();
        getNameLayout.setHolders(holder.getNames());
        getNameLayout.setValues(values == null ? null : values.getName());
        sectionStack.addSection(getSomeStack(false, "Name", getNameLayout));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "Genres", "Genre", lang
                                                        .genreMARC()),
                                                false,
                                                values == null ? null : values.getGenre(),
                                                holder.getGenres()));

        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(TextItem.class, "continent", "Continent", lang.continent()),
                        new Attribute(TextItem.class, "country", "Country", lang.country()),
                        new Attribute(TextItem.class, "province", "Province", lang.province()),
                        new Attribute(TextItem.class, "region", "Region", lang.region()),
                        new Attribute(TextItem.class, "state", "State", lang.state()),
                        new Attribute(TextItem.class, "territory", "Territory", lang.territory()),
                        new Attribute(TextItem.class, "county", "County", lang.county()),
                        new Attribute(TextItem.class, "city", "City", lang.city()),
                        new Attribute(TextItem.class, "city_section", "City Section", lang.citySection()),
                        new Attribute(TextItem.class, "island", "Island", lang.island()),
                        new Attribute(TextItem.class, "area", "Area", lang.area()),
                        new Attribute(TextItem.class,
                                      "extraterrestrial_area",
                                      "Extraterrestrial Area",
                                      lang.extraArea()),};

        // TODO: values&holders pass
        sectionStack.addSection(getSomeStack(false,
                                             "Hierarchical Geographic",
                                             new GetGeneralDeepLayout(attributes)));

        attributes =
                new Attribute[] {
                        new Attribute(TextItem.class, "coordinates", "Coordinates", lang.coordinates()),
                        new Attribute(TextItem.class, "scale", "Scale", lang.scale()),
                        new Attribute(TextItem.class, "projection", "Projection", lang.projection())};
        // TODO: values&holders pass
        sectionStack.addSection(getSomeStack(false, "Cartographics", new GetGeneralDeepLayout(attributes)));

        List<List<String>> vals = null;
        if (values != null && values.getGeographicCode() != null && values.getGeographicCode().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (GeographicCodeClient code : values.getGeographicCode()) {
                if (code != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(code.getValue());
                    list.add(code.getAuthority() == null ? "" : code.getAuthority().value());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Geographic Code",
                                                          new Attribute(TextItem.class,
                                                                        ModsConstants.GEO_CODE,
                                                                        "Geographic Code",
                                                                        lang.geoCode()),
                                                          false,
                                                          new Attribute[] {new Attribute(SelectItem.class,
                                                                                         ModsConstants.AUTHORITY,
                                                                                         "Authority",
                                                                                         new HashMap<String, String>() {

                                                                                             {
                                                                                                 put("",
                                                                                                     lang.attributeOmitted());
                                                                                                 put("marcgac",
                                                                                                     "marcgac");
                                                                                                 put("marccountry",
                                                                                                     "marccountry");
                                                                                                 put("iso3166",
                                                                                                     "iso3166");
                                                                                             }
                                                                                         })},
                                                          vals,
                                                          holder.getGeoCodes(),
                                                          false));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Occupations",
                                                              "Occupation",
                                                              lang.occupationsMARC()),
                                                false,
                                                values == null ? null : values.getOccupation(),
                                                holder.getOccupations()));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the location layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the location layout
     */
    private static VLayout getLocationLayout(LocationTypeClient values, LocationHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        List<List<String>> vals = null;
        if (values != null && values.getPhysicalLocation() != null && values.getPhysicalLocation().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (PhysicalLocationTypeClient val : values.getPhysicalLocation()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getDisplayLabel());
                    list.add(val.getType());
                    list.add(val.getAuthority());
                    list.add(val.getXlink());
                    list.add(val.getLang());
                    list.add(val.getXmlLang());
                    list.add(val.getTransliteration());
                    list.add(val.getScript());
                    vals.add(list);
                }
            }
        }
        Attribute[] attributes =
                new Attribute[] {getDisplayLabel(lang.locationMarc(), ""),
                        new Attribute(TextItem.class, ModsConstants.TYPE, "Type", lang.locationType()),
                        ATTR_AUTHORITY(""), ATTR_XLINK(""), ATTR_LANG(""), ATTR_XML_LANG(""),
                        ATTR_TRANSLITERATION(""), ATTR_SCRIPT("")};
        sectionStack.addSection(getStackSectionWithAttributes("Physical Location",
                                                              new Attribute(TextItem.class,
                                                                            ModsConstants.PHYSICAL_LOCATION,
                                                                            "Physical Location",
                                                                            lang.physLocMARC()),
                                                              true,
                                                              attributes,
                                                              vals,
                                                              holder.getPhysicalLocations(),
                                                              false));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Shelf Locators",
                                                              "Shelf Locator",
                                                              lang.shelfLocator()),
                                                false,
                                                values == null ? null : values.getShelfLocator(),
                                                holder.getShelfLocators()));
        attributes =
                new Attribute[] {
                        getDisplayLabel(lang.shelfLocMARC(), ""),
                        new Attribute(DateItem.class,
                                      ModsConstants.DATE_LAST_ACCESSED,
                                      "Date Last Accessed",
                                      lang.dateLastAcc()),
                        new Attribute(TextAreaItem.class, ModsConstants.NOTE, "Note", lang.linkAsocURL()),
                        new Attribute(SelectItem.class,
                                      ModsConstants.ACCESS,
                                      "Access",
                                      new HashMap<String, String>() {

                                          {
                                              put("", lang.attributeOmitted());
                                              put("preview", lang.preview());
                                              put("raw object", lang.rawObject());
                                              put("object in context", lang.objContext());
                                          }
                                      }),
                        new Attribute(SelectItem.class,
                                      ModsConstants.USAGE,
                                      "Usage",
                                      new HashMap<String, String>() {

                                          {
                                              put("", lang.attributeOmitted());
                                              put("primary display", lang.primDisplay());
                                          }
                                      })};
        vals = null;
        if (values != null && values.getUrl() != null && values.getUrl().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (UrlTypeClient val : values.getUrl()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getDisplayLabel());
                    list.add(val.getDateLastAccessed());
                    list.add(val.getNote());
                    list.add(val.getAccess());
                    list.add(val.getUsage());
                    vals.add(list);
                }
            }
        }
        sectionStack.addSection(getStackSectionWithAttributes("Url",
                                                              new Attribute(TextItem.class,
                                                                            ModsConstants.URL,
                                                                            "URL",
                                                                            lang.url()),
                                                              true,
                                                              attributes,
                                                              vals,
                                                              holder.getUrls(),
                                                              false));

        GetCopyInformationLayout getCopyInformationLayout = new GetCopyInformationLayout();
        getCopyInformationLayout.setValues(values == null || values.getHoldingSimple() == null ? null
                : values.getHoldingSimple().getCopyInformation());
        getCopyInformationLayout.setHolders(holder.getHoldingSimples());
        sectionStack.addSection(getSomeStack(false, "Holding Simple", getCopyInformationLayout));
        sectionStack
                .addSection(getStackSection(new Attribute(TextAreaItem.class,
                                                          "Holding Externals",
                                                          "Holding External",
                                                          lang.holdExt()),
                                            false,
                                            values != null && values.getHoldingExternal() != null
                                                    && values.getHoldingExternal().getContent() != null ? Arrays
                                                    .asList(values.getHoldingExternal().getContent()) : null,
                                            holder.getHoldingExternals()));
        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the part layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the part layout
     */
    private static VLayout getPartLayout(PartTypeClient values, PartHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(TextItem.class,
                                      ModsConstants.TYPE,
                                      "Type",
                                      lang.partType(),
                                      values == null ? "" : values.getType()),
                        new Attribute(TextItem.class, "order", "Order", lang.order(), values == null ? ""
                                : values.getOrder()), ATTR_ID(values == null ? "" : values.getId())};

        DynamicForm formAttr = getAttributes(true, attributes);
        holder.setAttributeForm(formAttr);
        layout.addMember(formAttr);
        GetDetailLayout getDetailLayout = new GetDetailLayout();
        getDetailLayout.setValues(values == null ? null : values.getDetail());
        getDetailLayout.setHolders(holder.getDetails());
        sectionStack.addSection(getSomeStack(false, "Detail", getDetailLayout));

        GetExtentLayout getExtentLayout = new GetExtentLayout();
        getExtentLayout.setValues(values == null ? null : values.getExtent());
        getExtentLayout.setHolders(holder.getExtents());
        sectionStack.addSection(getSomeStack(false, "Extent", getExtentLayout));

        GetDateLayout getDateLayout =
                new GetDateLayout("date", "Date", lang.partDate(), null, false, values == null ? null
                        : values.getDate(), holder.getDates());
        sectionStack.addSection(getSomeStack(false, "Date", getDateLayout));
        sectionStack.addSection(getStackSection(new Attribute(TextAreaItem.class, "Texts", "Text", lang
                                                        .partText()),
                                                false,
                                                values == null ? null : values.getText(),
                                                holder.getTexts()));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the detail layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the detail layout
     */
    private static VLayout getDetailLayout(DetailTypeClient values, DetailHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();

        Attribute[] attributes =
                new Attribute[] {
                        new Attribute(TextItem.class,
                                      ModsConstants.TYPE,
                                      "Type",
                                      lang.detailType(),
                                      values == null ? "" : values.getType()),
                        new Attribute(TextItem.class,
                                      ModsConstants.LEVEL,
                                      "Level",
                                      lang.detailLevel(),
                                      values == null ? "" : values.getLevel())};
        DynamicForm form = getAttributes(true, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);

        sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "Numbers", "Number", lang
                                                        .detailNum()),
                                                false,
                                                values == null ? null : values.getNumber(),
                                                holder.getNumbers()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "Captions", "Caption", lang
                                                        .caption()),
                                                false,
                                                values == null ? null : values.getCaption(),
                                                holder.getCaptions()));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "Titles", "Title", lang
                                                        .detailTitle()),
                                                false,
                                                values == null ? null : values.getTitle(),
                                                holder.getTitles()));
        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the extent layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the extent layout
     */
    private static VLayout getExtentLayout(ExtentTypeClient values, ExtentHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();

        Attribute[] attributes =
                new Attribute[] {new Attribute(TextItem.class,
                                               ModsConstants.UNIT,
                                               "Unit",
                                               lang.extUnit(),
                                               values == null ? "" : values.getUnit())};
        DynamicForm form = getAttributes(false, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class,
                                                               ModsConstants.START,
                                                               "Start",
                                                               lang.extStart(),
                                                               values == null ? "" : values.getStart()),
                                                 false,
                                                 holder.getStart()));
        sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, ModsConstants.END, "End", lang
                                                         .extEnd(), values == null ? "" : values.getEnd()),
                                                 false,
                                                 holder.getEnd()));
        sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class,
                                                               ModsConstants.TOTAL,
                                                               "Total",
                                                               lang.extTotal(),
                                                               values == null ? "" : values.getTotal()),
                                                 false,
                                                 holder.getTotal()));
        sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class,
                                                               ModsConstants.LIST,
                                                               "List",
                                                               lang.extList(),
                                                               values == null ? "" : values.getList()),
                                                 false,
                                                 holder.getList()));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the record info layout.
     * 
     * @param values
     *        the values
     * @param holder
     *        the holder
     * @return the record info layout
     */
    private static VLayout getRecordInfoLayout(RecordInfoTypeClient values, RecordInfoHolder holder) {
        final VLayout layout = new VLayout();
        final SectionStack sectionStack = new SectionStack();
        sectionStack.setLeaveScrollbarGap(true);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setStyleName("metadata-elements");
        sectionStack.setWidth100();
        sectionStack.setMinHeight(500);
        sectionStack.setMinMemberSize(300);
        sectionStack.setOverflow(Overflow.VISIBLE);
        Attribute[] attributes =
                new Attribute[] {ATTR_LANG(values == null ? "" : values.getLang()),
                        ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
                        ATTR_SCRIPT(values == null ? "" : values.getScript()),
                        ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration())};
        DynamicForm form = getAttributes(false, attributes);
        layout.addMember(form);
        holder.setAttributeForm(form);
        List<List<String>> vals = null;
        if (values != null && values.getRecordContentSource() != null
                && values.getRecordContentSource().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (StringPlusAuthorityPlusLanguageClient val : values.getRecordContentSource()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getAuthority());
                    list.add(val.getLang());
                    list.add(val.getXmlLang());
                    list.add(val.getScript());
                    list.add(val.getTransliteration());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Record Content Source",
                                                          new Attribute(TextItem.class,
                                                                        ModsConstants.RECORD_CONTENT_SOURCE,
                                                                        "Record Content Source",
                                                                        lang.recConSourMARC()),
                                                          false,
                                                          new Attribute[] {ATTR_AUTHORITY(""), ATTR_LANG(""),
                                                                  ATTR_XML_LANG(""), ATTR_SCRIPT(""),
                                                                  ATTR_TRANSLITERATION("")},
                                                          vals,
                                                          holder.getRecordContentSource(),
                                                          false));
        sectionStack.addSection(getSomeStack(false,
                                             "Record Creation Date",
                                             new GetDateLayout("record_creation_date",
                                                               "Record Creation Date",
                                                               lang.recCreDateMARC(),
                                                               null,
                                                               false,
                                                               values == null ? null : values
                                                                       .getRecordCreationDate(), holder
                                                                       .getCreationDate())));
        sectionStack.addSection(getSomeStack(false,
                                             "Record Change Date",
                                             new GetDateLayout("record_change_date",
                                                               "Record Creation Date",
                                                               lang.recCreDate(),
                                                               null,
                                                               false,
                                                               values == null ? null : values
                                                                       .getRecordChangeDate(), holder
                                                                       .getChangeDate())));
        vals = null;
        if (values != null && values.getRecordIdentifier() != null && values.getRecordIdentifier().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (RecordIdentifierClient val : values.getRecordIdentifier()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getSource());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Record Identifiers",
                                                          new Attribute(TextItem.class,
                                                                        ModsConstants.RECORD_IDENTIFIER,
                                                                        "Record Identifier",
                                                                        lang.recIdMARC()),
                                                          false,
                                                          new Attribute[] {new Attribute(TextItem.class,
                                                                                         ModsConstants.SOURCE,
                                                                                         "Source",
                                                                                         lang.sourceMarc())},
                                                          vals,
                                                          holder.getRecordIdentifier(),
                                                          false));
        sectionStack.addSection(getStackSection(new Attribute(TextItem.class,
                                                              "Record Origins",
                                                              "Record Origin",
                                                              lang.recOrigin()), false, values == null ? null
                : values.getRecordOrigin(), holder.getRecordOrigin()));

        GetLanguageLayout getLanguageLayout = new GetLanguageLayout();
        getLanguageLayout.setValues(values == null ? null : values.getLanguageOfCataloging());
        getLanguageLayout.setHolders(holder.getLanguage());
        sectionStack.addSection(getSomeStack(false, "Language of Cataloging", getLanguageLayout));
        if (values != null && values.getDescriptionStandard() != null
                && values.getDescriptionStandard().size() > 0) {
            vals = new ArrayList<List<String>>();
            for (StringPlusAuthorityClient val : values.getDescriptionStandard()) {
                if (val != null) {
                    List<String> list = new ArrayList<String>();
                    list.add(val.getValue());
                    list.add(val.getAuthority());
                    vals.add(list);
                }
            }
        }
        sectionStack
                .addSection(getStackSectionWithAttributes("Description Standard",
                                                          new Attribute(TextItem.class,
                                                                        ModsConstants.DESCRIPTION_STANDARD,
                                                                        "Description Standard",
                                                                        lang.descrStandMARC()),
                                                          false,
                                                          new Attribute[] {ATTR_AUTHORITY("")},
                                                          vals,
                                                          holder.getDescriptionStandard(),
                                                          false));

        layout.addMember(sectionStack);
        return layout;
    }

    /**
     * Gets the values.
     * 
     * @param values
     *        the values
     * @param valueKey
     *        the value key
     * @return the values
     */
    private static List<Map<String, String>> getValues(List<? extends UnstructuredTextClient> values,
                                                       String valueKey) {
        List<Map<String, String>> valueList = null;
        if (values != null && values.size() > 0) {
            valueList = new ArrayList<Map<String, String>>();
            Map<String, String> valueMap = new HashMap<String, String>();
            for (UnstructuredTextClient value : values) {
                if (value != null) {
                    if (value.getAtType() != null && !"".equals(value.getAtType()))
                        valueMap.put(ModsConstants.TYPE, value.getAtType());
                    if (value.getDisplayLabel() != null && !"".equals(value.getDisplayLabel()))
                        valueMap.put(ModsConstants.DISPLAY_LABEL, value.getDisplayLabel());
                    if (value.getLang() != null && !"".equals(value.getLang()))
                        valueMap.put(ModsConstants.LANG, value.getLang());
                    if (value.getXmlLang() != null && !"".equals(value.getXmlLang()))
                        valueMap.put(ModsConstants.XML_LANG, value.getXmlLang());
                    if (value.getXlink() != null && !"".equals(value.getXlink()))
                        valueMap.put(ModsConstants.XLINK, value.getXlink());
                    if (value.getScript() != null && !"".equals(value.getScript()))
                        valueMap.put(ModsConstants.SCRIPT, value.getScript());
                    if (value.getTransliteration() != null && !"".equals(value.getTransliteration()))
                        valueMap.put(ModsConstants.TRANSLITERATION, value.getTransliteration());
                    if (value.getValue() != null && !"".equals(value.getValue()))
                        valueMap.put(valueKey, value.getValue());
                }
                valueList.add(valueMap);
            }
        }
        return valueList;
    }

}
