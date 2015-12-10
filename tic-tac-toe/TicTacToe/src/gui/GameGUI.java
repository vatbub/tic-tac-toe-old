/**
 * The Game window
 * @author The WindowBuilder
 * @author Frederik Kammel
 */

package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JToolBar;

import model.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class GameGUI {

	private JFrame frmTicTacToe;
	private MyJTable gameTable;
	private MyTableModel model;
	private Player playerForNextTurn;
	private JLabel turnLabel;
	private WelcomeGUI caller;

	public boolean gameFinished = false;

	/**
	 * Launch the GameGUI window. ATTENTION: It is highly recommended to launch
	 * the WelcomeGUi or the Main-class since lainching GameGUI directly will
	 * bypass the Main Menu
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the GameGUI window.
	 */
	public GameGUI() {
		this(null);
	}

	public GameGUI(WelcomeGUI caller) {
		//Validate the config
		Config.validate();
		
		this.caller = caller;

		if (Player.initPlayers() == false) {
			this.quitGame();
		}

		playerForNextTurn = Player.Player1;
		initialize();
		setTurnLabel();
		this.frmTicTacToe.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTicTacToe = new JFrame();
		frmTicTacToe.addWindowListener(new MyWindowAdapter(this) {
			@Override
			public void windowClosing(WindowEvent e) {
				caller.quitGame();
			}
		});
		frmTicTacToe.setTitle("Tic Tac Toe (Frederik Kammel)");
		frmTicTacToe.setBounds(100, 100, 482, 349);
		frmTicTacToe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		frmTicTacToe.getContentPane().add(toolBar, BorderLayout.SOUTH);

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new MyActionListener<GameGUI>(this) {
			public void actionPerformed(ActionEvent e) {
				caller.quitGame();
			}
		});
		toolBar.add(btnQuit);

		turnLabel = new JLabel("It's Payer A's turn");
		toolBar.add(turnLabel);

		gameTable = new MyJTable();
		gameTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				int column = gameTable.columnAtPoint(event.getPoint());
				int row = gameTable.rowAtPoint(event.getPoint());

				playerPlayed(row, column);
			}
		});

		// Generate empty data to set the table dimensions
		// Object[][] data = new Object[][] { { null, null, null }, { null,
		// null, null }, { null, null, null }, };
		Object[][] data = new Object[Config.gameRowCount][Config.gameColumnCount];
		String[] columnHeaders = new String[Config.gameColumnCount];

		for (int c = 0; c < Config.gameColumnCount; c++) {
			columnHeaders[c]="" + (c+1);
			for (int r = 0; r < Config.gameRowCount; r++) {
				data[r][c] = null;
			}
		}

		// String[] columnHeaders = new String[] { "1", "2", "3" };

		model = new MyTableModel(data, columnHeaders);
		gameTable.setModel(model);

		gameTable.setColumnSelectionAllowed(true);
		gameTable.setCellSelectionEnabled(true);

		// Make the rows fit the window
		gameTable.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				gameTable.setRowHeight(16);
				Dimension p = gameTable.getPreferredSize();
				Dimension v = gameTable.getSize();
				if (v.height > p.height) {
					int available = v.height - gameTable.getRowCount() * gameTable.getRowMargin();
					int perRow = available / gameTable.getRowCount();

					// Set the row height
					gameTable.setRowHeight(perRow);

					// Set the font size
					gameTable.setFont(new Font("Serif", Font.BOLD, perRow));
				}
			}
		});

		frmTicTacToe.getContentPane().add(gameTable, BorderLayout.CENTER);
	}

	/**
	 * Quits the current game and returns to the calling window (normally the
	 * WelcomeGUI) if applicable
	 */
	public void quitGame() {
		// switch the Players
		Player.switchPlayers();

		if (caller == null) {
			// No caller specified, we shall quit the app completely
			System.exit(0);
		} else {
			// There is a welcome screen specified
			if (frmTicTacToe != null) {
				frmTicTacToe.dispose();
			}
			caller.getFrmTicTacToe().setVisible(true);
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
	 */
	public void playerPlayed(int row, int column) {
		if (gameTable.getPlayerAt(row, column) == null) {
			// Draw the corresponding field
			gameTable.setPlayerAt(row, column, playerForNextTurn);

			// set the next player for turning
			if (playerForNextTurn.equals(Player.Player1)) {
				playerForNextTurn = Player.Player2;
			} else {
				playerForNextTurn = Player.Player1;
			}

			setTurnLabel();
		}

		// Check for win
		Player winningPlayer = winDetector2(row, column);
		if (!(winningPlayer == null)) {
			if (winningPlayer.equals(Player.PlayerTie)) {
				JOptionPane.showMessageDialog(null, "It's a tie!", "Tie", JOptionPane.OK_CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(null, winningPlayer.name + " won! Grats :)", "Player won",
						JOptionPane.OK_CANCEL_OPTION);
			}

			quitGame();
		}
	}

	/**
	 * Checks if a player has won the game
	 * 
	 * @return Player.Player1 if Player 1 has won, Player.Player2 if Player 2
	 *         has won, Player.PlayerTie if the game is a tie, null if the game
	 *         is not finished yet
	 */
	private Player winDetector() {
		// Check for 3 crosses in each line
		for (int i = 0; i < 3; i++) {
			if (!(gameTable.getPlayerAt(i, 0) == null) && !(gameTable.getPlayerAt(i, 1) == null)
					&& !(gameTable.getPlayerAt(i, 2) == null)) {
				if (gameTable.getPlayerAt(i, 0).equals(gameTable.getPlayerAt(i, 1))
						&& gameTable.getPlayerAt(i, 0).equals(gameTable.getPlayerAt(i, 2))) {
					// The Player won in one line
					return gameTable.getPlayerAt(i, 0);
				}
			}
		}

		// Check for 3 crosses in each column
		for (int i = 0; i < 3; i++) {
			if (!(gameTable.getPlayerAt(0, i) == null) && !(gameTable.getPlayerAt(1, i) == null)
					&& !(gameTable.getPlayerAt(2, i) == null)) {
				if (gameTable.getPlayerAt(0, i).equals(gameTable.getPlayerAt(1, i))
						&& gameTable.getPlayerAt(0, i).equals(gameTable.getPlayerAt(2, i))) {
					// The Player won in one line
					return gameTable.getPlayerAt(0, i);
				}
			}
		}

		// Check for diagonal wins
		boolean notNull = true;
		// Check if the diagonal (from left top to right bottom) is not null
		for (int i = 0; i < 3; i++) {
			if (gameTable.getPlayerAt(i, i) == null) {
				notNull = false;
			}
		}

		if (notNull == true) {
			if (gameTable.getPlayerAt(0, 0).equals(gameTable.getPlayerAt(1, 1))
					&& gameTable.getPlayerAt(0, 0).equals(gameTable.getPlayerAt(2, 2))) {
				return gameTable.getPlayerAt(0, 0);
			}
		}

		// Check if the diagonal (from left bottom to right top) is not null
		notNull = true;
		for (int i = 0; i < 3; i++) {
			if (gameTable.getPlayerAt(2 - i, i) == null) {
				notNull = false;
			}
		}

		if (notNull == true) {
			if (gameTable.getPlayerAt(2, 0).equals(gameTable.getPlayerAt(1, 1))
					&& gameTable.getPlayerAt(2, 0).equals(gameTable.getPlayerAt(0, 2))) {
				return gameTable.getPlayerAt(2, 0);
			}
		}

		// We only arrive here if no one won yet

		boolean isTie = true;

		// Check if it is a tie
		for (int r = 0; r < gameTable.getRowCount(); r++) {
			for (int c = 0; c < gameTable.getColumnCount(); c++) {
				if (gameTable.getPlayerAt(r, c) == null) {
					isTie = false;
					break;
				}
			}
		}

		if (isTie == true) {
			return Player.PlayerTie;
		} else {
			// Nobody won yet and it's no tie
			return null;
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
	public Player winDetector2(int row, int column) {
		int gemCount = 0;
		Player playerAtPosition = gameTable.getPlayerAt(row, column);
		Player playerTemp;

		// Go to the left of the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (column - i >= 0) {
				playerTemp = gameTable.getPlayerAt(row, column - i);
				if (gameTable.getPlayerAt(row, column - i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go to the right of the last gem
		// i=1 since we've already verified the spot where the player set his
		// gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			// Check if the wanted cell is out of the bounds
			if (column + i < gameTable.getColumnCount()) {
				playerTemp = gameTable.getPlayerAt(row, column + i);
				if (gameTable.getPlayerAt(row, column + i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go up from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0) {
				playerTemp = gameTable.getPlayerAt(row - i, column);
				if (gameTable.getPlayerAt(row - i, column) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go down from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < gameTable.getRowCount()) {
				playerTemp = gameTable.getPlayerAt(row + i, column);
				if (gameTable.getPlayerAt(row + i, column) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go diagonally up left from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column - i >= 0) {
				playerTemp = gameTable.getPlayerAt(row - i, column - i);
				if (gameTable.getPlayerAt(row - i, column - i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go diagonally down right from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < gameTable.getRowCount() && column + i < gameTable.getColumnCount()) {
				playerTemp = gameTable.getPlayerAt(row + i, column + i);
				if (gameTable.getPlayerAt(row + i, column + i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// Go diagonally up right from the last gem
		for (int i = 0; i < Config.gemsToWin; i++) {
			if (row - i >= 0 && column + i < gameTable.getColumnCount()) {
				playerTemp = gameTable.getPlayerAt(row - i, column + i);
				if (gameTable.getPlayerAt(row - i, column + i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		}

		// Go diagonally down left from the last gem
		for (int i = 1; i < Config.gemsToWin; i++) {
			if (row + i < gameTable.getRowCount() && column - i >= 0) {
				playerTemp = gameTable.getPlayerAt(row + i, column - i);
				if (gameTable.getPlayerAt(row + i, column - i) == playerAtPosition) {
					gemCount++;
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

		if (gemCount == Config.gemsToWin) {
			return playerAtPosition;
		} else {
			// Reset the gemCount
			gemCount = 0;
		}

		// We only arrive here if nobody won or if it is a tie
		boolean isTie = true;

		// Check if it is a tie
		for (int r = 0; r < gameTable.getRowCount(); r++) {
			for (int c = 0; c < gameTable.getColumnCount(); c++) {
				if (gameTable.getPlayerAt(r, c) == null) {
					isTie = false;
					break;
				}
			}
		}

		if (isTie == true) {
			return Player.PlayerTie;
		} else {
			// Nobody won yet and it's no tie
			return null;
		}
	}

	private void setTurnLabel() {
		if (turnLabel != null) {
			turnLabel.setText("It's " + playerForNextTurn.name + "'s turn");
		}
	}
}
