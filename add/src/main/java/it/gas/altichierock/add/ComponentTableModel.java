package it.gas.altichierock.add;

import it.gas.altichierock.database.entities.Component;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ComponentTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private List<Component> l;
	
	public ComponentTableModel(List<Component> l) {
		super();
		this.l = l;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
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
			return "description";
		case 1:
			return "price";
		default:
			return "NaN";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return Float.class;
		case 2:
			return Integer.class; //hidden id
		default:
			return Object.class;
		}
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (l == null)
			return "";
		switch (arg1) {
		case 0:
			return l.get(arg0).getDescription();
		case 1:
			return l.get(arg0).getPrice();
		case 2:
			return l.get(arg0).getId(); //hidden id
		default:
			return "";
		}
	}

}
