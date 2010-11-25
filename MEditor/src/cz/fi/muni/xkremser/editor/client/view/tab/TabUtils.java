/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.metadata.DateHolder;
import cz.fi.muni.xkremser.editor.client.metadata.GenreHolder;
import cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.metadata.NameHolder;
import cz.fi.muni.xkremser.editor.client.metadata.OriginInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PlaceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TitleInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TypeOfResourceHolder;
import cz.fi.muni.xkremser.editor.client.mods.DateTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NamePartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PlaceTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RoleTypeClient.RoleTermClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;

// TODO: Auto-generated Javadoc
/**
 * The Class TabUtils.
 */
public final class TabUtils {

	/** The Constant W3C_ENCODING. */
	private static final String W3C_ENCODING = "w3cdtf";

	/** The Constant LANG_AUTHORITY_TOOLTIPS. */
	private static final Map<String, String> LANG_AUTHORITY_TOOLTIPS = new HashMap<String, String>() {
		{
			put("", "Nothing.");
			put("iso639-2b", "A bibliographic language code from ISO 639-2 (Codes for the representation of names of languages: alpha-3 code).");
			put("rfc3066", "A language identifier as specified by the Internet Best Current Practice specification RFC3066.");
			put("iso639-3",
					"A language code from ISO 639-3 (Codes for the representation of names of languages - Part 3: Alpha-3 code for comprehensive coverage of languages).");
			put("rfc4646", "A language identifier as specified by the Internet Best Current Practice specification RFC4646.");
		}
	};

	/** The Constant TYPE_TEXT_CODE_TOOLTIPS. */
	private static final Map<String, String> TYPE_TEXT_CODE_TOOLTIPS = new HashMap<String, String>() {
		{
			put("", "This attribute will be omitted.");
			put("text", "This value is used to express attribute in a textual form.");
			put("code", "This value is used to express attribute in a coded form. The authority attribute may be used to indicate the source of the code.");
		}
	};

	/** The Constant ENCODING_TOOLTIPS. */
	private static final Map<String, String> ENCODING_TOOLTIPS = new HashMap<String, String>() {
		{
			put("", "This attribute will be omitted.");
			put(W3C_ENCODING, "date pattern: YYYY-MM-DD");
			put("iso8601", "date pattern: YYYYMMDD or range: YYYY/YYYY");
			put("marc", "This value is used only for dates coded according to MARC 21 rules in field 008/07-14 for dates of publication/issuance.");
		}
	};

	/** The Constant POINT_TOOLTIPS. */
	private static final Map<String, String> POINT_TOOLTIPS = new HashMap<String, String>() {
		{
			put("", "This attribute will be omitted.");
			put("start", "This value is used for the first date of a range (or a single date, if used).");
			put("end", "This value is used for the end date of a range.");
		}
	};

	/** The Constant QUALIFIER_TOOLTIPS. */
	private static final Map<String, String> QUALIFIER_TOOLTIPS = new HashMap<String, String>() {
		{
			put("", "This attribute will be omitted.");
			put("approximate", "This value is used to identify a date that may not be exact, but is approximated, such as 'ca. 1972'.");
			put("inferred", "This value is used to identify a date that has not been transcribed directly from a resource, such as '[not before 1852]'.");
			put("questionable", "This value is used to identify a questionable date for a resource, such as '1972?'.");
		}
	};

	private static final Attribute ATTR_AUTHORITY(String value) {
		return new Attribute(
				TextItem.class,
				ModsConstants.AUTHORITY,
				"Authority",
				"The name of the authoritative list for a controlled value is recorded here. An authority attribute may be used to indicate that a title is controlled by a record in an authority file.",
				value);
	}

	/** The Constant ATTR_AUTHORITY. */
	private static final Attribute ATTR_AUTHORITY = new Attribute(
			TextItem.class,
			ModsConstants.AUTHORITY,
			"Authority",
			"The name of the authoritative list for a controlled value is recorded here. An authority attribute may be used to indicate that a title is controlled by a record in an authority file.");

	/** The Constant ATTR_LANG. */
	public static final Attribute ATTR_LANG(String value) {
		return new Attribute(TextItem.class, ModsConstants.LANG, "Lang",
				"This attribute is used to specify the language used within individual elements, using the codes from ISO 639-2/b.");
	}

	public static final Attribute ATTR_LANG = new Attribute(TextItem.class, ModsConstants.LANG, "Lang",
			"This attribute is used to specify the language used within individual elements, using the codes from ISO 639-2/b.");

	public static final Attribute ATTR_XML_LANG(String value) {
		return new Attribute(TextItem.class, ModsConstants.XML_LANG, "xml:lang",
				"In the XML standard, this attribute is used to specify the language used within individual elements, using specifications in RFC 3066.", value);
	}

	/** The Constant ATTR_XML_LANG. */
	public static final Attribute ATTR_XML_LANG = new Attribute(TextItem.class, ModsConstants.XML_LANG, "xml:lang",
			"In the XML standard, this attribute is used to specify the language used within individual elements, using specifications in RFC 3066.");

	public static final Attribute ATTR_TRANSLITERATION(String value) {
		return new Attribute(TextItem.class, ModsConstants.TRANSLITERATION, "Transliteration",
				"It specifies the transliteration technique used within individual elements. There is no MARC 21 equivalent for this attribute. ", value);
	}

	/** The Constant ATTR_TRANSLITERATION. */
	public static final Attribute ATTR_TRANSLITERATION = new Attribute(TextItem.class, "transliteration", "Transliteration",
			"It specifies the transliteration technique used within individual elements. There is no MARC 21 equivalent for this attribute. ");

	public static final Attribute ATTR_SCRIPT(String value) {
		return new Attribute(TextItem.class, ModsConstants.SCRIPT, "Script",
				"This attribute specifies the script used within individual elements, using codes from ISO 15924.", value);
	}

	/** The Constant ATTR_SCRIPT. */
	public static final Attribute ATTR_SCRIPT = new Attribute(TextItem.class, ModsConstants.SCRIPT, "Script",
			"This attribute specifies the script used within individual elements, using codes from ISO 15924.");

	public static final Attribute ATTR_ID(String value) {
		return new Attribute(TextItem.class, ModsConstants.ID, "ID",
				"This attribute is used to link internally and to reference an element from outside the instance.", value);
	}

	/** The Constant ATTR_ID. */
	public static final Attribute ATTR_ID = new Attribute(TextItem.class, ModsConstants.ID, "ID",
			"This attribute is used to link internally and to reference an element from outside the instance.");

	public static final Attribute ATTR_XLINK(String value) {
		return new Attribute(TextItem.class, ModsConstants.XLINK, "xlink",
				"This attribute is used for an external link. It is defined in the MODS schema as xlink:simpleLink.", value);
	}

	/** The Constant ATTR_XLINK. */
	public static final Attribute ATTR_XLINK = new Attribute(TextItem.class, ModsConstants.XLINK, "xlink",
			"This attribute is used for an external link. It is defined in the MODS schema as xlink:simpleLink.");

	/** The Constant ATTR_ENCODING. */
	public static final Attribute ATTR_ENCODING = new Attribute(SelectItem.class, "encoding", "Encoding", ENCODING_TOOLTIPS);

	public static final Attribute ATTR_ENCODING(String value) {
		return new Attribute(SelectItem.class, ModsConstants.ENCODING, "Encoding", ENCODING_TOOLTIPS, value);
	}

	/** The Constant ATTR_POINT. */
	public static final Attribute ATTR_POINT = new Attribute(SelectItem.class, "point", "Point", POINT_TOOLTIPS);

	public static final Attribute ATTR_POINT(String value) {
		return new Attribute(SelectItem.class, ModsConstants.POINT, "Point", POINT_TOOLTIPS, value);
	}

	/** The Constant ATTR_KEY_DATE. */
	public static final Attribute ATTR_KEY_DATE = new Attribute(
			CheckboxItem.class,
			ModsConstants.KEY_DATE,
			"Key Date",
			"This value is used so that a particular date may be distinguished among several dates. Thus for example, when sorting MODS records by date, a date with keyDate='yes' would be the date to sort on.");

