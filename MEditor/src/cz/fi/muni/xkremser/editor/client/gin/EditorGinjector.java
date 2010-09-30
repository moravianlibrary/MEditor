package cz.fi.muni.xkremser.editor.client.gin;

import net.customware.gwt.dispatch.client.gin.ClientDispatchModule;
import net.customware.gwt.presenter.client.place.PlaceManager;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import cz.fi.muni.xkremser.editor.client.mvp.AppPresenter;

@GinModules({ ClientDispatchModule.class, EditorClientModule.class })
public interface EditorGinjector extends Ginjector {

	AppPresenter getAppPresenter();

	PlaceManager getPlaceManager();

}