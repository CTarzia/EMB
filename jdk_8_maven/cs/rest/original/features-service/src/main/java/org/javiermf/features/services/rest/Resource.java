package org.javiermf.features.services.rest;


import io.swagger.annotations.Api;
import org.javiermf.features.epaservices.rest.EnablementResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Component
@Produces("application/json")
@Path("/")
@Api
public class Resource {
    @Autowired
    EnablementResource enablementResource;

    @Autowired
    ProductsResource productsResource;

    @Path("products")
    public ProductsResource productsResource() {
        return productsResource;
    }
    @Path("enabledEndpoints")
    public EnablementResource enablementResource() {
        return enablementResource;
    }
}
