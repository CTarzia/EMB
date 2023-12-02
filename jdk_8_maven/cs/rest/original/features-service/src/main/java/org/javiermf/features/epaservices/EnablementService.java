package org.javiermf.features.epaservices;


import org.javiermf.features.daos.ProductsConfigurationsDAO;
import org.javiermf.features.daos.ProductsDAO;
import org.javiermf.features.services.rest.ProductsConfigurationResource;
import org.javiermf.features.services.rest.ProductsConstraintsResource;
import org.javiermf.features.services.rest.ProductsFeaturesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnablementService {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProductsConfigurationsDAO productsConfigurationsDAO;

    @Autowired
    ProductsConfigurationResource productsConfigurationResource;

    @Autowired
    ProductsFeaturesResource productsFeaturesResource;

    @Autowired
    ProductsConstraintsResource productsConstraintsResource;

    public boolean withProducts() {
        return productsDAO.findAll().size() > 0;
    }


    public boolean withProductWithFeature() {
        return productsDAO.findAll().stream()
                .filter(p -> p.getProductFeatures().size() > 0)
                .toArray().length > 0;
    }

    public boolean withProductWithTwoFeatures() {
        return productsDAO.findAll().stream()
                .filter(p -> p.getProductFeatures().size() >= 2)
                .toArray().length > 0;
    }

    public boolean withConstraints() {
        return productsDAO.findAllConstraints().size() > 0;
    }

    public boolean withConfiguration() {
        return productsConfigurationsDAO.findAllConfigurations().size() > 0;
    }

    public boolean withConfigurationWithAvailableFeatures() {
        return productsConfigurationsDAO.findAllConfigurations().stream()
                .filter(c -> c.availableFeatures().size() > 0)
                .toArray().length > 0;
    }

    public boolean withConfigurationWithActiveFeatures() {
        return productsConfigurationsDAO.findAllConfigurations().stream()
                .filter(c -> c.activedFeatures().size() > 0)
                .toArray().length > 0;
    }
}
