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

import java.util.Arrays;
import java.util.List;

import cz.fi.muni.xkremser.editor.server.fedora.utils.Token.TokenType;

// TODO: Auto-generated Javadoc
/**
 * Simple parser for PID.
 * 
 * @see EBNF 
 *      http://www.fedora-commons.org/confluence/display/FCR30/Fedora+Identifiers
 * @author pavels
 */
public class PIDParser {

	/** The lexer. */
	private final Lexer lexer;

	/**
	 * Gets the object id.
	 * 
	 * @return the object id
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * Gets the namespace id.
	 * 
	 * @return the namespace id
	 */
	public String getNamespaceId() {
		return namespaceId;
	}

	/** The token. */
	private Token token;

	/** The object id. */
	private String objectId;

	/** The namespace id. */
	private String namespaceId;

	/**
	 * Instantiates a new pID parser.
	 * 
	 * @param sform
	 *          the sform
	 * @throws LexerException
	 *           the lexer exception
	 */
	public PIDParser(String sform) throws LexerException {
		super();
		this.lexer = new Lexer(sform);
	}

	/**
	 * Object pid.
	 * 
	 * @throws LexerException
	 *           the lexer exception
	 */
	public void objectPid() throws LexerException {
		this.consume();
		String namespaceId = namespaceId();
		matchDoubleDot();
		String objectId = objectId();

		this.namespaceId = namespaceId;
		this.objectId = objectId;
	}

	/**
	 * Dissemination uri.
	 * 
	 * @throws LexerException
	 *           the lexer exception
	 */
	public void disseminationURI() throws LexerException {
		this.lexer.matchString("info:fedora/");
		this.objectPid();
	}

	/**
	 * Match double dot.
	 * 
	 * @throws LexerException
	 *           the lexer exception
	 */
	public void matchDoubleDot() throws LexerException {
		if (this.token.getType() == TokenType.DOUBLEDOT) {
			matchToken(TokenType.DOUBLEDOT);
		} else if (this.token.getType() == TokenType.PERCENT) {
			this.consume();
			if (this.token.getType() == TokenType.HEXDIGIT) {
				String value = this.token.getValue();
				if (value.equals("3A")) {
					this.consume();
				} else
					throw new LexerException("Expecting 3A");
			} else
				throw new LexerException("Expecting %");
		} else
			throw new LexerException("Expecting ':' (probably bad format of uuid string).");
	}

	/**
	 * Match token.
	 * 
	 * @param doubledot
	 *          the doubledot
	 * @throws LexerException
	 *           the lexer exception
	 */
	private void matchToken(TokenType doubledot) throws LexerException {
		if (this.token.getType() == doubledot) {
			this.consume();
		} else
			throw new LexerException("Expecting ':'");
	}

	/**
	 * Namespace id.
	 * 
	 * @return the string
	 * @throws LexerException
	 *           the lexer exception
	 */
	private String namespaceId() throws LexerException {
		List<TokenType> types = Arrays.asList(new TokenType[] { TokenType.ALPHA, TokenType.DIGIT, TokenType.MINUS, TokenType.DOT });
		StringBuffer buffer = new StringBuffer();
		if (!types.contains(this.token.getType()))
			throw new LexerException("expecting ALPHA, DIGIT, MINUS or DOT");
		while (types.contains(this.token.getType())) {
			buffer.append(token.getValue());
			this.consume();

		}
		return buffer.toString();
	}

	/**
	 * Consume.
	 * 
	 * @throws LexerException
	 *           the lexer exception
	 */
	public void consume() throws LexerException {
		this.token = this.lexer.readToken();
	}

	/**
	 * Object id.
	 * 
	 * @return the string
	 * @throws LexerException
	 *           the lexer exception
	 */
	private String objectId() throws LexerException {
		List<TokenType> types = Arrays.asList(new TokenType[] { TokenType.ALPHA, TokenType.DIGIT, TokenType.MINUS, TokenType.DOT, TokenType.TILDA,
				TokenType.UNDERSCOPE, TokenType.PERCENT, TokenType.HEXDIGIT });
		StringBuffer buffer = new StringBuffer();
		if (!types.contains(this.token.getType()))
			throw new LexerException("expecting ALPHA, DIGIT, MINUS or DOT");
		while (types.contains(this.token.getType())) {
			buffer.append(token.getValue());
			if (token.getType() == TokenType.PERCENT) {
				this.consume();
				while (token.getType().equals(TokenType.HEXDIGIT)) {
					buffer.append(token.getValue());
					this.consume();
				}
			} else {
				this.consume();
			}
		}
		return buffer.toString();
	}
}
