package controle;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Aluno;
import model.Genero;
import model.Turma;
import percistencia.AlunoPercistencia;
import percistencia.TurmaPercistencia;


@Named("alunoCon")
@ApplicationScoped
public class AlunoControle implements Serializable {
	
	
	private Aluno alunoFoco;
	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Aluno> alunos;
	private Turma turmaFoco;
		
	private AlunoPercistencia ap;
	

	public AlunoControle() {
		
	}	
	
	@PostConstruct
	public void init() {	
		
		
		ap = new AlunoPercistencia();
		sincronizarDados();
		novoAluno();		
	}
	
	@PreDestroy
	public void exit() {
	ap.fechar();
	}
	
	public void sincronizarDados() {
		alunos = ap.getAlunos();
	}
	//metodos
	public void novoAluno() {
		alunoFoco = new Aluno();
		existente = false;
		sincronizarDados();
		
	}
	public void salvarNovoAluno(){		
		ap.adicionarNovoAluno(alunoFoco);	
		novoAluno();
		
	}
	
	public void excluirAluno() {
		if(alunos.contains(alunoFoco)) {
			ap.deletarAluno(alunoFoco);
			sincronizarDados();
		}
		novoAluno();
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public List<Turma> turmasDoAluno() {		
		return (List<Turma>) alunoFoco.getTurmasMatriculadas();
		
	}
	
	public void removerTurma() {
		ap.dematricularAlunoDeTurma(alunoFoco, turmaFoco);
		sincronizarDados();
		alunoFoco = ap.pedarDadosAtualizadosDoAluno(alunoFoco);
	}
	
	public void matricularAlunoATurma() {
		ap.matricularAlunoATurma(alunoFoco, turmaFoco);
		sincronizarDados();
		alunoFoco = ap.pedarDadosAtualizadosDoAluno(alunoFoco);
	}
	
	public List<Turma> turmasDisponiveis() {
		TurmaPercistencia tp = new TurmaPercistencia();
		List<Turma> turmasDisponiveis =  tp.getTurmas();	
		turmasDisponiveis.removeAll(alunoFoco.getTurmasMatriculadas());
		return turmasDisponiveis;
		
	}
	
	//GETs e SETs
	
	public Aluno getAlunoFoco() {
		return alunoFoco;
	}

	public void setAlunoFoco(Aluno alunoFoco) {
		this.alunoFoco = alunoFoco;
		existente = true;
		mostrarTurmasDisponiveis = false;
	}

	public Collection<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Collection<Aluno> alunos) {
		this.alunos = alunos;
	}

	public boolean isExistente() {
		return existente;
		
	}
	
	public void mudarMostrarTurmasSisponiveis() {
		mostrarTurmasDisponiveis = !mostrarTurmasDisponiveis;
		
	}

	public String MensagemCampoTurmas() {
		if (existente) {
			return "";			
		} else {
			return "O Aluno precisa ser salvo antes de matricular em alguma turma";
		}		
	}

	public void setTurmaFoco(Turma turmaFoco) {
		this.turmaFoco = turmaFoco;
		
	}

	public boolean isMostrarTurmasDisponiveis() {
		return mostrarTurmasDisponiveis;
		
	}
	
	
	
	
	
	
	
	

}
