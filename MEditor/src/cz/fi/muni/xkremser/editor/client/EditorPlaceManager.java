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
package cz.fi.muni.xkremser.editor.client;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

import cz.fi.muni.xkremser.editor.client.gin.DefaultPlace;

// TODO: Auto-generated Javadoc
/**
 * The Class EditorPlaceManager.
 */
public class EditorPlaceManager extends PlaceManagerImpl {

	/** The default place request. */
	private final PlaceRequest defaultPlaceRequest;

	/**
	 * Instantiates a new editor place manager.
	 * 
	 * @param eventBus
	 *          the event bus
	 * @param tokenFormatter
	 *          the token formatter
	 * @param defaultNameToken
	 *          the default name token
	 */
	@Inject
	public EditorPlaceManager(final EventBus eventBus, final TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
		super(eventBus, tokenFormatter);

		this.defaultPlaceRequest = new PlaceRequest(NameTokens.HOME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gwtplatform.mvp.client.proxy.PlaceManager#revealDefaultPlace()
	 */
	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest);
	}

	// @Override
	// public void revealErrorPlace(String invalidHistoryToken) {
	// super.revealErrorPlace(invalidHistoryToken);
	// }
}