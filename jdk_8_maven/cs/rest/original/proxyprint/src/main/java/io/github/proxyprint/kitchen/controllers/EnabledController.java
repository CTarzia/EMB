package io.github.proxyprint.kitchen.controllers;

import io.github.proxyprint.kitchen.models.repositories.*;
import io.swagger.annotations.ApiOperation;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestAction;
import org.evomaster.client.java.controller.api.dto.database.execution.epa.RestActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@Transactional
public class EnabledController {

    @Autowired
    private PrintShopDAO printshops;

    @Autowired
    private RegisterRequestDAO registerRequests;

    @Autowired
    private ConsumerDAO consumers;

    @Autowired
    private NotificationDAO notifications;

    @Autowired
    private PrintRequestDAO printRequests;

    @Autowired
    private PrintingSchemaDAO printingSchemas;

    @Autowired
    private ManagerDAO managers;

    @Autowired
    private EmployeeDAO employees;

    @Autowired
    private DocumentDAO documents;

    @Autowired
    private ReviewDAO reviews;

    /**
     * We ignore all endpointsToSkip. We assume the app is initialized as in EmbeddedEvomasterController::resetStateOfSUT
     */
    @ApiOperation(value = "Returns list of enabled endpoints.")
    @RequestMapping(value = "/enabledEndpoints", method = RequestMethod.GET)
    public RestActions getEnabledEndpoints() {
        RestActions enabledRestActions = new RestActions();
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/admin/seed"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/admin/useed"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/admin/printshops"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/"));
        enabledRestActions.enabledRestActions.add(new RestAction("options", "/*"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/api/secured"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/login"));

        if (!hasPrintShops()) {
            return enabledRestActions;
        }

        enabledRestActions.enabledRestActions.add(new RestAction("post", "/request/register"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/requests/pending"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/admin/register"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printdocument"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/register"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/{printShopID}/employees"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{printShopID}/employees"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/nearest"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/requests"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/requests"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/{id}"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/{id}/pricetable"));
        enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/{id}/reviews"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/reviews"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/covers"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/deletecover"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/deletepaper"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/deletering"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/papers"));
        enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/{id}/pricetable/rings"));
        enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{printShopID}/pricetable/editstapling"));

        if (hasRegisteredRequests()) {
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/request/accept/{id}"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/request/reject/{id}"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/requests/cancel/{id}"));
        }

        if (hasConsumers()) {
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/notifications"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/subscribe"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/{id}/notify"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/budget"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/{consumerID}/printingschemas"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/{consumerID}/printingschemas"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/balance"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/info"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/requests"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/consumer/satisfied"));
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/consumer/{username}/notifications"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/consumer/{username}/notifications"));

            if (hasNotifications()) {
                enabledRestActions.enabledRestActions.add(new RestAction("put", "/notifications/{notificationId}"));
                enabledRestActions.enabledRestActions.add(new RestAction("delete", "/notifications/{notificationId}"));
            }

            if (hasPrintRequests()) {
                enabledRestActions.enabledRestActions.add(new RestAction("post", "/consumer/printrequest/{printRequestID}/submit"));
                enabledRestActions.enabledRestActions.add(new RestAction("get", "/printdocument/{id}"));
                enabledRestActions.enabledRestActions.add(new RestAction("post", "/printdocument/{id}/budget"));
                enabledRestActions.enabledRestActions.add(new RestAction("delete", "/consumer/requests/cancel/{id}"));
                enabledRestActions.enabledRestActions.add(new RestAction("post", "/paypal/ipn/consumer/{consumerID}"));
                enabledRestActions.enabledRestActions.add(new RestAction("post", "/paypal/ipn/{printRequestID}"));
            }

            if (hasPrintingSchemas()) {
                enabledRestActions.enabledRestActions.add(new RestAction("put", "/consumer/{consumerID}/printingschemas/{printingSchemaID}"));
                enabledRestActions.enabledRestActions.add(new RestAction("delete", "/consumer/{consumerID}/printingschemas/{printingSchemaID}"));
            }
        }

        if (hasManagersWithPrintShop()) {
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshop"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/stats"));
        }

        if (hasPrintShopWithEmployees()) {
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{printShopID}/employees"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/printshops/{printShopID}/employees/{employeeID}"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/history"));
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/satisfied"));

            if (hasDocuments()) {
                enabledRestActions.enabledRestActions.add(new RestAction("get", "/documents/{id}"));
            }
        }

        if (hasEmployeeWithPrintRequests()) {
            enabledRestActions.enabledRestActions.add(new RestAction("get", "/printshops/requests/{id}"));
            enabledRestActions.enabledRestActions.add(new RestAction("post", "/printshops/requests/{id}"));
        }

        if (hasPrintShopWithReviews()) {
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{printShopId}/reviews/{reviewId}"));
            enabledRestActions.enabledRestActions.add(new RestAction("delete", "/printshops/{printShopId}/reviews/{reviewId}"));
        }

        if (hasPrintShopWithCoverItems()) {
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{id}/pricetable/covers"));
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{id}/pricetable/papers"));
            enabledRestActions.enabledRestActions.add(new RestAction("put", "/printshops/{id}/pricetable/rings"));
        }

        return enabledRestActions;
    }

    private boolean hasPrintShopWithCoverItems() {
        AtomicBoolean printShopWithCoverItems = new AtomicBoolean(false);
        printshops.findAll().forEach(p -> {
            if (!printShopWithCoverItems.get() && !p.getPriceTable().isEmpty()) {
                printShopWithCoverItems.set(true);
            }
        });
        return printShopWithCoverItems.get();
    }

    private boolean hasPrintShopWithReviews() {
        AtomicBoolean printShopWithReviews = new AtomicBoolean(false);
        printshops.findAll().forEach(e -> {
            if (!printShopWithReviews.get() && e.getReviews().size() > 0) {
                printShopWithReviews.set(true);
            }
        });
        return printShopWithReviews.get();
    }

    private boolean hasDocuments() {
        return documents.count() > 0;
    }

    private boolean hasEmployeeWithPrintRequests() {
        AtomicBoolean employeeHasPrintRequests = new AtomicBoolean(false);
        employees.findAll().forEach(e -> {
            if (!employeeHasPrintRequests.get() && e.getPrintShop().getPrintRequests() != null) {
                employeeHasPrintRequests.set(true);
            }
        });
        return employeeHasPrintRequests.get();
    }

    private boolean hasPrintShopWithEmployees() {
        AtomicBoolean printshopHasEmployees = new AtomicBoolean(false);
        employees.findAll().forEach(e -> {
            if (!printshopHasEmployees.get() && e.getPrintShop() != null) {
                printshopHasEmployees.set(true);
            }
        });
        return printshopHasEmployees.get();
    }

    private boolean hasManagersWithPrintShop() {
        AtomicBoolean managerHasPrintShop = new AtomicBoolean(false);
         managers.findAll().forEach(m -> {
             if (!managerHasPrintShop.get() && m.getPrintShop() != null) {
                 managerHasPrintShop.set(true);
             }
         });
         return managerHasPrintShop.get();
    }

    private boolean hasPrintingSchemas() {
        return printingSchemas.count() > 0;
    }

    private boolean hasPrintRequests() {
        return printRequests.count() > 0;
    }


    private boolean hasNotifications() {
        return notifications.count() > 0;
    }

    private boolean hasConsumers() {
        return consumers.count() > 0;
    }

    private boolean hasRegisteredRequests() {
        return registerRequests.count() > 0;
    }

    private boolean hasPrintShops() {
        return printshops.count() > 0;
    }
}
