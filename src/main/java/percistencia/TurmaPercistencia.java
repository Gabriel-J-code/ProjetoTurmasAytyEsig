package percistencia;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Aluno;
import model.Professor;
import model.Sala;
import model.Turma;

@RequestScoped
public class  TurmaPercistencia {
	@Produces
    @Named
	private static List<Turma> turmas;
	
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
	
	public TurmaPercistencia() {
		pegarTurmasOrdenadasPorDiciplina();	
		
	}
	
	private TurmaPercistencia abrir() {
		em.getTransaction().begin();
		return this;
	}

	private TurmaPercistencia fechar() {		
		em.getTransaction().commit();
		return this;
	}

	//Criar
	public TurmaPercistencia adicionarNovaTurma(Turma entidade) {
		try {
			abrir();
			em.persist(entidade);			
			fechar();
		}catch (Exception e) {
			em.getTransaction().rollback();
		}finally {
			
			pegarTurmasOrdenadasPorDiciplina();
		}
		return this;
	}

	public void onListaTurmasChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Turma turma) {
		pegarTurmasOrdenadasPorDiciplina();
    }
	@PostConstruct
	private void pegarTurmasOrdenadasPorDiciplina() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
        Root<Turma> turma = criteria.from(Turma.class);       
        criteria.select(turma).orderBy(cb.asc(turma.get("disciplina")));
        turmas = em.createQuery(criteria).getResultList();
	}

	//LER
	//todos
	public List<Turma> getTurmas() {
		pegarTurmasOrdenadasPorDiciplina();
		return turmas;
	}
	//por id
	public Turma encontrarPeloId(int id) {
		Turma encontrada = null;
		if(em.isOpen()) {
			encontrada = em.find(Turma.class, id);
		}
		return encontrada;
	}	
	
	private List<Turma> consultarTurmaPorCampo(String campo, String valor) {
		List<Turma> resultadoConsultaTurmas = new ArrayList<Turma>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
		Root<Turma> turma = criteria.from(Turma.class);
		criteria.select(turma)
			.where(cb.equal(turma.get(campo),valor))
			.orderBy(cb.asc(turma.get(campo)));
		resultadoConsultaTurmas.addAll(em.createQuery(criteria).getResultList());
		
	    criteria.select(turma)
	    	.where(cb.like(turma.get(campo), valor + "%"))
	    	.orderBy(cb.asc(turma.get(campo)));
	    resultadoConsultaTurmas.addAll(em.createQuery(criteria).getResultList());
	    
	    criteria.select(turma)
	    	.where(cb.like(turma.get(campo), "%" + valor + "%"))
	    	.orderBy(cb.asc(turma.get(campo)));
	    resultadoConsultaTurmas.addAll(em.createQuery(criteria).getResultList());
		
		return resultadoConsultaTurmas;
	}
	
	//diciplina
	public List<Turma> consultarTurmaPorDiciplina(String diciplina) {
		return consultarTurmaPorCampo("diciplina", diciplina);	
	}
	
	//horario
	public List<Turma> consultarTurmaPorHorario(String horario) {
		return consultarTurmaPorCampo("horario", horario);	
	}
	
	//sala
	public List<Turma> consultarTurmaPorSala(Sala sala) {
		List<Turma> resultadoConsultaTurmas = new ArrayList<Turma>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
		Root<Turma> turma = criteria.from(Turma.class);
		criteria.select(turma)
			.where(cb.equal(turma.get("sala"), sala))
			.orderBy(cb.asc(turma.get("disciplina")));
		resultadoConsultaTurmas.addAll(em.createQuery(criteria).getResultList());
		return resultadoConsultaTurmas;
		
	}
		
	//aluno
	public List<Aluno> consultarAlunosMatriculadosNaTurma(Turma turma) {
		return (List<Aluno>) turma.getAlunos();		
	}
	
	
	//Atualizar
	public Turma atualizarTurma(Turma turma) {
		
		try {
			abrir();
			turma = em.merge(turma);	
			em.flush();
			fechar();	
		} catch (Exception e) {
			em.getTransaction().rollback();
		}finally {	
			pegarTurmasOrdenadasPorDiciplina();
		}		
		return turma;
	}
	
	
	public List<Turma> getTurmasSemProfessor() {
		List<Turma> resultadoConsultaTurmas = new ArrayList<Turma>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
		Root<Turma> turma = criteria.from(Turma.class);
		criteria.select(turma)
			.where(cb.isNull(turma.get("professor")))
			.orderBy(cb.asc(turma.get("disciplina")));
		resultadoConsultaTurmas.addAll(em.createQuery(criteria).getResultList());
		return resultadoConsultaTurmas;
	}
	
	public void removerProfessorDeTurma(Turma turma) {
		if(turma.getProfessor() != null) {			
			ProfessorPercistencia pp = new ProfessorPercistencia();
			Professor profAntigo = pp.encontrarPeloId(turma.getProfessor().getId());
			profAntigo.getTurmasMinistradas().remove(turma);
			Turma turmaDB = encontrarPeloId(turma.getId());
			turmaDB.setProfessor(null);			
			pp.atualizarProfessor(profAntigo);
			pp.getProfessores();
			atualizarTurma(turmaDB);
			
			/* 	
			abrir();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaUpdate<Turma> update = cb.createCriteriaUpdate(Turma.class);
			Root<Turma> t = update.from(Turma.class);
			update.set("professor", null).where(cb.equal(t.get("id"), turma.getId()));
			int result = em.createQuery(update).executeUpdate();
			if (result == 1 && antigo!=null) {
				antigo.getTurmasMinistradas().remove(turma);			
			}*/
			
		}
			
	}

	
	
	public void cadastrarProfessorATurma(Professor professor, Turma turma) {
		turma.setProfessor(professor);
		professor.getTurmasMinistradas().add(turma);
		atualizarTurma(turma);		
		
	}
	
	public void cadastraSalaATurma(Turma turma, Sala sala) {
		turma.setSala(sala);
		atualizarTurma(turma);
		
	}
	
	public void removerSalaDaTurma(Turma turma) {
		turma.setSala(null);
		atualizarTurma(turma);
	}

	//Deletar
	public TurmaPercistencia deletarTurmaPorId(int id) {
		try {
			abrir();			
			Turma turma = encontrarPeloId(id);
			turma = em.merge(turma);
			//removendo provessor da turma
			Professor professor = em.merge( turma.getProfessor());
			professor.getTurmasMinistradas().remove(turma);
			turma.setProfessor(null);
			//removendo alunos da turma
			for (Aluno aluno : turma.getAlunos()) {
				aluno = em.merge(aluno);
				aluno.getTurmasMatriculadas().remove(turma);
				turma.getAlunos().remove(aluno);
			}			
			em.remove(em.contains(turma) ? turma : em.merge(turma));
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}	
		pegarTurmasOrdenadasPorDiciplina();
		return this;
	}

	public TurmaPercistencia deletarTurma(Turma turma) {
		return deletarTurmaPorId(turma.getId());
	}
	
	/*public void removerSalaDaTurma(Turma turma) {
		abrir();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaUpdate<Turma> update = cb.createCriteriaUpdate(Turma.class);
		Root<Turma> t = update.from(Turma.class);
		update.set("sala", null).where(cb.equal(t.get("id"), turma.getId()));
		em.createQuery(update).executeUpdate();		
	}*/
		
}
