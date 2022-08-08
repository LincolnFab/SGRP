package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Curso;
import model.RecuperacaoParalela;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Disciplina.class)
public class Disciplina_ { 

    public static volatile SingularAttribute<Disciplina, String> sigla;
    public static volatile CollectionAttribute<Disciplina, RecuperacaoParalela> recuperacaoParalelaCollection;
    public static volatile SingularAttribute<Disciplina, String> nome;
    public static volatile SingularAttribute<Disciplina, Curso> cursoId;

}