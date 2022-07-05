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
		tp = new TurmaPercistencia();
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
	public void matricularTurma(Aluno aluno,Turma turma) throws InvalideFieldException {
		validarAluno(aluno);
		aluno.getTurmasMatriculadas().add(turma);
		turma.getAlunos().add(aluno);
		ap.atualizarAluno(aluno);
		tp.atualizarTurma(turma);
	}	
	
	//desmatricular aluno
	public void desmatricularTurma(Aluno aluno,Turma turma){		
		aluno.getTurmasMatriculadas().remove(turma);
		turma.getAlunos().remove(aluno);		
		tp.atualizarTurma(turma);
		ap.atualizarAluno(aluno);
	}
			
	//atualizar
	public Aluno atualizarAluno(Aluno aluno) {
		try {
			validarAluno(aluno);
			ap.atualizarAluno(aluno);
			return aluno;
		} catch (InvalideFieldException e) {
			Aluno novo = ap.encontrarPeloId(aluno.getId());
			return novo;
		}		
	}
	
	public void salvarNovoAluno(String nome, int idade, String email, String curso, String matricula, Genero genero) throws InvalideFieldException {
		Aluno aluno = new Aluno(nome, idade, email, curso, matricula, genero);
		salvarNovoAluno(aluno);		
	}
	
	public void salvarNovoAluno(Aluno aluno) throws InvalideFieldException {
		validarAluno(aluno);		
		ap.adicionarNovoAluno(aluno);
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
		ap.deletarAlunoPorId(aluno.getId());
	}
	

}
