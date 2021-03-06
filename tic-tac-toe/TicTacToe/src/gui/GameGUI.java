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
import java.awt.event.ActionListener;
import java.awt.Toolkit;

/**
 * The Game window
 * 
 * @author The WindowBuilder
 * @author Frederik Kammel
 */
public class GameGUI {

	private JFrame frmTicTacToe;
	/**
	 * the GameJTable of the GUI
	 */
	public GameJTable gameTable;
	private MyTableModel model;
	private Player playerForNextTurn;
	private Player opponentOfPlayerForNextTurn;
	private JLabel turnLabel;
	private WelcomeGUI caller;

	@Deprecated
	public boolean gameFinished = false;
	private JLabel lblThinking;
	private JLabel lblSpacing2;

	private boolean guiLocked = false;
	private JButton btnThinkForMe;
	private JLabel labelSpacing1;
	
	/**
	 * Launch the GameGUI window. ATTENTION: It is highly recommended to launch
	 * the WelcomeGUI or the Main-class since launching GameGUI directly will
	 * bypass the Main Menu
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GameGUI();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the GameGUI window. Same as GameGUI(null)
	 */
	public GameGUI() {
		this(null);
	}

	/**
	 * Creates the GameGUI window and reopens the WelcomeGUI after the game ends
	 * if caller object is not null.
	 * 
	 * @param caller
	 *            The handle of the calling WelcomeGUI window
	 */
	@SuppressWarnings("deprecation")
	public GameGUI(WelcomeGUI caller) {
		// Validate the config
		Config.validate();

		// initialize the tie Player
		Player.PlayerTie = new Player("tie");

		this.caller = caller;

		if (Player.Player1 == null || Player.Player2 == null) {
			if (Player.initPlayers() == false) {
				this.quitGame();
			}
		}

		playerForNextTurn = Player.Player1;
		initialize();
		setTurnLabel();
		this.frmTicTacToe.setVisible(true);

		// Let the AI do her first turn if Player1 is AI
		if (playerForNextTurn.isAi == true) {
			//Player player, GameJTable currentGameTable,int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI, Player opponent
			MySwingWorker SWorker = new MySwingWorker(playerForNextTurn, gameTable,this, opponentOfPlayerForNextTurn);
			SWorker.doInBackground();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTicTacToe = new JFrame();
		frmTicTacToe.setIconImage(Toolkit.getDefaultToolkit().getImage(GameGUI.class.getResource("/gui/images/tic.png")));
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

		btnThinkForMe = new JButton("Think for me");
		btnThinkForMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thinkForMe();
			}
		});

		labelSpacing1 = new JLabel("   ");
		toolBar.add(labelSpacing1);
		toolBar.add(btnThinkForMe);

		lblSpacing2 = new JLabel("   ");
		toolBar.add(lblSpacing2);

		lblThinking = new JLabel("Thinking...");
		toolBar.add(lblThinking);
		lblThinking.setVisible(false);

		gameTable = new GameJTable();
		gameTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				int column = gameTable.columnAtPoint(event.getPoint());
				int row = gameTable.rowAtPoint(event.getPoint());
				if (guiLocked == false) {
					playerPlayed(row, column);
				}
			}
		});

		// Generate empty data to set the table dimensions
		// Object[][] data = new Object[][] { { null, null, null }, { null,
		// null, null }, { null, null, null }, };
		Object[][] data = new Object[Config.gameRowCount][Config.gameColumnCount];
		String[] columnHeaders = new String[Config.gameColumnCount];

		for (int c = 0; c < Config.gameColumnCount; c++) {
			columnHeaders[c] = "" + (c + 1);
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
		if (Config.doNotSwitchPlayerAfterGame == false) {
			Player.switchPlayers();
		}

		if (caller == null) {
			// No caller specified, we shall quit the app completely
			System.exit(0);
		} else {
			// There is a welcome screen specified
			if (frmTicTacToe != null) {
				frmTicTacToe.dispose();
				gameTable = null;
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
		boolean res = gameTable.playerPlayed(row, column, playerForNextTurn);

		if (res == true) {
			// set the next player for turning
			if (playerForNextTurn.equals(Player.Player1)) {
				playerForNextTurn = Player.Player2;
				opponentOfPlayerForNextTurn = Player.Player1;
			} else {
				playerForNextTurn = Player.Player1;
				opponentOfPlayerForNextTurn = Player.Player2;
			}
		}
		setTurnLabel();
		
		// Check for win
		Player winningPlayer = gameTable.winDetector(row, column, true);
		if (!(winningPlayer == null)) {
			if (winningPlayer.equals(Player.PlayerTie)) {
				JOptionPane.showMessageDialog(null, "It's a tie!", "Tie", JOptionPane.OK_CANCEL_OPTION);
			} else {
				JOptionPane.showMessageDialog(null, winningPlayer.name + " won! Grats :)", "Player won",
						JOptionPane.OK_CANCEL_OPTION);
			}

			quitGame();
		}

		// Do AI turns if selected
		if (playerForNextTurn.isAi == true) {
			//Player player, GameJTable currentGameTable,int lastPlayedAtRow, int lastPlayedAtColumn, GameGUI callerGUI, Player opponent
			MySwingWorker SWorker = new MySwingWorker(playerForNextTurn, gameTable,this, opponentOfPlayerForNextTurn);
			SWorker.doInBackground();
		}
	}

	private void thinkForMe() {
		if (guiLocked == false) {
			MySwingWorker SWorker = new MySwingWorker(playerForNextTurn, gameTable,this, opponentOfPlayerForNextTurn);
			SWorker.doInBackground();
		}
	}

	private void setTurnLabel() {
		if (turnLabel != null) {
			turnLabel.setText("It's " + playerForNextTurn.name + "'s turn");
		}
	}

	/**
	 * Locks the GUI for user input. Useful while AI calculates its turn.
	 */
	public void lockUI() {
		guiLocked = true;
		lblThinking.setVisible(true);
	}

	/**
	 * Unlocks the GUI again so that the user can do input.
	 */
	public void unlockUI() {
		guiLocked = false;
		lblThinking.setVisible(false);
	}
}
