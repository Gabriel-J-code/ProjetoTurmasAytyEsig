package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-07-04T20:24:00.637-0300")
@StaticMetamodel(Professor.class)
public class Professor_ {
	public static volatile SingularAttribute<Professor, Integer> id;
	public static volatile SingularAttribute<Professor, String> nome;
	public static volatile SingularAttribute<Professor, Integer> idade;
	public static volatile SingularAttribute<Professor, String> email;
	public static volatile SingularAttribute<Professor, String> formacao;
	public static volatile SingularAttribute<Professor, Genero> genero;
	public static volatile CollectionAttribute<Professor, Turma> turmasMinistradas;
}
