package services;

import entities.Etudiant;
import entities.Hotel;
import entities.ReservationHotel;
import entities.ReservationRestaurant;
import entities.Restaurant;
import entities.facade.EtudiantFacade;
import entities.facade.HotelFacade;
import entities.facade.ReservationHotelFacade;
import entities.facade.ReservationRestaurantFacade;
import entities.facade.RestaurantFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import pojo.Reservation;

@Path("reservationService")
@Stateless()
public class ReservationService {
   
   /* Facades reservations */
   @Inject
   ReservationHotelFacade reshotel_facade;
   @Inject
   ReservationRestaurantFacade resrestaurant_facade;
   
   /* Facades Hotel, Restaurant, Etudiant */
   @Inject
   HotelFacade hotel_facade;
   @Inject
   RestaurantFacade restaurant_facade;
   @Inject
   EtudiantFacade etu_facade;
   
   @Path("findResHotelByUser/{id_etudiant}")
   @GET
   @Produces("application/xml")
   public Response findResHotelByUser(@PathParam("id_etudiant") Long id) {
        List<ReservationHotel> result = reshotel_facade.findByEtudiant(id);
        if (result == null)
            return null;
        Object response; 
        response = new GenericEntity< List< ReservationHotel > >(result) { };
        return Response.status(200).type("application/xml").entity(response).build();
   }
   
   @Path("addResHotel/{id_etudiant}")
   @POST
   @Consumes("application/xml") // Objet Reservation : Comprend l'hotel et les dates souhaitées
   @Produces("text/plain")
   public String addResHotel(@PathParam("id_etudiant") Long id_etud, Reservation r) {
       
       Etudiant e = etu_facade.findById(id_etud);
       if(e == null) return "fail";
       
       Hotel hotel = hotel_facade.findByTel(r.getHotel().getTel());
       if(hotel == null) {
           hotel_facade.create(r.getHotel());
           hotel = hotel_facade.findByTel(r.getHotel().getTel());
       }
       
       ReservationHotel reservation = new ReservationHotel(e, hotel, r.getDeb(), r.getFin());
       reshotel_facade.create(reservation);
       return "ok";
   }
   
   @Path("addResRestaurant/{id_etudiant}")
   @POST
   @Consumes("application/xml") // Objet Reservation : Comprend le restaurant et les dates souhaitées
   @Produces("text/plain")
   public String addResRestaurant(@PathParam("id_etudiant") Long id_etud, Reservation r) {
       
       Etudiant e = etu_facade.findById(id_etud);
       if(e == null) return "fail";
       
       Restaurant resto = restaurant_facade.findByAdresse(r.getRestaurant().getAdresse());
       if(resto == null) {
           restaurant_facade.create(r.getRestaurant());
           resto = restaurant_facade.findByAdresse(r.getRestaurant().getAdresse());
       }
       
       ReservationRestaurant reservation = new ReservationRestaurant(e, resto, r.getDeb());
       resrestaurant_facade.create(reservation);
       return "ok";
   }
}
