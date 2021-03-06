package pojo;

import entities.Hotel;
import entities.Restaurant;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ViaMichelinXMLParser {
    
    SAXBuilder builder;
    
    public ViaMichelinXMLParser() {
        builder = new SAXBuilder();
    }
    
    public Coords getCoords(String locations) {

        Coords coords = new Coords();        
        try {
            
            Document doc = builder.build(new InputSource( new StringReader( locations ) ) );
            Element root = doc.getRootElement();

            Element size = root.getChild("size");
            if(Integer.valueOf(size.getText()) < 1)
                return null;
            
            
            Element location = root.getChild("locationList").getChild("item").getChild("location");
            coords.setAddress(location.getChild("formattedAddressLine").getText());
            coords.setLatitude(Float.valueOf(location.getChild("coords").getChild("lat").getText()));
            coords.setLongitude(Float.valueOf(location.getChild("coords").getChild("lon").getText()));
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ViaMichelinXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return coords;
    }
    
    public List<Hotel> getHotels(String src) {
        List<Hotel> hotels = new ArrayList<>();
        
        try {
            
            Document doc = builder.build(new InputSource(new StringReader(src)));
            Element root = doc.getRootElement();
            
            Element size = root.getChild("searchInfos").getChild("numFound");
            if(Integer.valueOf(size.getText()) < 1)
                return hotels;
            
            List<Element> list = root.getChild("poiList").getChildren("poi");
            
            for(Element e : list) {
                Element infos = e.getChild("datasheets");
                if(infos.getChild("datasheet") == null) continue;
                infos = infos.getChild("datasheet");
                
                Hotel h = new Hotel(infos.getChild("name").getText(),
                                    infos.getChild("city").getText(),
                                    infos.getChild("address").getText(),
                                    infos.getChild("local_phone").getText(),
                                    Float.valueOf(infos.getChild("lowest_room_price").getText()),
                                    Integer.valueOf(infos.getChild("nb_rooms").getText()));
                 
                hotels.add(h);
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ViaMichelinXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return hotels;
    }

    public List<Restaurant> getRestaurants(String response) {
        
        List<Restaurant> restaurants = new ArrayList<>();
        
        try {
            
            Document doc = builder.build(new InputSource(new StringReader(response)));
            Element root = doc.getRootElement();
            
            Element size = root.getChild("searchInfos").getChild("numFound");
            if(Integer.valueOf(size.getText()) < 1)
                return restaurants;
            
            List<Element> list = root.getChild("poiList").getChildren("poi");
            
            for(Element e : list) {
                Element infos = e.getChild("datasheets");
                if(infos.getChild("datasheet") == null) continue;
                infos = infos.getChild("datasheet");
                
                Restaurant r = new Restaurant(infos.getChild("name").getText(),
                                    infos.getChild("city").getText(),
                                    infos.getChild("address").getText(),
                                    infos.getChild("cooking_lib").getText(),
                                    Float.valueOf(infos.getChild("price_min_gm21").getText()));
                
                
                               
                restaurants.add(r);
            }
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(ViaMichelinXMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return restaurants;
    }
}
