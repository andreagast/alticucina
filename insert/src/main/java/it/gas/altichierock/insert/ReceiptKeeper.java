package it.gas.altichierock.insert;

import java.util.ArrayList;
import java.util.List;

public class ReceiptKeeper {
	private List<Row> list;
	private ReceiptTableModel model;
	
	public ReceiptKeeper() {
		list = new ArrayList<Row>();
		model = new ReceiptTableModel(this);
	}
	
	public void add(int qty, String descr, float price) {
		Row r = new Row();
		r.quantity = qty;
		r.description = descr;
		r.price = price;
		list.add(r);
		model.fireTableDataChanged();
	}
	
	public int getSize() {
		return list.size();
	}
	
	public int getQuantity(int row) {
		return list.get(row).quantity;
	}
	
	public void setQuantity(int row, int qty) {
		list.get(row).quantity = qty;
		model.fireTableDataChanged();
	}
	
	public String getDescription(int row) {
		return list.get(row).description;
	}
	
	public void setDescription(int row, String descr) {
		list.get(row).description = descr;
		model.fireTableDataChanged();
	}
	
	public float getPrice(int row) {
		return list.get(row).price;
	}
	
	public void setPrice(int row, float price) {
		list.get(row).price = price;
		model.fireTableDataChanged();
	}
	
	public ReceiptTableModel getModel() {
		return model;
	}

	public void clear() {
		list.clear();
		model.fireTableDataChanged();
	}
	
	private class Row {
		private int quantity;
		private String description;
		private float price;
	}
}
