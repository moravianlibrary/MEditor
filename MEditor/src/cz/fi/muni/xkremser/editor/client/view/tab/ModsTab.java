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
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import cz.fi.muni.xkremser.editor.client.metadata.AbstractHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AudienceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ClassificationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.GenreHolder;
import cz.fi.muni.xkremser.editor.client.metadata.IdentifierHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LanguageHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LocationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.metadata.NameHolder;
import cz.fi.muni.xkremser.editor.client.metadata.NoteHolder;
import cz.fi.muni.xkremser.editor.client.metadata.OriginInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PhysicalDescriptionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.RelatedItemHolder;
import cz.fi.muni.xkremser.editor.client.metadata.SubjectHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TableOfContentsHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TitleInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TypeOfResourceHolder;
import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RelatedItemTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class ModsTab.
 */
public class ModsTab extends Tab implements RelatedItemHolder {
	// TODO: dat do konstanty, nebo resource bundlu
	/** The Constant MAX_DEEP. */
	public static final Tab MAX_DEEP(final String id) {
		return new Tab("Related Item", "pieces/16/piece_blue.png") {
			{
				setID(id);
				setPane(new Label("Maximum level of recursion of element Related Item has been reached."));
			}
		};
	}

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
	private final ClassificationHolder classificationHolder;
	private final List<RelatedItemHolder> relatedItemHolders;
	private final ListOfListOfSimpleValuesHolder relatedItemAttributeHolder;
	private final IdentifierHolder identifierHolder;
	private final List<LocationHolder> locationHolders;

	/**
	 * The Class GetRelatedItem.
	 */
	private class GetRelatedItem extends GetLayoutOperation {

