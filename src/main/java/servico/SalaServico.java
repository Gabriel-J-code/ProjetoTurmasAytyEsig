package servico;

import java.util.ArrayList;
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
	public void salvarSala(Sala sala) throws InvalideFieldException {
		validarSala(sala);
		sp.adicionarNovaTurma(sala);		
	}
	
	public void criarSala(String numero, String predio, String campus) throws InvalideFieldException {
		Sala sala = new Sala(numero, predio, campus);
		salvarSala(sala);		
	}
	//pegar
	
	//id
	public Sala procurarSalaPorId(Integer id) {
		return sp.encontrarSalaPeloId(id);		
	}
	
	public List<Sala> procurarSalaPorCampo(String campo, String valor){		
		ArrayList<Sala> resultadoConsultaSalas = new ArrayList<Sala>();
		String campoLC = campo.toLowerCase();
		resultadoConsultaSalas.addAll(
				sp.consultaSQL(
						String.format(
								"SELECT a FROM Sala a WHERE %s = '%s' ORDER BY %s",campoLC, valor,campoLC)));
		resultadoConsultaSalas.addAll(
				sp.consultaSQL(
						String.format("SELECT a FROM Sala a WHERE %s LIKE '%%%s%%' AND %s <> '%s' ORDER BY %s" ,
								campoLC,valor,campoLC,valor,campoLC)));						
		return resultadoConsultaSalas;
	}
	//numero
	public List<Sala> procurarSalaPorNumero(String numero){		
		return procurarSalaPorCampo("numero", numero);
	}
	//predio
	public List<Sala> procurarSalaPorPredio(String predio){		
		return procurarSalaPorCampo("predio", predio);
	}
	//campus
	public List<Sala> procurarSalaPorCampus(String campus){		
		return procurarSalaPorCampo("campus", campus);
	}	
	
	//listar
	public List<Sala> listarSalas() {
		return sp.getSalas();
	}
	
	//deletar
	public void deletarSala(Sala sala) {
		sp.deleteSalaProId(sala.getId());
	}
	

}
