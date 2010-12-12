/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;

// TODO: Auto-generated Javadoc
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor implements EntryPoint {

	/** The injector. */
	private final EditorGinjector injector = GWT.create(EditorGinjector.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {

		// IButton adminButton = new IButton("Admin Console");
		// adminButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// com.smartgwtee.tools.client.SCEE.openDataSourceConsole();
		// }
		// });
		// adminButton.draw();
		// FileLoader.cacheImgs(skinImgDir, baseImageURLs)

		DelayedBindRegistry.bind(injector);

		// UserAuthenticationAsync auth = (UserAuthenticationAsync)
		// GWT.create(UserAuthentication.class);
		// ServiceDefTarget endpoint = (ServiceDefTarget) auth;
		// endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "auth");
		//
		// auth.login("", new AsyncCallback<LoginResult>() {
		//
		// @Override
		// public void onSuccess(LoginResult result) {
		// redirect(result.getUrl());
		// }
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		injector.getPlaceManager().revealCurrentPlace();
		RootPanel.getBodyElement().removeChild(RootPanel.get("loadingWrapper").getElement());
	}

	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;
}
