package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.RecuperacaoParalela;
import model.SalaDeAula;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-08-08T14:39:29")
@StaticMetamodel(Aula.class)
public class Aula_ { 

    public static volatile SingularAttribute<Aula, SalaDeAula> saladeaulaidSala;
    public static volatile SingularAttribute<Aula, Date> horario;
    public static volatile SingularAttribute<Aula, Short> frequencia;
    public static volatile SingularAttribute<Aula, RecuperacaoParalela> recuperacaoParalelaId;
    public static volatile SingularAttribute<Aula, Date> dia;
    public static volatile SingularAttribute<Aula, Integer> idaula;

}