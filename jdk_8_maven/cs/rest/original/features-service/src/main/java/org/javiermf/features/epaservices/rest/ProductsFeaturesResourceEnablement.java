package org.javiermf.features.epaservices.rest;

import org.javiermf.features.epaservices.EnablementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

@Component
@Produces("application/json")
public class ProductsFeaturesResourceEnablement {

    @Autowired
    EnablementService enablementService;

    @GET
    public boolean getFeaturesForProduct() {
        return enablementService.withProducts();
    }

    @POST
    @Path("/{featureName}")
    public boolean addFeatureToProduct() {
        return enablementService.withProducts();
    }

    @PUT
    @Path("/{featureName}")
    public boolean updateFeatureOfProduct() {
        return enablementService.withProductWithFeature();
    }

    @DELETE
    @Path("/{featureName}")
    public boolean deleteFeatureOfProduct() {
        return enablementService.withProductWithFeature();
    }
}
