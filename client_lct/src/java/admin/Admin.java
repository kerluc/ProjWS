
package admin;

import entities.Etudiant;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@Named(value = "admin")
@SessionScoped
public class Admin implements Serializable{
    
    private List<Etudiant> etudiants;
    
    public Admin() {
    }
    
    public List<Etudiant> getEtudiants() {
        Client client = ClientBuilder.newClient();
        etudiants = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("etudiantService").path("find/")
                    .request(MediaType.APPLICATION_XML)
                    .get().readEntity(new GenericType<List<Etudiant>>(){});
        
        return etudiants;
    }
    
}