	public static final Attribute ATTR_KEY_DATE(String value) {
		return new Attribute(
				CheckboxItem.class,
				ModsConstants.KEY_DATE,
				"Key Date",
				"This value is used so that a particular date may be distinguished among several dates. Thus for example, when sorting MODS records by date, a date with keyDate='yes' would be the date to sort on.",
				value);
	}

	/** The Constant ATTR_QUALIFIER. */
	public static final Attribute ATTR_QUALIFIER = new Attribute(SelectItem.class, "qualifier", "Qualifier", QUALIFIER_TOOLTIPS);

	public static final Attribute ATTR_QUALIFIER(String value) {
		return new Attribute(SelectItem.class, ModsConstants.QUALIFIER, "Qualifier", QUALIFIER_TOOLTIPS, value);
	}

	/** The Constant ATTR_TYPE_TEXT_CODE. */
	public static final Attribute ATTR_TYPE_TEXT_CODE = new Attribute(SelectItem.class, "type", "Type", TYPE_TEXT_CODE_TOOLTIPS);

	/** The Constant ATTR_LANG_AUTHORITY. */
	public static final Attribute ATTR_LANG_AUTHORITY = new Attribute(SelectItem.class, "authority", "Authority", LANG_AUTHORITY_TOOLTIPS);

	/**
	 * Gets the display label.
	 * 
	 * @param tooltip
	 *          the tooltip
	 * @return the display label
	 */
	public static Attribute getDisplayLabel(final String tooltip, final String value) {
		return new Attribute(TextItem.class, ModsConstants.DISPLAY_LABEL, "Display Label", tooltip, value);
	}

	public static Attribute getDisplayLabel(final String tooltip) {
		return new Attribute(TextItem.class, ModsConstants.DISPLAY_LABEL, "Display Label", tooltip);
	}

	/** The Constant GET_TITLE_INFO_LAYOUT. */
	// private static final GetLayoutOperation GET_TITLE_INFO_LAYOUT = new
	// GetTitleInfoLayout();

	/** The Constant GET_NAME_LAYOUT. */
	// private static final GetLayoutOperation GET_NAME_LAYOUT = new
	// GetNameLayout();

	/** The Constant GET_TYPE_OF_RESOURCE_LAYOUT. */
	private static final GetLayoutOperation GET_TYPE_OF_RESOURCE_LAYOUT = new GetTypeOfResourceLayout();

	/** The Constant GET_GENRE_LAYOUT. */
	private static final GetLayoutOperation GET_GENRE_LAYOUT = new GetGenreLayout();

	/** The Constant GET_ORIGIN_INFO_LAYOUT. */
	private static final GetLayoutOperation GET_ORIGIN_INFO_LAYOUT = new GetOriginInfoLayout();

	/** The Constant GET_PLACE_LAYOUT. */
	private static final GetLayoutOperation GET_PLACE_LAYOUT = new GetPlaceLayout();

	/** The Constant GET_LANGUAGE_LAYOUT. */
	private static final GetLayoutOperation GET_LANGUAGE_LAYOUT = new GetLanguageLayout();

	/** The Constant GET_PHYSICIAL_DESCRIPTION_LAYOUT. */
	private static final GetLayoutOperation GET_PHYSICIAL_DESCRIPTION_LAYOUT = new GetPhysicialDescriptionLayout();

	/**
	 * The Interface GetLayoutOperation.
	 */
	public static abstract class GetLayoutOperation {
		private List<? extends Object> holders;
		private List<? extends Object> values;
		protected int counter = 0;

		public void increaseCounter() {
			++counter;
		}

		public void decreaseCounter() {
			--counter;
		}

		public int getCounter() {
			return counter;
		}

		public int getElementNumber() {
			return values == null ? 1 : values.size();
		}

		public List<? extends Object> getHolders() {
			return holders;
		}

		public void setHolders(List<? extends Object> holders) {
			this.holders = holders;
		}

