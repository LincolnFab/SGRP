/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.Disciplina;

/**
 *
 * @author linkf
 */
@Stateless
public class DisciplinaDAO extends AbstractDAO<Disciplina> {

    public List<Disciplina> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("Disciplina.findAll", Disciplina.class)
                .getResultList();
    }

    public List<Disciplina> buscarPorCurso(String cursoId) {
        return getEntityManager()
                .createNamedQuery("Disciplina.findByCursoId", Disciplina.class)
                .setParameter("cursoId", cursoId)
                .getResultList();
    }

    public Disciplina buscarPorSigla(String sigla) {
        return getEntityManager()
                .createNamedQuery("Disciplina.findBySigla", Disciplina.class)
                .setParameter("sigla", sigla)
                .getSingleResult();
    }

    public Disciplina buscarPorSiglaECurso(String sigla, String curso) {
        return getEntityManager()
                .createNamedQuery("Disciplina.findBySiglaAndCursoId", Disciplina.class)
                .setParameter("sigla", sigla)
                .setParameter("cursoId", curso)
                .getSingleResult();
    }
}
