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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "tbl_aluno")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "O campo nome não pode estar em branco")
	@Size(min = 1, max = 100, message = "o nome tem que ter entre 1 e 100 caracteres")
	@Pattern(regexp = "[^0-9]*" , message = "Não pode conter numeros!")
	private String nome;
	
	@Min(value = 0, message = "A idade precisa conter um valor maior que 0!")
	private int idade;
	
	@NotBlank(message = "O campo email não pode estar em branco")
	@Column(unique = true)
	@Email(message = "Email invalido!")	
	private String email;
	
	@NotBlank(message = "O campo curso não pode estar em branco")
	private String curso;
	
	@NotBlank(message = "O campo matricula não pode estar em branco")
	@Column(unique = true)
	private String matricula;
	
	@Enumerated(EnumType.STRING)
	private Genero genero;
	
	@ManyToMany(mappedBy = "alunos", fetch = FetchType.EAGER)
	private Collection<Turma> turmasMatriculadas;


	public Aluno() {
		turmasMatriculadas = new ArrayList<Turma>();
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
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Aluno temp = (Aluno) obj;
		return this.id == temp.getId();
	}
   
}
