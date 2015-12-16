package services;

import entities.Etudiant;
import entities.facade.EtudiantFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("etudiantService")
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
    @Path("editEtudiant/{id}/{nom}/{prenom}/{email}/{budget}")
    @Produces("text/plain")
    public String editEtudiant(@PathParam("id") Long id, @PathParam("nom") String nom, 
                               @PathParam("prenom") String prenom, @PathParam("email") String email, 
                               @PathParam("budget") int budget)
    {
        Etudiant e = facade.findById(id);
        
        if(e == null)
            return "fail";
        
        e.setBudget(budget);
        e.setEmail(email);
        e.setNom(nom);
        e.setPrenom(prenom);
        
        facade.edit(e);
        
        return "ok";
    }
    
    
    /**
     * find/ ou find/{ID}
     * @param id
     * @return Response : Etudiant si id est spécifié, sinon tous les étudiants.
     */
    @GET
    @Path("find/{id:([0-9]+)?}")
    @Produces("application/xml")
    public Response find(@PathParam("id") Long id) {
        Object response;
        if(id != null) {
            response = facade.findById(id);
        }
        else {
            List<Etudiant> list = facade.findAll();
            response = new GenericEntity< List< Etudiant > >(list) { };
        }
        return Response.status(200).type("application/xml").entity(response).build();
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
