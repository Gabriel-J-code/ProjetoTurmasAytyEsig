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
	
	//validar
	private void validarSala(Sala sala) throws InvalideFieldException {
		
		if(sala.getNumero()==null ||sala.getNumero()=="") {
			throw new InvalideFieldException("Salas com numero em branco; ");
		}
		if(sala.getPredio()==null ||sala.getPredio()=="") {
			throw new InvalideFieldException("Salas com predio em branco; ");
		}
		if(sala.getCampus()==null || sala.getCampus()=="") {
			throw new InvalideFieldException("Salas com campus em branco; ");
		}					
	}
		
	
	//criar
	public void salvarNovaSala(Sala sala) throws InvalideFieldException {
		validarSala(sala);
		sp.adicionarNovaSala(sala);		
	}
	
	public void salvarNovaSala(String numero, String predio, String campus) throws InvalideFieldException {
		Sala sala = new Sala(numero, predio, campus);
		salvarNovaSala(sala);		
	}
	
	public Sala atualizarSala(Sala sala) {
		try {
			validarSala(sala);
			sp.atualizarSala(sala);
			return sala;
		} catch (InvalideFieldException e) {
			return sp.encontrarSalaPeloId(sala.getId());
		}		
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
		sp.deleteSalaPorId(sala.getId());
	}
	

}
