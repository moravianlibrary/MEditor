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

import java.io.IOException;
import java.io.StringReader;

// TODO: Auto-generated Javadoc
/**
 * The Class CharBuffer.
 *
 * @author pavels
 */
public class CharBuffer {

	/** The buffer. */
	private int[] buffer = null;
	
	/** The positions. */
	private int[] positions = null;
	
	/** The depth. */
	private int depth = 0;
	
	/** The input. */
	private StringReader input = null;
	
	/** The counter. */
	private int counter = 0;

	/**
	 * Instantiates a new char buffer.
	 *
	 * @param inputString the input string
	 * @param depth the depth
	 * @throws LexerException the lexer exception
	 */
	public CharBuffer(String inputString, int depth) throws LexerException {
		try {
			this.input = new StringReader(inputString);
			this.depth = depth;
			this.buffer = new int[this.depth];
			this.positions = new int[this.depth];

			for (int i = 0; i < this.buffer.length; i++) {
				this.buffer[i] = this.input.read();
				this.positions[i] = counter++;
			}
		} catch (IOException e) {
			throw new LexerException(e.getMessage());
		}
	}

	/**
	 * La.
	 *
	 * @param pos the pos
	 * @return the int
	 * @throws LexerException the lexer exception
	 */
	public int la(int pos) throws LexerException {
		if ((pos >= 1) && (pos <= this.depth)) {
			return this.buffer[pos - 1];
		} else
			throw new LexerException("cannot look ahead to '" + pos + "' position");
	}

	/**
	 * Position.
	 *
	 * @param pos the pos
	 * @return the int
	 * @throws LexerException the lexer exception
	 */
	public int position(int pos) throws LexerException {
		if ((pos >= 1) && (pos <= this.depth)) {
			return this.positions[pos - 1];
		} else
			throw new LexerException("cannot look ahead to '" + pos + "' position");
	}

	/**
	 * Consume.
	 *
	 * @throws LexerException the lexer exception
	 */
	public void consume() throws LexerException {
		try {
			for (int i = 0; i < this.depth - 1; i++) {
				this.buffer[i] = this.buffer[i + 1];
				this.positions[i] = this.positions[i + 1];
			}

			this.buffer[this.depth - 1] = this.input.read();
			this.positions[this.depth - 1] = this.counter++;
		} catch (IOException e) {
			throw new LexerException(e.getMessage());
		}
	}

}