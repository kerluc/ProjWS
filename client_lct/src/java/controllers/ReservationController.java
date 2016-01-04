package controllers;

import entities.Etudiant;
import javax.ejb.Stateless;
import entities.Hotel;
import entities.ReservationInfos;
import entities.Restaurant;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Named(value = "reservationController")
@Stateless
public class ReservationController {
    
    Etudiant e;
    Hotel selectedHotel;
    Restaurant selectedRest;
    Date debut;
    Date fin;
    
    
    String selectedType;
    String responseMessage;
    
    public ReservationController() {
    }

    public Hotel getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public Restaurant getSelectedRest() {
        return selectedRest;
    }

    public void setSelectedRest(Restaurant selectedRest) {
        this.selectedRest = selectedRest;
    }

    public Etudiant getE() {
        return e;
    }

    public void setE(Etudiant e) {
        this.e = e;
    }
    
    public String getResponseMessage() {
        return responseMessage;
    }
    
    private void setResponseMessage(String response) {
        if(response.equals("ok"))
            responseMessage = "Réservation effectuée";
        else 
            responseMessage = "Echec lors de la réservation";
    }
    
    /*
    ** Premiere etape de la reservation : On choisi les dates
    */
    public String reserver_step1() {
        return "reservation";
    }
    
    /*
    ** Deuxieme étape : On fait la requête de reservation et on affiche si c'est ok ou non
    */
    public String reserver_step2() {
    
        Client client = ClientBuilder.newClient();
        WebTarget target  = client.target("http://localhost:8080/webservice_lct/rest");
        
        String response;
        
        ReservationInfos reservation = new ReservationInfos();
        
        String xml;
        StringWriter sw = new StringWriter();
        
        if(selectedType.equals("hotel"))
        {    
            
        Logger.getAnonymousLogger().severe(selectedType);
            reservation.setDeb(debut);
            reservation.setFin(fin);
            reservation.setHotel(selectedHotel);
                
            try {
                JAXBContext carContext = JAXBContext.newInstance(ReservationInfos.class);
                Marshaller carMarshaller = carContext.createMarshaller();
                carMarshaller.marshal(reservation, sw);
                xml = sw.toString();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            
            
            response = target.path("reservationService")
                             .path("addResHotel")
                             .path(String.valueOf(e.getId()))
                             .request(MediaType.TEXT_PLAIN)
                             .post(Entity.entity(xml, MediaType.APPLICATION_XML), String.class);
            
            
        }
        else if (selectedType.equals("restaurant")) 
        {
            
            reservation.setDeb(debut);
            reservation.setFin(debut);
            reservation.setRestaurant(selectedRest);
            
            try {
                JAXBContext carContext = JAXBContext.newInstance(ReservationInfos.class);
                Marshaller carMarshaller = carContext.createMarshaller();
                carMarshaller.marshal(reservation, sw);
                xml = sw.toString();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            
            response = target.path("reservationService")
                             .path("addResRestaurant")
                             .path(String.valueOf(e.getId()))
                             .request(MediaType.TEXT_PLAIN)
                             .post(Entity.entity(xml, MediaType.APPLICATION_XML), String.class);
            
        }
        else {
            response = "";
        }
        
        setResponseMessage(response);
        
        return "validation_reservation";
    }


    
}
