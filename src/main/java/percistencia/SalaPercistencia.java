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

import model.Sala;

public class SalaPercistencia {

	private static List<Sala> salas = new ArrayList<Sala>();

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

	public SalaPercistencia() {
		pegarSalasOrdenadosPorNumero();
	}

	public void onListaSalasChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Sala sala) {
		pegarSalasOrdenadosPorNumero();
	}

	private void pegarSalasOrdenadosPorNumero() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sala> criteria = cb.createQuery(Sala.class);
		Root<Sala> sala = criteria.from(Sala.class);

		criteria.select(sala).orderBy(cb.asc(sala.get("numero")));
		salas = em.createQuery(criteria).getResultList();
	}

	private SalaPercistencia abrir() {
		em.getTransaction().begin();
		return this;
	}

	public SalaPercistencia fechar() {
		em.getTransaction().commit();
		return this;
	}

	// Criar
	public SalaPercistencia adicionarNovaSala(Sala sala) {
		try {
			abrir();
			em.persist(sala);
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			fechar();
			pegarSalasOrdenadosPorNumero();
		}
		return this;
	}

	// LER
	// todos
	public List<Sala> getSalas() {
		return salas;
	}

	// por id
	public Sala encontrarSalaPeloId(int id) {
		Sala salaEncontrada = null;
		if (em.isOpen()) {
			salaEncontrada = em.find(Sala.class, id);
		}
		return salaEncontrada;
	}

	public List<Sala> consultaSQL(String sql) {
		abrir();
		@SuppressWarnings("unchecked")
		List<Sala> Salas = em.createQuery(sql).getResultList();
		fechar();
		return Salas;
	}

	private List<Sala> consultarSalaPorCampo(String campo, String valor) {
		List<Sala> resultadoConsultaSalas = new ArrayList<Sala>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sala> criteria = cb.createQuery(Sala.class);
		Root<Sala> sala = criteria.from(Sala.class);
		criteria.select(sala).where(cb.equal(sala.get(campo), valor)).orderBy(cb.asc(sala.get(campo)));
		resultadoConsultaSalas.addAll(em.createQuery(criteria).getResultList());

		criteria.select(sala).where(cb.like(sala.get(campo), valor + "%")).orderBy(cb.asc(sala.get(campo)));
		resultadoConsultaSalas.addAll(em.createQuery(criteria).getResultList());

		criteria.select(sala).where(cb.like(sala.get(campo), "%" + valor + "%")).orderBy(cb.asc(sala.get(campo)));
		resultadoConsultaSalas.addAll(em.createQuery(criteria).getResultList());

		return resultadoConsultaSalas;
	}

	// numero
	public List<Sala> contultarSalaPorNumero(String numero) {
		return consultarSalaPorCampo("numero", numero);
	}

	// predio
	public List<Sala> contultarSalaPorPredio(String predio) {
		return consultarSalaPorCampo("predio", predio);
	}

	// campus
	public List<Sala> contultarSalaPorCampus(String campus) {
		return consultarSalaPorCampo("campus", campus);
	}

	// Atualizar
	public Sala atualizarSala(Sala Sala) {
		Sala s = null;
		try {
			abrir();
			s = em.merge(Sala);
			em.flush();
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {

			pegarSalasOrdenadosPorNumero();
		}
		return s;
	}

	// Deletar
	public SalaPercistencia deleteSalaPorId(int id) {
		try {
			abrir();
			SalaPercistencia sp = new SalaPercistencia();
			Sala s = sp.encontrarSalaPeloId(id);			
			em.remove(em.contains(s) ? s : em.merge(s));
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		pegarSalasOrdenadosPorNumero();
		return this;
	}

	public SalaPercistencia deleteSala(Sala sala) {
		return deleteSalaPorId(sala.getId());
	}

}
