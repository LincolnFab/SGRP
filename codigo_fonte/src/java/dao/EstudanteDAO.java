/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.Estudante;
import model.Turma;

/**
 *
 * @author linkf
 */
@Stateless
public class EstudanteDAO extends AbstractDAO<Estudante> {

    public List<Estudante> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("Estudante.findAll", Estudante.class)
                .getResultList();
    }

    public List<Estudante> buscarPorTurma(Turma t) {
        return getEntityManager()
                .createNamedQuery("Estudante.findByTurma", Estudante.class)
                .setParameter("turma", t)
                .getResultList();
    }
    
    public Estudante buscarPorProntuario(String prontuario) {
        return getEntityManager()
                .createNamedQuery("Estudante.findByProntuario", Estudante.class)
                .setParameter("prontuario", prontuario)
                .getSingleResult();
    }
}
