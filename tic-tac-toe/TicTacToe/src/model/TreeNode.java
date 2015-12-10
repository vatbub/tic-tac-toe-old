/**
 * A node of a game tree (A Tree that contains all possible turns in a tic tac toe game)
 */

package model;

import java.util.ArrayList;
import model.*;

public class TreeNode{

	private AiGameTable object;
	private ArrayList<TreeNode> children;

	// Getters and Setters
	public AiGameTable getObject() {
		return object;
	}

	public void setObject(AiGameTable object) {
		this.object = object;
	}

	// Constructors
	public TreeNode() {
		this(null);
	}

	public TreeNode(AiGameTable object) {
		this(object, null);
	}

	public TreeNode(AiGameTable object, ArrayList<TreeNode> children) {
		this.object = object;
		this.children = children;
	}

	// Methods
	public void addChild(TreeNode child) {
		this.addChildAt(children.size(), child);
	}

	public void addChildAt(int index, TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		
		children.add(index, child);
	}
	
	public TreeNode getChildAt(int index){
		return children.get(index);
	}
	
	public int getChildCount(){
		return children.size();
	}
	
	public int getTotalScore(){
		int sum=0;
		
		for (int i=0;i<children.size();i++){
			sum=sum+this.getChildAt(i).getTotalScore();
		}
		
		return sum;
	}
	
	public int getBestTurnFromChildren(){
		int maxIndex=0;
		
		for(int i=0;i<children.size();i++){
			if (children.get(i).getTotalScore()>children.get(maxIndex).getTotalScore()){
				maxIndex=i;
			}
		}
		
		return maxIndex;
	}
}
