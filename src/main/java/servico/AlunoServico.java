package servico;

import java.util.List;

import model.Aluno;
import model.Genero;
import model.Turma;
import percistencia.AlunoPercistencia;

public class AlunoServico {
	private AlunoPercistencia ap;
		
	public AlunoServico() {
		ap = new AlunoPercistencia();
	}
	
	public void exit() {
		ap.fechar();
	}
		
	//matricular
	public void matricularAlunoATurma(Aluno aluno,Turma turma){
		ap.matricularAlunoATurma(aluno, turma);
	}	
	
	//desmatricular aluno
	public Aluno desmatricularAlunoDeTurma(Aluno aluno,Turma turma){		
		return ap.dematricularAlunoDeTurma(aluno, turma);
	}
			
	//atualizar
	public Aluno atualizarAluno(Aluno aluno) {
		return ap.atualizarAluno(aluno);		
	}	
	
	public void salvarNovoAluno(Aluno aluno){			
		ap.adicionarNovoAluno(aluno);
	}
	//pegar
		
		
	//nome
	public List<Aluno> procurarAlunoPorNome(String nome){		
		return ap.consultarAlunoPorNome(nome); //procurarAlunoPorCampo("nome", nome);
	}
		
	//email
	public List<Aluno> procurarAlunoPorEmail(String email){		
		return ap.consultarAlunoPorEmail(email);
	}
	
	//curso
	public List<Aluno> procurarAlunoPorCurso(String curso){		
		return ap.consultarAlunoPorCurso(curso);
	}
	//matricula
	public List<Aluno> procurarAlunoPorMatricula(String matricula){		
		return ap.consultarAlunoPorMatricula(matricula);
	}
	//genero
	public List<Aluno> procurarAlunoPorCurso(Genero genero){		
		return ap.consultarAlunoPorGenero(genero);
	}
	//turmas
	/*public List<Turma> listarTurmaMatriculadaDeAluno(Aluno aluno){		
		List<Turma> resultadoConsultaTurmas = ap.listarTurmasDoId(aluno.getId());					
		return resultadoConsultaTurmas;
	}	*/
	
	//listar
	public List<Aluno> listarAlunos() {
		return ap.getAlunos();
	}
	
	//deletar
	public void deletarAluno(Aluno aluno) {
		ap.deletarAluno(aluno);
	}

	
	
	

}
