package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Aula;
import model.Disciplina;
import model.RecuperacaoParalelaHasEstudante;
import model.Servidor;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(RecuperacaoParalela.class)
public class RecuperacaoParalela_ { 

    public static volatile SingularAttribute<RecuperacaoParalela, Disciplina> disciplinaSigla;
    public static volatile CollectionAttribute<RecuperacaoParalela, Servidor> servidorCollection;
    public static volatile SingularAttribute<RecuperacaoParalela, Integer> quantidadeAlunos;
    public static volatile CollectionAttribute<RecuperacaoParalela, Aula> aulaCollection;
    public static volatile SingularAttribute<RecuperacaoParalela, Date> dataProposta;
    public static volatile SingularAttribute<RecuperacaoParalela, Integer> anoLetivo;
    public static volatile SingularAttribute<RecuperacaoParalela, String> atividadesPropostas;
    public static volatile SingularAttribute<RecuperacaoParalela, Integer> quantidadeAulas;
    public static volatile SingularAttribute<RecuperacaoParalela, Integer> bimestre;
    public static volatile SingularAttribute<RecuperacaoParalela, String> justificativa;
    public static volatile SingularAttribute<RecuperacaoParalela, String> objetivoGeral;
    public static volatile CollectionAttribute<RecuperacaoParalela, RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection;
    public static volatile SingularAttribute<RecuperacaoParalela, String> id;
    public static volatile SingularAttribute<RecuperacaoParalela, String> procedimentosAvaliativos;
    public static volatile SingularAttribute<RecuperacaoParalela, String> status;

}