package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.HashMap;
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

public final class TabUtils {

	private interface GetLayoutOperation {
		VLayout execute();
	}

	private static class GetTitleInfoLayout implements GetLayoutOperation {
		@Override
		public VLayout execute() {
			return getTitleInfoLayout();
		}
	}

	private static class GetNameLayout implements GetLayoutOperation {
		@Override
		public VLayout execute() {
			return getNameLayout();
		}
	}

	private static class GetTypeOfResourceLayout implements GetLayoutOperation {
		@Override
		public VLayout execute() {
			return getTypeOfResourceLayout();
		}
	}

	public static SectionStackSection getStackSection(final String label1, final String label2, final String tooltip, final boolean expanded) {
		return getStackSectionWithAttributes(label1, label2, tooltip, expanded, null);
	}

	public static SectionStackSection getSimpleStackSection(final Class<? extends FormItem> type, final String label, final String tooltip, final boolean expanded) {
		return getSimpleSectionWithAttributes(type, label, tooltip, expanded, null);
	}

	public static SectionStackSection getSimpleSectionWithAttributes(final Class<? extends FormItem> type, final String label, final String tooltip,
			final boolean expanded, Attribute... attributes) {
		final boolean isAttribPresent = attributes != null && attributes.length != 0;
		final String name = label.toLowerCase().replaceAll(" ", "_");
		final VLayout layout = new VLayout();
		if (isAttribPresent) {
			layout.setOverflow(Overflow.AUTO);
			layout.setLeaveScrollbarGap(true);
			layout.addMember(getAttributes(true, attributes));
		}

		SectionStackSection section = new SectionStackSection(label.toLowerCase().replaceAll(" ", "_"));
		section.setTitle(label);

		final DynamicForm form = new DynamicForm();
		FormItem item = newItem(new Attribute() {
			{
				setType(type);
				setName(name);
			}
		});
		item.setWidth(200);
		item.setTitle(label);
		if (tooltip != null && !"".equals(tooltip)) {
			item.setTooltip(tooltip);
		}
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

	public static SectionStackSection getStackSectionWithAttributes(final String label1, final String label2, final String tooltip, final boolean expanded,
			final Attribute... attributes) {
		final boolean isAttribPresent = attributes != null && attributes.length != 0;
		final String name = label2.toLowerCase().replaceAll(" ", "_");
		final VLayout layout = new VLayout();
		// layout.setOverflow(Overflow.AUTO);
		// layout.setLeaveScrollbarGap(true);
		SectionStackSection section = new SectionStackSection(label1.toLowerCase().replaceAll(" ", "_"));
		section.setTitle(label1);
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
				addButton.setPrompt("Add new " + label2 + ".");
			}
		});
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DynamicForm form = new DynamicForm();
				TextItem item = new TextItem(name);
				item.setTitle(label2);
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
				removeButton.setPrompt("Remove last " + label2 + ".");
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
		TextItem item = new TextItem(name);
		item.setTitle(label2);
		item.setWidth(200);
		if (tooltip != null && !"".equals(tooltip)) {
			item.setTooltip(tooltip);
		}
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
		section.addItem(layout);
		section.setExpanded(expanded);

		return section;
	}

	public static SectionStackSection getTitleInfoStack(boolean expanded) {
		return getSomeStack(expanded, "Title Info", new GetTitleInfoLayout());
	}

	public static SectionStackSection getNameStack(boolean expanded) {
		return getSomeStack(expanded, "Name", new GetNameLayout());
	}

	public static SectionStackSection getTypeOfResourceStack(boolean expanded) {
		return getSomeStack(expanded, "Type of Resource", new GetTypeOfResourceLayout());
	}

	public static DynamicForm getAttributes(final boolean group, final Attribute... attributes) {
		final DynamicForm form = new DynamicForm();
		form.setMargin(10);
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
				if (attr.getTooltip() != null && !"".equals(attr.getTooltip())) {
					item.setTooltip(attr.getTooltip());
				}
				fields[i++] = item;
			}
		}
		form.setFields(fields);

		return form;
	}

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
		return item;
	}

	private static SectionStackSection getSomeStack(boolean expanded, final String label, final GetLayoutOperation operation) {
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
		section.addItem(layout);
		section.setExpanded(expanded);

		return section;
	}

	public static VLayout getTitleInfoLayout() {
		final VLayout layout = new VLayout();

		final Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("abbreviated", "equivalent to MARC 21 field 210");
		tooltips.put("translated", "equivalent to MARC 21 fields 242, 246");
		tooltips.put("alternative", "equivalent to MARC 21 fields 246, 740");
		tooltips.put("uniform", "equivalent to MARC 21 fields 130, 240, 730");

		Attribute[] attributes = new Attribute[] {
				new Attribute(SelectItem.class, "type", "Type", tooltips),
				new Attribute(TextItem.class, "lang", "Lang",
						"This attribute is used to specify the language used within individual elements, using the codes from ISO 639-2/b."),
				new Attribute(
						TextItem.class,
						"display_label",
						"Display Label",
						"This attribute is intended to be used when additional text associated with the title is needed for display. It is equivalent to MARC 21 field 246 subfield $i. "),
				new Attribute(TextItem.class, "xml:lang", "xml:lang",
						"In the XML standard, this attribute is used to specify the language used within individual elements, using specifications in RFC 3066."),
				new Attribute(
						TextItem.class,
						"authority",
						"Authority",
						"The name of the authoritative list for a controlled value is recorded here. An authority attribute may be used to indicate that a title is controlled by a record in an authority file."),
				new Attribute(TextItem.class, "transliteration", "Transliteration",
						"It specifies the transliteration technique used within individual elements. There is no MARC 21 equivalent for this attribute. "),
				new Attribute(TextItem.class, "script", "Script", "This attribute specifies the script used within individual elements, using codes from ISO 15924."),
				new Attribute(TextItem.class, "id", "ID", "This attribute is used to link internally and to reference an element from outside the instance."),
				new Attribute(TextItem.class, "xlink", "xlink", "This attribute is used for an external link. It is defined in the MODS schema as xlink:simpleLink.") };
		layout.addMember(getAttributes(true, attributes));
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack
				.addSection(getStackSection("Titles", "Title", "title without the <titleInfo> type attribute is roughly equivalent to MARC 21 field 245", true));
		sectionStack.addSection(getStackSection("Sub Titles", "Sub Title", "Equivalent to MARC 21 fields 242, 245, 246 subfield $b.", false));
		sectionStack.addSection(getStackSection("Part Numbers", "Part Number",
				"Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $n.", false));
		sectionStack.addSection(getStackSection("Part Names", "Part Name", "Equivalent to MARC 21 fields 130, 240, 242, 243, 245, 246, 247, 730, 740 subfield $p.",
				false));
		sectionStack.addSection(getStackSection("Non Sort", "Non Sort",
				"It is equivalent to the new technique in MARC 21 that uses control characters to surround data disregarded for sorting.", false));

		layout.addMember(sectionStack);

		return layout;
	}

	public static VLayout getNameLayout() {
		final VLayout layout = new VLayout();

		Map<String, String> tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("personal", "equivalent to MARC 21 fields 100, 700");
		tooltips.put("corporate", "equivalent to MARC 21 fields 110, 710");
		tooltips.put("conference", "equivalent to MARC 21 fields 111, 711");

		Attribute[] attributes = new Attribute[] {
				new Attribute(SelectItem.class, "type", "Type", tooltips),
				new Attribute(TextItem.class, "lang", "Lang",
						"This attribute is used to specify the language used within individual elements, using the codes from ISO 639-2/b."),
				new Attribute(
						TextItem.class,
						"authority",
						"Authority",
						"The name of the authoritative list for a controlled value is recorded here. An authority attribute may be used to indicate that a title is controlled by a record in an authority file."),

				new Attribute(TextItem.class, "xml:lang", "xml:lang",
						"In the XML standard, this attribute is used to specify the language used within individual elements, using specifications in RFC 3066."),
				new Attribute(TextItem.class, "script", "Script", "This attribute specifies the script used within individual elements, using codes from ISO 15924."),
				new Attribute(TextItem.class, "transliteration", "Transliteration",
						"It specifies the transliteration technique used within individual elements. There is no MARC 21 equivalent for this attribute. "),
				new Attribute(TextItem.class, "xlink", "xlink", "This attribute is used for an external link. It is defined in the MODS schema as xlink:simpleLink."),
				new Attribute(TextItem.class, "id", "ID", "This attribute is used to link internally and to reference an element from outside the instance.") };
		layout.addMember(getAttributes(true, attributes));
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setStyleName("metadata-elements");
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.AUTO);

		tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("date", "The attribute date is used to parse dates that are not integral parts of a name.");
		tooltips.put("family", "part of a personal name (surname)");
		tooltips.put("given ", "part of a personal name (first name)");
		tooltips.put("termsOfAddress ", "It is roughly equivalent to MARC X00 fields, subfields $b and $c.");

		sectionStack
				.addSection(getStackSectionWithAttributes(
						"Name Parts",
						"Name Part",
						"Includes each part of the name that is parsed. Parsing is used to indicate a date associated with the name, to parse the parts of a corporate name (MARC 21 fields X10 subfields $a and $b).",
						false, new Attribute(SelectItem.class, "type", "Type", tooltips)));
		sectionStack.addSection(getStackSection("Display Forms", "Display Form", "This data is usually carried in MARC 21 field 245 subfield $c.", false));
		sectionStack
				.addSection(getStackSection(
						"Affiliations",
						"Affiliation",
						"Contains the name of an organization, institution, etc. with which the entity recorded in <name> was associated at the time that the resource was created. (MARS21 100, 700 $u)",
						false));
		tooltips = new HashMap<String, String>();
		tooltips.put("", "This attribute will be omitted.");
		tooltips.put("text", "This value is used to express role in a textual form.");
		tooltips.put("code", "This value is used to express place in a coded form. The authority attribute may be used to indicate the source of the code.");
		sectionStack.addSection(getStackSectionWithAttributes("Roles", "Role", "", false, new Attribute[] {
				new Attribute(SelectItem.class, "type", "Type", tooltips),
				new Attribute(TextItem.class, "authority", "Authority", "The name of the authoritative list for a controlled value is recorded here.") }));
		sectionStack.addSection(getStackSection("Descriptions", "Description",
				"May be used to give a textual description for a name when necessary, for example, to distinguish from other names.", false));

		layout.addMember(sectionStack);

		return layout;
	}

	public static VLayout getTypeOfResourceLayout() {
		final VLayout layout = new VLayout();

		Attribute[] attributes = new Attribute[] {
				new Attribute(CheckboxItem.class, "collection", "Collection",
						"This attribute is used as collection='yes' when the resource is a collection. (Leader/07 code 'c')"),
				new Attribute(CheckboxItem.class, "manuscript", "Manuscript",
						"This attribute is used as manuscript='yes' when the resource is written in handwriting or typescript. (Leader/06 values 'd', 'f' and 't')") };
		layout.addMember(getAttributes(true, attributes));

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
		tooltips.put("software, multimedia", "This is roughly equivalent to MARC 21 Leader/06 value 'p'.");
		tooltips.put("mixed material", "");
		DynamicForm form = new DynamicForm();
		form.setItems(newItem(new Attribute(SelectItem.class, "type", "Type", tooltips)));
		layout.addMember(form);

		return layout;
	}

}
