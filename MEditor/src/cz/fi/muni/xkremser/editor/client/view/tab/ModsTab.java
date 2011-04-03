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
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.Timer;
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
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import cz.fi.muni.xkremser.editor.client.metadata.AbstractHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AccessConditionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.AudienceHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ClassificationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ExtensionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.GenreHolder;
import cz.fi.muni.xkremser.editor.client.metadata.IdentifierHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LanguageHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ListOfListOfSimpleValuesHolder;
import cz.fi.muni.xkremser.editor.client.metadata.LocationHolder;
import cz.fi.muni.xkremser.editor.client.metadata.ModsConstants;
import cz.fi.muni.xkremser.editor.client.metadata.NameHolder;
import cz.fi.muni.xkremser.editor.client.metadata.NoteHolder;
import cz.fi.muni.xkremser.editor.client.metadata.OriginInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PartHolder;
import cz.fi.muni.xkremser.editor.client.metadata.PhysicalDescriptionHolder;
import cz.fi.muni.xkremser.editor.client.metadata.RecordInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.RelatedItemHolder;
import cz.fi.muni.xkremser.editor.client.metadata.SubjectHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TableOfContentsHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TitleInfoHolder;
import cz.fi.muni.xkremser.editor.client.metadata.TypeOfResourceHolder;
import cz.fi.muni.xkremser.editor.client.mods.AbstractTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.AccessConditionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ExtensionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.GenreTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LanguageTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.LocationTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.ModsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NameTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.NoteTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.OriginInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PartTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.PhysicalDescriptionTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RecordInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.RelatedItemTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.SubjectTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TableOfContentsTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TitleInfoTypeClient;
import cz.fi.muni.xkremser.editor.client.mods.TypeOfResourceTypeClient;
import cz.fi.muni.xkremser.editor.client.view.ModalWindow;
import cz.fi.muni.xkremser.editor.client.view.tab.TabUtils.GetLayoutOperation;

// TODO: Auto-generated Javadoc
/**
 * The Class ModsTab.
 */
public class ModsTab extends Tab implements RelatedItemHolder {
	// TODO: dat do konstanty, nebo resource bundlu
	/** The Constant MAX_DEEP. */
	private static final String TAB_TYPE = "type";

	/** The Constant MODS_TITLE. */
	private static final int MODS_TITLE = 0;

	/** The Constant MODS_NAME. */
	private static final int MODS_NAME = 1;

	/** The Constant MODS_TYPE. */
	private static final int MODS_TYPE = 2;

	/** The Constant MODS_GENRE. */
	private static final int MODS_GENRE = 3;

	/** The Constant MODS_ORIGIN. */
	private static final int MODS_ORIGIN = 4;

	/** The Constant MODS_LANG. */
	private static final int MODS_LANG = 5;

	/** The Constant MODS_PHYS. */
	private static final int MODS_PHYS = 6;

	/** The Constant MODS_ABSTRACT. */
	private static final int MODS_ABSTRACT = 7;

	/** The Constant MODS_TOC. */
	private static final int MODS_TOC = 8;

	/** The Constant MODS_TARGET. */
	private static final int MODS_TARGET = 9;

	/** The Constant MODS_NOTE. */
	private static final int MODS_NOTE = 10;

	/** The Constant MODS_SUBJECT. */
	private static final int MODS_SUBJECT = 11;

	/** The Constant MODS_CLASS. */
	private static final int MODS_CLASS = 12;

	/** The Constant MODS_RELATED. */
	private static final int MODS_RELATED = 13;

	/** The Constant MODS_IDENTIFIER. */
	private static final int MODS_IDENTIFIER = 14;

	/** The Constant MODS_LOCATION. */
	private static final int MODS_LOCATION = 15;

	/** The Constant MODS_ACCESS. */
	private static final int MODS_ACCESS = 16;

	/** The Constant MODS_PART. */
	private static final int MODS_PART = 17;

	/** The Constant MODS_EXTENSION. */
	private static final int MODS_EXTENSION = 18;

