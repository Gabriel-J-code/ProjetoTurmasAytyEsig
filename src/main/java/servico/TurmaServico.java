package servico;

import java.util.List;

import model.Turma;
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
	
		
	//criar
	public void salvarTurma(Turma turma){		
		tp.adicionarNovaTurma(turma);		
	}
	
	public void criarTurma(String disciplina, String horario){
		Turma turma = new Turma(disciplina, horario);
		salvarTurma(turma);		
	}
	
	//cadastarProfessor		
	public void cadastrarProfessorATurma(Professor professor, Turma turma) {
		tp.cadastrarProfessorATurma(professor, turma);
		
	}

	public void descastrarProfessor(Turma turma) {
		tp.removerProfessorDeTurma(turma);
	}
	
	//cadastrarSala
	public void cadastrarSala(Turma turma, Sala sala){
		turma.setSala(sala);
		tp.atualizarTurma(turma);				
	}
	
	public void removerSala(Turma turma) {
		//tp.removerSalaDaTurma(turma);
		tp.removerSalaDaTurma(turma);
	}
	
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
