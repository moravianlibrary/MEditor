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
package cz.fi.muni.xkremser.editor.shared.rpc.result;

import com.gwtplatform.dispatch.shared.Result;

// TODO: Auto-generated Javadoc
/**
 * The Class SendGreetingResult.
 */
public class SendGreetingResult implements Result {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7917449246674223581L;

	/** The name. */
	private String name;
	
	/** The message. */
	private String message;

	/**
	 * Instantiates a new send greeting result.
	 *
	 * @param name the name
	 * @param message the message
	 */
	public SendGreetingResult(final String name, final String message) {
		this.name = name;
		this.message = message;
	}

	/**
	 * Instantiates a new send greeting result.
	 */
	@SuppressWarnings("unused")
	private SendGreetingResult() {
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}