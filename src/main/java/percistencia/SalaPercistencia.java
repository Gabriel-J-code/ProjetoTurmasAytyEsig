package percistencia;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Sala;

public class  SalaPercistencia {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("turmasAyty");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public SalaPercistencia() {
		
		em = emf.createEntityManager();
		abrir();
	}
	
	private SalaPercistencia abrir() {
		em.getTransaction().begin();
		return this;
	}
	
	public SalaPercistencia fechar() {
		em.getTransaction().commit();
		return this;
	}
	
	//Criar
	public SalaPercistencia create(Sala entidade) {
		abrir();
		em.persist(entidade);
		fechar();
		return this;
	}
	
	//LER
	//todos
	public List<Sala> getSalas() {
		abrir();
		@SuppressWarnings("unchecked")
		List<Sala> Salas = em.createQuery("SELECT s FROM Sala s").getResultList();
		fechar();
		return Salas;
	}
	//por id
	public Sala encontrarPeloId(int id) {
		abrir();
		Sala encotrado = em.find(Sala.class, id);
		fechar();
		return encotrado;
	}
	public List<Sala> consultaSQL(String sql) {
		abrir();
		@SuppressWarnings("unchecked")
		List<Sala> Salas = em.createQuery(sql).getResultList();
		fechar();
		return Salas;
	}
	//Atualizar
	public Sala atualizar(Sala Sala) {
		abrir();		
		Sala p = em.merge(Sala);		
		fechar();		
		return p;
	}
	//Deletar
	public SalaPercistencia delete(int id) {
		abrir();
		SalaPercistencia dao = new SalaPercistencia();
		Sala obj = dao.encontrarPeloId(id);
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		fechar();
		return this;
	}
	
		
}
