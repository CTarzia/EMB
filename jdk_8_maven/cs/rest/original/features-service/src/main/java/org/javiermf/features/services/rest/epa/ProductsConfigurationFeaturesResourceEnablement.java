package org.javiermf.features.services.rest.epa;

import org.javiermf.features.exceptions.WrongProductConfigurationException;
import org.javiermf.features.models.evaluation.EvaluationResult;
import org.javiermf.features.services.ProductsConfigurationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
