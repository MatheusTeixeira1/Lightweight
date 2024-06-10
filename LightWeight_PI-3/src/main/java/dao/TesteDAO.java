package dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Teste;




public class TesteDAO {
	public List<Teste> pesquisarTodos() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Teste> usuarios = entityManager.createQuery("FROM Teste", Teste.class).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return usuarios;
		
	}
}
