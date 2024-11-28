package com.globant.test.api.stepDefinitions;
import com.globant.test.api.models.Resource;
import com.globant.test.api.requests.ResourceRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import java.util.List;
import java.util.Map;

public class ResourceSteps {

        private static final Logger logger = LogManager.getLogger(com.globant.test.api.stepDefinitions.ClientSteps.class);
        private final ResourceRequest resourceRequest = new ResourceRequest();
        private Response response;
        private Resource resource;

        @Given("there are resources available")
        public void thereAreRegisteredClientsInTheSystem() {
            response = resourceRequest.getResources();
            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
            if(resourceList.isEmpty()) {
                response = resourceRequest.createResources();
                for(int i =1; i<15; i++) {
                    response = resourceRequest.createResources();
                    logger.info(response.jsonPath().prettify());
                }
            }
        }



        @When("I retrieve the details of the resource with ID {string}")
        public void sendGETRequest(String resourceId) {
            response = resourceRequest.getResource(resourceId);
            logger.info(response.jsonPath().prettify());
            logger.info("The status code is: {}", response.statusCode());
        }




        @When("I send a GET request to view all the resources")
        public void iSendAGETRequestToViewAllTheResources() {
            response = resourceRequest.getResources();
        }




        @When("I send a DELETE request to delete all the resources")
        public void iSendADELETERequestToDeleteAllTheResources() {
            logger.info(response.jsonPath().prettify());
            response = resourceRequest.getResources();
            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
            if (!resourceList.isEmpty()) {
                for (Resource resources : resourceList) {
                    Response deleteResponse = resourceRequest.deleteResource(resources.getId());
                    logger.info("Resource with ID " + resources.getId() + " deleted.");
                }
            } else {
                logger.info("Error");
            }
        }

        @When("I send a PUT request to update all the resources active status should be true")
        public void iSendAPUTRequestToUpdateTheResourceWithActive() {
            response = resourceRequest.getResources();
            List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
            if (!resourceList.isEmpty()) {
                for (Resource resources : resourceList) {
                    boolean active = resources.isActive();
                   if(active){
                      resources.setActive(false);
                      response = resourceRequest.updateResource(resources,false);
                       logger.info("Resource with ID " + resources.getId() + " changed.");
                   }

                }
            } else {
                logger.info("Error");
            }

        }

        @Then("the resource response should have a status code of {int}")
        public void theResourceResponseShouldHaveAStatusCodeOf(int statusCode) {
            Assert.assertEquals(statusCode, response.statusCode());
        }

        @Then("the resources response should have the following details:")
        public void theResourcesResponseShouldHaveTheFollowingDetails(DataTable expectedData) {
            resource = resourceRequest.getResourceEntity(response);
            Map<String, String> expectedDataMap = expectedData.asMaps().get(0);
            Assert.assertEquals(expectedDataMap.get("Name"), resource.getName());
            Assert.assertEquals(expectedDataMap.get("Trademark"), resource.getTrademark());
            Assert.assertEquals(expectedDataMap.get("Stock"), resource.getStock());
            Assert.assertEquals(expectedDataMap.get("Price"), resource.getPrice());
            Assert.assertEquals(expectedDataMap.get("Description"), resource.getDescription());
            Assert.assertEquals(expectedDataMap.get("Tags"), resource.getTags());
            Assert.assertTrue(expectedDataMap.get("Active"), resource.isActive());
            Assert.assertEquals(expectedDataMap.get("Id"), resource.getId());
        }

        @Then("the response should include the details of the created resource")
        public void theResponseShouldIncludeTheDetailsOfTheCreatedResource() {
            Resource new_resource = resourceRequest.getResourceEntity(response);
            new_resource.setId(null);
            Assert.assertEquals(resource, new_resource);
        }

        @Then("validates the resource response with resource JSON schema")
        public void userValidatesResponseWithResourceJSONSchema() {
            String path = "schemas/ResourceSchema.json";
            Assert.assertTrue(resourceRequest.validateSchema(response, path));
            logger.info("Successfully Validated schema from Resource object");
        }

        @Then("validates the response with resource list JSON schema")
        public void userValidatesResponseWithResourceListJSONSchema() {
            String path = "schemas/ResourceListSchema.json";
            Assert.assertTrue(resourceRequest.validateSchema(response, path));
            logger.info("Successfully Validated schema from Client List object");
        }

    @When("I retrieve the details of the resources with active status true")
    public void iRetrieveTheDetailsOfTheResourcesWithActiveStatusTrue() {
        response = resourceRequest.getResources();
        List<Resource> resourceList = resourceRequest.getResourcesEntity(response);
        if (!resourceList.isEmpty()) {
            for (Resource resources : resourceList) {
                if(resources.isActive()){
                    logger.info("Resource with ID " + resources.getId() + " has active status: " + resources.isActive());

                }
            }
        } else {
            logger.info("Error");

        }

    }


    @When("I send a PUT request to update the resource with ID {string}")
    public void iSendAPUTRequestToUpdateTheResourceWithIDString(String resourceId,String requestBody) {
        resource = resourceRequest.getResourceEntity(requestBody);
        response = resourceRequest.updateResourceById(resource, resourceId);
    }




}


