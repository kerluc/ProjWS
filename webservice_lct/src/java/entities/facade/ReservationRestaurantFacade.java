
package entities.facade;

import entities.ReservationRestaurant;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public ReservationRestaurant findById(Long id) {
        Query q = getEntityManager().createNamedQuery("ReservationRestaurant.findById");
        q = q.setParameter("id", id);
        try {
            ReservationRestaurant e = (ReservationRestaurant) q.getSingleResult();
            return e;
        }
        catch (NoResultException e) {
            return null;
        }
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

    public void removeByIdEtud(Long id) {
        List<ReservationRestaurant> reservations = findByEtudiant(id);
        if(reservations != null) {
            for(ReservationRestaurant r : reservations) {
                remove(r);
            }
        }
    }
}
