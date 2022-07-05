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
	
	//validar
	/*private void validarAluno(Aluno aluno) throws InvalideFieldException {
		
		if(aluno.getNome()==null ||aluno.getNome()=="") {
			throw new InvalideFieldException("Alunos com nome em branco; ");
		}
		if(aluno.getIdade()<0) {
			throw new InvalideFieldException("Alunos com idade invalida; ");
		}
		if(aluno.getCurso()==null ||aluno.getCurso()=="") {
			throw new InvalideFieldException("Alunos com curso em branco; ");
		}
		if(aluno.getEmail()==null ||aluno.getEmail()=="") {
			throw new InvalideFieldException("Alunos com email em branco; ");
		}
		if(!aluno.getEmail().contains("@")) {
			throw new InvalideFieldException("Alunos com email invalido; ");
		}
		if(aluno.getMatricula()==null ||aluno.getMatricula()=="") {
			throw new InvalideFieldException("Alunos com matricula em branco; ");
		}				
	}*/
	
	//matricular
	public void matricularTurma(Aluno aluno,Turma turma){
		ap.matricularAlunoATurma(aluno, turma);
	}	
	
	//desmatricular aluno
	public void desmatricularTurma(Aluno aluno,Turma turma){		
		ap.dematricularAlunoDeTurma(aluno, turma);
	}
			
	//atualizar
	public Aluno atualizarAluno(Aluno aluno) {
		return ap.atualizarAluno(aluno);		
	}
	
	public void salvarNovoAluno(String nome, int idade, String email, String curso, String matricula, Genero genero){
		Aluno aluno = new Aluno(nome, idade, email, curso, matricula, genero);
		salvarNovoAluno(aluno);		
	}
	
	public void salvarNovoAluno(Aluno aluno){			
		ap.adicionarNovoAluno(aluno);
	}
	//pegar
		
		
	//nome
	public List<Aluno> procurarAlunoPorNome(String nome){		
		return ap.consultarAlunoPorNome(nome); //procurarAlunoPorCampo("nome", nome);
	}
	
	//idade
	public List<Aluno> procurarAlunoPorIdade(int idade){		
		return ap.consultarAlunoPorIdade(idade);
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
