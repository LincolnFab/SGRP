package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RecuperacaoParalelaHasEstudante;
import model.Turma;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Estudante.class)
public class Estudante_ { 

    public static volatile SingularAttribute<Estudante, String> emailAluno;
    public static volatile SingularAttribute<Estudante, String> senha;
    public static volatile SingularAttribute<Estudante, String> prontuario;
    public static volatile SingularAttribute<Estudante, Turma> turmaIdturma;
    public static volatile SingularAttribute<Estudante, String> cpf;
    public static volatile SingularAttribute<Estudante, String> emailPessoal;
    public static volatile SingularAttribute<Estudante, String> nome;
    public static volatile CollectionAttribute<Estudante, RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection;
    public static volatile SingularAttribute<Estudante, String> emailResponsavel;

}