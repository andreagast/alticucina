package it.gas.altichierock.add;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.Item;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AddLogic {
	private EntityManager em; 
	
	public AddLogic() {
		em = DatabaseHandler.getInstance().getEntityManager();
	}

	public List<Item> getItems() {
		TypedQuery<Item> q = em.createNamedQuery("item.ordered.all", Item.class);
		return q.getResultList();
	}
	
	public void addItem(String descr, float price) {
		Item i = new Item();
		i.setDescription(descr);
		i.setPrice(price);
		i.setDeprecated(false);
		em.getTransaction().begin();
		em.persist(i);
		em.getTransaction().commit();
	}
	
	public void changeEnabled(int id, boolean b) {
		TypedQuery<Item> q = em.createNamedQuery("item.search", Item.class);
		q.setParameter("id", id);
		Item item = q.getSingleResult();
		item.setDeprecated(! b);
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
	}
	
}
