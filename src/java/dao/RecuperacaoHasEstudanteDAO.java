/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import model.RecuperacaoParalelaHasEstudante;

/**
 *
 * @author Marilena Oshima
 */
@Stateless
public class RecuperacaoHasEstudanteDAO extends AbstractDAO<RecuperacaoParalelaHasEstudante>{
    
    public List<RecuperacaoParalelaHasEstudante> buscarTodos() {
        return getEntityManager()
                .createNamedQuery("RecuperacaoParalelaHasEstudante.findAll", RecuperacaoParalelaHasEstudante.class)
                .getResultList();
    }
}
