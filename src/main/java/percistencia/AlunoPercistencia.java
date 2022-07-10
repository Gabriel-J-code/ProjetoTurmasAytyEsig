package percistencia;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Aluno;
import model.Genero;
import model.Turma;


public class  AlunoPercistencia {
	
	private static List<Aluno> alunos = new ArrayList<Aluno>();
		
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {
		try {			
			emf = Persistence.createEntityManagerFactory("TurmasAytyEsig");
			em = emf.createEntityManager();
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
		
	public AlunoPercistencia() {			
		pegarAlunosOrdenadosPorNome();
	}
	
	public void onListaAlunosChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Aluno aluno) {
        pegarAlunosOrdenadosPorNome();
    }
		
	private void pegarAlunosOrdenadosPorNome() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Aluno_.name)));
        criteria.select(aluno).orderBy(cb.asc(aluno.get("nome")));
       alunos = em.createQuery(criteria).getResultList(); 		
	}

	private AlunoPercistencia abrir() {
		em.getTransaction().begin();
		return this;
	}
	
	public AlunoPercistencia fechar() {
		
		em.getTransaction().commit();
		return this;
	}
	
	//Criar
	public AlunoPercistencia adicionarNovoAluno(Aluno aluno) {
		try {
			abrir();
			em.persist(aluno);	
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}finally {			
			pegarAlunosOrdenadosPorNome();
		}
		
		return this;
	}
	
	//LER
	//todos
	public List<Aluno> getAlunos() {	
		pegarAlunosOrdenadosPorNome();
		return alunos;
	}
	//por id
	private Aluno encontrarAlunoPeloId(int id) {
		Aluno encotrado = em.find(Aluno.class, id);				
		return encotrado;
	}
	
	public Aluno pegarDadosAtualizadosDoAluno(Aluno aluno) {
		return encontrarAlunoPeloId(aluno.getId());	
	}
	
	//	
	//busca por campo
	private List<Aluno> consultarAlunoPorCampo(String campo, String valor){
		ArrayList<Aluno> resultadoConsultaAlunos = new ArrayList<Aluno>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Aluno_.name)));
       criteria.select(aluno).where(cb.equal(aluno.get(campo),valor)).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       criteria.select(aluno).where(cb.like(aluno.get(campo), valor + "%")).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       criteria.select(aluno).where(cb.like(aluno.get(campo), "%" + valor + "%")).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       return resultadoConsultaAlunos;
	}
	
	
	//nome
	public List<Aluno> consultarAlunoPorNome(String nome) {
		return consultarAlunoPorCampo("nome", nome);
	}
	//idade
	public List<Aluno> consultarAlunoPorIdade(Date nascimento) {
		ArrayList<Aluno> resultadoConsultaAlunos = new ArrayList<Aluno>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        criteria.select(aluno).where(cb.equal(aluno.get("idade"), nascimento)).orderBy(cb.asc(aluno.get("nome")));
        resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
        return resultadoConsultaAlunos;
	}
	
	//email
	public List<Aluno> consultarAlunoPorEmail(String email) {
		return consultarAlunoPorCampo("email", email);
	}
	//curso
	public List<Aluno> consultarAlunoPorCurso(String curso) {
		return consultarAlunoPorCampo("curso", curso);
	}
	
	//matricula
	public List<Aluno> consultarAlunoPorMatricula(String matricula) {
		return consultarAlunoPorCampo("matricula", matricula);
	}
	
	//genero	
	public List<Aluno> consultarAlunoPorGenero(String genero) {
		return consultarAlunoPorCampo("genero", genero);
	}
	
	public List<Aluno> consultarAlunoPorGenero(Genero genero) {
		return consultarAlunoPorGenero(genero.name());
	}	
	
	public List<String> listarMatriculas() {
		ArrayList<String> resultadoConsultaMatriculas = new ArrayList<String>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        criteria.select(aluno.get("matricula")).orderBy(cb.asc(aluno.get("matricula")));
        resultadoConsultaMatriculas.addAll(em.createQuery(criteria).getResultList());
        return resultadoConsultaMatriculas;
	}
	
	public List<String> listarMatriculasDoAno(String ano) {
		ArrayList<String> resultadoConsultaMatriculas = new ArrayList<String>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        criteria.select(aluno.get("matricula"))
        			.where(cb.like(aluno.get("matricula"), ano + "%"))
        				.orderBy(cb.asc(aluno.get("matricula")));
        resultadoConsultaMatriculas.addAll(em.createQuery(criteria).getResultList());
        return resultadoConsultaMatriculas;
	}
	//turmas
	
	
	private List<Turma> listarTurmasDoId(int idAluno) {		
		Aluno aluno = encontrarAlunoPeloId(idAluno);		
        return (List<Turma>) aluno.getTurmasMatriculadas();
        		
	}
	
	public List<Turma> listarTurmasDoAluno(Aluno aluno) {
		return listarTurmasDoId(aluno.getId());
	}
	
	public List<Turma> listarTurmasDisponiveisParaOAluno(Aluno aluno) {
				
		TurmaPercistencia tp = new TurmaPercistencia();
		List<Turma> turmasDisponiveis = tp.getTurmas();
		turmasDisponiveis.removeAll(listarTurmasDoAluno(aluno));
        return turmasDisponiveis;
        
		
		
	}
	
	//Atualizar
	public Aluno atualizarAluno(Aluno aluno) {
		
			try {
				abrir();				
				aluno = em.merge(aluno);
				fechar();
			} catch (Exception e) {
				 em.getTransaction().rollback();
			}finally {				
				pegarAlunosOrdenadosPorNome();
			}				
		return aluno;
			
	}
	//Deletar
	private AlunoPercistencia deletarAlunoPorId(int id) {
		try {
			abrir();
			Aluno alunoDB = encontrarAlunoPeloId(id);
			alunoDB = em.merge(alunoDB);
			for (Turma turma : alunoDB.getTurmasMatriculadas()) {
				turma = em.merge(turma);
				alunoDB.getTurmasMatriculadas().remove(turma);
				turma.getAlunos().remove(alunoDB);
				em.flush();
			}
			em.remove(em.contains(alunoDB) ? alunoDB : em.merge(alunoDB));
			fechar();
		}catch (Exception e) {
			em.getTransaction().rollback();
		}finally {				
			pegarAlunosOrdenadosPorNome();
		}
		return this;
	}
	
	public AlunoPercistencia deletarAluno(Aluno aluno) {
		return deletarAlunoPorId(aluno.getId());
		
	}
	
	//
	
	//Matricular
	public Aluno matricularAlunoATurma(Aluno aluno, Turma turma) {
		Aluno alunoDB = encontrarAlunoPeloId(aluno.getId());
		if (alunoDB != null) {
			TurmaPercistencia  tp = new TurmaPercistencia();
			Turma turmaDB = tp.encontrarPeloId(turma.getId());
			if(turmaDB != null) {
				boolean alunoNaoEstaNaListaDaTurma = !turmaDB.getAlunos().contains(alunoDB);		
				boolean turmaNaoEstaNaListaDoAluno = !alunoDB.getTurmasMatriculadas().contains(turmaDB);
				if (alunoNaoEstaNaListaDaTurma) {
						turmaDB.getAlunos().add(alunoDB);
				}
				if (turmaNaoEstaNaListaDoAluno) {
					alunoDB.getTurmasMatriculadas().add(turmaDB);
				}				
				tp.atualizarTurma(turmaDB);
				alunoDB = atualizarAluno(alunoDB);
				tp.getTurmas();
				getAlunos();
			}
		}		
		return alunoDB;	
	}
	//Dematricular
	public Aluno dematricularAlunoDeTurma(Aluno aluno, Turma turma) {		
		try {
			abrir();
			aluno = em.merge(aluno);
			turma = em.merge(turma);
			aluno.getTurmasMatriculadas().remove(turma);
			turma.getAlunos().remove(aluno);
			/*em.createNativeQuery(
					String.format("DELETE FROM tbl_matriculas WHERE id_aluno = %d AND id_turma = %d",
							idAluno, turma.getId())).executeUpdate();*/
					
			fechar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getAlunos();
		return aluno;
	}
	
		
}