	/** The Constant MODS_RECORD. */
	private static final int MODS_RECORD = 19;

	/**
	 * MA x_ deep.
	 * 
	 * @param id
	 *          the id
	 * @return the tab
	 */
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

	/** The title info holders. */
	private final List<TitleInfoHolder> titleInfoHolders;

	/** The name holders. */
	private final List<NameHolder> nameHolders;

	/** The type of resource holders. */
	private final List<TypeOfResourceHolder> typeOfResourceHolders;

	/** The genre holders. */
	private final List<GenreHolder> genreHolders;

	/** The origin info holders. */
	private final List<OriginInfoHolder> originInfoHolders;

	/** The language holders. */
	private final List<LanguageHolder> languageHolders;

	/** The physical description holders. */
	private final List<PhysicalDescriptionHolder> physicalDescriptionHolders;

	/** The abstract holders. */
	private final List<AbstractHolder> abstractHolders;

	/** The table of contents holders. */
	private final List<TableOfContentsHolder> tableOfContentsHolders;

	/** The audience holder. */
	private final AudienceHolder audienceHolder;

	/** The note holders. */
	private final List<NoteHolder> noteHolders;

	/** The subject holders. */
	private final List<SubjectHolder> subjectHolders;

	/** The classification holder. */
	private final ClassificationHolder classificationHolder;

	/** The related item holders. */
	private final List<RelatedItemHolder> relatedItemHolders;

	/** The related item attribute holder. */
	private final ListOfListOfSimpleValuesHolder relatedItemAttributeHolder;

	/** The identifier holder. */
	private final IdentifierHolder identifierHolder;

	/** The location holders. */
	private final List<LocationHolder> locationHolders;

	/** The access condition holders. */
	private final List<AccessConditionHolder> accessConditionHolders;

	/** The part holders. */
	private final List<PartHolder> partHolders;

	/** The extension holders. */
	private final List<ExtensionHolder> extensionHolders;

	/** The record info holders. */
	private final List<RecordInfoHolder> recordInfoHolders;

	/** The tabs initialized. */
	private final boolean[] tabsInitialized = new boolean[20];

	/** The mods type client_. */
	private ModsTypeClient modsTypeClient_;

	/**
	 * The Class GetRelatedItem.
	 */
	private class GetRelatedItem extends GetLayoutOperation {

		/**
		 * Instantiates a new gets the related item.
		 */
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
		accessConditionHolders = new ArrayList<AccessConditionHolder>();
		partHolders = new ArrayList<PartHolder>();
		extensionHolders = new ArrayList<ExtensionHolder>();
		recordInfoHolders = new ArrayList<RecordInfoHolder>();
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
		Tab tab = new Tab(name);
		tab.setPane(sectionStack);
		return tab;
	}

