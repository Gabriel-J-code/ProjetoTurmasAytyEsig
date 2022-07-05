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
import servico.AlunoServico;
import servico.InvalideFieldException;
import servico.TurmaServico;

@Named("alunoCon")
@ApplicationScoped
public class AlunoControle implements Serializable {
	
	
	private Aluno alunoFoco;
	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Aluno> alunos;
	private Turma turmaFoco;
	
	private AlunoServico as;
	private TurmaServico ts;
	

	public AlunoControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		as = new AlunoServico();
		ts = new TurmaServico();
		alunos = as.listarAlunos();
		novoAluno();		
	}
	
	@PreDestroy
	public void exit() {
		as.exit();
	}
	
	public void sincronizarDados() {
		alunos = as.listarAlunos();
	}
	//metodos
	public void novoAluno() {
		alunoFoco = new Aluno();
		existente = false;
		sincronizarDados();
		
	}
	public void salvarNovoAluno(){
		try {
			as.salvarNovoAluno(alunoFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		novoAluno();
		
	}
	
	public void excluirAluno() {
		if(alunos.contains(alunoFoco)) {
			as.deletarAluno(alunoFoco);
			sincronizarDados();
		}
		novoAluno();
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public List<Turma> turmasDoAluno() {
		return as.listarTurmaMatriculadaDeAluno(alunoFoco);
		
	}
	
	public void removerTurma() {
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
	
	public List<Turma> turmasDisponiveis() {
		return ts.listarTurmas();
		
	}
	
	public void matricularAlunoATurma() {
		try {
			as.matricularTurma(alunoFoco, turmaFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	

}
