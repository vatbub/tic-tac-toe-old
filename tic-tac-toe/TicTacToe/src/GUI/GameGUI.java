package GUI;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Model.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameGUI {

	private JFrame frmTicTacToe;
	private MyJTable gameTable;
	private MyTableModel model;
	private Player playerForNextTurn;
	private JLabel turnLabel;
	private WelcomeGUI caller;
	
	public boolean gameFinished=false;

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public GameGUI() {
		if (Player.initPlayers() == false) {
			this.quitGame();
		}

		playerForNextTurn = Player.Player1;
		initialize();
		setTurnLabel();
		this.frmTicTacToe.setVisible(true);
	}
	
	public GameGUI(WelcomeGUI caller){
		this();
		this.caller=caller;
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
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
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
		Object[][] data = new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, };

		String[] columnHeaders = new String[] { "1", "2", "3" };
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
					
					//Set the row height
					gameTable.setRowHeight(perRow);
					
					//Set the font size
					gameTable.setFont(new Font("Serif", Font.BOLD, perRow));
				}
			}
		});

		frmTicTacToe.getContentPane().add(gameTable, BorderLayout.CENTER);
	}
	
	public void quitGame(){
		
		if (caller==null){
			//No caller specified, we shall quit the app completely
			System.exit(0);
		}else{
			//There is a welcome screen specified
			frmTicTacToe.dispose();
			caller.getFrmTicTacToe().setVisible(true);
		}
	}

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
		Player winningPlayer = winDetector();
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

	private void setTurnLabel() {
		turnLabel.setText("It's " + playerForNextTurn.name + "'s turn");
	}
}
