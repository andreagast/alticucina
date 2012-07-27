package it.gas.altichierock.insert;

import javax.swing.table.AbstractTableModel;

public class InsertTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private Container container;
	
	public InsertTableModel(Container c) {
		this.container = c;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Integer.class;
		default:
			return Object.class;
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Description";
		case 1:
			return "Quantity";
		default:
			return "NaN";
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 1)
			return true;
		return false;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return container.getSize();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
		case 0: //description
			return container.getItem(arg0).getDescription();
		case 1: //quantity
			return container.getQuantity(arg0);
		default:
			return new Object();
		}
	}

}
