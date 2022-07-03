package percistencia;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Turma;

public class  TurmaPercistencia {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("turmasAyty");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public TurmaPercistencia() {
		
		em = emf.createEntityManager();
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
	public TurmaPercistencia create(Turma entidade) {
		abrir();
		em.persist(entidade);
		fechar();
		return this;
	}
	
	//LER
	//todos
	public List<Turma> getTurmas() {
		abrir();
		@SuppressWarnings("unchecked")
		List<Turma> Turmas = em.createQuery("SELECT t FROM Turma t").getResultList();
		fechar();
		return Turmas;
	}
	//por id
	public Turma encontrarPeloId(int id) {
		abrir();
		Turma encotrado = em.find(Turma.class, id);
		fechar();
		return encotrado;
	}
	public List<Turma> consultaSQL(String sql) {
		abrir();
		@SuppressWarnings("unchecked")
		List<Turma> Turmas = em.createQuery(sql).getResultList();
		fechar();
		return Turmas;
	}
	//Atualizar
	public Turma atualizar(Turma Turma) {
		abrir();		
		Turma p = em.merge(Turma);		
		fechar();		
		return p;
	}
	//Deletar
	public TurmaPercistencia delete(int id) {
		abrir();
		TurmaPercistencia dao = new TurmaPercistencia();
		Turma obj = dao.encontrarPeloId(id);
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		fechar();
		return this;
	}
	
		
}