	/**
	 * Gets the mods tab set.
	 * 
	 * @param modsTypeClient
	 *          the mods type client
	 * @param attributePresent
	 *          the attribute present
	 * @param relatedItem
	 *          the related item
	 * @param counter
	 *          the counter
	 * @return the mods tab set
	 */
	public VLayout getModsLayout(ModsTypeClient modsTypeClient, boolean attributePresent, RelatedItemTypeClient relatedItem, final int counter) {
		VLayout layout = new VLayout();

		modsTypeClient_ = modsTypeClient;
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
		final List<RelatedItemTypeClient> relatedItems = modsTypeClient_ == null ? null : modsTypeClient_.getRelatedItem();
		Tab name = new Tab("Name");
		name.setAttribute(TAB_TYPE, MODS_NAME);
		Tab type = new Tab("Type");
		type.setAttribute(TAB_TYPE, MODS_TYPE);
		Tab genre = new Tab("Genre");
		genre.setAttribute(TAB_TYPE, MODS_GENRE);
		Tab origin = new Tab("Origin");
		origin.setAttribute(TAB_TYPE, MODS_ORIGIN);
		Tab language = new Tab("Language");
		language.setAttribute(TAB_TYPE, MODS_LANG);
		Tab physical = new Tab("Physical desc.");
		physical.setAttribute(TAB_TYPE, MODS_PHYS);
		Tab abstractt = new Tab("Abstract");
		abstractt.setAttribute(TAB_TYPE, MODS_ABSTRACT);
		Tab toc = new Tab("Table of Con.");
		toc.setAttribute(TAB_TYPE, MODS_TOC);
		Tab audience = new Tab("Audience");
		audience.setAttribute(TAB_TYPE, MODS_TARGET);
		Tab note = new Tab("Note");
		note.setAttribute(TAB_TYPE, MODS_NOTE);
		Tab subject = new Tab("Subject");
		subject.setAttribute(TAB_TYPE, MODS_SUBJECT);
		Tab classification = new Tab("Classification");
		classification.setAttribute(TAB_TYPE, MODS_CLASS);
		Tab related = new Tab("Related Item");
		related.setAttribute(TAB_TYPE, MODS_RELATED);
		Tab identifier = new Tab("Identifier");
		identifier.setAttribute(TAB_TYPE, MODS_IDENTIFIER);
		Tab location = new Tab("Location");
		location.setAttribute(TAB_TYPE, MODS_LOCATION);
		Tab access = new Tab("Access Conditio");
		access.setAttribute(TAB_TYPE, MODS_ACCESS);
		Tab part = new Tab("Part");
		part.setAttribute(TAB_TYPE, MODS_PART);
		Tab extension = new Tab("Extension");
		extension.setAttribute(TAB_TYPE, MODS_EXTENSION);
		Tab record = new Tab("Record Info");
		record.setAttribute(TAB_TYPE, MODS_RECORD);

		Tab[] lazyTabs = new Tab[] {
				getTab(TabUtils.getTitleInfoStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTitleInfo(), titleInfoHolders), "Title Info"), name, type,
				genre, origin, language, physical, abstractt, toc, audience, note, subject, classification, related, identifier, location, access, part, extension,
				record };
		tabsInitialized[MODS_TITLE] = true;

