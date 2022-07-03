package controle;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Aluno;
import model.Genero;
import servico.AlunoServico;
import servico.InvalideFieldException;

@Named("alunoCon")
@ApplicationScoped
public class AlunoControle implements Serializable {
	
	
	private Aluno alunoFoco;
	private Collection<Aluno> alunos;
	
	private AlunoServico as;	
	

	public AlunoControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		as = new AlunoServico();
		alunos = as.listarAlunos();
		//alunos.add(new Aluno("Temp", 2, "tmp@tmp", "temp", "2022", Genero.O));
		novoAluno();		
	}
	
	@PreDestroy
	public void exit() {
		as.exit();
	}
	
	public void syscronizar() {
		alunos = as.listarAlunos();
	}
	//metodos
	public void novoAluno() {
		alunoFoco = new Aluno();
		syscronizar();
		
	}
	public void salvarNovoAluno() throws InvalideFieldException {
		as.salvarNovoAluno(alunoFoco);	
		novoAluno();
		
	}
	
	public void excluirAluno() {
		if(alunos.contains(alunoFoco)) {
			as.deletarAluno(alunoFoco);
			syscronizar();
		}
		novoAluno();
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	//GETs e SETs
	
	public Aluno getAlunoFoco() {
		return alunoFoco;
	}

	public void setAlunoFoco(Aluno alunoFoco) {
		this.alunoFoco = alunoFoco;
	}

	public Collection<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Collection<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	
	

}
