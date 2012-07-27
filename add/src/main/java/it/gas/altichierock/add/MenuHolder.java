package it.gas.altichierock.add;

import it.gas.altichierock.database.Item;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

public class MenuHolder {
	private List<Item> items;
	private AddLogic logic;
	private AddTableModel model;
	
	public MenuHolder() {
		items = new ArrayList<Item>();
		logic = new AddLogic();
		model = new AddTableModel();
	}
	
	public synchronized int size() {
		return items.size();
	}
	
	public synchronized Item getItemAt(int pos) {
		return items.get(pos);
	}
	
	public synchronized void refresh() {
		items = logic.getItems();
		model.setData(items);
		model.fireTableDataChanged();
	}
	
	public synchronized void addItem(String descr, float price) {
		logic.addItem(descr, price);
		refresh();
	}
	
	public synchronized void changeEnabled(int id, boolean enabled) {
		logic.changeEnabled(id, enabled);
	}
	
	public TableModel getModel() {
		return model;
	}
}
