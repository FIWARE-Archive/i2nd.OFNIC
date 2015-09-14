/*package uniroma1.fiware.ofnic;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
         Other code omitted for brevity

        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", SimpleCORSFilter.class);
        corsFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}*/