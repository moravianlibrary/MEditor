/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view.tab;

import java.util.ArrayList;
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

import cz.fi.muni.xkremser.editor.client.metadata.AbstractHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AudienceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.GenreHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LanguageHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.metadata.NameHolder;
import cz.fi.muni.xkremser.editor.client.metadata.NoteHolder;
import cz.fi.muni.xkremser.editor.client.metadata.OriginInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PhysicalDescriptionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.SubjectHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TableOfContentsHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TitleInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TypeOfResourceHolder;
import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
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
	private final List<TitleInfoHolder> titleInfoHolders;
	private final List<NameHolder> nameHolders;
	private final List<TypeOfResourceHolder> typeOfResourceHolders;
	private final List<GenreHolder> genreHolders;
	private final List<OriginInfoHolder> originInfoHolders;
	private final List<LanguageHolder> languageHolders;
	private final List<PhysicalDescriptionHolder> physicalDescriptionHolders;
	private final List<AbstractHolder> abstractHolders;
	private final List<TableOfContentsHolder> tableOfContentsHolders;
	private final AudienceHolder audienceHolder;
	private final List<NoteHolder> noteHolders;
	private final List<SubjectHolder> subjectHolders;

	// private final List<ClassificationtHolder> classificationHolders;

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
		titleInfoHolders = new ArrayList<TitleInfoHolder>();
		nameHolders = new ArrayList<NameHolder>();
		typeOfResourceHolders = new ArrayList<TypeOfResourceHolder>();
		genreHolders = new ArrayList<GenreHolder>();
		originInfoHolders = new ArrayList<OriginInfoHolder>();
		languageHolders = new ArrayList<LanguageHolder>();
		physicalDescriptionHolders = new ArrayList<PhysicalDescriptionHolder>();
		abstractHolders = new ArrayList<AbstractHolder>();
		tableOfContentsHolders = new ArrayList<TableOfContentsHolder>();
		audienceHolder = new AudienceHolder("target_audience", ModsConstants.AUTHORITY, ModsConstants.LANG, ModsConstants.XML_LANG, ModsConstants.TRANSLITERATION,
				ModsConstants.SCRIPT);
		noteHolders = new ArrayList<NoteHolder>();
		subjectHolders = new ArrayList<SubjectHolder>();

		// classificationHolders = new ArrayList<ClassificationtHolder>();

		this.deep = deep;

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setOverflow(Overflow.AUTO);

		if (!topLvl) {
			final SectionStack sectionStack = new SectionStack();
			sectionStack.setLeaveScrollbarGap(true);
			sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
			sectionStack.setWidth100();
			sectionStack.setOverflow(Overflow.AUTO);
			sectionStack.addSection(TabUtils.getSomeStack(true, "Related Item", new GetRelatedItem(modsTypeClient)));
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
				getTab(TabUtils.getTitleInfoStack(true, modsTypeClient == null ? null : modsTypeClient.getTitleInfo(), titleInfoHolders), "Title Info"),
				getTab(TabUtils.getNameStack(true, modsTypeClient == null ? null : modsTypeClient.getName(), nameHolders), "Name"),
				getTab(TabUtils.getTypeOfResourceStack(true, modsTypeClient == null ? null : modsTypeClient.getTypeOfResource(), typeOfResourceHolders), "Type"),
				getTab(TabUtils.getGenreStack(true, modsTypeClient == null ? null : modsTypeClient.getGenre(), genreHolders), "Genre"),
				getTab(TabUtils.getOriginInfoStack(true, modsTypeClient == null ? null : modsTypeClient.getOriginInfo(), originInfoHolders), "Origin"),
				getTab(TabUtils.getLanguageStack(true, modsTypeClient == null ? null : modsTypeClient.getLanguage(), languageHolders), "Language"),
				getTab(TabUtils.getPhysicalDescriptionStack(true, modsTypeClient == null ? null : modsTypeClient.getPhysicalDescription(), physicalDescriptionHolders),
						"Physical desc."),
				getTab(TabUtils.getAbstractStack(true, modsTypeClient == null ? null : modsTypeClient.getAbstrac(), abstractHolders), "Abstract"),
				getTab(TabUtils.getTableOfContentsStack(true, modsTypeClient == null ? null : modsTypeClient.getTableOfContents(), tableOfContentsHolders),
						"Table of Con."),
				getTab(TabUtils.getTargetAudienceStack(true, modsTypeClient == null ? null : modsTypeClient.getTargetAudience(), audienceHolder), "Audience"),
				getTab(TabUtils.getNoteStack(true, modsTypeClient == null ? null : modsTypeClient.getNote(), noteHolders), "Note"),
				getTab(TabUtils.getSubjectStack(true, modsTypeClient == null ? null : modsTypeClient.getSubject(), subjectHolders), "Subject"),
		// getTab(TabUtils.getClassificationStack(true), "Classification"), deep > 0
		// ? new ModsTab(deep - 1, false, null) : MAX_DEEP,
		// getTab(TabUtils.getIdentifierStack(true), "Identifier"),
		// getTab(TabUtils.getLocationStack(true), "Location"),
		// getTab(TabUtils.getAccessConditionStack(true), "Access Condition"),
		// getTab(TabUtils.getPartStack(true), "Part"),
		// getTab(TabUtils.getExtensionStack(true), "Extension"),
		// getTab(TabUtils.getRecordInfoStack(true), "Record Info")
		};
		topTabSet.setTabs(tabs);
		layout.addMember(topTabSet);
		return layout;
	}

	public ModsTypeClient getMods() {
		// title info
		ModsTypeClient modsTypeClient = new ModsTypeClient();
		List<TitleInfoTypeClient> titleInfo = new ArrayList<TitleInfoTypeClient>(titleInfoHolders.size());
		for (TitleInfoHolder holder : titleInfoHolders) {
			titleInfo.add(holder.getTitleInfo());
		}
		modsTypeClient.setTitleInfo(titleInfo);

		// name
		List<NameTypeClient> names = new ArrayList<NameTypeClient>(nameHolders.size());
		for (NameHolder holder : nameHolders) {
			names.add(holder.getName());
		}
		modsTypeClient.setName(names);

		// type of resource
		List<TypeOfResourceTypeClient> typesOfResource = new ArrayList<TypeOfResourceTypeClient>(typeOfResourceHolders.size());
		for (TypeOfResourceHolder holder : typeOfResourceHolders) {
			typesOfResource.add(holder.getType());
		}
		modsTypeClient.setTypeOfResource(typesOfResource);

		// genre
		List<GenreTypeClient> genre = new ArrayList<GenreTypeClient>(genreHolders.size());
		for (GenreHolder holder : genreHolders) {
			genre.add(holder.getGenre());
		}
		modsTypeClient.setGenre(genre);

		// origin info
		List<OriginInfoTypeClient> origin = new ArrayList<OriginInfoTypeClient>(originInfoHolders.size());
		for (OriginInfoHolder holder : originInfoHolders) {
			origin.add(holder.getOriginInfo());
		}
		modsTypeClient.setOriginInfo(origin);

		// language
		List<LanguageTypeClient> language = new ArrayList<LanguageTypeClient>(languageHolders.size());
		for (LanguageHolder holder : languageHolders) {
			language.add(holder.getLanguage());
		}
		modsTypeClient.setLanguage(language);

		// physical description
		List<PhysicalDescriptionTypeClient> physicalDescription = new ArrayList<PhysicalDescriptionTypeClient>(physicalDescriptionHolders.size());
		for (PhysicalDescriptionHolder holder : physicalDescriptionHolders) {
			physicalDescription.add(holder.getPhysicalDescription());
		}
		modsTypeClient.setPhysicalDescription(physicalDescription);

		// abstract
		List<AbstractTypeClient> abstractt = new ArrayList<AbstractTypeClient>(abstractHolders.size());
		for (AbstractHolder holder : abstractHolders) {
			abstractt.add(holder.getAbstract());
		}
		modsTypeClient.setAbstrac(abstractt);

		// toc
		List<TableOfContentsTypeClient> toc = new ArrayList<TableOfContentsTypeClient>(tableOfContentsHolders.size());
		for (TableOfContentsHolder holder : tableOfContentsHolders) {
			toc.add(holder.getTableOfContents());
		}
		modsTypeClient.setTableOfContents(toc);

		modsTypeClient.setTargetAudience(audienceHolder.getAudience());

		// note
		List<NoteTypeClient> note = new ArrayList<NoteTypeClient>(noteHolders.size());
		for (NoteHolder holder : noteHolders) {
			note.add(holder.getNote());
		}
		modsTypeClient.setNote(note);

		// subject
		List<SubjectTypeClient> subject = new ArrayList<SubjectTypeClient>(subjectHolders.size());
		for (SubjectHolder holder : subjectHolders) {
			subject.add(holder.getSubject());
		}
		modsTypeClient.setSubject(subject);

		return modsTypeClient;
	}

}
