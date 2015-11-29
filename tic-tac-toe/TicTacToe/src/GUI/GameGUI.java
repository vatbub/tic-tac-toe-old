package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Model.*;

public class GameGUI {

	private JFrame frame;
	private MyJTable gameTable;
	private MyTableModel model;
	private Player playerForNextTurn;
	private JLabel turnLabel;

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
			System.exit(0);
		}

		playerForNextTurn = Player.Player1;
		initialize();
		setTurnLabel();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 224, 136);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.SOUTH);

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
				
				//Check for win
				Player winningPlayer=winDetector();
				if (!(winningPlayer==null)){
					JOptionPane.showMessageDialog(null, "Player " + winningPlayer.name + " won! Grats :)", "Player won", JOptionPane.OK_CANCEL_OPTION);
					System.exit(0);
				}
			}
		});

		// Generate empty data to set the table dimensions
		Object[][] data = new Object[][] { { null, null, null }, { null, null, null }, { null, null, null }, };

		String[] columnHeaders = new String[] { "1", "2", "3" };
		model = new MyTableModel(data, columnHeaders);
		gameTable.setModel(model);

		gameTable.setColumnSelectionAllowed(true);
		gameTable.setCellSelectionEnabled(true);

		frame.getContentPane().add(gameTable, BorderLayout.CENTER);
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
		for (int i = 0; i < 3; i++) {
			if (gameTable.getPlayerAt(2-i, i) == null) {
				notNull = false;
			}
		}

		if (notNull == true) {
			if (gameTable.getPlayerAt(2, 0).equals(gameTable.getPlayerAt(1, 1))
					&& gameTable.getPlayerAt(2,0).equals(gameTable.getPlayerAt(0, 2))) {
				return gameTable.getPlayerAt(2, 0);
			}
		}
		
		//We only arrive here if no one won yet
		return null;
	}
	
	private void setTurnLabel(){
		turnLabel.setText("It's " + playerForNextTurn.name + "'s turn");
	}
}
