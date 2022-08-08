package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Aula;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(SalaDeAula.class)
public class SalaDeAula_ { 

    public static volatile SingularAttribute<SalaDeAula, Integer> idSala;
    public static volatile CollectionAttribute<SalaDeAula, Aula> aulaCollection;
    public static volatile SingularAttribute<SalaDeAula, String> descricao;

}