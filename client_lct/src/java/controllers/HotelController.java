/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import javax.ejb.Stateless;
import entities.Hotel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lucien
 */
@Named(value = "hotelController")
@Stateless
public class HotelController {

    List<Hotel> hotels;
    String ville;
    String adresse;
    
    /*
        1/ Moins de 50 EUR
        2/ Entre 50 et 70 EUR
        3/ Entre 70 et 90 EUR
        4/ Entre 90 et 130 EUR
        5/ Supérieur à 130 EUR
    */
    int budget;
    
    // distance de recherche (en km)
    int range;
    
    public HotelController() {
    }
    
    @PostConstruct
    public void init() {
        hotels = new ArrayList<>();
    }

    public List<Hotel> getHotels() {
        return hotels;
    }
    
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
    
    public String search() {
        Client client = ClientBuilder.newClient();
            hotels  = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("viaMichelinService").path("findHotel")
                    .path(ville)
                    .path(adresse)
                    .path(String.valueOf(range*1000))
                    .path(String.valueOf(budget))
                    .request(MediaType.APPLICATION_XML)
                    .get().readEntity(new GenericType<List<Hotel>>(){});
            
            
            return "hotel?faces-redirect=true";
    }
}
