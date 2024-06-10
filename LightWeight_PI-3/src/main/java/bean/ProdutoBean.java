package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.ProdutoDAO;
import dao.UsuarioDAO;
import entidades.Login;
import entidades.Produto;
import entidades.TamanhoProduto;
import entidades.TipoProduto;

@ViewScoped
@ManagedBean
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	ProdutoDAO produtoDAO = new ProdutoDAO();
	UsuarioDAO uasuarioDAO = new UsuarioDAO();

	private List<Produto> produtos = new ArrayList<Produto>();
	private Produto produtoEscolhido = new Produto();
	private Produto produto = new Produto();
	private Part foto;

	private String viewEscolhida = "Pesquisa";
	private String viewPop="fechar";
	
	private TipoProduto tipoSelecionado;
	private TamanhoProduto tamanhoSelecionado;

	

	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	Login login = (Login) session.getAttribute("usuario");

	public ProdutoBean() {
		setProduto(new Produto());
	}

	// navegação -----------------------------------
	
	
	
	
	
	public void mostrarPopCriar() {
		setViewPop("criar");
	}
	public void fecharPop() {
		setViewPop("fechar");
	}
	
	public void mostrarPopPesquisa() {
		setViewPop("pesquisa");
	}
	
	public void mostrarPopDetalhes(Produto p) {
		setProdutoEscolhido(p);
		setViewPop("detalhes");
	}
	
	
	
	
	public void navegarParaPesquisa() {
		limparProduto();
		limparProdutoEscolhido();
		setViewEscolhida("Pesquisa");
	}

	public void navegarParaCriarProduto() {
		setViewEscolhida("Criar");
	}

	public void navegarParaProdutoEscolhido(Produto p) {
		setViewEscolhida("Detalhes");
		setViewPop("detalhes");
		setProdutoEscolhido(p);
	}

	public void navegarParaEditarProduto() {
		setViewEscolhida("DetalhesEditar");
	}

	// data ----------------------------------------

	public void deletarProduto() {
		if (login.isEfuncionario() && login.isGerenciarProdutos()) {
			produtoDAO.removerPorId(getProdutoEscolhido().getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não possui permissão para isso"));
		}
		carregarProdutos();
		navegarParaPesquisa();

	}

	public void criarProduto() {

		if (login.isEfuncionario() && login.isGerenciarProdutos()) {
			System.out.println("------------1-----------");
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			salvarFoto();
			produto.setDesconto(0);
			produto.setCriador(usuarioDAO.pesquisarPorApelido(login.getApelido()));
			produtoDAO.criarProduto(produto);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!"));
			carregarProdutos();
			navegarParaPesquisa();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não possui permissão para isso"));
			navegarParaPesquisa();
		}

		limparProduto();
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	public void editarProduto() {
		if (login.isEfuncionario() && login.isGerenciarProdutos()) {
			produtoDAO.criarProduto(produtoEscolhido);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sucesso!"));
			carregarProdutos();
			navegarParaPesquisa();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Você não possui permissão para isso"));
			navegarParaPesquisa();
		}

		limparProduto();
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	@PostConstruct
	public void preCarregarProdutos() {
		limparProdutos();
		setProdutos(produtoDAO.pesquisarTodos());
	}

	public void carregarProdutos() {
		limparProdutos();
		if (produto.getNome() == null) {
			setProdutos(produtoDAO.pesquisarTodos());
		} else if (produto.getTipoDoProduto() != null && produto.getTamanhoProduto() != null) {
			System.out.println("tipo e tamanho");
			setProdutos(produtoDAO.pesquisaPorNome(produto.getNome(), produto.getTipoDoProduto(),
					produto.getTamanhoProduto()));
		} else if (produto.getTipoDoProduto() != null) {
			System.out.println("tipo");
			setProdutos(produtoDAO.pesquisaPorNome(produto.getNome(), produto.getTipoDoProduto()));
		} else if (produto.getTamanhoProduto() != null) {
			setProdutos(produtoDAO.pesquisaPorNome(produto.getNome(), produto.getTamanhoProduto()));
		} else {
			setProdutos(produtoDAO.pesquisaPorNome(produto.getNome()));
		}
		limparProduto();
		context.getExternalContext().getFlash().setKeepMessages(true);
	}

	// utilitarios ---------------------------------
	public void limparProduto() {
		setProduto(new Produto());
	}

	public void limparProdutoEscolhido() {
		setProdutoEscolhido(new Produto());
	}

	public void limparProdutos() {
		setProdutos(new ArrayList<Produto>());
	}

	public void salvarFoto() {
		try (InputStream is = foto.getInputStream()) {
			String originalFileName = foto.getSubmittedFileName();
			String randomFileName = generateRandomFileName(originalFileName);
			Path targetPath = new File("C:\\imagens\\imagens-produto", randomFileName).toPath();
			Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
			produto.setFoto(randomFileName);
			System.out.println("Saved file as: " + randomFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generateRandomFileName(String originalFileName) {
		SecureRandom random = new SecureRandom();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		return Long.toHexString(random.nextLong()) + extension;
	}

	// getters e setter ---------------------------------

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public TipoProduto[] getTipoSelecionado() {
		return TipoProduto.values();
	}

	public TamanhoProduto[] getTamanhoSelecionado() {
		return TamanhoProduto.values();
	}

	public void setTipoSelecionado(TipoProduto tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public void setTamanhoSelecionado(TamanhoProduto tamanhoSelecionado) {
		this.tamanhoSelecionado = tamanhoSelecionado;
	}

	public Produto getProdutoEscolhido() {
		return produtoEscolhido;
	}

	public void setProdutoEscolhido(Produto produtoEscolhido) {
		this.produtoEscolhido = produtoEscolhido;
	}

	public String getViewEscolhida() {
		return viewEscolhida;
	}

	public void setViewEscolhida(String viewEscolhida) {
		this.viewEscolhida = viewEscolhida;
	}
	public String getViewPop() {
		return viewPop;
	}

	public void setViewPop(String viewPop) {
		this.viewPop = viewPop;
	}
}
