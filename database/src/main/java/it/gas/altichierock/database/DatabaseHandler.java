package it.gas.altichierock.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHandler {
	private static DatabaseHandler INSTANCE;
	private Logger log = LoggerFactory.getLogger(DatabaseHandler.class);
	
	private EntityManager em;
	private EntityTransaction tx;
	
	public static DatabaseHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DatabaseHandler();
		return INSTANCE;
	}
	
	private DatabaseHandler() {
		//TODO total shit!
		//retrieve the ip of the server
		Properties p = new Properties();
		try {
			File f = new File("altichierock.properties");
			log.debug(f.getCanonicalPath());
			p.load(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			//no problem, we default to localhost
			log.error("File not found, defaulting!", e);
			p.put("database.ip", "localhost");
		} catch (IOException e) {
			log.error("There was a problem reading the properties.", e);
			p.put("database.ip", "localhost");
		}
		//connect
		Map<String, String> map = new HashMap<String, String>();
		map.put("javax.persistence.jdbc.url", "jdbc:derby://" + p.getProperty("database.ip") + ":1527/altichierock;create=true");
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
