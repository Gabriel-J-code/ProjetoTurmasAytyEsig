package controle;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import javax.inject.Named;

import model.Aluno;
import model.Genero;
import model.Professor;
import model.Sala;
import model.Turma;
import servico.AlunoServico;
import servico.InvalideFieldException;
import servico.ProfessorServico;
import servico.SalaServico;
import servico.TurmaServico;

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
	
	
	private AlunoServico as;
	private TurmaServico ts;
	private ProfessorServico ps;
	private SalaServico ss;

	public TurmaControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		as = new AlunoServico();
		ts = new TurmaServico();
		ps = new ProfessorServico();
		ss = new SalaServico();
		
		novaTurma();		
	}
	
		
	public void sincronizarDados() {
		turmas = ts.listarTurmas();
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
		try {
			ts.salvarTurma(turmaFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		novaTurma();
		
	}
	
	public void excluirTurma() {
		if(turmas.contains(turmaFoco)) {
			ts.deletarTurma(turmaFoco);
			sincronizarDados();
		}
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

	public Genero[] generos() {
		return Genero.values();
		
	}
		
	public void removerAluno() {
		as.desmatricularTurma(alunoFoco, turmaFoco);
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
		return ps.listarProfessors();		
	}
	public void cadastraProfessor() {
		try {
			removerProfessor();
			ts.cadastrarProfessor(turmaFoco, professorFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		sincronizarDados();
	}
	
	public void removerProfessor() {
		ts.descastrarProfessor(turmaFoco);
		sincronizarDados();
	}
	
	public List<Sala> salasDisponiveis() {
		return ss.listarSalas();		
	}
	
	public void registrarSala() {
		try {
			ts.cadastrarSala(turmaFoco, salaFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sincronizarDados();
	}
	
	public void removerSala() {
		ts.removerSala(turmaFoco);
		sincronizarDados();
	}
	
	
	
	
	
	

}
