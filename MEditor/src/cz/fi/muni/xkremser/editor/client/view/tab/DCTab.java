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

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.fi.muni.xkremser.editor.client.metadata.ListOfSimpleValuesHolder;
import cz.fi.muni.xkremser.editor.client.metadata.MetadataHolder;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class DCTab.
 */
public class DCTab extends Tab {
	
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
	 * @param title the title
	 * @param icon the icon
	 */
	public DCTab(String title, String icon) {
		super(title, icon);
	}

	/**
	 * Instantiates a new dC tab.
	 *
	 * @param dc the dc
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

		sectionStack.addSection(TabUtils.getStackSection("Titles", "Title", "A name given to the resource.", true, dc.getTitle(), title));
		sectionStack.addSection(TabUtils.getStackSection("Identifiers", "Identifier", "An unambiguous reference to the resource within a given context.", true,
				dc.getIdentifier(), identifier));
		sectionStack.addSection(TabUtils.getStackSection("Creators", "Creator", "An entity primarily responsible for making the resource.", true, dc.getCreator(),
				creator));
		sectionStack.addSection(TabUtils.getStackSection("Publishers", "Publisher", "An entity responsible for making the resource available.", true,
				dc.getPublisher(), publisher));
		sectionStack.addSection(TabUtils.getStackSection("Contributors", "Contributor", "An entity responsible for making contributions to the resource.", true,
				dc.getContributor(), contributor));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(DateItem.class, "date", "Date",
				"A point or period of time associated with an event in the lifecycle of the resource."), true, dc.getDate(), date));
		sectionStack.addSection(TabUtils.getStackSection("Languages", "Language", "A language of the resource.", true, dc.getLanguage(), language));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "description", "Description", "An account of the resource."), false,
				dc.getDescription(), description));
		sectionStack.addSection(TabUtils.getStackSection("Formats", "Format", "The file format, physical medium, or dimensions of the resource.", false,
				dc.getFormat(), format));
		sectionStack.addSection(TabUtils.getStackSection("Subjects", "Subject", "The topic of the resource.", false, dc.getSubject(), subject));
		sectionStack.addSection(TabUtils.getStackSection("Types", "Type", "The nature or genre of the resource.", false, dc.getType(), type));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "rights", "Rights",
				"Information about rights held in and over the resource."), false, dc.getRights(), rights));
		sectionStack.addSection(TabUtils.getStackSection("Coverages", "Coverage",
				"The spatial or temporal topic of the resource, the spatial applicability of the resource, or the jurisdiction under which the resource is relevant.",
				false, dc.getCoverage(), coverage));
		sectionStack.addSection(TabUtils.getStackSection("Relations", "Relation", "A related resource.", false, dc.getRelation(), relation));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "source", "Source",
				"A related resource from which the described resource is derived."), false, dc.getSource(), source));

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
	 * @param dc the new dc
	 */
	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

}
