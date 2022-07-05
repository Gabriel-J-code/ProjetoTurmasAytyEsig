package servico;

import java.util.List;

import model.Turma;
import model.Aluno;
import model.Professor;
import model.Sala;
import percistencia.AlunoPercistencia;
import percistencia.ProfessorPercistencia;
import percistencia.TurmaPercistencia;

public class TurmaServico {
	private TurmaPercistencia tp;
	private AlunoPercistencia ap;
	private ProfessorPercistencia pp;
	
		
	public TurmaServico() {
		tp = new TurmaPercistencia();
		ap = new AlunoPercistencia();
		pp = new ProfessorPercistencia();		
		
	}
	
	public void exit() {
		ap.fechar();
	}
	
	//validar
	private void validarTurma(Turma turma) throws InvalideFieldException {
		
		if(turma.getDisciplina()==null ||turma.getDisciplina()=="") {
			throw new InvalideFieldException("Turma com disciplina em branco; ");
		}
		if(turma.getHorario()==null ||turma.getHorario()=="") {
			throw new InvalideFieldException("Turma com horario em branco; ");
		}
		
					
	}
	
	
	
	//criar
	public void salvarTurma(Turma turma) throws InvalideFieldException {
		validarTurma(turma);
		tp.adicionarNovaTurma(turma);		
	}
	
	public void criarTurma(String disciplina, String horario) throws InvalideFieldException {
		Turma turma = new Turma(disciplina, horario);
		salvarTurma(turma);		
	}
	
	//cadastarProfessor
	public void cadastrarProfessor(Turma turma, Professor professor) throws InvalideFieldException {
		validarTurma(turma);
		turma.setProfessor(professor);
		professor.getTurmasMinistradas().add(turma);
		tp.atualizarTurma(turma);
		pp.atualizarProfessor(professor);				
	}
	
	public void descastrarProfessor(Turma turma) {
		tp.removerProfessorDeTurma(turma);
	}
	
	//cadastrarSala
	public void cadastrarSala(Turma turma, Sala sala) throws InvalideFieldException {
		turma.setSala(sala);
		tp.atualizarTurma(turma);				
	}
	
	public void removerSala(Turma turma) {
		//tp.removerSalaDaTurma(turma);
		turma.setSala(null);
		tp.atualizarTurma(turma);
	}
	
	//matricular
		public void matricularAluno(Turma turma, Aluno aluno) throws InvalideFieldException {
			validarTurma(turma);
			turma.getAlunos().add(aluno);
			aluno.getTurmasMatriculadas().add(turma);
			tp.atualizarTurma(turma);
			ap.atualizarAluno(aluno);
			
		}	
		
		public void desmatricularAluno(Turma turma, Aluno aluno){		
			aluno.getTurmasMatriculadas().remove(turma);
			turma.getAlunos().remove(aluno);		
			tp.atualizarTurma(turma);
			ap.atualizarAluno(aluno);
		}
	//pegar
	
	//id
	public Turma procurarTurmaPorId(Integer id) {
		return tp.encontrarPeloId(id);		
	}
	//generico
		
	
	//diciplina
	public List<Turma> procurarTurmaPorDiciplina(String diciplina){		
		return tp.consultarTurmaPorDiciplina(diciplina);
	}
	
	

	//horario
	public List<Turma> procurarTurmaPorHorario(String horario){		
		return tp.consultarTurmaPorHorario(horario);
	}
	
		
	//listar
	public List<Turma> listarTurmas() {
		return tp.getTurmas();
	}
	
	public List<Turma> listarTurmasSemProfessor(){
		return tp.getTurmasSemProfessor();
	}
	
	//deletar
	public void deletarTurma(Turma turma) {
		tp.deletarTurma(turma);
	}
	

}
