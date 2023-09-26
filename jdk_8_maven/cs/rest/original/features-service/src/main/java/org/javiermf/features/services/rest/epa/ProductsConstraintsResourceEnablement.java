package org.javiermf.features.services.rest.epa;

import org.javiermf.features.models.constraints.FeatureConstraint;
import org.javiermf.features.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@Produces("application/json")
public class ProductsConstraintsResourceEnablement {

    @Autowired
    EnablementService enablementService;

    @POST
    @Path("requires")
    public boolean addRequiresConstraintToProduct() {
        return enablementService.withProductWithTwoFeatures();
    }

    @POST
    @Path("excludes")
    public boolean addExcludesConstraintToProduct() {
        return enablementService.withProductWithTwoFeatures();
    }

    @DELETE
    @Path("{constraintId}")
    public boolean deleteConstraint() {
        return enablementService.withConstraints();
    }
}
