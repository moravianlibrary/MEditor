/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.HashMap;
import java.util.List;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class ModsTab.
 */
public class ModsTab extends Tab {
	// TODO: dat do konstanty, nebo resource bundlu
	/** The Constant MAX_DEEP. */
	public static final Tab MAX_DEEP = new Tab("Related Item", "pieces/16/piece_blue.png") {
		{
			setPane(new Label("Maximum level of recursion of element Related Item has been reached."));
		}
	};

	/** The deep. */
	private final int deep;
	private ModsTypeClient modsTypeClient;
	private List<TitleInfoTypeClient> titleInfo;

	/**
	 * The Class GetRelatedItem.
	 */
	private class GetRelatedItem extends GetLayoutOperation {
		private final ModsTypeClient modsTypeClient;

		public GetRelatedItem(ModsTypeClient modsTypeClient) {
			this.modsTypeClient = modsTypeClient;
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
			return getModsLayout(modsTypeClient, true);
		}
	}

	/**
	 * Instantiates a new mods tab.
	 * 
	 * @param deep
	 *          the deep
	 * @param topLvl
	 *          the top lvl
	 * @param modsTypeClient
	 */
	public ModsTab(int deep, boolean topLvl, ModsTypeClient modsTypeClient) {
		super(topLvl ? "MODS" : "Related Item", topLvl ? "pieces/16/pawn_blue.png" : "pieces/16/piece_blue.png");
		this.deep = deep;

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setOverflow(Overflow.AUTO);
		// topTabSet.setHeight100();

		if (!topLvl) {
			// Layout layout = getModsLayout(modsTypeClient, true);

			final SectionStack sectionStack = new SectionStack();
			sectionStack.setLeaveScrollbarGap(true);
			sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
			sectionStack.setWidth100();
			sectionStack.setOverflow(Overflow.AUTO);
			sectionStack.addSection(TabUtils.getSomeStack(true, "Related Item", new GetRelatedItem(modsTypeClient)));
			// layout.addMember(sectionStack);
			setPane(sectionStack);
		} else {
			setPane(getModsLayout(modsTypeClient, false));
		}
	}

	/**
	 * Gets the tab.
	 * 
	 * @param section
	 *          the section
	 * @param name
	 *          the name
	 * @return the tab
	 */
	private static Tab getTab(final SectionStackSection section, final String name) {
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setOverflow(Overflow.AUTO);
		sectionStack.addSection(section);
		Tab tab = new Tab(name, "pieces/16/piece_blue.png");
		tab.setPane(sectionStack);
		return tab;
	}

	/**
	 * Gets the mods tab set.
	 * 
	 * @return the mods tab set
	 */
	private VLayout getModsLayout(ModsTypeClient modsTypeClient, boolean attributesPresent) {
		final VLayout layout = new VLayout();

		if (attributesPresent) {
			Attribute[] attributes = new Attribute[] { new Attribute(SelectItem.class, "type", "Type", new HashMap<String, String>() {
				{
					put("", "This attribute will be omitted.");
					put("preceding", "Information concerning a predecessor to the resource (Equivalent to MARC 21 field 780).");
					put("succeeding", "Information concerning a successor to the resource (Equivalent to MARC 21 field 785).");
					put("original", "Information concerning an original form of the resource (Equivalent to MARC 21 fields 534, 786).");
					put("host",
							"Information concerning a host or parent resource for the resource described; this may be a parent collection (Equivalent to MARC 21 fields 760, 772, 773).");
					put("constituent",
							"Information concerning a constituent unit of the resource. This allows for more specific parsed information than may be used in <tableOfContents>. (Equivalent to MARC 21 fields 762, 770, 774; fields 700, 710, 711 with subfield $t).");
					put("series", "Information concerning the series in which a resource is issued.");
					put("otherVersion", "Information concerning another version (i.e. change in intellectual content) of the resource (Equivalent to MARC 21 field 775).");
					put("otherFormat", "Information concerning another format (i.e. change in physical format) of the resource (Equivalent to MARC 21 field 776). ");
					put("isReferencedBy",
							"Citations or references to published bibliographic descriptions, reviews, abstracts, or indexes of the content of the resource (Roughly equivalent to MARC 21 field 510, but allows for additional parsing of data).");
				}
			}), TabUtils.ATTR_XLINK, TabUtils.getDisplayLabel("Equivalent to MARC 21 fields 76X-78X subfields $i and $3."), TabUtils.ATTR_ID };
			layout.addMember(TabUtils.getAttributes(false, attributes));
		}

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		// topTabSet.setHeight100();
		// if (modsTypeClient == null)
		// return layout; // TODO: handle
		Tab[] tabs = new Tab[] {
				getTab(TabUtils.getTitleInfoStack(true, modsTypeClient == null ? null : modsTypeClient.getTitleInfo(), null/* holders */), "Title Info"),
				getTab(TabUtils.getNameStack(true), "Name"), getTab(TabUtils.getTypeOfResourceStack(true), "Type"), getTab(TabUtils.getGenreStack(true), "Genre"),
				getTab(TabUtils.getOriginInfoStack(true), "Origin"), getTab(TabUtils.getLanguageStack(true), "Language"),
				getTab(TabUtils.getPhysicalDescriptionStack(true), "Physical desc."), getTab(TabUtils.getAbstractStack(true), "Abstract"),
				getTab(TabUtils.getTableOfContentsStack(true), "Table of Con."), getTab(TabUtils.getTargetAudienceStack(true), "Audience"),
				getTab(TabUtils.getNoteStack(true), "Note"), getTab(TabUtils.getSubjectStack(true), "Subject"),
				getTab(TabUtils.getClassificationStack(true), "Classification"), deep > 0 ? new ModsTab(deep - 1, false, null) : MAX_DEEP,
				getTab(TabUtils.getIdentifierStack(true), "Identifier"), getTab(TabUtils.getLocationStack(true), "Location"),
				getTab(TabUtils.getAccessConditionStack(true), "Access Condition"), getTab(TabUtils.getPartStack(true), "Part"),
				getTab(TabUtils.getExtensionStack(true), "Extension"), getTab(TabUtils.getRecordInfoStack(true), "Record Info") };
		topTabSet.setTabs(tabs);
		layout.addMember(topTabSet);
		return layout;
	}

	public ModsTypeClient getDc() {
		if (modsTypeClient == null)
			return null;
		modsTypeClient.setTitleInfo(titleInfo);
		return modsTypeClient;
	}

	public void setDc(ModsTypeClient modsTypeClient) {
		this.modsTypeClient = modsTypeClient;
	}

}
