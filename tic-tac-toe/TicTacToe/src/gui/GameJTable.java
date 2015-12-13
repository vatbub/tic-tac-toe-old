package gui;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.*;

/**
 * Extends the default JTable to add the PlayerModel to it
 * @author Frederik Kammel
 */
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

	@SuppressWarnings("rawtypes")
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
	 *            The row of the specified cell
	 * @param column
	 *            The column of the specified cell
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
	 * @param player
	 *            The player that did the turn
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
	public Player winDetector(int row, int column) {
		return winDetector(row, column, false);
	}

	/**
	 * Checks if a player has won the game and draws a special line in the GUI
	 * where the player won if desired
	 * 
	 * @param row
	 *            Row in which the last gem was set
	 * @param column
	 *            Column in which the last gem was set
	 * @param updateGUI
	 *            If true, a line will be drawn in the GUI at the spot where the
	 *            player has won
	 * @return Player.Player1 if Player 1 has won, Player.Player2 if Player 2
	 *         has won, Player.PlayerTie if the game is a tie, null if the game
	 *         is not finished yet
	 */
	public Player winDetector(int row, int column, boolean updateGUI) {
		/**
		 * The number of gems a player has set in a line
		 */
		int gemCount = 0;
		Player playerAtPosition = this.getPlayerAt(row, column);

		/**
		 * For drawing the winner line
		 */
		int gemOffset = 0;

		// Go to the left of the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (column - i >= 0) {
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
			if (updateGUI == true) {
				drawWinner(row, column, GUIDirection.left);
			}
			return playerAtPosition;
		} else {
			gemOffset = gemCount - 1;
		}

		// Go to the right of the last gem
		// i=1 since we've already verified the spot where the player set his
		// gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			// Check if the wanted cell is out of the bounds
			if (column + i < this.getColumnCount()) {
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
			if (updateGUI == true) {
				drawWinner(row, column - gemOffset, GUIDirection.right);
			}
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
			gemOffset = 0;
		}

		// Go up from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0) {
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
			if (updateGUI == true) {
				drawWinner(row, column, GUIDirection.up);
			}
			return playerAtPosition;
		} else {
			gemOffset = gemCount - 1;
		}

		// Go down from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount()) {
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
			if (updateGUI == true) {
				drawWinner(row - gemOffset, column, GUIDirection.down);
			}
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemOffset = 0;
			gemCount = 0;
		}

		// Go diagonally up left from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column - i >= 0) {
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
			if (updateGUI == true) {
				drawWinner(row, column, GUIDirection.upleft);
			}
			return playerAtPosition;
		} else {
			gemOffset = gemCount - 1;
		}

		// Go diagonally down right from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount() && column + i < this.getColumnCount()) {
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
			if (updateGUI == true) {
				drawWinner(row - gemOffset, column - gemOffset, GUIDirection.downright);
			}
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemOffset = 0;
			gemCount = 0;
		}

		// Go diagonally up right from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column + i < this.getColumnCount()) {
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
			if (updateGUI == true) {
				drawWinner(row, column, GUIDirection.upright);
			}
			return playerAtPosition;
		} else {
			gemOffset = gemCount - 1;
		}

		// Go diagonally down left from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < this.getRowCount() && column - i >= 0) {
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
			if (updateGUI == true) {
				drawWinner(row - gemOffset, column + gemOffset, GUIDirection.downleft);
			}
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemOffset = 0;
			gemCount = 0;
		}

		// We only arrive here if nobody won or if it is a tie

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

	/**
	 * Checks if this GameJTable is empty
	 * 
	 * @return true if the table is empty
	 */
	public boolean isEmpty() {
		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				if (this.getPlayerAt(r, c) != null) {
					return false;
				}
			}
		}

		// We only arrive here if the table is empty
		return true;
	}

	/**
	 * Checks if this GameJTable is full
	 * 
	 * @return true if the table is full
	 */
	public boolean isFull() {
		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				if (this.getPlayerAt(r, c) == null) {
					return false;
				}
			}
		}

		// We only arrive here if the table is full
		return true;
	}

	/**
	 * Checks if two GameJTables have equal contents.
	 * 
	 * @param gameTable
	 *            The GameJTable this GameJTable should be compared to.
	 * @return true if this GameJTable and gameTable have equal contents
	 */
	public boolean equals(GameJTable gameTable) {
		if (this.getRowCount() == gameTable.getRowCount() && this.getColumnCount() == gameTable.getColumnCount()) {
			for (int r = 0; r < this.getRowCount(); r++) {
				for (int c = 0; c < this.getColumnCount(); c++) {
					if (this.getPlayerAt(r, c) == null && gameTable.getPlayerAt(r, c) != null) {
						return false;
					}
					if (this.getPlayerAt(r, c) != null && gameTable.getPlayerAt(r, c) == null) {
						return false;
					}
					if (this.getPlayerAt(r, c) != null && gameTable.getPlayerAt(r, c) != null) {
						if (!(this.getPlayerAt(r, c).equals(gameTable.getPlayerAt(r, c)))) {
							return false;

						}
					}
				}
			}
		} else {
			return false;
		}

		// we only arrive here if the tables are equal
		return true;
	}

	/**
	 * Draws a line where a player won the game (but does not detect the winner,
	 * use winDetector for detecting the winner)
	 * 
	 * @param rowStart
	 *            The row where the line starts
	 * @param columnStart
	 *            The column where the line starts
	 * @param dir
	 *            The direction of the line
	 */
	private void drawWinner(int rowStart, int columnStart, GUIDirection dir) throws ArrayIndexOutOfBoundsException {
		for (int i = 0; i < Config.gemsToWin; i++) {
			int[] rc = new int[2];
			switch (dir) {
			case down:
				rc[0] = rowStart + i;
				rc[1] = columnStart;
				break;
			case downleft:
				rc[0] = rowStart + i;
				rc[1] = columnStart - i;
				break;
			case downright:
				rc[0] = rowStart + i;
				rc[1] = columnStart + i;
				break;
			case left:
				rc[0] = rowStart;
				rc[1] = columnStart - i;
				break;
			case right:
				rc[0] = rowStart;
				rc[1] = columnStart + i;
				break;
			case up:
				rc[0] = rowStart - i;
				rc[1] = columnStart;
				break;
			case upleft:
				rc[0] = rowStart - i;
				rc[1] = columnStart - i;
				break;
			case upright:
				rc[0] = rowStart - i;
				rc[1] = columnStart + i;
				break;
			}

			((MyCellRenderer) this.getCellRenderer(rowStart, columnStart)).playerWonAt.add(rc);
		}
		
		this.repaint();
	}
}
