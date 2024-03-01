package lexer;

public class Token {
	private String lexeme;
	private String tag;
	private int pointer;

	public Token(String lexeme, String tag, int pointer) {
		this.lexeme = lexeme;
		this.tag = tag;
		this.pointer = pointer;
	}

	public String getLexeme() {
		return lexeme;
	}

	public String getTag() {
		return tag;
	}

	public Integer getPointer() {
		return pointer;
	}

}
