package org.javiermf.features.services.rest.epa;

import org.javiermf.features.services.rest.ProductsConfigurationFeaturesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.net.URISyntaxException;

@Component
@Produces("application/json")
public class ProductsConfigurationResourceEnablement {

    @Autowired
    EnablementService enablementService;

    @Autowired
    ProductsConfigurationFeaturesResourceEnablement productsConfigurationFeaturesResourceEnablement;

    @GET
    public boolean getConfigurationsForProduct(@PathParam("productName") String productName) {
        return enablementService.withProducts();
    }

    @Path("/{configurationName}")
    @GET
    public boolean getConfigurationWithNameForProduct() {
        return  enablementService.withConfiguration();
    }

    @POST
    @Path("/{configurationName}")
    public boolean addConfiguration() {
        return enablementService.withProducts();
    }

    @DELETE
    @Path("/{configurationName}")
    public boolean deleteConfiguration() {
        return enablementService.withConfiguration();
    }

    @Path("/{configurationName}/features")
    public ProductsConfigurationFeaturesResourceEnablement getConfigurationActivedFeatures() {
        return productsConfigurationFeaturesResourceEnablement;

    }


}
