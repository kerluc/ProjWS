package services;

import addresses.Coords;
import addresses.GeocodeParser;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Path("viaMichelinService")
@Stateless()
public class ViaMichelinService {

    static final String BASE_URL = "http://apir.viamichelin.com/apir";
    
    /*
    @GET
    @Path("find/itineraire")
    @Produces("application/xml")
    public String getItineraire(@PathParam("adresse") String adresse) {
        String baseURL = "http://apir.viamichelin.com/apir/";
        String KEY = "RESTGP20151212170947528688600615";
        Client client = Client.create();
        WebResource resource = client.resource(baseURL+"1/route.xml/fra?steps=1:e:2.0:48.0;1:e:3.0:49.0&authkey="+KEY);
        String string = resource.accept(MediaType.APPLICATION_XML)
                .get(String.class);
        return string;
    }
    */
    
    /*
    ** findHotel dans un certain rayon autour d'une adresse
    ** TODO: utiliser les filtres
    */
    @GET
    @Path("findHotel/{city}/{address}/{distance}")
    @Produces("application/xml")
    public String getHotels(@PathParam("city") String city, 
                            @PathParam("address") String address, 
                            @PathParam("distance") int distance) 
    {
        Coords coords = getLongLat(city, address);
        
        if (coords == null)
            return null;
        
        // On vérifie qu'on dépasse pas les bornes imposées par l'API
        distance = (distance < 1000) ? 1000 : distance;
        distance = (distance > 200000) ? 200000 : distance;
        
        String url = ViaMichelinService.BASE_URL+"/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("HOTEL")
                .path("fra")
                .queryParam("center", coords.getLongitude()+":"+coords.getLatitude())
                .queryParam("authkey", config.ConfigViaMichelin.auth_key)
                .queryParam("dist", distance)
                .queryParam("nb", 40)
                .queryParam("source", "HOTGR")        
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        return response;
    }
    
    /**
     * Pour retrouver les longitudes / latitudes depuis une addresse
     */
    private Coords getLongLat(String city, String address) {
        String url = ViaMichelinService.BASE_URL+"/1/geocode3f.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .queryParam("lg", "fra")
                .queryParam("favc", "FRA")
                .queryParam("cityzip", city)
                .queryParam("address", address)
                .queryParam("nb", String.valueOf(20))
                .queryParam("authkey", config.ConfigViaMichelin.auth_key)
                .queryParam("charset", "UTF-8")
                .queryParam("ie", "UTF-8")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);
        
        GeocodeParser parser = new GeocodeParser();
        Coords coords = parser.getCoords(response);
        
        return coords;
    }
}
