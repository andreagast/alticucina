package it.gas.altichierock.add;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.entities.Component;
import it.gas.altichierock.database.entities.Product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AddLogic {
	private EntityManager em; 
	
	public AddLogic() {
		em = DatabaseHandler.getInstance().getEntityManager();
	}

	public List<Product> getProducts() {
		TypedQuery<Product> q = em.createNamedQuery("item.ordered.all", Product.class);
		return q.getResultList();
	}
	
	public Product getProduct(int id) {
		return em.find(Product.class, id);
	}
	
	public void addProduct(String descr, double price) {
		em.getTransaction().begin();
		Product i = new Product();
		i.setDescription(descr);
		i.setBasePrice(price);
		em.persist(i);
		em.getTransaction().commit();
	}
	
	public void removeProduct(int id) {
		em.getTransaction().begin();
		Product p = em.find(Product.class, id);
		if (p != null)
			em.remove(p);
		em.getTransaction().commit();
	}
	
	public void editProduct(Product p) {
		em.getTransaction().begin();
		em.merge(p);
		em.getTransaction().commit();
	}
	
	public void refreshProduct(Product p) {
		em.getTransaction().begin();
		em.refresh(p);
		em.getTransaction().commit();
	}
	
	public void addComponent(Product p, String descr, double price) {
		em.getTransaction().begin();
		Component c = new Component();
		c.setDescription(descr);
		c.setPrice(price);
		em.persist(c);
		p.getComponents().add(c);
		em.merge(p);
		em.getTransaction().commit();
	}
	
	public void editComponent(Component e) {
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
	}
	
	public void removeComponent(int id) {
		em.getTransaction().begin();
		Component c = em.find(Component.class, id);
		if (c != null)
			em.remove(c);
		em.getTransaction().commit();
	}
	
}
