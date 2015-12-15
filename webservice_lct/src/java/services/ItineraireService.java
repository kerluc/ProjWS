/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Wael
 */
@WebService(serviceName = "ItineraireService")
@Stateless()
public class ItineraireService {

    /**
     * This is a sample web service operation
     */
    @GET
    @Path("find/itineraire")
    @Produces("application/xml")
    @WebMethod(operationName = "getItineraire")
    public String getItineraire(@WebParam(name = "adresse") String adresse) {
        String baseURL = "http://apir.viamichelin.com/apir/";
        String KEY = "RESTGP20151212170947528688600615";
        Client client = Client.create();
        WebResource resource = client.resource(baseURL+"1/route.xml/fra?steps=1:e:2.0:48.0;1:e:3.0:49.0&authkey="+KEY);
        String string = resource.accept(MediaType.APPLICATION_XML)
                .get(String.class);
        return string;
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
