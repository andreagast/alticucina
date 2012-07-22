package it.gas.altichierock.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DatabaseHandler {
	private static DatabaseHandler INSTANCE;
	
	private EntityManager em;
	private EntityTransaction tx;
	
	public static DatabaseHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DatabaseHandler();
		return INSTANCE;
	}
	
	private DatabaseHandler() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("altichierockPU");
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	public EntityTransaction getTransaction() {
		return tx;
	}
	
}
