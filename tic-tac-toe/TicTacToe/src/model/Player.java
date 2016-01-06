package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.GameGUI;
import gui.GameJTable;

/**
 * Model of a Player
 * 
 * @author Frederik Kammel
 */
public class Player {
	public static Player Player1;
	public static Player Player2;
	public static Player PlayerTie;
	public static int playerCount;

	public String name;

	/**
	 * Specifies if the player is an artificial intelligence (true) or a human
	 * (false)
	 */
	public boolean isAi;

	/**
	 * Initializes a new Player.
	 * 
	 * @param name
	 *            Name of the Player
	 */
	public Player(String name) {
		playerCount = playerCount + 1;

		if (name.equals("")) {
			this.name = "Player " + playerCount;
		} else {
			this.name = name;
		}
	}

	/**
	 * Switches Player.Player1 and Player.player2
	 */
	public static void switchPlayers() {
		// Switches Player1 and Player 2
		Player player1Copy = Player1;
		Player player2Copy = Player2;

		Player2 = player1Copy;
		Player1 = player2Copy;
	}

	@Deprecated
	/**
	 * Guides the user through the name selection.
	 * 
	 * @return Returns true, if all dialogs were completed successfully and
	 *         false if the user cancelled the operation
	 */
	public static boolean initPlayers() {
		// returns false if the player wishes to cancel the game

		// reset the PlayerCount
		playerCount = 0;

		// Ask for Player 1's name
		String namePlayer1;

		if (Player1 == null) {
			namePlayer1 = "Player " + (playerCount + 1);
		} else if (Player1.name == "") {
			namePlayer1 = "Player " + (playerCount + 1);
		} else {
			namePlayer1 = Player1.name;
		}

		// String namePlayer2=(String)JOptionPane.showInputDialog(null,
		// "Message", "Title", JOptionPane.INFORMATION_MESSAGE, null, null,
		// "DefaultValue");
		namePlayer1 = (String) JOptionPane.showInputDialog(null,
				"Player " + (playerCount + 1) + ", please enter your name", "Playernames",
				JOptionPane.INFORMATION_MESSAGE, null, null, namePlayer1);

		// Check if the player clicked Cancel
		if (namePlayer1 == null) {
			return false;
		}

		Player1 = new Player(namePlayer1);

		// Ask for Player 2's name
		String namePlayer2;

		if (Player2 == null) {
			namePlayer2 = "Player " + (playerCount + 1);
		} else if (Player2.name == "") {
			namePlayer2 = "Player " + (playerCount + 1);
		} else {
			namePlayer2 = Player2.name;
		}
		// String namePlayer2=(String)JOptionPane.showInputDialog(null,
		// "Message", "Title", JOptionPane.INFORMATION_MESSAGE, null, null,
		// "DefaultValue");
		namePlayer2 = (String) JOptionPane.showInputDialog(null,
				"Player " + (playerCount + 1) + ", please enter your name", "Playernames",
				JOptionPane.INFORMATION_MESSAGE, null, null, namePlayer2);

		// Check if the player clicked Cancel
		if (namePlayer2 == null) {
			return false;
		}

		Player2 = new Player(namePlayer2);

		// We only arrive here if no cancel button was clicked
		return true;
	}

	/**
	 * Decides where the AI will do its next turn by implementing the
	 * Mini-Max-Algorithm.
	 * 
	 * Needs the reference to the callerGUI to send it the info about the played
	 * spot
	 * 
	 * @param currentGameTable
	 *            The gameTable of the current game situation
	 * @param callerGUI
	 *            the GameGUI which calls the AI
	 * @param opponent
	 *            The Player object of the opponent
	 */
	public void doAiTurn(GameJTable currentGameTable, GameGUI callerGUI, Player opponent) {

		if (currentGameTable != null) {
			if (currentGameTable.isEmpty() == false) {
				// get the best turn
				// TreeNode gameTree = buildGameTree2(currentGameTable,
				// opponent);
				// buildGameTree2(GameJTable currentGameTable, int
				// lastPlayedAtRow, int lastPlayedAtRow,
				// Player opponent) {
				GameTree gameTree = buildGameTree2(currentGameTable, opponent);
				if (gameTree.getChildCount() != 0) {

					// debugGameTree.print();

					// output the tree as a visio file
					System.out.println(System.getProperty("user.dir") + "\\gameTreeAsVisioFile.graphml");
					gameTree.printToVisioFile(System.getProperty("user.dir") + "\\gameTreeAsVisioFile.graphml");
					// gameTree.clone(3)
					// .printToVisioFile(System.getProperty("user.dir") +
					// "\\gameTreeAsVisioFile.graphml");

					// print some stats
					System.out.println("Score of the root: " + gameTree.getRoot().scoreIfStateIsReached);
					for (int i = 0; i < gameTree.getChildCount(); i++) {
						System.out.println("Score at " + i + ": " + gameTree.getTotalScore2(gameTree.getChildAt(i))
								+ " - " + gameTree.getChildAt(i).playedAtRow + "/"
								+ gameTree.getChildAt(i).playedAtColumn);
					}

					int bestTurn = gameTree.getBestTurnFromChildren();

					if (gameTree.getChildAt(bestTurn) != null) {
						// There is a turn to do
						// print some more stats
						System.out.println("R: " + gameTree.getChildAt(bestTurn).playedAtRow);
						System.out.println("C: " + gameTree.getChildAt(bestTurn).playedAtColumn);
						System.out.println("Picked " + bestTurn);

						// do the actual turn
						callerGUI.playerPlayed(gameTree.getChildAt(bestTurn).playedAtRow,
								gameTree.getChildAt(bestTurn).playedAtColumn);
					}
				} else {
					// Tree is only one indent deep, so pick a random field
					callerGUI.playerPlayed((int) Math.round(Math.random() * (currentGameTable.getRowCount() - 1)),
							(int) Math.round(Math.random() * (currentGameTable.getColumnCount() - 1)));
				}
			} else {
				// Game is empty -> pick a random field
				callerGUI.playerPlayed((int) Math.round(Math.random() * (currentGameTable.getRowCount() - 1)),
						(int) Math.round(Math.random() * (currentGameTable.getColumnCount() - 1)));
			}
		}
	}

