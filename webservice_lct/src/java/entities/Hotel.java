/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "HOTEL")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_HOTEL")
    private Long id;

    @Column(name="NOM")
    private String nom;
    
    @Column(name="VILLE")
    private String ville;
    
    @Column(name="ADRESSE")
    private String adresse;
    
    @Column(name="TEL")
    private String tel;
    
    @Column(name="PRIX")
    private float prix;
        
    @OneToMany(mappedBy="hotel", cascade = CascadeType.PERSIST)
    private List<Chambre> chambres = new ArrayList<>();
    
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

    public List<Chambre> getChambres() {
        return chambres;
    }

    public void addChambre(Chambre c) {
        this.chambres.add(c);
        if (c.getHotel() != this) {
            c.setHotel(this);
        }
    }

    public Hotel () {
        
    }

    public Hotel(String nom, String ville, String adresse, String tel, float prix) {
        this.nom = nom;
        this.ville = ville;
        this.adresse = adresse;
        this.tel = tel;
        this.prix = prix;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", nom=" + nom + ", ville=" + ville + ", adresse=" + adresse + ", tel=" + tel + ", prix=" + prix + ", chambres=" + chambres + '}';
    }

    
}
