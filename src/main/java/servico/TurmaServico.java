package servico;

import java.util.ArrayList;
import java.util.List;

import model.Turma;
import model.Aluno;
import model.Professor;
import model.Sala;
import percistencia.AlunoPercistencia;
import percistencia.ProfessorPercistencia;
import percistencia.SalaPercistencia;
import percistencia.TurmaPercistencia;

public class TurmaServico {
	private TurmaPercistencia tp;
	private AlunoPercistencia ap;
	private ProfessorPercistencia pp;
	private SalaPercistencia sp;
	
		
	public TurmaServico() {
		tp = new TurmaPercistencia();
		ap = new AlunoPercistencia();
		pp = new ProfessorPercistencia();
		sp = new SalaPercistencia();
		
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
	
	//cadastrarSala
	public void cadastrarSala(Turma turma, Sala sala) throws InvalideFieldException {
		validarTurma(turma);
		turma.setSala(sala);
		sp.atualizarTurma(sala);
		tp.atualizarTurma(turma);				
	}
	
	//matricular
		public void matricularAluno(Aluno aluno,Turma turma) throws InvalideFieldException {
			validarTurma(turma);
			turma.getAlunos().add(aluno);
			aluno.getTurmasMatriculadas().add(turma);
			tp.atualizarTurma(turma);
			ap.atualizarAluno(aluno);
			
		}	
	//pegar
	
	//id
	public Turma procurarTurmaPorId(Integer id) {
		return tp.encontrarPeloId(id);		
	}
	//generico
	private List<Turma> procurarTurmaPorCampo(String campo, String valor){		
		ArrayList<Turma> resultadoConsultaTurmas = new ArrayList<Turma>();
		resultadoConsultaTurmas.addAll(
				tp.consultaSQL(
						String.format(
								"SELECT a FROM Turma a WHERE %s = '%s' ORDER BY %s",campo, valor,campo)));
		resultadoConsultaTurmas.addAll(
				tp.consultaSQL(
						String.format("SELECT a FROM Turma a WHERE %s LIKE '%%%s%%' AND %s <> '%s' ORDER BY %s" ,
								campo,valor,campo,valor,campo)));						
		return resultadoConsultaTurmas;
	}
	
	
	//diciplina
	public List<Turma> procurarTurmaPorDiciplina(String diciplina){		
		return procurarTurmaPorCampo("diciplina", diciplina);
	}
	
	

	//horario
	public List<Turma> procurarTurmaPorEmail(String horario){		
		return procurarTurmaPorCampo("horario", horario);
	}
	
	
	//turmas
	public List<Turma> listarTurmaMatriculadaDeTurma(Turma turma){		
		List<Turma> resultadoConsultaTurmas = ap.listarTurmasDoId(turma.getId());					
		return resultadoConsultaTurmas;
	}	
	
	//listar
	public List<Turma> listarTurmas() {
		return tp.getTurmas();
	}
	
	//deletar
	public void deletarTurma(Turma turma) {
		ap.deletarAlunoPorId(turma.getId());
	}
	

}
