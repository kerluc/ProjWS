/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Etudiant;
import entities.facade.EtudiantFacade;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Lucien
 */
@WebService(serviceName = "EtudiantService")
@Stateless()
public class EtudiantService {
    
    @Inject
    EtudiantFacade facade;
    
    @POST
    @Path("addEtudiant/{nom}/{prenom}/{email}/{password}/{budget}")
    @Produces("text/plain")
    public String addEtudiant(@PathParam("nom") String nom, @PathParam("prenom") String prenom,
                              @PathParam("email") String email, @PathParam("password") String password,
                              @PathParam("budget") int budget)
    {
        Etudiant e = facade.findByEmail(email);
        
        if (e != null) 
            return "fail";
        
        e = new Etudiant(nom, prenom, email, password, budget);
        facade.create(e);
        return "ok";
    }
    
    @PUT
    @Path("editEtudiant/{id}/{nom}/{prenom}/{email}/{password}/{budget}")
    @Produces("text/plain")
    public String editEtudiant(@PathParam("id") Long id, @PathParam("nom") String nom, 
                               @PathParam("prenom") String prenom, @PathParam("email") String email, 
                               @PathParam("password") String password, @PathParam("budget") int budget)
    {
        Etudiant e = facade.findById(id);
        if(e == null)
            return "fail";
        
        e.setBudget(budget);
        e.setEmail(email);
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setPw(password);
        
        facade.edit(e);
        
        return "ok";
    }
    
    @GET
    @Path("find/{id}")
    @Produces("application/xml")
    public Etudiant find(@PathParam("id") Long id) {
        Etudiant e = facade.findById(id);
        return e;
    }
    
    @DELETE
    @Path("delete/{id}")
    @Produces("text/plain")
    public String delete(@PathParam("id") Long id) {
        Etudiant e = facade.findById(id);
        if (e == null)
            return "fail";
        
        facade.remove(e);
        return "ok";
    }
}
