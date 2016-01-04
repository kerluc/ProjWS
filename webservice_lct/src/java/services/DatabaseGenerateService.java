package services;

import entities.Hotel;
import entities.facade.EtudiantFacade;
import entities.facade.HotelFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * N'utiliser ce service que pour remplir la base de données avec des tuples de bases
 */

@Path("database")
@Stateless()
public class DatabaseGenerateService {

    @EJB
    private ViaMichelinService service;
    @Inject
    EtudiantFacade facade_etu;
    
    @Inject
    HotelFacade hotel_facade;
    
    @GET
    @Path("generateHotels")
    @Produces("application/xml")
    public Response hotels() {
        List<Hotel> hotels = service.getHotels("Paris", "6 Quai de Gesvres", 200000, 0).readEntity(new GenericType<List<Hotel>>(){});
        
        for(Hotel h : hotels) {
                hotel_facade.create(h);
        }
        
        // On récupère tout et on renvoie pour vérifier
        Object response;
        List<Hotel> list = hotel_facade.findAll();
        response = new GenericEntity< List< Hotel > >(list) { };
        return Response.status(200).type("application/xml").entity(response).build();
    }
}
