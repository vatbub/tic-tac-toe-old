package model;

import java.awt.Color;

/**
 * The game config
 */
public class Config {
	/**
	 * Number of rows in the game
	 */
	public static int gameRowCount = 3;
	/**
	 * Number of columns in the game
	 */
	public static int gameColumnCount = 3;

	/**
	 * Number of gems a player needs to place next to each other to win
	 */
	public static int gemsToWin = 3;

	/**
	 * Intent after the game-tree is cut while the AI computes its next turn.
	 * Set this to 0 (zero) to build the full tree. Set this to 1 to make the AI
	 * completely random (not smart at all)
	 */
	public static int cutGameTreeAtIntent = 5;

	public static String defaultPlayer1Name = "Player 1";
	public static String defaultPlayer2Name = "Player 2";
	public static String defaultAI1Name = "Computer 1";
	public static String defaultAI2Name = "Computer 2";

	/**
	 * Background color of cells occupied by Player1
	 */
	public static Color player1Color = Color.red;
	/**
	 * Foreground color of cells occupied by Player1
	 */
	public static Color player1ForeColor = Color.black;
	/**
	 * Background color of cells occupied by Player2
	 */
	public static Color player2Color = Color.black;
	/**
	 * Foreground color of cells occupied by Player2
	 */
	public static Color player2ForeColor = Color.white;
	/**
	 * Background color of cells that are not occupied
	 */
	public static Color playerNoneColor = Color.white;
	/**
	 * Foreground color of cells that are not occupied
	 */
	public static Color playerNoneForeColor = Color.black;

	/**
	 * Background color of cells where a player has won
	 */
	public static Color playerWonColor = Color.yellow;
	/**
	 * Foreground color of cells where a player has won
	 */
	public static Color playerWonForeColor = Color.black;

	/**
	 * String written into cells occupied by Player1
	 */
	public static String player1String = "X";
	/**
	 * String written into cells occupied by Player2
	 */
	public static String player2String = "O";
	
	/**
	 * Specifies if the players should switch positions after each game
	 */
	public static boolean doNotSwitchPlayerAfterGame=true;

	/**
	 * Validates the config and throws exceptions or warnings if the game is configured improperly
	 */
	public static void validate() {
		if (gemsToWin > Math.max(gameRowCount, gameColumnCount)) {
			throw new InvalidConfigException(
					"The player cannot win the game since gemsToWin is bigger than all game table dimensions");
		}

		if (player1String == "") {
			throw new InvalidConfigException("player1String is an empty string");
		}

		if (player2String == "") {
			throw new InvalidConfigException("player2String is an empty string");
		}

		if (cutGameTreeAtIntent < 0) {
			throw new InvalidConfigException("cutGameTreeAtIntent must not be negative");
		}

		if (cutGameTreeAtIntent == 1) {
			System.out.println(
					"WARNING in Config: With cutGameTreeAtIntent=1, the AI will make random turns.\nIt is recommended to set cutGameTreeAtIntent at least to 2");
		}
	}
}