		public List<? extends Object> getValues() {
			return values;
		}

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
	private static class GetTitleInfoLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetNameLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetTypeOfResourceLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetGenreLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetOriginInfoLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetPhysicialDescriptionLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getPhysicialDescriptionLayout();
		}
	}

	/**
	 * The Class GetGeneralLayout.
	 */
	private static class GetGeneralLayout extends GetLayoutOperation {

		/** The head. */
		private final Attribute head;

		/** The attributes. */
		private final Attribute[] attributes;

		/**
		 * Instantiates a new gets the general layout.
		 * 
		 * @param head
		 *          the head
		 * @param attributes
		 *          the attributes
		 */
		public GetGeneralLayout(Attribute head, Attribute[] attributes) {
			super();
			this.head = head;
			this.attributes = attributes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getGeneralLayout(head, attributes);
		}
	}

	/**
	 * The Class GetGeneralDeepLayout.
	 */
	private static class GetGeneralDeepLayout extends GetLayoutOperation {

		/** The attributes. */
		private final Attribute[] attributes;

		/**
		 * Instantiates a new gets the general deep layout.
		 * 
		 * @param attributes
		 *          the attributes
		 */
		public GetGeneralDeepLayout(Attribute[] attributes) {
			super();
			this.attributes = attributes;
		}

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetSubjectLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getSubjectLayout();
		}
	}

	/**
	 * The Class GetLocationLayout.
	 */
	private static class GetLocationLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getLocationLayout();
		}
	}

	/**
	 * The Class GetCopyInformationLayout.
	 */
	private static class GetCopyInformationLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getCopyInformationLayout();
		}
	}

	/**
	 * The Class GetPartLayout.
	 */
	private static class GetPartLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getPartLayout();
		}
	}

	/**
	 * The Class GetRecordInfoLayout.
	 */
	private static class GetRecordInfoLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getRecordInfoLayout();
		}
	}

	/**
	 * The Class GetPlaceLayout.
	 */
	private static class GetPlaceLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
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
	private static class GetDetailLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getDetailLayout();
		}
	}

	/**
	 * The Class GetExtentLayout.
	 */
	private static class GetExtentLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getExtentLayout();
		}
	}

	/**
	 * The Class GetLanguageOfCatalogingLayout.
	 */
	private static class GetLanguageOfCatalogingLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getLanguageOfCatalogingLayout();
		}
	}

	/**
	 * The Class GetLanguageLayout.
	 */
	private static class GetLanguageLayout extends GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getLanguageLayout();
		}
	}

	/**
	 * The Class GetDateLayout.
	 */
	private static class GetDateLayout extends GetLayoutOperation {

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

		private final boolean isOtherDate;

		/**
		 * Instantiates a new gets the date layout.
		 * 
		 * @param name
		 *          the name
		 * @param title
		 *          the title
		 * @param tooltip
		 *          the tooltip
		 */
		public GetDateLayout(String name, String title, String tooltip, List<DateTypeClient> values, List<DateHolder> holders) {
			this(name, title, tooltip, null, true, values, holders);
		}

		/**
		 * Instantiates a new gets the date layout.
		 * 
		 * @param name
		 *          the name
		 * @param title
		 *          the title
		 * @param tooltip
		 *          the tooltip
		 * @param attribute
		 *          the attribute
		 * @param keyDate
		 *          the key date
		 */
		public GetDateLayout(String name, String title, String tooltip, Attribute attribute, boolean keyDate, List<DateTypeClient> values, List<DateHolder> holders) {
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

		public GetDateLayout(String name, String title, String tooltip, Attribute attribute, boolean keyDate, List<? extends DateTypeClient> values,
				List<DateHolder> holders, boolean isOtherDate) {
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
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			DateTypeClient dateTypeClient = null;
			if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
				dateTypeClient = (DateTypeClient) getValues().get(counter);
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
	 *          the attribute
	 * @param expanded
	 *          the expanded
	 * @return the stack section
	 */
	public static SectionStackSection getStackSection(Attribute attribute, final boolean expanded, MetadataHolder holder) {
		return getStackSectionWithAttributes(attribute.getLabel(), attribute, expanded, null, holder);
	}

	public static SectionStackSection getStackSection(Attribute attribute, final boolean expanded, final List<String> values, MetadataHolder holder) {
		return getStackSectionWithAttributes(attribute.getLabel(), attribute, expanded, null, new ArrayList<List<String>>() {
			{
				add(values);
			}
		}, holder, true);
	}

	public static SectionStackSection getStackSection(final String label1, final String label2, final String tooltip, final boolean expanded) {
		return getStackSectionWithAttributes(label1, label2, tooltip, expanded, null, null);
	}

	/**
	 * Gets the stack section.
	 * 
	 * @param label1
	 *          the label1
	 * @param label2
	 *          the label2
	 * @param tooltip
	 *          the tooltip
	 * @param expanded
	 *          the expanded
	 * @return the stack section
	 */
	public static SectionStackSection getStackSection(final String label1, final String label2, final String tooltip, final boolean expanded,
			final List<String> values, MetadataHolder holder) {
		return getStackSectionWithAttributes(label1, label2, tooltip, expanded, null, new ArrayList<List<String>>() {
			{
				add(values);
			}
		}, holder, true);
	}

	/**
	 * Gets the simple section.
	 * 
	 * @param attribute
	 *          the attribute
	 * @param expanded
	 *          the expanded
	 * @return the simple section
	 */
	public static SectionStackSection getSimpleSection(final Attribute attribute, final boolean expanded) {
		return getSimpleSectionWithAttributes(attribute, expanded, null, null);
	}

	/**
	 * Gets the simple section.
	 * 
	 * @param type
	 *          the type
	 * @param label
	 *          the label
	 * @param tooltip
	 *          the tooltip
	 * @param expanded
	 *          the expanded
	 * @return the simple section
	 */
	public static SectionStackSection getSimpleSection(final Class<? extends FormItem> type, final String label, final String tooltip, final boolean expanded) {
		return getSimpleSectionWithAttributes(new Attribute(type, label.toLowerCase().replaceAll(" ", "_"), label, tooltip), expanded, null, null);
	}

	public static SectionStackSection getStackSectionWithAttributes(final String label1, final String label2, final String tooltip, final boolean expanded,
			final Attribute[] attributes, MetadataHolder holder) {
		return getStackSectionWithAttributes(label1, label2, tooltip, expanded, attributes, null, holder, true);
	}

	public static SectionStackSection getStackSectionWithAttributes(final String label1, final String label2, final String tooltip, final boolean expanded,
			final Attribute[] attributes, final List<List<String>> values, MetadataHolder holder, boolean flat) {
		return getStackSectionWithAttributes(label1, new Attribute(TextItem.class, label2.toLowerCase().replaceAll(" ", "_"), label2, tooltip), expanded,
				attributes, values, holder, flat);
	}

	public static SectionStackSection getSimpleSectionWithAttributes(final Attribute mainAttr, final boolean expanded, Attribute[] attributes,
			MetadataHolder holder) {
		final boolean isAttribPresent = attributes != null && attributes.length != 0;
		final VLayout layout = new VLayout();
		if (isAttribPresent) {
			layout.addMember(getAttributes(true, attributes));
		}

		if (holder != null) {
			holder.setLayout(layout);
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
		} else {
			section.addItem(form);
		}
		section.setExpanded(expanded);

		return section;
	}

	public static SectionStackSection getStackSectionWithAttributes(final String stackLabel, final Attribute mainAttr, final boolean expanded,
			final Attribute[] attributes, MetadataHolder holder) {
		return getStackSectionWithAttributes(stackLabel, mainAttr, expanded, attributes, null, holder, true);
	}

	/**
	 * Gets the stack section with attributes.
	 * 
	 * @param stackLabel
	 *          the stack label
	 * @param mainAttr
	 *          the main attr
	 * @param expanded
	 *          the expanded
	 * @param attributes
	 *          the attributes
	 * @return the stack section with attributes
	 */
	public static SectionStackSection getStackSectionWithAttributes(final String stackLabel, final Attribute mainAttr, final boolean expanded,
			final Attribute[] attributes, List<List<String>> values, MetadataHolder holder, boolean flat) {
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

		if (!flat && values != null && values.size() > 0) {
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
						itemsToAdd[i + 1].setValue(valueVector == null ? "" : valueVector.get(i + 1) == null ? "" : valueVector.get(i + 1));
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
			boolean isVals = values != null && values.size() > 0 && values.get(0) != null && values.get(0).size() > 0;
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

		// layout.setAutoHeight(); // todo: ???
		section.addItem(layout);
		section.setExpanded(expanded);

		return section;
	}

	/**
	 * Gets the attributes.
	 * 
	 * @param group
	 *          the group
	 * @param attributes
	 *          the attributes
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
	 *          the attr
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
			item.setTooltip(attr.getTooltip());
		}
		item.setTitle(attr.getLabel());
		return item;
	}

	/**
	 * Gets the some stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @param label
	 *          the label
	 * @param operation
	 *          the operation
	 * @return the some stack
	 */
	public static SectionStackSection getSomeStack(boolean expanded, final String label, final GetLayoutOperation operation) {
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
	 *          the expanded
	 * @return the title info stack
	 */
	public static SectionStackSection getTitleInfoStack(boolean expanded, List<TitleInfoTypeClient> values, List<TitleInfoHolder> holders) {
		GetTitleInfoLayout getTitleInfoLayout = new GetTitleInfoLayout();
		getTitleInfoLayout.setHolders(holders);
		getTitleInfoLayout.setValues(values);
		return getSomeStack(expanded, "Title Info", getTitleInfoLayout);
	}

	/**
	 * Gets the name stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the name stack
	 */
	public static SectionStackSection getNameStack(boolean expanded, List<NameTypeClient> values, List<NameHolder> holders) {
		GetNameLayout getNameLayout = new GetNameLayout();
		getNameLayout.setHolders(holders);
		getNameLayout.setValues(values);
		return getSomeStack(expanded, "Name", getNameLayout);
	}

	/**
	 * Gets the type of resource stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the type of resource stack
	 */
	public static SectionStackSection getTypeOfResourceStack(boolean expanded, List<TypeOfResourceTypeClient> values, List<TypeOfResourceHolder> holders) {
		GetTypeOfResourceLayout getTypeOfResourceLayout = new GetTypeOfResourceLayout();
		getTypeOfResourceLayout.setHolders(holders);
		getTypeOfResourceLayout.setValues(values);
		return getSomeStack(expanded, "Type of Resource", getTypeOfResourceLayout);
	}

	/**
	 * Gets the genre stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the genre stack
	 */
	public static SectionStackSection getGenreStack(boolean expanded, List<GenreTypeClient> values, List<GenreHolder> holders) {
		GetGenreLayout getLayout = new GetGenreLayout();
		getLayout.setHolders(holders);
		getLayout.setValues(values);
		return getSomeStack(expanded, "Genre", getLayout);
	}

	/**
	 * Gets the origin info stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the origin info stack
	 */
	public static SectionStackSection getOriginInfoStack(boolean expanded, List<OriginInfoTypeClient> values, List<OriginInfoHolder> holders) {
		GetOriginInfoLayout getOriginInfoLayout = new GetOriginInfoLayout();
		getOriginInfoLayout.setValues(values);
		getOriginInfoLayout.setHolders(holders);
		return getSomeStack(expanded, "Origin Info", getOriginInfoLayout);
	}

	/**
	 * Gets the language stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the language stack
	 */
	public static SectionStackSection getLanguageStack(boolean expanded) {
		return getSomeStack(expanded, "Language", GET_LANGUAGE_LAYOUT);
	}

	/**
	 * Gets the physical description stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the physical description stack
	 */
	public static SectionStackSection getPhysicalDescriptionStack(boolean expanded) {
		return getSomeStack(expanded, "Physicial Description", GET_PHYSICIAL_DESCRIPTION_LAYOUT);
	}

	/**
	 * Gets the abstract stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the abstract stack
	 */
	public static SectionStackSection getAbstractStack(boolean expanded) {
		return getSomeStack(expanded, "Abstract", new GetGeneralLayout(new Attribute(TextAreaItem.class, "abstract", "Abstract",
				"Roughly equivalent to MARC 21 field 520."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."),
				getDisplayLabel("This attribute is intended to be used when additional text associated with the abstract is necessary for display."), ATTR_XLINK,
				ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
	}

	/**
	 * Gets the table of contents stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the table of contents stack
	 */
	public static SectionStackSection getTableOfContentsStack(boolean expanded) {
		return getSomeStack(expanded, "Table of Contents", new GetGeneralLayout(new Attribute(TextAreaItem.class, "table_of_contents", "Table of Contents",
				"Roughly equivalent to MARC 21 field 505."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."),
				getDisplayLabel("This attribute is intended to be used when additional text associated with the table of contents is necessary for display."),
				ATTR_XLINK, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
	}

	/**
	 * Gets the target audience stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the target audience stack
	 */
	public static SectionStackSection getTargetAudienceStack(boolean expanded) {
		Attribute[] attributes = new Attribute[] { ATTR_AUTHORITY, ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		return getStackSectionWithAttributes("Target Audience", "Target Audience",
				"A description of the intellectual level of the audience for which the resource is intended.", true, attributes, null);
	}

	/**
	 * Gets the note stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the note stack
	 */
	public static SectionStackSection getNoteStack(boolean expanded) {
		return getSomeStack(expanded, "Note", new GetGeneralLayout(new Attribute(TextAreaItem.class, "note", "Note", "Roughly equivalent to MARC 21 fields 5XX."),
				new Attribute[] { new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."),
						getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display."), ATTR_XLINK,
						ATTR_ID, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
	}

	/**
	 * Gets the subject stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the subject stack
	 */
	public static SectionStackSection getSubjectStack(boolean expanded) {
		return getSomeStack(expanded, "Subject", new GetSubjectLayout());
	}

	/**
	 * Gets the classification stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the classification stack
	 */
	public static SectionStackSection getClassificationStack(boolean expanded) {
		Attribute[] attributes = new Attribute[] {
				ATTR_AUTHORITY,
				new Attribute(
						TextItem.class,
						"edition",
						"Edition",
						"This attribute contains a designation of the edition of the particular classification scheme indicated in authority for those schemes that are issued in editions (e.g. DDC)."),
				getDisplayLabel("Equivalent to MARC 21 field 050 subfield $3."), ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		return getStackSectionWithAttributes("Classifications", "Classification", "Equivalent to MARC fields 050-08X, subfields $a and $b.", true, attributes, null);
	}

	/**
	 * Gets the identifier stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the identifier stack
	 */
	public static SectionStackSection getIdentifierStack(boolean expanded) {
		Attribute[] attributes = new Attribute[] {
				new Attribute(
						TextItem.class,
						"type",
						"Type",
						"There is no controlled list of abstract types. (Suggested values: doi, hdl, isbn, ismn, isrc, issn,	issue number,	istc, lccn, loca, matrix number, music plate, music publisher, sici, stock number, upc, uri, videorecording identifier)"),
				getDisplayLabel("Equivalent to MARC 21 field 050 subfield $3."), ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT,
				new Attribute(CheckboxItem.class, "invalid", "Invalid", "If invalid='yes' is not present, the identifier is assumed to be valid.") };
		return getStackSectionWithAttributes("Identifiers", "Identifier", "Roughly equivalent to MARC fields 010, 020, 022, 024, 856.", true, attributes, null);
	}

	/**
	 * Gets the location stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the location stack
	 */
	public static SectionStackSection getLocationStack(boolean expanded) {
		return getSomeStack(expanded, "Location", new GetLocationLayout());
	}

	/**
	 * Gets the access condition stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the access condition stack
	 */
	public static SectionStackSection getAccessConditionStack(boolean expanded) {
		return getSomeStack(
				expanded,
				"Access Condition",
				new GetGeneralLayout(
						new Attribute(TextAreaItem.class, "access_condition", "Access Condition", "Roughly equivalent to MARC 21 fields 506 and 540."),
						new Attribute[] {
								new Attribute(
										TextItem.class,
										"type",
										"Type",
										"There is no controlled list of abstract types. Suggested values are: restriction on access (equivalent to MARC 21 field 506) and use and reproduction (equivalent to MARC 21 field 540)."),
								getDisplayLabel("This attribute is intended to be used when additional text associated with the access conditions is necessary for display."),
								ATTR_XLINK, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
	}

	/**
	 * Gets the extension stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the extension stack
	 */
	public static SectionStackSection getExtensionStack(boolean expanded) {
		return getSomeStack(
				expanded,
				"Access Condition",
				new GetGeneralLayout(
						new Attribute(
								TextAreaItem.class,
								"extension",
								"Extension",
								"Used to provide for additional information not covered by MODS. It may be used for elements that are local to the creator of the data, similar to MARC 21 9XX fields."),
						new Attribute[] { new Attribute(TextItem.class, "namespace", "Namespace", "Namespace") }));
	}

	/**
	 * Gets the record info stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the record info stack
	 */
	public static SectionStackSection getRecordInfoStack(boolean expanded) {
		return getSomeStack(expanded, "Record Info", new GetRecordInfoLayout());
	}

	/**
	 * Gets the part stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the part stack
	 */
	public static SectionStackSection getPartStack(boolean expanded) {
		return getSomeStack(expanded, "Part", new GetPartLayout());
	}

	/**
	 * Gets the place stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the place stack
	 */
	private static SectionStackSection getPlaceStack(boolean expanded, List<PlaceTypeClient> values, List<PlaceHolder> holders) {
		GetPlaceLayout getPlaceLayout = new GetPlaceLayout();
		getPlaceLayout.setHolders(holders);
		getPlaceLayout.setValues(values);
		return getSomeStack(expanded, "Place", getPlaceLayout);
	}

	/**
	 * Gets the title info layout.
	 * 
	 * @return the title info layout
	 */
	public static VLayout getTitleInfoLayout(final TitleInfoTypeClient values, final TitleInfoHolder holder) {
		final VLayout layout = new VLayout();

		final Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("abbreviated", "equivalent to MARC 21 field 210");
		tooltips.put("translated", "equivalent to MARC 21 fields 242, 246");
		tooltips.put("alternative", "equivalent to MARC 21 fields 246, 740");
		tooltips.put("uniform", "equivalent to MARC 21 fields 130, 240, 730");

		Attribute[] attributes = new Attribute[] {
				new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", tooltips, values == null ? "" : values.getType()),
				ATTR_LANG(values == null ? "" : values.getLang()),
				getDisplayLabel(
						"This attribute is intended to be used when additional text associated with the title is needed for display. It is equivalent to MARC 21 field 246 subfield $i.",
						values == null ? "" : values.getDisplayLabel()), ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
				ATTR_AUTHORITY(values == null ? "" : values.getAuthority()), ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()),
				ATTR_SCRIPT(values == null ? "" : values.getScript()), ATTR_ID, ATTR_XLINK(values == null ? "" : values.getXlink()) };
		DynamicForm form = getAttributes(true, attributes);
		layout.addMember(form);
		holder.setAttributeForm(form);
		layout.setLeaveScrollbarGap(true);
		final SectionStack sectionStack = new SectionStack();
		// sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.VISIBLE);
		layout.setExtraSpace(40);
		// sectionStack.

		sectionStack.addSection(getStackSection("Titles", "Title", "title without the <titleInfo> type attribute is roughly equivalent to MARC 21 field 245", true,
				values == null ? null : values.getTitle(), holder.getTitles()));
		sectionStack.addSection(getStackSection("Sub Titles", "Sub Title", "Equivalent to MARC 21 fields 242, 245, 246 subfield $b.", false, values == null ? null
				: values.getSubTitle(), holder.getSubTitles()));
		sectionStack.addSection(getStackSection("Part Numbers", "Part Number",
				"Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $n.", false, values == null ? null : values.getPartNumber(),
				holder.getPartNumbers()));
		sectionStack.addSection(getStackSection("Part Names", "Part Name", "Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $p.",
				false, values == null ? null : values.getPartName(), holder.getPartNames()));
		sectionStack.addSection(getStackSection("Non Sort", "Non Sort",
				"It is equivalent to the new technique in MARC 21 that uses control characters to surround data disregarded for sorting.", false, values == null ? null
						: values.getNonSort(), holder.getNonSorts()));
		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the name layout.
	 * 
	 * @return the name layout
	 */
	public static VLayout getNameLayout(final NameTypeClient values, final NameHolder holder) {
		final VLayout layout = new VLayout();

		Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("personal", "equivalent to MARC 21 fields 100, 700");
		tooltips.put("corporate", "equivalent to MARC 21 fields 110, 710");
		tooltips.put("conference", "equivalent to MARC 21 fields 111, 711");
		Attribute[] attributes = new Attribute[] {
				new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", tooltips, values == null ? "" : values.getType().value()),
				ATTR_LANG(values == null ? "" : values.getLang()), ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
				ATTR_XML_LANG(values == null ? "" : values.getXmlLang()), ATTR_SCRIPT(values == null ? "" : values.getScript()),
				ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()), ATTR_XLINK(values == null ? "" : values.getXlink()),
				ATTR_ID(values == null ? "" : values.getId()) };
		DynamicForm form = getAttributes(true, attributes);
		layout.addMember(form);
		holder.setAttributeForm(form);
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setMinHeight(500);
		sectionStack.setMinMemberSize(300);
		sectionStack.setOverflow(Overflow.VISIBLE);
		layout.setExtraSpace(40);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();

		tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("date", "The attribute date is used to parse dates that are not integral parts of a name.");
		tooltips.put("family", "part of a personal name (surname)");
		tooltips.put("given ", "part of a personal name (first name)");
		tooltips.put("termsOfAddress ", "It is roughly equivalent to MARC X00 fields, subfields $b and $c.");

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
				.addSection(getStackSectionWithAttributes(
						"Name Parts",
						"Name Part",
						"Includes each part of the name that is parsed. Parsing is used to indicate a date associated with the name, to parse the parts of a corporate name (MARC 21 fields X10 subfields $a and $b).",
						false, new Attribute[] { new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", tooltips) }, vals, holder.getNameParts(), false));
		sectionStack.addSection(getStackSection("Display Forms", "Display Form", "This data is usually carried in MARC 21 field 245 subfield $c.", false,
				values == null ? null : values.getDisplayForm(), holder.getDisplayForms()));
		sectionStack
				.addSection(getStackSection(
						"Affiliations",
						"Affiliation",
						"Contains the name of an organization, institution, etc. with which the entity recorded in <name> was associated at the time that the resource was created. (MARS21 100, 700 $u)",
						false, values == null ? null : values.getAffiliation(), holder.getAffiliations()));
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
		sectionStack.addSection(getStackSectionWithAttributes("Roles", "Role", "", false, new Attribute[] { ATTR_TYPE_TEXT_CODE, ATTR_AUTHORITY }, vals,
				holder.getRoles(), false));
		sectionStack.addSection(getStackSection("Descriptions", "Description",
				"May be used to give a textual description for a name when necessary, for example, to distinguish from other names.", false, values == null ? null
						: values.getDescription(), holder.getDescriptions()));

		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the type of resource layout.
	 * 
	 * @return the type of resource layout
	 */
	public static VLayout getTypeOfResourceLayout(final TypeOfResourceTypeClient values, final TypeOfResourceHolder holder) {
		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] {
				new Attribute(CheckboxItem.class, ModsConstants.COLLECTION, "Collection",
						"This attribute is used as collection='yes' when the resource is a collection. (Leader/07 code 'c')", values == null ? ""
								: values.getCollection() == null ? "" : values.getCollection().value()),
				new Attribute(CheckboxItem.class, ModsConstants.MANUSCRIPT, "Manuscript",
						"This attribute is used as manuscript='yes' when the resource is written in handwriting or typescript. (Leader/06 values 'd', 'f' and 't')",
						values == null ? "" : values.getManuscript() == null ? "" : values.getManuscript().value()) };
		DynamicForm form = getAttributes(true, attributes);
		layout.addMember(form);
		holder.setAttributeForm(form);
		layout.setExtraSpace(40);

		final Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "Nothing.");
		tooltips.put("cartographic", "Includes MARC 21 Leader/06 values 'e' and 'f'.");
		tooltips.put("notated music", "Includes MARC 21 Leader/06 values 'c' and 'd'.");
		tooltips
				.put(
						"sound recording",
						"This value by itself is used when a mixture of musical and nonmusical sound recordings occurs in a resource or when a user does not want to make a distinction between musical and nonmusical. ");
		tooltips.put("sound recording-musical", "This is roughly equivalent to MARC 21 Leader/06 value 'j'.");
		tooltips.put("sound recording-nonmusical", "This is roughly equivalent to MARC 21 Leader/06 value 'i'.");
		tooltips.put("still image", "This is roughly equivalent to MARC 21 Leader/06 value 'k'.");
		tooltips.put("moving image", "This is roughly equivalent to MARC 21 Leader/06 value 'g'.");
		tooltips.put("three dimensional object", "This is roughly equivalent to MARC 21 Leader/06 value 'r'.");
		tooltips.put("software, multimedia", "This is roughly equivalent to MARC 21 Leader/06 value 'm'.");
		tooltips.put("mixed material", "This is roughly equivalent to MARC 21 Leader/06 value 'p'.");
		form = new DynamicForm();
		form.setItems(newItem(new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", tooltips, values == null ? "" : values.getValue())));
		layout.addMember(form);
		holder.setAttributeForm2(form);

		return layout;
	}

	/**
	 * Gets the genre layout.
	 * 
	 * @return the genre layout
	 */
	public static VLayout getGenreLayout(final GenreTypeClient values, final GenreHolder holder) {
		final VLayout layout = new VLayout();
		Attribute[] attributes = new Attribute[] {
				new Attribute(TextItem.class, ModsConstants.TYPE, "Type",
						"This attribute may be used if desired to distinguish different aspects of genre, such as class, work type, or style."),
				ATTR_LANG(values == null ? "" : values.getLang()), ATTR_AUTHORITY(values == null ? "" : values.getAuthority()),
				ATTR_XML_LANG(values == null ? "" : values.getXmlLang()), ATTR_SCRIPT(values == null ? "" : values.getScript()),
				ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()) };
		DynamicForm form = getAttributes(true, attributes);
		layout.addMember(form);
		holder.setAttributeForm(form);
		layout.setExtraSpace(40);

		form = new DynamicForm();
		form.setItems(newItem(new Attribute(TextItem.class, ModsConstants.GENRE, "Genre",
				"A term(s) that designates a category characterizing a particular style, form, or content, such as artistic, musical, literary composition, etc. ",
				values == null ? "" : values.getValue())));
		layout.addMember(form);
		holder.setAttributeForm2(form);

		return layout;
	}

	/**
	 * Gets the date layout.
	 * 
	 * @param name
	 *          the name
	 * @param title
	 *          the title
	 * @param tooltip
	 *          the tooltip
	 * @param attribute
	 *          the attribute
	 * @param keyDate
	 *          the key date
	 * @return the date layout
	 */
	public static VLayout getDateLayout(String name, String title, String tooltip, Attribute attribute, boolean keyDate, DateTypeClient value, DateHolder holder) {
		final VLayout layout = new VLayout();
		Attribute[] attributes = null;
		final Attribute encoding = ATTR_ENCODING(value == null ? "" : value.getEncoding());
		if (attribute != null) {
			attribute.setValue(value == null ? "" : value.getValue());
			if (keyDate) {
				attributes = new Attribute[] { attribute, encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
						ATTR_KEY_DATE(value == null ? "" : value.getKeyDate() == null ? "" : value.getKeyDate().value()),
						ATTR_QUALIFIER(value == null ? "" : value.getQualifier()) };
			} else {
				attributes = new Attribute[] { attribute, encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
						ATTR_QUALIFIER(value == null ? "" : value.getQualifier()) };
			}
			attributes = new Attribute[] { attribute, encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
					ATTR_KEY_DATE(value == null ? "" : value.getKeyDate() == null ? "" : value.getKeyDate().value()),
					ATTR_QUALIFIER(value == null ? "" : value.getQualifier()) };
		} else if (keyDate) {
			attributes = new Attribute[] { encoding, ATTR_POINT(value == null ? "" : value.getPoint()),
					ATTR_KEY_DATE(value == null ? "" : value.getKeyDate() == null ? "" : value.getKeyDate().value()),
					ATTR_QUALIFIER(value == null ? "" : value.getQualifier()) };
		} else {
			attributes = new Attribute[] { encoding, ATTR_POINT(value == null ? "" : value.getPoint()), ATTR_QUALIFIER(value == null ? "" : value.getQualifier()) };
		}

		final DynamicForm formAttr = getAttributes(true, attributes);

		FormItem item1 = newItem(new Attribute(TextItem.class, name, title, tooltip, value == null ? "" : value.getValue()));
		FormItem item2 = newItem(new Attribute(DateItem.class, name, title, tooltip, value == null ? "" : value.getValue()));

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
	 * @return the place layout
	 */
	private static VLayout getPlaceLayout(PlaceTypeClient value, PlaceHolder holder) {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.setStyleName("metadata-sectionstack-topmargin");
		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Place Terms",
						"Place Term",
						"Used to express place in a textual or coded form. If both a code and a term are given that represent the same place, use one <place> and multiple occurrences of <placeTerm>. If different places, repeat <place><placeTerm>.",
						true, new Attribute[] { ATTR_TYPE_TEXT_CODE, ATTR_AUTHORITY }, null));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the origin info layout.
	 * 
	 * @return the origin info layout
	 */
	public static VLayout getOriginInfoLayout(OriginInfoTypeClient values, OriginInfoHolder holder) {
		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] { ATTR_LANG(values == null ? "" : values.getLang()), ATTR_XML_LANG(values == null ? "" : values.getXmlLang()),
				ATTR_SCRIPT(values == null ? "" : values.getScript()), ATTR_TRANSLITERATION(values == null ? "" : values.getTransliteration()) };
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
		sectionStack.addSection(getPlaceStack(false, values == null ? null : values.getPlace(), holder.getPlaces()));
		sectionStack.addSection(getStackSection("Publishers", "Publisher", "It is equivalent to MARC 21 field 260 subfield $b.", false, values == null ? null
				: values.getPublisher(), holder.getPublishers()));
		sectionStack
				.addSection(getSomeStack(
						false,
						"Dates Issued",
						new GetDateLayout(
								ModsConstants.DATE_ISSUED,
								"Issued Date",
								"Equivalent to dates in MARC 21 field 260 subfield $c. It may be in textual or structured form. <dateIssued> may be recorded according to the MARC 21 rules for field 008/07-14 with encoding='marc.'",
								values == null ? null : values.getDateIssued(), holder.getDatesIssued())));
		sectionStack
				.addSection(getSomeStack(
						false,
						"Dates Created",
						new GetDateLayout(
								ModsConstants.DATE_CREATED,
								"Created Date",
								"This type of date is recorded in various places in MARC 21: field 260 subfield $g, for some types of material in field 260 subfield $c, date of original in field 534 subfield $c and date of reproduction in field 533 subfield $d.",
								values == null ? null : values.getDateCreated(), holder.getDatesCreated())));
		sectionStack.addSection(getSomeStack(false, "Dates Captured", new GetDateLayout(ModsConstants.DATE_CAPTURED, "Captured Date",
				"This could be a date that the resource was captured or could also be the date of creation. This is roughly equivalent to MARC field 033.",
				values == null ? null : values.getDateCaptured(), holder.getDatesCaptured())));
		sectionStack.addSection(getSomeStack(false, "Dates Valid", new GetDateLayout(ModsConstants.DATE_VALID, " Valid Date",
				"Roughly equivalent to MARC 21 field 046 subfields $m and $n. ", values == null ? null : values.getDateValid(), holder.getDatesValid())));
		sectionStack.addSection(getSomeStack(false, "Dates Modified", new GetDateLayout(ModsConstants.DATE_MODIFIED, "Modified Date",
				"Roughly equivalent to MARC 21 field 046 subfield $j.", values == null ? null : values.getDateModified(), holder.getDatesModified())));
		sectionStack.addSection(getSomeStack(
				false,
				"Dates Copyright",
				new GetDateLayout(ModsConstants.DATE_COPYRIGHT, "Copyright Date",
						"A copyright date may be used in 260$c preceded by the letter 'c' if it appears that way on the resource.", values == null ? null : values
								.getCopyrightDate(), holder.getDatesCopyright())));
		sectionStack
				.addSection(getSomeStack(
						false,
						"Other Dates",
						new GetDateLayout(
								ModsConstants.DATE_OTHER,
								"Other Date",
								"Roughly equivalent to MARC 21 field 046 subfield $j.",
								new Attribute(TextItem.class, "type", "Type",
										"This attribute allows for extensiblity of date types so that other specific date types may be designated if not given a separate element in MODS."),
								true, values == null ? null : values.getDateOther(), holder.getDatesOther(), true)));
		sectionStack.addSection(getStackSection("Editions", "Edition", "Equivalent to MARC 21 fields 242, 245, 246 subfield $b.", false));

		// getStackSectionWithAttributes(final String label1, final String label2,
		// final String tooltip, final boolean expanded,
		// final Attribute[] attributes, final List<List<String>> values,
		// MetadataHolder holder, boolean flat)

		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, ModsConstants.ISSUANCE, "Issuance", new HashMap<String, String>() {
			{
				put("", "This element will be omitted.");
				put("continuing", "The value is equivalent to codes 'b,' 'i' and 's' in Leader/07.");
				put("monographic", "The value is equivalent to codes 'a,' 'c,' 'd' and 'm' in Leader/07.");
			}
		}, values == null ? null : values.getIssuance() == null ? null : values.getIssuance().get(0)), false, null, holder.getIssuances()));
		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Frequencies",
						"Frequency",
						"Equivalent to the information in MARC 21 008/18 (Frequency) for Continuing Resources, where it is given in coded form or in field 310 (Current Publication Frequency), where it is given in textual form.",
						false, new Attribute[] { ATTR_AUTHORITY("") }, values == null ? null : ClientUtils.toListOfListOfStrings(values.getFrequency()),
						holder.getFrequencies(), false));

		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the physicial description layout.
	 * 
	 * @return the physicial description layout
	 */
	private static VLayout getPhysicialDescriptionLayout() {

		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] { ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		layout.addMember(getAttributes(true, attributes));
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setMinHeight(500);
		sectionStack.setMinMemberSize(300);
		sectionStack.setOverflow(Overflow.VISIBLE);
		layout.setExtraSpace(40);
		// sectionStack.setOverflow(Overflow.AUTO);

		attributes = new Attribute[] {
				new Attribute(TextItem.class, "type", "Type",
						"Used if desired to specify whether the form concerns materials or techniques, e.g. type='material': oil paint; type='technique': painting."),
				ATTR_AUTHORITY };
		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Forms",
						"Form",
						"Includes information that specifies the physical form or medium of material for a resource. Either a controlled list of values or free text may be used.",
						false, attributes, null));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, "reformatting_quality", "Reformatting Quality",
				new HashMap<String, String>() {
					{
						put("", "This element will be omitted.");
						put("access",
								"The electronic resource is intended to support current electronic access to the original item (i.e., reference use), but is not sufficient to serve as a preservation copy.");
						put("preservation",
								"The electronic resource was created via reformatting to help preserve the original item. The capture and storage techniques ensure high-quality, long-term protection. ");
						put("replacement",
								"The electronic resource is of high enough quality to serve as a replacement if the original is lost, damaged, or destroyed. If serving more than one purpose, the element may be repeated. ");
					}
				}), false, null, null));
		sectionStack.addSection(getStackSection("Internet Media Types", "Internet Media Type",
				"Roughly equivalent to MARC 21 field 856 subfield $q, although subfield $q may also contain other designations of electronic format.", false));
		sectionStack.addSection(getStackSection("Extents", "Extent", "Roughly equivalent to MARC 21 fields 300 subfields $a, $b, $c, and $e and 306 subfield $a.",
				false));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, "digital_origin", "Digital Origin", new HashMap<String, String>() {
			{
				put("", "This element will bauthoritye omitted.");
				put("born digital", "A resource was created and is intended to remain in digital form. (No MARC equivalent, but includes value 'c')");
				put("reformatted digital", "A resource was created by digitization of the original non-digital form. (MARC 007/11 value 'a')");
				put("digitized microfilm", "A resource was created by digitizing a microform (MARC 007/11 value 'b')");
				put("digitized other analog",
						"A resource was created by digitizing another form of the resource not covered by the other values, e.g. an intermediate form such as photocopy, transparency, slide, etc. (MARC 007/11 value 'd')");
			}
		}), false, null, null));
		attributes = new Attribute[] {
				new Attribute(
						TextItem.class,
						"type",
						"Type",
						"Roughly equivalent to the types of notes that may be contained in MARC 21 fields 340, 351 or general 500 notes concerning physical condition, characteristics, etc."),
				getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display."), ATTR_XLINK,
				ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION };
		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Notes",
						new Attribute(TextAreaItem.class, "note", "Note",
								"Includes information that specifies the physical form or medium of material for a resource. Either a controlled list of values or free text may be used."),
						false, attributes, null));

		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the general layout.
	 * 
	 * @param head
	 *          the head
	 * @param attribs
	 *          the attribs
	 * @return the general layout
	 */
	private static VLayout getGeneralLayout(final Attribute head, final Attribute[] attribs) {
		final VLayout layout = new VLayout();
		layout.addMember(getAttributes(true, attribs));
		DynamicForm form = new DynamicForm();

		FormItem item = newItem(head);
		if (head.getType() == TextAreaItem.class)
			item.setWidth(600);
		else {
			item.setWidth(200);
		}
		form.setItems(item);
		layout.addMember(form);
		layout.setExtraSpace(40);
		return layout;
	}

	/**
	 * Gets the general deep layout.
	 * 
	 * @param attribs
	 *          the attribs
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
				if (first)
					first = false;
			}
		}
		return layout;
	}

	/**
	 * Gets the language layout.
	 * 
	 * @return the language layout
	 */
	private static VLayout getLanguageLayout() {
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
		// sectionStack.setOverflow(Overflow.AUTO);
		layout
				.addMember(getAttributes(
						true,
						new Attribute[] { new Attribute(
								TextItem.class,
								"object_part",
								"Object Part",
								"Designates which part of the resource is in the language supplied, e.g. <language objectPart='summary'><languageTerm authority='iso639-2b'>spa</languageTerm></language> indicates that only the summary is in Spanish.") }));

		sectionStack.addSection(getStackSectionWithAttributes("Language Terms", "Language Term",
				"contains the language(s) of the content of the resource. It may be expressed in textual or coded form.", true, new Attribute[] { ATTR_TYPE_TEXT_CODE,
						ATTR_LANG_AUTHORITY }, null));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the copy information layout.
	 * 
	 * @return the copy information layout
	 */
	private static VLayout getCopyInformationLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(TextItem.class, "form", "Form",
				"Designation of a particular physical presentation of a resource."), false, new Attribute[] { ATTR_AUTHORITY }, null));
		sectionStack
				.addSection(getStackSection(new Attribute(TextItem.class, "sublocation", "Sublocation",
						"Equivalent to MARC 852 $b (Sublocation or collection), $c (Shelving location), $e (Address), which are expressed together as a string."), false,
						null));
		sectionStack
				.addSection(getStackSection(
						new Attribute(
								TextItem.class,
								"shelf_locator",
								"Shelf Locator",
								"Equivalent to MARC 852 $h (Classification part), $i (Item part), $j (Shelving control number), $k (Call number prefix), $l (Shelving form of title), $m (Call number suffix) and $t (Copy number)."),
						false, null));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "electronic_locator", "Electronic Locator",
				"This is a copy-specific form of the MODS <location><url>, without its attributes."), false, null));
		getSomeStack(false, "Note", new GetGeneralLayout(new Attribute(TextAreaItem.class, "note", "Note", "Note relating to a specific copy of a document. "),
				new Attribute[] { new Attribute(TextItem.class, "type", "Type", "This attribute is not controlled by a list and thus is open-ended."),
						getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display. ") }));
		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Enumeration and Chronology",
						new Attribute(
								TextItem.class,
								"enumeration_and_chronology",
								"Enumeration and Chronology",
								"Unparsed string that comprises a summary holdings statement. If more granularity is needed, a parsed statement using an external schema may be used within <holdingExternal>."),
						false, new Attribute[] { new Attribute(SelectItem.class, "unit_type", "Unit Type", new HashMap<String, String>() {
							{
								put("", "This attribute will be omitted.");
								put("1", "Information is about the basic bibliographic unit. (863 or 866)");
								put("2", "Information is about supplementary material to the basic unit. (864 or 867)");
								put("3", "Information is about index(es) to the basic unit. (865 or 868)");
							}
						}) }, null));
		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the subject layout.
	 * 
	 * @return the subject layout
	 */
	private static VLayout getSubjectLayout() {
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
		// sectionStack.setOverflow(Overflow.AUTO);
		layout.addMember(getAttributes(true, new Attribute[] { ATTR_AUTHORITY, ATTR_XLINK, ATTR_ID, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));

		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "topic", "Topic",
				"Equivalent to MARC 21 fields 650 and 6XX subfields $x and $v (with authority attribute defined) and MARC 21 field 653 with no authority attribute."),
				false, null));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "geographic", "Geographic", "Equivalent to MARC 21 field 651 and 6XX subfield $z."),
				false, null));
		sectionStack.addSection(getSomeStack(false, "Temporal", new GetDateLayout("temporal", "Temporal", "Equivalent to MARC 21 fields 045 and 6XX subfield $y.",
				null, null)));
		// TODO: handle
		GetTitleInfoLayout getTitleInfoLayout = new GetTitleInfoLayout();
		getTitleInfoLayout.setHolders(new ArrayList<Object>());
		sectionStack.addSection(getSomeStack(false, "Title Info", getTitleInfoLayout));
		// TODO: handle
		GetNameLayout getNameLayout = new GetNameLayout();
		getNameLayout.setHolders(new ArrayList<Object>());
		sectionStack.addSection(getSomeStack(false, "Name", getNameLayout));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "genre", "Genre", "Equivalent to subfield $v in MARC 21 6XX fields."), false, null));

		Attribute[] attributes = new Attribute[] {
				new Attribute(TextItem.class, "continent", "Continent", "Includes Asia, Africa, Europe, North America, South America."),
				new Attribute(TextItem.class, "province", "Province", "Includes first order political divisions called provinces within a country, e.g. Canada."),
				new Attribute(TextItem.class, "region", "Region",
						"Includes regions that have status as a jurisdiction, usually incorporating more than one first level jurisdiction."),
				new Attribute(TextItem.class, "state", "State",
						"Includes first order political divisions called states within a country, e.g. in U.S., Argentina, Italy. Use also for France département."),
				new Attribute(TextItem.class, "territory", "Territory",
						"Name of a geographical area belonging to or under the jurisdiction of a governmental authority ."),
				new Attribute(TextItem.class, "county", "County", "Name of the largest local administrative unit in various countries, e.g. England."),
				new Attribute(TextItem.class, "city", "City", "Name of an inhabited place incorporated as a city, town, etc."),
				new Attribute(TextItem.class, "city_section", "City Section", "Name of a smaller unit within a populated place, e.g., neighborhoods, parks or streets."),
				new Attribute(TextItem.class, "island", "Island",
						"Name of a tract of land surrounded by water and smaller than a continent but is not itself a separate country."),
				new Attribute(TextItem.class, "area", "Area", "Name of a non-jurisdictional geographic entity."),
				new Attribute(
						TextItem.class,
						"extraterrestrial_area",
						"Extraterrestrial Area",
						"Name of any extraterrestrial entity or space, including solar systems, galaxies, star systems, and planets as well as geographic features of individual planets."), };
		sectionStack.addSection(getSomeStack(false, "Hierarchical Geographic", new GetGeneralDeepLayout(attributes)));

		attributes = new Attribute[] {
				new Attribute(
						TextItem.class,
						"coordinates",
						"Coordinates",
						"One or more statements may be supplied. If one is supplied, it is a point (i.e., a single location); if two, it is a line; if more than two, it is a n-sided polygon where n=number of coordinates assigned. No three points should be co-linear, and coordinates should be supplied in polygon-traversal order."),
				new Attribute(TextItem.class, "scale", "Scale",
						"It may include any equivalency statements, vertical scales or vertical exaggeration statements for relief models and other three-dimensional items."),
				new Attribute(TextItem.class, "projection", "Projection", "Provides a statement of projection.") };
		sectionStack.addSection(getSomeStack(false, "Cartographics", new GetGeneralDeepLayout(attributes)));

		sectionStack.addSection(getStackSectionWithAttributes("Geographic Code", new Attribute(TextItem.class, "geographic_code", "Geographic Code",
				"Equivalent to MARC 21 field 043."), false, new Attribute[] { new Attribute(SelectItem.class, "authority", "Authority", new HashMap<String, String>() {
			{
				put("", "This attribute will be omitted.");
				put("marcgac", "marcgac");
				put("marccountry", "marccountry");
				put("iso3166", "iso3166");
			}
		}) }, null));
		sectionStack
				.addSection(getStackSection(new Attribute(TextItem.class, "occupation", "Occupation", "Roughtly equivalent to MARC 21 field 656."), false, null));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the location layout.
	 * 
	 * @return the location layout
	 */
	private static VLayout getLocationLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setMinHeight(500);
		sectionStack.setMinMemberSize(300);
		sectionStack.setOverflow(Overflow.VISIBLE);
		Attribute[] attributes = new Attribute[] {
				getDisplayLabel("Equivalent to MARC 21 field 852 subfield $3."),
				new Attribute(TextItem.class, "type", "Type",
						"This attribute is used to indicate different kinds of locations, e.g. current, discovery, former, creation."), ATTR_AUTHORITY, ATTR_XLINK,
				ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		sectionStack.addSection(getStackSectionWithAttributes("Physical Location", new Attribute(TextItem.class, "physical_location", "Physical Location",
				"Equivalent to MARC 21 field 852 subfields $a, $b and $e."), true, attributes, null));
		sectionStack
				.addSection(getStackSection(
						new Attribute(
								TextItem.class,
								"shelfLocator",
								"Shelf Locator",
								"This information is equivalent to MARC 852 $h (Classification part), $i (Item part), $j (Shelving control number), $k (Call number prefix), $l (Shelving form of title), $m (Call number suffix) and $t (Copy number)."),
						false, null));
		attributes = new Attribute[] {
				getDisplayLabel("Equivalent to MARC 21 field 856 subfields $y and $3."),
				new Attribute(DateItem.class, "date_last_accessed", "Date Last Accessed", "There is no MARC equivalent for 'dateLastAccessed'."),
				new Attribute(TextAreaItem.class, "note", "Note",
						"This attribute includes notes that are associated with the link that is included as the value of the <url> element. It is generally free text."),
				new Attribute(SelectItem.class, "access", "Access", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("preview", "Indicates a link to a thumbnail or snippet of text.");
						put("raw object",
								"Indicates a direct link to the object described (e.g. a jpg or pdf document). Used only when the object is represented by a single file.");
						put("object in context", "Indicates a link to the object within the context of its environment (with associated metadata, navigation, etc.)");
					}
				}), new Attribute(SelectItem.class, "usage", "Usage", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("primary display", "Indicates that the link is the most appropriate to display for end users.");
					}
				}) };
		sectionStack.addSection(getSomeStack(false, "Holding Simple", new GetCopyInformationLayout()));
		sectionStack
				.addSection(getSimpleSection(
						new Attribute(
								TextAreaItem.class,
								"holding_external",
								"Holding External",
								"Holdings information that uses a schema defined externally to MODS. <holdingExternal> may include more detailed holdings information than that accommodated by the MODS schema. An example is ISO 20775 and its accompanying schema."),
						false));
		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the part layout.
	 * 
	 * @return the part layout
	 */
	private static VLayout getPartLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setMinHeight(500);
		sectionStack.setMinMemberSize(300);
		sectionStack.setOverflow(Overflow.VISIBLE);
		Attribute[] attributes = new Attribute[] {
				new Attribute(TextItem.class, "type", "Type",
						"A designation of a document segment type. Suggested values include volume, issue, chapter, section, paragraph, track. Other values may be used as needed."),
				new Attribute(TextItem.class, "order", "Order", "An integer that designates the sequence of parts."), ATTR_ID };

		layout.addMember(getAttributes(true, attributes));
		sectionStack.addSection(getSomeStack(false, "Detail", new GetDetailLayout()));
		sectionStack.addSection(getSomeStack(false, "Extent", new GetExtentLayout()));
		sectionStack.addSection(getSomeStack(false, "Date", new GetDateLayout("date", "Date", "Contains date information relevant to the part described.", null,
				false, null, null)));
		sectionStack
				.addSection(getStackSection(
						new Attribute(TextAreaItem.class, "text", "Text",
								"Contains unparsed information in textual form about the part. When used in relatedItem type='host', it is equivalent to MARC 21 field 773 subfield $g."),
						false, null));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the detail layout.
	 * 
	 * @return the detail layout
	 */
	private static VLayout getDetailLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);

		Attribute[] attributes = new Attribute[] {
				new Attribute(TextItem.class, "type", "Type",
						"The type of part described. Suggested values include part, volume, issue, chapter, section, paragraph, track. Other values may be used as needed."),
				new Attribute(
						TextItem.class,
						"level",
						"Level",
						"Describes the level of numbering in the host/parent item. This is used to ensure that the numbering is retained in the proper order. An example is: 'v.2, no. 3'.") };
		layout.addMember(getAttributes(true, attributes));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "number", "Number", "Contains the actual number within the part."), false, null));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "caption", "Caption",
				"Contains the caption describing the enumeration within a part. This may be the same as type, but conveys what is on the item being described. "),
				false, null));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "title", "Title",
				"Contains the title of the part. Only include if this is different than the title in <titleInfo><title>."), false, null));
		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the extent layout.
	 * 
	 * @return the extent layout
	 */
	private static VLayout getExtentLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);

		Attribute[] attributes = new Attribute[] { new Attribute(TextItem.class, "unit", "Unit", "Suggested values include page, minute, etc.") };
		layout.addMember(getAttributes(false, attributes));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "start", "Start",
				"Contains the beginning unit of the extent within a part (e.g., first page)."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "end", "End", "Contains the ending unit of the extent within a part."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "total", "Total",
				"Contains the total number of units within a part, rather than specific units."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "list", "List",
				"Contains a textual listing of the units within a part (e.g., 'pp. 5-9'.)"), false));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the language of cataloging layout.
	 * 
	 * @return the language of cataloging layout
	 */
	private static VLayout getLanguageOfCatalogingLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();

		Attribute[] attributes = new Attribute[] { ATTR_TYPE_TEXT_CODE, ATTR_LANG_AUTHORITY };
		sectionStack.addSection(getStackSectionWithAttributes("Language Term", new Attribute(TextItem.class, "language_term", "Language Term",
				"contains the language(s) of the content of the resource. It may be expressed in textual or coded form."), true, attributes, null));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the record info layout.
	 * 
	 * @return the record info layout
	 */
	private static VLayout getRecordInfoLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setMinHeight(500);
		sectionStack.setMinMemberSize(300);
		sectionStack.setOverflow(Overflow.VISIBLE);
		// sectionStack.setOverflow(Overflow.AUTO);
		Attribute[] attributes = new Attribute[] { ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION };
		layout.addMember(getAttributes(true, attributes));

		sectionStack.addSection(getStackSectionWithAttributes("Record Content Source", new Attribute(TextItem.class, "record_content_source",
				"Record Content Source", "Roughly equivalent to MARC 21 field 040."), false, new Attribute[] { ATTR_AUTHORITY }, null));
		sectionStack.addSection(getSomeStack(false, "Record Creation Date", new GetDateLayout("record_creation_date", "Record Creation Date",
				"Roughly equivalent to MARC 21 field 008/00-05.", null, false, null, null)));
		sectionStack.addSection(getSomeStack(false, "Record Change Date", new GetDateLayout("record_change_date", "Record Creation Date",
				"May serve as a version identifier for the record. It is roughly equivalent to MARC 21 field 005.", null, false, null, null)));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(TextItem.class, "record_identifier", "Record Identifier",
				"Roughly equivalent to MARC 21 field 001."), false, new Attribute[] { new Attribute(TextItem.class, "source", "Source",
				"Roughly equivalent to MARC 21 field 003.") }, null));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "record_origin", "Record Origin",
				"Intended to show the origin, or provenance of the MODS record. There is no MARC equivalent to <recordOrigin>."), false, null));
		sectionStack.addSection(getSomeStack(false, "Language of Cataloging", new GetLanguageOfCatalogingLayout()));
		sectionStack.addSection(getStackSectionWithAttributes("Description Standard", new Attribute(TextItem.class, "description_standard", "Description Standard",
				"Roughly equivalent to the information found in MARC 21 field 040$e or Leader/18."), false, new Attribute[] { ATTR_AUTHORITY }, null));

		layout.addMember(sectionStack);
		return layout;
	}

}
