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
package cz.fi.muni.xkremser.editor.server.handler;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import cz.fi.muni.xkremser.editor.server.config.EditorConfiguration;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigAction;
import cz.fi.muni.xkremser.editor.shared.rpc.action.GetClientConfigResult;

// TODO: Auto-generated Javadoc
/**
 * The Class GetClientConfigHandler.
 */
public class GetClientConfigHandler implements ActionHandler<GetClientConfigAction, GetClientConfigResult> {

	/** The logger. */
	private static final Logger LOGGER = Logger.getLogger(GetClientConfigHandler.class.getPackage().toString());

	/** The configuration. */
	private final EditorConfiguration configuration;

	/**
	 * Instantiates a new gets the client config handler.
	 * 
	 * @param configuration
	 *          the configuration
	 */
	@Inject
	public GetClientConfigHandler(final EditorConfiguration configuration) {
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
	 * .gwtplatform.dispatch.shared.Action,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public GetClientConfigResult execute(final GetClientConfigAction action, final ExecutionContext context) throws ActionException {
		LOGGER.debug("Processing action: GetClientConfigAction");
		HashMap<String, Object> result = new HashMap<String, Object>();
		Iterator<String> it = configuration.getClientConfiguration().getKeys();
		while (it.hasNext()) {
			String key = it.next();
			result.put(key, configuration.getConfiguration().getProperty(EditorConfiguration.Constants.GUI_CONFIGURATION_PPREFIX + '.' + key));
		}
		result.put(EditorConfiguration.Constants.FEDORA_HOST, configuration.getFedoraHost());
		result.put(EditorConfiguration.Constants.KRAMERIUS_HOST, configuration.getKrameriusHost());
		return new GetClientConfigResult(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType()
	 */
	@Override
	public Class<GetClientConfigAction> getActionType() {
		return GetClientConfigAction.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
	 * gwtplatform.dispatch.shared.Action, com.gwtplatform.dispatch.shared.Result,
	 * com.gwtplatform.dispatch.server.ExecutionContext)
	 */
	@Override
	public void undo(GetClientConfigAction action, GetClientConfigResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}
}