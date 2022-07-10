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
import model.Turma;
//import model.Turma;

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
		pegarSalasOrdenadasPelaIdentificacao();
	}

	public void onListaSalasChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Sala sala) {
		pegarSalasOrdenadasPelaIdentificacao();
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
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {			
			pegarSalasOrdenadasPelaIdentificacao();
		}
		return this;
	}

	private void pegarSalasOrdenadasPelaIdentificacao() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Sala> criteria = cb.createQuery(Sala.class);
		Root<Sala> sala = criteria.from(Sala.class);
	
		criteria.select(sala).orderBy(cb.asc(sala.get("identificacao")));
		salas = em.createQuery(criteria).getResultList();
	}

	// LER
	// todos
	public List<Sala> getSalas() {
		pegarSalasOrdenadasPelaIdentificacao();
		return salas;
	}

	// por id
	public Sala encontrarSalaPeloId(int id) {		
		Sala salaEncontrada = em.find(Sala.class, id);		
		return salaEncontrada;
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
		return consultarSalaPorCampo("identificacao", numero);
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
	public Sala atualizarSala(Sala sala) {
		
		try {
			abrir();
			sala = em.merge(sala);
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		pegarSalasOrdenadasPelaIdentificacao();
		return sala;
	}

	// Deletar
	
	public SalaPercistencia deletarSalaPorId(int id) {
		try {
			abrir();
			Sala sala = encontrarSalaPeloId(id);
			sala = em.merge(sala);		
			//remover sala de turmas que a usam
			TurmaPercistencia tp = new TurmaPercistencia();
			for(Turma turma : tp.consultarTurmaPorSala(sala)) {
				turma = em.merge(turma);
				turma.setSala(null);
				em.flush();
			}
			em.remove(em.contains(sala)? sala : em.merge(sala));
			fechar();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		pegarSalasOrdenadasPelaIdentificacao();
		return this;
	}
	
	public SalaPercistencia deleteSala(Sala sala) {
		return deletarSalaPorId(sala.getId());
	}

}
