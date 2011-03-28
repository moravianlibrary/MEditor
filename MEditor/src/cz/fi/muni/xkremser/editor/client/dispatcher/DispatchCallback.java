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
package cz.fi.muni.xkremser.editor.client.dispatcher;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

import cz.fi.muni.xkremser.editor.client.MEditor;
import cz.fi.muni.xkremser.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class DispatchCallback.
 * 
 * @param <T>
 *          the generic type
 */
public abstract class DispatchCallback<T> implements AsyncCallback<T> {

	/**
	 * Instantiates a new dispatch callback.
	 */
	public DispatchCallback() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {
		// log
		callbackError(caught);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(T result) {
		callback(result);
	}

	/**
	 * Must be overriden by clients to handle callbacks.
	 * 
	 * @param result
	 *          the result
	 */
	public abstract void callback(T result);

	/**
	 * Should be overriden by clients who want to handle error cases themselves.
	 * 
	 * @param t
	 *          the t
	 */
	public void callbackError(final Throwable t) {
		String msg = null;
		final boolean redirect = (t.getMessage() != null && t.getMessage().length() > 0 && t.getMessage().charAt(0) == Constants.SESSION_EXPIRED_FLAG);
		if (redirect) {
			msg = "Session has expired. Do you want to be redirected to login page?";
		}
		SC.confirm((redirect ? "" : "RPC failed:" + t.getMessage()) + (msg == null ? "" : msg), new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if (value != null && value) {
					if (redirect) {
						MEditor.redirect(t.getMessage().substring(1));
					}
				} else {

				}
			}
		});

	}
}
