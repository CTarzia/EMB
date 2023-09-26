package org.javiermf.features.services.rest.epa;


import io.swagger.annotations.Api;
import org.javiermf.features.models.Product;
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
import java.util.List;


@Component
@Path("/enablement")
@Produces("application/json")
@Api
public class EnablementResource {
    private static final String PRODUCTS = "products";

    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsConfigurationResource productsConfigurationResource;

    @Autowired
    ProductsFeaturesResource productsFeaturesResource;

    @Autowired
    ProductsConstraintsResource productsConstraintsResource;
//
//    @Path("{httpVerb}/{requestPath}")
//    @GET
//    public boolean getEnablement(@PathParam("httpVerb") String httpVerb, @PathParam("requestPath") String requestPath) {
//        String[] path = requestPath.split("/");
//        if (!PRODUCTS.equals(path[1])) { //the first part of the path is empty as it starts with "/"
//            return true; // get OpenApi documentation
//        } else if (path.length == 2){
//            /* path[2] is always products
//            if the length is two it must be /products*/
//            return true;
//        }
////        switch (path) {
////
////        }
//    }

}
