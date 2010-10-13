package cz.fi.muni.xkremser.editor.fedora.utils;

public class Token {

	private final TokenType type;
	private final String value;

	public Token(TokenType type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public TokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public enum TokenType {
		ALPHA, DIGIT, HEXDIGIT, PERCENT, DOT, DOUBLEDOT, MINUS, TILDA, EOI, UNDERSCOPE,
	}
}
