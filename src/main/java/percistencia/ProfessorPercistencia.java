package percistencia;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

public class  ProfessorPercistencia {
	
	private static List<Professor> professores = new ArrayList<Professor>();
	
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
	
	public ProfessorPercistencia() {			
		abrir();
	}
	
	public void onListaProfessoresChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Professor professor) {
        pegarProfessoresOrdenadosPorNome();
    }	
	
	private void pegarProfessoresOrdenadosPorNome() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
        Root<Professor> professor = criteria.from(Professor.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Aluno_.name)));
        criteria.select(professor).orderBy(cb.asc(professor.get("nome")));
       professores = em.createQuery(criteria).getResultList();
		
	}
	private ProfessorPercistencia abrir() {
		em.getTransaction().begin();		
		return this;
	}
	
	public ProfessorPercistencia fechar() {
		em.getTransaction().commit();
		return this;
	}
	
	//Criar
	public ProfessorPercistencia adicionarNovoProfessor(Professor professor) {
		if (em.isOpen()) {
			em.persist(professor);
		}
		return this;
	}
	
	//LER
	//todos
	
	public static List<Professor> getProfessores() {				
		return professores;
	}
	//por id
	public Professor encontrarPeloId(int id) {
		Professor encotrado = new Professor();
		if (em.isOpen()) {
			encotrado = em.find(Professor.class, id);
		}
		return encotrado;
	}
	@SuppressWarnings("unchecked")
	public List<Professor> consultaSQL(String sql) {
		List<Professor> professores = new ArrayList<Professor>();
		if (em.isOpen()) {		
			professores = em.createQuery(sql).getResultList();
		}
		return professores;
	}
	//Atualizar
	public Professor atualizar(Professor professor) {
		Professor p = null;
		if (em.isOpen()) {		
			p = em.merge(professor);		
		}		
		return p;
	}
	//Deletar
	public ProfessorPercistencia delete(int id) {
		if (em.isOpen()) {
			ProfessorPercistencia dao = new ProfessorPercistencia();
			Professor obj = dao.encontrarPeloId(id);
			em.remove(em.contains(obj) ? obj : em.merge(obj));
		}
		return this;
	}

	public List<Turma> listarTurmasDoId(int idProfessor) {
		Professor professor = encontrarPeloId(idProfessor);
		return (List<Turma>) professor.getTurmasMinistradas();
	}

	
	
		
}
