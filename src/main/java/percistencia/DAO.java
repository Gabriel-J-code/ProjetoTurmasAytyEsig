package percistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO<E> {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private Class<E> entidade;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("turmasAyty");
			em = emf.createEntityManager();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public DAO(Class<E> entidade) {
		this.entidade = entidade;		
	}
	
	public DAO<E> abrir() {
		em.getTransaction().begin();
		return this;
	}
	
	public static void fechar() {
		if (em.isOpen()) {
		em.getTransaction().commit();
		}
	}
	public DAO<E> create(E entidade) {
		em.persist(entidade);
		return this;
	}
	
	public E encontrarPeloId(Object id) {
		return em.find(entidade, id);
	}
	
	public DAO<E> delete(int id) {
		DAO<E> dao = new DAO<E>(entidade);
		E obj = dao.encontrarPeloId(id);
		em.remove(em.contains(obj) ? obj : em.merge(obj));
		return this;
	}
	
	public E atualizar(E obj) {				
		E alvo = (E)em.merge(obj);					
		return alvo;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<E> consultaSQL(String sql) {
		List<E> resultado = new ArrayList<E>();
		if (em.isOpen()) {		
			resultado = em.createQuery(sql).getResultList();
		}
		return resultado;
	}
	
}
