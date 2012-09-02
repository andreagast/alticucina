package it.gas.altichierock.insert;

import javax.swing.table.AbstractTableModel;

public class ReceiptTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private ReceiptKeeper keeper;
	
	public ReceiptTableModel(ReceiptKeeper rk) {
		this.keeper = rk;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return keeper.getSize();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			if (keeper.getQuantity(row) == 0)
				return null;
			return keeper.getQuantity(row);
		case 1:
			return keeper.getDescription(row);
		case 2:
			if (keeper.getPrice(row) == 0)
				return null;
			return keeper.getPrice(row);
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return Double.class;
		default:
			return super.getColumnClass(columnIndex);
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "quantity";
		case 1:
			return "description";
		case 2:
			return "price";
		default:
			return super.getColumnName(column);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (keeper.getQuantity(rowIndex) == 0)
				return false;
			return true;
		case 1:
			return true;
		case 2:
			if (keeper.getPrice(rowIndex) == 0)
				return false;
			return true;
		default:
			return false;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			keeper.setQuantity(rowIndex, (Integer) aValue);
			break;
		case 1:
			keeper.setDescription(rowIndex, (String) aValue);
			break;
		case 2:
			keeper.setPrice(rowIndex, (Float) aValue);
			break;
		default:
			//do nada
		}
	}
	
	

}
