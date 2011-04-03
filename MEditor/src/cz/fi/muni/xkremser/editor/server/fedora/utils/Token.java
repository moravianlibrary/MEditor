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
package cz.fi.muni.xkremser.editor.server.fedora.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class Token.
 */
public class Token {

	/** The type. */
	private final TokenType type;

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new token.
	 * 
	 * @param type
	 *          the type
	 * @param value
	 *          the value
	 */
	public Token(TokenType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * The Enum TokenType.
	 */
	public enum TokenType {

		/** The ALPHA. */
		ALPHA,
		/** The DIGIT. */
		DIGIT,
		/** The HEXDIGIT. */
		HEXDIGIT,
		/** The PERCENT. */
		PERCENT,
		/** The DOT. */
		DOT,
		/** The DOUBLEDOT. */
		DOUBLEDOT,
		/** The MINUS. */
		MINUS,
		/** The TILDA. */
		TILDA,
		/** The EOI. */
		EOI,
		/** The UNDERSCOPE. */
		UNDERSCOPE,
	}
}
