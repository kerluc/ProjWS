/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.facade;

import entities.Etudiant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucien
 */
@Stateless
public class EtudiantFacade extends AbstractFacade<Etudiant> {

    @PersistenceContext(unitName = "webservice_lct_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtudiantFacade() {
        super(Etudiant.class);
    }
    
    public Etudiant findByEmail(String email) {
        Query q = getEntityManager().createNamedQuery("Etudiant.findByEmail");
        q = q.setParameter("email", email);
        try {
            Etudiant e = (Etudiant)q.getSingleResult();
            return e;
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
    public Etudiant findById(Long id) {
        Query q = getEntityManager().createNamedQuery("Etudiant.findById");
        q = q.setParameter("id", id);
        try {
            Etudiant e = (Etudiant) q.getSingleResult();
            return e;
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
}
