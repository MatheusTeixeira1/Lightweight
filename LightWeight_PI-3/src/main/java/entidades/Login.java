package entidades;

import java.util.List;

public class Login {
	
	private int id;
	private String apelido;
	private String senha;
	
	private boolean efuncionario = false;
	private boolean gerenciarCliente = false;
	private boolean gerenciarFeed = false;
	private boolean gerenciarFuncionario = false;
	private boolean gerenciarProdutos = false;
	
	public Login() {

	}

	public Login(String login, String senha) {
		this.apelido = login;
		this.senha = senha;
	}
	
	public void atribuirPermissoes(List<Boolean> permissoes) {
		setGerenciarCliente(permissoes.get(0));
		setGerenciarFeed(permissoes.get(1));
		setGerenciarFuncionario(permissoes.get(2));
		setGerenciarProdutos(permissoes.get(3));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String login) {
		this.apelido = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isEfuncionario() {
		return efuncionario;
	}

	public void setEfuncionario(boolean efuncionario) {
		this.efuncionario = efuncionario;
	}

	public boolean isGerenciarCliente() {
		return gerenciarCliente;
	}

	public void setGerenciarCliente(boolean gerenciarCliente) {
		this.gerenciarCliente = gerenciarCliente;
	}

	public boolean isGerenciarFeed() {
		return gerenciarFeed;
	}

	public void setGerenciarFeed(boolean gerenciarFeed) {
		this.gerenciarFeed = gerenciarFeed;
	}

	public boolean isGerenciarFuncionario() {
		return gerenciarFuncionario;
	}

	public void setGerenciarFuncionario(boolean gerenciarFuncionario) {
		this.gerenciarFuncionario = gerenciarFuncionario;
	}

	public boolean isGerenciarProdutos() {
		return gerenciarProdutos;
	}

	public void setGerenciarProdutos(boolean gerenciarProdutos) {
		this.gerenciarProdutos = gerenciarProdutos;
	}
	
}
