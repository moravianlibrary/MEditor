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

import cz.fi.muni.xkremser.editor.shared.valueobj.DublinCore;

// TODO: Auto-generated Javadoc
/**
 * The Class DCTab.
 */
public class DCTab extends Tab {

	/**
	 * Instantiates a new dC tab.
	 */
	public DCTab(DublinCore dc) {
		super("DC", "pieces/16/pawn_green.png");

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();

		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(TabUtils.getStackSection("Titles", "Title", "A name given to the resource.", true, dc.getTitle()));
		sectionStack.addSection(TabUtils.getStackSection("Identifiers", "Identifier", "An unambiguous reference to the resource within a given context.", true,
				dc.getIdentifier()));
		sectionStack.addSection(TabUtils.getStackSection("Creators", "Creator", "An entity primarily responsible for making the resource.", true, dc.getCreator()));
		sectionStack.addSection(TabUtils.getStackSection("Publishers", "Publisher", "An entity responsible for making the resource available.", true,
				dc.getPublisher()));
		sectionStack.addSection(TabUtils.getStackSection("Contributors", "Contributor", "An entity responsible for making contributions to the resource.", true,
				dc.getContributor()));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(DateItem.class, "date", "Date",
				"A point or period of time associated with an event in the lifecycle of the resource."), true, dc.getDate()));
		sectionStack.addSection(TabUtils.getStackSection("Languages", "Language", "A language of the resource.", true, dc.getLanguage()));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "description", "Description", "An account of the resource."), false,
				dc.getDescription()));
		sectionStack.addSection(TabUtils.getStackSection("Formats", "Format", "The file format, physical medium, or dimensions of the resource.", false,
				dc.getFormat()));
		sectionStack.addSection(TabUtils.getStackSection("Subjects", "Subject", "The topic of the resource.", false, dc.getSubject()));
		sectionStack.addSection(TabUtils.getStackSection("Types", "Type", "The nature or genre of the resource.", false, dc.getType()));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "rights", "Rights",
				"Information about rights held in and over the resource."), false, dc.getRights()));
		sectionStack.addSection(TabUtils.getStackSection("Coverages", "Coverage",
				"The spatial or temporal topic of the resource, the spatial applicability of the resource, or the jurisdiction under which the resource is relevant.",
				false, dc.getCoverage()));
		sectionStack.addSection(TabUtils.getStackSection("Relations", "Relation", "A related resource.", false, dc.getRelation()));
		sectionStack.addSection(TabUtils.getStackSection(new Attribute(TextAreaItem.class, "source", "Source",
				"A related resource from which the described resource is derived."), false, dc.getSource()));

		setPane(sectionStack);
	}
}
