package it.gas.altichierock.insert;

import it.gas.altichierock.database.Item;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

public class InsertComboModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;
	private List<Item> items;
	
	public InsertComboModel(List<Item> l) {
		items = l;
		if (items != null && items.size() > 0)
			setSelectedItem(items.get(0));
	}

	@Override
	public Object getElementAt(int index) {
		return items.get(index);
	}
	
	@Override
	public int getSize() {
		if (items == null)
			return 0;
		return items.size();
	}


}
