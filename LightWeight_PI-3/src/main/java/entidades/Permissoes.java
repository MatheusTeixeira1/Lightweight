package entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Permissoes implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(mappedBy = "permissoes")
	@JoinColumn(nullable = 	false)
	private Usuario id_usuario;
	
	@JoinColumn(nullable = false)
	private boolean gerenciarProdutos = false;
	@JoinColumn(nullable = false)
    private boolean gerenciarCliente = false;
	@JoinColumn(nullable = false)
    private boolean gerenciarFuncionario = false;
	@JoinColumn(nullable = false)
    private boolean gerenciarFeed = false;
	

	public Permissoes() {
		
	}
	
	
	
	//getters e setters
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Usuario getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Usuario id_usuario) {
		this.id_usuario = id_usuario;
	}
	public boolean isGerenciarProdutos() {
		return gerenciarProdutos;
	}
	public void setGerenciarProdutos(boolean gerenciarProdutos) {
		this.gerenciarProdutos = gerenciarProdutos;
	}
	public boolean isGerenciarCliente() {
		return gerenciarCliente;
	}
	public void setGerenciarCliente(boolean gerenciarCliente) {
		this.gerenciarCliente = gerenciarCliente;
	}
	public boolean isGerenciarFuncionario() {
		return gerenciarFuncionario;
	}
	public void setGerenciarFuncionario(boolean gerenciarFuncionario) {
		this.gerenciarFuncionario = gerenciarFuncionario;
	}
	public boolean isGerenciarFeed() {
		return gerenciarFeed;
	}
	public void setGerenciarFeed(boolean gerenciarFeed) {
		this.gerenciarFeed = gerenciarFeed;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permissoes other = (Permissoes) obj;
		return id == other.id;
	}
	
	
}
