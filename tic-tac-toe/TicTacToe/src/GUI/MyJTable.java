package GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import Model.Main;

public class MyJTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1925175781596366195L;
	
	private MyTableModel model;
	private Color Player1Color=Color.RED;
	private Color Player2Color=Color.GREEN;
	private Color NoPlayerColor=Color.WHITE;

	public MyJTable() {
		// TODO Auto-generated constructor stub
		model=new MyTableModel();
	}

	public MyJTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		model=new MyTableModel(numRows, numColumns);
	}

	public MyJTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		model=new MyTableModel(this.getRowCount(), this.getColumnCount());
	}

	public MyJTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		model=new MyTableModel(this.getRowCount(), this.getColumnCount());
	}
	
	@Override
	public boolean editCellAt(int row,int  column,EventObject e){
		return false;
	}
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		
		Component comp = super.prepareRenderer(renderer, row, col);
		
		if (model.getCellUsedByPlayer(row, col).equals(null)){
			comp.setBackground(NoPlayerColor);
		}else if (model.getCellUsedByPlayer(row, col).equals(Main.Player1)){
			comp.setBackground(Player1Color);
		}else if (model.getCellUsedByPlayer(row, col).equals(Main.Player2)){
			comp.setBackground(Player2Color);
		}
		
		return comp;
	}
	
	@Override 
	public MyTableModel getModel(){
		return model;
	}

}
