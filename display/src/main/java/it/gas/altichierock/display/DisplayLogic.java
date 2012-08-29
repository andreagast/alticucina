package it.gas.altichierock.display;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.gas.altichierock.database.DatabaseHandler;
import it.gas.altichierock.database.entities.Ticket;

public class DisplayLogic {
	private DatabaseHandler dh;
	private EntityManager em;

	public DisplayLogic() {
		dh = DatabaseHandler.getInstance();
		em = dh.getEntityManager();
	}

	public void markAsDisplayed(Ticket t) {
		if (t == null)
			return;
		dh.getTransaction().begin();
		t.setServed(true);
		em.merge(t);
		dh.getTransaction().commit();
	}

	public List<Ticket> getCompletedNotServed() {
		TypedQuery<Ticket> q = em.createNamedQuery("order.notserved",
				Ticket.class);
		return q.getResultList();
	}

}
