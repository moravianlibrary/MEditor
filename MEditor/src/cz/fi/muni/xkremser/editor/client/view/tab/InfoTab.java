package cz.fi.muni.xkremser.editor.client.view.tab;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

public class InfoTab extends Tab {

	public InfoTab(String title, String icon, DublinCore dc, LangConstants lang, String type) {
		super(title, icon);
		VStack layout = new VStack();
		layout.setPadding(15);
		HTMLFlow info = new HTMLFlow("<h2>" + lang.doInfo() + "</h2>");
		info.setExtraSpace(25);
		HTMLFlow pid = new HTMLFlow("<b>PID:</b> " + (dc.getIdentifier() != null && dc.getIdentifier().size() > 0 ? dc.getIdentifier().get(0) : lang.noTitle()));
		HTMLFlow tit = new HTMLFlow("<b>" + lang.name() + ":</b> " + (dc.getTitle() != null && dc.getTitle().size() > 0 ? dc.getTitle().get(0) : lang.noTitle()));
		HTMLFlow typ = new HTMLFlow("<b>" + lang.dcType() + ":</b> " + type);
		layout.setMembers(info, pid, tit, typ);

		setPane(layout);
	}

}
