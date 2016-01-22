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

package cz.mzk.editor.client.config;

import javax.inject.Inject;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import cz.mzk.editor.client.dispatcher.DispatchCallback;
import cz.mzk.editor.shared.event.ConfigReceivedEvent;
import cz.mzk.editor.shared.rpc.action.GetClientConfigAction;
import cz.mzk.editor.shared.rpc.action.GetClientConfigResult;


// TODO: Auto-generated Javadoc
/**
 * The Class EditorClientConfigurationImpl.
 */
@SuppressWarnings("deprecation")
@Singleton
public class EditorClientConfigurationImpl
        extends EditorClientConfiguration
        implements HasHandlers {

    /** The configuration. */
    private MyConfiguration configuration;

    /** The event bus. */
    @Inject
    private EventBus eventBus;

    /**
     * Instantiates a new editor client configuration impl.
     * 
     * @param dispatcher
     *        the dispatcher
     */
    @Inject
    public EditorClientConfigurationImpl(final DispatchAsync dispatcher) {
        if (configuration == null) {
            dispatcher.execute(new GetClientConfigAction(), new DispatchCallback<GetClientConfigResult>() {

                @Override
                public void callbackError(final Throwable cause) {
                    ConfigReceivedEvent.fire(EditorClientConfigurationImpl.this, false);
                    Log.error("Client configuration was not returned from server. Cause: ", cause);
                }

                @Override
                public void callback(GetClientConfigResult result) {
                    EditorClientConfigurationImpl.this.configuration =
                            new MyConfiguration(result.getConfig());
                    ConfigReceivedEvent.fire(EditorClientConfigurationImpl.this, true);
                    Log.debug("Client configuration successfully returned from server.");
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.config.EditorClientConfiguration#
     * getConfiguration()
     */
    @Override
    public MyConfiguration getConfiguration() {
        return configuration;
    }

    /*
     * (non-Javadoc)
     * @see cz.mzk.editor.client.config.EditorClientConfiguration#
     * setConfiguration (cz.mzk.editor.client.config.MyConfiguration)
     */
    @Override
    public void setConfiguration(MyConfiguration configuration) {
        this.configuration = configuration;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.
     * shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

}
