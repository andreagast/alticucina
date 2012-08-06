package it.gas.altichierock.database;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> map = new HashMap<String, String>();
		map.put("javax.persistence.jdbc.url", "jdbc:derby://localhost:1527/altichierock;create=true");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("altichierockPU", map);
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
