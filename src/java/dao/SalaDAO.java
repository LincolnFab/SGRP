/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.SalaDeAula;

/**
 *
 * @author linkf
 */
@Stateless
public class SalaDAO extends AbstractDAO<SalaDeAula> {

    public List<SalaDeAula> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("SalaDeAula.findAll", SalaDeAula.class)
                .getResultList();
    }

    public SalaDeAula buscarPorDescricao(String descricao) {
        return getEntityManager()
                .createNamedQuery("SalaDeAula.findByDescricao", SalaDeAula.class)
                .setParameter("descricao", descricao)
                .getSingleResult();
    }

    public SalaDeAula buscarPorId(int idSala) {
        return getEntityManager()
                .createNamedQuery("SalaDeAula.findByIdSala", SalaDeAula.class)
                .setParameter("idSala", idSala)
                .getSingleResult();
    }
}
