package services;

import entities.Hotel;
import entities.Restaurant;
import java.util.List;
import java.util.logging.Logger;
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
    @GET
    @Path("/itineraire")
    @Produces("application/xml")
    @WebMethod(operationName = "getItineraire")
    public void getItineraire(@WebParam(name = "adresse") String adresse) {
        String baseURL = "http://apir.viamichelin.com/apir/";
        String KEY = "RESTGP20151212170947528688600615";
        Client client = Client.create();
        WebResource resource
                = client.resource(baseURL + "1/route.xml/fra?steps=1:e:2.0:48.0;1:e:3.0:49.0&authkey=" + KEY);
        String string = resource.accept(MediaType.APPLICATION_XML).get(String.class);
        //System.out.println(string);
    }
    */
    
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
                            @PathParam("budget") int budget) 
    {
        Coords coords = getLongLat(city, address);
        
        if (coords == null)
            return null;
        
        String filtre = "AGG.provider eq HOTGR";
        if(budget != 0) {
            filtre += " AND price_classification eq "+budget;
        }
        
        String url = ViaMichelinService.BASE_URL+"/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("HOTEL")
                .path("fra")
                .queryParam("center", coords.getLongitude()+":"+coords.getLatitude())
                .queryParam("authkey", "RESTGP20151212170947528688600615")
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
        
        result = new GenericEntity< List< Hotel > >(hotels) { };
        return Response.status(200).type("application/xml").entity(result).build();
    }
    
    /*
    ** trouver des restaurants dans un certain rayon autour d'une adresse
    ** Si coords non nul alors privilégier les coordonées
    */
    @GET
    @Path("findRestaurant/{city}/{address}/{distance}/{budget}")
    @Produces("application/xml")
    public Response getRestaurants(@PathParam("city") String city, 
                            @PathParam("address") String address, 
                            @PathParam("distance") int distance,
                            @PathParam("budget") int budget) 
    {
        
        
        Coords coordonees = getLongLat(city, address);

        if (coordonees == null)
            return null;

        String coords = coordonees.getLongitude()+":"+coordonees.getLatitude();
        
        Logger.getAnonymousLogger().severe(coords);
        String filtre = "AGG.provider eq RESGR";
        if(budget != 0) {
            filtre += " AND price_classification eq "+budget;
        }
        
        String url = ViaMichelinService.BASE_URL+"/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("RESTAURANT")
                .path("fra")
                .queryParam("center", coords)
                .queryParam("authkey", "RESTGP20151212170947528688600615")
                .queryParam("dist", distance)
                .queryParam("nb", 50)
                .queryParam("source", "RESGR")
                .queryParam("filter", filtre)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        Logger.getAnonymousLogger().severe(response);
        ViaMichelinXMLParser xmlParser = new ViaMichelinXMLParser();
        List<Restaurant> restaurants = xmlParser.getRestaurants(response);

        // On récupère tout et on renvoie pour vérifier
        Object result;
        
        result = new GenericEntity< List< Restaurant > >(restaurants) { };
        return Response.status(200).type("application/xml").entity(result).build();
    }
    
    /**
     * Pour retrouver les longitudes / latitudes depuis une adresse donnée
     * @param city
     * @param address
     * @return coordonees pour l'adresse donnée
     */
    protected Coords getLongLat(String city, String address) {
        String url = ViaMichelinService.BASE_URL+"/1/geocode3f.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .queryParam("lg", "fra")
                .queryParam("favc", "FRA")
                .queryParam("cityzip", city)
                .queryParam("address", address)
                .queryParam("nb", String.valueOf(20))
                .queryParam("authkey", "RESTGP20151212170947528688600615")
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        ViaMichelinXMLParser parser = new ViaMichelinXMLParser();
        Coords coords = parser.getCoords(response);
        
        return coords;
    }
}
