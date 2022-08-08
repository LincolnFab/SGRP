package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Curso;
import model.Estudante;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Turma.class)
public class Turma_ { 

    public static volatile CollectionAttribute<Turma, Estudante> estudanteCollection;
    public static volatile SingularAttribute<Turma, Integer> idturma;
    public static volatile SingularAttribute<Turma, Curso> cursoId;
    public static volatile SingularAttribute<Turma, String> descricao;

}