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
		TypedQuery<Item> q = em.createQuery("SELECT i FROM Item i ORDER BY i.id", Item.class);
		return q.getResultList();
	}
	
	public void addItem(String descr, float price) {
		Item i = new Item();
		i.setDescription(descr);
		i.setPrice(price);
		em.getTransaction().begin();
		em.persist(i);
		em.getTransaction().commit();
	}
	
	public void changeEnabled(int id, boolean b) {
		TypedQuery<Item> q = em.createQuery("SELECT i FROM Item i WHERE i.id = ?1", Item.class);
		q.setParameter(1, id);
		Item item = q.getSingleResult();
		item.setDeprecated(b);
		em.getTransaction().begin();
		em.merge(item);
		em.getTransaction().commit();
	}
	
}