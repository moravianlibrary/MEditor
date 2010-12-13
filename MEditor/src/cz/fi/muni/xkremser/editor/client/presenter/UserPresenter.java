/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client.presenter;

import java.util.ArrayList;

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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.HasChangedHandlers;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

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
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetUserRolesAndIdentitiesResult;

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

		public IButton getRemoveRole();

		public IButton getRemoveIdentity();

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
		getView().getUserGrid().setDataSource(new UsersGwtRPCDS(dispatcher));

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
				} else {
					getView().getRemoveUser().setDisabled(true);
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

		getView().getRemoveUser().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getUserGrid().removeSelectedData();
			}
		});

		//
		// checkAvailability();
		// getView().getCheckAvailability().addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// getView().setLoading();
		// Timer timer = new Timer() {
		// @Override
		// public void run() {
		// checkAvailability();
		// }
		// };
		// timer.schedule(100);
		// }
		// });
		// getView().getOpen().addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// if (getView().getForm().validate())
		// ;
		// // placeManager.revealRelativePlace(new
		// // PlaceRequest(NameTokens.MODIFY).with(Constants.URL_PARAM_UUID,
		// // getView().getUuid()));
		// }
		// });
		//
		// getView().getUuidItem().addChangedHandler(new ChangedHandler() {
		// @Override
		// public void onChanged(ChangedEvent event) {
		// String text = (String) event.getValue();
		// if (text != null && !"".equals(text)) {
		// getView().getOpen().setDisabled(false);
		// } else {
		// getView().getOpen().setDisabled(true);
		// }
		// }
		// });

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