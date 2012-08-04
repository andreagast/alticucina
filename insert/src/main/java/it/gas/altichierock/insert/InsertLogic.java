package it.gas.altichierock.insert;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.Item;
import it.gas.altichierock.database.OrderTicket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
	
	public void save(OrderTicket o) {
		handler.getTransaction().begin();
		EntityManager em = handler.getEntityManager();
		//retrieve the highest id used
		TypedQuery<Integer> q = em.createQuery("SELECT MAX(i.id) FROM OrderTicket i WHERE i.created = CURRENT_DATE", Integer.class);
		int max = q.getSingleResult();
		System.out.println("MAX->" + max);
		o.getId().setId(max+1);
		//persist every child
		for (int i = 0; i < o.getDetail().size(); i++)
			em.persist(o.getDetail().get(i));
		//save and commit
		em.persist(o);
		handler.getTransaction().commit();
	}
	
}
