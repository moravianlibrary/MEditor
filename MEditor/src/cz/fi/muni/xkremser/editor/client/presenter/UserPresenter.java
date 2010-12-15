/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

import cz.fi.muni.xkremser.editor.client.ClientUtils;
import cz.fi.muni.xkremser.editor.client.Constants;
import cz.fi.muni.xkremser.editor.client.NameTokens;
import cz.fi.muni.xkremser.editor.client.config.EditorClientConfiguration;
import cz.fi.muni.xkremser.editor.client.dispatcher.DispatchCallback;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.UsersGwtRPCDS;
import cz.fi.muni.xkremser.editor.shared.rpc.OpenIDItem;
import cz.fi.muni.xkremser.editor.shared.rpc.RoleItem;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailability;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.CheckAvailabilityResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetAllRolesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetAllRolesResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserIdentityResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.PutUserRoleResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserIdentityResult;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.RemoveUserRoleResult;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePresenter.
 */
public class UserPresenter extends Presenter<UserPresenter.MyView, UserPresenter.MyProxy> {

	/**
	 * The Interface MyView.
	 */
	public interface MyView extends View {

		/**
		 * Gets the name.
		 * 
		 * @return the name
		 */
		public void refreshFedora(boolean fedoraRunning, String url);

		public void refreshKramerius(boolean krameriusRunning, String url);

		public void setURLs(String fedoraUrl, String krameriusUrl);

		public void setLoading();

		public ListGrid getUserGrid();

		public ListGrid getUserRoleGrid();

		public ListGrid getUserIdentityGrid();

		public IButton getRemoveUser();

		public IButton getAddUser();

		public IButton getRemoveRole();

		public IButton getAddRole();

		public IButton getRemoveIdentity();

		public IButton getAddIdentity();

		public HasChangedHandlers getUuidItem();

		public IButton getOpen();

		public DynamicForm getForm();
	}

	/**
	 * The Interface MyProxy.
	 */
	@ProxyCodeSplit
	@NameToken(NameTokens.USERS)
	public interface MyProxy extends ProxyPlace<UserPresenter> {

	}

	/** The dispatcher. */
	private final DispatchAsync dispatcher;

	/** The left presenter. */
	private final DigitalObjectMenuPresenter leftPresenter;

	private final PlaceManager placeManager;

	private List<String> roles;

