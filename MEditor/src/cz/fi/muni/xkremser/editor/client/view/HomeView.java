/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import cz.fi.muni.xkremser.editor.client.presenter.HomePresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class HomeView extends ViewImpl implements HomePresenter.MyView {
	private static final int LOADING = -1;
	private static final int NOT_AVAIL = 0;
	private static final int AVAIL = 1;
	private volatile String fedoraUrl = "#";
	private volatile String krameriusUrl = "#";
	private volatile int fedoraStatus = LOADING;
	private volatile int krameriusStatus = LOADING;

	private final VStack layout;

	private final IButton checkButton;

	private final HTMLFlow status;

	private final DynamicForm form;

	private final IButton open;

	private final TextItem uuidField;

	/**
	 * Instantiates a new home view.
	 */
	public HomeView() {
		layout = new VStack();
		layout.setHeight100();
		layout.setPadding(15);
		HTMLFlow html1 = new HTMLFlow();
		html1
				.setContents("<h1>Metadata Editor</h1>"
						+ "Rich Internet application used for modification of metadata stored in <a href='http://fedora-commons.org'>Fedora Commons repository</a>. Editor should be primarily run by link from Kramerius 4. The licence of Metadata Editor is GNU GPL and system itself can be downloaded from <a href='http://code.google.com/p/meta-editor/'> google code</a>."
						+ "<br/><br/><h2>Availability of cooperating systems</h2>");

		status = new HTMLFlow(getStatusString());

		checkButton = new IButton("Check availability");
		checkButton.setAutoFit(true);
		checkButton.setExtraSpace(60);

		HTMLFlow html2 = new HTMLFlow();
		html2.setContents("<h2>Open digital object</h2>");

		DataSource dataSource = new DataSource();
		dataSource.setID("regularExpression");

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator.setExpression("^([\\da-fA-F]){8}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){4}-([\\da-fA-F]){12}$");

		uuidField = new TextItem();
		uuidField.setTitle("uuid");
		uuidField.setHint("<nobr>Without \"uuid:\" prefix</nobr>");
		uuidField.setValidators(regExpValidator);

		form = new DynamicForm();
		form.setWidth(300);
		form.setFields(uuidField);

		open = new IButton();
		open.setTitle("Open");
		open.setDisabled(true);

		HLayout hLayout = new HLayout();
		hLayout.setMembersMargin(10);
		hLayout.addMember(form);
		hLayout.addMember(open);

		HTMLFlow html3 = new HTMLFlow();
		// html3.setAlign(Alignment.RIGHT);
		html3.setHeight("*");
		html3.setLayoutAlign(VerticalAlignment.BOTTOM);
		html3.setContents("Created by Jiri Kremser.");
		html3.setHeight(20);

		layout.addMember(html1);
		layout.addMember(status);
		layout.addMember(checkButton);
		layout.addMember(html2);
		layout.addMember(hLayout);
		layout.addMember(html3);
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 * 
	 * @return the widget
	 */
	@Override
	public Widget asWidget() {
		return layout;
	}

	@Override
	public HasClickHandlers getCheckAvailability() {
		return checkButton;
	}

	@Override
	public void refreshFedora(boolean fedoraRunning, String url) {
		this.fedoraStatus = fedoraRunning ? AVAIL : NOT_AVAIL;
		if (url != null)
			this.fedoraUrl = url;
		status.setContents(getStatusString());
	}

	@Override
	public void refreshKramerius(boolean krameriusRunning, String url) {
		this.krameriusStatus = krameriusRunning ? AVAIL : NOT_AVAIL;
		if (url != null)
			this.krameriusUrl = url;
		status.setContents(getStatusString());
	}

	@Override
	public void setURLs(String fedoraUrl, String krameriusUrl) {
		if (fedoraUrl != null)
			this.fedoraUrl = fedoraUrl;
		if (krameriusUrl != null)
			this.krameriusUrl = krameriusUrl;
	}

	private String getStatusString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul><li>Fedora (<a href='").append(fedoraUrl == null ? "" : fedoraUrl).append("'>link</a>) <span class='");
		switch (fedoraStatus) {
			case (LOADING):
				sb.append("loading'>loading...");
			break;
			case (AVAIL):
				sb.append("greenFont'>is running");
			break;
			case (NOT_AVAIL):
				sb.append("redFont'>is not running");
			break;
		}
		sb.append("</span></li><li>Kramerius 4 (<a href='").append(krameriusUrl == null ? "" : krameriusUrl).append("'>link</a>) <span class='");
		switch (krameriusStatus) {
			case (LOADING):
				sb.append("loading'>loading...");
			break;
			case (AVAIL):
				sb.append("greenFont'>is running");
			break;
			case (NOT_AVAIL):
				sb.append("redFont'>is not running");
			break;
		}
		sb.append("</span></li></ul>");
		return sb.toString();
	}

	@Override
	public void setLoading() {
		this.krameriusStatus = LOADING;
		this.fedoraStatus = LOADING;
		status.setContents(getStatusString());
	}

	@Override
	public IButton getOpen() {
		return open;
	}

	@Override
	public DynamicForm getForm() {
		return form;
	}

	@Override
	public String getUuid() {
		return (String) uuidField.getValue();
	}

	@Override
	public HasChangedHandlers getUuidItem() {
		return uuidField;
	}

}