/**
 * The Main Menu
 * @author Frederik Kammel
 */
package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import model.Config;
import model.MyActionListener;
import model.Player;

import javax.swing.JButton;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class WelcomeGUI {

	private JComboBox<String> comboBoxPlayer1;
	private JComboBox<String> comboBoxPlayer2;
	private JFrame frmTicTacToe;
	private JTextField player1Name;
	private JTextField player2Name;
	private String player1NameTemp;
	private String player2NameTemp;

	/**
	 * Launch the Main Menu.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeGUI window = new WelcomeGUI();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the Main Menu.
	 */
	public WelcomeGUI() {
		initialize();
		this.frmTicTacToe.setVisible(true);
	}

	public JFrame getFrmTicTacToe() {
		return frmTicTacToe;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTicTacToe = new JFrame();
		frmTicTacToe.addComponentListener(new ComponentAdapter() {
			@Override
			/**
			 * Get the player names form the player objects if they exist
			 */
			public void componentShown(ComponentEvent arg0) {
				if (Player.Player1 != null) {
					player1Name.setText(Player.Player1.name);
					if (Player.Player1.isAi) {
						// it's an AI
						comboBoxPlayer1.setSelectedIndex(1);
					} else {
						// it's a human
						comboBoxPlayer1.setSelectedIndex(0);
					}
				}
				if (Player.Player2 != null) {
					player2Name.setText(Player.Player2.name);
					if (Player.Player2.isAi) {
						// it's an AI
						comboBoxPlayer2.setSelectedIndex(1);
					} else {
						// it's a human
						comboBoxPlayer2.setSelectedIndex(0);
					}
				}
			}
		});
		frmTicTacToe.setTitle("Tic Tac Toe (Frederik Kammel)");
		frmTicTacToe.setBounds(100, 100, 450, 202);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(new BorderLayout(0, 0));

		JButton btnStartGame = new JButton("Start game");
		btnStartGame.addActionListener(new MyActionListener<WelcomeGUI>(this) {
			public void actionPerformed(ActionEvent arg0) {
				// set the player params
				Player.Player1 = new Player(caller.player1Name.getText());
				Player.Player2 = new Player(caller.player2Name.getText());

				// Activate/deactivate the ai
				Player.Player1.isAi = (caller.comboBoxPlayer1.getSelectedIndex() == 1);
				Player.Player2.isAi = (caller.comboBoxPlayer2.getSelectedIndex() == 1);

				// start the game
				frmTicTacToe.setVisible(false);
				GameGUI game = new GameGUI(caller);
			}
		});
		frmTicTacToe.getContentPane().add(btnStartGame, BorderLayout.SOUTH);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmTicTacToe.getContentPane().add(splitPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		comboBoxPlayer1 = new JComboBox<String>();
		comboBoxPlayer1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange()==1){
					//Selected an object
					if (comboBoxPlayer1.getSelectedIndex() == 0) {
						// Human selected
						player1Name.setEnabled(true);

						if (player1NameTemp != "") {
							player1Name.setText(player1NameTemp);
						} else {
							player1Name.setText(Config.defaultPlayer1Name);
						}
					} else {
						// Computer selected
						player1NameTemp = player1Name.getText();
						player1Name.setEnabled(false);
						player1Name.setText(Config.defaultAI1Name);
					}
				}
			}
		});

		comboBoxPlayer1.setModel(new DefaultComboBoxModel<String>(new String[] { "Human", "Computer" }));
		panel.add(comboBoxPlayer1);

		JLabel lblName_1 = new JLabel("Name:");
		panel.add(lblName_1);

		player1Name = new JTextField();
		player1Name.setText(Config.defaultPlayer1Name);
		panel.add(player1Name);
		player1Name.setColumns(10);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		comboBoxPlayer2 = new JComboBox<String>();
		comboBoxPlayer2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==1){
					//Selected an object
					if (comboBoxPlayer2.getSelectedIndex() == 0) {
						// Human selected
						player2Name.setEnabled(true);

						if (player2NameTemp != "") {
							player2Name.setText(player2NameTemp);
						} else {
							player2Name.setText(Config.defaultPlayer2Name);
						}
					} else {
						// Computer selected
						player2NameTemp = player2Name.getText();
						player2Name.setEnabled(false);
						player2Name.setText(Config.defaultAI2Name);
					}
				}
			}
		});

		comboBoxPlayer2.setModel(new DefaultComboBoxModel<String>(new String[] { "Human", "Computer" }));
		panel_1.add(comboBoxPlayer2);

		JLabel lblName = new JLabel("Name:");
		panel_1.add(lblName);

		player2Name = new JTextField();
		player2Name.setText(Config.defaultPlayer2Name);
		panel_1.add(player2Name);
		player2Name.setColumns(10);
	}
}
