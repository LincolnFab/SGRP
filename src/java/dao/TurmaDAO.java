/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.Turma;

/**
 *
 * @author linkf
 */
@Stateless
public class TurmaDAO extends AbstractDAO<Turma> {

    public List<Turma> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("Turma.findAll", Turma.class)
                .getResultList();
    }

    public Turma buscarPorId(String id) {
        return getEntityManager()
                .createNamedQuery("Turma.findByIdturma", Turma.class)
                .setParameter("idturma", id)
                .getSingleResult();
    }
}
