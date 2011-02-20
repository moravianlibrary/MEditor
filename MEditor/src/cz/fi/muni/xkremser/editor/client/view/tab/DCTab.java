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

import com.google.inject.Inject;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder;
import cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class DCTab.
 */
public class DCTab extends Tab {

	@Inject
	private static LangConstants lang;

	/** The dc. */
	private DublinCore dc;

	/** The contributor. */
	private MetadataHolder contributor;

	/** The coverage. */
	private MetadataHolder coverage;

	/** The creator. */
	private MetadataHolder creator;

	/** The date. */
	private MetadataHolder date;

	/** The description. */
	private MetadataHolder description;

	/** The format. */
	private MetadataHolder format;

	/** The identifier. */
	private MetadataHolder identifier;

	/** The language. */
	private MetadataHolder language;

	/** The publisher. */
	private MetadataHolder publisher;

	/** The relation. */
	private MetadataHolder relation;

	/** The rights. */
	private MetadataHolder rights;

	/** The source. */
	private MetadataHolder source;

	/** The subject. */
	private MetadataHolder subject;

	/** The title. */
	private MetadataHolder title;

	/** The type. */
	private MetadataHolder type;

	/**
	 * Instantiates a new dC tab.
	 * 
	 * @param title
	 *          the title
	 * @param icon
	 *          the icon
	 */
	public DCTab(String title, String icon) {
		super(title, icon);
	}

	/**
	 * Instantiates a new dC tab.
	 * 
	 * @param dc
	 *          the dc
	 */
	public DCTab(DublinCore dc) {
		super("DC", "pieces/16/pawn_green.png");
		this.dc = dc;

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.AUTO);

		contributor = new ListOfSimpleValuesHolder();
		coverage = new ListOfSimpleValuesHolder();
		creator = new ListOfSimpleValuesHolder();
		date = new ListOfSimpleValuesHolder();
		description = new ListOfSimpleValuesHolder();
		format = new ListOfSimpleValuesHolder();
		identifier = new ListOfSimpleValuesHolder();
		language = new ListOfSimpleValuesHolder();
		publisher = new ListOfSimpleValuesHolder();
		relation = new ListOfSimpleValuesHolder();
		rights = new ListOfSimpleValuesHolder();
		source = new ListOfSimpleValuesHolder();
		subject = new ListOfSimpleValuesHolder();
		title = new ListOfSimpleValuesHolder();
		type = new ListOfSimpleValuesHolder();

		sectionStack.addSection(TabUtils.getStackSection(lang.dcTitles(), lang.dcTitle(), lang.dcTitleHoover(), true, dc.getTitle(), title));
		sectionStack
				.addSection(TabUtils.getStackSection(lang.dcIdentifiers(), lang.dcIdentifier(), lang.dcIdentifierHoover(), true, dc.getIdentifier(), identifier));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcCreators(), lang.dcCreator(), lang.dcCreatorHoover(), true, dc.getCreator(), creator));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcPublishers(), lang.dcPublisher(), lang.dcPublisherHoover(), true, dc.getPublisher(), publisher));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcContributors(), lang.dcContributor(), lang.dcContributorHoover(), true, dc.getContributor(),
				contributor));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(DateItem.class, lang.dcDates(), lang.dcDate(), lang.dcDateHoover()), true, dc.getDate(),
				date));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcLanguages(), lang.dcLanguage(), lang.dcLanguageHoover(), true, dc.getLanguage(), language));
		sectionStack.addSection(TabUtils.getStackSection(
				new Attribute(TextAreaItem.class, lang.dcDescriptions(), lang.dcDescription(), lang.dcDescriptionHoover()), false, dc.getDescription(), description));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcFormats(), lang.dcFormat(), lang.dcFormatHoover(), false, dc.getFormat(), format));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcSubjects(), lang.dcSubject(), lang.dcSubjectHoover(), false, dc.getSubject(), subject));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcTypes(), lang.dcType(), lang.dcTypeHoover(), false, dc.getType(), type));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, lang.dcRights(), lang.dcRight(), lang.dcRightHoover()), false,
				dc.getRights(), rights));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcCoverages(), lang.dcCoverage(), lang.dcCoverageHoover(), false, dc.getCoverage(), coverage));
		sectionStack.addSection(TabUtils.getStackSection(lang.dcRelations(), lang.dcRelation(), lang.dcRelationHoover(), false, dc.getRelation(), relation));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, lang.dcSources(), lang.dcSource(), lang.dcSourceHoover()), false,
				dc.getSource(), source));

		setPane(sectionStack);
	}

	/**
	 * Gets the dc.
	 * 
	 * @return the dc
	 */
	public DublinCore getDc() {
		if (dc == null)
			return null;
		dc.setIdentifier(identifier.getValues());
		dc.setContributor(contributor.getValues());
		dc.setCoverage(coverage.getValues());
		dc.setCreator(creator.getValues());
		dc.setDate(date.getValues());
		dc.setDescription(description.getValues());
		dc.setFormat(format.getValues());
		dc.setIdentifier(identifier.getValues());
		dc.setLanguage(language.getValues());
		dc.setPublisher(publisher.getValues());
		dc.setRelation(relation.getValues());
		dc.setRights(rights.getValues());
		dc.setSource(source.getValues());
		dc.setSubject(subject.getValues());
		dc.setTitle(title.getValues());
		dc.setType(type.getValues());
		return dc;
	}

	/**
	 * Sets the dc.
	 * 
	 * @param dc
	 *          the new dc
	 */
	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

}
