package entidades;

public enum TamanhoProduto {
	PEQUENO("PEQUENO"),
	MEDIO("MEDIO"),
	GRANDE("GRANDE");
	
	private String label;
	private TamanhoProduto(String label) {
		
		this.label = label;
		
	}

	public String getlabel() {
		return label;
	}
}
