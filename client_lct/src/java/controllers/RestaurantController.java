package controllers;

import javax.ejb.Stateless;
import entities.Restaurant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@Named(value = "restaurantController")
@Stateless
public class RestaurantController {

    List<Restaurant> restaurants;
    String ville;
    String adresse;

    int budget;

    // distance de recherche (en km)
    int range;

    public RestaurantController() {
    }

    @PostConstruct
    public void init() {
        restaurants = new ArrayList<>();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
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
        restaurants = client.target("http://localhost:8080/webservice_lct/rest")
                .path("viaMichelinService").path("findRestaurant")
                .path(ville)
                .path(adresse)
                .path(String.valueOf(range * 1000))
                .path(String.valueOf(budget))
                .request(MediaType.APPLICATION_XML)
                .get().readEntity(new GenericType<List<Restaurant>>() {
                });

        return "restaurant?faces-redirect=true";
    }

    public String itineraire() {
        return "restaurant?faces-redirect=true";
    }
    
}
