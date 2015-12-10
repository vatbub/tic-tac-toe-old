/**
 * Model of a Player
 * 
 * @author Frederik Kammel
 */

package model;

import javax.swing.JOptionPane;

public class Player {
	public static Player Player1;
	public static Player Player2;
	public static Player PlayerTie;
	public static int playerCount;

	public String name;

	/**
	 * Initializes a new Player.
	 * @param name Name of the Player
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
	public static void switchPlayers(){
		//Switches Player1 and Player 2
		Player player1Copy=Player1;
		Player player2Copy=Player2;
		
		Player2=player1Copy;
		Player1=player2Copy;
	}

	/**
	 * Guides the user through the name selection.
	 * @return Returns true, if all dialogs were completed successfully and false if the user cancelled the operation
	 */
	public static boolean initPlayers() {
		// returns false if the player wishes to cancel the game

		// reset the PlayerCount
		playerCount = 0;

		// Ask for Player 1's name
		String namePlayer1;
		
		if (Player1==null){
			namePlayer1="Player " + (playerCount + 1);
		} else if (Player1.name==""){
			namePlayer1="Player " + (playerCount + 1);
		}else{
			namePlayer1=Player1.name;
		}
		
		// String namePlayer2=(String)JOptionPane.showInputDialog(null, "Message", "Title", JOptionPane.INFORMATION_MESSAGE, null, null, "DefaultValue");
		namePlayer1=(String)JOptionPane.showInputDialog(null, "Player " + (playerCount + 1) + ", please enter your name", "Playernames", JOptionPane.INFORMATION_MESSAGE, null, null, namePlayer1);

		// Check if the player clicked Cancel
		if (namePlayer1 == null) {
			return false;
		}

		Player1 = new Player(namePlayer1);

		// Ask for Player 2's name
		String namePlayer2;
		
		if (Player2==null){
			namePlayer2="Player " + (playerCount + 1);
		} else if (Player2.name==""){
			namePlayer2="Player " + (playerCount + 1);
		}else{
			namePlayer2=Player2.name;
		}
		// String namePlayer2=(String)JOptionPane.showInputDialog(null, "Message", "Title", JOptionPane.INFORMATION_MESSAGE, null, null, "DefaultValue");
		namePlayer2=(String)JOptionPane.showInputDialog(null, "Player " + (playerCount + 1) + ", please enter your name", "Playernames", JOptionPane.INFORMATION_MESSAGE, null, null, namePlayer2);

		// Check if the player clicked Cancel
		if (namePlayer2 == null) {
			return false;
		}

		Player2 = new Player(namePlayer2);

		// We only arrive here if no cancel button was clicked
		PlayerTie=new Player("tie");
		return true;
	}
}
