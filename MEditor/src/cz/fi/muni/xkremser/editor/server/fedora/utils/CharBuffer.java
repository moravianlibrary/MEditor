/**
 * Metadata Editor
 * @author Jiri Kremser
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