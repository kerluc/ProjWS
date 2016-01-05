package admin;

import entities.Etudiant;
import entities.ReservationHotel;
import entities.ReservationInfos;
import entities.ReservationRestaurant;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.primefaces.event.RowEditEvent;

@Named(value = "adminReservation")
@Stateless
public class AdminReservation {

    private Etudiant user;
    private List<ReservationHotel> res_hotels;
    private List<ReservationRestaurant> res_restos;
    
    private ReservationHotel rhot;
    private ReservationRestaurant rresto;
    
    public AdminReservation() {
    }

    public Etudiant getUser() {
        return user;
    }

    public void setUser(Etudiant user) {
        this.user = user;
    }

    public List<ReservationHotel> getRes_hotels() {
        Client client = ClientBuilder.newClient();
        res_hotels = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("reservationService").path("findResHotelByUser")
                    .path(String.valueOf(user.getId()))
                    .request(MediaType.APPLICATION_XML)
                    .get().readEntity(new GenericType<List<ReservationHotel>>(){});
        
        return res_hotels;
    }

    public void setRes_hotels(List<ReservationHotel> res_hotels) {
        this.res_hotels = res_hotels;
    }

    public List<ReservationRestaurant> getRes_restos() {
        Client client = ClientBuilder.newClient();
        res_restos = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("reservationService").path("findResRestoByUser")
                    .path(String.valueOf(user.getId()))
                    .request(MediaType.APPLICATION_XML)
                    .get().readEntity(new GenericType<List<ReservationRestaurant>>(){});
        
        return res_restos;
    }

    public void setRes_restos(List<ReservationRestaurant> res_restos) {
        this.res_restos = res_restos;
    }

    public ReservationHotel getRhot() {
        return rhot;
    }

    public void setRhot(ReservationHotel rhot) {
        this.rhot = rhot;
    }

    public ReservationRestaurant getRresto() {
        return rresto;
    }

    public void setRresto(ReservationRestaurant rresto) {
        this.rresto = rresto;
    }

    public String showReservations() {
        return "admin_reservations";
    }

    public void editInitHotel(RowEditEvent e) {
        rhot = (ReservationHotel) e.getObject();
    }
    
    public void editInitResto(RowEditEvent e) {
        rresto = (ReservationRestaurant) e.getObject();
    }        
    
    public void editResHotel(RowEditEvent event) {
        
        // On prépare l'enveloppe
        ReservationInfos res_infos = new ReservationInfos(rhot.getHotel(), null, rhot.getDate_debut(), rhot.getDate_fin());
        
        StringWriter sw = new StringWriter();
        String xml;
        
        Client client = ClientBuilder.newClient();
        WebTarget target  = client.target("http://localhost:8080/webservice_lct/rest");
        try {
            JAXBContext carContext = JAXBContext.newInstance(ReservationInfos.class);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.marshal(res_infos, sw);
            xml = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        
        String response = target.path("reservationService")
                         .path("editResHotel")
                         .request(MediaType.TEXT_PLAIN)
                         .post(Entity.entity(xml, MediaType.APPLICATION_XML), String.class);

        if (response.equals("fail")) {
            Logger.getAnonymousLogger().severe("Erreur modification Reservation");
        }
        
    }
    
    public String supprResHotel() {
        Client client = ClientBuilder.newClient();
        String response = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("reservationService").path("deleteResHotel").path(String.valueOf(rhot.getId()))
                    .request(MediaType.TEXT_PLAIN)
                    .delete(String.class);
        
        if (response.equals("ok")) {
            res_hotels.remove(rhot);
        }
        
        return "admin_reservations?faces-redirect=true";
    }
    
    
    public void editResResto(RowEditEvent event) {
        
        // On prépare l'enveloppe qu'on va envoyer
        ReservationInfos res_infos = new ReservationInfos(null, rresto.getRestaurant(), rresto.getDate_debut(), rresto.getDate_debut());
        
        StringWriter sw = new StringWriter();
        String xml;
        
        Client client = ClientBuilder.newClient();
        WebTarget target  = client.target("http://localhost:8080/webservice_lct/rest");
        try {
            JAXBContext carContext = JAXBContext.newInstance(ReservationInfos.class);
            Marshaller carMarshaller = carContext.createMarshaller();
            carMarshaller.marshal(res_infos, sw);
            xml = sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        
        String response = target.path("reservationService")
                         .path("editResResto")
                         .request(MediaType.TEXT_PLAIN)
                         .post(Entity.entity(xml, MediaType.APPLICATION_XML), String.class);

        if (response.equals("fail")) {
            Logger.getAnonymousLogger().severe("Erreur modification Reservation");
        }
        
    }
    
    public String supprResRestaurant() {
        Logger.getAnonymousLogger().severe(String.valueOf(rresto.getId()));
        Client client = ClientBuilder.newClient();
        String response = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("reservationService").path("deleteResResto").path(String.valueOf(rresto.getId()))
                    .request(MediaType.TEXT_PLAIN)
                    .delete(String.class);
        
        if (response.equals("ok")) {
            res_restos.remove(rresto);
        }
        
        return "admin_reservations?faces-redirect=true";
    }
}
