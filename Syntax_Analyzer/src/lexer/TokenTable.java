package lexer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TokenTable {
	public List<Token> tokens;
	private int size;

	public TokenTable() {
		this.tokens = new ArrayList<Token>();
		updateSize();
	}

	public void printToken() {
		for (int i = 0; i < size; i++) {
			Token token = tokens.get(i);
			System.out.print(token.getLexeme() + " <" + token.getTag() + ",");
			if (token.getPointer() == -1) {
				System.out.print(token.getLexeme());
			} else {
				System.out.print(token.getPointer());
			}
			System.out.println(">");
		}
	}

	public void printLexeme() {
		for (int i = 0; i < size; i++) {
			Token token = tokens.get(i);
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(token.getLexeme());
		}
		System.out.println("");
	}

	public void print() {
		System.out.println("Lexemes:\n");
		printLexeme();
		System.out.println("\nTokens:\n");
		printToken();
	}

	public int getSize() {
		return size;
	}

	private void updateSize() {
		size = tokens.size();
	}

	public void addToken(Token token) {
		tokens.add(token);
		updateSize();
	}

	public void printToken2File(File file) {
		String str;
		try {
			OutputStream outStream = new FileOutputStream(file);
			for (int i = 0; i < size; i++) {
				Token token = tokens.get(i);
				str = token.getLexeme() + " <" + token.getTag() + ",";
				outStream.write(str.getBytes());
				if (token.getPointer() == -1) {
					outStream.write(token.getLexeme().getBytes());
				} else {
					outStream.write(token.getPointer().toString().getBytes());
				}
				str = ">";
				if (i != size - 1) {
					str += (char)10;
				}
				outStream.write(str.getBytes());
			}
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
