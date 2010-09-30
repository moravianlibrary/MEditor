package cz.fi.muni.xkremser.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import cz.fi.muni.xkremser.editor.client.gin.EditorGinjector;
import cz.fi.muni.xkremser.editor.client.mvp.AppPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MEditor implements EntryPoint {
	private final EditorGinjector injector = GWT.create(EditorGinjector.class);

	@Override
	public void onModuleLoad() {
		final AppPresenter appPresenter = injector.getAppPresenter();

		// Button button = new Button("Click me!");
		// IButton iButton = new IButton("Click me!");
		// DynamicForm form = new DynamicForm();
		// final TextItem textItem = new TextItem();
		// button.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// SC.say("hello " + textItem.getValue());
		// }
		// });

		// VLayout main = new VLayout();
		// main.setWidth100();
		// main.setHeight100();
		// VLayout sideNavLayout = new VLayout();
		// sideNavLayout.setHeight100();
		// sideNavLayout.setWidth(285);
		// sideNavLayout.setShowResizeBar(true);
		// SideNavInputTree sideNavTree1 = new SideNavInputTree();
		// sideNavTree1.setHeight("600");
		// SideNavRecentlyTree sideNavTree2 = new SideNavRecentlyTree();
		// sideNavLayout.addMember(sideNavTree1);
		// sideNavLayout.addMember(sideNavTree2);
		// HLayout hLayout = new HLayout();
		// hLayout.setLayoutMargin(5);
		// hLayout.setWidth100();
		// hLayout.setHeight100();
		// hLayout.addMember(sideNavLayout);
		// hLayout.addMember(sideNavLayout);

		// sideNav.addLeafClickHandler(new LeafClickHandler() {
		// @Override
		// public void onLeafClick(LeafClickEvent event) {
		// TreeNode node = event.getLeaf();
		// // showSample(node);
		// }
		// });
		// main.addMember(hLayout);
		// main.draw();

		// form.setFields(textItem);
		// RootPanel.get().add(main);
		// RootPanel.get().add(button);

		appPresenter.go(RootPanel.get("treeContainer"), RootPanel.get("mainContainer"));

		injector.getPlaceManager().fireCurrentPlace();
	}
}
