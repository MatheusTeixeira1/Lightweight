package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import entidades.Produto;
import entidades.TamanhoProduto;
import entidades.TipoProduto;
import entidades.Usuario;

public class ProdutoDAO {
	public void criarProduto(Produto produto) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		entityManager.merge(produto);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Gravados com Sucesso :)"));
	}

	public Produto atualizarProduto(Produto produto) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		// fazer validadação antes do merge
		Produto produtoRetorno = entityManager.merge(produto);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Atualizados com Sucesso :)"));
		return produtoRetorno;
	}

	public List<Produto> pesquisarTodos() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Produto> produto = entityManager.createQuery("FROM Produto", Produto.class).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return produto;

	}

	public Produto pesquisarPorId(int id) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Produto produto = entityManager.find(Produto.class, id);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return produto;
	}

	public List<Produto> pesquisaPorNome(String pesquisa) {
		List<Produto> retorno;

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TypedQuery<Produto> query = entityManager.createQuery("FROM Produto WHERE nome LIKE :pesquisa", Produto.class);
		query.setParameter("pesquisa", "%" + pesquisa + "%");

		retorno = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

		if (retorno.isEmpty()) {
			retorno = pesquisaPelaDescicao(pesquisa);
		}

		return retorno;
	}

	public List<Produto> pesquisaPorNome(String pesquisa, TipoProduto tipoProduto, TamanhoProduto tamanhoProduto) {
		List<Produto> retorno;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TypedQuery<Produto> query = entityManager.createQuery(
				"FROM Produto WHERE nome LIKE :pesquisa AND tamanhoProduto = :tamanhoProduto AND tipoDoProduto = :tipoProduto",
				Produto.class);
		query.setParameter("pesquisa", "%" + pesquisa + "%");
		query.setParameter("tamanhoProduto", tamanhoProduto);
		query.setParameter("tipoProduto", tipoProduto); 

		retorno = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

		if (retorno.isEmpty()) {
			retorno = pesquisaPorNome(pesquisa);
		}

		return retorno;
	}

	public List<Produto> pesquisaPorNome(String pesquisa, TipoProduto tipoProduto) {
		List<Produto> retorno;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TypedQuery<Produto> query = entityManager.createQuery(
				"FROM Produto WHERE nome LIKE :pesquisa AND tipoDoProduto = :tipoProduto",
				Produto.class);
		query.setParameter("pesquisa", "%" + pesquisa + "%");
		query.setParameter("tipoProduto", tipoProduto); 

		retorno = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

		if (retorno.isEmpty()) {
			retorno = pesquisaPorNome(pesquisa);
		}

		return retorno;
	}

	public List<Produto> pesquisaPorNome(String pesquisa, TamanhoProduto tamanhoProduto) {
		List<Produto> retorno;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TypedQuery<Produto> query = entityManager.createQuery(
				"FROM Produto WHERE nome LIKE :pesquisa AND tamanhoProduto = :tamanhoProduto",
				Produto.class);
		query.setParameter("pesquisa", "%" + pesquisa + "%");
		query.setParameter("tamanhoProduto", tamanhoProduto);

		retorno = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

		if (retorno.isEmpty()) {
			retorno = pesquisaPorNome(pesquisa);
		}

		return retorno;
	}

	public List<Produto> pesquisaPelaDescicao(String pesquisa) {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		TypedQuery<Produto> query = entityManager.createQuery("FROM Produto WHERE descricao LIKE :pesquisa",
				Produto.class);
		query.setParameter("pesquisa", "%" + pesquisa + "%");

		List<Produto> retorno = query.getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		context.addMessage(null, new FacesMessage("Pesquisa não encontrada, mostrando produtos relacionados"));
		return retorno;
	}

	public void removerPorId(int id) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Produto produtoDelecao = entityManager.find(Produto.class, id);

		entityManager.getTransaction().begin();
		entityManager.remove(produtoDelecao);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Excluídos com Sucesso :)"));
	}
}
