package dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Publicacao;

public class PublicacaoDAO implements Serializable{
	private static final long serialVersionUID = 1L;

	public void criarPublicacao(Publicacao publicacao) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(publicacao);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
	}

	public List<Publicacao> listaDePublicacoes() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Publicacao> listaProdutos = entityManager.createQuery("FROM Publicacao ORDER BY id DESC", Publicacao.class)
				.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return listaProdutos;
	}

	
	
	
	
	
	public void editarPublicacao() {

	}

	public void deletarPublicacao(Publicacao publicacao) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Publicacao pessoaDelecao = entityManager.find(Publicacao.class, publicacao.getId());

		entityManager.getTransaction().begin();
		entityManager.remove(pessoaDelecao);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Excluídos com Sucesso :)"));

	}
}
