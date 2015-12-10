/**
 * A node of a tree
 */

package tree;

import java.util.ArrayList;

public class Node<DataType> {

	private DataType object;
	private ArrayList<Node<DataType>> children;

	// Getters and Setters
	public DataType getObject() {
		return object;
	}

	public void setObject(DataType object) {
		this.object = object;
	}

	/*
	 * public ArrayList<Node<DataType>> getChildren() { return children; }
	 * 
	 * public void setChildren(ArrayList<Node<DataType>> children) {
	 * this.children = children; }
	 * 
	 */

	// Constructors
	public Node() {
		this(null);
	}

	public Node(DataType object) {
		this(object, null);
	}

	public Node(DataType object, ArrayList<Node<DataType>> children) {
		this.object = object;
		this.children = children;
	}

	// Methods
	public void addChild(Node<DataType> child) {
		this.addChildAt(children.size(), child);
	}

	public void addChildAt(int index, Node<DataType> child) {
		if (children == null) {
			children = new ArrayList<Node<DataType>>();
		}
		
		children.add(index, child);
	}
	
	public Node<DataType> getChildAt(int index){
		return children.get(index);
	}
}
