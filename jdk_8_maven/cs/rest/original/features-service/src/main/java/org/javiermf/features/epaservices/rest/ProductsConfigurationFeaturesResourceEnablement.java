package org.javiermf.features.epaservices.rest;

import org.javiermf.features.epaservices.EnablementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

@Component
@Produces("application/json")
public class ProductsConfigurationFeaturesResourceEnablement {

    @Autowired
    EnablementService enablementService;

    @GET
    public boolean getConfigurationActivedFeatures() {
        return enablementService.withConfiguration();
    }

    @POST
    @Path("/{featureName}")
    public boolean addFeatureToConfiguration() {
        return enablementService.withConfigurationWithAvailableFeatures();
    }


    @DELETE
    @Path("/{featureName}")
    public boolean deleteFeature() {
        return enablementService.withConfigurationWithActiveFeatures();
    }

}
