package it.gas.altichierock.insert;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.Item;
import it.gas.altichierock.database.OrderTicket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertLogic {
	private Logger log = LoggerFactory.getLogger(InsertLogic.class);
	private List<Item> menu;
	private DatabaseHandler handler;
	
	public InsertLogic() {
		menu = new ArrayList<Item>();
		handler = DatabaseHandler.getInstance();
	}
	
	public void refresh() {
		TypedQuery<Item> q = handler.getEntityManager().createNamedQuery("item.ordered.enabled", Item.class);
		menu = q.getResultList();
	}
	
	public List<Item> getMenu() {
		return menu;
	}
	
	public void save(OrderTicket o) {
		handler.getTransaction().begin();
		EntityManager em = handler.getEntityManager();
		//retrieve the highest id used
		TypedQuery<Integer> q = em.createNamedQuery("order.maxidtoday", Integer.class);
		Integer max = q.getSingleResult();
		if (max == null) //first order of the day!
			max = -1;
		log.debug("MAX->{}", max);
		o.getId().setId(max+1);
		//persist every child
		for (int i = 0; i < o.getDetail().size(); i++)
			em.persist(o.getDetail().get(i));
		//save and commit
		em.persist(o);
		handler.getTransaction().commit();
	}
	
}
