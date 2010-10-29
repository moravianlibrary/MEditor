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
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();
		sectionStack.setOverflow(Overflow.AUTO);

		sectionStack.addSection(TabUtils.getTitleInfoStack(false));
		sectionStack.addSection(TabUtils.getNameStack(false));
		sectionStack.addSection(TabUtils.getTypeOfResourceStack(false));

		setPane(sectionStack);
	}

}
