package dao;

import java.util.ArrayList;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entidades.Usuario;


public class UsuarioDAO {
	
	public void criarUsuario(Usuario usuario) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();		
		
		entityManager.persist(usuario);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Gravados com Sucesso :)"));		
	}
	
	
	
	public Usuario atualizarUsuario(Usuario usuario) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();		
		
		//fazer validadação antes do merge
		Usuario pessoaRetorno = entityManager.merge(usuario);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Atualizados com Sucesso :)"));
        return pessoaRetorno;
	}

	
	
	public List<Usuario> pesquisarTodos() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		List<Usuario> usuarios = entityManager.createQuery("FROM Usuario", Usuario.class).getResultList();

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return usuarios;
		
	}
	
	
	
	public Usuario pesquisarPorId(int id) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		Usuario usuario = entityManager.find(Usuario.class, id);
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return usuario;
	}
	
	
	
	public Usuario pesquisarPorApelido(String apelido) {
		Usuario retorno;
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	
		
		TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE apelido LIKE :apelido", Usuario.class);
		query.setParameter("apelido", apelido);
		
		retorno = query.getSingleResult();
		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		
		return retorno;
	}

	public boolean existeUsuarioComApelido(String apelido) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();

	    TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(apelido) FROM Usuario WHERE apelido LIKE :nome", Long.class);
	    query.setParameter("nome", apelido);

	    Long count = query.getSingleResult();

	    entityManager.getTransaction().commit();
	    entityManager.close();
	    entityManagerFactory.close();

	    return count > 0;
	}

	public boolean validacaoApelidoSenha(String apelido, String senha) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();

	    TypedQuery<Long> query = entityManager.createQuery("SELECT count(apelido) FROM Usuario WHERE apelido = :apelido AND senha = :senha", Long.class);
	    query.setParameter("apelido", apelido);
	    query.setParameter("senha", senha);
	    Long count = query.getSingleResult();

	    entityManager.getTransaction().commit();
	    entityManager.close();
	    entityManagerFactory.close();

	    return count > 0;
	}
	public boolean isFuncionario(String apelido) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
	    EntityManager entityManager = entityManagerFactory.createEntityManager();
	    entityManager.getTransaction().begin();
	    TypedQuery<Long> query = entityManager.createQuery(
	    	    "SELECT SUM(CASE WHEN efuncionario = true THEN 1 ELSE 0 END) FROM Usuario WHERE apelido = :apelido", Long.class);
	    	query.setParameter("apelido", apelido);
	    	Long count = query.getSingleResult();

	    entityManager.getTransaction().commit();
	    entityManager.close();
	    entityManagerFactory.close();
	    return count > 0;
	}
	
	public List<Boolean> buscarPermissoes(String apelido){
		List<Boolean> permicoes = new ArrayList<>();
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	

		TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario WHERE apelido = :apelido", Usuario.class);

		query.setParameter("apelido", apelido);
		Usuario usuario = query.getSingleResult();

		
		permicoes.add(usuario.getPermissoes().isGerenciarCliente());
		permicoes.add(usuario.getPermissoes().isGerenciarFeed());
		permicoes.add(usuario.getPermissoes().isGerenciarFuncionario());
		permicoes.add(usuario.getPermissoes().isGerenciarProdutos());

		
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
		return permicoes;
	}
	
	public void removerPorId(int id) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Lightweight");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		 
		Usuario pessoaDelecao = entityManager.find(Usuario.class, id);
		entityManager.getTransaction().begin();		
		entityManager.remove(pessoaDelecao);
		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dados Excluídos com Sucesso :)"));		
	}
}
