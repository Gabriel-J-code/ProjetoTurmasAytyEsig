package model;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
/**
 * Entity implementation class for Entity: Professor
 *
 */
@Entity
@Table(name = "tbl_professor")
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "P professor tem que ter um nome")
	@Pattern(regexp = "[^0-9]*" , message = "Não pode conter numeros!")
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Past
	@NotNull(message = "O campo data de nascimento não pode ser vazio")
	private Date data_de_nascimento;
	
	@NotBlank(message = "o email não pode esta em branco")
	@Column(unique = true)	
	@Email(message = "Email invalido!")	
	private String email;
	
	@NotBlank(message = "O campo formação não pode está em branco")
	private String formacao;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	@OneToMany(mappedBy = "professor")
	private Collection<Turma> turmasMinistradas;


	public Professor() {
		this.turmasMinistradas = new ArrayList<Turma>();
	}   
	
	public Professor(String nome, String email, String formacao, Genero genero) {
		super();
		this.nome = nome;
		this.email = email;
		this.formacao = formacao;
		this.genero = genero;
		this.turmasMinistradas = new ArrayList<Turma>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}   
		
	public Date getData_de_nascimento() {
		return data_de_nascimento;
	}

	public void setData_de_nascimento(Date data_de_nascimento) {
		this.data_de_nascimento = data_de_nascimento;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}  
	
	public String getFormacao() {
		return this.formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}   
	
	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
		
	public Collection<Turma> getTurmasMinistradas() {
		return turmasMinistradas;
		
	}
	public void setTurmasMinistradas(Collection<Turma> turmasMatriculadas) {
		this.turmasMinistradas = turmasMatriculadas;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Professor temp = (Professor) obj;
		return this.id == temp.getId();
	}
   
}
