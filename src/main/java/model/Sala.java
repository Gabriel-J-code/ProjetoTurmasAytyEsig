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
	@Size(min = 1, max = 10, message = "O campo numero precisa ter entre 1 e 10 caracteres")
	private String numero;
	
	@NotBlank(message = "O campo predio não pode esta em branco.")
	@Size(min = 1, max = 50, message = "O campo predio precisa ter entre 1 e 50 caracteres")
	private String predio;
	
	@NotBlank(message = "O campo campus não pode esta em branco.")
	@Size(min = 1, max = 50, message = "O campo campus precisa ter entre 1 e 50 caracteres")
	private String campus;


	public Sala() {
		super();
	} 
	
	public Sala(String numero, String predio, String campus) {
		super();
		this.numero = numero;
		this.predio = predio;
		this.campus = campus;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
			return String.format("Sala %s, predio: %s, campus: %s.", numero, predio, campus);
		}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Sala temp = (Sala) obj;
		return this.id == temp.getId();
	}
}
