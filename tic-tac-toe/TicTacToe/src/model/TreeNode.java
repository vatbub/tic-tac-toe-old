/**
 * A node of a game tree (A Tree that contains all possible turns in a tic tac toe game)
 */

package model;

import java.util.ArrayList;

import gui.GameJTable;
import model.*;

public class TreeNode {

	private GameJTable object;
	public int playedAtRow;
	public int playedAtColumn;
	private int lastTotalScore;
	private ArrayList<TreeNode> children;

	// Getters and Setters
	public GameJTable getObject() {
		return object;
	}

	public void setObject(GameJTable object) {
		this.object = object;
	}

	// Constructors
	public TreeNode() {
		this(null);
	}

	public TreeNode(GameJTable object) {
		this(object, null);
	}

	public TreeNode(GameJTable object, ArrayList<TreeNode> children) {
		this.object = object;
		this.children = children;
	}

	// Methods
	public void addChild(TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		this.addChildAt(children.size(), child);
	}

	public void addChildAt(int index, TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}

		children.add(index, child);
	}

	public TreeNode getChildAt(int index) {
		if (children != null) {
			return children.get(index);
		} else {
			return null;
		}
	}

	public int getChildCount() {
		return children.size();
	}

	public int getTotalScore() {
		return getTotalScore(false);
	}

	public int getTotalScore(boolean overrideCache) {
		if (lastTotalScore == 0 | overrideCache == true) {
			int sum = 0;
			if (children == null) {
				return object.scoreIfStateIsReached;
			} else {
				for (int i = 0; i < children.size(); i++) {
					sum = sum + this.getChildAt(i).getTotalScore();
				}

				lastTotalScore = sum;
				return sum;
			}
		} else {
			return lastTotalScore;
		}
	}

	public int getBestTurnFromChildren() {
		ArrayList<int[]> maxIndeces = new ArrayList<int[]>();
		int maxIndex = 0;
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getTotalScore() > children.get(maxIndex).getTotalScore()) {
					maxIndex = i;
				}
			}

			System.out.println("Initial maxIndex: " + maxIndex);

			// randomize between all equal maxes
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getTotalScore() == children.get(maxIndex).getTotalScore()) {
					int[] e = { i };
					maxIndeces.add(e);
				}
			}

			int randIndex = (int) Math.round(Math.random() * (maxIndeces.size() - 1));
			maxIndex = maxIndeces.get(randIndex)[0];
		}

		return maxIndex;
	}
}
