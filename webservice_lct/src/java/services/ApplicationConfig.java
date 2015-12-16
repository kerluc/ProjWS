package services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {
    
    public ApplicationConfig() {}
    
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                EtudiantService.class,
                ViaMichelinService.class
        ));
    }
}
