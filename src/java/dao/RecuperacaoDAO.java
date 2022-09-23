/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
}
