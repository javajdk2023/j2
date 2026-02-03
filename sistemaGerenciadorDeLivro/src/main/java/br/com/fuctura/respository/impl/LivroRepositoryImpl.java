package br.com.fuctura.respository.impl;

import java.sql.SQLException;
import java.util.List;

import br.com.fuctura.domain.Livro;
import br.com.fuctura.infrastructura.ConnectionFactory;
import br.com.fuctura.respository.LivroRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class LivroRepositoryImpl implements LivroRepository {

	@Override
	public void cadastrar(Livro livro) throws SQLException {
		ConnectionFactory fabrica = new ConnectionFactory();
		
		EntityManagerFactory emf = fabrica.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(livro); 
		em.getTransaction().commit();
	}

	@Override
	public List<Livro> buscarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
