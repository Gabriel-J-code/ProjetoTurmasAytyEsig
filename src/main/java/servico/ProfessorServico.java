package servico;

import java.util.ArrayList;
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
		pp.atualizar(professor);
		tp.atualizar(turma);
	}	
	
	//criar
	public void salvarProfessor(Professor professor) throws InvalideFieldException {
		validarProfessor(professor);
		pp.adicionarNovoAluno(professor);		
	}
	
	public void criarProfessor(String nome, int idade, String email, String formacao, Genero genero) throws InvalideFieldException {
		Professor professor = new Professor(nome, idade, email, formacao, genero);
		salvarProfessor(professor);		
	}
	//pegar
	
	//id
	public Professor procurarProfessorPorId(Integer id) {
		return pp.encontrarPeloId(id);		
	}
	
	public List<Professor> procurarProfessorPorCampo(String campo, String valor){		
		ArrayList<Professor> resultadoConsultaSalas = new ArrayList<Professor>();
		resultadoConsultaSalas.addAll(
				pp.consultaSQL(
						String.format(
								"SELECT a FROM Professor a WHERE %s = '%s' ORDER BY %s",campo, valor,campo)));
		resultadoConsultaSalas.addAll(
				pp.consultaSQL(
						String.format("SELECT a FROM Professor a WHERE %s LIKE '%%%s%%' AND %s <> '%s' ORDER BY %s" ,
								campo,valor,campo,valor,campo)));						
		return resultadoConsultaSalas;
	}
	
	//nome
	public List<Professor> procurarProfessorPorNome(String nome){		
		return procurarProfessorPorCampo("nome", nome);
	}
	
	//idade
	public List<Professor> procurarProfessorPorIdade(int idade){		
		ArrayList<Professor> resultadoConsultaProfessors = new ArrayList<Professor>();
		resultadoConsultaProfessors.addAll(
				pp.consultaSQL(
						String.format(
								"SELECT a FROM Professor a WHERE idade = '%d' ORDER BY nome",idade)));					
		return resultadoConsultaProfessors;
	}

	//email
	public List<Professor> procurarProfessorPorEmail(String email){		
		return procurarProfessorPorCampo("email", email);
	}
	
	//curso
	public List<Professor> procurarProfessorPorFormacao(String formacao){		
		return procurarProfessorPorCampo("formacao", formacao);
	}	
	//genero
	public List<Professor> procurarProfessorPorCurso(Genero genero){		
		ArrayList<Professor> resultadoConsultaProfessors = new ArrayList<Professor>();
		resultadoConsultaProfessors.addAll(
				pp.consultaSQL(
						String.format(
								"SELECT a FROM Professor a WHERE genero = '%s' ORDER BY nome",genero.toString())));
		return resultadoConsultaProfessors;
	}
	//turmas
	public List<Turma> listarTurmaMatriculadaDeProfessor(Professor professor){		
		List<Turma> resultadoConsultaTurmas = pp.listarTurmasDoId(professor.getId());					
		return resultadoConsultaTurmas;
	}	
	
	//listar
	public List<Professor> listarProfessors() {
		return pp.getProfessores();
	}
	
	//deletar
	public void deletarProfessor(Professor professor) {
		pp.delete(professor.getId());
	}
	

}
