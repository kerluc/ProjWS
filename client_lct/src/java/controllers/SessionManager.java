package controllers;

import entities.Etudiant;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Named(value = "sessionManager")
@SessionScoped
public class SessionManager implements Serializable {

    // Types d'utilisateurs possibles
    static final int GUEST = 0;
    static final int USER = 1;
    static final int ADMIN = 2;
    
    // Informations de session
    Etudiant user;
    int type_account;
    
    // Pour les formulaires de connexion / enregistrement
    String lname;
    String fname;
    int budget;
    String login;
    String password;
    
    public SessionManager() {
    }
    
    @PostConstruct
    public void init() {
        user = null;
        type_account = SessionManager.GUEST;
    }

    public Etudiant getUser() {
        return user;
    }

    public int getType_account() {
        return type_account;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
    
    public String create() {

            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("etudiantService").path("addEtudiant")
                    .path(lname).path(fname).path(login).path(password).path(String.valueOf(budget))
                    .request(MediaType.TEXT_PLAIN)
                    .post(null, String.class);
            
            if(response.equals("fail")) {
                Logger.getLogger(SessionManager.class.getName()).log(Level.WARNING, "Echec a la creation du compte");
                clear_form();
                return "connexion?faces-redirect=true";
            }
            
            return connect();
    }
    
    public String connect() {
            
            Client client = ClientBuilder.newClient();
            Etudiant etudiant = client.target("http://localhost:8080/webservice_lct/rest")
                    .path("etudiantService").path("validate/"+login+"/"+password)
                    .request(MediaType.APPLICATION_XML)
                    .get(Etudiant.class);
            
            clear_form();
            
            if(etudiant != null) {
                user = etudiant;
                type_account = USER;
                return "index?faces-redirect=true";
            }
            else {
                Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, "Echec a la connexion");   
                return "connexion?faces-redirect=true";
            }
    }
    
    public String logout() {
        user = null;
        type_account = GUEST;
        clear_form();
        return "index?faces-redirect=true";
    }
    
    public void clear_form() {
        fname = lname = password = login = "";
        budget = 0;
    }
}
