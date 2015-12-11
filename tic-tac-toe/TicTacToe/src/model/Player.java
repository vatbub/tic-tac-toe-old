/**
 * Model of a Player
 * 
 * @author Frederik Kammel
 */

package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.GameGUI;
import gui.GameJTable;

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
	 * Mini-Max-Algorithm
	 * 
	 * Needs the reference to the callerGUI to send it the info about the played
	 * spot
	 * 
	 * @param currentGameTable
	 *            The gameTable of the current game situation
	 * @param callerGui
	 *            the GameGUI which calls the AI
	 */
	public void doAiTurn(GameJTable currentGameTable, GameGUI callerGUI, Player opponent) {

		if (currentGameTable.isEmpty() == false) {
			TreeNode gameTree = buildGameTree(currentGameTable, opponent);
			int bestTurn = gameTree.getBestTurnFromChildren();

			for (int i=0;i<gameTree.getChildCount();i++){
				System.out.println("Score at " + i + ": " + gameTree.getChildAt(i).getTotalScore() + " - " + gameTree.getChildAt(i).playedAtRow + "/" + gameTree.getChildAt(i).playedAtColumn);
			}
			// do the actual turn
			if (gameTree.getChildAt(bestTurn) != null) {
				// There is a turn to do
				System.out.println("R: " + gameTree.getChildAt(bestTurn).playedAtRow);
				System.out.println("C: " + gameTree.getChildAt(bestTurn).playedAtColumn);
				System.out.println("Picked " + bestTurn);
				callerGUI.playerPlayed(gameTree.getChildAt(bestTurn).playedAtRow,
						gameTree.getChildAt(bestTurn).playedAtColumn);
			}
		} else {
			// Game is empty -> pick a random field
			callerGUI.playerPlayed((int) Math.round(Math.random() * (currentGameTable.getRowCount() - 1)),
					(int) Math.round(Math.random() * (currentGameTable.getColumnCount() - 1)));
		}
	}

	/**
	 * Builds a tree with all possible turn combinations
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @return The tree containing all possible turn combinations
	 */
	private TreeNode buildGameTree(GameJTable currentGameTable, Player opponent) {
		return buildGameTree_recursive(currentGameTable, false, opponent, 1);
	}

	/**
	 * Builds a tree with all possible turn combinations
	 * 
	 * @param currentGameTable
	 *            The current gameTable
	 * @param opponentsTurn
	 *            Specifies if scores should be inverted in this part of the
	 *            tree because its the opponents turn
	 * @return The tree containing all possible turn combinations
	 */
	private TreeNode buildGameTree_recursive(GameJTable currentGameTable, boolean opponentsTurn, Player opponent, int intent) {
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
			Player playerWonTemp = tableTemp.winDetector(turns.get(i)[0], turns.get(i)[1], "");

			// set the score to 10 if I would win and to -10 if the opponent
			// would win and continue if nobody would win
			if (playerWonTemp == null) {
				// opponents turn
				TreeNode child = buildGameTree_recursive(tableTemp, !opponentsTurn, opponent, intent+1);
				child.playedAtColumn = turns.get(i)[1];
				child.playedAtRow = turns.get(i)[0];
				gameTree.addChild(child);
			} else {
				//System.out.println(10.0/intent);
				if (playerWonTemp.equals(this)) {
					tableTemp.scoreIfStateIsReached = (int) (10.0/(intent)) * scoreCoeff;
				} else if (playerWonTemp.equals(PlayerTie)) {
					// its a tie
					tableTemp.scoreIfStateIsReached = (int) (5.0/(intent)) * scoreCoeff;
				} else {
					// opponent wins
					tableTemp.scoreIfStateIsReached = (int) (50.0/(intent)) * scoreCoeff;
				}
				TreeNode childNode = new TreeNode(tableTemp);
				childNode.playedAtColumn = turns.get(i)[1];
				childNode.playedAtRow = turns.get(i)[0];
				gameTree.addChild(childNode);
			}
		}

		return gameTree;
	}
}
