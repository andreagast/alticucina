package it.gas.altichierock.order;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.entities.Ticket;

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

	public List<Ticket> getNonCompletedOrders() {
		TypedQuery<Ticket> q = em.createNamedQuery("order.notcomplete",
				Ticket.class);
		q.setMaxResults(10); // limit to first 10 results
		return q.getResultList();
	}

	public void markAsCompleted(Ticket t) {
		tx.begin();
		t.setCompleted(true);
		em.merge(t);
		tx.commit();
	}

}
