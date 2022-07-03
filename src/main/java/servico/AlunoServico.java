package servico;

import java.util.List;

import model.Aluno;
import model.Genero;
import model.Turma;
import percistencia.AlunoPercistencia;
import percistencia.TurmaPercistencia;

public class AlunoServico {
	private AlunoPercistencia ap;
	private TurmaPercistencia tp;
		
	public AlunoServico() {
		ap = new AlunoPercistencia();
	}
	
	public void exit() {
		ap.fechar();
	}
	
	//validar
	private void validarAluno(Aluno aluno) throws InvalideFieldException {
		
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
	}
	
	//matricular
	public void matricularAluno(Aluno aluno,Turma turma) throws InvalideFieldException {
		validarAluno(aluno);
		aluno.getTurmasMatriculadas().add(turma);
		turma.getAlunos().add(aluno);
		ap.atualizar(aluno);
		tp.atualizar(turma);
	}	
	
	//criar
	private void salvarAluno(Aluno aluno) throws InvalideFieldException {
		validarAluno(aluno);
		if (ap.getAlunos().contains(aluno)) {
			ap.atualizar(aluno);
		}else {
			ap.create(aluno);	
		}
	}
	
	public void salvarNovoAluno(String nome, int idade, String email, String curso, String matricula, Genero genero) throws InvalideFieldException {
		Aluno aluno = new Aluno(nome, idade, email, curso, matricula, genero);
		salvarNovoAluno(aluno);		
	}
	
	public void salvarNovoAluno(Aluno aluno) throws InvalideFieldException {
		salvarAluno(aluno);
	}
	//pegar
	
	//id
	public Aluno procurarAlunoPorId(Integer id) {
		return ap.encontrarPeloId(id);		
	}
		
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
	public List<Turma> listarTurmaMatriculadaDeAluno(Aluno aluno){		
		List<Turma> resultadoConsultaTurmas = ap.listarTurmasDoId(aluno.getId());					
		return resultadoConsultaTurmas;
	}	
	
	//listar
	public List<Aluno> listarAlunos() {
		return ap.getAlunos();
	}
	
	//deletar
	public void deletarAluno(Aluno aluno) {
		ap.delete(aluno.getId());
	}
	

}
