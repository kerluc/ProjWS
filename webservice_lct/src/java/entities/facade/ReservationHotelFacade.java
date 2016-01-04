package entities.facade;

import entities.ReservationHotel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ReservationHotelFacade extends AbstractFacade<ReservationHotel> {

    @PersistenceContext(unitName = "webservice_lct_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationHotelFacade() {
        super(ReservationHotel.class);
    }

    public List<ReservationHotel> findByEtudiant(Long id) {
        Query q = getEntityManager().createNamedQuery("ReservationHotel.findByEtudiant");
        q = q.setParameter("id", id);
        try {
            List<ReservationHotel> r = q.getResultList();
            return r;
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
    
}
