package servico;

import java.util.List;

import model.Professor;
import model.Genero;
import model.Turma;
import percistencia.ProfessorPercistencia;
import percistencia.TurmaPercistencia;

public class ProfessorServico {
	private ProfessorPercistencia pp;
	private TurmaPercistencia tp;
		
	public ProfessorServico() {
		pp = new ProfessorPercistencia();
		tp = new TurmaPercistencia();
	}
	
	public void exit() {
		pp.fechar();
	}
	
	//validar
	private void validarProfessor(Professor professor) throws InvalideFieldException {
		
		if(professor.getNome()==null ||professor.getNome()=="") {
			throw new InvalideFieldException("Professor com nome em branco; ");
		}
		if(professor.getIdade()<0) {
			throw new InvalideFieldException("Professor com idade invalida; ");
		}		
		if(professor.getEmail()==null ||professor.getEmail()=="") {
			throw new InvalideFieldException("Professor com email em branco; ");
		}
		if(!professor.getEmail().contains("@")) {
			throw new InvalideFieldException("Professor com email invalido; ");
		}
		if(professor.getFormacao()==null ||professor.getFormacao()=="") {
			throw new InvalideFieldException("Professor com formação em branco; ");
		}				
	}
	
	//matricular
	public void matricularProfessor(Professor professor,Turma turma) throws InvalideFieldException {
		validarProfessor(professor);
		professor.getTurmasMinistradas().add(turma);
		turma.setProfessor(professor);
		pp.atualizarProfessor(professor);
		tp.atualizarTurma(turma);
	}
	
	public void desmatricularProfessor(Professor professor,Turma turma) {
		professor.getTurmasMinistradas().remove(turma);
		turma.setProfessor(new Professor());
		pp.atualizarProfessor(professor);
		tp.atualizarTurma(turma);
		
	}
	
	//criar
	public void salvarNovoProfessor(Professor professor) throws InvalideFieldException {
		validarProfessor(professor);	
		pp.adicionarNovoProfessor(professor);
	}
	
	public void salvarNovoProfessor(String nome, int idade, String email, String formacao, Genero genero) throws InvalideFieldException {
		Professor professor = new Professor(nome, idade, email, formacao, genero);
		salvarNovoProfessor(professor);		
	}
	
	///atualizar
	public Professor atualizarProfessor(Professor professor) {
		try {
			validarProfessor(professor);
			pp.atualizarProfessor(professor);
			return professor;
		}catch (InvalideFieldException e) {
			return pp.encontrarPeloId(professor.getId());
		}
		
	}
	//pegar
	
	//id
	public Professor procurarProfessorPorId(Integer id) {
		return pp.encontrarPeloId(id);		
	}	
	
	//nome
	public List<Professor> consultarProfessorPorNome(String nome) {
		return pp.consultarProfessorPorNome(nome);
	}
	
	//idade
	public List<Professor> consultarProfessorPorIdade(int idade) {
		return pp.consultarProfessorPorIdade(idade);
	}

	//email
	public List<Professor> consultarProfessorPorEmail(String email) {
		return pp.consultarProfessorPorEmail(email);
		
	}
	//formacao
	public List<Professor> consultarProfessorPorFormacao(String formacao) {
		return pp.consultarProfessorPorFormacao(formacao);
		
	}
	
	//listar
	public List<Professor> listarProfessors() {
		return pp.getProfessores();
	}	
	
	//deletar
	public void deletarProfessor(Professor professor) {
		pp.deletarProfessorPorId(professor.getId());
	}
	

}
