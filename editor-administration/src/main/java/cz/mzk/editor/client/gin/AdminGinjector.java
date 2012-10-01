/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.editor.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

import cz.mzk.editor.client.presenter.AdminPresenter;
import cz.mzk.editor.client.presenter.AdminHomePresenter;

// TODO: Auto-generated Javadoc
/**
 * The Interface EditorGinjector.
 */
@GinModules({DispatchAsyncModule.class, AdminClientModule.class})
public interface AdminGinjector
        extends Ginjector {

    /**
     * Gets the place manager.
     * 
     * @return the place manager
     */
    PlaceManager getPlaceManager();

    /**
     * Gets the event bus.
     * 
     * @return the event bus
     */
    EventBus getEventBus();

    /*
     * PRESENTERS Provider<> if you're using @ProxyStandard or AsyncProvider<>
     * if you're using @ProxyCodeSplit
     */

    Provider<AdminPresenter> getAdminPresenter();

    AsyncProvider<AdminHomePresenter> getAdminHomePresenter();

    //    AsyncProvider<UserPresenter> getUserPresenter();

    //    AsyncProvider<ModifyPresenter> getModifyPresenter();

    //    AsyncProvider<FindMetadataPresenter> getFindMetadataPresenterPresenter();

    //    AsyncProvider<CreateStructurePresenter> getCreatePresenterPresenter();

    //    Provider<DigitalObjectMenuPresenter> getDigitalObjectMenuPresenterPresenter();

    //    Provider<CreateObjectMenuPresenter> getCreateObjectMenuPresenterPresenter();

    DispatchAsync getDispatcher();

}