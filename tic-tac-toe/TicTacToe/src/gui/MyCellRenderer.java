package gui;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import model.*;

/**
 * Overrides javax.swing.table.DefaultTableCellRenderer to add the possibility
 * to color the cells for each player
 * 
 * @author Frederik Kammel
 */
public class MyCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -117877613993904379L;

	/**
	 * Contains all rows and columns where a player has won so that it can be
	 * rendered in the GUI
	 */
	public MyCellArrayList playerWonAt = new MyCellArrayList();

	public MyCellRenderer() {
		super();
		// set the cell alignment to the center
		this.setHorizontalAlignment(JLabel.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		// Make an array of row and column for checking if playerWonAt contains
		// that cell
		int[] rc = { row, column };
		
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (playerWonAt.contains(rc)) {
			cell.setBackground(Config.playerWonColor);
			cell.setForeground(Config.playerWonForeColor);
		} else if (!(((GameJTable) table).getPlayerAt(row, column) == null)) {
			if (((GameJTable) table).getPlayerAt(row, column).equals(Player.Player1)) {
				cell.setBackground(Config.player1Color);
				cell.setForeground(Config.player1ForeColor);
			} else if (((GameJTable) table).getPlayerAt(row, column).equals(Player.Player2)) {
				cell.setBackground(Config.player2Color);
				cell.setForeground(Config.player2ForeColor);
			}
		} else {
			cell.setBackground(Config.playerNoneColor);
			cell.setForeground(Config.playerNoneForeColor);
		}

		return cell;
	}
}
