package services;

import pojo.Coords;
import pojo.ViaMichelinXMLParser;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Wael
 */
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

    @GET
    @Path("/getHotels")
    @Produces("application/xml")
    @WebMethod(operationName = "getHotels")
    public String getHotels(@WebParam(name = "adresse") String adresse) {
        String baseURL = "http://apir.viamichelin.com/apir/";
        String KEY = "RESTGP20151212170947528688600615";
        Client client = Client.create();
        WebResource resource
                = client.resource(baseURL + "2/findPoi.xml/HOTEL/eng?center=2.35:48.85&nb=30&dist=1500&source=HOTGR&filter=AGG.provider eq HOTGR&charset=UTF-8&ie=UTF-8&authKey=" + KEY);
        String string = resource.accept(MediaType.APPLICATION_XML).get(String.class);
        return string;
    }

    @GET
    @Path("/getRestaurants")
    @Produces("application/xml")
    @WebMethod(operationName = "getRestaurants")
    public String getRestaurants(@WebParam(name = "adresse") String adresse) {
        String baseURL = "http://apir.viamichelin.com/apir/";
        String KEY = "RESTGP20151212170947528688600615";
        Client client = Client.create();
        WebResource resource
                = client.resource(baseURL + "2/findPoi.xml/RESTAURANT/eng?center=2.35:48.85&nb=10&dist=1000&source=RESGR&filter=AGG.provider eq RESGR&charset=UTF-8&ie=UTF-8&authKey=" + KEY);
        String string = resource.accept(MediaType.APPLICATION_XML).get(String.class);
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
        
        String url = ViaMichelinService.BASE_URL+"/2/findPOI.xml";
        Client client = ClientBuilder.newClient();
        String response = client.target(url)
                .path("HOTEL")
                .path("fra")
                .queryParam("center", coords.getLongitude()+":"+coords.getLatitude())
                .queryParam("authkey", config.ConfigViaMichelin.auth_key)
                .queryParam("dist", distance)
                .queryParam("nb", 50)
                .queryParam("source", "HOTGR")
                .queryParam("filter","AGG.provider eq HOTGR")
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
        
        ViaMichelinXMLParser parser = new ViaMichelinXMLParser();
        Coords coords = parser.getCoords(response);
        
        return coords;
    }
}

/*
            @WebParam(name = "lg") String lg,
            @WebParam(name = "steps") String steps,
            @WebParam(name = "data") String data,
            @WebParam(name = "veht") String veht,
            @WebParam(name = "wCaravan") String wCaravan,
            @WebParam(name = "favMotorways") String favMotorways,
            @WebParam(name = "avoidBorders") String avoidBorders,
            @WebParam(name = "avoidTolls") String avoidTolls,
            @WebParam(name = "avoidCCZ") String avoidCCZ,
            @WebParam(name = "avoidORC") String avoidORC,
            @WebParam(name = "multipleIti") String multipleIti,
            @WebParam(name = "itiIdx") String itiIdx,
            @WebParam(name = "distUnit") String distUnit,
            @WebParam(name = "fuelConsump") String fuelConsump,
            @WebParam(name = "fuelCost") String fuelCost,
            @WebParam(name = "date") String date,
            @WebParam(name = "currency") String currency,
            @WebParam(name = "ecoTax") String ecoTax,
            @WebParam(name = "truckOpts") String truckOpts,
            @WebParam(name = "distanceByCountry") String distanceByCountry,
            @WebParam(name = "authkey") String authkey,
            @WebParam(name = "signature") String signature,
            @WebParam(name = "expires") String expires,
            @WebParam(name = "callback") String callback,
            @WebParam(name = "charset") String charset,
            @WebParam(name = "ie") String ie)
 */
