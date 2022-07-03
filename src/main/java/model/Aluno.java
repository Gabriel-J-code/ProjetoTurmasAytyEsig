package model;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@Table(name = "tbl_aluno")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(min = 1, max = 100)
	@Pattern(regexp = "[^0-9]*" , message = "Não pode conter numeros!")
	private String nome;
	
	@Min(value = 0, message = "A idade precisa conter um valor maior que 0!")
	private int idade;
	
	@NotNull(message = "Não pode ser nulo!")
	@NotEmpty(message = "Não pode esta vazio!")
	@Column(unique = true)
	@Email(message = "Email invalido!")	
	private String email;
	
	@NotNull(message = "Não pode ser nulo!")
	@NotEmpty(message = "Não pode esta vazio!")
	private String curso;
	
	@NotNull(message = "Não pode ser nulo!")
	@NotEmpty(message = "Não pode esta vazio!")
	@Column(unique = true)
	private String matricula;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	@ManyToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
	private Collection<Turma> turmasMatriculadas;


	public Aluno() {
		super();
	}  
	
	public Aluno(String nome, int idade, String email, String curso, String matricula, Genero genero) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.curso = curso;
		this.matricula = matricula;
		this.genero = genero;
		turmasMatriculadas = new ArrayList<Turma>();
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
	
	public int getIdade() {
		return this.idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}  
	
	public String getCurso() {
		return this.curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}  
	
	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}  
				
	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

		
	public Collection<Turma> getTurmasMatriculadas() {
		return turmasMatriculadas;
	}

	public void setTurmasMatriculadas(Collection<Turma> turmas) {
		this.turmasMatriculadas = turmas;
	}
	@Override
	public String toString() {
		
		return String.format("(%d) nome: %s; idade: %d, genero %s", getId(),getNome(), getIdade(), getGenero().name());
	}
   
}
