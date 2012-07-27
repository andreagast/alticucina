package it.gas.altichierock.add;

import it.gas.altichierock.database.Item;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AddTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Item> l;
	
	public AddTableModel() {
		super();
		l = new ArrayList<Item>();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		if (l != null)
			return l.size();
		return 0;
	}

	@Override
	public String getColumnName(int arg0) {
		switch (arg0) {
		case 0:
			return "id";
		case 1:
			return "description";
		case 2:
			return "price";
		default:
			return "NaN";
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
			return Float.class;
		case 3:
			return Boolean.class;
		default:
			return Object.class;
		}
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		switch (arg1) {
		case 0:
			if (l != null)
				return l.get(arg0).getId();
			return 0;
		case 1:
			if (l != null)
				return l.get(arg0).getDescription();
			return "lol";
		case 2:
			if (l != null)
				return l.get(arg0).getPrice();
			return 0;
		case 3:
			return l.get(arg0).isDeprecated();
		default:
			return "";
		}
	}
	
	public void setData(List<Item> l) {
		this.l = l;
		fireTableDataChanged();
	}

}
