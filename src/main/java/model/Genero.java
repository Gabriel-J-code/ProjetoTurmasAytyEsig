package model;


public enum Genero {
	M("Masculino"),
	F("Feminino"),
	O("Outro");
	
	private String descricao;
	
	Genero(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
        return descricao;
    }
}
