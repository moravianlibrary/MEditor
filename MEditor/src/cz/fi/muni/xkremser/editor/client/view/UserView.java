/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SortArrow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.presenter.UserPresenter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeView.
 */
public class UserView extends ViewImpl implements UserPresenter.MyView {

	private final VLayout layout;

	private IButton checkButton;

	private DynamicForm form;

	private IButton open;

	private TextItem uuidField;

	private final ListGrid userGrid;
	private final ListGrid userRolesGrid;
	private final ListGrid userIdentitiesGrid;
	private final IButton removeUser;
	private final IButton removeRole;
	private final IButton removeIdentity;

	/**
	 * Instantiates a new home view.
	 */
	public UserView() {
		layout = new VLayout();
		layout.setHeight100();
		layout.setWidth(610);
		layout.setPadding(10);
		this.userGrid = new ListGrid();
		userGrid.setWidth(585);
		userGrid.setHeight(500);
		userGrid.setShowSortArrow(SortArrow.CORNER);
		userGrid.setShowAllRecords(true);
		userGrid.setAutoFetchData(true);
		userGrid.setCanHover(true);
		userGrid.setCanSort(false); // TODO: sort by date (define in datasource)
		userGrid.setHoverOpacity(75);
		userGrid.setHoverStyle("interactImageHover");
		userGrid.setCanEdit(true);
		userGrid.setMargin(5);

		HLayout detailLayout = new HLayout();

		VLayout rolesLayout = new VLayout();
		rolesLayout.setPadding(0);
		rolesLayout.setMargin(0);
		this.userRolesGrid = new ListGrid();
		userRolesGrid.setWidth(290);
		userRolesGrid.setHeight(200);
		userRolesGrid.setShowSortArrow(SortArrow.CORNER);
		userRolesGrid.setShowAllRecords(true);
		// userRolesGrid.setAutoFetchData(true);
		userRolesGrid.setCanHover(true);
		userRolesGrid.setCanSort(false);
		userRolesGrid.setHoverOpacity(75);
		userRolesGrid.setHoverStyle("interactImageHover");
		// userRolesGrid.setCanEdit(true);
		userRolesGrid.setMargin(5);

		DataSource source = new DataSource();
		DataSourceField field;
		field = new DataSourceTextField(Constants.ATTR_NAME, "Name");
		field.setRequired(true);
		field.setAttribute("width", "40%");
		source.addField(field);
		field = new DataSourceTextField(Constants.ATTR_DESC, "Description");
		field.setRequired(true);
		field.setAttribute("width", "*");
		source.addField(field);
		field = new DataSourceTextField(Constants.ATTR_GENERIC_ID, "id");
		field.setPrimaryKey(true);
		field.setHidden(true);
		field.setRequired(true);
		source.addField(field);
		userRolesGrid.setDataSource(source);
		HTMLFlow roles = new HTMLFlow("<b>Roles</b>");
		roles.setHeight(15);
		rolesLayout.addMember(roles);
		rolesLayout.addMember(userRolesGrid);
		HLayout buttonLayout2 = new HLayout();
		buttonLayout2.setPadding(5);
		IButton addRole = new IButton("Add role");
		addRole.setExtraSpace(10);
		removeRole = new IButton("Remove selected");
		removeRole.setAutoFit(true);
		removeRole.setDisabled(true);
		buttonLayout2.addMember(addRole);
		buttonLayout2.addMember(removeRole);
		buttonLayout2.setAlign(Alignment.CENTER);
		rolesLayout.addMember(buttonLayout2);
		detailLayout.addMember(rolesLayout);

		VLayout identitiesLayout = new VLayout();
		identitiesLayout.setPadding(0);
		identitiesLayout.setMargin(0);
		this.userIdentitiesGrid = new ListGrid();
		userIdentitiesGrid.setWidth(290);
		userIdentitiesGrid.setHeight(200);
		userIdentitiesGrid.setShowSortArrow(SortArrow.CORNER);
		userIdentitiesGrid.setShowAllRecords(true);
		// userIdentitiesGrid.setAutoFetchData(true);
		userIdentitiesGrid.setCanHover(true);
		userIdentitiesGrid.setCanSort(false);
		userIdentitiesGrid.setHoverOpacity(75);
		userIdentitiesGrid.setHoverStyle("interactImageHover");
		userIdentitiesGrid.setMargin(5);
		// userIdentitiesGrid.setCanEdit(true);
		DataSource source2 = new DataSource();
		field = new DataSourceTextField(Constants.ATTR_IDENTITY, "Identity");
		field.setRequired(true);
		source2.addField(field);
		field = new DataSourceTextField(Constants.ATTR_GENERIC_ID, "id");
		field.setPrimaryKey(true);
		field.setHidden(true);
		field.setRequired(true);
		source2.addField(field);
		userIdentitiesGrid.setDataSource(source2);
		HTMLFlow openIds = new HTMLFlow("<b>OpenID identities</b>");
		openIds.setHeight(15);
		identitiesLayout.addMember(openIds);
		identitiesLayout.addMember(userIdentitiesGrid);
		HLayout buttonLayout = new HLayout();
		buttonLayout.setPadding(5);
		buttonLayout.setAlign(Alignment.CENTER);
		IButton addIdentity = new IButton("Add identity");
		addIdentity.setExtraSpace(10);
		removeIdentity = new IButton("Remove selected");
		removeIdentity.setAutoFit(true);
		removeIdentity.setDisabled(true);
		buttonLayout.addMember(addIdentity);
		buttonLayout.addMember(removeIdentity);
		identitiesLayout.addMember(buttonLayout);
		detailLayout.addMember(identitiesLayout);

		HTMLFlow users = new HTMLFlow("<b>Users</b>");
		users.setHeight(15);
		layout.addMember(users);
		layout.addMember(userGrid);
		HLayout buttonLayout3 = new HLayout();
		buttonLayout3.setPadding(5);
		buttonLayout3.setAlign(Alignment.CENTER);
		IButton addUser = new IButton("Add user");
		addUser.setExtraSpace(10);
		addUser.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Window winModal = new Window();
				winModal.setHeight(200);
				winModal.setWidth(550);
				// winModal.setPadding(15);
				winModal.setCanDragResize(true);
				winModal.setShowEdges(true);
				winModal.setTitle("New user");
				winModal.setShowMinimizeButton(false);
				winModal.setIsModal(true);
				winModal.setShowModalMask(true);
				winModal.addCloseClickHandler(new CloseClickHandler() {
					@Override
					public void onCloseClick(CloseClientEvent event) {
						winModal.destroy();
					}
				});
				final DynamicForm form = new DynamicForm();
				form.setMargin(15);
				form.setWidth(500);
				form.setHeight(150);
				form.setDataSource(getUserGrid().getDataSource());
				TextItem name = new TextItem(Constants.ATTR_NAME, "First Name");
				name.setWidth(320);
				TextItem surname = new TextItem(Constants.ATTR_SURNAME, "Last Name");
				surname.setWidth(320);
				CheckboxItem sex = new CheckboxItem(Constants.ATTR_SEX, "Male");
				ButtonItem add = new ButtonItem();
				add.setTitle("Add user");
				add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						form.saveData();
						Timer timer = new Timer() {
							@Override
							public void run() {
								winModal.destroy();
							}
						};
						timer.schedule(100);
					}
				});
				ButtonItem cancel = new ButtonItem();
				cancel.setTitle("Cancel");
				cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						winModal.destroy();
					}
				});
				form.setFields(name, surname, sex, add, cancel);
				winModal.addItem(form);

				winModal.centerInPage();
				winModal.show();
			}
		});
		removeUser = new IButton("Remove selected");
		removeUser.setAutoFit(true);
		removeUser.setDisabled(true);
		buttonLayout3.addMember(addUser);
		buttonLayout3.addMember(removeUser);
		layout.addMember(buttonLayout3);
		layout.addMember(detailLayout);
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
	public IButton getOpen() {
		return open;
	}

	@Override
	public DynamicForm getForm() {
		return form;
	}

	@Override
	public HasChangedHandlers getUuidItem() {
		return uuidField;
	}

	@Override
	public void refreshFedora(boolean fedoraRunning, String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshKramerius(boolean krameriusRunning, String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setURLs(String fedoraUrl, String krameriusUrl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoading() {
		// TODO Auto-generated method stub

	}

	@Override
	public ListGrid getUserGrid() {
		return userGrid;
	}

	@Override
	public ListGrid getUserRoleGrid() {
		return userRolesGrid;
	}

	@Override
	public ListGrid getUserIdentityGrid() {
		return userIdentitiesGrid;
	}

	@Override
	public IButton getRemoveUser() {
		return removeUser;
	}

	@Override
	public IButton getRemoveRole() {
		return removeRole;
	}

	@Override
	public IButton getRemoveIdentity() {
		return removeIdentity;
	}

}