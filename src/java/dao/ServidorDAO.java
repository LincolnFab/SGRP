/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.Servidor;

/**
 *
 * @author linkf
 */
@Stateless
public class ServidorDAO extends AbstractDAO<Servidor> {

    public List<Servidor> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("Servidor.findAll", Servidor.class)
                .getResultList();
    }
    
    public Servidor buscarPorTipo(String tipo) {
        return getEntityManager().createNamedQuery("Servidor.findByTipo", Servidor.class)
                .setParameter("tipo", tipo)
                .getSingleResult();
    }


    public Servidor buscarPorProntuario(String prontuario) {
        return getEntityManager()
                .createNamedQuery("Servidor.findByProntuario", Servidor.class)
                .setParameter("prontuario", prontuario)
                .getSingleResult();
    }
}
