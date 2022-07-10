package servico;

import java.util.List;

import model.Professor;
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
			
	//matricular
	public void matricularProfessor(Professor professor,Turma turma) {
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
	public void salvarNovoProfessor(Professor professor){
		pp.adicionarNovoProfessor(professor);
	}
		
	///atualizar
	public Professor atualizarProfessor(Professor professor) {		
		professor = pp.atualizarProfessor(professor);					
		return professor;		
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
		
	//email
	public List<Professor> consultarProfessorPorEmail(String email) {
		return pp.consultarProfessorPorEmail(email);
		
	}
	//formacao
	public List<Professor> consultarProfessorPorFormacao(String formacao) {
		return pp.consultarProfessorPorFormacao(formacao);
		
	}
	
	//listar
	public List<Professor> listarProfessores() {
		return pp.getProfessores();
	}	
	
	//deletar
	public void deletarProfessor(Professor professor) {
		pp.deletarProfessorPorId(professor.getId());
	}
	

}
