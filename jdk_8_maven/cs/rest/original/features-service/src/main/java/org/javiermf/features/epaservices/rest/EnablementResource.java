package org.javiermf.features.epaservices.rest;


import io.swagger.models.HttpMethod;

import org.evomaster.client.java.controller.api.dto.database.execution.epa.EnabledDto;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.EnabledRestActionsDto;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestActionDto;
import org.javiermf.features.epaservices.EnablementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import java.util.Arrays;


@Component
@Produces("application/json")
public class EnablementResource {
//    @Autowired
//    ProductsResourceEnablement productsResourceEnablement;
//
//    @Autowired
//    ProductsConfigurationResourceEnablement productsConfigurationResourceEnablement;
//
//    @Autowired
//    ProductsFeaturesResourceEnablement productsFeaturesResourceEnablement;
//
//    @Autowired
//    ProductsConstraintsResourceEnablement productsConstraintsResourceEnablement;

    @Autowired
    EnablementService enablementService;

//    @Path("products")
//    public ProductsResourceEnablement productsResourceEnablement() {
//        return productsResourceEnablement;
//    }
//
//    @Path("products/{productName}/configurations")
//    public ProductsConfigurationResourceEnablement productsConfigurationResourceEnablement() {
//        return productsConfigurationResourceEnablement;
//    }
//
//    @Path("products/{productName}/features")
//    public ProductsFeaturesResourceEnablement productsFeaturesResourceEnablement() {
//        return productsFeaturesResourceEnablement;
//    }
//
//    @Path("products/{productName}/constraints")
//    public ProductsConstraintsResourceEnablement productsConstraintsResourceEnablement() {
//        return productsConstraintsResourceEnablement;
//    }
    @GET
    public EnabledRestActionsDto getEnablementInfo() {
        EnabledRestActionsDto enabledRestActionsDto = new EnabledRestActionsDto();

        enabledRestActionsDto.enabledRestActions.add(new RestActionDto("get", "/products"));
        enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "/products/{productName}"));

        if (enablementService.withProducts()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("get", "/products/{productName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("get", "products/{productName}/configurations"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("get", "/products/{productName}/features"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfiguration()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("get", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}/features"));
        }

        if (enablementService.withProductWithTwoFeatures()) {
           enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/constraints/excludes"));
           enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/constraints/requires"));
        }

        if (enablementService.withProductWithFeature()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("put", "/products/{productName}/features/{featureName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithAvailableFeatures()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("post", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithActiveFeatures()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConstraints()) {
            enabledRestActionsDto.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}/constraints/{constraintId}"));
        }

        return enabledRestActionsDto;
    }

}
