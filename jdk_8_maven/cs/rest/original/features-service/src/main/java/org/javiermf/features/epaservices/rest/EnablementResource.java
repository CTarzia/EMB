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
        RestActions enabledRestActionsDto = new RestActions();

        enabledRestActionsDto.enabledRestActions.add(new RestAction("get", "/products"));
        enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "/products/{productName}"));

        if (enablementService.withProducts()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("get", "/products/{productName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "/products/{productName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("get", "products/{productName}/configurations"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("get", "/products/{productName}/features"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfiguration()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("get", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}/features"));
        }

        if (enablementService.withProductWithTwoFeatures()) {
           enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "/products/{productName}/constraints/excludes"));
           enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "/products/{productName}/constraints/requires"));
        }

        if (enablementService.withProductWithFeature()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("put", "/products/{productName}/features/{featureName}"));
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithAvailableFeatures()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("post", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithActiveFeatures()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConstraints()) {
            enabledRestActionsDto.enabledRestActions.add(new RestAction("delete", "/products/{productName}/constraints/{constraintId}"));
        }

        return enabledRestActionsDto;
    }

}
