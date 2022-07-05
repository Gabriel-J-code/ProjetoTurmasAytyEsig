package controle;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Genero;
import model.Professor;
import model.Turma;
import servico.InvalideFieldException;
import servico.ProfessorServico;
import servico.TurmaServico;

@Named("professorCon")
@ApplicationScoped
public class ProfessorControle {
	
	private Professor professorFoco;
	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Professor> professores;
	private Turma turmaFoco;
	
	private ProfessorServico ps;
	private TurmaServico ts;

	public ProfessorControle() {
		//TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		ps = new ProfessorServico();
		ts = new TurmaServico();
		professores = ps.listarProfessors();
		novoProfessor();		
	}
	
	@PreDestroy
	public void exit() {
		ps.exit();
	}
	
	public void sincronizarDados() {
		professores = ps.listarProfessors();
		
	}
	
	public void novoProfessor() {
		professorFoco = new Professor();
		existente = false;
		sincronizarDados();		
	}
	
	public void salvarNovoProfessor() {
		try {
			ps.salvarNovoProfessor(professorFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sincronizarDados();
	}
	
	public void excluirProfessor() {
		if(professores.contains(professorFoco)) {
			ps.deletarProfessor(professorFoco);
			sincronizarDados();
		}
		novoProfessor();
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public List<Turma> turmasDoProfessor() {
		return (List<Turma>) professorFoco.getTurmasMinistradas();
		
	}
	
	public void removerTurma() {
		ps.desmatricularProfessor(professorFoco, turmaFoco);
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
		return ts.listarTurmasSemProfessor();
	}
	
	public void cadastrarTurmaAProfessor() {
			try {
				ps.matricularProfessor(professorFoco, turmaFoco);
			} catch (InvalideFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		
	
	public String MensagemCampoTurmas() {
		if (existente) {
			return "";			
		} else {
			return "O Professor precisa ser salvo antes de o cadastrar alguma turma";
		}		
	}
	
	
	
	
	
	
	
	
	
	
	

}
