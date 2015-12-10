/**
 * Extends the default JTable to add the PlayerModel to it
 * @author Frederik Kammel
 */

package gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import model.*;

public class GameJTable extends JTable {
	private static final long serialVersionUID = -1925175781596366195L;

	public GameJTable() {
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm) {
		super(dm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	public GameJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		super.setDefaultRenderer(String.class, new MyCellRenderer());
	}

	/**
	 * Set the cell contet of the specified cell to the players String and Color
	 * @param row The row of the cell that will be changed
	 * @param column The column of the cell that will be changed
	 * @param player The player that should be set at the specified position
	 */
	public void setPlayerAt(int row, int column, Player player) {
		if (player.equals(Player.Player1)) {
			//this.getCellRenderer(row, column).getTableCellRendererComponent(this, this.getValueAt(row, column),
			//		this.isCellSelected(row, column), this.hasFocus(), row, column).setBackground(player1Color);
			
			this.setValueAt(Config.player1String, row, column);
		} else if (player.equals(Player.Player2)) {
			//this.getCellRenderer(row, column).getTableCellRendererComponent(this, this.getValueAt(row, column),
			//		this.isCellSelected(row, column), this.hasFocus(), row, column).setBackground(player2Color);
			this.setValueAt(Config.player2String, row, column);
		} else {
			throw new InvalidPlayerException();
		}
		
		
	}

	/**
	 * Returns the Player at the specified position
	 * @param row
	 * @param column
	 * @return The Player in the specified cell
	 */
	public Player getPlayerAt(int row, int column) {
		String value = (String) this.getValueAt(row, column);
		if (value == null) {
			return null;
		} else {
			if (value.equals(Config.player1String)) {
				return Player.Player1;
			} else if (value.equals(Config.player2String)) {
				return Player.Player2;
			}else{
				return null; //For the warning to disappear
			}
		}
	}
}
