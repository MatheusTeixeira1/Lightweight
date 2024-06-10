package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.TesteDAO;
import entidades.Teste;

@SessionScoped
@ManagedBean(name = "testeBean")
public class TesteBean {
	private Teste teste;
	private List<Teste> testes;
	TesteDAO testeDAO = new TesteDAO();
	
	private String view = "fechar";
	
	
	
	public void mostrarPop() {
		setView("pop");
	}
	public void fecharPop() {
		setView("fechar");
	}
	
	public void mostrarPopPesquisa() {
		setView("pesquisa");
	}
	
	
	
	
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public TesteBean() {
		setTeste(new Teste());
	}
	
	public String navegarPara(Teste t) {
		setTeste(t);
		return "TESTE2.xhtml";
	}
	
	@PostConstruct
	public void carregarTestes() {
		setTestes(testeDAO.pesquisarTodos());
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	public List<Teste> getTestes() {
		return testes;
	}

	public void setTestes(List<Teste> testes) {
		this.testes = testes;
	}

	public TesteDAO getTesteDAO() {
		return testeDAO;
	}

	public void setTesteDAO(TesteDAO testeDAO) {
		this.testeDAO = testeDAO;
	}
}
