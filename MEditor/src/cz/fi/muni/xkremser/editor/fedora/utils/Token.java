/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */
package cz.fi.muni.xkremser.editor.fedora.utils;

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
	 * @param type the type
	 * @param value the value
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
