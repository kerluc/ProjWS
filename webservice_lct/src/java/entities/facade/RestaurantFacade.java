/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.facade;

import entities.Hotel;
import entities.Restaurant;
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
public class RestaurantFacade extends AbstractFacade<Restaurant> {

    @PersistenceContext(unitName = "webservice_lct_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RestaurantFacade() {
        super(Restaurant.class);
    }
    
    public Restaurant findByAdresse(String adresse) {
        Query q = getEntityManager().createNamedQuery("Restaurant.findByAdresse");
        q = q.setParameter("adresse", adresse);
        try {
            Restaurant h = (Restaurant)q.getSingleResult();
            return h;
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
