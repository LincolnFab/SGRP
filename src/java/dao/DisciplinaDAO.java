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
}
