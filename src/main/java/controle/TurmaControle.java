package controle;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Named;

import model.Aluno;
import model.Professor;
import model.Sala;
import model.Turma;
import percistencia.ProfessorPercistencia;
import percistencia.SalaPercistencia;
import percistencia.TurmaPercistencia;

@Named("turmaCon")
@ApplicationScoped
public class TurmaControle implements Serializable {
	
	private Turma turmaFoco;
	private Aluno alunoFoco;
	private Professor professorFoco;
	private Sala salaFoco;
	private boolean existente = false;
	
	private boolean podeMostrarProfessores = false;
	private boolean podeMostrarAlunosDaTurma = false;
	private boolean podeMostrarSalas = false;
	
	
	private Collection<Turma> turmas;
	
	private TurmaPercistencia tp;
	private ProfessorPercistencia pp;
	private SalaPercistencia sp;
	
	public TurmaControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		tp = new TurmaPercistencia();
		pp = new ProfessorPercistencia();	
		sp = new SalaPercistencia();
		
		novaTurma();		
	}
	
		
	public void sincronizarDados() {
		turmas = tp.getTurmas();
	}
	//metodos
	public void novaTurma() {
		turmaFoco = new Turma();
		existente = false;
		podeMostrarAlunosDaTurma = false;
		podeMostrarProfessores = false;
		podeMostrarSalas = false;
		sincronizarDados();				
		
	}
	public void salvarNovaTurma(){
		tp.adicionarNovaTurma(turmaFoco);			
		novaTurma();
		
	}
	
	public void excluirTurma() {
		if(turmas.contains(turmaFoco)) {
			tp.deletarTurma(turmaFoco);			
		}
		sincronizarDados();
		novaTurma();
	}
	
	public Professor getProfessorFoco() {
		return professorFoco;
	}

	public void setProfessorFoco(Professor professorFoco) {
		this.professorFoco = professorFoco;
	}

	public Sala getSalaFoco() {
		return salaFoco;
	}

	public void setSalaFoco(Sala salaFoco) {
		this.salaFoco = salaFoco;
	}

	public Turma getTurmaFoco() {
		return turmaFoco;
	}

	public boolean isPodeMostrarProfessores() {
		return podeMostrarProfessores;
	}

	public boolean isPodeMostrarAlunosDaTurma() {
		return podeMostrarAlunosDaTurma;
	}

	public boolean isPodeMostrarSalas() {
		return podeMostrarSalas;
	}

	public Collection<Turma> getTurmas() {
		return turmas;
	}

			
	public void removerAluno() {
		tp.dematricularAlunoDeTurma(turmaFoco,alunoFoco);
		sincronizarDados();
	}
	
	//GETs e SETs
	
	public Aluno getAlunoFoco() {
		return alunoFoco;
	}

	public void setAlunoFoco(Aluno alunoFoco) {
		this.alunoFoco = alunoFoco;
		existente = true;
	}

	public Collection<Turma> getTumas() {
		return turmas;
	}
	

	public boolean isExistente() {
		return existente;
		
	}
	
	public void mudarPodeMostrarAlunosDaTurma() {
		podeMostrarAlunosDaTurma = !podeMostrarAlunosDaTurma;		
	}
	
	public void mudarPodeMostrarProfessores() {
		podeMostrarProfessores = !podeMostrarProfessores;
	}
	
	public void mudarPodeMostrarSalas() {
		podeMostrarSalas = !podeMostrarSalas;
	}

	public String MensagemCampoProfessores() {
		if (existente) {
			return "";			
		} else {
			return "A Turma precisa ser salva antes de cadastra um professor";
		}		
	}

	
	public String MensagemCampoSalas() {
		if (existente) {
			return "";			
		} else {
			return "A Turma precisa ser salva antes de cadastra uma sala";
		}		
	}
	
	public void setTurmaFoco(Turma turmaFoco) {		
		this.turmaFoco = turmaFoco;
		existente = true;
	}		
	
	public List<Professor> professoresDisponiveis() {
		return pp.getProfessores();		
	}
	
	
	public void cadastraProfessor() {		
		removerProfessor();
		tp.cadastrarProfessorATurma(professorFoco, turmaFoco);				
		mudarPodeMostrarProfessores();
		sincronizarDados();
	}
	
	public void removerProfessor() {
		tp.removerProfessorDeTurma(turmaFoco);
		sincronizarDados();
	}
	
	public List<Sala> salasDisponiveis() {		
		return sp.getSalas();		
	}
	
	public void registrarSala() {
		tp.cadastraSalaATurma(turmaFoco, salaFoco);
		sincronizarDados();
		podeMostrarSalas = false;
	}
	
	public void removerSala() {
		tp.removerSalaDaTurma(turmaFoco);
		sincronizarDados();
	}
	
	public String titulo() {
		if(existente) {
			return "Atualização de Turma";
		}else {
			return "Cadatro de Nova Turma";
		}
		
	}
	
	
	
	

}
