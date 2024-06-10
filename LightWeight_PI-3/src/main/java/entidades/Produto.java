package entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;//
	private double preco;//
	private String descricao;//
	private int quantidade;//
	
	@Enumerated(EnumType.STRING)
	private TipoProduto tipoDoProduto;//
	@Enumerated(EnumType.STRING)
	private TamanhoProduto tamanhoProduto;//
	private int pesoLiquido;//
	private int desconto	;//
	private String foto;//
	
	
	@ManyToOne
	@JoinColumn(nullable = 	false)
	private Usuario criador;
	
	
	
	
	public void setId(int id) {
		this.id = id;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public void setPreco(double preco) {
		this.preco = preco;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}



	public void setTipoDoProduto(TipoProduto tipoDoProduto) {
		this.tipoDoProduto = tipoDoProduto;
	}



	public void setTamanhoProduto(TamanhoProduto tamanhoProduto) {
		this.tamanhoProduto = tamanhoProduto;
	}



	public void setPesoLiquido(int pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}



	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}



	public void setCriador(Usuario criador) {
		this.criador = criador;
	}



	public Produto() {
		super();
	}


	
	public int getId() {
		return id;
	}



	public String getNome() {
		return nome;
	}



	public double getPreco() {
		return preco;
	}



	public String getDescricao() {
		return descricao;
	}



	public int getQuantidade() {
		return quantidade;
	}



	public TipoProduto getTipoDoProduto() {
		return tipoDoProduto;
	}



	public TamanhoProduto getTamanhoProduto() {
		return tamanhoProduto;
	}



	public int getPesoLiquido() {
		return pesoLiquido;
	}



	public int getDesconto() {
		return desconto;
	}



	public String getFoto() {
		return foto;
	}



	public Usuario getCriador() {
		return criador;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