		public GetRelatedItem() {
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
			RelatedItemTypeClient modsTypeClient = null;
			if (getValues() != null && getValues().size() > counter && getValues().get(counter) != null) {
				modsTypeClient = (RelatedItemTypeClient) getValues().get(counter);
			}

			ModsTab modsTab = new ModsTab(deep - 1, false);
			((List<ModsTab>) getHolders()).add(modsTab);
			increaseCounter();
			return modsTab.getModsLayout(null, true, modsTypeClient, getCounter());
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
	public ModsTab(int deep, boolean topLvl) {
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
		audienceHolder = new AudienceHolder();
		noteHolders = new ArrayList<NoteHolder>();
		subjectHolders = new ArrayList<SubjectHolder>();
		classificationHolder = new ClassificationHolder();
		relatedItemHolders = new ArrayList<RelatedItemHolder>();
		relatedItemAttributeHolder = new ListOfListOfSimpleValuesHolder();
		identifierHolder = new IdentifierHolder();
		locationHolders = new ArrayList<LocationHolder>();
		this.deep = deep;
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
	public VLayout getModsLayout(ModsTypeClient modsTypeClient, boolean attributePresent, RelatedItemTypeClient relatedItem, int counter) {
		VLayout layout = new VLayout();

		ModsTypeClient modsTypeClient_ = modsTypeClient;
		if (attributePresent) {
			modsTypeClient_ = relatedItem == null ? null : relatedItem.getMods();
			Attribute[] attributes = new Attribute[] {
					new Attribute(SelectItem.class, ModsConstants.TYPE, "Type", new HashMap<String, String>() {
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
							put("otherVersion",
									"Information concerning another version (i.e. change in intellectual content) of the resource (Equivalent to MARC 21 field 775).");
							put("otherFormat", "Information concerning another format (i.e. change in physical format) of the resource (Equivalent to MARC 21 field 776). ");
							put("isReferencedBy",
									"Citations or references to published bibliographic descriptions, reviews, abstracts, or indexes of the content of the resource (Roughly equivalent to MARC 21 field 510, but allows for additional parsing of data).");
						}
					}, relatedItem == null ? "" : relatedItem.getType()), TabUtils.ATTR_XLINK(relatedItem == null ? "" : relatedItem.getXlink()),
					TabUtils.getDisplayLabel("Equivalent to MARC 21 fields 76X-78X subfields $i and $3.", (relatedItem == null ? "" : relatedItem.getDisplayLabel())),
					TabUtils.ATTR_ID(relatedItem == null ? "" : relatedItem.getId()) };
			DynamicForm form = TabUtils.getAttributes(true, attributes);
			layout.addMember(form);
			relatedItemAttributeHolder.setAttributeForm(form);
		}

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		List<RelatedItemTypeClient> relatedItems = modsTypeClient_ == null ? null : modsTypeClient_.getRelatedItem();

		Tab rel = null;
		if (deep > 0) {
			GetRelatedItem getRelatedItem = new GetRelatedItem();
			getRelatedItem.setValues(relatedItems);
			getRelatedItem.setHolders(relatedItemHolders);
			rel = getTab(TabUtils.getSomeStack(true, "Related Item", getRelatedItem), "Related Item");
		} else {
			rel = MAX_DEEP(deep + ":" + counter);
		}

		// relatedItems.g
		Tab[] tabs = new Tab[] {
				getTab(TabUtils.getTitleInfoStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTitleInfo(), titleInfoHolders), "Title Info"),
				getTab(TabUtils.getNameStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getName(), nameHolders), "Name"),
				getTab(TabUtils.getTypeOfResourceStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTypeOfResource(), typeOfResourceHolders), "Type"),
				getTab(TabUtils.getGenreStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getGenre(), genreHolders), "Genre"),
				getTab(TabUtils.getOriginInfoStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getOriginInfo(), originInfoHolders), "Origin"),
				getTab(TabUtils.getLanguageStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getLanguage(), languageHolders), "Language"),
				getTab(
						TabUtils.getPhysicalDescriptionStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getPhysicalDescription(), physicalDescriptionHolders),
						"Physical desc."),
				getTab(TabUtils.getAbstractStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getAbstrac(), abstractHolders), "Abstract"),
				getTab(TabUtils.getTableOfContentsStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTableOfContents(), tableOfContentsHolders),
						"Table of Con."),
				getTab(TabUtils.getTargetAudienceStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTargetAudience(), audienceHolder), "Audience"),
				getTab(TabUtils.getNoteStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getNote(), noteHolders), "Note"),
				getTab(TabUtils.getSubjectStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getSubject(), subjectHolders), "Subject"),
				getTab(TabUtils.getClassificationStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getClassification(), classificationHolder),
						"Classification"), rel,
				getTab(TabUtils.getIdentifierStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getIdentifier(), identifierHolder), "Identifier"),
				getTab(TabUtils.getLocationStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getLocation(), locationHolders), "Location"),
				getTab(TabUtils.getAccessConditionStack(true), "Access Condition"), getTab(TabUtils.getPartStack(true), "Part"),
				getTab(TabUtils.getExtensionStack(true), "Extension"), getTab(TabUtils.getRecordInfoStack(true), "Record Info") };
		topTabSet.setTabs(tabs);
		layout.addMember(topTabSet);
		return layout;
	}

	@Override
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

		// audience
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

		// classification
		modsTypeClient.setClassification(classificationHolder.getClassification());

		// related item
		List<RelatedItemTypeClient> items = new ArrayList<RelatedItemTypeClient>(relatedItemHolders.size());
		for (RelatedItemHolder item : relatedItemHolders) {
			RelatedItemTypeClient relatedItemTypeClient = new RelatedItemTypeClient();
			if (item != null) {
				if (item.getRelatedItemAttributeHolder().getAttributeForm() != null) {
					relatedItemTypeClient.setDisplayLabel(item.getRelatedItemAttributeHolder().getAttributeForm().getValueAsString(ModsConstants.DISPLAY_LABEL));
					relatedItemTypeClient.setId(item.getRelatedItemAttributeHolder().getAttributeForm().getValueAsString(ModsConstants.ID));
					relatedItemTypeClient.setType(item.getRelatedItemAttributeHolder().getAttributeForm().getValueAsString(ModsConstants.TYPE));
					relatedItemTypeClient.setXlink(item.getRelatedItemAttributeHolder().getAttributeForm().getValueAsString(ModsConstants.XLINK));
				}
				relatedItemTypeClient.setMods(item.getMods());
			}
			items.add(relatedItemTypeClient);
		}
		modsTypeClient.setRelatedItem(items);

		// identifier
		modsTypeClient.setIdentifier(identifierHolder.getIdentifier());

		// location
		List<LocationTypeClient> location = new ArrayList<LocationTypeClient>(locationHolders.size());
		for (LocationHolder holder : locationHolders) {
			location.add(holder.getLocation());
		}
		modsTypeClient.setLocation(location);

		return modsTypeClient;
	}

	@Override
	public ListOfListOfSimpleValuesHolder getRelatedItemAttributeHolder() {
		return relatedItemAttributeHolder;
	}

}
