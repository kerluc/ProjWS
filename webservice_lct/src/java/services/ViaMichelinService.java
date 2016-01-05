package services;

import config.ConfigViaMichelin;
import entities.Hotel;
import entities.Restaurant;
import java.util.List;
import pojo.Coords;
import pojo.ViaMichelinXMLParser;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("viaMichelinService")
@Stateless()
public class ViaMichelinService {

    static final String BASE_URL = "http://apir.viamichelin.com/apir";

    /*
    ** trouver des hotels dans un certain rayon autour d'une adresse
    ** TODO: utiliser les filtres
     */
    @GET
    @Path("findHotel/{city}/{address}/{distance}/{budget}")
    @Produces("application/xml")
    public Response getHotels(@PathParam("city") String city,
            @PathParam("address") String address,
            @PathParam("distance") int distance,
            @PathParam("budget") int budget) {
        Coords coords = getLongLat(city, address);

        if (coords == null) {
            return null;
        }

        String filtre = "AGG.provider eq HOTGR";
        if (budget != 0) {
            filtre += " AND price_classification eq " + budget;
        }

        String url = ViaMichelinService.BASE_URL + "/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("HOTEL")
                .path("fra")
                .queryParam("center", coords.getLongitude() + ":" + coords.getLatitude())
                .queryParam("authkey", ConfigViaMichelin.auth_key)
                .queryParam("dist", distance)
                .queryParam("nb", 50)
                .queryParam("source", "HOTGR")
                .queryParam("filter", filtre)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        ViaMichelinXMLParser xmlParser = new ViaMichelinXMLParser();
        List<Hotel> hotels = xmlParser.getHotels(response);

        // On récupère tout et on renvoie pour vérifier
        Object result;

        result = new GenericEntity< List< Hotel>>(hotels) {
        };
        return Response.status(200).type("application/xml").entity(result).build();
    }

    /*
    ** trouver des restaurants dans un certain rayon autour d'une adresse
     */
    @GET
    @Path("findRestaurant/{city}/{address}/{distance}/{budget}")
    @Produces("application/xml")
    public Response getRestaurants(@PathParam("city") String city,
            @PathParam("address") String address,
            @PathParam("distance") int distance,
            @PathParam("budget") int budget) {

        Coords coordonees = getLongLat(city, address);

        if (coordonees == null) {
            return null;
        }

        String coords = coordonees.getLongitude() + ":" + coordonees.getLatitude();

        String filtre = "AGG.provider eq RESGR";
        if (budget != 0) {
            filtre += " AND price_classification eq " + budget;
        }

        String url = ViaMichelinService.BASE_URL + "/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("RESTAURANT")
                .path("fra")
                .queryParam("center", coords)
                .queryParam("authkey", ConfigViaMichelin.auth_key)
                .queryParam("dist", distance)
                .queryParam("nb", 50)
                .queryParam("source", "RESGR")
                .queryParam("filter", filtre)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        ViaMichelinXMLParser xmlParser = new ViaMichelinXMLParser();
        List<Restaurant> restaurants = xmlParser.getRestaurants(response);

        // On récupère tout et on renvoie pour vérifier
        Object result;

        result = new GenericEntity< List< Restaurant>>(restaurants) {
        };
        return Response.status(200).type("application/xml").entity(result).build();
    }

    /**
     * Pour retrouver les longitudes / latitudes depuis une adresse donnée
     *
     * @param city
     * @param address
     * @return coordonees pour l'adresse donnée
     */
    protected Coords getLongLat(String city, String address) {
        String url = ViaMichelinService.BASE_URL + "/1/geocode3f.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .queryParam("lg", "fra")
                .queryParam("favc", "FRA")
                .queryParam("cityzip", city)
                .queryParam("address", address)
                .queryParam("nb", String.valueOf(20))
                .queryParam("authkey", ConfigViaMichelin.auth_key)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        ViaMichelinXMLParser parser = new ViaMichelinXMLParser();
        Coords coords = parser.getCoords(response);

        return coords;
    }

    @GET
    @Path("/itineraire/{address}/{city}")
    @Produces("application/xml")
    public String getItineraire(@PathParam("address") String address, @PathParam("city") String city) {
        
        Coords coordonees = getLongLat(city, address);
        if (coordonees == null) {
            return null;
        }
        String coords = coordonees.getLongitude() + ":" + coordonees.getLatitude();
        
        //
        String urlroute = ViaMichelinService.BASE_URL + "/1/route.xml";
        Client client = ClientBuilder.newClient();
        String iti_trace = client.target(urlroute)
                .path("fra")
                .queryParam("steps", coords)
                .queryParam("authkey", ConfigViaMichelin.auth_key)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        String urlMap = ViaMichelinService.BASE_URL + "/1/bestMap.xml";
        Client client2 = ClientBuilder.newClient();
        String map = client2.target(urlMap)
                .path("6.1:45.8:6.2:46.0")
                .path("800:600")
                .queryParam("iti_trace", iti_trace)
                .queryParam("fra")
                .queryParam("authkey", ConfigViaMichelin.auth_key)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        return map;
    }
    //exemple requete reims -> paris
    //http://apir.viamichelin.com/apir/1/Route.xml/
    //fra/?steps=1%3Ae%3A4.03309%3A49.2566%3B1%3Ae%3A1.90398%3A47.90139&distUnit=m&currency=EUR&fuelCost=1.4&veht=0&itit=0&tollCat=car&itiIdx=0&favMotorways=true&authKey=RESTGP20151212170947528688600615&charset=UTF-8
}
