/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Lucien
 */
@Named(value = "MainController")
@Dependent
public class MainController {
    
    public MainController() {
    }
    
    public String signUp() {        
        return "index";
    }
    
    public String addEtudiant() {   
        String baseURL = "http://localhost:8080/webservice_lct/";
        Client client = Client.create();
        WebResource resource = client.resource(baseURL+"addEtudiant/{nom}/{prenom}/{email}/{password}/{budget}");
        return resource.toString();
    }
}
