package org.javiermf.features.services.rest.epa;

import org.javiermf.features.models.Feature;
import org.javiermf.features.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

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
