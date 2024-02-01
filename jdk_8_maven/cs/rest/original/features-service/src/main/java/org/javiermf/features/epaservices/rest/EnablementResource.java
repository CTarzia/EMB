package org.javiermf.features.epaservices.rest;


import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestActionDto;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestActionsDto;
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
    public RestActionsDto getEnablementInfo() {
        RestActionsDto enabledRestActions = new RestActionsDto();

        enabledRestActions.enabledRestActions.add(new RestActionDto("get", "/products"));
        enabledRestActions.enabledRestActions.add(new RestActionDto("post", "/products/{productName}"));

        if (enablementService.withProducts()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("get", "/products/{productName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("get", "products/{productName}/configurations"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("post", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("get", "/products/{productName}/features"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfiguration()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("get", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}/features"));
        }

        if (enablementService.withProductWithTwoFeatures()) {
           enabledRestActions.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/constraints/excludes"));
           enabledRestActions.enabledRestActions.add(new RestActionDto("post", "/products/{productName}/constraints/requires"));
        }

        if (enablementService.withProductWithFeature()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("put", "/products/{productName}/features/{featureName}"));
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithAvailableFeatures()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("post", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConfigurationWithActiveFeatures()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "products/{productName}/configurations/{configurationName}/features/{featureName}"));
        }

        if (enablementService.withConstraints()) {
            enabledRestActions.enabledRestActions.add(new RestActionDto("delete", "/products/{productName}/constraints/{constraintId}"));
        }

        return enabledRestActions;
    }

}
