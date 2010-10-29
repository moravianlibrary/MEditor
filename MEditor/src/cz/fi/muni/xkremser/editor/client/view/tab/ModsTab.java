package cz.fi.muni.xkremser.editor.client.view.tab;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.tab.Tab;

public class ModsTab extends Tab {

	public ModsTab() {
		super("MODS", "pieces/16/pawn_red.png");
		final SectionStack sectionStack = new SectionStack();
		sectionStack.setLeaveScrollbarGap(true);
		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(TabUtils.getTitleInfoStack(false));
		sectionStack.addSection(TabUtils.getNameStack(false));
		sectionStack.addSection(TabUtils.getTypeOfResourceStack(false));
		sectionStack.addSection(TabUtils.getGenreStack(false));
		sectionStack.addSection(TabUtils.getOriginInfoStack(false));
		sectionStack.addSection(TabUtils.getLanguageStack(false));
		sectionStack.addSection(TabUtils.getPhysicalDescriptionStack(false));
		sectionStack.addSection(TabUtils.getAbstractStack(false));
		sectionStack.addSection(TabUtils.getTableOfContentsStack(true));

		// sectionStack.addSection(TabUtils.getTargetAudienceStack(false));
		// sectionStack.addSection(TabUtils.getNoteStack(false));
		// sectionStack.addSection(TabUtils.getSubjectStack(false));
		// sectionStack.addSection(TabUtils.getClassificationStack(false));
		// sectionStack.addSection(TabUtils.getRelatedItemStack(false));
		// sectionStack.addSection(TabUtils.getIdentifierStack(false));
		// sectionStack.addSection(TabUtils.getLocationStack(false));
		// sectionStack.addSection(TabUtils.getAccessConditionStack(false));
		// sectionStack.addSection(TabUtils.getParteStack(false));
		// sectionStack.addSection(TabUtils.getExtensionStack(false));
		// sectionStack.addSection(TabUtils.getRecordInfoStack(false));

		setPane(sectionStack);
	}

}
