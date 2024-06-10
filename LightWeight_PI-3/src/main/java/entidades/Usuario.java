package entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(unique = true)
	private String apelido;
	private String senha;
	private String nome;
	private String sobrenome;
	private String cpf;
	private String foto;
	private LocalDateTime expiracaoDaAssinatura;

	@ManyToOne
	//@JoinColumn(nullable = false)
	private Usuario criador;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "permissoes_id", referencedColumnName = "id")
	private Permissoes permissoes;	
	private boolean efuncionario = false;
	
	
	
	
	
	//getters e setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Usuario getCriador() {
		return criador;
	}
	public void setCriador(Usuario criador) {
		this.criador = criador;
	}
	public boolean isEfuncionario() {
		return efuncionario;
	}
	public void setEfuncionario(boolean efuncionario) {
		this.efuncionario = efuncionario;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	public LocalDateTime getExpiracaoDaAssinatura() {
		return expiracaoDaAssinatura;
	}
	public void setExpiracaoDaAssinatura(LocalDateTime expiracaoDaAssinatura) {
		this.expiracaoDaAssinatura = expiracaoDaAssinatura;
	}
	public Permissoes getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(Permissoes permissoes) {
		this.permissoes = permissoes;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id;
	}
	
	
}
