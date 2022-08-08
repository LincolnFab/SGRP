/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.Curso;

/**
 *
 * @author linkf
 */
@Stateless
public class CursoDAO extends AbstractDAO<Curso> {

    public List<Curso> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("Curso.findAll", Curso.class)
                .getResultList();
    }

    public Curso buscarPorId(int id) {
        return getEntityManager()
                .createNamedQuery("Curso.findById", Curso.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
