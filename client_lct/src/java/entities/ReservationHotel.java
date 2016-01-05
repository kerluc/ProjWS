package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "res_hotel")
public class ReservationHotel {
    
    private Long id;
    private Etudiant etudiant;
    private Hotel hotel;
    private Date date_debut;
    private Date date_fin;

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
    
    public String getDateFormat(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("d/M/y", Locale.FRANCE);
        return df.format(d);
    }
    
    
    
}
