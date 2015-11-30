package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import Model.Player;

public class MyCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -117877613993904379L;

	public MyCellRenderer() {
		super();
		//set the cell alignment to the center
		this.setHorizontalAlignment( JLabel.CENTER );
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (!(((MyJTable) table).getPlayerAt(row, column) == null)) {
			if (((MyJTable) table).getPlayerAt(row, column).equals(Player.Player1)) {
				cell.setBackground(MyJTable.player1Color);
				cell.setForeground(MyJTable.player1ForeColor);
			} else if (((MyJTable) table).getPlayerAt(row, column).equals(Player.Player2)) {
				cell.setBackground(MyJTable.player2Color);
				cell.setForeground(MyJTable.player2ForeColor);
			}
		} else {
			cell.setBackground(MyJTable.playerNoneColor);
			cell.setForeground(MyJTable.playerNoneForeColor);
		}

		return cell;
	}

}
