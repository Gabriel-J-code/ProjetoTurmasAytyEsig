package controle;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Genero;
import model.Professor;
import model.Turma;
import percistencia.ProfessorPercistencia;
import percistencia.TurmaPercistencia;

@Named("professorCon")
@ApplicationScoped
public class ProfessorControle {
	
	private Professor professorFoco;
	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Professor> professores;
	private Turma turmaFoco;
	
	private ProfessorPercistencia pp;
	private TurmaPercistencia tp;
	

	public ProfessorControle() {
		//TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		pp = new ProfessorPercistencia();	
		tp = new TurmaPercistencia();
		sincronizarDados();
		novoProfessor();		
	}
	
		
	public void sincronizarDados() {
		professores = pp.getProfessores();
		
	}
	
	public void novoProfessor() {
		professorFoco = new Professor();
		existente = false;
		sincronizarDados();		
	}
	
	public void salvarNovoProfessor() {
		
		pp.adicionarNovoProfessor(professorFoco);
		
		sincronizarDados();
		novoProfessor();
	}
	
	public void excluirProfessor() {
		if(professores.contains(professorFoco)) {
			pp.deletarProfessor(professorFoco);			
		}
		sincronizarDados();
		novoProfessor();
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public List<Turma> turmasDoProfessor() {
		return (List<Turma>) professorFoco.getTurmasMinistradas();
		
	}
	
	public void removerTurma() {
		pp.removerProfessorDaTurma(professorFoco, turmaFoco);
		sincronizarDados();
	}


	public Professor getProfessorFoco() {
		return professorFoco;
	}


	public void setProfessorFoco(Professor professorFoco) {
		existente = true;
		this.professorFoco = professorFoco;
	}


	public boolean isExistente() {
		return existente;
	}


	public Collection<Professor> getProfessores() {
		return professores;
	}


	public void setTurmaFoco(Turma turmaFoco) {
		this.turmaFoco = turmaFoco;
	}
	
	public void mudarMostrarTurmasSisponiveis() {
		mostrarTurmasDisponiveis = !mostrarTurmasDisponiveis;
		
	}

	public boolean isMostrarTurmasDisponiveis() {
		return mostrarTurmasDisponiveis;
		
	}
	
	public List<Turma> turmasDisponives() {
		
		return tp.getTurmasSemProfessor();
	}
	
	public void cadastrarTurmaAProfessor() {
			tp.cadastrarProfessorATurma(professorFoco, turmaFoco);
	}
		
	
	public String MensagemCampoTurmas() {
		if (existente) {
			return "";			
		} else {
			return "O Professor precisa ser salvo antes de o cadastrar alguma turma";
		}		
	}
	
	public String titulo() {
		if(existente) {
			return "Atualização de Professor";
		}else {
			return "Cadastro de Novo Professor";
		}
	}
	
	

}
