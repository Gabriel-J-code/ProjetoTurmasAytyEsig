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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "tbl_aluno")
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "O campo nome não pode estar em branco")
	@Pattern(regexp = "[^0-9]*" , message = "Não pode conter numeros!")
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Past
	@NotNull(message = "O campo data de nascimento não pode ser vazio")
	private Date data_de_nascimento;
	
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
		
	
	public Aluno(
			@NotBlank(message = "O campo nome não pode estar em branco") @Pattern(regexp = "[^0-9]*", message = "Não pode conter numeros!") String nome,
			Date data_de_nascimento,
			@NotBlank(message = "O campo email não pode estar em branco") @Email(message = "Email invalido!") String email,
			@NotBlank(message = "O campo curso não pode estar em branco") String curso,
			@NotBlank(message = "O campo matricula não pode estar em branco") String matricula, Genero genero) {
		super();
		this.nome = nome;
		this.data_de_nascimento = data_de_nascimento;
		this.email = email;
		this.curso = curso;
		this.matricula = matricula;
		this.genero = genero;
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
		
		return String.format("(%d) nome: %s; nascimento: %s, genero %s", getId(),getNome(), getData_de_nascimento().toString(), getGenero().name());
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Aluno temp = (Aluno) obj;
		return this.id == temp.getId();
	}
   
}
