package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RecuperacaoParalela;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Servidor.class)
public class Servidor_ { 

    public static volatile SingularAttribute<Servidor, String> senha;
    public static volatile SingularAttribute<Servidor, String> prontuario;
    public static volatile SingularAttribute<Servidor, String> tipo;
    public static volatile CollectionAttribute<Servidor, RecuperacaoParalela> recuperacaoParalelaCollection;
    public static volatile SingularAttribute<Servidor, String> cpf;
    public static volatile SingularAttribute<Servidor, String> nome;
    public static volatile SingularAttribute<Servidor, String> email;

}