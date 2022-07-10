package model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Sala
 *
 */
@Entity
@Table(name = "tbl_sala")
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;
	   
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "O campo numero não pode esta em branco.")
	@Size(min = 1, max = 30, message = "O campo numero precisa ter entre 1 e 10 caracteres")
	private String identificacao;
	
	@NotBlank(message = "O campo predio não pode esta em branco.")
	@Size(min = 1, max = 50, message = "O campo predio precisa ter entre 1 e 50 caracteres")
	private String predio;
	
	@NotBlank(message = "O campo campus não pode esta em branco.")
	@Size(min = 1, max = 50, message = "O campo campus precisa ter entre 1 e 50 caracteres")
	private String campus;


	public Sala() {
		super();
	} 
	
	public Sala(String identificacao, String predio, String campus) {
		super();
		this.identificacao = identificacao;
		this.predio = predio;
		this.campus = campus;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public String getIdentificacao() {
		return this.identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}   
	
	public String getPredio() {
		return this.predio;
	}

	public void setPredio(String predio) {
		this.predio = predio;
	} 
	
	public String getCampus() {
		return this.campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}
   
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format("Sala %s, predio: %s, campus: %s.", identificacao, predio, campus);
		}
	
	@Override
	public boolean equals(Object obj) {
		boolean resultado = false;
		if (obj.getClass()== Sala.class) {
			Sala temp = (Sala) obj;
			if (temp.getId()==id) {
				resultado = true;
			}else if (temp.getIdentificacao()==identificacao 
					&& temp.getPredio()==predio 
					&& temp.getCampus()==campus) {
				resultado = true;				
			}
		}
		
		return resultado;
	}
}
