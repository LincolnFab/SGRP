/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import model.RecuperacaoParalela;

/**
 *
 * @author linkf
 */
@Stateless
public class RecuperacaoDAO extends AbstractDAO<RecuperacaoParalela> {

    public List<RecuperacaoParalela> buscarTodas() {
        return getEntityManager()
                .createNamedQuery("RecuperacaoParalela.findAll", RecuperacaoParalela.class)
                .getResultList();
    }

//    public List<Estudante> verificarAlunoJaCadastradoEmUmaRp(String dia, String horario, String prontuario) {
//        return getEntityManager()
//                .createQuery("SELECT rp FROM Estudante e JOIN e.recuperacaoParalelaHasEstudanteCollection rphe ON e = rphe.estudante JOIN rphe.recuperacaoParalela rp ON rphe.recuperacaoParalela = rp JOIN rp.aulaCollection a ON rp = a.recuperacaoParalelaId WHERE a.dia = :dia AND a.horario = :horario AND e.prontuario = :prontuario", Estudante.class)
//                .setParameter("dia", dia)
//                .setParameter("horario", horario)
//                .setParameter("prontuario", prontuario)
//                .getResultList();
//    }
    
    public List<RecuperacaoParalela> buscarPorEstudante(String prontuario) {
        
       List<RecuperacaoParalela> rps = new ArrayList<>();
       rps = getEntityManager()
                .createQuery("SELECT rp FROM Estudante e JOIN e.recuperacaoParalelaHasEstudanteCollection rphe ON e = rphe.estudante JOIN rphe.recuperacaoParalela rp ON rphe.recuperacaoParalela = rp JOIN rp.aulaCollection a ON rp = a.recuperacaoParalelaId WHERE e.prontuario = :prontuario", RecuperacaoParalela.class)
                .setParameter("prontuario", prontuario)
                .getResultList();
        System.out.println("rps......." + rps);
        
        return rps;   
        /*return getEntityManager()
                .createQuery("SELECT rp FROM Estudante e JOIN e.recuperacaoParalelaHasEstudanteCollection rphe ON e = rphe.estudante JOIN rphe.recuperacaoParalela rp ON rphe.recuperacaoParalela = rp JOIN rp.aulaCollection a ON rp = a.recuperacaoParalelaId WHERE e.prontuario = :prontuario", RecuperacaoParalela.class)
                .setParameter("prontuario", prontuario)
                .getResultList();
*/
    }
}
