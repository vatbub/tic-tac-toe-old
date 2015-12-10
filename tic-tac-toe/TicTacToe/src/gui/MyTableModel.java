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

	public MyTableModel clone() {
		Object data[][] = new String[this.getRowCount()][this.getColumnCount()];

		for (int r = 0; r < this.getRowCount(); r++) {
			for (int c = 0; c < this.getColumnCount(); c++) {
				data[r][c] = this.getValueAt(r, c);
			}
		}

		String[] columnNames = new String[this.getColumnCount()];

		for (int c = 0; c < this.getColumnCount(); c++) {
			columnNames[c] = super.getColumnName(c);
		}
		
		return new MyTableModel(data, columnNames);
	}
}
