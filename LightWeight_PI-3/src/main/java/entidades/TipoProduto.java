package entidades;

public enum TipoProduto {
	COMESTIVEL("COMESTIVEL"),
	EQUIPAMENTOS("EQUIPAMENTOS");
	
	private String label;
	private TipoProduto(String label) {
		
		this.label = label;
		
	}

	public String getlabel() {
		return label;
	}

}
