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

import cz.fi.muni.xkremser.editor.server.fedora.utils.Token.TokenType;

// TODO: Auto-generated Javadoc
/**
 * The Class Lexer.
 * 
 * @author pavels Window - Preferences - Java - Code Style - Code Templates
 */
public class Lexer {

    /** The Constant LOOK_AHEAD_DEPTH. */
    public static final int LOOK_AHEAD_DEPTH = 5;

    // char buffer with lookahead support
    /** The buffer. */
    private CharBuffer buffer = null;

    /**
     * Instantiates a new lexer.
     * 
     * @param inputString
     *        the input string
     * @throws LexerException
     *         the lexer exception
     */
    public Lexer(String inputString)
            throws LexerException {
        this.buffer = new CharBuffer(inputString, LOOK_AHEAD_DEPTH);
    }

    /**
     * Instantiates a new lexer.
     * 
     * @param inputString
     *        the input string
     * @param lookAhead
     *        the look ahead
     * @throws LexerException
     *         the lexer exception
     */
    public Lexer(String inputString, int lookAhead)
            throws LexerException {
        this.buffer = new CharBuffer(inputString, lookAhead);
    }

    /**
     * Returns char from given position.
     * 
     * @param charPosition
     *        the char position
     * @return char
     * @throws LexerException
     *         the lexer exception
     */
    protected char charLookAhead(int charPosition) throws LexerException {
        char ch = (char) this.buffer.la(charPosition);
        return ch;
    }

    /**
     * Returns real stream position of char.
     * 
     * @param charPosition
     *        the char position
     * @return int
     * @throws LexerException
     *         the lexer exception
     */
    protected int charPosition(int charPosition) throws LexerException {
        return this.buffer.position(charPosition);
    }

    /**
     * Consume char and read new char into buffer.
     * 
     * @throws LexerException
     *         the lexer exception
     */
    protected void consumeChar() throws LexerException {
        this.buffer.consume();
    }

    /**
     * Match char.
     * 
     * @param expectingChar
     *        the expecting char
     * @throws LexerException
     *         the lexer exception
     */
    protected void matchChar(char expectingChar) throws LexerException {
        if (charLookAhead(1) == expectingChar) {
            this.consumeChar();
        } else
            throw new LexerException("i am expecting '" + expectingChar + "' but got '" + charLookAhead(1)
                    + "'");
    }

    /**
     * Match alpha.
     * 
     * @return the token
     * @throws LexerException
     *         the lexer exception
     */
    protected Token matchALPHA() throws LexerException {
        int ch = this.buffer.la(1);
        if (((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z'))) {
            this.consumeChar();
            return new Token(TokenType.ALPHA, "" + (char) ch);
        } else
            throw new LexerException("");
    }

    /**
     * Hex digit postfix.
     * 
     * @param ch
     *        the ch
     * @return true, if successful
     */
    public boolean hexDigitPostfix(char ch) {
        switch (ch) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
                return true;
            default:
                return false;
        }
    }

    /**
     * Match hex digit.
     * 
     * @return the token
     * @throws LexerException
     *         the lexer exception
     */
    public Token matchHexDigit() throws LexerException {
        StringBuffer buffer = new StringBuffer();
        char ch = charLookAhead(1);
        if (!Character.isDigit(ch)) throw new LexerException("Expecting Digit !");
        buffer.append(ch);
        this.consumeChar();
        ch = charLookAhead(1);
        if (!hexDigitPostfix(ch)) throw new LexerException("Expecting 'A','B','C','D','E' or 'F' !");
        buffer.append(Character.toUpperCase(ch));
        this.consumeChar();
        return new Token(TokenType.HEXDIGIT, buffer.toString());
    }

    /**
     * Match digit.
     * 
     * @return the token
     * @throws LexerException
     *         the lexer exception
     */
    public Token matchDigit() throws LexerException {
        StringBuffer buffer = new StringBuffer();
        char ch = charLookAhead(1);
        if (!Character.isDigit(ch)) throw new LexerException("Expecting Digit !");
        buffer.append(ch);
        this.consumeChar();
        return new Token(TokenType.DIGIT, buffer.toString());
    }

    /**
     * Match string.
     * 
     * @param str
     *        the str
     * @throws LexerException
     *         the lexer exception
     */
    public void matchString(String str) throws LexerException {
        char[] chrs = str.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            matchChar(chrs[i]);
        }
    }

    /**
     * Read token.
     * 
     * @return the token
     * @throws LexerException
     *         the lexer exception
     */
    public Token readToken() throws LexerException {
        char ch = charLookAhead(1);
        if (ch == 65535) return new Token(TokenType.EOI, "eoi");
        switch (ch) {
            case ':': {
                this.matchChar(':');
                return new Token(TokenType.DOUBLEDOT, ":");
            }
            case '-': {
                this.matchChar('-');
                return new Token(TokenType.MINUS, "-");
            }
            case '~': {
                this.matchChar('~');
                return new Token(TokenType.TILDA, "~");
            }
            case '.': {
                this.matchChar('.');
                return new Token(TokenType.DOT, ".");
            }
            case '%': {
                this.matchChar('%');
                if (Character.isDigit(charLookAhead(2)) && hexDigitPostfix(charLookAhead(3))) {
                    return matchHexDigit();
                } else
                    return new Token(TokenType.PERCENT, "%");
            }
            case '_': {
                this.matchChar('_');
                return new Token(TokenType.UNDERSCOPE, "_");
            }
            default: {
                if (Character.isDigit(ch)) {
                    return matchDigit();
                }
                return matchALPHA();
            }

        }
    }

}