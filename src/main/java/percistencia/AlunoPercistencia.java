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
import model.Genero;
import model.Turma;


public class  AlunoPercistencia {
	
	private static List<Aluno> alunos = new ArrayList<Aluno>();
		
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
		
	public AlunoPercistencia() {	
		abrir();
		pegarAlunosOrdenadosPorNome();
	}
	
	public void onListaAlunosChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Aluno aluno) {
        pegarAlunosOrdenadosPorNome();
    }
		
	private void pegarAlunosOrdenadosPorNome() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Aluno_.name)));
        criteria.select(aluno).orderBy(cb.asc(aluno.get("nome")));
       alunos = em.createQuery(criteria).getResultList(); 		
	}

	private AlunoPercistencia abrir() {
		em.getTransaction().begin();
		return this;
	}
	
	public AlunoPercistencia fechar() {
		
		em.getTransaction().commit();
		return this;
	}
	
	//Criar
	public AlunoPercistencia adicionarNovoAluno(Aluno aluno) {
		try {
			em.getTransaction().begin();
			em.persist(aluno);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}finally {
			pegarAlunosOrdenadosPorNome();
		}
		
		return this;
	}
	
	//LER
	//todos
	public List<Aluno> getAlunos() {		
		return alunos;
	}
	//por id
	public Aluno encontrarPeloId(int id) {
		Aluno encotrado = em.find(Aluno.class, id);				
		return encotrado;
	}
	
	//	
	//busca por campo
	private List<Aluno> consultarAlunoPorCampo(String campo, String valor){
		ArrayList<Aluno> resultadoConsultaAlunos = new ArrayList<Aluno>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Aluno_.name)));
       criteria.select(aluno).where(cb.equal(aluno.get(campo),valor)).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       criteria.select(aluno).where(cb.like(aluno.get(campo), valor + "%")).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       criteria.select(aluno).where(cb.like(aluno.get(campo), "%" + valor + "%")).orderBy(cb.asc(aluno.get(campo)));
       resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
       return resultadoConsultaAlunos;
	}
	
	
	//nome
	public List<Aluno> consultarAlunoPorNome(String nome) {
		return consultarAlunoPorCampo("nome", nome);
	}
	//idade
	public List<Aluno> consultarAlunoPorIdade(int idade) {
		ArrayList<Aluno> resultadoConsultaAlunos = new ArrayList<Aluno>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
        Root<Aluno> aluno = criteria.from(Aluno.class);
        criteria.select(aluno).where(cb.equal(aluno.get("idade"), idade)).orderBy(cb.asc(aluno.get("nome")));
        resultadoConsultaAlunos.addAll(em.createQuery(criteria).getResultList());
        return resultadoConsultaAlunos;
	}
	
	//email
	public List<Aluno> consultarAlunoPorEmail(String email) {
		return consultarAlunoPorCampo("email", email);
	}
	//curso
	public List<Aluno> consultarAlunoPorCurso(String curso) {
		return consultarAlunoPorCampo("curso", curso);
	}
	
	//matricula
	public List<Aluno> consultarAlunoPorMatricula(String matricula) {
		return consultarAlunoPorCampo("matricula", matricula);
	}
	
	//genero	
	public List<Aluno> consultarAlunoPorGenero(String genero) {
		return consultarAlunoPorCampo("genero", genero);
	}
	
	public List<Aluno> consultarAlunoPorGenero(Genero genero) {
		return consultarAlunoPorGenero(genero.name());
	}	
	
	//turmas
	public List<Turma> listarTurmasDoId(int idAluno) {		
		Aluno aluno = encontrarPeloId(idAluno);
        return (List<Turma>) aluno.getTurmasMatriculadas();
		
	}
	
	//Atualizar
	public Aluno atualizarAluno(Aluno aluno) {
		Aluno a = null;
			try {
				em.getTransaction().begin();
				a = em.merge(aluno);
				 em.getTransaction().commit();
			} catch (Exception e) {
				 em.getTransaction().rollback();
			}finally {
				pegarAlunosOrdenadosPorNome();
			}				
		return a;
			
	}
	//Deletar
	public AlunoPercistencia deletarAlunoPorId(int id) {
		try {
			em.getTransaction().begin();
			Aluno obj = encontrarPeloId(id);
			em.remove(em.contains(obj) ? obj : em.merge(obj));
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
		}finally {			
			pegarAlunosOrdenadosPorNome();
		}
		return this;
	}
	
		
}