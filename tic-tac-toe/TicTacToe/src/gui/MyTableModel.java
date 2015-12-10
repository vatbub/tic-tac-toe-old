package gui;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8709111641384632066L;

	public MyTableModel(Object[][] data, String[] columnHeaders) {
		super(data, columnHeaders);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public Class getColumnClass(int col) {
		return String.class; // To force String as datatype in the GUI
	}
}
