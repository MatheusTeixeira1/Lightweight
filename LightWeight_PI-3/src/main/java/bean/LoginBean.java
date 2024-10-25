package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;

import dao.UsuarioDAO;
import entidades.Login;

@SessionScoped
@ManagedBean
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public UsuarioDAO uDao = new UsuarioDAO();
	
	private Login login;
	
	public LoginBean() {
		login = new Login();
	}
	
	public String logarNoSistema() {
		boolean validacao = uDao.validacaoApelidoSenha(login.getApelido(), login.getSenha());
		if(validacao) {
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			if(uDao.isFuncionario(login.getApelido())) {
				login.setEfuncionario(true);
				login.atribuirPermissoes(uDao.buscarPermissoes(login.getApelido()));
				login.setNome(uDao.buscarNomePorApelido(login.getApelido()));
				session.setAttribute("usuario", login);
				System.out.println("É funcionario");
				return "/funcionario/homeFuncionario.jsf?faces-redirect=true";
			}else {
				session.setAttribute("usuario", login);
				System.out.println("Não é funcionario");
				return "/aluno/home.jsf?faces-redirect=true";
			}
		}else {
			return "/home/login.jsf?faces-redirect=true";
		}
	}
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		login = new Login();
		return "/home/login.jsf?faces-redirect=true";
	}
	
	
	
	

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
}
