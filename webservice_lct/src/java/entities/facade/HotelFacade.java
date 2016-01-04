/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.facade;

import entities.Hotel;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucien
 */
@Stateless
public class HotelFacade extends AbstractFacade<Hotel> {

    @PersistenceContext(unitName = "webservice_lct_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HotelFacade() {
        super(Hotel.class);
    }

    public Hotel findByTel(String tel) {
        Query q = getEntityManager().createNamedQuery("Hotel.findByTel");
        q = q.setParameter("tel", tel);
        try {
            Hotel h = (Hotel)q.getSingleResult();
            return h;
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
}
