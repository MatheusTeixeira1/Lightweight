package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UsuarioDAO;
import dao.PublicacaoDAO;
import entidades.Login;
import entidades.Publicacao;
import entidades.Usuario;

@ViewScoped
@ManagedBean(name = "publicacaoBean")
public class PublicacaoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Publicacao publicacao;
	private Publicacao publicacaoEscolhida;
	private List<Publicacao> publicacoes = new ArrayList<Publicacao>();
	
	PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	private Part foto;

	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	Login login = (Login) session.getAttribute("usuario");

	private String viewEscolhida = "Criar";
	
	public PublicacaoBean() {
		setPublicacao(new Publicacao());
	}

	// Navegação ---------------------------------------------------------
	
	public void navegarParaCriar() {
		setViewEscolhida("Criar");
		limparPublicacao();
		limparPublicacaoEscolhida();
	}
	
	public void navegarParaDetalhes(Publicacao p) {
		setPublicacaoEscolhida(p);
		setViewEscolhida("Detalhes");
	}
	
	
	// Data ------------------------------------------------------------
	public void apagarPublicacao() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("--------------1--------------");
		if(login.isEfuncionario() && login.isGerenciarFeed()) {
			System.out.println("--------------2--------------");
			publicacaoDAO.deletarPublicacao(publicacaoEscolhida);
			System.out.println("--------------3--------------");
			context.addMessage(null, new FacesMessage("Publicação feita!"));
			System.out.println("--------------4--------------");
			setViewEscolhida("Criar");
		}else {
			context.addMessage(null, new FacesMessage("Você não possui permissão para isso"));
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		System.out.println("--------------5--------------");
		carregarPublicacoes();
		System.out.println("--------------6--------------");
	}
	
	public String cirarPublicacao() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(login.isEfuncionario() && login.isGerenciarFeed()) {
			salvarFoto();
			publicacao.setCriador(usuarioDAO.pesquisarPorApelido(login.getApelido()));
			
			final LocalDateTime dataPublicacao = LocalDateTime.now();
			publicacao.setDataCriacao(dataPublicacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			
			publicacaoDAO.criarPublicacao(publicacao);
			carregarPublicacoes();
			limparPublicacao();
			context.addMessage(null, new FacesMessage("Publicação feita!"));
		}else {
			context.addMessage(null, new FacesMessage("Você não possui permissão para isso"));
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/funcionario/CriarPublicacao.jsf?faces-redirect=true";
	}

	@PostConstruct
	public void carregarPublicacoes() {
		limparPublicacoes();
		setPublicacoes(publicacaoDAO.listaDePublicacoes());
	}

	
	
	
	
	
	
	
	
	
	// Utilitarios
	public void limparPublicacaoEscolhida() {
		publicacaoEscolhida = new Publicacao();
	}
	
	public void limparPublicacoes() {
		publicacoes = new ArrayList<Publicacao>();
	}

	public void limparPublicacao() {
		publicacao = new Publicacao();
	}

	public void salvarFoto() {
		try (InputStream is = foto.getInputStream()) {
			String originalFileName = foto.getSubmittedFileName();
			String randomFileName = generateRandomFileName(originalFileName);
			Path targetPath = new File("C:\\imagens\\imagens-feed", randomFileName).toPath();
			Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
			publicacao.setConteudoMidia(randomFileName);
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

	
	
	
	
	
	
	
	
	
	
	// Getters e setters
	public Part getFile() {
		return foto;
	}

	public void setFile(Part file) {
		this.foto = file;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public List<Publicacao> getPublicacoes() {
		return publicacoes;
	}

	public void setPublicacoes(List<Publicacao> publicacoes) {
		this.publicacoes = publicacoes;
	}

	public Publicacao getPublicacaoEscolhida() {
		return publicacaoEscolhida;
	}

	public void setPublicacaoEscolhida(Publicacao publicacaoEscolhida) {
		this.publicacaoEscolhida = publicacaoEscolhida;
	}

	public String getViewEscolhida() {
		return viewEscolhida;
	}

	public void setViewEscolhida(String viewEscolhida) {
		this.viewEscolhida = viewEscolhida;
	}
	
}