	/**
	 * Instantiates a new home presenter.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param view
	 *          the view
	 * @param proxy
	 *          the proxy
	 * @param leftPresenter
	 *          the left presenter
	 */
	@Inject
	public UserPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final DigitalObjectMenuPresenter leftPresenter,
			final DispatchAsync dispatcher, final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.leftPresenter = leftPresenter;
		this.dispatcher = dispatcher;
		this.placeManager = placeManager;
		dispatcher.execute(new GetAllRolesAction(), new DispatchCallback<GetAllRolesResult>() {
			@Override
			public void callback(GetAllRolesResult result) {
				UserPresenter.this.roles = result.getRoles();
			}
		});
		bind();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
	 */
	@Override
	protected void onBind() {
		super.onBind();
		final UsersGwtRPCDS source = new UsersGwtRPCDS(dispatcher);
		getView().getUserGrid().setDataSource(source);

		// fetch roles and identities
		getView().getUserGrid().addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				dispatcher.execute(new GetUserRolesAndIdentitiesAction(event.getRecord().getAttribute(Constants.ATTR_USER_ID)),
						new DispatchCallback<GetUserRolesAndIdentitiesResult>() {
							@Override
							public void callback(GetUserRolesAndIdentitiesResult result) {
								if (result != null) {
									ArrayList<RoleItem> roles = result.getRoles();
									if (roles != null && roles.size() > 0) {
										ListGridRecord[] roleRecords = new ListGridRecord[roles.size()];
										for (int i = 0, lastIndex = roles.size(); i < lastIndex; i++) {
											ListGridRecord rec = new ListGridRecord();
											copyValues(roles.get(i), rec);
											roleRecords[i] = rec;
										}
										getView().getUserRoleGrid().setData(roleRecords);
									} else {
										getView().getUserRoleGrid().setData(new ListGridRecord[] {});
									}

									ArrayList<OpenIDItem> identities = result.getIdentities();
									if (identities != null && identities.size() > 0) {
										ListGridRecord[] identityRecords = new ListGridRecord[identities.size()];
										for (int i = 0, lastIndex = identities.size(); i < lastIndex; i++) {
											ListGridRecord rec = new ListGridRecord();
											copyValues(identities.get(i), rec);
											identityRecords[i] = rec;
										}
										getView().getUserIdentityGrid().setData(identityRecords);
									} else {
										getView().getUserIdentityGrid().setData(new ListGridRecord[] {});
									}
								} else {
									getView().getUserRoleGrid().setData(new ListGridRecord[] {});
									getView().getUserIdentityGrid().setData(new ListGridRecord[] {});
								}
							}
						});
			}
		});
		getView().getUserRoleGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] selection = event.getSelection();
				if (selection != null && selection.length > 0) {
					getView().getRemoveRole().setDisabled(false);
				} else {
					getView().getRemoveRole().setDisabled(true);
				}
			}
		});
		getView().getUserGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] selection = event.getSelection();
				if (selection != null && selection.length > 0) {
					getView().getRemoveUser().setDisabled(false);
					getView().getAddRole().setDisabled(false);
					getView().getAddIdentity().setDisabled(false);
				} else {
					getView().getRemoveUser().setDisabled(true);
					getView().getAddRole().setDisabled(true);
					getView().getAddIdentity().setDisabled(true);
				}
			}
		});
		getView().getUserIdentityGrid().addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord[] selection = event.getSelection();
				if (selection != null && selection.length > 0) {
					getView().getRemoveIdentity().setDisabled(false);
				} else {
					getView().getRemoveIdentity().setDisabled(true);
				}
			}
		});

		// remove user
		getView().getRemoveUser().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getView().getUserGrid().removeSelectedData();
				getView().getUserRoleGrid().setData(new ListGridRecord[] {});
				getView().getUserIdentityGrid().setData(new ListGridRecord[] {});
			}
		});

		// add user
		getView().getAddUser().addClickHandler(new ClickHandler() {
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
				form.setDataSource(source);
				HiddenItem id = new HiddenItem(Constants.ATTR_USER_ID);
				TextItem name = new TextItem(Constants.ATTR_NAME, "First Name");
				name.setWidth(320);
				TextItem surname = new TextItem(Constants.ATTR_SURNAME, "Last Name");
				surname.setWidth(320);
				CheckboxItem sex = new CheckboxItem(Constants.ATTR_SEX, "Male");
				ButtonItem add = new ButtonItem();
				add.setTitle("Add User");
				add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						form.saveData(new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								winModal.destroy();
							}
						});
					}
				});
				ButtonItem cancel = new ButtonItem();
				cancel.setColSpan(0);
				cancel.setTitle("Cancel");
				cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						winModal.destroy();
					}
				});
				form.setFields(name, surname, sex, add, cancel, id);
				winModal.addItem(form);

				winModal.centerInPage();
				winModal.show();
			}
		});

		// remove identity
		getView().getRemoveIdentity().addClickHandler(new ClickHandler() {
			private volatile int deletedCounter;

			private void deleteFromGUI(int total, ListGridRecord[] selection) {
				if (deletedCounter == total) {
					ListGridRecord[] oldData = getView().getUserIdentityGrid().getRecords();
					ListGridRecord[] newData = ClientUtils.subtract(oldData, selection);
					getView().getUserIdentityGrid().setData(newData);
				}
			}

			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord[] selection = getView().getUserIdentityGrid().getSelection();
				if (selection != null && selection.length > 0) {
					final int total = selection.length;
					for (final ListGridRecord record : selection) {
						dispatcher.execute(new RemoveUserIdentityAction(record.getAttribute(Constants.ATTR_GENERIC_ID)), new DispatchCallback<RemoveUserIdentityResult>() {
							@Override
							public void callback(RemoveUserIdentityResult result) {
								deletedCounter++;
								deleteFromGUI(total, selection);
							}
						});
					}
				}
			}
		});

		// add identity
		getView().getAddIdentity().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Window winModal = new Window();
				winModal.setHeight(200);
				winModal.setWidth(550);
				winModal.setCanDragResize(true);
				winModal.setShowEdges(true);
				winModal.setTitle("New Identity");
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
				// form.setNumCols(8);
				form.setMargin(15);
				form.setWidth(500);
				form.setHeight(150);
				final TextItem identity = new TextItem(Constants.ATTR_IDENTITY, "OpenID identity");
				identity.setWidth(320);
				ButtonItem add = new ButtonItem();
				add.setEndRow(false);
				add.setTitle("Add Identity");
				add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						final String identityValue = (String) identity.getValue();
						if (identityValue != null && !"".equals(identityValue.trim())) {
							String userId = getView().getUserGrid().getSelectedRecord().getAttribute(Constants.ATTR_USER_ID);
							dispatcher.execute(new PutUserIdentityAction(new OpenIDItem(identityValue, "PleaseGenerateIDForMe"), userId),
									new DispatchCallback<PutUserIdentityResult>() {
										@Override
										public void callback(PutUserIdentityResult result) {
											if (!result.isFound() && !"error".equals(result.getId())) {
												ListGridRecord record = new ListGridRecord();
												record.setAttribute(Constants.ATTR_GENERIC_ID, result.getId());
												record.setAttribute(Constants.ATTR_IDENTITY, identityValue);
												ListGridRecord[] previousData = getView().getUserIdentityGrid().getRecords();
												ListGridRecord[] newData = new ListGridRecord[previousData.length + 1];
												System.arraycopy(previousData, 0, newData, 0, previousData.length);
												newData[previousData.length] = record;
												getView().getUserIdentityGrid().setData(newData);
											}
											winModal.destroy();
										}
									});
						}
					}
				});
				ButtonItem cancel = new ButtonItem();
				cancel.setEndRow(false);
				// cancel.setColSpan(1);
				cancel.setTitle("Cancel");
				cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						winModal.destroy();
					}
				});
				form.setFields(identity, add, cancel);
				winModal.addItem(form);
				winModal.centerInPage();
				winModal.show();
			}
		});

		// remove role
		getView().getRemoveRole().addClickHandler(new ClickHandler() {
			private volatile int deletedCounter;

			private void deleteFromGUI(int total, ListGridRecord[] selection) {
				if (deletedCounter == total) {
					ListGridRecord[] oldData = getView().getUserRoleGrid().getRecords();
					ListGridRecord[] newData = ClientUtils.subtract(oldData, selection);
					getView().getUserRoleGrid().setData(newData);
				}
			}

			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord[] selection = getView().getUserRoleGrid().getSelection();
				if (selection != null && selection.length > 0) {
					final int total = selection.length;
					for (final ListGridRecord record : selection) {
						dispatcher.execute(new RemoveUserRoleAction(record.getAttribute(Constants.ATTR_GENERIC_ID)), new DispatchCallback<RemoveUserRoleResult>() {
							@Override
							public void callback(RemoveUserRoleResult result) {
								deletedCounter++;
								deleteFromGUI(total, selection);
							}
						});
					}
				}
			}
		});

		// add role
		getView().getAddRole().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Window winModal = new Window();
				winModal.setHeight(200);
				winModal.setWidth(550);
				winModal.setCanDragResize(true);
				winModal.setShowEdges(true);
				winModal.setTitle("New Identity");
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
				// form.setNumCols(8);
				form.setMargin(15);
				form.setWidth(500);
				form.setHeight(150);
				final SelectItem role = new SelectItem(Constants.ATTR_NAME, "Role");
				role.setWidth(320);
				role.setValueMap(roles.toArray(new String[] {}));
				ButtonItem add = new ButtonItem();
				add.setEndRow(false);
				add.setTitle("Add Role");
				add.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						final String roleValue = (String) role.getValue();
						if (roleValue != null && !"".equals(roleValue.trim())) {
							String userId = getView().getUserGrid().getSelectedRecord().getAttribute(Constants.ATTR_USER_ID);
							dispatcher.execute(new PutUserRoleAction(new RoleItem(roleValue, "", "PleaseGenerateIDForMe"), userId),
									new DispatchCallback<PutUserRoleResult>() {
										@Override
										public void callback(PutUserRoleResult result) {
											if (!result.isFound() && !"error".equals(result.getId())) {
												ListGridRecord record = new ListGridRecord();
												record.setAttribute(Constants.ATTR_GENERIC_ID, result.getId());
												record.setAttribute(Constants.ATTR_NAME, roleValue);
												record.setAttribute(Constants.ATTR_DESC, result.getDescription());
												ListGridRecord[] previousData = getView().getUserRoleGrid().getRecords();
												ListGridRecord[] newData = new ListGridRecord[previousData.length + 1];
												System.arraycopy(previousData, 0, newData, 0, previousData.length);
												newData[previousData.length] = record;
												getView().getUserRoleGrid().setData(newData);
											}
											winModal.destroy();
										}
									});
						}
					}
				});
				ButtonItem cancel = new ButtonItem();
				cancel.setEndRow(false);
				cancel.setTitle("Cancel");
				cancel.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
					@Override
					public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						winModal.destroy();
					}
				});
				form.setFields(role, add, cancel);
				winModal.addItem(form);
				winModal.centerInPage();
				winModal.show();
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
	 */
	@Override
	protected void onReset() {
		super.onReset();
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetLeftContent, leftPresenter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
	 */
	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, AppPresenter.TYPE_SetMainContent, this);
	}

	private void checkAvailability() {
		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.KRAMERIUS_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				String krameriusURL = result.getUrl();
				getView().refreshKramerius(result.isAvailability(), krameriusURL);
				if (krameriusURL == null || "".equals(krameriusURL)) {
					SC.warn("Please set " + EditorClientConfiguration.Constants.KRAMERIUS_HOST + " in system configuration.");
				}
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshKramerius(false, null);
			}
		});

		dispatcher.execute(new CheckAvailabilityAction(CheckAvailability.FEDORA_ID), new DispatchCallback<CheckAvailabilityResult>() {
			@Override
			public void callback(CheckAvailabilityResult result) {
				String fedoraURL = result.getUrl();
				getView().refreshFedora(result.isAvailability(), fedoraURL);
				if (fedoraURL == null || "".equals(fedoraURL)) {
					SC.warn("Please set " + EditorClientConfiguration.Constants.FEDORA_HOST + " in system configuration.");
				}
			}

			@Override
			public void callbackError(Throwable t) {
				SC.warn(t.getMessage());
				getView().refreshFedora(false, null);
			}
		});
	}

	private static void copyValues(RoleItem from, ListGridRecord to) {
		to.setAttribute(Constants.ATTR_NAME, from.getName());
		to.setAttribute(Constants.ATTR_DESC, from.getDescription());
		to.setAttribute(Constants.ATTR_GENERIC_ID, from.getId());
	}

	private static void copyValues(OpenIDItem from, ListGridRecord to) {
		to.setAttribute(Constants.ATTR_IDENTITY, from.getIdentity());
		to.setAttribute(Constants.ATTR_GENERIC_ID, from.getId());
	}
}