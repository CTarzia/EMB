package org.javiermf.features.services.rest.epa;


import io.swagger.annotations.Api;
import org.javiermf.features.services.ProductsService;
import org.javiermf.features.services.rest.ProductsConfigurationResource;
import org.javiermf.features.services.rest.ProductsConstraintsResource;
import org.javiermf.features.services.rest.ProductsFeaturesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;


@Component
@Path("enabled/products")
@Produces("application/json")
@Api
public class ProductsResourceEnablement {

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsConfigurationResourceEnablement productsConfigurationResourceEnablement;

    @Autowired
    ProductsFeaturesResourceEnablement productsFeaturesResourceEnablement;

    @Autowired
    ProductsConstraintsResourceEnablement productsConstraintsResourceEnablement;

    @Autowired
    EnablementService enablementService;

    @GET
    public boolean getAllProducts() {
        return true;
    }

    @Path("{productName}")
    @GET
    public boolean getProductByName(@PathParam("productName") String productName) {
        return enablementService.withProducts();
    }

    @Path("{productName}")
    @DELETE
    public boolean deleteProductByName(@PathParam("productName") String productName) {
        return enablementService.withProducts();
    }

    @Path("{productName}")
    @POST
    public boolean addProduct(@PathParam("productName") String productName) throws URISyntaxException {
        return true;
    }


    @Path("{productName}/configurations")
    public ProductsConfigurationResourceEnablement productsConfigurationResource() {
        return productsConfigurationResourceEnablement;
    }

    @Path("{productName}/features")
    public ProductsFeaturesResourceEnablement productsFeaturesResource() {
        return productsFeaturesResourceEnablement;
    }

    @Path("{productName}/constraints")
    public ProductsConstraintsResourceEnablement productsConstraintsResource() {
        return productsConstraintsResourceEnablement;
    }

}
