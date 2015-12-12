/**
 * A node of a game tree (A Tree that contains all possible turns in a tic tac toe game)
 */

package model;

import java.util.ArrayList;

import gui.GameJTable;

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
	/**
	 * Adds a child to the tree
	 * 
	 * @param child
	 *            The child node to be added
	 */
	public void addChild(TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}
		this.addChildAt(children.size(), child);
	}

	/**
	 * Adds a child to the tree at the specified position
	 * 
	 * @param index
	 *            Index where the child should be added
	 * @param child
	 *            The child node to be added
	 */
	public void addChildAt(int index, TreeNode child) {
		if (children == null) {
			children = new ArrayList<TreeNode>();
		}

		children.add(index, child);
	}

	/**
	 * Returns the child at the specified index
	 * 
	 * @param index
	 *            The index of the desired child
	 * @return The child at the index index
	 */
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

	/**
	 * Sums up the score of this node and all of its child nodes. Total score is
	 * cached for speed reasons. Use getTotalScore(true) to override the cache.
	 * 
	 * @return The total score of this node
	 */
	public int getTotalScore() {
		return getTotalScore(false);
	}

	/**
	 * Sums up the score of this node and all of its child nodes. Total score is
	 * cached for speed reasons
	 * 
	 * @param overrideCache
	 *            If true, the cache will be overridden
	 * @return The total score of this node
	 */
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

	/**
	 * Determines the best possible turn of all of this nodes children by
	 * determining the turn with the highest score. If there is more than one
	 * turn with the highest score, a turn will be piyked by random among the
	 * best turns.
	 * 
	 * @return The child index with the best turn
	 */
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

	@Deprecated
	public TreeNode getChildByTable(GameJTable gameTable) {
		TreeNode res;
		res = null;
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getObject().equals(gameTable)) {
					res = children.get(i);
					break;
				}
			}
		}

		return res;
	}
}
