package org.javiermf.features.epaservices.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Component
@Produces("application/json")
public class EnablementResource {
    @Autowired
    ProductsResourceEnablement productsResourceEnablement;

    @Autowired
    ProductsConfigurationResourceEnablement productsConfigurationResourceEnablement;

    @Autowired
    ProductsFeaturesResourceEnablement productsFeaturesResourceEnablement;

    @Autowired
    ProductsConstraintsResourceEnablement productsConstraintsResourceEnablement;

    @Path("products")
    public ProductsResourceEnablement productsResourceEnablement() {
        return productsResourceEnablement;
    }

    @Path("products/{productName}/configurations")
    public ProductsConfigurationResourceEnablement productsConfigurationResourceEnablement() {
        return productsConfigurationResourceEnablement;
    }

    @Path("products/{productName}/features")
    public ProductsFeaturesResourceEnablement productsFeaturesResourceEnablement() {
        return productsFeaturesResourceEnablement;
    }

    @Path("products/{productName}/constraints")
    public ProductsConstraintsResourceEnablement productsConstraintsResourceEnablement() {
        return productsConstraintsResourceEnablement;
    }

}
