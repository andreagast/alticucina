package it.gas.altichierock.display;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.OrderTicket;

public class DisplayLogic {
	private DatabaseHandler dh;
	private EntityManager em;
	
	public DisplayLogic() {
		dh = DatabaseHandler.getInstance();
		em = dh.getEntityManager();
	}
	
	public void markAsDisplayed(OrderTicket t) {
		if (t == null)
			return;
		dh.getTransaction().begin();
		t.setServed(true);
		em.merge(t);
		dh.getTransaction().commit();
	}
	
	public List<OrderTicket> getCompletedNotServed() {
		TypedQuery<OrderTicket> q = em.createNamedQuery("order.notserved", OrderTicket.class);
		return q.getResultList();
	}
	
	
}
