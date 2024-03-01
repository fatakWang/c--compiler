package dfa;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int ID;
	private Boolean isFinal;
	private Boolean isBackoff;
	private String tag;
	private int size;
	List<Edge> edges;

	public Node(int ID, Boolean isFinal, Boolean isBackoff, String tag) {
		this.ID = ID;
		this.isFinal = isFinal;
		this.isBackoff = isBackoff;
		this.tag = tag;
		this.edges = new ArrayList<Edge>();
	}

	public int getID() {
		return ID;
	}

	public Boolean getIsBackoff() {
		return isBackoff;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public String getTag() {
		return tag;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	private void updateSize() {
		size = edges.size();
	}

	public int getSize() {
		updateSize();
		return size;
	}
	
	public Edge getEdge(int index) {
		return edges.get(index);
	}

}
