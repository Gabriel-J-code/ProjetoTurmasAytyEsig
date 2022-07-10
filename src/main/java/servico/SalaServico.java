package servico;

import java.util.List;

import model.Sala;
import percistencia.SalaPercistencia;

public class SalaServico {
	private SalaPercistencia sp;
		
	public SalaServico() {
		sp = new SalaPercistencia();
	}
	
	public void exit() {
		sp.fechar();
	}
	
	
		
	
	//criar
	public void salvarNovaSala(Sala sala){
		
		sp.adicionarNovaSala(sala);		
	}
	
	public void salvarNovaSala(String numero, String predio, String campus){
		Sala sala = new Sala(numero, predio, campus);
		salvarNovaSala(sala);		
	}
	
	public Sala atualizarSala(Sala sala) {		
		sp.atualizarSala(sala);
		return sala;		
	}
	//pegar
	
	//id
	public Sala procurarSalaPorId(Integer id) {
		return sp.encontrarSalaPeloId(id);		
	}
		
	//numero
	public List<Sala> procurarSalaPorNumero(String numero){		
		return sp.contultarSalaPorNumero(numero);
	}
	//predio
	public List<Sala> procurarSalaPorPredio(String predio){		
		return sp.contultarSalaPorPredio(predio);
	}
	//campus
	public List<Sala> procurarSalaPorCampus(String campus){		
		return sp.contultarSalaPorCampus(campus);
	}	
	
	//listar
	public List<Sala> listarSalas() {
		return sp.getSalas();
	}
	
	//deletar
	public void deletarSala(Sala sala) {
		sp.deleteSala(sala);
	}
	

}
