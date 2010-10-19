package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor implements EntryPoint {
	private final EditorGinjector injector = GWT.create(EditorGinjector.class);

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

		DelayedBindRegistry.bind(injector);
		injector.getPlaceManager().revealCurrentPlace();

	}
}
