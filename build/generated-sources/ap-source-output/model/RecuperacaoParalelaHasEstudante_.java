package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Estudante;
import model.RecuperacaoParalela;
import model.RecuperacaoParalelaHasEstudantePK;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(RecuperacaoParalelaHasEstudante.class)
public class RecuperacaoParalelaHasEstudante_ { 

    public static volatile SingularAttribute<RecuperacaoParalelaHasEstudante, Float> notaPos;
    public static volatile SingularAttribute<RecuperacaoParalelaHasEstudante, Float> notaPre;
    public static volatile SingularAttribute<RecuperacaoParalelaHasEstudante, RecuperacaoParalela> recuperacaoParalela;
    public static volatile SingularAttribute<RecuperacaoParalelaHasEstudante, RecuperacaoParalelaHasEstudantePK> recuperacaoParalelaHasEstudantePK;
    public static volatile SingularAttribute<RecuperacaoParalelaHasEstudante, Estudante> estudante;

}