		topTabSet.setTabs(lazyTabs);
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(final TabSelectedEvent event) {
				if (event.getTab().getPane() == null) {
					final ModalWindow mw = new ModalWindow(topTabSet);
					Timer timer = null;
					switch (event.getTab().getAttributeAsInt(TAB_TYPE)) {

						case MODS_NAME:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getNameStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getName(), nameHolders), "Name");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_NAME, t.getPane());
									tabsInitialized[MODS_NAME] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_TYPE:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(
											TabUtils.getTypeOfResourceStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTypeOfResource(), typeOfResourceHolders),
											"Type");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_TYPE, t.getPane());
									tabsInitialized[MODS_TYPE] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_GENRE:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getGenreStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getGenre(), genreHolders), "Genre");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_GENRE, t.getPane());
									tabsInitialized[MODS_GENRE] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_ORIGIN:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getOriginInfoStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getOriginInfo(), originInfoHolders),
											"Origin");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_ORIGIN, t.getPane());
									tabsInitialized[MODS_ORIGIN] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_LANG:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getLanguageStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getLanguage(), languageHolders), "Language");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_LANG, t.getPane());
									tabsInitialized[MODS_LANG] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_ABSTRACT:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getAbstractStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getAbstrac(), abstractHolders), "Abstract");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_ABSTRACT, t.getPane());
									tabsInitialized[MODS_ABSTRACT] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_PHYS:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getPhysicalDescriptionStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getPhysicalDescription(),
											physicalDescriptionHolders), "Physical desc.");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_PHYS, t.getPane());
									tabsInitialized[MODS_PHYS] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_TOC:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(
											TabUtils.getTableOfContentsStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTableOfContents(), tableOfContentsHolders),
											"Table of Con.");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_TOC, t.getPane());
									tabsInitialized[MODS_TOC] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_TARGET:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getTargetAudienceStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getTargetAudience(), audienceHolder),
											"Audience");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_TARGET, t.getPane());
									tabsInitialized[MODS_TARGET] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_NOTE:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getNoteStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getNote(), noteHolders), "Note");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_NOTE, t.getPane());
									tabsInitialized[MODS_NOTE] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_SUBJECT:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getSubjectStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getSubject(), subjectHolders), "Subject");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_SUBJECT, t.getPane());
									tabsInitialized[MODS_SUBJECT] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_CLASS:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(
											TabUtils.getClassificationStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getClassification(), classificationHolder),
											"Classification");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_CLASS, t.getPane());
									tabsInitialized[MODS_CLASS] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_RELATED:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = null;
									if (deep > 0) {
										GetRelatedItem getRelatedItem = new GetRelatedItem();
										getRelatedItem.setValues(relatedItems);
										getRelatedItem.setHolders(relatedItemHolders);
										t = getTab(TabUtils.getSomeStack(true, "Related Item", getRelatedItem), "Related Item");
									} else {
										t = MAX_DEEP(deep + ":" + counter);
									}
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_RELATED, t.getPane());
									tabsInitialized[MODS_RELATED] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_LOCATION:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getLocationStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getLocation(), locationHolders), "Location");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_LOCATION, t.getPane());
									tabsInitialized[MODS_LOCATION] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_IDENTIFIER:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getIdentifierStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getIdentifier(), identifierHolder),
											"Identifier");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_IDENTIFIER, t.getPane());
									tabsInitialized[MODS_IDENTIFIER] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_ACCESS:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(
											TabUtils.getAccessConditionStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getAccessCondition(), accessConditionHolders),
											"Access Condition");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_ACCESS, t.getPane());
									tabsInitialized[MODS_ACCESS] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_PART:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getPartStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getPart(), partHolders), "Part");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_PART, t.getPane());
									tabsInitialized[MODS_PART] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_EXTENSION:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getExtensionStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getExtension(), extensionHolders),
											"Extension");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_EXTENSION, t.getPane());
									tabsInitialized[MODS_EXTENSION] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;
						case MODS_RECORD:
							mw.setLoadingIcon("loadingAnimation.gif");
							mw.show(true);
							timer = new Timer() {
								@Override
								public void run() {
									Tab t = getTab(TabUtils.getRecordInfoStack(true, modsTypeClient_ == null ? null : modsTypeClient_.getRecordInfo(), recordInfoHolders),
											"Record Info");
									TabSet ts = event.getTab().getTabSet();
									ts.setTabPane(MODS_RECORD, t.getPane());
									tabsInitialized[MODS_RECORD] = true;
									mw.hide();
								}
							};
							timer.schedule(25);
						break;

					}
				}

			}
		});
		layout.addMember(topTabSet);
		return layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.RelatedItemHolder#getMods()
	 */
	@Override
	public ModsTypeClient getMods() {
		ModsTypeClient modsTypeClient = new ModsTypeClient();

		// title info
		if (tabsInitialized[MODS_TITLE]) {
			List<TitleInfoTypeClient> titleInfo = new ArrayList<TitleInfoTypeClient>(titleInfoHolders.size());
			for (TitleInfoHolder holder : titleInfoHolders) {
				titleInfo.add(holder.getTitleInfo());
			}
			modsTypeClient.setTitleInfo(titleInfo);
		} else {
			modsTypeClient.setTitleInfo(modsTypeClient_ == null ? null : modsTypeClient_.getTitleInfo());
		}

		// name
		if (tabsInitialized[MODS_NAME]) {
			List<NameTypeClient> names = new ArrayList<NameTypeClient>(nameHolders.size());
			for (NameHolder holder : nameHolders) {
				names.add(holder.getName());
			}
			modsTypeClient.setName(names);
		} else {
			modsTypeClient.setName(modsTypeClient_ == null ? null : modsTypeClient_.getName());
		}

		// type of resource
		if (tabsInitialized[MODS_TYPE]) {
			List<TypeOfResourceTypeClient> typesOfResource = new ArrayList<TypeOfResourceTypeClient>(typeOfResourceHolders.size());
			for (TypeOfResourceHolder holder : typeOfResourceHolders) {
				typesOfResource.add(holder.getType());
			}
			modsTypeClient.setTypeOfResource(typesOfResource);
		} else {
			modsTypeClient.setTypeOfResource(modsTypeClient_ == null ? null : modsTypeClient_.getTypeOfResource());
		}

		// genre
		if (tabsInitialized[MODS_GENRE]) {
			List<GenreTypeClient> genre = new ArrayList<GenreTypeClient>(genreHolders.size());
			for (GenreHolder holder : genreHolders) {
				genre.add(holder.getGenre());
			}
			modsTypeClient.setGenre(genre);
		} else {
			modsTypeClient.setGenre(modsTypeClient_ == null ? null : modsTypeClient_.getGenre());
		}

		// origin info
		if (tabsInitialized[MODS_ORIGIN]) {
			List<OriginInfoTypeClient> origin = new ArrayList<OriginInfoTypeClient>(originInfoHolders.size());
			for (OriginInfoHolder holder : originInfoHolders) {
				origin.add(holder.getOriginInfo());
			}
			modsTypeClient.setOriginInfo(origin);
		} else {
			modsTypeClient.setOriginInfo(modsTypeClient_ == null ? null : modsTypeClient_.getOriginInfo());
		}

		// language
		if (tabsInitialized[MODS_LANG]) {
			List<LanguageTypeClient> language = new ArrayList<LanguageTypeClient>(languageHolders.size());
			for (LanguageHolder holder : languageHolders) {
				language.add(holder.getLanguage());
			}
			modsTypeClient.setLanguage(language);
		} else {
			modsTypeClient.setLanguage(modsTypeClient_ == null ? null : modsTypeClient_.getLanguage());
		}

		// physical description
		if (tabsInitialized[MODS_PHYS]) {
			List<PhysicalDescriptionTypeClient> physicalDescription = new ArrayList<PhysicalDescriptionTypeClient>(physicalDescriptionHolders.size());
			for (PhysicalDescriptionHolder holder : physicalDescriptionHolders) {
				physicalDescription.add(holder.getPhysicalDescription());
			}
			modsTypeClient.setPhysicalDescription(physicalDescription);
		} else {
			modsTypeClient.setPhysicalDescription(modsTypeClient_ == null ? null : modsTypeClient_.getPhysicalDescription());
		}

		// abstract
		if (tabsInitialized[MODS_ABSTRACT]) {
			List<AbstractTypeClient> abstractt = new ArrayList<AbstractTypeClient>(abstractHolders.size());
			for (AbstractHolder holder : abstractHolders) {
				abstractt.add(holder.getAbstract());
			}
			modsTypeClient.setAbstrac(abstractt);
		} else {
			modsTypeClient.setAbstrac(modsTypeClient_ == null ? null : modsTypeClient_.getAbstrac());
		}

		// toc
		if (tabsInitialized[MODS_TOC]) {
			List<TableOfContentsTypeClient> toc = new ArrayList<TableOfContentsTypeClient>(tableOfContentsHolders.size());
			for (TableOfContentsHolder holder : tableOfContentsHolders) {
				toc.add(holder.getTableOfContents());
			}
			modsTypeClient.setTableOfContents(toc);
		} else {
			modsTypeClient.setTableOfContents(modsTypeClient_ == null ? null : modsTypeClient_.getTableOfContents());
		}

		// audience
		if (tabsInitialized[MODS_TARGET]) {
			modsTypeClient.setTargetAudience(audienceHolder.getAudience());
		} else {
			modsTypeClient.setTargetAudience(modsTypeClient_ == null ? null : modsTypeClient_.getTargetAudience());
		}

		// note
		if (tabsInitialized[MODS_NOTE]) {
			List<NoteTypeClient> note = new ArrayList<NoteTypeClient>(noteHolders.size());
			for (NoteHolder holder : noteHolders) {
				note.add(holder.getNote());
			}
			modsTypeClient.setNote(note);
		} else {
			modsTypeClient.setNote(modsTypeClient_ == null ? null : modsTypeClient_.getNote());
		}

		// subject
		if (tabsInitialized[MODS_SUBJECT]) {
			List<SubjectTypeClient> subject = new ArrayList<SubjectTypeClient>(subjectHolders.size());
			for (SubjectHolder holder : subjectHolders) {
				subject.add(holder.getSubject());
			}
			modsTypeClient.setSubject(subject);
		} else {
			modsTypeClient.setSubject(modsTypeClient_ == null ? null : modsTypeClient_.getSubject());
		}

		// classification
		if (tabsInitialized[MODS_CLASS]) {
			modsTypeClient.setClassification(classificationHolder.getClassification());
		} else {
			modsTypeClient.setClassification(modsTypeClient_ == null ? null : modsTypeClient_.getClassification());
		}

		// related item
		if (tabsInitialized[MODS_RELATED]) {
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
		} else {
			modsTypeClient.setRelatedItem(modsTypeClient_ == null ? null : modsTypeClient_.getRelatedItem());
		}

		// identifier
		if (tabsInitialized[MODS_IDENTIFIER]) {
			modsTypeClient.setIdentifier(identifierHolder.getIdentifier());
		} else {
			modsTypeClient.setIdentifier(modsTypeClient_ == null ? null : modsTypeClient_.getIdentifier());
		}

		// location
		if (tabsInitialized[MODS_LOCATION]) {
			List<LocationTypeClient> location = new ArrayList<LocationTypeClient>(locationHolders.size());
			for (LocationHolder holder : locationHolders) {
				location.add(holder.getLocation());
			}
			modsTypeClient.setLocation(location);
		} else {
			modsTypeClient.setLocation(modsTypeClient_ == null ? null : modsTypeClient_.getLocation());
		}

		// access condition
		if (tabsInitialized[MODS_ACCESS]) {
			List<AccessConditionTypeClient> acc = new ArrayList<AccessConditionTypeClient>(accessConditionHolders.size());
			for (AccessConditionHolder holder : accessConditionHolders) {
				acc.add(holder.getAccessCondition());
			}
			modsTypeClient.setAccessCondition(acc);
		} else {
			modsTypeClient.setAccessCondition(modsTypeClient_ == null ? null : modsTypeClient_.getAccessCondition());
		}

		// part
		if (tabsInitialized[MODS_PART]) {
			List<PartTypeClient> part = new ArrayList<PartTypeClient>(partHolders.size());
			for (PartHolder holder : partHolders) {
				part.add(holder.getPart());
			}
			modsTypeClient.setPart(part);
		} else {
			modsTypeClient.setPart(modsTypeClient_ == null ? null : modsTypeClient_.getPart());
		}

		// extension
		if (tabsInitialized[MODS_EXTENSION]) {
			List<ExtensionTypeClient> extension = new ArrayList<ExtensionTypeClient>(extensionHolders.size());
			for (ExtensionHolder holder : extensionHolders) {
				extension.add(holder.getExtension());
			}
			modsTypeClient.setExtension(extension);
		} else {
			modsTypeClient.setExtension(modsTypeClient_ == null ? null : modsTypeClient_.getExtension());
		}

		// record info
		if (tabsInitialized[MODS_RECORD]) {
			List<RecordInfoTypeClient> record = new ArrayList<RecordInfoTypeClient>(recordInfoHolders.size());
			for (RecordInfoHolder holder : recordInfoHolders) {
				record.add(holder.getRecordInfo());
			}
			modsTypeClient.setRecordInfo(record);
		} else {
			modsTypeClient.setRecordInfo(modsTypeClient_ == null ? null : modsTypeClient_.getRecordInfo());
		}

		return modsTypeClient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.fi.muni.xkremser.editor.client.metadata.RelatedItemHolder#
	 * getRelatedItemAttributeHolder()
	 */
	@Override
	public ListOfListOfSimpleValuesHolder getRelatedItemAttributeHolder() {
		return relatedItemAttributeHolder;
	}

}
