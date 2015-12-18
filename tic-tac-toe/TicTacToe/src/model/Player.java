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
	public void doAiTurn(GameJTable currentGameTable, int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI,
			Player opponent) {

		if (currentGameTable != null) {
			if (currentGameTable.isEmpty() == false) {
				// get the best turn
				// TreeNode gameTree = buildGameTree2(currentGameTable,
				// opponent);
				// buildGameTree2(GameJTable currentGameTable, int
				// lastPlayedAtRow, int lastPlayedAtRow,
				// Player opponent) {
				TreeNode gameTree = buildGameTree2(currentGameTable, lastPlayedAtRow, lastPlayedAtRow, opponent);
				if (gameTree.getChildCount() != 0) {
					// print some stats
					System.out.println("Score of the root: " + gameTree.getObject().scoreIfStateIsReached);
					for (int i = 0; i < gameTree.getChildCount(); i++) {
						System.out.println("Score at " + i + ": " + gameTree.getChildAt(i).getTotalScore() + " - "
								+ gameTree.getChildAt(i).playedAtRow + "/" + gameTree.getChildAt(i).playedAtColumn);
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
					// Tree is only one intent deep, so pick a random field
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
	 * Builds a tree with all possible turn combinations ATTENTION: For speed
	 * reasons the tree will be cut at Config.cutGameTreeAtIntent
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @param opponent
	 *            The Player object of the opponent
	 * @return The tree containing all possible turn combinations
	 */
	private TreeNode buildGameTree(GameJTable currentGameTable, Player opponent) {
		// We only arrive here if no tree was built before or if the child was
		// not found
		return buildGameTree_recursive(currentGameTable, false, opponent, 1);
	}

	/**
	 * Builds a tree with all possible turn combinations ATTENTION: For speed
	 * reasons the tree will be cut at Config.cutGameTreeAtIntent
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @param opponentsTurn
	 *            Specifies if scores should be inverted in this part of the
	 *            tree because its the opponents turn
	 * @param opponent
	 *            The Player object of the opponent
	 * @param intent
	 *            the current intent of the tree
	 * @return The tree containing all possible turn combinations
	 */
	private TreeNode buildGameTree_recursive(GameJTable currentGameTable, boolean opponentsTurn, Player opponent,
			int intent) {
		TreeNode gameTree = new TreeNode();

		int scoreCoeff = 1;

		// invert scores eventually
		if (opponentsTurn == true) {
			scoreCoeff = -1;
		}

		// determine all possible turns
		ArrayList<int[]> turns;
		turns = new ArrayList<int[]>();

		for (int r = 0; r < currentGameTable.getRowCount(); r++) {
			for (int c = 0; c < currentGameTable.getColumnCount(); c++) {
				if (currentGameTable.getPlayerAt(r, c) == null) {
					int[] rc = { r, c };
					turns.add(rc);
				}
			}
		}

		gameTree.setObject(currentGameTable.clone());

		for (int i = 0; i < turns.size(); i++) {
			// duplicate the table
			GameJTable tableTemp = gameTree.getObject().clone();

			// do the turn

			if (opponentsTurn == false) {
				// My turn
				tableTemp.setPlayerAt(turns.get(i)[0], turns.get(i)[1], this);
			} else {
				// opponents turn
				tableTemp.setPlayerAt(turns.get(i)[0], turns.get(i)[1], opponent);
			}

			// check if somebody won
			Player playerWonTemp = tableTemp.winDetector(turns.get(i)[0], turns.get(i)[1]);

			// set the score to 10 if I would win and to -10 if the opponent
			// would win and continue if nobody would win
			if (playerWonTemp == null) {
				// opponents turn
				// cut the tree at a specific intent
				if (intent + 1 <= Config.cutGameTreeAtIntent || Config.cutGameTreeAtIntent == 0) {
					TreeNode child = buildGameTree_recursive(tableTemp, !opponentsTurn, opponent, intent + 1);
					child.playedAtColumn = turns.get(i)[1];
					child.playedAtRow = turns.get(i)[0];
					gameTree.addChild(child);
				}
			} else {
				double intentWeight = 0.000000001;
				if (playerWonTemp.equals(this)) {
					tableTemp.scoreIfStateIsReached = (int) (10.0 / (intentWeight * intent + 1)) * scoreCoeff;
				} else if (playerWonTemp.equals(PlayerTie)) {
					// its a tie
					// tableTemp.scoreIfStateIsReached = (int) (0.0 /
					// 0.00000001*(intent)) * scoreCoeff;
					tableTemp.scoreIfStateIsReached = 0;
				} else {
					// opponent wins
					tableTemp.scoreIfStateIsReached = (int) (15.0 / (intentWeight * intent + 1)) * scoreCoeff;
				}
				TreeNode childNode = new TreeNode(tableTemp);
				childNode.playedAtColumn = turns.get(i)[1];
				childNode.playedAtRow = turns.get(i)[0];
				gameTree.addChild(childNode);
			}
		}

		return gameTree;
	}

	/**
	 * Builds a tree with all possible turn combinations ATTENTION: For speed
	 * reasons the tree will be cut at Config.cutGameTreeAtIntent
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @param opponent
	 *            The Player object of the opponent
	 * @return The tree containing all possible turn combinations
	 */
	private TreeNode buildGameTree2(GameJTable currentGameTable, int lastPlayedAtRow, int lastPlayedAtColumn,
			Player opponent) {
		// We only arrive here if no tree was built before or if the child was
		// not found

		TreeNode gameTreeRoot = new TreeNode(currentGameTable);
		gameTreeRoot.playedAtRow = lastPlayedAtRow;
		gameTreeRoot.playedAtColumn = lastPlayedAtColumn;
		return buildGameTree_recursive2(gameTreeRoot, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false,
				opponent, 1);
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
	private TreeNode buildGameTree_recursive2(TreeNode node, double alpha, double beta, boolean opponentsTurn,
			Player opponent, int intent) {

		// initialize the return tree
		TreeNode gameTree = node.clone();

		// determine if somebody has won
		Player winner = node.getObject().winDetector(node.playedAtRow, node.playedAtColumn, false);

		double intentWeight = 0.000000001;
		//double intentWeight = 2;
		
		if (winner == null) {
			// determine all possible turns
			ArrayList<int[]> turns;
			turns = new ArrayList<int[]>();

			for (int r = 0; r < node.getObject().getRowCount(); r++) {
				for (int c = 0; c < node.getObject().getColumnCount(); c++) {
					if (node.getObject().getPlayerAt(r, c) == null) {
						int[] rc = { r, c };
						turns.add(rc);
					}
				}
			}

			if (opponentsTurn == false) {
				for (int i = 0; i < turns.size(); i++) {
					// duplicate the table
					TreeNode child = new TreeNode(gameTree.getObject().clone());

					// do the turn
					child.getObject().setPlayerAt(turns.get(i)[0], turns.get(i)[1], this);
					child.playedAtRow=turns.get(i)[0];
					child.playedAtColumn=turns.get(i)[1];
					
					//System.out.println(child.getObject().toString());
					
					child = buildGameTree_recursive2(child, alpha, beta, true, opponent, intent + 1);
					
					//System.out.println("===returned to node===");
					//System.out.println(node.getObject().toString());
					
					alpha = Math.max(alpha, child.getObject().scoreIfStateIsReached);
					gameTree.addChild(child);

					if (alpha >= beta) {
						// prune
						break;
					}
				}

				gameTree.getObject().scoreIfStateIsReached = alpha;
			} else {
				for (int i = 0; i < turns.size(); i++) {
					// duplicate the table
					TreeNode child = new TreeNode(gameTree.getObject().clone());

					// do the turn
					child.getObject().setPlayerAt(turns.get(i)[0], turns.get(i)[1], opponent);
					child.playedAtRow=turns.get(i)[0];
					child.playedAtColumn=turns.get(i)[1];
					
					//System.out.println(child.getObject().toString());

					child = buildGameTree_recursive2(child, alpha, beta, false, opponent, intent + 1);
					
					//System.out.println("===returned to node===");
					//System.out.println(node.getObject().toString());
					
					beta = Math.min(beta, child.getObject().scoreIfStateIsReached);
					gameTree.addChild(child);

					if (alpha >= beta) {
						// prune
						break;
					}
				}

				gameTree.getObject().scoreIfStateIsReached = beta;
			}
		} else if (winner.equals(this)) {
			gameTree.getObject().scoreIfStateIsReached = 15.0/ (intentWeight * intent + 1);
		} else if (winner.equals(opponent)) {
			gameTree.getObject().scoreIfStateIsReached = -100.0/ (intentWeight * intent + 1);
			//(15.0 / (intentWeight * intent + 1))
		} else {
			gameTree.getObject().scoreIfStateIsReached = 0;
		}
		
		return gameTree;
	}
}
