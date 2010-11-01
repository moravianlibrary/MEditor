/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.HashMap;
import java.util.Map;

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
			put("iso639-3", "A language code from ISO 639-3 (Codes for the representation of names of languages - Part 3: Alpha-3 code for comprehensive coverage of languages).");
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

	/** The Constant ATTR_AUTHORITY. */
	private static final Attribute ATTR_AUTHORITY = new Attribute(TextItem.class, "authority", "Authority",
			"The name of the authoritative list for a controlled value is recorded here. An authority attribute may be used to indicate that a title is controlled by a record in an authority file.");

	/** The Constant ATTR_LANG. */
	public static final Attribute ATTR_LANG = new Attribute(TextItem.class, "lang", "Lang", "This attribute is used to specify the language used within individual elements, using the codes from ISO 639-2/b.");

	/** The Constant ATTR_XML_LANG. */
	public static final Attribute ATTR_XML_LANG = new Attribute(TextItem.class, "xml:lang", "xml:lang", "In the XML standard, this attribute is used to specify the language used within individual elements, using specifications in RFC 3066.");

	/** The Constant ATTR_TRANSLITERATION. */
	public static final Attribute ATTR_TRANSLITERATION = new Attribute(TextItem.class, "transliteration", "Transliteration", "It specifies the transliteration technique used within individual elements. There is no MARC 21 equivalent for this attribute. ");

	/** The Constant ATTR_SCRIPT. */
	public static final Attribute ATTR_SCRIPT = new Attribute(TextItem.class, "script", "Script", "This attribute specifies the script used within individual elements, using codes from ISO 15924.");

	/** The Constant ATTR_ID. */
	public static final Attribute ATTR_ID = new Attribute(TextItem.class, "id", "ID", "This attribute is used to link internally and to reference an element from outside the instance.");

	/** The Constant ATTR_XLINK. */
	public static final Attribute ATTR_XLINK = new Attribute(TextItem.class, "xlink", "xlink", "This attribute is used for an external link. It is defined in the MODS schema as xlink:simpleLink.");

	/** The Constant ATTR_ENCODING. */
	public static final Attribute ATTR_ENCODING = new Attribute(SelectItem.class, "encoding", "Encoding", ENCODING_TOOLTIPS);

	/** The Constant ATTR_POINT. */
	public static final Attribute ATTR_POINT = new Attribute(SelectItem.class, "point", "Point", POINT_TOOLTIPS);

	/** The Constant ATTR_KEY_DATE. */
	public static final Attribute ATTR_KEY_DATE = new Attribute(CheckboxItem.class, "key_date", "Key Date",
			"This value is used so that a particular date may be distinguished among several dates. Thus for example, when sorting MODS records by date, a date with keyDate='yes' would be the date to sort on.");

	/** The Constant ATTR_QUALIFIER. */
	public static final Attribute ATTR_QUALIFIER = new Attribute(SelectItem.class, "qualifier", "Qualifier", QUALIFIER_TOOLTIPS);

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
	public static Attribute getDisplayLabel(final String tooltip) {
		return new Attribute(TextItem.class, "display_label", "Display Label", tooltip);
	}

	/** The Constant GET_TITLE_INFO_LAYOUT. */
	private static final GetLayoutOperation GET_TITLE_INFO_LAYOUT = new GetTitleInfoLayout();

	/** The Constant GET_NAME_LAYOUT. */
	private static final GetLayoutOperation GET_NAME_LAYOUT = new GetNameLayout();

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

	/** The Constant GET_LOCATION_LAYOUT. */
	private static final GetLayoutOperation GET_LOCATION_LAYOUT = new GetLocationLayout();

	/**
	 * The Interface GetLayoutOperation.
	 */
	public interface GetLayoutOperation {

		/**
		 * Execute.
		 * 
		 * @return the canvas
		 */
		Canvas execute();
	}

	/**
	 * The Class GetTitleInfoLayout.
	 */
	private static class GetTitleInfoLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getTitleInfoLayout();
		}
	}

	/**
	 * The Class GetNameLayout.
	 */
	private static class GetNameLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getNameLayout();
		}
	}

	/**
	 * The Class GetTypeOfResourceLayout.
	 */
	private static class GetTypeOfResourceLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getTypeOfResourceLayout();
		}
	}

	/**
	 * The Class GetGenreLayout.
	 */
	private static class GetGenreLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getGenreLayout();
		}
	}

	/**
	 * The Class GetOriginInfoLayout.
	 */
	private static class GetOriginInfoLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getOriginInfoLayout();
		}
	}

	/**
	 * The Class GetPhysicialDescriptionLayout.
	 */
	private static class GetPhysicialDescriptionLayout implements GetLayoutOperation {

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
	private static class GetGeneralLayout implements GetLayoutOperation {

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
	private static class GetGeneralDeepLayout implements GetLayoutOperation {

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
	private static class GetSubjectLayout implements GetLayoutOperation {

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
	private static class GetLocationLayout implements GetLayoutOperation {

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
	private static class GetCopyInformationLayout implements GetLayoutOperation {

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
	private static class GetPartLayout implements GetLayoutOperation {

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
	private static class GetRecordInfoLayout implements GetLayoutOperation {

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
	private static class GetPlaceLayout implements GetLayoutOperation {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation
		 * #execute()
		 */
		@Override
		public VLayout execute() {
			return getPlaceLayout();
		}
	}

	/**
	 * The Class GetDetailLayout.
	 */
	private static class GetDetailLayout implements GetLayoutOperation {

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
	private static class GetExtentLayout implements GetLayoutOperation {

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
	private static class GetLanguageOfCatalogingLayout implements GetLayoutOperation {

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
	private static class GetLanguageLayout implements GetLayoutOperation {

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
	private static class GetDateLayout implements GetLayoutOperation {

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
		public GetDateLayout(String name, String title, String tooltip) {
			this(name, title, tooltip, null, true);
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
		public GetDateLayout(String name, String title, String tooltip, Attribute attribute, boolean keyDate) {
			super();
			this.name = name;
			this.title = title;
			this.tooltip = tooltip;
			this.attribute = attribute;
			this.keyDate = keyDate;
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
			return getDateLayout(name, title, tooltip, attribute, keyDate);
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
	public static SectionStackSection getStackSection(Attribute attribute, final boolean expanded) {
		return getStackSectionWithAttributes(attribute.getLabel(), attribute, expanded, null);
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
	public static SectionStackSection getStackSection(final String label1, final String label2, final String tooltip, final boolean expanded) {
		return getStackSectionWithAttributes(label1, label2, tooltip, expanded, null);
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
		return getSimpleSectionWithAttributes(attribute, expanded, null);
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
		return getSimpleSectionWithAttributes(new Attribute(type, label.toLowerCase().replaceAll(" ", "_"), label, tooltip), expanded, null);
	}

	/**
	 * Gets the stack section with attributes.
	 * 
	 * @param label1
	 *          the label1
	 * @param label2
	 *          the label2
	 * @param tooltip
	 *          the tooltip
	 * @param expanded
	 *          the expanded
	 * @param attributes
	 *          the attributes
	 * @return the stack section with attributes
	 */
	public static SectionStackSection getStackSectionWithAttributes(final String label1, final String label2, final String tooltip, final boolean expanded, final Attribute[] attributes) {
		return getStackSectionWithAttributes(label1, new Attribute(TextItem.class, label2.toLowerCase().replaceAll(" ", "_"), label2, tooltip), expanded, attributes);
	}

	/**
	 * Gets the simple section with attributes.
	 * 
	 * @param mainAttr
	 *          the main attr
	 * @param expanded
	 *          the expanded
	 * @param attributes
	 *          the attributes
	 * @return the simple section with attributes
	 */
	public static SectionStackSection getSimpleSectionWithAttributes(final Attribute mainAttr, final boolean expanded, Attribute[] attributes) {
		final boolean isAttribPresent = attributes != null && attributes.length != 0;
		final VLayout layout = new VLayout();
		if (isAttribPresent) {
			// layout.setOverflow(Overflow.AUTO);
			// layout.setLeaveScrollbarGap(true);
			layout.addMember(getAttributes(true, attributes));
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
	public static SectionStackSection getStackSectionWithAttributes(final String stackLabel, final Attribute mainAttr, final boolean expanded, final Attribute[] attributes) {
		final boolean isAttribPresent = attributes != null && attributes.length != 0;
		final VLayout layout = new VLayout();
		// layout.setOverflow(Overflow.AUTO);
		// layout.setLeaveScrollbarGap(true);
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
		Class<? extends FormItem> type = attr.getType();
		if (type.equals(TextAreaItem.class)) {
			item = new TextAreaItem(attr.getName());
		} else if (type.equals(TextItem.class)) {
			item = new TextItem(attr.getName());
		} else if (type.equals(CheckboxItem.class)) {
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
		layout.setLeaveScrollbarGap(true);
		layout.setWidth100();
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
				}
			}
		});
		section.setControls(addButton, removeButton);
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
	public static SectionStackSection getTitleInfoStack(boolean expanded) {
		return getSomeStack(expanded, "Title Info", GET_TITLE_INFO_LAYOUT);
	}

	/**
	 * Gets the name stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the name stack
	 */
	public static SectionStackSection getNameStack(boolean expanded) {
		return getSomeStack(expanded, "Name", GET_NAME_LAYOUT);
	}

	/**
	 * Gets the type of resource stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the type of resource stack
	 */
	public static SectionStackSection getTypeOfResourceStack(boolean expanded) {
		return getSomeStack(expanded, "Type of Resource", GET_TYPE_OF_RESOURCE_LAYOUT);
	}

	/**
	 * Gets the genre stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the genre stack
	 */
	public static SectionStackSection getGenreStack(boolean expanded) {
		return getSomeStack(expanded, "Genre", GET_GENRE_LAYOUT);
	}

	/**
	 * Gets the origin info stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the origin info stack
	 */
	public static SectionStackSection getOriginInfoStack(boolean expanded) {
		return getSomeStack(expanded, "Origin Info", GET_ORIGIN_INFO_LAYOUT);
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
		return getSomeStack(expanded, "Abstract", new GetGeneralLayout(new Attribute(TextAreaItem.class, "abstract", "Abstract", "Roughly equivalent to MARC 21 field 520."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."), getDisplayLabel("This attribute is intended to be used when additional text associated with the abstract is necessary for display."), ATTR_XLINK,
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
		return getSomeStack(expanded, "Table of Contents", new GetGeneralLayout(new Attribute(TextAreaItem.class, "table_of_contents", "Table of Contents", "Roughly equivalent to MARC 21 field 505."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."), getDisplayLabel("This attribute is intended to be used when additional text associated with the table of contents is necessary for display."), ATTR_XLINK,
				ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
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
		return getStackSectionWithAttributes("Target Audience", "Target Audience", "A description of the intellectual level of the audience for which the resource is intended.", true, attributes);
	}

	/**
	 * Gets the note stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the note stack
	 */
	public static SectionStackSection getNoteStack(boolean expanded) {
		return getSomeStack(expanded, "Note", new GetGeneralLayout(new Attribute(TextAreaItem.class, "note", "Note", "Roughly equivalent to MARC 21 fields 5XX."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types."), getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display."), ATTR_XLINK, ATTR_ID,
				ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
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
		Attribute[] attributes = new Attribute[] { ATTR_AUTHORITY,
				new Attribute(TextItem.class, "edition", "Edition", "This attribute contains a designation of the edition of the particular classification scheme indicated in authority for those schemes that are issued in editions (e.g. DDC)."),
				getDisplayLabel("Equivalent to MARC 21 field 050 subfield $3."), ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		return getStackSectionWithAttributes("Classifications", "Classification", "Equivalent to MARC fields 050-08X, subfields $a and $b.", true, attributes);
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
				new Attribute(TextItem.class, "type", "Type",
						"There is no controlled list of abstract types. (Suggested values: doi, hdl, isbn, ismn, isrc, issn,	issue number,	istc, lccn, loca, matrix number, music plate, music publisher, sici, stock number, upc, uri, videorecording identifier)"),
				getDisplayLabel("Equivalent to MARC 21 field 050 subfield $3."), ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT,
				new Attribute(CheckboxItem.class, "invalid", "Invalid", "If invalid='yes' is not present, the identifier is assumed to be valid.") };
		return getStackSectionWithAttributes("Identifiers", "Identifier", "Roughly equivalent to MARC fields 010, 020, 022, 024, 856.", true, attributes);
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
		return getSomeStack(expanded, "Access Condition", new GetGeneralLayout(new Attribute(TextAreaItem.class, "access_condition", "Access Condition", "Roughly equivalent to MARC 21 fields 506 and 540."), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "There is no controlled list of abstract types. Suggested values are: restriction on access (equivalent to MARC 21 field 506) and use and reproduction (equivalent to MARC 21 field 540)."),
				getDisplayLabel("This attribute is intended to be used when additional text associated with the access conditions is necessary for display."), ATTR_XLINK, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));
	}

	/**
	 * Gets the extension stack.
	 * 
	 * @param expanded
	 *          the expanded
	 * @return the extension stack
	 */
	public static SectionStackSection getExtensionStack(boolean expanded) {
		return getSomeStack(expanded, "Access Condition", new GetGeneralLayout(new Attribute(TextAreaItem.class, "extension", "Extension",
				"Used to provide for additional information not covered by MODS. It may be used for elements that are local to the creator of the data, similar to MARC 21 9XX fields."), new Attribute[] { new Attribute(TextItem.class, "namespace", "Namespace",
				"Namespace") }));
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
	private static SectionStackSection getPlaceStack(boolean expanded) {
		return getSomeStack(expanded, "Place", GET_PLACE_LAYOUT);
	}

	/**
	 * Gets the title info layout.
	 * 
	 * @return the title info layout
	 */
	public static VLayout getTitleInfoLayout() {
		final VLayout layout = new VLayout();

		final Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("abbreviated", "equivalent to MARC 21 field 210");
		tooltips.put("translated", "equivalent to MARC 21 fields 242, 246");
		tooltips.put("alternative", "equivalent to MARC 21 fields 246, 740");
		tooltips.put("uniform", "equivalent to MARC 21 fields 130, 240, 730");

		Attribute[] attributes = new Attribute[] { new Attribute(SelectItem.class, "type", "Type", tooltips), ATTR_LANG,
				getDisplayLabel("This attribute is intended to be used when additional text associated with the title is needed for display. It is equivalent to MARC 21 field 246 subfield $i."), ATTR_XML_LANG, ATTR_AUTHORITY, ATTR_TRANSLITERATION, ATTR_SCRIPT,
				ATTR_ID, ATTR_XLINK };
		layout.addMember(getAttributes(true, attributes));
		final SectionStack sectionStack = new SectionStack();
		// sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(getStackSection("Titles", "Title", "title without the <titleInfo> type attribute is roughly equivalent to MARC 21 field 245", true));
		sectionStack.addSection(getStackSection("Sub Titles", "Sub Title", "Equivalent to MARC 21 fields 242, 245, 246 subfield $b.", false));
		sectionStack.addSection(getStackSection("Part Numbers", "Part Number", "Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $n.", false));
		sectionStack.addSection(getStackSection("Part Names", "Part Name", "Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $p.", false));
		sectionStack.addSection(getStackSection("Non Sort", "Non Sort", "It is equivalent to the new technique in MARC 21 that uses control characters to surround data disregarded for sorting.", false));
		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the name layout.
	 * 
	 * @return the name layout
	 */
	public static VLayout getNameLayout() {
		final VLayout layout = new VLayout();

		Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("personal", "equivalent to MARC 21 fields 100, 700");
		tooltips.put("corporate", "equivalent to MARC 21 fields 110, 710");
		tooltips.put("conference", "equivalent to MARC 21 fields 111, 711");
		Attribute[] attributes = new Attribute[] { new Attribute(SelectItem.class, "type", "Type", tooltips), ATTR_LANG, ATTR_AUTHORITY, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION, ATTR_XLINK, ATTR_ID };
		layout.addMember(getAttributes(true, attributes));
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);

		tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("date", "The attribute date is used to parse dates that are not integral parts of a name.");
		tooltips.put("family", "part of a personal name (surname)");
		tooltips.put("given ", "part of a personal name (first name)");
		tooltips.put("termsOfAddress ", "It is roughly equivalent to MARC X00 fields, subfields $b and $c.");

		sectionStack.addSection(getStackSectionWithAttributes("Name Parts", "Name Part",
				"Includes each part of the name that is parsed. Parsing is used to indicate a date associated with the name, to parse the parts of a corporate name (MARC 21 fields X10 subfields $a and $b).", false, new Attribute[] { new Attribute(SelectItem.class,
						"type", "Type", tooltips) }));
		sectionStack.addSection(getStackSection("Display Forms", "Display Form", "This data is usually carried in MARC 21 field 245 subfield $c.", false));
		sectionStack
				.addSection(getStackSection("Affiliations", "Affiliation", "Contains the name of an organization, institution, etc. with which the entity recorded in <name> was associated at the time that the resource was created. (MARS21 100, 700 $u)", false));

		sectionStack.addSection(getStackSectionWithAttributes("Roles", "Role", "", false, new Attribute[] { ATTR_TYPE_TEXT_CODE, ATTR_AUTHORITY }));

		layout.addMember(sectionStack);

		return layout;
	}

	/**
	 * Gets the type of resource layout.
	 * 
	 * @return the type of resource layout
	 */
	public static VLayout getTypeOfResourceLayout() {
		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] { new Attribute(CheckboxItem.class, "collection", "Collection", "This attribute is used as collection='yes' when the resource is a collection. (Leader/07 code 'c')"),
				new Attribute(CheckboxItem.class, "manuscript", "Manuscript", "This attribute is used as manuscript='yes' when the resource is written in handwriting or typescript. (Leader/06 values 'd', 'f' and 't')") };
		layout.addMember(getAttributes(true, attributes));

		final Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "Nothing.");
		tooltips.put("cartographic", "Includes MARC 21 Leader/06 values 'e' and 'f'.");
		tooltips.put("notated music", "Includes MARC 21 Leader/06 values 'c' and 'd'.");
		tooltips.put("sound recording", "This value by itself is used when a mixture of musical and nonmusical sound recordings occurs in a resource or when a user does not want to make a distinction between musical and nonmusical. ");
		tooltips.put("sound recording-musical", "This is roughly equivalent to MARC 21 Leader/06 value 'j'.");
		tooltips.put("sound recording-nonmusical", "This is roughly equivalent to MARC 21 Leader/06 value 'i'.");
		tooltips.put("still image", "This is roughly equivalent to MARC 21 Leader/06 value 'k'.");
		tooltips.put("moving image", "This is roughly equivalent to MARC 21 Leader/06 value 'g'.");
		tooltips.put("three dimensional object", "This is roughly equivalent to MARC 21 Leader/06 value 'r'.");
		tooltips.put("software, multimedia", "This is roughly equivalent to MARC 21 Leader/06 value 'm'.");
		tooltips.put("mixed material", "This is roughly equivalent to MARC 21 Leader/06 value 'p'.");
		DynamicForm form = new DynamicForm();
		form.setItems(newItem(new Attribute(SelectItem.class, "type", "Type", tooltips)));
		layout.addMember(form);

		return layout;
	}

	/**
	 * Gets the genre layout.
	 * 
	 * @return the genre layout
	 */
	public static VLayout getGenreLayout() {
		final VLayout layout = new VLayout();
		Attribute[] attributes = new Attribute[] { new Attribute(TextItem.class, "type", "Type", "This attribute may be used if desired to distinguish different aspects of genre, such as class, work type, or style."), ATTR_XML_LANG, ATTR_AUTHORITY, ATTR_XML_LANG,
				ATTR_SCRIPT, ATTR_TRANSLITERATION };
		layout.addMember(getAttributes(true, attributes));

		DynamicForm form = new DynamicForm();
		form.setItems(newItem(new Attribute(TextItem.class, "genre", "Genre", "A term(s) that designates a category characterizing a particular style, form, or content, such as artistic, musical, literary composition, etc. ")));
		layout.addMember(form);

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
	public static VLayout getDateLayout(String name, String title, String tooltip, Attribute attribute, boolean keyDate) {
		final VLayout layout = new VLayout();
		Attribute[] attributes = null;
		if (attribute != null) {
			if (keyDate) {
				attributes = new Attribute[] { attribute, ATTR_ENCODING, ATTR_POINT, ATTR_KEY_DATE, ATTR_QUALIFIER };
			} else {
				attributes = new Attribute[] { attribute, ATTR_ENCODING, ATTR_POINT, ATTR_QUALIFIER };
			}
			attributes = new Attribute[] { attribute, ATTR_ENCODING, ATTR_POINT, ATTR_KEY_DATE, ATTR_QUALIFIER };
		} else if (keyDate) {
			attributes = new Attribute[] { ATTR_ENCODING, ATTR_POINT, ATTR_KEY_DATE, ATTR_QUALIFIER };
		} else {
			attributes = new Attribute[] { ATTR_ENCODING, ATTR_POINT, ATTR_QUALIFIER };
		}

		final DynamicForm formAttr = getAttributes(true, attributes);

		FormItem item1 = newItem(new Attribute(TextItem.class, name, title, tooltip));
		FormItem item2 = newItem(new Attribute(DateItem.class, name, title, tooltip));

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
				if (ATTR_ENCODING.getName() == null || "".equals(ATTR_ENCODING.getName())) {
					return false;
				}
				String encoding = (String) form.getValue(ATTR_ENCODING.getName());
				boolean returnVal = false;
				if (encoding != null && !"".equals(encoding)) {
					returnVal = !W3C_ENCODING.equals(encoding);
				}
				return returnVal;
			}
		});
		item2.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (ATTR_ENCODING.getName() == null || "".equals(ATTR_ENCODING.getName())) {
					return true;
				}
				String encoding = (String) form.getValue(ATTR_ENCODING.getName());
				boolean returnVal = true;
				if (encoding != null && !"".equals(encoding)) {
					returnVal = W3C_ENCODING.equals(encoding);
				}
				return returnVal;
			}
		});
		formAttr.setItems(itemsToAdd);
		layout.addMember(formAttr);

		return layout;
	}

	/**
	 * Gets the place layout.
	 * 
	 * @return the place layout
	 */
	private static VLayout getPlaceLayout() {
		final VLayout layout = new VLayout();
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.setStyleName("metadata-sectionstack-topmargin");
		sectionStack.addSection(getStackSectionWithAttributes("Place Terms", "Place Term",
				"Used to express place in a textual or coded form. If both a code and a term are given that represent the same place, use one <place> and multiple occurrences of <placeTerm>. If different places, repeat <place><placeTerm>.", true, new Attribute[] {
						ATTR_TYPE_TEXT_CODE, ATTR_AUTHORITY }));

		layout.addMember(sectionStack);
		return layout;
	}

	/**
	 * Gets the origin info layout.
	 * 
	 * @return the origin info layout
	 */
	public static VLayout getOriginInfoLayout() {
		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] { ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION };
		layout.addMember(getAttributes(true, attributes));

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		// sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.addSection(getPlaceStack(false));
		sectionStack.addSection(getStackSection("Publishers", "Publisher", "It is equivalent to MARC 21 field 260 subfield $b.", false));
		sectionStack.addSection(getSomeStack(false, "Dates Issued", new GetDateLayout("date_issued", "Issued Date",
				"Equivalent to dates in MARC 21 field 260 subfield $c. It may be in textual or structured form. <dateIssued> may be recorded according to the MARC 21 rules for field 008/07-14 with encoding='marc.'")));
		sectionStack.addSection(getSomeStack(false, "Dates Created", new GetDateLayout("date_created", "Created Date",
				"This type of date is recorded in various places in MARC 21: field 260 subfield $g, for some types of material in field 260 subfield $c, date of original in field 534 subfield $c and date of reproduction in field 533 subfield $d.")));
		sectionStack.addSection(getSomeStack(false, "Dates Captured", new GetDateLayout("date_captured", "Captured Date", "This could be a date that the resource was captured or could also be the date of creation. This is roughly equivalent to MARC field 033.")));
		sectionStack.addSection(getSomeStack(false, "Dates Valid", new GetDateLayout("date_valid", " Valid Date", "Roughly equivalent to MARC 21 field 046 subfields $m and $n. ")));
		sectionStack.addSection(getSomeStack(false, "Dates Modified", new GetDateLayout("date_modified", "Modified Date", "Roughly equivalent to MARC 21 field 046 subfield $j.")));
		sectionStack.addSection(getSomeStack(false, "Dates Copyright", new GetDateLayout("date_copyright", "Copyright Date", "A copyright date may be used in 260$c preceded by the letter 'c' if it appears that way on the resource.")));
		sectionStack.addSection(getSomeStack(false, "Dates Other", new GetDateLayout("date_other", "Other Date", "Roughly equivalent to MARC 21 field 046 subfield $j.", new Attribute(TextItem.class, "type", "Type",
				"This attribute allows for extensibility of date types so that other specific date types may be designated if not given a separate element in MODS."), true)));
		sectionStack.addSection(getStackSection("Editions", "Edition", "Equivalent to MARC 21 fields 242, 245, 246 subfield $b.", false));

		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, "issuance", "Issuance", new HashMap<String, String>() {
			{
				put("", "This element will be omitted.");
				put("continuing", "The value is equivalent to codes 'b,' 'i' and 's' in Leader/07.");
				put("monographic", "The value is equivalent to codes 'a,' 'c,' 'd' and 'm' in Leader/07.");
			}
		}), false, null));
		sectionStack.addSection(getStackSectionWithAttributes("Frequencies", "Frequency",
				"Equivalent to the information in MARC 21 008/18 (Frequency) for Continuing Resources, where it is given in coded form or in field 310 (Current Publication Frequency), where it is given in textual form.", false, new Attribute[] { ATTR_AUTHORITY }));

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
		// sectionStack.setOverflow(Overflow.AUTO);

		attributes = new Attribute[] { new Attribute(TextItem.class, "type", "Type", "Used if desired to specify whether the form concerns materials or techniques, e.g. type='material': oil paint; type='technique': painting."), ATTR_AUTHORITY };
		sectionStack.addSection(getStackSectionWithAttributes("Forms", "Form", "Includes information that specifies the physical form or medium of material for a resource. Either a controlled list of values or free text may be used.", false, attributes));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, "reformatting_quality", "Reformatting Quality", new HashMap<String, String>() {
			{
				put("", "This element will be omitted.");
				put("access", "The electronic resource is intended to support current electronic access to the original item (i.e., reference use), but is not sufficient to serve as a preservation copy.");
				put("preservation", "The electronic resource was created via reformatting to help preserve the original item. The capture and storage techniques ensure high-quality, long-term protection. ");
				put("replacement", "The electronic resource is of high enough quality to serve as a replacement if the original is lost, damaged, or destroyed. If serving more than one purpose, the element may be repeated. ");
			}
		}), false, null));
		sectionStack.addSection(getStackSection("Internet Media Types", "Internet Media Type", "Roughly equivalent to MARC 21 field 856 subfield $q, although subfield $q may also contain other designations of electronic format.", false));
		sectionStack.addSection(getStackSection("Extents", "Extent", "Roughly equivalent to MARC 21 fields 300 subfields $a, $b, $c, and $e and 306 subfield $a.", false));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(SelectItem.class, "digital_origin", "Digital Origin", new HashMap<String, String>() {
			{
				put("", "This element will bauthoritye omitted.");
				put("born digital", "A resource was created and is intended to remain in digital form. (No MARC equivalent, but includes value 'c')");
				put("reformatted digital", "A resource was created by digitization of the original non-digital form. (MARC 007/11 value 'a')");
				put("digitized microfilm", "A resource was created by digitizing a microform (MARC 007/11 value 'b')");
				put("digitized other analog", "A resource was created by digitizing another form of the resource not covered by the other values, e.g. an intermediate form such as photocopy, transparency, slide, etc. (MARC 007/11 value 'd')");
			}
		}), false, null));
		attributes = new Attribute[] { new Attribute(TextItem.class, "type", "Type", "Roughly equivalent to the types of notes that may be contained in MARC 21 fields 340, 351 or general 500 notes concerning physical condition, characteristics, etc."),
				getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display."), ATTR_XLINK, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION };
		sectionStack.addSection(getStackSectionWithAttributes("Notes", new Attribute(TextAreaItem.class, "note", "Note",
				"Includes information that specifies the physical form or medium of material for a resource. Either a controlled list of values or free text may be used."), false, attributes));

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
		if (attribs != null || attribs.length > 0) {
			boolean first = true;
			for (Attribute attr : attribs) {
				final SectionStack sectionStack = new SectionStack();
				sectionStack.setLeaveScrollbarGap(true);
				sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
				sectionStack.setStyleName("metadata-elements");
				sectionStack.setWidth100();
				sectionStack.addSection(getStackSection(attr, first));
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
		// sectionStack.setOverflow(Overflow.AUTO);
		layout.addMember(getAttributes(true, new Attribute[] { new Attribute(TextItem.class, "object_part", "Object Part",
				"Designates which part of the resource is in the language supplied, e.g. <language objectPart='summary'><languageTerm authority='iso639-2b'>spa</languageTerm></language> indicates that only the summary is in Spanish.") }));

		sectionStack.addSection(getStackSectionWithAttributes("Language Terms", "Language Term", "contains the language(s) of the content of the resource. It may be expressed in textual or coded form.", true, new Attribute[] { ATTR_TYPE_TEXT_CODE,
				ATTR_LANG_AUTHORITY }));

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

		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(TextItem.class, "form", "Form", "Designation of a particular physical presentation of a resource."), false, new Attribute[] { ATTR_AUTHORITY }));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "sublocation", "Sublocation", "Equivalent to MARC 852 $b (Sublocation or collection), $c (Shelving location), $e (Address), which are expressed together as a string."), false));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "shelf_locator", "Shelf Locator",
				"Equivalent to MARC 852 $h (Classification part), $i (Item part), $j (Shelving control number), $k (Call number prefix), $l (Shelving form of title), $m (Call number suffix) and $t (Copy number)."), false));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "electronic_locator", "Electronic Locator", "This is a copy-specific form of the MODS <location><url>, without its attributes."), false));
		getSomeStack(false, "Note", new GetGeneralLayout(new Attribute(TextAreaItem.class, "note", "Note", "Note relating to a specific copy of a document. "), new Attribute[] {
				new Attribute(TextItem.class, "type", "Type", "This attribute is not controlled by a list and thus is open-ended."), getDisplayLabel("This attribute is intended to be used when additional text associated with the note is necessary for display. ") }));
		sectionStack.addSection(getStackSectionWithAttributes("Enumeration and Chronology", new Attribute(TextItem.class, "enumeration_and_chronology", "Enumeration and Chronology",
				"Unparsed string that comprises a summary holdings statement. If more granularity is needed, a parsed statement using an external schema may be used within <holdingExternal>."), false, new Attribute[] { new Attribute(SelectItem.class, "unit_type",
				"Unit Type", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("1", "Information is about the basic bibliographic unit. (863 or 866)");
						put("2", "Information is about supplementary material to the basic unit. (864 or 867)");
						put("3", "Information is about index(es) to the basic unit. (865 or 868)");
					}
				}) }));
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
		// sectionStack.setOverflow(Overflow.AUTO);
		layout.addMember(getAttributes(true, new Attribute[] { ATTR_AUTHORITY, ATTR_XLINK, ATTR_ID, ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION }));

		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "topic", "Topic", "Equivalent to MARC 21 fields 650 and 6XX subfields $x and $v (with authority attribute defined) and MARC 21 field 653 with no authority attribute."), false));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "geographic", "Geographic", "Equivalent to MARC 21 field 651 and 6XX subfield $z."), false));
		sectionStack.addSection(getSomeStack(false, "Temporal", new GetDateLayout("temporal", "Temporal", "Equivalent to MARC 21 fields 045 and 6XX subfield $y.")));
		sectionStack.addSection(getSomeStack(false, "Title Info", GET_TITLE_INFO_LAYOUT));
		sectionStack.addSection(getSomeStack(false, "Name", GET_NAME_LAYOUT));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "genre", "Genre", "Equivalent to subfield $v in MARC 21 6XX fields."), false));

		Attribute[] attributes = new Attribute[] { new Attribute(TextItem.class, "continent", "Continent", "Includes Asia, Africa, Europe, North America, South America."),
				new Attribute(TextItem.class, "province", "Province", "Includes first order political divisions called provinces within a country, e.g. Canada."),
				new Attribute(TextItem.class, "region", "Region", "Includes regions that have status as a jurisdiction, usually incorporating more than one first level jurisdiction."),
				new Attribute(TextItem.class, "state", "State", "Includes first order political divisions called states within a country, e.g. in U.S., Argentina, Italy. Use also for France département."),
				new Attribute(TextItem.class, "territory", "Territory", "Name of a geographical area belonging to or under the jurisdiction of a governmental authority ."),
				new Attribute(TextItem.class, "county", "County", "Name of the largest local administrative unit in various countries, e.g. England."), new Attribute(TextItem.class, "city", "City", "Name of an inhabited place incorporated as a city, town, etc."),
				new Attribute(TextItem.class, "city_section", "City Section", "Name of a smaller unit within a populated place, e.g., neighborhoods, parks or streets."),
				new Attribute(TextItem.class, "island", "Island", "Name of a tract of land surrounded by water and smaller than a continent but is not itself a separate country."),
				new Attribute(TextItem.class, "area", "Area", "Name of a non-jurisdictional geographic entity."),
				new Attribute(TextItem.class, "extraterrestrial_area", "Extraterrestrial Area", "Name of any extraterrestrial entity or space, including solar systems, galaxies, star systems, and planets as well as geographic features of individual planets."), };
		sectionStack.addSection(getSomeStack(false, "Hierarchical Geographic", new GetGeneralDeepLayout(attributes)));

		attributes = new Attribute[] {
				new Attribute(
						TextItem.class,
						"coordinates",
						"Coordinates",
						"One or more statements may be supplied. If one is supplied, it is a point (i.e., a single location); if two, it is a line; if more than two, it is a n-sided polygon where n=number of coordinates assigned. No three points should be co-linear, and coordinates should be supplied in polygon-traversal order."),
				new Attribute(TextItem.class, "scale", "Scale", "It may include any equivalency statements, vertical scales or vertical exaggeration statements for relief models and other three-dimensional items."),
				new Attribute(TextItem.class, "projection", "Projection", "Provides a statement of projection.") };
		sectionStack.addSection(getSomeStack(false, "Cartographics", new GetGeneralDeepLayout(attributes)));

		sectionStack.addSection(getStackSectionWithAttributes("Geographic Code", new Attribute(TextItem.class, "geographic_code", "Geographic Code", "Equivalent to MARC 21 field 043."), false, new Attribute[] { new Attribute(SelectItem.class, "authority",
				"Authority", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("marcgac", "marcgac");
						put("marccountry", "marccountry");
						put("iso3166", "iso3166");
					}
				}) }));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "occupation", "Occupation", "Roughtly equivalent to MARC 21 field 656."), false));

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
		// sectionStack.setOverflow(Overflow.AUTO);
		Attribute[] attributes = new Attribute[] { getDisplayLabel("Equivalent to MARC 21 field 852 subfield $3."),
				new Attribute(TextItem.class, "type", "Type", "This attribute is used to indicate different kinds of locations, e.g. current, discovery, former, creation."), ATTR_AUTHORITY, ATTR_XLINK, ATTR_LANG, ATTR_XML_LANG, ATTR_TRANSLITERATION, ATTR_SCRIPT };
		sectionStack.addSection(getStackSectionWithAttributes("Physical Location", new Attribute(TextItem.class, "physical_location", "Physical Location", "Equivalent to MARC 21 field 852 subfields $a, $b and $e."), true, attributes));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "shelfLocator", "Shelf Locator",
				"This information is equivalent to MARC 852 $h (Classification part), $i (Item part), $j (Shelving control number), $k (Call number prefix), $l (Shelving form of title), $m (Call number suffix) and $t (Copy number)."), false));
		attributes = new Attribute[] { getDisplayLabel("Equivalent to MARC 21 field 856 subfields $y and $3."), new Attribute(DateItem.class, "date_last_accessed", "Date Last Accessed", "There is no MARC equivalent for 'dateLastAccessed'."),
				new Attribute(TextAreaItem.class, "note", "Note", "This attribute includes notes that are associated with the link that is included as the value of the <url> element. It is generally free text."),
				new Attribute(SelectItem.class, "access", "Access", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("preview", "Indicates a link to a thumbnail or snippet of text.");
						put("raw object", "Indicates a direct link to the object described (e.g. a jpg or pdf document). Used only when the object is represented by a single file.");
						put("object in context", "Indicates a link to the object within the context of its environment (with associated metadata, navigation, etc.)");
					}
				}), new Attribute(SelectItem.class, "usage", "Usage", new HashMap<String, String>() {
					{
						put("", "This attribute will be omitted.");
						put("primary display", "Indicates that the link is the most appropriate to display for end users.");
					}
				}) };
		sectionStack.addSection(getSomeStack(false, "Holding Simple", new GetCopyInformationLayout()));
		sectionStack.addSection(getSimpleSection(new Attribute(TextAreaItem.class, "holding_external", "Holding External",
				"Holdings information that uses a schema defined externally to MODS. <holdingExternal> may include more detailed holdings information than that accommodated by the MODS schema. An example is ISO 20775 and its accompanying schema."), false));
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
		// sectionStack.setOverflow(Overflow.AUTO);
		Attribute[] attributes = new Attribute[] { new Attribute(TextItem.class, "type", "Type", "A designation of a document segment type. Suggested values include volume, issue, chapter, section, paragraph, track. Other values may be used as needed."),
				new Attribute(TextItem.class, "order", "Order", "An integer that designates the sequence of parts."), ATTR_ID };

		layout.addMember(getAttributes(true, attributes));
		sectionStack.addSection(getSomeStack(false, "Detail", new GetDetailLayout()));
		sectionStack.addSection(getSomeStack(false, "Extent", new GetExtentLayout()));
		sectionStack.addSection(getSomeStack(false, "Date", new GetDateLayout("date", "Date", "Contains date information relevant to the part described.", null, false)));
		sectionStack.addSection(getStackSection(new Attribute(TextAreaItem.class, "text", "Text", "Contains unparsed information in textual form about the part. When used in relatedItem type='host', it is equivalent to MARC 21 field 773 subfield $g."), false));

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

		Attribute[] attributes = new Attribute[] { new Attribute(TextItem.class, "type", "Type", "The type of part described. Suggested values include part, volume, issue, chapter, section, paragraph, track. Other values may be used as needed."),
				new Attribute(TextItem.class, "level", "Level", "Describes the level of numbering in the host/parent item. This is used to ensure that the numbering is retained in the proper order. An example is: 'v.2, no. 3'.") };
		layout.addMember(getAttributes(true, attributes));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "number", "Number", "Contains the actual number within the part."), false));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "caption", "Caption", "Contains the caption describing the enumeration within a part. This may be the same as type, but conveys what is on the item being described. "), false));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "title", "Title", "Contains the title of the part. Only include if this is different than the title in <titleInfo><title>."), false));
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
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "start", "Start", "Contains the beginning unit of the extent within a part (e.g., first page)."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "end", "End", "Contains the ending unit of the extent within a part."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "total", "Total", "Contains the total number of units within a part, rather than specific units."), false));
		sectionStack.addSection(getSimpleSection(new Attribute(TextItem.class, "list", "List", "Contains a textual listing of the units within a part (e.g., 'pp. 5-9'.)"), false));

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
		sectionStack.addSection(getStackSectionWithAttributes("Language Term", new Attribute(TextItem.class, "language_term", "Language Term", "contains the language(s) of the content of the resource. It may be expressed in textual or coded form."), true,
				attributes));

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
		// sectionStack.setOverflow(Overflow.AUTO);
		Attribute[] attributes = new Attribute[] { ATTR_LANG, ATTR_XML_LANG, ATTR_SCRIPT, ATTR_TRANSLITERATION };
		layout.addMember(getAttributes(true, attributes));

		sectionStack.addSection(getStackSectionWithAttributes("Record Content Source", new Attribute(TextItem.class, "record_content_source", "Record Content Source", "Roughly equivalent to MARC 21 field 040."), false, new Attribute[] { ATTR_AUTHORITY }));
		sectionStack.addSection(getSomeStack(false, "Record Creation Date", new GetDateLayout("record_creation_date", "Record Creation Date", "Roughly equivalent to MARC 21 field 008/00-05.", null, false)));
		sectionStack.addSection(getSomeStack(false, "Record Change Date", new GetDateLayout("record_change_date", "Record Creation Date", "May serve as a version identifier for the record. It is roughly equivalent to MARC 21 field 005.", null, false)));
		sectionStack.addSection(getSimpleSectionWithAttributes(new Attribute(TextItem.class, "record_identifier", "Record Identifier", "Roughly equivalent to MARC 21 field 001."), false, new Attribute[] { new Attribute(TextItem.class, "source", "Source",
				"Roughly equivalent to MARC 21 field 003.") }));
		sectionStack.addSection(getStackSection(new Attribute(TextItem.class, "record_origin", "Record Origin", "Intended to show the origin, or provenance of the MODS record. There is no MARC equivalent to <recordOrigin>."), false));
		sectionStack.addSection(getSomeStack(false, "Language of Cataloging", new GetLanguageOfCatalogingLayout()));
		sectionStack.addSection(getStackSectionWithAttributes("Description Standard", new Attribute(TextItem.class, "description_standard", "Description Standard", "Roughly equivalent to the information found in MARC 21 field 040$e or Leader/18."), false,
				new Attribute[] { ATTR_AUTHORITY }));

		layout.addMember(sectionStack);
		return layout;
	}

}
