package org.javiermf.features.epaservices.rest;

import org.javiermf.features.epaservices.EnablementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
