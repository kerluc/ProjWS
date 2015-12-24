package entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "etudiant")
public class Etudiant {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private int budget;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email,int budget) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.budget = budget;
    }

    @XmlElement(name="id")
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @XmlElement(name="nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlElement(name="prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @XmlElement(name="budget")
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
    
    
}