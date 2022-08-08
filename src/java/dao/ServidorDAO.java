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
}
