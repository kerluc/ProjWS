/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.facade;

import entities.ReservationHotel;
import entities.ReservationRestaurant;
import java.util.List;
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
public class ReservationRestaurantFacade extends AbstractFacade<ReservationRestaurant> {

    @PersistenceContext(unitName = "webservice_lct_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationRestaurantFacade() {
        super(ReservationRestaurant.class);
    }
    
    public List<ReservationRestaurant> findByEtudiant(Long id) {
        Query q = getEntityManager().createNamedQuery("ReservationRestaurant.findByEtudiant");
        q = q.setParameter("id", id);
        try {
            List<ReservationRestaurant> r = q.getResultList();
            return r;
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
