package br.com.fuctura.infrastructura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionFactory {

	public Connection getConnection() {
		Properties props = new DatabaseConfig().loadProperties();

		try {
			return DriverManager.getConnection(props.getProperty("url"), props);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("meuPU");
		return emf;
	}
	
}
