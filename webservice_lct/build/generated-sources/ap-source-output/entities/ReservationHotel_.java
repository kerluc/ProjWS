package entities;

import entities.Etudiant;
import entities.Hotel;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-04T23:06:28")
@StaticMetamodel(ReservationHotel.class)
public class ReservationHotel_ { 

    public static volatile SingularAttribute<ReservationHotel, Date> date_debut;
    public static volatile SingularAttribute<ReservationHotel, Hotel> hotel;
    public static volatile SingularAttribute<ReservationHotel, Date> date_fin;
    public static volatile SingularAttribute<ReservationHotel, Long> id;
    public static volatile SingularAttribute<ReservationHotel, Etudiant> etudiant;

}