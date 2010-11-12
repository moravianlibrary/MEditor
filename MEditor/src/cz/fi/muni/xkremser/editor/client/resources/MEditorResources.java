package cz.fi.muni.xkremser.editor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MEditorResources extends ClientBundle {
	public static final MEditorResources INSTANCE = GWT.create(MEditorResources.class);

	@Source("logo.png")
	ImageResource logo();

	// @Source("logo_blue.gif")
	// ImageResource logoBlue();

}
