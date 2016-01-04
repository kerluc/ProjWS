package entities;

import entities.Etudiant;
import entities.Restaurant;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-04T23:06:28")
@StaticMetamodel(ReservationRestaurant.class)
public class ReservationRestaurant_ { 

    public static volatile SingularAttribute<ReservationRestaurant, Restaurant> restau;
    public static volatile SingularAttribute<ReservationRestaurant, Date> date_debut;
    public static volatile SingularAttribute<ReservationRestaurant, Long> id;
    public static volatile SingularAttribute<ReservationRestaurant, Etudiant> etudiant;

}