package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameGUI {

	private JFrame frame;
	private MyJTable gameTable;
	private MyTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI window = new GameGUI();
					window.frame.setVisible(true);
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Model.Main.Player1=new Model.Player();
				((MyTableModel) gameTable.getModel()).setCellUsedByPlayer(Model.Main.Player1, 0, 0);
			}
		});
		toolBar.add(btnQuit);
		
		JLabel turnLabel = new JLabel("It's Payer A's turn");
		toolBar.add(turnLabel);
		
		gameTable = new MyJTable();
		gameTable.setModel(new MyTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		
		gameTable.setColumnSelectionAllowed(true);
		gameTable.setCellSelectionEnabled(true);
		
		model=(MyTableModel) gameTable.getModel();
		
		model.addColumn("1");
		//model.addRow(new Object[]{"1"});
		//model.addRow(new Object[]{"2"});
		model.addColumn("2");
		
		
		frame.getContentPane().add(gameTable, BorderLayout.CENTER);
	}

}
