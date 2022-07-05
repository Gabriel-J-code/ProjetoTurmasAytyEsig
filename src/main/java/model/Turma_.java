package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2022-07-04T20:50:25.996-0300")
@StaticMetamodel(Turma.class)
public class Turma_ {
	public static volatile SingularAttribute<Turma, Integer> id;
	public static volatile SingularAttribute<Turma, String> disciplina;
	public static volatile SingularAttribute<Turma, String> horario;
	public static volatile SingularAttribute<Turma, Professor> professor;
	public static volatile SingularAttribute<Turma, Sala> sala;
	public static volatile CollectionAttribute<Turma, Aluno> alunos;
}
