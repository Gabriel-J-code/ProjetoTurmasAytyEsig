package percistencia;


import java.util.ArrayList;
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
import model.Professor;
import model.Turma;

public class  TurmaPercistencia {
	
	private static List<Turma> turmas = new ArrayList<Turma>();
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("turmasAyty");
			em = emf.createEntityManager();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public TurmaPercistencia() {
		abrir();
		pegarTurmasOrdenadasPorDiciplina();
		
	}
	
	public void onListaTurmasChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Turma turma) {
		pegarTurmasOrdenadasPorDiciplina();
    }
	
	private void pegarTurmasOrdenadasPorDiciplina() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Turma> criteria = cb.createQuery(Turma.class);
        Root<Turma> turma = criteria.from(Turma.class);       
        criteria.select(turma).orderBy(cb.asc(turma.get("disciplina")));
        turmas = em.createQuery(criteria).getResultList();
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
	
	//LER
	//todos
	public List<Turma> getTurmas() {		
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
		
	//aluno
	public List<Aluno> consultarAlunosMatriculadosNaTurma(Turma turma) {
		return (List<Aluno>) turma.getAlunos();		
	}
	
	//Atualizar
	public Turma atualizarTurma(Turma Turma) {
		abrir();		
		Turma p = em.merge(Turma);		
		fechar();		
		return p;
	}
	//Deletar
	public TurmaPercistencia deletarTurmaPorId(int id) {
		abrir();
		TurmaPercistencia dao = new TurmaPercistencia();
		Turma obj = dao.encontrarPeloId(id);
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		fechar();
		return this;
	}
	
		
}
