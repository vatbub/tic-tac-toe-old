package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.*;

public class WelcomeGUI {

	private JFrame frame;
	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public WelcomeGUI() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
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
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				GameGUI game = new GameGUI();
			}
		});
		splitPane.setRightComponent(button_1);
	}
}
