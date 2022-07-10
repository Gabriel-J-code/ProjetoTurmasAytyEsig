package controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import model.Sala;
import servico.SalaServico;

@Named("salaCon")
@SessionScoped
public class SalaControle implements Serializable {
	
	
	private Sala salaFoco;
	private boolean nova = true;
	private Collection<Sala> salas;
	
	private SalaServico ss;

	public SalaControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		ss = new SalaServico();
		sincronizarDados();
		novaSala();		
	}
	
		
	public void sincronizarDados() {
		salas = ss.listarSalas();
	}
	//metodos
	public void novaSala() {
		salaFoco = new Sala();		
		nova = true;
		sincronizarDados();
		
	}
	public void salvarNovaSala(){
		
		if (nova) {
			ss.salvarNovaSala(salaFoco);	
		}else {
			ss.atualizarSala(salaFoco);
		}
		novaSala();	
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sala salva com sucesso!", ""));
		
	}
	
	public void excluirSala() {
		FacesContext facesContext = FacesContext.getCurrentInstance();	
		if(salas.contains(salaFoco)) {
			ss.deletarSala(salaFoco);	
		}
		novaSala();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sala excluida com sucesso!", ""));
		
	}
	
	public void cancelar() {
		
		novaSala();
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
		
	//GETs e SETs
	
	public Sala getSalaFoco() {
		return salaFoco;
	}

	public void setSalaFoco(Sala salaFoco) {
		this.salaFoco = salaFoco;	
		nova = false;
	}

	public Collection<Sala> getSalas() {
		sincronizarDados();
		return salas;
	}

	public boolean isNova() {
		return nova;
		
	}
	
	public String titulo() {
		if(nova) {
			return "Cadastro de Nova Sala";
		}else {
			return "Atualização de Sala";
		}
	}

	
	
	
	
	
	
	
	
	
	

}
