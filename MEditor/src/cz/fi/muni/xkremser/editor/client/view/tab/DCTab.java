package cz.fi.muni.xkremser.editor.client.view.tab;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;

public class DCTab extends Tab {

	public DCTab() {
		super("DC", "pieces/16/pawn_green.png");

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();

		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(TabUtils.getSimpleStackSection(TextAreaItem.class, "Title", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Identifiers", "Identifier", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Creators", "Creator", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Publishers", "Publisher", null, true));
		sectionStack.addSection(TabUtils.getStackSection("Contributors", "Contributor", null, true));
		sectionStack.addSection(TabUtils.getSimpleStackSection(DateItem.class, "Date", null, true));
		sectionStack.addSection(TabUtils.getSimpleStackSection(TextItem.class, "Language", null, true));
		sectionStack.addSection(TabUtils.getSimpleStackSection(TextAreaItem.class, "Description", null, false));
		sectionStack.addSection(TabUtils.getSimpleStackSection(TextItem.class, "Format", null, false));
		sectionStack.addSection(TabUtils.getStackSection("Subjects", "Subject", null, false));
		sectionStack.addSection(TabUtils.getSimpleStackSection(TextItem.class, "Type", null, false));
		sectionStack.addSection(TabUtils.getSimpleStackSection(TextAreaItem.class, "Rights", null, false));

		setPane(sectionStack);
	}
}
