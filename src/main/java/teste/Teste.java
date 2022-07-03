package teste;

import java.util.List;

import model.Aluno;
import model.Genero;
import model.Turma;
import servico.AlunoServico;
import servico.InvalideFieldException;

public class Teste {

	public static void main(String[] args) {	
		/*
		AlunoPercistencia ap = new AlunoPercistencia();
		
		List<Aluno> alunos = ap.getAlunos();
		TurmaPercistencia tp = new TurmaPercistencia();
		Turma t = tp.encontrarPeloId(5);
		for (Aluno aluno : t.getAlunos()) {
			//t.addAluno(aluno);
			//aluno.getTurmasMatriculadas().add(t);
			System.out.println(aluno.toString());
			for (Turma turma : aluno.getTurmasMatriculadas()) {
				System.out.println(turma.toString());
				
			}
		}	
		System.out.println(t.toString());
		tp.create(t);*/
		
		
		AlunoServico as = new AlunoServico();
		
		Aluno gabrielDeJesus = new Aluno("Gabriel de Jesus", 24, "Gabriel@mail.com", "LCC", "20190000000", Genero.M);
		Aluno ArthurGabriel = new Aluno("Arthur Gabriel", 30, "Arthur@mail.com", "SI", "20210000000", Genero.M);
		try {
			as.criarAluno("Gabriel de Jesus", 24, "Gabriel@mail.com", "LCC", "20190000000", Genero.M);
			as.criarAluno("Arthur Gabriel", 30, "Arthur@mail.com", "SI", "20210000000", Genero.M);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Aluno> alunos = as.procurarAlunoPorNome("Gabriel");
		for (Aluno aluno : alunos) {			
			System.out.println(aluno.toString());
		}
		List<Aluno> alunosporidade = as.procurarAlunoPorIdade(23);
		for (Aluno aluno : alunosporidade) {			
			System.out.println(aluno.toString());
			System.out.println(aluno.getGenero().toString());;
		}
		List<Turma> turmas = as.listarTurmaMatriculadaDeAluno(as.procurarAlunoPorId(1));
		for (Turma turma : turmas) {			
			System.out.println(turma.toString());
		}		
		as.exit();
	}	

}


