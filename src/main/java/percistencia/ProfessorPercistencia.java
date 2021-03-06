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

import model.Genero;
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
		pegarProfessoresOrdenadosPorNome();
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
		try {
			abrir();
			em.persist(professor);			
		} catch (Exception e) {
			em.getTransaction().rollback();
		}finally {
			 em.getTransaction().commit();
			pegarProfessoresOrdenadosPorNome();;
		}
		return this;
	}
	
	//LER
	//todos
	
	public List<Professor> getProfessores() {				
		return professores;
	}
	//por id
	public Professor encontrarPeloId(int id) {
		Professor encontrado = null;
		if (em.isOpen()) {
			encontrado = em.find(Professor.class, id);
		}
		return encontrado;
	}
	
	private List<Professor> consultarProfessorPorCampo(String campo, String valor) {
		List<Professor> resultadoConsultaProfessores = new ArrayList<Professor>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
		Root<Professor> professor = criteria.from(Professor.class);
		criteria.select(professor)
			.where(cb.equal(professor.get(campo),valor))
			.orderBy(cb.asc(professor.get(campo)));
		resultadoConsultaProfessores.addAll(em.createQuery(criteria).getResultList());
		
	    criteria.select(professor)
	    	.where(cb.like(professor.get(campo), valor + "%"))
	    	.orderBy(cb.asc(professor.get(campo)));
	    resultadoConsultaProfessores.addAll(em.createQuery(criteria).getResultList());
	    
	    criteria.select(professor)
	    	.where(cb.like(professor.get(campo), "%" + valor + "%"))
	    	.orderBy(cb.asc(professor.get(campo)));
	    resultadoConsultaProfessores.addAll(em.createQuery(criteria).getResultList());
		
		return resultadoConsultaProfessores;
	}
	
	public List<Professor> consultarProfessorPorNome(String nome){		
		return consultarProfessorPorCampo("nome", nome);
	}
	
	public List<Professor> consultarProfessorPorEmail(String email){		
		return consultarProfessorPorCampo("email", email);
	}
	
	//formacao
	public List<Professor> consultarProfessorPorFormacao(String formacao){		
		return consultarProfessorPorCampo("formacao", formacao);
	}	
	//genero
	public List<Professor> consultarProfessorPorGenero(Genero genero){		
		ArrayList<Professor> resultadoConsultaProfessores = new ArrayList<Professor>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
		Root<Professor> professor = criteria.from(Professor.class);
		criteria.select(professor)
			.where(cb.equal(professor.get("genero"), genero.name()))
			.orderBy(cb.asc(professor.get("idade")));
		resultadoConsultaProfessores.addAll(em.createQuery(criteria).getResultList());					
		return resultadoConsultaProfessores;
	}
	//turmas
	public List<Turma> listarTurmaMatriculadaDeProfessor(Professor professor){	
		return (List<Turma>) encontrarPeloId(professor.getId()).getTurmasMinistradas();
	}
	//Atualizar
	public Professor atualizarProfessor(Professor professor) {
		Professor p = null;
		try {
			abrir();
			p = em.merge(professor);
			em.flush();
			fechar();
		} catch (Exception e) {
			 em.getTransaction().rollback();
		}
		pegarProfessoresOrdenadosPorNome();	
		return p;
	}
	//Deletar
	public ProfessorPercistencia deletarProfessorPorId(int id) {
		try {
			abrir()	;		
			Professor professor = encontrarPeloId(id);
			professor = em.merge(professor);
			for(Turma turma : professor.getTurmasMinistradas()) {
				turma = em.merge(turma);
				turma.setProfessor(null);
				professor.getTurmasMinistradas().remove(turma);
			}
			em.remove(em.contains(professor) ? professor : em.merge(professor));
			fechar();
		} catch (Exception e) {
			 em.getTransaction().rollback();
		}
			 
		pegarProfessoresOrdenadosPorNome();		
		return this;
	}

	public ProfessorPercistencia deletarProfessor(Professor professor) {
		return deletarProfessorPorId(professor.getId());				
	}

	public ProfessorPercistencia removerProfessorDaTurma(Professor professor, Turma turma) {
		if(turma.getProfessor().equals(professor)) {
			TurmaPercistencia tp = new TurmaPercistencia();
			tp.removerProfessorDeTurma(turma);
		}
		getProfessores();
		return this;
	}

	

	
	
		
}
