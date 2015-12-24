package addresses;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

public class GeocodeParser {
    
    public Coords getCoords(String locations) {
        
        SAXBuilder builder = new SAXBuilder();
        
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
            
        } catch (JDOMException ex) {
            Logger.getLogger(GeocodeParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeocodeParser.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return coords;
    }
}
