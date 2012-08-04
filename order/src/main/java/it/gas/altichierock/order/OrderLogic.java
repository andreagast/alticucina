package it.gas.altichierock.order;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.OrderTicket;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class OrderLogic {
	private DatabaseHandler dh;
	private EntityManager em;
	private EntityTransaction tx;
	
	public OrderLogic() {
		dh = DatabaseHandler.getInstance();
		em = dh.getEntityManager();
		tx = dh.getTransaction();
	}
	
	public List<OrderTicket> getNonCompletedOrders() {
		TypedQuery<OrderTicket> q = em.createNamedQuery("order.notcomplete", OrderTicket.class);
		return q.getResultList();
	}
	
	public void markAsCompleted(OrderTicket t) {
		tx.begin();
		t.setCompleted(true);
		em.merge(t);
		tx.commit();
	}

}
