package controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import model.Aluno;
import model.Genero;
import model.Turma;
import percistencia.TurmaPercistencia;
import servico.AlunoServico;


@Named("alunoCon")

@SessionScoped
public class AlunoControle implements Serializable {
	
	
	private Aluno alunoFoco;
	

	private boolean existente = false;
	private boolean mostrarTurmasDisponiveis = false;
	private Collection<Aluno> alunos;
	private Turma turmaFoco;
		
	private AlunoServico as;
	

	public AlunoControle() {
		
	}	
	
	@PostConstruct
	public void init() {	
		as = new AlunoServico();
		sincronizarDados();
		novoAluno();		
	}
	
	@PreDestroy
	public void exit() {
	as.exit();;
	}
	
	public void sincronizarDados() {
		alunos = as.listarAlunos();
	}
	//metodos
	public void novoAluno() {
		alunoFoco = new Aluno();
		existente = false;
		sincronizarDados();
		
	}
	public void salvarNovoAluno(){	
			
		as.salvarNovoAluno(alunoFoco);	
		novoAluno();
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno salvo com sucesso!", ""));
		
	}
	
	public void excluirAluno() {		
		if(alunos.contains(alunoFoco)) {
			as.deletarAluno(alunoFoco);
			sincronizarDados();
		}
		novoAluno();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aluno excluido com sucesso!", ""));
		
	}
	
public void cancelar() {
		
		novoAluno();
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
	
	public Genero[] generos() {
		return Genero.values();
		
	}
	
	public Collection<Turma> turmasDoAluno() {		
		return alunoFoco.getTurmasMatriculadas();
		
	}
	
	public void removerTurma() {		
		alunoFoco = as.desmatricularAlunoDeTurma(alunoFoco, turmaFoco);
		sincronizarDados();		
	}
	
	public void matricularAlunoATurma() {
		as.matricularAlunoATurma(alunoFoco, turmaFoco);
		sincronizarDados();
	}
	
	public Collection<Turma> turmasDisponiveis() {
		TurmaPercistencia tp = new TurmaPercistencia();
		Collection<Turma> turmasDisponiveis =  tp.getTurmas();	
		turmasDisponiveis.removeAll(alunoFoco.getTurmasMatriculadas());
		return turmasDisponiveis;
		
	}
	
	//GETs e SETs
	
	public Aluno getAlunoFoco() {
		return alunoFoco;
	}

	public void setAlunoFoco(Aluno alunoFoco) {
		this.alunoFoco = alunoFoco;
		existente = true;
		mostrarTurmasDisponiveis = false;
	}

	public Collection<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Collection<Aluno> alunos) {
		this.alunos = alunos;
	}

	public boolean isExistente() {
		return existente;
		
	}
	
	public void mudarMostrarTurmasSisponiveis() {
		mostrarTurmasDisponiveis = !mostrarTurmasDisponiveis;
		
	}

	public String MensagemCampoTurmas() {
		if (existente) {
			return "";			
		} else {
			return "O Aluno precisa ser salvo antes de matricular em alguma turma";
		}		
	}

	public void setTurmaFoco(Turma turmaFoco) {
		this.turmaFoco = turmaFoco;
		
	}

	public boolean isMostrarTurmasDisponiveis() {
		return mostrarTurmasDisponiveis;
		
	}
	
	public String titulo() {
		if (existente) {
			return "Atualização de Aluno";
		} else {
			return "Cadastro de Novo Aluno";
		}		
	}
	
	@SuppressWarnings("deprecation")
	public int getIdadeAlunoFoco() {
		if(alunoFoco.getData_de_nascimento()==null) {
			return -1;
		}
		Date nascimento = alunoFoco.getData_de_nascimento();
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

}
