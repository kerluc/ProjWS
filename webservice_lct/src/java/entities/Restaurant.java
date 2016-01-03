package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "RESTAURANT")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name="RESTO_GEN", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="RESTO_SEQ")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RESTO_GEN")
    @Column(name="ID_RESTO")
    private Long id;

    @Column(name="NOM")
    private String nom;
    
    @Column(name="VILLE")
    private String ville;
    
    @Column(name="ADRESSE")
    private String adresse;
    
    @Column(name="TYPE") // Type de cuisine
    private String type;
    
    @Column(name="PRIX")
    private float prix;

    public Restaurant() {
    }

    public Restaurant(String nom, String ville, String adresse, String type, float prix) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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
        if (!(object instanceof Restaurant)) {
            return false;
        }
        Restaurant other = (Restaurant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Restaurant[ id=" + id + " ]";
    }
    
}
