package Model;

import javax.swing.JOptionPane;

public class Player {
	public static Player Player1;
	public static Player Player2;
	public static int playerCount;

	public String name;

	public Player(String name) {
		playerCount = playerCount + 1;

		if (name.equals("")) {
			this.name = "Player " + playerCount;
		} else {
			this.name = name;
		}
	}

	public static boolean initPlayers() {
		// returns false if the player wishes to cancel the game

		// reset the PlayerCount
		playerCount = 0;

		// Ask for Player 1's name
		String namePlayer1 = JOptionPane.showInputDialog("Player " + (playerCount + 1) + ", please enter your name", "Player " + (playerCount + 1));

		// Check if the player clicked Cancel
		if (namePlayer1 == null) {
			return false;
		}

		Player1 = new Player(namePlayer1);

		// Ask for Player 2's name
		String namePlayer2 = JOptionPane.showInputDialog("Player " + (playerCount + 1) + ", please enter your name", "Player " + (playerCount + 1));

		// Check if the player clicked Cancel
		if (namePlayer2 == null) {
			return false;
		}

		Player2 = new Player(namePlayer2);

		// We only arrive here if no cancel button was clicked
		return true;
	}
}
