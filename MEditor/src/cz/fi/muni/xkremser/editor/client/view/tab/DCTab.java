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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;

// TODO: Auto-generated Javadoc
/**
 * The Class DCTab.
 */
public class DCTab extends Tab {

	/**
	 * Instantiates a new dC tab.
	 */
	public DCTab() {
		super("DC", "pieces/16/pawn_green.png");

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();

		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(TabUtils.getSimpleSection(TextAreaItem.class, "Title", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Identifiers", "Identifier", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Creators", "Creator", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Publishers", "Publisher", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Contributors", "Contributor", null, true));
		sectionStack.addSection(TabUtils.getSimpleSection(DateItem.class, "Date", null, true));
		sectionStack.addSection(TabUtils.getSimpleSection(TextItem.class, "Language", null, true));
		sectionStack.addSection(TabUtils.getSimpleSection(TextAreaItem.class, "Description", null, false));
		sectionStack.addSection(TabUtils.getSimpleSection(TextItem.class, "Format", null, false));
		sectionStack.addSection(TabUtils.getStackSection("Subjects", "Subject", null, false));
		sectionStack.addSection(TabUtils.getSimpleSection(TextItem.class, "Type", null, false));
		sectionStack.addSection(TabUtils.getSimpleSection(TextAreaItem.class, "Rights", null, false));

		setPane(sectionStack);
	}
}
