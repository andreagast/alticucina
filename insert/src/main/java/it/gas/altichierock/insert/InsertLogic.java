package it.gas.altichierock.insert;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.Item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

public class InsertLogic {
	private List<Item> menu;
	private DatabaseHandler handler;
	
	public InsertLogic() {
		menu = new ArrayList<Item>();
		handler = DatabaseHandler.getInstance();
	}
	
	public void refresh() {
		TypedQuery<Item> q = handler.getEntityManager().createQuery("SELECT i FROM Item i ORDER BY i.id", Item.class);
		menu = q.getResultList();
	}
	
	public List<Item> getMenu() {
		return menu;
	}
	
}
