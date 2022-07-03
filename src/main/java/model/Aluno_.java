package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-07-02T22:26:55.097-0300")
@StaticMetamodel(Aluno.class)
public class Aluno_ {
	public static volatile SingularAttribute<Aluno, Integer> id;
	public static volatile SingularAttribute<Aluno, String> nome;
	public static volatile SingularAttribute<Aluno, Integer> idade;
	public static volatile SingularAttribute<Aluno, String> email;
	public static volatile SingularAttribute<Aluno, String> curso;
	public static volatile SingularAttribute<Aluno, String> matricula;
	public static volatile SingularAttribute<Aluno, Genero> genero;
	public static volatile CollectionAttribute<Aluno, Turma> turmasMatriculadas;
}
