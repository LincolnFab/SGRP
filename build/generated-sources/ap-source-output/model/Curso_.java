package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Disciplina;
import model.Turma;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Curso.class)
public class Curso_ { 

    public static volatile CollectionAttribute<Curso, Disciplina> disciplinaCollection;
    public static volatile CollectionAttribute<Curso, Turma> turmaCollection;
    public static volatile SingularAttribute<Curso, Integer> id;
    public static volatile SingularAttribute<Curso, String> descricao;

}