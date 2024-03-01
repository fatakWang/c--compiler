package dfa;

import java.util.ArrayList;
import java.util.List;

public class DFA {
	List<Node> nodes;
	private int startID;
	private int nowID;

	public DFA() {
		this.nodes = new ArrayList<Node>();
		this.startID = 0;
		this.nowID = 0;
		this.init();
	}

	private void init() {
		addNode(new Node(0, false, false, ""));
		addNode(new Node(1, false, false, ""));
		addNode(new Node(2, true, true, "INT"));
		addNode(new Node(3, false, false, ""));
		addNode(new Node(4, true, true, "Ident"));
		addNode(new Node(5, true, false, "SE"));
		addNode(new Node(6, true, false, "OP"));
		addNode(new Node(7, false, false, ""));
		addNode(new Node(8, false, false, ""));
		addNode(new Node(9, false, false, ""));
		addNode(new Node(10, false, false, ""));
		addNode(new Node(11, true, true, "OP"));
		addNode(new Node(12, true, false, "INT"));
		addEdge(new Edge(Type.DIGITNO, 0, 1, ' '));
		addEdge(new Edge(Type.CHAR, 0, 12, '0'));
		addEdge(new Edge(Type.DIGITNO, 1, 1, ' '));
		addEdge(new Edge(Type.CHAR, 1, 1, '0'));
		addEdge(new Edge(Type.NOTDIGIT, 1, 2, ' '));
		addEdge(new Edge(Type.LETTER_, 0, 3, ' '));
		addEdge(new Edge(Type.DIGITNO, 3, 3, ' '));
		addEdge(new Edge(Type.LETTER_, 3, 3, ' '));
		addEdge(new Edge(Type.CHAR, 3, 3, '0'));
		addEdge(new Edge(Type.NOTLETTER_ORDIGIT, 3, 4, ' '));
		addEdge(new Edge(Type.CHAR, 0, 5, '('));
		addEdge(new Edge(Type.CHAR, 0, 5, ')'));
		addEdge(new Edge(Type.CHAR, 0, 5, '{'));
		addEdge(new Edge(Type.CHAR, 0, 5, '}'));
		addEdge(new Edge(Type.CHAR, 0, 5, ';'));
		addEdge(new Edge(Type.CHAR, 0, 5, ','));
		addEdge(new Edge(Type.CHAR, 0, 6, '+'));
		addEdge(new Edge(Type.CHAR, 0, 6, '-'));
		addEdge(new Edge(Type.CHAR, 0, 6, '*'));
		addEdge(new Edge(Type.CHAR, 0, 6, '/'));
		addEdge(new Edge(Type.CHAR, 0, 6, '%'));
		addEdge(new Edge(Type.CHAR, 0, 7, '!'));
		addEdge(new Edge(Type.CHAR, 0, 8, '&'));
		addEdge(new Edge(Type.CHAR, 0, 9, '|'));
		addEdge(new Edge(Type.CHAR, 7, 6, '='));
		addEdge(new Edge(Type.CHAR, 8, 6, '&'));
		addEdge(new Edge(Type.CHAR, 9, 6, '|'));
		addEdge(new Edge(Type.CHAR, 0, 10, '>'));
		addEdge(new Edge(Type.CHAR, 0, 10, '<'));
		addEdge(new Edge(Type.CHAR, 0, 10, '='));
		addEdge(new Edge(Type.CHAR, 10, 6, '='));
		addEdge(new Edge(Type.NOTEQUAL, 10, 11, ' '));	
	}

	private void addNode(Node node) {
		nodes.add(node);
	}

	private void addEdge(Edge edge) {
		nodes.get(edge.getFromID()).addEdge(edge);
	}

	public void start() {
		nowID = startID;
	}

	public Boolean isFinal(int ID) {
		return nodes.get(ID).getIsFinal();
	}

	public Boolean ifBackoff(int ID) {
		return nodes.get(ID).getIsBackoff();
	}

	public String getTag(int ID) {
		return nodes.get(ID).getTag();
	}

	public int getNext(char ch) {
		Node node = nodes.get(nowID);
		int size = node.getSize();
		int ID = -1;
		for (int i = 0; i < size; i++) {
			Edge edge = node.getEdge(i);
			if (check(edge, ch)) {
				ID = edge.getToID();
				break;
			}
		}
		if (ID != -1)
			nowID = ID;
		return ID;
	}

	private Boolean check(Edge edge, char ch) {
		switch (edge.getType()) {
		case CHAR:
			return edge.getCh() == ch;
		case DIGITNO:
			return ('1' <= ch && ch <= '9');
		case NOTDIGIT:
			return (!Character.isDigit(ch));
		case NOTEQUAL:
			return (ch != '=');
		case LETTER_:
			return Character.isLetter(ch) || (ch == '_');
		case NOTLETTER_ORDIGIT:
			return (!Character.isLetterOrDigit(ch)) && (ch != '_');
		}
		return false;
	}
}
