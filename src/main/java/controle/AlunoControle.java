package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Aluno;
import model.Genero;
import servico.AlunoServico;

@Named("alunoCon")
@ApplicationScoped
public class AlunoControle implements Serializable {
	
	private String test = "aa";
	private Aluno alunoFoco;
	private Collection<Aluno> alunos;
	
	private AlunoServico as;	
	

	public AlunoControle() {
		as = new AlunoServico();
	}	
	
	@PostConstruct
	public void init() {
		alunos = new ArrayList<Aluno>();
		alunos.addAll(as.listarAlunos());
		alunos.add(new Aluno("Temp", 2, "tmp@tmp", "temp", "2022", Genero.O));
		novoAluno();
		test = "bbb";
	}
	
	//metodos
	public void novoAluno() {
		alunoFoco = new Aluno();
	}
	
	public void excluirAluno() {
		if(alunos.contains(alunoFoco)) {
			alunos.remove(alunoFoco);
		}
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

	public String getTest() {
		return test;
		
	}
	
	

}
