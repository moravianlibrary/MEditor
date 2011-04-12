package cz.fi.muni.xkremser.editor.client.view.tab;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.domain.DigitalObjectModel;
import cz.fi.muni.xkremser.editor.shared.valueobj.metadata.DublinCore;

public class InfoTab extends Tab {

	public InfoTab(String title, String icon, DublinCore dc, LangConstants lang, String type, DigitalObjectModel model, String firstPageURL) {
		super(title, icon);
		VStack layout = new VStack();
		layout.setPadding(15);
		HTMLFlow info = new HTMLFlow("<h2>" + lang.doInfo() + "</h2>");
		info.setExtraSpace(25);
		HTMLFlow pid = new HTMLFlow("<b>PID:</b> " + (dc.getIdentifier() != null && dc.getIdentifier().size() > 0 ? dc.getIdentifier().get(0) : lang.noTitle()));
		pid.setExtraSpace(5);
		HTMLFlow tit = new HTMLFlow("<b>" + lang.name() + ":</b> " + (dc.getTitle() != null && dc.getTitle().size() > 0 ? dc.getTitle().get(0) : lang.noTitle()));
		tit.setExtraSpace(5);
		HTMLFlow typ = new HTMLFlow("<b>" + lang.dcType() + ":</b> " + type);
		typ.setExtraSpace(5);
		boolean isPage = DigitalObjectModel.PAGE.equals(model);
		String imgTitle = isPage ? lang.fullImg() : lang.doFirstPage();
		HTMLFlow prev = new HTMLFlow("<b>" + imgTitle + ":</b>");
		prev.setExtraSpace(5);
		HTMLFlow img = new HTMLFlow("<img style='border: 3px solid;max-height: 700px;max-width: 700px;' src='./images/full/" + (isPage ? "" : "uuid:")
				+ firstPageURL + "' />");
		layout.setMembers(info, pid, tit, typ, prev, img);

		setPane(layout);
	}

}
