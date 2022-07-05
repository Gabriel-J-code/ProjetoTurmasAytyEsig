package model;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Turma
 *
 */
@Entity
@Table(name = "tbl_turma")
public class Turma implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 100)
	private String disciplina;
	
	
	private String horario;
	
	@ManyToOne(targetEntity=Professor.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "id_professor")
	private Professor professor;
	
	@ManyToOne(targetEntity=Sala.class, fetch=FetchType.EAGER)
	@JoinColumn(name = "id_sala")
	private Sala sala;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinTable(name="tbl_matriculas", joinColumns= {@JoinColumn(name="id_turma")}, inverseJoinColumns={@JoinColumn(name="id_aluno")})
	private Collection<Aluno> alunos;


	public Turma() {
		alunos = new ArrayList<Aluno>();
	}   
	
	public Turma(String disciplina, String horario) {
		super();
		this.disciplina = disciplina;
		this.horario = horario;
		this.professor = null;
		this.sala = null;
		alunos = new ArrayList<Aluno>();
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	
	public String getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}   
	
	
	public String getHorario() {
		return this.horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	} 
	
	
	public Professor getProfessor() {
		return this.professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}  
	
	public Sala getSala() {
		return this.sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Collection<Aluno> getAlunos() {
		return alunos;
		
	}
	public void setAlunos(Collection<Aluno> alunos) {
		this.alunos = alunos;		
	}
	
	public void addAluno(Aluno aluno) {
		this.alunos.add(aluno);
		
	}
	@Override
		public String toString() {
			
			return String.format("(%d) %s, horario: %s, professor: %s, sala: %s.", this.id, this.disciplina, this.horario, this.professor, this.sala);
		}
   
}
