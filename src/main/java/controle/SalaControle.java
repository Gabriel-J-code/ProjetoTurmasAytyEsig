package controle;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import model.Sala;
import servico.SalaServico;
import servico.InvalideFieldException;

@Named("salaCon")
@ApplicationScoped
public class SalaControle implements Serializable {
	
	
	private Sala salaFoco;
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
	
	@PreDestroy
	public void exit() {
		ss.exit();
	}
	
	public void sincronizarDados() {
		salas = ss.listarSalas();
	}
	//metodos
	public void novaSala() {
		salaFoco = new Sala();		
		sincronizarDados();
		
	}
	public void salvarNovaSala(){
		try {
			ss.salvarNovaSala(salaFoco);
		} catch (InvalideFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		novaSala();
		
	}
	
	public void excluirSala() {
		if(salas.contains(salaFoco)) {
			ss.deletarSala(salaFoco);
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
		return salas;
	}

	
	
	
	
	
	
	
	
	
	

}
