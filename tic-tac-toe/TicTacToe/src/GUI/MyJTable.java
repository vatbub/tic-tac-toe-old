package GUI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import Model.*;

public class MyJTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1925175781596366195L;

	private Color player1Color = Color.red;
	private Color player2Color = Color.black;
	private String player1String = "X";
	private String player2String = "O";

	public MyJTable() {

	}

	public MyJTable(TableModel dm) {
		super(dm);
	}

	public MyJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	public MyJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
	}

	public MyJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
	}

	public MyJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	public MyJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	public void setPlayerAt(int row, int column, Player player) {
		if (player.equals(Player.Player1)) {
			//this.getCellRenderer(row, column).getTableCellRendererComponent(this, this.getValueAt(row, column),
			//		this.isCellSelected(row, column), this.hasFocus(), row, column).setBackground(player1Color);
			this.setValueAt(player1String, row, column);
		} else if (player.equals(Player.Player2)) {
			//this.getCellRenderer(row, column).getTableCellRendererComponent(this, this.getValueAt(row, column),
			//		this.isCellSelected(row, column), this.hasFocus(), row, column).setBackground(player2Color);
			this.setValueAt(player2String, row, column);
		} else {
			throw new InvalidPlayerException();
		}
	}

	public Player getPlayerAt(int row, int column) {
		String value = (String) this.getValueAt(row, column);
		if (value == null) {
			return null;
		} else {
			if (value.equals(player1String)) {
				return Player.Player1;
			} else if (value.equals(player2String)) {
				return Player.Player2;
			}else{
				return null; //For the warning to disappear
			}
		}
	}
}
