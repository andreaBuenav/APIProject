package com.globant.test.api.requests;

import com.github.javafaker.Faker;
import com.globant.test.api.models.Resource;
import com.globant.test.api.util.Constants;
import com.google.gson.Gson;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResourceRequest extends BaseRequest{
    private String endpoint;

    /**
     Get Resource list

     @return rest-assured response
     */
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }


    /**
     Get client by id

     @param resourceId string
     @return rest-assured response
     */
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestGet(endpoint, createBaseHeaders());
    }



    /**
     Update client by id

     @param resource model
     @return rest-assured response
     */
    public Response updateResource(Resource resource, Boolean active) {
        resource.setActive(active);
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resource.getId());
        return requestPut(endpoint, createBaseHeaders(), resource);
    }
    public Response updateResourceById(Resource resource, String resourceId){
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }

    /**
     Delete resource by id

     @param resourceId string
     @return rest-assured response
     */
    public Response deleteResource(String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCES_PATH, resourceId);
        return requestDelete(endpoint, createBaseHeaders());
    }


    /**
     Get Resource entity

     @return rest-assured response
     */
    public Resource getResourceEntity(@NotNull Response response) {
        return response.as(Resource.class);
    }

    /**
     Get a list of Resources entities

     @return rest-assured response
     */

    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    /**
     Create resource
     @return new resource
     */

    public Response createResources(){
        Resource resource = new Resource();
        Faker faker = new Faker();
        String name = faker.commerce().productName();
        resource.setName(name);
        resource.setTrademark(String.valueOf(faker.company().name()));
        resource.setStock(String.valueOf(faker.number()));
        resource.setPrice(String.valueOf(faker.commerce().price()));
        resource.setDescription(String.valueOf(faker.lorem().paragraph()));
        resource.setTags(String.valueOf(faker.lorem().word()));
        resource.setActive(Boolean.parseBoolean(String.valueOf(faker.bool().bool())));

        return createResource(resource);

    }

    /**
     Create resource

     @param resource model
     @return rest-assured response
     */
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.URL, Constants.RESOURCES_PATH);
        return requestPost(endpoint, createBaseHeaders(), resource);
    }

    public Resource getResourceEntity(String resourceJson) {
        Gson gson = new Gson();
        return gson.fromJson(resourceJson, Resource.class);
    }

    /**
     Validates resource schemas

     @param schemaPath model
     @return rest-assured response
     */

    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true;
        }
        catch(AssertionError e) {
            e.printStackTrace();
            return false;
        }
    }

}
