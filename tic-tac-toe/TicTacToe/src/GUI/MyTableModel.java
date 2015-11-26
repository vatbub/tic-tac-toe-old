package GUI;


import java.util.Vector;
import Model.MyArrayList;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709111641384632066L;
	
	private MyArrayList cellUsedByPlayerList;

	public MyTableModel() {
		super();
		cellUsedByPlayerList=new MyArrayList();
	}

	public MyTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
		cellUsedByPlayerList=new MyArrayList(rowCount, columnCount);
	}

	public MyTableModel(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
		// TODO Auto-generated constructor stub
	}

	public MyTableModel(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
		cellUsedByPlayerList=new MyArrayList(rowCount, this.getColumnCount());
	}

	public MyTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
		cellUsedByPlayerList=new MyArrayList(this.getRowCount(), this.getColumnCount());
	}

	public MyTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		cellUsedByPlayerList=new MyArrayList(this.getRowCount(), this.getColumnCount());
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
	public void addColumn(Object columnName) {
		super.addColumn(columnName);
		cellUsedByPlayerList.addColumn();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void addRow(Object [] rowData){
		super.addRow(rowData);
		cellUsedByPlayerList.addRow();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void addRow(Vector rowData){
		super.addRow(rowData);
		cellUsedByPlayerList.addRow();
		this.fireTableStructureChanged();
	}
	
	
	
	
	public boolean isCellUsedByPlayer(int row, int column){
		if (cellUsedByPlayerList.getPlayerAt(row, column)==null){
			return false;
		}else{
			return true;
		}
	}
	
	public Model.Player getCellUsedByPlayer(int row, int column){
		return cellUsedByPlayerList.getPlayerAt(row, column);
	}
	
	public void setCellUsedByPlayer(Model.Player value, int row, int col){
		cellUsedByPlayerList.setPlayerAt(value, row, col);
	}
}
