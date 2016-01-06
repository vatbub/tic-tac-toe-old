package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import gui.GameJTable;
import org.jgrapht.EdgeFactory;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.ext.*;

/**
 * A game tree (A Tree that contains all possible turns in a tic tac
 * toe game)
 */

public class GameTree extends SimpleDirectedGraph<GameJTable, DefaultEdge> {
	private static final long serialVersionUID = -7983775946054813350L;
	
	/**
	 * The root element of the tree
	 */
	private GameJTable root;

	// Constructors

	public GameTree(GameJTable object) {
		// this(object, null);
		this(new ClassBasedEdgeFactory<GameJTable, DefaultEdge>(DefaultEdge.class), object);
	}

	public GameTree(EdgeFactory<GameJTable, DefaultEdge> ef, GameJTable object) {
		super(ef);
		
		root = object;
		this.addVertex(root);
	}

	// Getters and Setters
	
		/**
		 * Returns the root node
		 * @return The root node
		 */
		public GameJTable getRoot() {
			return root;
		}

		/**
		 * Sets the root node
		 * @param root The new root node
		 */
		public void setRoot(GameJTable root) {
			this.root = root;
		}
	
	/**
	 * Adds a child to the root of the tree
	 * 
	 * @param child
	 *            The child node to be added
	 */
	public void addChild(GameJTable child) {
		addChild(child, root);
	}

	/**
	 * Adds a child to a node of the tree
	 * @param child The child node to be added
	 * @param node The parent node of the child
	 */
	public void addChild(GameJTable child, GameJTable node) {
		this.addVertex(child);
		this.addEdge(node, child);
	}

	/**
	 * Returns the child of the root node at the specified index
	 * 
	 * @param index
	 *            The index of the desired child
	 * @return The child at the index index
	 */
	public GameJTable getChildAt(int index) {
		return getChildAt(index, root);
	}

	/**
	 * Returns the child of the specified node at the specified index
	 * @param index The index of the node
	 * @param node The parent node
	 * @return The child node of node at the specified index
	 */
	public GameJTable getChildAt(int index, GameJTable node) {
		return this.getEdgeTarget((DefaultEdge) this.outgoingEdgesOf(node).toArray()[index]);
	}
	
	/**
	 * Gets the child count of the root node
	 * @return The child count of the root node
	 */
	public int getChildCount() {
		return getChildCount(root);
	}

	/**
	 * Gets the child count of the specified node
	 * @param node The node where the children should be counted
	 * @return The child count of node
	 */
	public int getChildCount(GameJTable node) {
		return this.outgoingEdgesOf(node).size();
	}

	/**
	 * Sums up the score of the specified node and all of its child nodes.
	 * 
	 * @param child
	 *            The child the score will be calculated for
	 * @return The total score of this node
	 */
	public double getTotalScore(GameJTable child) {
		return getTotalScore_recursive(child, 0);
	}
	
	private double getTotalScore_recursive(GameJTable child, int indent){
		double sum = 0;
		// System.out.println(this.getChildCount(child));
		if (this.getChildCount(child) == 0 || indent>=9) {
			System.out.println(child.scoreIfStateIsReached);
			return child.scoreIfStateIsReached;
		} else {
			for (int i = 0; i < this.getChildCount(child); i++) {
				sum = sum + getTotalScore_recursive(this.getChildAt(i), indent+1);
			}
			return sum;
		}
	}

	/**
	 * Returns the score of the specified child (Does not sum up child nodes)
	 * 
	 * @param child
	 *            The child the score will be calculated for
	 * @return The total score of this node
	 */
	public double getTotalScore2(GameJTable child) {
		return child.scoreIfStateIsReached;
	}

	/**
	 * Determines the best possible turn of all of this nodes children by
	 * determining the turn with the highest score. If there is more than one
	 * turn with the highest score, a turn will be picked by random among the
	 * best turns.
	 * 
	 * @return The child index with the best turn
	 */
	public int getBestTurnFromChildren() {
		ArrayList<Integer> maxIndeces = new ArrayList<Integer>();
		int maxIndex = 0;
		if (this.getChildCount() != 0) {
			for (int i = 0; i < this.getChildCount(); i++) {
				if (getTotalScore2(this.getChildAt(i)) > getTotalScore2(this.getChildAt(maxIndex))) {
					maxIndex = i;
				}
			}

			System.out.println("Initial maxIndex: " + maxIndex);

			// randomize between all equal maxes
			for (int i = 0; i < this.getChildCount(); i++) {
				if (getTotalScore2(this.getChildAt(i)) == getTotalScore2(this.getChildAt(maxIndex))) {
					maxIndeces.add(i);
				}
			}

			
			int randIndex = (int) Math.round(Math.random() * (maxIndeces.size() - 1));
			maxIndex = maxIndeces.get(randIndex);
		}

		return maxIndex;
	}

	/**
	 * Prints this GameTree into a GraphML file that can be viewed with a software like <a href="https://www.yworks.com/products/yed">yED</a>
	 * @param fileName The absolute file name of the new file. If no path is specified, the file will be saved to the current bin directory
	 */
	public void printToVisioFile(String fileName) {
		FileWriter fileWriter = null;
		GraphMLExporter<GameJTable, DefaultEdge> exporter = new GraphMLExporter<GameJTable, DefaultEdge>(
				new IntegerNameProvider<GameJTable>(), new StringNameProvider<GameJTable>(),
				new IntegerEdgeNameProvider<DefaultEdge>(), new IntegerEdgeNameProvider<DefaultEdge>());

		try {

			fileWriter = new FileWriter(fileName);

			exporter.export(fileWriter, this);

			System.out.println("DOT file was created successfully !!!");

		} catch (Exception e) {

			System.out.println("Error in CsvFileWriter !!!");

			e.printStackTrace();

		} finally {

			try {

				fileWriter.flush();

				fileWriter.close();

			} catch (IOException e) {

				System.out.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();

			}

		}
	}
	
	@Override
	/**
	 * Clones this gameTree
	 * 
	 * @return A clone of this GameTree
	 */
	public GameTree clone(){
		return this.clone(-1);
	}

	/**
	 * Clones the GameTree
	 * 
	 * @param desiredDepth
	 *            The number of levels the new GameTree. All nodes deeper than
	 *            the specified level are cut off.<br>
	 *            {@code desiredDepth=-1 } will lead to a complete clone.
	 * @return A clone of this GameTree with the specified depth.
	 */
	public GameTree clone(long desiredDepth) {
		GameTree res = new GameTree(this.getRoot().clone());

		cloneChildren_recursive(res, res.getRoot(), this.getRoot(), 1, desiredDepth);

		return res;
	}

	private void cloneChildren_recursive(GameTree newGameTree, GameJTable curNewRootNode, GameJTable curRootNode,
			long indent, long desiredDepth) {
		if (this.getChildCount(curRootNode) != 0) {
			if (indent < desiredDepth || desiredDepth == -1) {
				for (int i = 0; i < this.getChildCount(curRootNode); i++) {
					GameJTable child = this.getChildAt(i, curRootNode).clone();
					newGameTree.addChild(child, curNewRootNode);
					cloneChildren_recursive(newGameTree, child, this.getChildAt(i, curRootNode), indent = indent + 1,
							desiredDepth);
				}
			}
		}
	}
}
