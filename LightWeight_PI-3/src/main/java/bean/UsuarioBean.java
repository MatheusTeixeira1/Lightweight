package bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UsuarioDAO;
import entidades.Login;
import entidades.Permissoes;
import entidades.Usuario;

@ViewScoped
@ManagedBean(name = "funcionarioBean")
public class UsuarioBean {
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	public Usuario usuario;
	public Permissoes permissoes;
	private Part foto;

	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	Login login = (Login) session.getAttribute("usuario");

	public UsuarioBean() {
		setUsuario(new Usuario());
		setPermissoes(new Permissoes());
	}
	
	public String inserirFuncionario() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (login.isEfuncionario() && login.isGerenciarFuncionario()) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			if (usuarioDAO.existeUsuarioComApelido(usuario.getApelido())) {
				context.addMessage(null, new FacesMessage("Apelido já existe"));
				System.out.println("Apelido já existente");
			} else {
				usuario.setPermissoes(permissoes);
				salvarFoto();
				usuario.setCriador(usuarioDAO.pesquisarPorApelido(login.getApelido()));
				usuarioDAO.criarUsuario(usuario);
				limparFuncionario();
			}
		}else {
			context.addMessage(null, new FacesMessage("Você não possui permissão para isso"));
			System.out.println("Sem permiço");
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		return "/funcionario/CriarFuncionario.jsf?faces-redirect=true";
	}

	public String limparFuncionario() {
		usuario = new Usuario();
		return "";
	}

	public String carregarFuncionario() {
		UsuarioDAO funcionarioDAO = new UsuarioDAO();
		setUsuarios(funcionarioDAO.pesquisarTodos());
		return "";
	}

	public void salvarFoto() {
		try (InputStream is = foto.getInputStream()) {
			String originalFileName = foto.getSubmittedFileName();
			String randomFileName = generateRandomFileName(originalFileName);
			Path targetPath = new File("C:\\imagens\\imagens-perfil", randomFileName).toPath();
			Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
			usuario.setFoto(randomFileName);
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

	// Getters e Setters

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public Permissoes getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Permissoes permissoes) {
		this.permissoes = permissoes;
	}

}
