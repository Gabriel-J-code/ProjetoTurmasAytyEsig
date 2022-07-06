package controle;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Sala;
import percistencia.SalaPercistencia;

@Named("salaCon")
@ApplicationScoped
public class SalaControle implements Serializable {
	
	
	private Sala salaFoco;
	private boolean nova = true;
	private Collection<Sala> salas;
	
	
	private SalaPercistencia sp;
	

	public SalaControle() {
		
	}	
	
	@PostConstruct
	public void init() {
		sp = new SalaPercistencia();
		sincronizarDados();
		novaSala();		
	}
	
		
	public void sincronizarDados() {
		salas = sp.getSalas();
	}
	//metodos
	public void novaSala() {
		salaFoco = new Sala();		
		nova = true;
		sincronizarDados();
		
	}
	public void salvarNovaSala(){
		sp.adicionarNovaSala(salaFoco);			
		novaSala();
		
	}
	
	public void excluirSala() {
		if(salas.contains(salaFoco)) {
			sp.deleteSala(salaFoco);			
			sincronizarDados();
		}

		novaSala();
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
