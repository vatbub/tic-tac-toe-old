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
 * A node of a game tree (A Tree that contains all possible turns in a tic tac
 * toe game)
 */

public class GameTree extends SimpleDirectedGraph<GameJTable, DefaultEdge> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7983775946054813350L;
	private GameJTable root;
	// private GameJTable object;
	// public int playedAtRow;
	// public int playedAtColumn;
	// private ArrayList<TreeNode> children;

	// Getters and Setters
	public GameJTable getRoot() {
		// return object;
		return root;
	}

	public void setRoot(GameJTable root) {
		this.root = root;
	}

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

	/*
	 * public TreeNode(GameJTable object, ArrayList<TreeNode> children) {
	 * this.object = object; this.children = children; }
	 */

	// Methods
	/**
	 * Adds a child to the tree
	 * 
	 * @param child
	 *            The child node to be added
	 */
	public void addChild(GameJTable child) {
		addChild(child, root);
	}

	public void addChild(GameJTable child, GameJTable node) {
		this.addVertex(child);
		this.addEdge(node, child);
	}

	/**
	 * Adds a child to the tree at the specified position
	 * 
	 * @param index
	 *            Index where the child should be added
	 * @param child
	 *            The child node to be added
	 */
	/*
	 * public void addChildAt(int index, TreeNode child) { if (children == null)
	 * { children = new ArrayList<TreeNode>(); }
	 * 
	 * children.add(index, child); }
	 */

	/**
	 * Returns the child at the specified index
	 * 
	 * @param index
	 *            The index of the desired child
	 * @return The child at the index index
	 */
	public GameJTable getChildAt(int index) {
		return getChildAt(index, root);
	}

	public GameJTable getChildAt(int index, GameJTable node) {
		return this.getEdgeTarget((DefaultEdge) this.outgoingEdgesOf(node).toArray()[index]);
	}

	public int getChildCount() {
		return getChildCount(root);
	}

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
		double sum = 0;
		//System.out.println(this.getChildCount(child));
		if (this.getChildCount(child) == 0) {
			return child.scoreIfStateIsReached;
		} else {
			for (int i = 0; i < this.getChildCount(child); i++) {
				sum = sum + getTotalScore(this.getChildAt(i));
			}
			return sum;
		}
	}

	/**
	 * Sums up the score of this node and all of its child nodes.
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
	 * turn with the highest score, a turn will be piyked by random among the
	 * best turns.
	 * 
	 * @return The child index with the best turn
	 */
	public int getBestTurnFromChildren() {
		ArrayList<int[]> maxIndeces = new ArrayList<int[]>();
		int maxIndex = 0;
		if (this.getChildCount() != 0) {
			for (int i = 0; i < this.getChildCount(); i++) {
				if (getTotalScore(this.getChildAt(i)) > getTotalScore(this.getChildAt(maxIndex))) {
					maxIndex = i;
				}
			}

			System.out.println("Initial maxIndex: " + maxIndex);

			// randomize between all equal maxes
			for (int i = 0; i < this.getChildCount(); i++) {
				if (getTotalScore(this.getChildAt(i)) == getTotalScore(this.getChildAt(maxIndex))) {
					int[] e = { i };
					maxIndeces.add(e);
				}
			}

			int randIndex = (int) Math.round(Math.random() * (maxIndeces.size() - 1));
			maxIndex = maxIndeces.get(randIndex)[0];
		}

		return maxIndex;
	}

	public void printToVisioFile(String fileName) {
		FileWriter fileWriter = null;
		DOTExporter<GameJTable, DefaultEdge> exporter=new DOTExporter<GameJTable, DefaultEdge>(new IntegerNameProvider<GameJTable>(), new StringNameProvider<GameJTable>(), new IntegerEdgeNameProvider<DefaultEdge>());

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
	/*
	 * public GameTree clone() {
	 * 
	 * 
	 * // Clone the object GameTree res = new GameTree(this.getRoot().clone());
	 * 
	 * // Clone the cildren if (children != null) { for (int i = 0; i <
	 * this.getChildCount(); i++) { res.addChild(this); getChildAt(i); } }
	 * 
	 * // Clone other variables res.playedAtRow = playedAtRow;
	 * res.playedAtColumn = playedAtColumn;
	 * 
	 * return res; }
	 */
}
