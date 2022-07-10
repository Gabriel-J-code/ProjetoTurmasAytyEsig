package controle;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import model.Genero;
import model.Professor;
import model.Turma;
import servico.ProfessorServico;
import servico.TurmaServico;

import java.io.IOException;
import java.io.Serializable;

@Named("professorCon")
@SessionScoped
public class ProfessorControle implements Serializable {
	
	private Professor professorFoco;
	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Professor> professores;
	private Turma turmaFoco;
	
	private ProfessorServico ps;
	private TurmaServico ts;
	
	

	public ProfessorControle() {
		//TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		ps = new ProfessorServico();			
		ts = new TurmaServico();
		sincronizarDados();
		novoProfessor();		
	}
	
		
	public void sincronizarDados() {
		professores = ps.listarProfessores();
		
	}
	
	public void novoProfessor() {
		professorFoco = new Professor();
		existente = false;
		sincronizarDados();		
	}
	
	public void salvarNovoProfessor() {		
		ps.salvarNovoProfessor(professorFoco);		
		sincronizarDados();
		novoProfessor();
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor salvo com sucesso!", ""));
		
	}
	
	public void excluirProfessor() {
		if(professores.contains(professorFoco)) {
			ps.deletarProfessor(professorFoco);			
		}
		sincronizarDados();
		novoProfessor();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Professor excluido com sucesso!", ""));
		
	}
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public List<Turma> turmasDoProfessor() {
		return (List<Turma>) professorFoco.getTurmasMinistradas();
		
	}
	
	public void removerTurma() {
		ps.desmatricularProfessor(professorFoco, turmaFoco);
		sincronizarDados();
	}


	public Professor getProfessorFoco() {
		return professorFoco;
	}


	public void setProfessorFoco(Professor professorFoco) {
		existente = true;
		this.professorFoco = professorFoco;
	}


	public boolean isExistente() {
		return existente;
	}


	public Collection<Professor> getProfessores() {
		return professores;
	}


	public void setTurmaFoco(Turma turmaFoco) {
		this.turmaFoco = turmaFoco;
	}
	
	public void mudarMostrarTurmasSisponiveis() {
		mostrarTurmasDisponiveis = !mostrarTurmasDisponiveis;
		
	}

	public boolean isMostrarTurmasDisponiveis() {
		return mostrarTurmasDisponiveis;
		
	}
	
	public List<Turma> turmasDisponives() {		
		return ts.listarTurmasSemProfessor();
	}
	
	public void cadastrarTurmaAProfessor() {
			ps.matricularProfessor(professorFoco, turmaFoco);
	}
		
	
	public String MensagemCampoTurmas() {
		if (existente) {
			return "";			
		} else {
			return "O Professor precisa ser salvo antes de o cadastrar alguma turma";
		}		
	}
	
	public String titulo() {
		if(existente) {
			return "Atualização de Professor";
		}else {
			return "Cadastro de Novo Professor";
		}
	}
	
	@SuppressWarnings("deprecation")
	public int getIdadeProfessorFoco() {
		if(professorFoco.getData_de_nascimento()==null) {
			return -1;
		}
		Date nascimento = professorFoco.getData_de_nascimento();
		Date now = new Date(System.currentTimeMillis());
		int idade =  now.getYear() - nascimento.getYear();	
		if (nascimento.getMonth() > now.getMonth()) {
			idade--;			
		} else {
			if (nascimento.getMonth() == now.getMonth()) {
				if(nascimento.getDate() > now.getDate()) {
					idade--;					
				}
			}
		}
		return idade;
	}
	
public void cancelar() {
		
		novoProfessor();
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    try {
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ação cancelada", ""));
		} catch (IOException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, e.getLocalizedMessage(), ""));
		}		
	}

}
