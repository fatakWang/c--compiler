package lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dfa.DFA;

public class Lexer {
	private String source;
	private TokenTable tokenTable;
	private DFA dfa;
	private List<String> kws;

	public Lexer(File f, TokenTable tokenTable) {
		try {
			InputStream in = new FileInputStream(f);
			int temp;
			this.source = "";
			while ((temp = in.read()) != -1) {
				this.source += (char) temp;
			}
			this.source += ' ';
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.tokenTable = tokenTable;
		this.dfa = new DFA();
		kws = new ArrayList<String>();
		kws.add("int");
		kws.add("void");
		kws.add("return");
		kws.add("const");
		kws.add("main");
	}

	public void work() {
		int nowptr = 0;
		int len = source.length();
		String lexeme = "";
		int ID;
		dfa.start();
		while (nowptr < len) {
			char ch = source.charAt(nowptr);
			if (lexeme.length() == 0 && (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r')) {
				nowptr++;
				continue;
			}
			if (lexeme.length() == 0 && ch == '/' && source.charAt(nowptr + 1) == '/') {
				while (ch != '\n') {
					nowptr++;
					ch = source.charAt(nowptr);
				}
				nowptr++;
				continue;
			}
			lexeme += ch;
			ID = dfa.getNext(ch);
			if (ID == -1) {
				System.out.println("LEXICAL ERROR: unexpected character \"" + ch + "\"");
				return;
			}
			if (dfa.isFinal(ID)) {
				if (dfa.ifBackoff(ID)) {
					lexeme = lexeme.substring(0, lexeme.length() - 1);
					nowptr--;
				}
				String tag = dfa.getTag(ID);
				if (kws.contains(lexeme.toLowerCase())) {
					tag = "KW";
				}
				tokenTable.addToken(new Token(lexeme, tag, getPointer(lexeme.toLowerCase())));
				lexeme = "";
				dfa.start();
			}
			nowptr++;
		}
	}

	private int getPointer(String lexeme) {
		switch (lexeme) {
		case "int":
			return 1;
		case "void":
			return 2;
		case "return":
			return 3;
		case "const":
			return 4;
		case "main":
			return 5;
		case "+":
			return 6;
		case "-":
			return 7;
		case "*":
			return 8;
		case "/":
			return 9;
		case "%":
			return 10;
		case "=":
			return 11;
		case ">":
			return 12;
		case "<":
			return 13;
		case "==":
			return 14;
		case "<=":
			return 15;
		case ">=":
			return 16;
		case "!=":
			return 17;
		case "&&":
			return 18;
		case "||":
			return 19;
		case "(":
			return 20;
		case ")":
			return 21;
		case "{":
			return 22;
		case "}":
			return 23;
		case ";":
			return 24;
		case ",":
			return 25;
		}
		return -1;
	}
}