	/**
	 * Builds a tree with all possible turn combinations for use with the Mini
	 * Max Algorithm.<br>
	 * This method uses alpha beta pruning.
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @param opponent
	 *            The Player object of the opponent
	 * @return The tree containing all possible turn combinations
	 */
	private GameTree buildGameTree2(GameJTable currentGameTable, Player opponent) {
		// We only arrive here if no tree was built before or if the child was
		// not found

		GameTree gameTree = new GameTree(currentGameTable);

		buildGameTree_recursive2(gameTree, currentGameTable, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false,
				opponent, 1);

		return gameTree;
	}

	/**
	 * Builds a tree with all possible turn combinations and determines the best
	 * turn for the AI. This method implements the mini-max algorithm with alpha
	 * beta pruning.
	 * 
	 * @param currentGameTable
	 *            The current game situation represented by a GameJTable
	 * @param alpha
	 *            The current best value for the maximizing player (This player)
	 * @param beta
	 *            The current best value for the minimizing player (The
	 *            opponent)
	 * @param opponentsTurn
	 *            (Determines if the opponents turn is simulated
	 * @param opponent
	 *            The Player object representing the opponent
	 * @return The explored gameTree
	 */
	private void buildGameTree_recursive2(GameTree gameTree, GameJTable node, double alpha, double beta,
			boolean opponentsTurn, Player opponent, int indent) {

		// initialize the return tree
		// GameTree gameTree = node.clone();

		// determine if somebody has won
		Player winner = node.winDetector();

		// double intentWeight = 0.000000001;
		double intentWeight = 2;

		if (winner == null) {
			// determine all possible turns
			ArrayList<int[]> turns;
			turns = new ArrayList<int[]>();

			for (int r = 0; r < node.getRowCount(); r++) {
				for (int c = 0; c < node.getColumnCount(); c++) {
					if (node.getPlayerAt(r, c) == null) {
						int[] rc = { r, c };
						turns.add(rc);
					}
				}
			}

			if (opponentsTurn == false) {
				// my turn

				for (int i = 0; i < turns.size(); i++) {
					// duplicate the table
					GameJTable child = node.clone();
					child.alpha = alpha;
					child.beta = beta;

					// do the turn
					child.setPlayerAt(turns.get(i)[0], turns.get(i)[1], this);
					// child.playedAtRow = turns.get(i)[0];
					// child.playedAtColumn = turns.get(i)[1];

					// System.out.println(child.getObject().toString());

					gameTree.addChild(child, node);
					// output the tree as a visio file
					// System.out.println(System.getProperty("user.dir") +
					// "\\gameTreeAsVisioFile.graphml");
					// gameTree.printToVisioFile(System.getProperty("user.dir")
					// + "\\gameTreeAsVisioFile.graphml");
					buildGameTree_recursive2(gameTree, child, alpha, beta, true, opponent, indent + 1);

					// System.out.println("===returned to node===");
					// System.out.println(node.getObject().toString());

					alpha = Math.max(alpha, child.scoreIfStateIsReached);
					child.alpha = alpha;

					if (alpha >= beta) {
						// prune
						break;
					}
				}

				node.scoreIfStateIsReached = alpha;
			} else {
				// opponents turn

				for (int i = 0; i < turns.size(); i++) {
					// duplicate the table
					GameJTable child = node.clone();
					child.alpha = alpha;
					child.beta = beta;

					// do the turn
					child.setPlayerAt(turns.get(i)[0], turns.get(i)[1], opponent);
					// child.playedAtRow = turns.get(i)[0];
					// child.playedAtColumn = turns.get(i)[1];

					// System.out.println(child.getObject().toString());

					gameTree.addChild(child, node);
					// output the tree as a visio file
					// System.out.println(System.getProperty("user.dir") +
					// "\\gameTreeAsVisioFile.graphml");
					// gameTree.printToVisioFile(System.getProperty("user.dir")
					// + "\\gameTreeAsVisioFile.graphml");
					buildGameTree_recursive2(gameTree, child, alpha, beta, false, opponent, indent + 1);

					// System.out.println("===returned to node===");
					// System.out.println(node.getObject().toString());

					beta = Math.min(beta, child.scoreIfStateIsReached);
					child.beta = beta;

					if (alpha >= beta) {
						// prune
						break;
					}
				}

				node.scoreIfStateIsReached = beta;
			}
		} else if (winner.equals(this)) {
			// I won
			node.scoreIfStateIsReached = 15.0 / (Math.pow(intentWeight, indent + 1));
		} else if (winner.equals(opponent)) {
			// opponent won
			node.scoreIfStateIsReached = -15.0 / (Math.pow(intentWeight, indent + 1));
			// (15.0 / (intentWeight * intent + 1))
		} else {
			// It's a tie
			node.scoreIfStateIsReached = 0;
		}
	}
}
