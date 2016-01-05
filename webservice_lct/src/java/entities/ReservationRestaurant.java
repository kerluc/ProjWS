package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "res_restaurant")
@Entity
@NamedQueries({
    @NamedQuery(name = "ReservationRestaurant.findById", query = "SELECT e FROM ReservationRestaurant e WHERE e.id = :id"),
    @NamedQuery(name = "ReservationRestaurant.findByEtudiant", query = "SELECT r FROM ReservationRestaurant r WHERE r.etudiant.id = :id")})
public class ReservationRestaurant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name="RESRESTO_GEN", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="RESRESTO_GEN")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RESRESTO_GEN")
    @Column(name="ID_RES")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID")
    private Etudiant etudiant;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_RESTO")
    private Restaurant restaurant;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="DATE_DEB")
    private Date date_debut;

    public ReservationRestaurant() {
    }

    public ReservationRestaurant(Etudiant etudiant, Restaurant restau, Date debut) {
        this.etudiant = etudiant;
        this.restaurant = restau;
        this.date_debut = debut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    @Override
    public String toString() {
        return "ReservationRestaurant{" + "id=" + id + ", etudiant=" + etudiant + ", restau=" + restaurant + ", date_debut=" + date_debut + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservationHotel other = (ReservationHotel) obj;
        return true;
    }
    
}
