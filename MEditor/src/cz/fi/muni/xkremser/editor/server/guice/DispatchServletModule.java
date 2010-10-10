package cz.fi.muni.xkremser.editor.server.guice;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {

	@Override
	public void configureServlets() {
		serve("/meditor/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
	}
}
