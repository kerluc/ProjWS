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

@XmlRootElement(name = "res_hotel")
@Entity
@NamedQueries({
    @NamedQuery(name = "ReservationHotel.findByEtudiant", query = "SELECT r FROM ReservationHotel r WHERE r.etudiant.id = :id")})
public class ReservationHotel implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name="RESHOT_GEN", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="RESHOT_GEN")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="RESHOT_GEN")
    @Column(name="ID_RES")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID")
    private Etudiant etudiant;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_HOTEL")
    private Hotel hotel;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="DATE_DEB")
    private Date date_debut;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name="DATE_FIN")
    private Date date_fin;

    public ReservationHotel() {
    }

    public ReservationHotel(Etudiant etudiant, Hotel hotel, Date debut, Date fin) {
        this.etudiant = etudiant;
        this.hotel = hotel;
        this.date_debut = debut;
        this.date_fin = fin;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "ReservationHotel{" + "id=" + id + ", etudiant=" + etudiant + ", hotel=" + hotel + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
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
