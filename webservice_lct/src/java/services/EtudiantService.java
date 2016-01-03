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
    @Path("addEtudiant/{nom}/{prenom}/{email}/{pw}/{adresse}/{ville}/{budget}")
    @Produces("text/plain")
    public String addEtudiant(@PathParam("nom") String nom, @PathParam("prenom") String prenom,
                              @PathParam("email") String email, @PathParam("pw") String pw,
                              @PathParam("adresse") String adresse, @PathParam("ville") String ville,
                              @PathParam("budget") int budget)
    {
        Etudiant e = facade.findByEmail(email);
        
        if (e != null) 
            return "fail";
        
        e = new Etudiant(nom, prenom, email, pw, ville, adresse, budget);
        facade.create(e);
        
        return "ok";
    }
    
    @PUT
    @Path("editEtudiant/{id}/{nom}/{prenom}/{email}/{pw}/{adresse}/{ville}/{budget}")
    @Produces("text/plain")
    public String editEtudiant(@PathParam("id") Long id, @PathParam("nom") String nom, 
                               @PathParam("prenom") String prenom, @PathParam("email") String email,
                               @PathParam("adresse") String adresse, @PathParam("ville") String ville,
                               @PathParam("pw") String pw, @PathParam("budget") int budget)
    {
        Etudiant e = facade.findById(id);
        
        if(e == null)
            return "fail";
        
        e.setBudget(budget);
        e.setEmail(email);
        e.setNom(nom);
        e.setPrenom(prenom);   
        e.setPw(pw);
        e.setVille(ville);
        e.setAdresse(adresse);
        
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
    
    @GET
    @Path("validate/{login}/{pw}")
    @Produces("application/xml")
    public Response validate(@PathParam("login") String login, @PathParam("pw") String pw) {
        // Renvoie l'étudiant pour le login et le pw donné s'il existe (pour la connexion)
        Etudiant e = facade.findByLoginAndPw(login, pw);
        if (e == null)
            return null;
        else
            return Response.status(200).type("application/xml").entity(e).build();
    }
    
}
