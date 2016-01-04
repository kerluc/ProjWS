
package pojo;

import entities.Hotel;
import entities.Restaurant;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reservation {
    
    private Hotel hotel;
    private Date deb;
    private Date fin;
    private Restaurant restaurant;

    public Reservation() {
    }

    public Reservation(Hotel hotel, Restaurant restaurant, Date deb, Date fin) {
        this.hotel = hotel;
        this.restaurant = restaurant;
        this.deb = deb;
        this.fin = fin;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public Date getDeb() {
        return deb;
    }

    public void setDeb(Date deb) {
        this.deb = deb;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
    
    
}
