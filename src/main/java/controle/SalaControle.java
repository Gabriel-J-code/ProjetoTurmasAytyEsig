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
	}

	public Collection<Sala> getSalas() {
		sincronizarDados();
		return salas;
	}

	
	
	
	
	
	
	
	
	
	

}
