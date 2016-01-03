package entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotel")
public class Hotel {
    
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
    private String tel;
    private float prix;
    
    public Hotel() {
    }

    public Hotel(Long id, String nom, String ville, String adresse, String tel, float prix) {
        this.id = id;
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.tel = tel;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
}
