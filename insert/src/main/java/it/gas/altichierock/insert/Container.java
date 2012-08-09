package it.gas.altichierock.insert;

import it.gas.altichierock.database.Item;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Container {
	private Logger log = LoggerFactory.getLogger(Container.class);
	private List<Record> records;
	
	public Container() {
		reset();
	}
	
	public void addRecord(Item i) {
		log.debug("addRecord: {}", i);
		Record r = new Record();
		r.setItem(i);
		r.setQuantity(1);
		records.add(r);
	}
	
	public void setQuantity(int pos, int qty) {
		records.get(pos).setQuantity(qty);
	}
	
	public int getSize() {
		return records.size();
	}
	
	public Item getItem(int pos) {
		return records.get(pos).getItem();
	}
	
	public int getQuantity(int pos) {
		return records.get(pos).getQuantity();
	}
	
	public void reset() {
		records = new ArrayList<Record>();
	}
	
	public void remove(int pos) {
		records.remove(pos);
	}
	
	private class Record {
		private Item item;
		private int quantity;
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	}
	
	public float getSubtotal() {
		float price = 0;
		for (int i = 0; i < records.size(); i++) {
			Record r = records.get(i);
			float sub = r.getItem().getPrice() * r.getQuantity();
			price += sub;
		}
		return price;
	}
}
