package dfa;

public class Edge {
	private Type type;
	private int fromID;
	private int toID;
	private char ch;
	public Edge(Type type, int fromID, int toID, char ch) {
		this.type = type;
		this.fromID = fromID;
		this.toID = toID;
		this.ch = ch;
	}
	
	public Type getType() {
		return type;
	}
	public int getFromID() {
		return fromID;
	}
	public int getToID() {
		return toID;
	}
	public char getCh() {
		return ch;
	}
	
}
