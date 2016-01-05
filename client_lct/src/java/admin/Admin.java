
package admin;

import entities.Etudiant;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.primefaces.event.RowEditEvent;

@Named(value = "admin")
@Stateless
public class Admin implements Serializable{
    
    private List<Etudiant> etudiants;
    private Etudiant selectedEtudiant;

    public Etudiant getSelectedEtudiant() {
        return selectedEtudiant;
    }

    public void setSelectedEtudiant(Etudiant selectedEtudiant) {
        this.selectedEtudiant = selectedEtudiant;
    }
    
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
    
    public String supprEtudiant() {
        
        Client client = ClientBuilder.newClient();
        String response = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("etudiantService").path("delete").path(String.valueOf(selectedEtudiant.getId()))
                    .request(MediaType.TEXT_PLAIN)
                    .delete(String.class);
        
        if (response.equals("ok")) {
            etudiants.remove(selectedEtudiant);
        }
        
        return "admin_etudiants?faces-redirect=true";
    }
    
    public void editInit(RowEditEvent e) {
        selectedEtudiant = (Etudiant) e.getObject();
    }
    
    public void editEtudiant(RowEditEvent event) {
        
        Etudiant e = selectedEtudiant;
        
        Client client = ClientBuilder.newClient();
        String response = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("etudiantService").path("editEtudiant")
                    .path(String.valueOf(e.getId()))
                    .path(e.getNom())
                    .path(e.getPrenom())
                    .path(e.getEmail())
                    .path(e.getPw())
                    .path(e.getAdresse())
                    .path(e.getVille())
                    .path(String.valueOf(e.getBudget()))
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);
        
        if (response.equals("fail")) {
            Logger.getAnonymousLogger().severe("Erreur modification Etudiant");
        }
        
    }
    
}
