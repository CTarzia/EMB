package org.javiermf.features.epaservices.rest;


import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestAction;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestActions;
import org.javiermf.features.epaservices.EnablementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;


@Component
@Produces("application/json")
public class EnablementResource {

    @Autowired
    EnablementService enablementService;

    @GET
    public RestActions getEnablementInfo() {
        RestActions enabledRestActions = new RestActions();

        enabledRestActions.enabledRestActions.add(new RestAction("get", "/products"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/products/{productName}"));

        if (enablementService.withProducts()) {
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/products/{productName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/products/{productName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "products/{productName}/configurations"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/products/{productName}/features"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfiguration()) {
            enabledRestActions.enabledRestActions.add(new RestAction("get", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}/features"));
        }

        if (enablementService.withProductWithTwoFeatures()) {
           enabledRestActions.enabledRestActions.add(new RestAction("post", "/products/{productName}/constraints/excludes"));
           enabledRestActions.enabledRestActions.add(new RestAction("post", "/products/{productName}/constraints/requires"));
        }

        if (enablementService.withProductWithFeature()) {
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/products/{productName}/features/{featureName}"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithAvailableFeatures()) {
            enabledRestActions.enabledRestActions.add(new RestAction("post", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithActiveFeatures()) {
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConstraints()) {
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/products/{productName}/constraints/{constraintId}"));
        }

        return enabledRestActions;
    }

}
