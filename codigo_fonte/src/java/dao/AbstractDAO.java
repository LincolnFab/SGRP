/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author linkf
 * @param <T>
 */
public abstract class AbstractDAO<T> {

    @PersistenceContext(unitName = "SGRPPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.merge(entity));
    }    
}
