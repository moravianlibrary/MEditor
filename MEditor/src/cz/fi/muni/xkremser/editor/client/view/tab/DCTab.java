/**
 * Metadata Editor
 * @author Jiri Kremser
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
	private DublinCore dc;
	private MetadataHolder contributor;
	private MetadataHolder coverage;
	private MetadataHolder creator;
	private MetadataHolder date;
	private MetadataHolder description;
	private MetadataHolder format;
	private MetadataHolder identifier;
	private MetadataHolder language;
	private MetadataHolder publisher;
	private MetadataHolder relation;
	private MetadataHolder rights;
	private MetadataHolder source;
	private MetadataHolder subject;
	private MetadataHolder title;
	private MetadataHolder type;

	public DCTab(String title, String icon) {
		super(title, icon);
	}

	/**
	 * Instantiates a new dC tab.
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

	public void setDc(DublinCore dc) {
		this.dc = dc;
	}

}
