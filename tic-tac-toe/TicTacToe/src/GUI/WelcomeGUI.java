/**
 * The Main Menu
 * @author Frederik Kammel
 */
package GUI;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import Model.MyActionListener;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.*;

public class WelcomeGUI {

	private JFrame frmTicTacToe;
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
	
	public JFrame getFrmTicTacToe(){
		return frmTicTacToe;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTicTacToe = new JFrame();
		frmTicTacToe.setTitle("Tic Tac Toe (Frederik Kammel)");
		frmTicTacToe.setBounds(100, 100, 450, 202);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(new BoxLayout(frmTicTacToe.getContentPane(), BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmTicTacToe.getContentPane().add(splitPane);
		splitPane.setResizeWeight(0.5);
		splitPane.setEnabled(false);

		JButton button = new JButton("Singleplayer");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,
						"Please use Multiplayer for right now, since the AI is not yet implemented.",
						"AI not implemented yet", JOptionPane.OK_CANCEL_OPTION);
			}
		});
		splitPane.setLeftComponent(button);

		JButton button_1 = new JButton("Multiplayer (2 Players)");
		button_1.addActionListener(new MyActionListener<WelcomeGUI>(this) {
			public void actionPerformed(ActionEvent arg0) {
				frmTicTacToe.setVisible(false);
				GameGUI game = new GameGUI(caller);
			}
		});
		splitPane.setRightComponent(button_1);
	}
}
