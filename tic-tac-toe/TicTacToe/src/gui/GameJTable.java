/**
 * Extends the default JTable to add the PlayerModel to it
 * @author Frederik Kammel
 */

package gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.*;

public class GameJTable extends JTable {
	private static final long serialVersionUID = -1925175781596366195L;

	public int scoreIfStateIsReached;

	public GameJTable() {
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm) {
		super(dm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	/**
	 * Set the cell contet of the specified cell to the players String and Color
	 * 
	 * @param row
	 *            The row of the cell that will be changed
	 * @param column
	 *            The column of the cell that will be changed
	 * @param player
	 *            The player that should be set at the specified position
	 */
	public void setPlayerAt(int row, int column, Player player) {
		if (player == null) {
			this.setValueAt("", row, column);
		} else if (player.equals(Player.Player1)) {
			// this.getCellRenderer(row,
			// column).getTableCellRendererComponent(this, this.getValueAt(row,
			// column),
			// this.isCellSelected(row, column), this.hasFocus(), row,
			// column).setBackground(player1Color);

			this.setValueAt(Config.player1String, row, column);
		} else if (player.equals(Player.Player2)) {
			// this.getCellRenderer(row,
			// column).getTableCellRendererComponent(this, this.getValueAt(row,
			// column),
			// this.isCellSelected(row, column), this.hasFocus(), row,
			// column).setBackground(player2Color);
			this.setValueAt(Config.player2String, row, column);
		} else {
			throw new InvalidPlayerException();
		}

	}

	/**
	 * Returns the Player at the specified position
	 * 
	 * @param row
	 * @param column
	 * @return The Player in the specified cell
	 */
	public Player getPlayerAt(int row, int column) {
		String value = (String) this.getValueAt(row, column);
		if (value == null) {
			return null;
		} else {
			if (value.equals(Config.player1String)) {
				return Player.Player1;
			} else if (value.equals(Config.player2String)) {
				return Player.Player2;
			} else {
				return null; // For the warning to disappear
			}
		}
	}

	/**
	 * Sets the symbol and color for the player who just played in the JTable of
	 * the GameGUI. Also checks if a player won the game and shows the
	 * win-message and quits the game then.
	 * 
	 * @param row
	 *            Row where the player played
	 * @param column
	 *            Column where the player played.
	 * @return Returns whether the player was set successfully
	 */
	public boolean playerPlayed(int row, int column, Player player) {
		if (this.getPlayerAt(row, column) == null) {
			// Draw the corresponding field
			this.setPlayerAt(row, column, player);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a player has won the game
	 * 
	 * @param row
	 *            Row in which the last gem was set
	 * @param column
	 *            Column in which the last gem was set
	 * @return Player.Player1 if Player 1 has won, Player.Player2 if Player 2
	 *         has won, Player.PlayerTie if the game is a tie, null if the game
	 *         is not finished yet
	 */
	public Player winDetector(int row, int column, String caller) {
		int gemCount = 0;
		Player playerAtPosition = this.getPlayerAt(row, column);
		Player playerTemp;

		// Go to the left of the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (column - i >= 0) {
				playerTemp = this.getPlayerAt(row, column - i);
				if (this.getPlayerAt(row, column - i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go to the right of the last gem
		// i=1 since we've already verified the spot where the player set his
		// gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			// Check if the wanted cell is out of the bounds
			if (column + i < this.getColumnCount()) {
				playerTemp = this.getPlayerAt(row, column + i);
				if (this.getPlayerAt(row, column + i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go up from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0) {
				playerTemp = this.getPlayerAt(row - i, column);
				if (this.getPlayerAt(row - i, column) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go down from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount()) {
				playerTemp = this.getPlayerAt(row + i, column);
				if (this.getPlayerAt(row + i, column) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go diagonally up left from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column - i >= 0) {
				playerTemp = this.getPlayerAt(row - i, column - i);
				if (this.getPlayerAt(row - i, column - i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go diagonally down right from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount() && column + i < this.getColumnCount()) {
				playerTemp = this.getPlayerAt(row + i, column + i);
				if (this.getPlayerAt(row + i, column + i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go diagonally up right from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column + i < this.getColumnCount()) {
				playerTemp = this.getPlayerAt(row - i, column + i);
				if (this.getPlayerAt(row - i, column + i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go diagonally down left from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount() && column - i >= 0) {
				playerTemp = this.getPlayerAt(row + i, column - i);
				if (this.getPlayerAt(row + i, column - i) == playerAtPosition) {
					gemCount++;
					if (gemCount >= Config.gemsToWin) {
						break;
					}
				} else {
					// its a different player
					// exit for
					break;
				}
			} else {
				// Its out of the bounds
				// exit for
				break;
			}
		}

		if (gemCount >= Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// We only arrive here if nobody won or if it is a tie
		/*boolean isTie = true;

		// Check if it is a tie
		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				if (this.getPlayerAt(r, c) == null) {
					isTie = false;
					break;
				}
			}
		}
*/
		//if (isTie == true) {
		if (isFull() == true) {
			return Player.PlayerTie;
		} else {
			// Nobody won yet and it's no tie
			return null;
		}
	}

	public GameJTable clone() {
		// clone the TableModel
		MyTableModel model = ((MyTableModel) this.getModel()).clone();

		// MyTableModel model=new MyTableModel();

		GameJTable res = new GameJTable(model);
		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				res.setPlayerAt(r, c, this.getPlayerAt(r, c));
			}
		}

		return res;
	}

	/**
	 * negate the scoreIfStateIsReached value
	 * 
	 * @return the negated scoreIfStateIsReached.
	 */
	public int invertScoreIfStateIsReached() {
		scoreIfStateIsReached = -scoreIfStateIsReached;
		return scoreIfStateIsReached;
	}

	/**
	 * Prints the gameTable to a String for debug purposes
	 * 
	 * @return The gameTable as a String
	 */
	public String toString() {
		String res = "";

		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				if (this.getPlayerAt(r, c) == null) {
					res = res + " ";
				} else if (this.getPlayerAt(r, c).equals(Player.Player1)) {
					res = res + Config.player1String;
				} else if (this.getPlayerAt(r, c).equals(Player.Player2)) {
					res = res + Config.player2String;
				}
			}

			res = res + "\n";

		}

		res = res + "===end of table===";
		return res;
	}
	
	public boolean isEmpty(){
		boolean res=true;
		for (int r=0;r<this.getRowCount();r++){
			for (int c=0;c<this.getColumnCount();c++){
				if (this.getPlayerAt(r, c)!=null){
					res=false;
					break;
				}
			}
			
			//break this loop too
			if (res==false){
				break;
			}
		}
		
		return res;
	}
	
	public boolean isFull(){
		boolean res=true;
		for (int r=0;r<this.getRowCount();r++){
			for (int c=0;c<this.getColumnCount();c++){
				if (this.getPlayerAt(r, c)==null){
					res=false;
					break;
				}
			}
			
			//break this loop too
			if (res==false){
				break;
			}
		}
		
		return res;
	}
}
