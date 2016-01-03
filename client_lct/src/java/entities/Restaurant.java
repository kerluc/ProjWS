package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Restaurant {
    
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
    private String type;
    private float prix;

    public Restaurant() {
    }

    public Restaurant(Long id, String nom, String ville, String adresse, String type, float prix) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.type = type;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
}
