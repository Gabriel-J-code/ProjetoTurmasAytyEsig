package percistencia;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Professor;
import model.Turma;

public class  ProfessorPercistencia {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("turmasAyty");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public ProfessorPercistencia() {		
		em = emf.createEntityManager();
		abrir();
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
	public ProfessorPercistencia create(Professor entidade) {
		if (em.isOpen()) {
			em.persist(entidade);
		}
		return this;
	}
	
	//LER
	//todos
	@SuppressWarnings("unchecked")
	public List<Professor> getProfessores() {			
		List<Professor> professores = null;
		if (!em.isOpen()) {	
			professores = em.createQuery("SELECT p FROM Professor p").getResultList();
		}		
		
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
